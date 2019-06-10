package net.java_school.board.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java_school.action.Action;
import net.java_school.action.ActionForward;
import net.java_school.board.Article;
import net.java_school.board.BoardService;
import net.java_school.commons.WebContants;
import net.java_school.db.dbpool.MySQLConnectionManager;

public class ReplyFormAction implements Action {

	private MySQLConnectionManager dbmgr;
	
	public ReplyFormAction(MySQLConnectionManager dbmgr) {
		this.dbmgr = dbmgr;
	}
	
	@Override
	public ActionForward execute(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {

		ActionForward forward = new ActionForward();
		
		int no = Integer.parseInt(req.getParameter("no"));
		
		BoardService service = new BoardService(dbmgr);
		Article article = service.getArticle(no);

		String content = article.getContent();
		content = content.replaceAll(WebContants.lineSeparator.value, WebContants.lineSeparator.value + ">");
		content = WebContants.lineSeparator.value + WebContants.lineSeparator.value + ">" + content;
		article.setContent(content);
		
		req.setAttribute("article", article);
		
		forward.setView("/board/reply.jsp");
		
		return forward;
	}

}
