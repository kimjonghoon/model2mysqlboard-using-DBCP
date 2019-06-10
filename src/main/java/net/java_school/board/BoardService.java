package net.java_school.board;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import net.java_school.db.dbpool.MySQLConnectionManager;

public class BoardService {

	private BoardDao dao;

	public BoardService() {}
	
	public BoardService(MySQLConnectionManager dbmgr) {
		dao = new BoardDao(dbmgr);
	}

	public Map<String, Integer> getNumbersForPaging(int totalRecord, int curPage, int numPerPage, int pagePerBlock) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		int totalPage = totalRecord / numPerPage;
		if (totalRecord % numPerPage != 0) totalPage++;
		int totalBlock = totalPage / pagePerBlock;
		if (totalPage % pagePerBlock != 0) totalBlock++;

		int block = curPage / pagePerBlock;
		if (curPage % pagePerBlock != 0) block++;

		int firstPage = (block - 1) * pagePerBlock + 1;
		int lastPage = block * pagePerBlock;

		int prevPage = 0;
		if (block > 1) {
			prevPage = firstPage - 1;
		}

		int nextPage = 0;
		if (block < totalBlock) {
			nextPage = lastPage + 1;
		}
		if (block >= totalBlock) {
			lastPage = totalPage;
		}

		int listItemNo = totalRecord - (curPage - 1) * numPerPage;

		map.put("totalPage", totalPage);
		map.put("firstPage", firstPage);
		map.put("lastPage", lastPage);
		map.put("prevPage", prevPage);
		map.put("nextPage", nextPage);
		map.put("listItemNo", listItemNo);

		return map;
	}

	public List<Article> getArticleList(String keyword, int startRecord, int endRecord) {
		return dao.getArticleList(keyword, startRecord, endRecord);
	}

	public int getTotalRecord(String keyword) {
		return dao.getTotalRecord(keyword);
	}
	
	public void addArticle(Article article) {
	    dao.insert(article);
	}
	public Article getArticle(int no) {
	    return dao.selectOne(no);
	}
	public void modifyArticle(Article article) {
	    dao.update(article);
	}
	public void deleteArticle(int no) {
	    dao.delete(no);
	}
	public void replyArticle(Article article) {
	    dao.reply(article);
	}	
}