let noticeList;
const DEFAULT_SORT = "updateDate,desc";
const DEFAULT_URL = getContextPath() + "/board/notice";
const DEFAULT_ATTACH_URL = getContextPath() + "/board/attach";

const searchInput = function () {
	let searchOption = document.getElementById("search_option");
	let searchTxt = document.getElementById("search_txt");
	let startDate = document.getElementById("start_date");
	let endDate = document.getElementById("end_date");
	let pageNo = document.getElementById("pageNo");
	let display = document.getElementById("display");

	return {
		search_option : searchOption,
		search_txt : searchTxt,
		start_date : startDate,
		end_date : endDate,
		pageNo : pageNo,
		display : display
	};
}();
const tableTbody = document.getElementById("noticeTbl").getElementsByTagName("tbody")[0];

document.addEventListener("DOMContentLoaded", function () {
	getNoticeInfo(1);
});

function getNoticeInfo(page, sort) {
	let dataObject = {
		"search_option" : searchInput.search_option.value,
		"search_txt" : searchInput.search_txt.value,
		"start_date" : searchInput.start_date.value,
		"end_date" : searchInput.end_date.value,
		"page" : Number(page) - 1,
		"size" : searchInput.display.value,
		"sort" : !isNull(sort) ? sort : DEFAULT_SORT
	};

	searchInput.pageNo.value = page;

	commonAjax(DEFAULT_URL + "/notice", "GET", dataObject, true, makeNoticeTable);
}

function makeNoticeTable(response) {
	if(isNull(response) || isNull(response.content) || Number(response.totalElements) === 0) {
		tableTbody.innerHTML = "<td colspan=\"100%\">등록된 공지사항이 없습니다.</td>";
		return false;
	}

	noticeList = response.content;
	let htmlTbody = [];
	let idx = 0;

	for(let noticeInfo of noticeList) {
		htmlTbody.push("<tr>");
		htmlTbody.push("<td><a href='#' data-key='" + (idx++) + "'>" + noticeInfo.subject + "</a></td>");
		htmlTbody.push("<td>" + noticeInfo.writer + "</td>");
		htmlTbody.push("<td>" + noticeInfo.updateDate + "</td>");
		htmlTbody.push("</tr>");
	}

	tableTbody.innerHTML = htmlTbody.join('');

	Array.from(document.querySelectorAll("table a")).forEach(
		function (element) {
			element.addEventListener("click", function () {
				noticeDetailShow(element.getAttribute("data-key"));
			});
		}
	)
}

function noticeDetailShow(idx) {
	alert("상세보기 제목 : " + noticeList[idx].subject);
}