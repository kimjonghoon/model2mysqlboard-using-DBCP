<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Reply</title>
<script>
function goView(no, curPage, keyword) {
	 location.href="view.do?no=" + no + "&curPage=" + curPage + "&keyword=" + keyword;
}
</script>
</head>
<body>
<h1>Reply</h1>
<form action="reply.do" method="post">
<input type="hidden" name="family" value="${article.family }" />
<input type="hidden" name="no" value="${article.articleNo }" />
<input type="hidden" name="indent" value="${article.indent }" />
<input type="hidden" name="depth" value="${article.depth }" />
<input type="hidden" name="curPage" value="${param.curPage }" />
<input type="hidden" name="keyword" value="${param.keyword }" />
<div>Title: <input type="text" name="title" size="45" value="${article.title }" /></div>
<div><textarea name="content" rows="10" cols="60">${article.content }</textarea></div>
<div>
<input type="submit" value="Submit" />
<input type="reset" value="Reset" />
<input type="button" value="Back to View Details" onclick="javascript:goView('${param.no }','${param.curPage }','${param.keyword }')" />
</div>
</form>
</body>
</html>