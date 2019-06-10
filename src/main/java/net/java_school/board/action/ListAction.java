package net.java_school.board.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java_school.action.Action;
import net.java_school.action.ActionForward;
import net.java_school.board.Article;
import net.java_school.board.BoardService;
import net.java_school.commons.NumbersForPaging;
import net.java_school.commons.Paginator;
import net.java_school.db.dbpool.MySQLConnectionManager;

public class ListAction extends Paginator implements Action {

	private MySQLConnectionManager dbmgr;
	
	public ListAction(MySQLConnectionManager dbmgr) {
		this.dbmgr = dbmgr;
	}
	
	@Override
	public ActionForward execute(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		
		ActionForward forward = new ActionForward();
		int curPage = Integer.parseInt(req.getParameter("curPage"));
		String keyword = req.getParameter("keyword");
 
		BoardService service = new BoardService(dbmgr);
		
		int numPerPage = 10;
		int pagePerBlock = 5;
		
		int totalRecord = service.getTotalRecord(keyword);
		NumbersForPaging numbers = this.getNumbersForPaging(totalRecord, curPage, numPerPage, pagePerBlock);
		
		int offSet = (curPage - 1) * numPerPage;
		
		List<Article> list = service.getArticleList(keyword, offSet, numPerPage);
		int listItemNo = numbers.getListItemNo();
		int prevPage = numbers.getPrevBlock();
		int firstPage = numbers.getFirstPage();
		int lastPage = numbers.getLastPage();
		int nextPage = numbers.getNextBlock();
		int totalPage = numbers.getTotalPage();
		
		req.setAttribute("list", list);
		req.setAttribute("listItemNo", listItemNo);
		req.setAttribute("prevPage", prevPage);
		req.setAttribute("firstPage", firstPage);
		req.setAttribute("lastPage", lastPage);
		req.setAttribute("nextPage", nextPage);
		req.setAttribute("totalPage", totalPage);
		
		forward.setView("/board/list.jsp");
		
		return forward;
	}

}
