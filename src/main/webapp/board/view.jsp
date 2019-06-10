<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Detailed</title>
<script type="text/javascript">
function goList(curPage, keyword) {
	location.href="list.do?curPage=" + curPage + "&keyword=" + keyword;
}
function goReply(no, curPage, keyword) {
	location.href="reply.do?no=" + no + "&curPage=" + curPage + "&keyword=" + keyword;
}
function goModify(no, curPage, keyword) {
	location.href="modify.do?no=" + no + "&curPage=" + curPage + "&keyword=" + keyword;
}
function goDelete(no, curPage, keyword) {
	var check = confirm('Are you sure you want to delete this item?');
	if (check) {
		var form = document.getElementById("delForm");
		form.submit();
	}
}
</script>
</head>
<body>
<h1>View Details</h1>
<h2>Title: ${title }, Creation: ${regdate }</h2>
<p>
${content }
</p>
<div>
	<input type="button" value="List" onclick="javascript:goList('${param.curPage }','${param.keyword }')" />
	<input type="button" value="Reply" onclick="javascript:goReply('${param.no }','${param.curPage }','${param.keyword }')" />
	<input type="button" value="Modify" onclick="javascript:goModify('${param.no }','${param.curPage }','${param.keyword }')" />
	<input type="button" value="Delete" onclick="javascript:goDelete('${param.no }','${param.curPage }','${param.keyword }')" />
</div>
<form id="delForm" action="del.do" method="post">
	<input type="hidden" name="no" value="${param.no }" />
	<input type="hidden" name="curPage" value="${param.curPage }" />
	<input type="hidden" name="keyword" value="${param.keyword }" />
</form>
</body>
</html>