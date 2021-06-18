<%--
  Created by IntelliJ IDEA.
  User: kakasleki
  Date: 2021-06-16
  Time: 오후 1:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<jsp:include page="/WEB-INF/views/common/common.jsp" />
	<link href="<c:url value="/css/login.css" />" rel="stylesheet" type="text/css" />
	<title>로그인 페이지</title>
</head>
<body>
	<div class="container">
		<div class="form-signin">
			<h2 class="form-signin-heading">로그인</h2>
			<label for="id" class="sr-only">아이디</label><input type="text" class="form-control" id="id" maxlength="30" required autofocus placeholder="Id"><br>
			<label for="password" class="sr-only">비밀번호</label><input type="password" class="form-control" id="password" maxlength="30" required placeholder="Password"><br>
			<button class="btn btn-lg btn-primary btn-block" id="loginBtn">로그인</button>
		</div>
	</div>
</body>
<script type="text/javascript" src="<c:url value="/js/views/login/login.js" />"></script>
</html>
