<%--
  Created by IntelliJ IDEA.
  User: kakasleki
  Date: 2021-06-16
  Time: 오후 4:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="Referrer-Policy" content="no-referrer | same-origin"/>
<link href="<c:url value="/css/common.css" />" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<c:url value="/js/common/task.common.js" />"></script>
<title>과제</title>

<input type="hidden" id="ctx" value="${ctx}">
<div id="ajaxLoadingDiv">
	<img src="<c:url value="/images/loading.gif" />" alt="처리중" />
</div>
