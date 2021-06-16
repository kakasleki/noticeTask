function getContextPath() {
	return document.getElementById("ctx").value;
}

function isNull(value) {
	let blank_pattern = /^\s+|\s+$/g;
	if(blank_pattern.test(value)) return true;

	return !value;
}

function replaceNull(value) {
	return isNull(value) ? "" : value;
}

function commonAjax(url, method, data, option, callBackFunction, isLoadingImg) {
	if(method.toUpperCase() === "FILE") {
		fileDownload(url, data);
		return;
	}
	let isLoading = !isNull(isLoadingImg) && isLoadingImg !== false ? true : isLoadingImg;
	const ajaxOption = {
		async: typeof option === "boolean" ? option : option.async || true,
		contentType: option.contentType || "application/x-www-form-urlencoded; charset=utf-8",
	}

	let xhr = new XMLHttpRequest();

	if(method.toUpperCase() === "GET") {
		xhr.open(method.toUpperCase(), url + "?" + jsonParamSerialize(data), ajaxOption.async);
	} else {
		xhr.open(method.toUpperCase(), url, ajaxOption.async);
	}

	xhr.setRequestHeader("ajax-request", "true");

	if (ajaxOption.contentType !== "multipart/form-data") {
		xhr.setRequestHeader("Content-Type", ajaxOption.contentType);
	}

	if(url.indexOf("session") === -1){
		ajaxLoadingImage(isLoading);
	}
	const dataOption = {
		"application/x-www-form-urlencoded; charset=utf-8": jsonParamSerialize(data),
		"application/json; charset=utf-8": JSON.stringify(data),
		"multipart/form-data": data
	}

	const params = dataOption[ajaxOption.contentType];

	xhr.send(method.toUpperCase() === "GET" ? null : params || data);

	xhr.onreadystatechange = function () {
		if(this.readyState === 4) {
			ajaxLoadingImage(false);

			if(this.status === 200) {
				callBackFunction(this.responseText && JSON.parse(this.responseText));
			} else if(this.status === 6653) {
				alert("로그인 세션이 만료되었습니다.");
				window.location.href =  getContextPath()+'/login';
			} else if(this.status === 400) {
				alert(this.responseText || "웹 페이지에서 유효하지 않은 요청을 하였습니다.(400)");
			} else if(this.status === 403) {
				alert(this.responseText || "웹 페이지를 볼 수 있는 권한이 없습니다.(403)");
			} else if(this.status === 404) {
				alert("요청하신 페이지를 찾을 수 없습니다.(404)");
			} else if(this.status === 405) {
				alert("서버로 올바르지 않은 요청이 들어왔습니다.(405)");
			} else if(this.status === 500) {
				alert("내부서버 오류가 발생하였습니다.(500)");
			} else {

			}
			if(url.indexOf("session") === -1 && url.indexOf("logout") === -1 && typeof(sessionExtension) == "function" && this.status !== 6653){
				sessionExtension();
			}
		}
	};

	xhr.onerror = function () {
		ajaxLoadingImage(false);
	};
}

function fileDownload(url, data) {
	window.location.href = url + "?" + jsonParamSerialize(data);
}

function ajaxLoadingImage(isShow) {
	if(!document.getElementById("ajaxLoadingDiv")) return false;
	if(isShow) document.getElementById("ajaxLoadingDiv").classList.add("on");
	else document.getElementById("ajaxLoadingDiv").classList.remove("on");
}

function jsonParamSerialize(jsonData) {
	if(isNull(jsonData)) return "";
	let resultParam = [];

	for(let data in jsonData) {
		resultParam.push(data + "=" + encodeURIComponent(jsonData[data]))
	}
	return resultParam.join("&");
}

function sessionExtension() {
	commonAjax(getContextPath() + "/set/session/time", "POST", null, true, function () {},false);
}