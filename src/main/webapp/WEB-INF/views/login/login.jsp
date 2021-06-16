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
</head>
<body>
	<div>
		<label for="id">아이디</label><input type="text" id="id" maxlength="30" required><br>
		<label for="password">비밀번호</label><input type="password" id="password" maxlength="30" required><br>
		<button id="loginBtn">로그인</button>
	</div>
</body>
<script type="text/javascript" src="<c:url value="/js/views/login/login.js" />"></script>
</html>
