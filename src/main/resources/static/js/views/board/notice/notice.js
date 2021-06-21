let noticeList;
let attachNoList = [];
let attachList = [];
const DEFAULT_SORT_COL = "createDate";
const DEFAULT_SORT = "desc";
const DEFAULT_URL = getContextPath() + "/board/notice";
const DEFAULT_ATTACH_URL = getContextPath() + "/board/attach";

const searchInput = function () {
	let searchOption = document.getElementById("search_option");
	let searchTxt = document.getElementById("search_txt");
	let startDate = document.getElementById("start_date");
	let endDate = document.getElementById("end_date");
	let pageNo = document.getElementById("pageNo");
	let display = document.getElementById("display");
	let orderColumn = "";
	let order = "";

	return {
		search_option : searchOption,
		search_txt : searchTxt,
		start_date : startDate,
		end_date : endDate,
		pageNo : pageNo,
		display : display,
		orderColumn : orderColumn,
		order : order
	};
}();
const noticeInput = function () {
	let subject = document.getElementById("subject");
	let content = document.getElementById("content");
	let noticeNo = document.getElementById("noticeNo");

	return {
		subject : subject,
		content : content,
		noticeNo : noticeNo
	};
}();
const tableTbody = document.getElementById("noticeTbl").getElementsByTagName("tbody")[0];
const noticeSaveBtn = document.getElementById("noticeSaveBtn");
const noticeDeleteBtn = document.getElementById("noticeDeleteBtn");
const attachFileDiv = document.getElementById("attachFileDiv");
const attachListDiv = document.getElementById("attachListDiv");
const noticeInputDivCloseBtn = document.getElementsByName("noticeInputDlgCloseBtn");
document.addEventListener("DOMContentLoaded", function () {
	setTableSort();
	getNoticeInfo();
});
document.getElementById("noticeSearchBtn").addEventListener("click", getNoticeInfo);
noticeInputDivCloseBtn.forEach(
	function (element) {
		element.addEventListener("click", function () {
			attachList = [];
			attachNoList = [];
			noticeInput.subject.value = "";
			noticeInput.content.value = "";
			noticeInput.noticeNo.value = "";

			Array.from(document.querySelectorAll(".dz-preview")).forEach(
				function (element) {
					element.remove();
				}
			);
		});
	}
);
document.getElementById("noticeSaveBtn").addEventListener("click", function () {
	saveNoticeInfo(this.value);
});
document.getElementById("noticeInsertBtn").addEventListener("click", function () {
	noticeSaveBtn.value = "INSERT";
	noticeDeleteBtn.classList.add("hidden");
	attachFileDiv.classList.add("hidden");
	noticeInput.noticeNo.value = "";
});
noticeDeleteBtn.addEventListener("click", function () {
	if(!confirm("공지사항을 삭제하시겠습니까?")) return false;

	commonAjax(DEFAULT_URL + "/notice/" + noticeInput.noticeNo.value, "DELETE", null, true, function (response) {
		if(response.result === "success") {
			alert("공지사항이 삭제되었습니다.");
			getNoticeInfo();
			noticeInputDivCloseBtn[0].click();
		} else {
			alert(response.msg);
		}
	});
});

Dropzone.options.fileUploadForm = {
	addRemoveLinks: true,
	dictRemoveFile: '삭제',
	removedfile : function(file) {
		let fileName = file.name;

		for(let i = 0; i < attachList.length; i++) {
			if(attachList[i].oriFileName === fileName) {
				removeAttachFile(attachNoList[i]);
				attachList.splice(i, 1);
				attachNoList.splice(i, 1);
				break;
			}
		}

		let _ref;
		return (_ref = file.previewElement) != null ? _ref.parentNode.removeChild(file.previewElement) : void 0;
	},
	success : function(data) {
		let result = JSON.parse(data.xhr.response);
		attachList.push(result[0]);
		attachNoList.push(result[0].attachNo)
	}
};

function removeAttachFile(attachNo) {
	commonAjax(DEFAULT_ATTACH_URL + "/attach/" + attachNo, "DELETE", null, true, function () {});
}

function getNoticeInfo() {
	let dataObject = {
		"search_option" : searchInput.search_option.value,
		"search_txt" : searchInput.search_txt.value,
		"start_date" : searchInput.start_date.value,
		"end_date" : searchInput.end_date.value,
		"page" : Number(searchInput.pageNo.value) - 1,
		"size" : searchInput.display.value,
		"sort" : !isNull(searchInput.orderColumn) ? searchInput.orderColumn + "," + searchInput.order : DEFAULT_SORT_COL + "," + DEFAULT_SORT
	};

	commonAjax(DEFAULT_URL + "/notice", "GET", dataObject, true, makeNoticeTable);
}

