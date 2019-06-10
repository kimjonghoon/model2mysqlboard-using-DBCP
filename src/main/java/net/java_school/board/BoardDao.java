package net.java_school.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import net.java_school.db.dbpool.MySQLConnectionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoardDao {
	Logger log = LoggerFactory.getLogger(BoardDao.class);
	
	private MySQLConnectionManager dbmgr;

	public BoardDao() {}

	public BoardDao(MySQLConnectionManager dbmgr) {
		this.dbmgr = dbmgr;
	}

	private Connection getConnection() throws SQLException {
		return dbmgr.getConnection();
	}

	private void close(ResultSet rs, PreparedStatement stmt, Connection con) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Article> getArticleList(String keyword, int offSet, int rowCount) {
		List<Article> list = new ArrayList<Article>();
		String sql = null;

		sql = "SELECT articleno,family,parent,depth,indent,title,regdate FROM thread_article ";
		if (keyword != null && !keyword.equals("")) {
			sql += " WHERE title LIKE '%" + keyword + "%' OR content LIKE '%" + keyword + "%' ";
		}	
		sql += "ORDER BY family DESC,depth ASC " + 
				"LIMIT ?,?";

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, offSet);
			stmt.setInt(2, rowCount);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Article article = new Article();
				article.setArticleNo(rs.getInt("articleno"));
				article.setFamily(rs.getInt("family"));
				article.setParent(rs.getInt("parent"));
				article.setDepth(rs.getInt("depth"));
				article.setIndent(rs.getInt("indent"));
				article.setTitle(rs.getString("title"));
				article.setRegdate(rs.getDate("regdate"));
				list.add(article);
			}
		} catch (SQLException e) {
			log.debug("Error Source : BoardDao.getArticleList() : SQLException");
			log.debug("SQLState : {}", e.getSQLState());
			log.debug("Message : {}", e.getMessage());
			log.debug("Oracle Error Code : {}", e.getErrorCode());
			log.debug("sql : {}", sql);
		} finally {
			close(rs, stmt, con);
		}

		return list;
	}

	public int getTotalRecord(String keyword) {
		int totalRecord = 0;
		String sql = null;

		if (keyword == null || keyword.equals("")) {
			sql = "SELECT count(*) FROM thread_article";
		} else {
			sql = "SELECT count(*) FROM thread_article " +
					"WHERE title LIKE ? OR content LIKE ?";
		}

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			if (keyword != null && !keyword.equals("")) {
				keyword = "%" + keyword + "%";
				stmt.setString(1, keyword);
				stmt.setString(2, keyword);
			}
			rs = stmt.executeQuery();
			rs.next();
			totalRecord = rs.getInt(1);
		} catch (SQLException e) {
			log.debug("Error Source : BoardDao.getTotalRecord() : SQLException");
			log.debug("SQLState : {}", e.getSQLState());
			log.debug("Message : {}", e.getMessage());
			log.debug("Oracle Error Code : {}", e.getErrorCode());
			log.debug("sql : {}", sql);
		} finally {
			close(rs, stmt, con);
		}

		return totalRecord;
	}

	public void insert(Article article) {
		String insert = "INSERT INTO thread_article (title, content, regdate, parent, depth, indent) "
				+ "VALUES (?, ?, now(), 0, 0, 0)";
		String update = "UPDATE thread_article SET family = articleno WHERE articleno = LAST_INSERT_ID()";
		
		Connection con = null;
		
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;

		try {
			con = getConnection();
			con.setAutoCommit(false);
			
			stmt1 = con.prepareStatement(insert);
			stmt1.setString(1, article.getTitle());
			stmt1.setString(2, article.getContent());
			stmt1.executeUpdate();
			
			stmt2 = con.prepareStatement(update);
			stmt2.executeUpdate();
			
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.debug("Error Source : BoardDao.insert() : SQLException");
			log.debug("SQLState : {}", e.getSQLState());
			log.debug("Message : {}", e.getMessage());
			log.debug("Oracle Error Code : {}", e.getErrorCode());
			log.debug("insert : {}", insert);
			log.debug("update : {}", update);
		} finally {
			if (stmt1 != null) {
				try {
					stmt1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt2 != null) {
				try {
					stmt2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Article selectOne(int no) {
		Article article = null;
		String sql = "SELECT articleno,family,parent,depth,indent,title,content,regdate FROM thread_article WHERE articleno = ?";

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, no);
			rs = stmt.executeQuery();
			while (rs.next()) {
				article = new Article();
				article.setArticleNo(rs.getInt("articleno"));
				article.setFamily(rs.getInt("family"));
				article.setParent(rs.getInt("parent"));
				article.setDepth(rs.getInt("depth"));
				article.setIndent(rs.getInt("indent"));				
				article.setTitle(rs.getString("title"));
				article.setContent(rs.getString("content"));
				article.setRegdate(rs.getDate("regdate"));
			}
		} catch (SQLException e) {
			log.debug("Error Source : BoardDao.selectOne() : SQLException");
			log.debug("SQLState : {}", e.getSQLState());
			log.debug("Message : {}", e.getMessage());
			log.debug("Oracle Error Code : {}", e.getErrorCode());
			log.debug("sql : {}", sql);
		} finally {
			close(rs, stmt, con);
		}

		return article;
	}

	public void update(Article article) {
		String sql = "UPDATE thread_article SET title = ?, content = ? WHERE articleno = ?";        
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, article.getTitle());
			stmt.setString(2, article.getContent());
			stmt.setInt(3, article.getArticleNo());
			stmt.executeUpdate();
		} catch (SQLException e) {
			log.debug("Error Source : BoardDao.update() : SQLException");
			log.debug("SQLState : {}", e.getSQLState());
			log.debug("Message : {}", e.getMessage());
			log.debug("Oracle Error Code : {}", e.getErrorCode());
			log.debug("sql : {}", sql);
		} finally {
			close(null, stmt, con);
		}
	}

	public void delete(int no) {
		String sql1 = "SELECT count(*) FROM thread_article WHERE parent = ?";
		String sql2 = "DELETE FROM thread_article WHERE articleno = ?";

		Connection con = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;

		boolean check = false;// if true, delete

		try {
			con = getConnection();
			stmt1 = con.prepareStatement(sql1);
			stmt1.setInt(1, no);
			rs = stmt1.executeQuery();
			rs.next();
			int num = rs.getInt(1);
			if (num == 0) {
				check = true;
			}
			if (check == true) {
				stmt2 = con.prepareStatement(sql2);
				stmt2.setInt(1, no);
				stmt2.executeUpdate();
			}
		} catch (SQLException e) {
			log.debug("Error Source : BoardDao.delete() : SQLException");
			log.debug("SQLState : {}", e.getSQLState());
			log.debug("Message : {}", e.getMessage());
			log.debug("Oracle Error Code : {}", e.getErrorCode());
			log.debug("sql : {}", sql2);
		} finally {
			if (stmt2 != null) {
				try {
					stmt2.close();
				} catch (SQLException e) {}
			}
			close(rs, stmt1, con);
		}
	}

	public void reply(Article article) {
	    String sql1 = "UPDATE thread_article SET depth = depth + 1 " + 
	            "WHERE family = ? AND depth > ? ";
	    
		String sql2 = "INSERT INTO thread_article " + 
				"(family, parent, depth, indent, title, content, regdate) " + 
				"VALUES (?, ?, ?, ?, ?, ?, now())";

		Connection con = null;
		
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		
		try {
			con = getConnection();
			con.setAutoCommit(false);
			
			stmt1 = con.prepareStatement(sql1);
			stmt1.setInt(1, article.getFamily());
			stmt1.setInt(2, article.getDepth());
			stmt1.executeUpdate();
			
			stmt2 = con.prepareStatement(sql2);
			stmt2.setInt(1, article.getFamily());
			stmt2.setInt(2, article.getParent());
			stmt2.setInt(3, article.getDepth() + 1);
			stmt2.setInt(4, article.getIndent() + 1);
			stmt2.setString(5, article.getTitle());
			stmt2.setString(6, article.getContent());
			stmt2.executeUpdate();
			
			con.commit();
		} catch (SQLException e) {
		    try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
			log.debug("Error Source:BoardDao.reply() : SQLException");
			log.debug("SQLState : {}", e.getSQLState());
			log.debug("Message : {}", e.getMessage());
			log.debug("Oracle Error Code : {}", e.getErrorCode());
			log.debug("sql1 : {}", sql1);
			log.debug("sql2 : {}", sql2);
		} finally {
			if (stmt1 != null) {
				try {
					stmt1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt2 != null) {
				try {
					stmt2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}	
}