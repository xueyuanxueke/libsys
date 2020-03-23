package bean;
import java.util.*;
public class Reader {
	private int id;//读者编号
	private String name;//读者姓名
	private boolean gender;//性别
	private String tel;//电话
	private Date registerDate;//注册日期
	private boolean available;//读者是否有效
	
	public int getId() {
	    return id;
	}

	public void setId(int id) {
	    this.id = id;
	}

	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	public boolean isGender() {
	    return gender;
	}

	public void setGender(boolean gender) {
	    this.gender = gender;
	}

	public String getTel() {
	    return tel;
	}

	public void setTel(String tel) {
	    this.tel = tel;
	}

	public Date getRegisterDate() {
	    return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
	    this.registerDate = registerDate;
	}

	public boolean isAvailable() {
	    return available;
	}

	public void setAvailable(boolean available) {
	    this.available = available;
	}
}