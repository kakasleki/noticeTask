<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="Referrer-Policy" content="no-referrer | same-origin"/>
<link href="<c:url value="/css/bootstrap.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/css/bootstrap-theme.min.css" />" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<c:url value="/js/jquery.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/bootstrap.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/common/task.common.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/common/task.tablesort.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/common/task.paging.js" />"></script>

<input type="hidden" id="ctx" value="${ctx}">
<div id="ajaxLoadingDiv" style="display: none;">
	<img src="<c:url value="/images/loading.gif" />" alt="처리중" />
</div>