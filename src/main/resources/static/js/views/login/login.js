const loginInput = (function () {
	const id = document.getElementById("id");
	const pass = document.getElementById("password");
	const loginBtn = document.getElementById("loginBtn");

	return {
		user_id : id,
		user_pass : pass,
		login_btn : loginBtn
	};
})();

loginInput.login_btn.addEventListener("click", function () {
	if(isNull(loginInput.user_id.value)) {
		alert("아이디를 입력해 주십시오.");
		loginInput.user_id.focus();
		return false;
	} else if(isNull(loginInput.user_pass.value)) {
		alert("비밀번호를 입력해 주십시오.");
		loginInput.user_pass.focus();
		return false;
	}

	let dataObject = {
		"id" : loginInput.user_id.value,
		"password" : loginInput.user_pass.value
	};

	commonAjax(getContextPath() + "/login", "POST", dataObject, true, function (response) {
		if(response.result === "success") {
			window.location.href = getContextPath() + "/board/notice/main";
		} else {
			alert(response.msg);
		}
	});
});