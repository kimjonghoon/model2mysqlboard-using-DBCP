package net.java_school.board.action;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java_school.action.Action;
import net.java_school.action.ActionForward;
import net.java_school.board.Article;
import net.java_school.board.BoardService;
import net.java_school.db.dbpool.MySQLConnectionManager;

public class ReplyAction implements Action {

	private MySQLConnectionManager dbmgr;
	
	public ReplyAction(MySQLConnectionManager dbmgr) {
		this.dbmgr = dbmgr;
	}
	
	@Override
	public ActionForward execute(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {

		ActionForward forward = new ActionForward();

		int family = Integer.parseInt(req.getParameter("family"));
		int no = Integer.parseInt(req.getParameter("no"));
		int depth = Integer.parseInt(req.getParameter("depth"));
		int indent = Integer.parseInt(req.getParameter("indent"));
		
		int curPage = Integer.parseInt(req.getParameter("curPage"));
		String keyword = req.getParameter("keyword");
		keyword = URLEncoder.encode(keyword, "UTF-8");
		
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		Article article = new Article();
		article.setFamily(family);
		article.setParent(no);
		article.setDepth(depth);
		article.setIndent(indent);
		article.setTitle(title);
		article.setContent(content);

		BoardService service = new BoardService(dbmgr);
		service.replyArticle(article);
		
		forward.setView("list.do?curPage=" + curPage + "&keyword=" + keyword);
		forward.setRedirect(true);
		
		return forward;
	}

}
