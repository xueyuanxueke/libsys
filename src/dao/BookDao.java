package dao;
import bean.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class BookDao {
	private static final double PUNISH_PER_DAY = 0.2;
	private static final int MAX_LEND_DAYS = 30;
	private static final int MILLIS_PER_DAY = 24 * 60 * 60 * 1000; // 一天的毫秒数
	
	/**
	 * 添加新图书
	 * @param book 图书
	 * @return 添加成功返回true 否则返回false
	 */
	public boolean addNewBook(Book book) {
	    try (Connection connection = DBUtil.getCon()) {
	        return DBUtil.executeUpdate(connection,
	                "insert into tb_book (bookid, isbn, bname, price, author, publisher, pubdate,counter,lended) values (?,?,?,?,?,?,?,0,0)",
	                book.getId(), book.getIsbn(), book.getName(), 
	                book.getPrice(), book.getAuthor(), book.getPublisher(), 
	                book.getPubDate()) == 1;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	/**
	 * 根据编号移除图书(不可借阅)
	 * @param id
	 * @return 移除成功返回true 否则返回false
	 */
	public boolean removeBookById(int id) {
	    try (Connection connection = DBUtil.getCon()) {
	        return DBUtil.executeUpdate(connection, 
	                "update tb_book set lended=1 where bookid=? and lended=0",
	                id) == 1;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	/**
	 * 借出图书
	 * @param bookId 图书编号
	 * @param readerId 读者编号
	 * @return 借出成功返回true 否则返回false
	 */
	public boolean lendOut(int bookId, int readerId) {
	    boolean result = false;
	    Connection connection = DBUtil.getCon();
	    try {
	        PreparedStatement stmt = connection.prepareStatement(
	                "select available from tb_reader where readerid=?");
	        stmt.setInt(1, readerId);
//	        System.out.println(readerId);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next() ) {
//	        	&& rs.getBoolean(1)
	        	System.out.println("------------");
	            DBUtil.beginTx(connection);
	            if (DBUtil.executeUpdate(connection,
	                    "update tb_book set lended=1, counter=counter+1 where lended=0 and bookid=?", 
	                    bookId) == 1) {
//	            	String sql = "select recordid from tb_record";
//	            	PreparedStatement st=connection.prepareStatement(sql);
////	            	System.out.println("=======");
//	            	ResultSet p = st.executeQuery();
//	            	int Max_roleId = 0;	//存放最大ID值
//	            	if (p.next()) {
//	            		Max_roleId = p.getInt("recordid")+1;		//获取结果集中第一个字段的最大ID值+1，给下一个ID
//	            		System.out.println("=======");
//	            	}
//	            	System.out.println(Max_roleId);
	                result = DBUtil.executeUpdate(connection,
	                        "insert into tb_record (bid, rid, lenddate) values (?,?,?)", 
	                        bookId, readerId, new Date(System.currentTimeMillis())) == 1;
	            }
	            DBUtil.commitTx(connection);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        DBUtil.rollbackTx(connection);
	    } finally {
	        DBUtil.close(connection);
	    }
	    return result;
	}

	/**
	 * 归还图书
	 * @param bookId 图书编号
	 * @param readerId 读者编号
	 * @return 如果超期则返回罚款金额 否则返回0 操作失败返回-1
	 */
	public double returnBack(int bookId, int readerId) {
	    double pulishment = -1;
	    Connection connection = DBUtil.getCon();
	    try  {
	        PreparedStatement stmt = connection.prepareStatement(
	                "select recordid, lenddate from tb_record where lenddate=("
	                + "select max(lenddate) from tb_record where bid=? and rid=?) "
	                + "and backdate is null and bid=? and rid=?");
	        stmt.setInt(1, bookId);
	        stmt.setInt(2, readerId);
	        stmt.setInt(3, bookId);
	        stmt.setInt(4, readerId);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            int recordId = rs.getInt("recordid");
	            Date lendDate = rs.getDate("lenddate");
	            Date backDate = new Date();
	            double days = Math.ceil(
	                    (backDate.getTime() - lendDate.getTime()) / (double) MILLIS_PER_DAY);
	            pulishment = days > MAX_LEND_DAYS ? 
	                    Math.round((days - MAX_LEND_DAYS) * PUNISH_PER_DAY * 100) / 100.0 : 0;
	            DBUtil.beginTx(connection);
	            if (DBUtil.executeUpdate(connection, 
	                    "update tb_book set lended=0 where lended=1 and bookid=?", 
	                    bookId) == 1) {
	                DBUtil.executeUpdate(connection, 
	                        "update tb_record set backdate=?, publishment=? where recordid=?", 
	                        backDate, pulishment, recordId);
	            }
	            DBUtil.commitTx(connection);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        DBUtil.rollbackTx(connection);
	    } finally {
	        DBUtil.close(connection);
	    }
	    return pulishment;
	}

	/**
	 * 根据图书编号查找图书
	 * @param id 图书编号
	 * @return 图书对象或null
	 */
	public Book searchBookById(int id) {
	    Book book = null;
	    try (Connection connection = DBUtil.getCon()) {
	        PreparedStatement stmt = connection.prepareStatement(
	                "select bookid, isbn, bname, price, author, publisher, pubdate, lended, counter "
	                + "from tb_book where bookid=?");
	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            book = handleResultSet(rs);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return book;
	}

	/**
	 * 根据ISBN号查找图书
	 * @param isbn
	 * @return 保存查询结果的列表容器
	 */
	public List<Book> searchBookByIsbn(String isbn) {
	    List<Book> bookList = new ArrayList<>();
	    try (Connection connection = DBUtil.getCon()) {
	        PreparedStatement stmt = connection.prepareStatement(
	                "select bookid, isbn, bname, price, author, publisher, pubdate, lended, counter "
	                + "from tb_book where isbn=?");
	        stmt.setString(1, isbn);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Book book = handleResultSet(rs);
	            bookList.add(book);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return bookList.size() > 0 ? bookList : Collections.emptyList();
	}

	/**
	 * 根据书名查找图书
	 * @param name 书名(支持模糊查找)
	 * @return 保存查询结果的列表容器
	 */
	public List<Book> searchBookByName(String name) {
	    List<Book> bookList = new ArrayList<>();
	    try (Connection connection = DBUtil.getCon()) {
	        PreparedStatement stmt = connection.prepareStatement(
	                "select bookid, isbn, bname, price, author, publisher, pubdate, lended, counter "
	                + "from tb_book where bname like ?");
	        stmt.setString(1, "%" + name + "%");
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Book book = handleResultSet(rs);
	            bookList.add(book);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return bookList.size() > 0 ? bookList : Collections.emptyList();
	}

	/**
	 * 根据作者查找图书
	 * @param author  作者
	 * @return 保存查询结果的列表容器
	 */
	public List<Book> searchBookByAuthor(String author) {
	    List<Book> bookList = new ArrayList<>();
	    try (Connection connection = DBUtil.getCon()) {
	        PreparedStatement stmt = connection.prepareStatement(
	                "select bookid, isbn, bname, price, author, publisher, pubdate, lended, counter "
	                + "from tb_book where author like ?");
	        stmt.setString(1, "%" + author + "%");
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Book book = handleResultSet(rs);
	            bookList.add(book);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return bookList.size() > 0 ? bookList : Collections.emptyList();
	}

	/**
	 * 查询借阅排行榜前10名
	 * @return 保存查询结果的列表容器
	 */
	public List<Book> searchTop10Books() {
	    List<Book> bookList = new ArrayList<>();
	    try (Connection connection = DBUtil.getCon()) {
	        PreparedStatement stmt = connection.prepareStatement(
	                "select bookid, isbn, bname, price, author, publisher, pubdate, lended, "
	                + "sum(counter) as counter from tb_book group by isbn "
	                + "order by counter desc limit 10");
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Book book = handleResultSet(rs);
	            bookList.add(book);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return bookList.size() > 0 ? bookList : Collections.emptyList();
	}

	// 处理结果集
	private Book handleResultSet(ResultSet rs) throws SQLException {
	    Book book = new Book();
	    book.setId(rs.getInt("bookid"));
	    book.setIsbn(rs.getString("isbn"));
	    book.setName(rs.getString("bname"));
	    book.setPrice(rs.getDouble("price"));
	    book.setAuthor(rs.getString("author"));
	    book.setPublisher(rs.getString("publisher"));
	    book.setPubDate(rs.getDate("pubdate"));
	    book.setLended(rs.getBoolean("lended"));
	    book.setCounter(rs.getInt("counter"));
	    return book;
	}

}
