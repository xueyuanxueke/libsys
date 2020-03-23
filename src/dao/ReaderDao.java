package dao;
import java.sql.*;
import java.util.*;
import java.util.Date;

import bean.*;

public class ReaderDao {
	/**
	* 新增一个读者
	* @param reader 读者
	* @return 新增成功返回true 否则返回false
	*/
	public boolean createNewReader(Reader reader) {
		try (Connection connection = DBUtil.getCon()) {
			return DBUtil.executeUpdate(connection,
			"insert into tb_reader (readerid, rname, gender, tel, regdate) values (?,?,?,?,?)",
			reader.getId(), reader.getName(), reader.isGender(), reader.getTel(),
			new Date()) == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 注销一个读者
	 * @param id 读者编号
	 * @return 注销成功返回true 否则返回false
	 */
	public boolean cancelReaderById(int id) {
	    try (Connection connection = DBUtil.getCon()) {
	        return DBUtil.executeUpdate(connection, 
	                "update tb_reader set available=0 where readerid=?", id) == 1;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	/**
	 * 编辑读者电话
	 * @param reader 读者
	 * @return 编辑成功返回true 否则返回false
	 */
	public boolean editReaderInfo(Reader reader) {
	    try (Connection connection = DBUtil.getCon()) {
	        return DBUtil.executeUpdate(connection, 
	                "update tb_reader set tel=? where readerid=?", 
	                reader.getTel(), reader.getId()) == 1;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

}