function makeNoticeTable(response) {
	if(isNull(response) || isNull(response.content) || Number(response.totalElements) === 0) {
		tableTbody.innerHTML = "<td colspan=\"100%\">등록된 공지사항이 없습니다.</td>";
		return false;
	}

	noticeList = response.content;
	let totalCnt = response.totalElements;
	let htmlTbody = [];
	let idx = 0;

	for(let noticeInfo of noticeList) {
		htmlTbody.push("<tr>");
		htmlTbody.push("<td><a href='#' data-toggle='modal' data-target='#noticeInputDlg' data-key='" + (idx++) + "'>" + noticeInfo.subject + "</a></td>");
		htmlTbody.push("<td>" + noticeInfo.writer + "</td>");
		htmlTbody.push("<td>" + noticeInfo.createDate + "</td>");
		htmlTbody.push("<td>" + noticeInfo.updateDate + "</td>");
		htmlTbody.push("</tr>");
	}

	tableTbody.innerHTML = htmlTbody.join('');

	new Paging('paging', 'display', 'pageNo', getNoticeInfo, totalCnt).init();

	Array.from(document.querySelectorAll("table a")).forEach(
		function (element) {
			element.addEventListener("click", function () {
				noticeDetailShow(element.getAttribute("data-key"));
			});
		}
	)
}

function noticeDetailShow(idx) {
	noticeSaveBtn.value = "UPDATE";
	noticeDeleteBtn.classList.remove("hidden");
	attachFileDiv.classList.remove("hidden");

	noticeInput.noticeNo.value = noticeList[idx].noticeNo;
	noticeInput.subject.value = noticeList[idx].subject;
	noticeInput.content.value = noticeList[idx].content;

	let contentAttach = noticeList[idx].attach;
	let attachHtml = [];

	for(let attach of contentAttach) {
		attachHtml.push("<div>");
		attachHtml.push("<a class='col-sm-60' href='#' name='attachDownload' value='" + attach.attachNo + "'>" + attach.oriFileName + "</a>");
		attachHtml.push("<button class='btn btn-default col-sm-20' name='attachDeleteBtn' value='" + attach.attachNo + "'>X</button>");
		attachHtml.push("</div>");
	}

	attachListDiv.innerHTML = attachHtml.join('');

	Array.from(document.querySelectorAll("a[name=attachDownload]")).forEach(
		function (element) {
			element.addEventListener("click", function () {
				getAttachFileDownload(element.getAttribute("value"));
			});
		}
	);

	Array.from(document.querySelectorAll("button[name=attachDeleteBtn]")).forEach(
		function (element) {
			element.addEventListener("click", function () {
				if(!confirm("첨부파일을 삭제하시겠습니까?")) return false;
				removeAttachFile(element.getAttribute("value"));
				element.parentElement.remove();

				for(let i = 0; i < noticeList.length; i++) {
					if(Number(noticeInput.noticeNo.value) === Number(noticeList[i].noticeNo)) {
						let attachInfoList = noticeList[i].attach;
						for(let j = 0; j < attachInfoList.length; j++) {
							if(Number(element.getAttribute("value")) === Number(attachInfoList[j].attachNo)) {
								noticeList[i].attach.splice(j, 1);
							}
						}
					}
				}
			});
		}
	);
}

function getAttachFileDownload(attachNo) {
	commonAjax(DEFAULT_ATTACH_URL + "/attach/" + attachNo, "FILE", null, true, function () {});
}

function saveNoticeInfo(type) {
	if(!confirm("공지사항을 저장하시겠습니까?")) return false;

	let method = type === "INSERT" ? "PUT" : "PATCH";
	let dataObject = {
		"subject" : noticeInput.subject.value,
		"content" : noticeInput.content.value,
		"attachNo" : attachNoList.join(',')
	};
	if(type === "UPDATE") dataObject.noticeNo = noticeInput.noticeNo.value;

	commonAjax(DEFAULT_URL + "/notice", method, dataObject, true, function (response) {
		if(response.result === "success") {
			alert("공지사항이 저장되었습니다.");
			getNoticeInfo();
			noticeInputDivCloseBtn[0].click();
		} else {
			alert(response.msg);
		}
	});
}

function setTableSort() {
	new TableSort("noticeTbl", {
		click: searchByOrder,
		headers: [
			{
				name: "제목",
				column: "subject"
			},
			{
				name: "작성자",
				column: "writer"
			},
			{
				name: "작성일",
				column: "createDate"
			},
			{
				name: "수정일",
				column: "updateDate"
			}
		]
	});
}
function searchByOrder(obj) {
	searchInput.order = obj.order;
	searchInput.orderColumn = obj.column;
	getNoticeInfo();
}
