package net.java_school.board.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java_school.action.Action;
import net.java_school.action.ActionForward;
import net.java_school.board.Article;
import net.java_school.board.BoardService;
import net.java_school.commons.NumbersForPaging;
import net.java_school.commons.Paginator;
import net.java_school.commons.WebContants;
import net.java_school.db.dbpool.MySQLConnectionManager;

public class ViewAction extends Paginator implements Action  {
	
	private MySQLConnectionManager dbmgr;
	
	public ViewAction(MySQLConnectionManager dbmgr) {
		this.dbmgr = dbmgr;
	}
	
	@Override
	public ActionForward execute(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		
		ActionForward forward = new ActionForward();
		
		int no = Integer.parseInt(req.getParameter("no"));
		int curPage = Integer.parseInt(req.getParameter("curPage"));
		String keyword = req.getParameter("keyword");
		
		BoardService service = new BoardService(dbmgr);
		
		int numPerPage = 20;
		int pagePerBlock = 10;
		
		int totalRecord = service.getTotalRecord(keyword);
		
		NumbersForPaging numbers = this.getNumbersForPaging(totalRecord, curPage, numPerPage, pagePerBlock);

		Article article = service.getArticle(no);
		
		int startRecord = (curPage - 1) * numPerPage + 1;
		int endRecord = curPage * numPerPage;
		List<Article> list = service.getArticleList(keyword, startRecord, endRecord);
		
		String title = article.getTitle();
		String content = article.getContent();
		content = content.replaceAll(WebContants.lineSeparator.value, "<br />");
		Date regdate = article.getRegdate();
		
		int listItemNo = numbers.getListItemNo();
		int prevPage = numbers.getPrevBlock();
		int firstPage = numbers.getFirstPage();
		int lastPage = numbers.getLastPage();
		int nextPage = numbers.getNextBlock();
		
		req.setAttribute("title", title);
		req.setAttribute("content", content);
		req.setAttribute("regdate", regdate);
		req.setAttribute("list", list);
		req.setAttribute("listItemNo", listItemNo);
		req.setAttribute("prevPage", prevPage);
		req.setAttribute("firstPage", firstPage);
		req.setAttribute("lastPage", lastPage);
		req.setAttribute("nextPage", nextPage);

		forward.setView("/board/view.jsp");
		
		return forward;
	}

}
