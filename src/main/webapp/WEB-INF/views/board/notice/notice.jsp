<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<jsp:include page="/WEB-INF/views/common/common.jsp" />
	<link href="<c:url value="/css/dropzone.css" />" rel="stylesheet" type="text/css" />
	<title>공지사항</title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="container">
		<div class="form-inline">
			<label for="search_option" class="sr-only">검색조건</label>
			<select class="form-control" id="search_option" title="검색조건">
				<option value="">검색조건</option>
				<option value="0">제목</option>
				<option value="1">내용</option>
				<option value="2">제목+내용</option>
				<option value="3">작성자</option>
			</select>
			<label for="search_txt" class="sr-only">검색어</label><input type="text" class="form-control" id="search_txt" placeholder="검색어 입력" />
			<label for="start_date" class="sr-only">시작일자</label><input type="text" class="form-control" id="start_date" maxlength="8" />
			<label for="end_date" class="sr-only">종료일자</label><input type="text" class="form-control" id="end_date" maxlength="8" />
			<button class="btn btn-default" id="noticeSearchBtn">검색</button>
			<button class="btn btn-primary" data-toggle="modal" data-target="#noticeInputDlg" id="noticeInsertBtn">입력</button>

			<input type="hidden" id="pageNo" value="1" />
			<input type="hidden" id="display" value="10" />
		</div>

		<div class="table-responsive">
			<table class="table table-bordered" id="noticeTbl">
				<caption class="hidden">공지사항 정보</caption>
				<thead>
					<tr>
						<th scope="col">제목</th>
						<th scope="col">작성자</th>
						<th scope="col">작성일</th>
						<th scope="col">수정일</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="100%">등록된 공지사항이 없습니다.</td>
					</tr>
				</tbody>
			</table>
		</div>
		<nav>
			<ul class="pagination" id="paging"></ul>
		</nav>
	</div>

	<div class="modal fade" id="noticeInputDlg" tabindex="-1" role="dialog" aria-labelledby="noticeInputDlgLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="noticeInputDlgLabel">공지사항 입력</h5>
					<button type="button" class="close" name="noticeInputDlgCloseBtn" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>

				<div class="modal-body">
					<div class="form-horizontal">
						<div class="form-group">
							<label for="subject" class="col-sm-2 control-label">제목</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="subject" />
							</div>
						</div>
						<div class="form-group" id="attachFileDiv">
							<label class="col-sm-2 control-label">첨부파일</label>
							<div class="col-sm-10" id="attachListDiv"></div>
						</div>
						<div class="form-group">
							<label for="content" class="col-sm-2 control-label">내용</label>
							<div class="col-sm-10">
								<textarea class="form-control" rows="10" id="content"></textarea>
							</div>
						</div>
						<div class="form-group">
							<label for="content" class="col-sm-2 control-label">파일업로드</label>
							<div class="col-sm-10">
								<form id="fileUploadForm" action="${ctx}/board/attach/attach" class="dropzone" method="post"></form>
							</div>
						</div>
					</div>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="noticeSaveBtn">저장</button>
					<button type="button" class="btn btn-danger" id="noticeDeleteBtn">삭제</button>
					<button type="button" class="btn btn-default" name="noticeInputDlgCloseBtn" data-dismiss="modal">닫기</button>
					<input type="hidden" id="noticeNo" />
				</div>
			</div>
		</div>

	</div>

	<script type="text/javascript" src="<c:url value="/js/dropzone.js" />"></script>
	<script type="text/javascript" src="<c:url value="/js/views/board/notice/notice.js" />"></script>
</body>
</html>
