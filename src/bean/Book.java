package bean;
import java.util.*;
public class Book {
	private int id;//图书编号
	private String isbn;//图书国际编号
	private String name;//书名
	private double price;//价格
	private String author;//作者
	private String publisher;//出版社
	private Date pubDate;//出版日期
	private boolean lended;//是否借出
	private int counter;//借书次数
	
	public int getId() {
	    return id;
	}

	public void setId(int id) {
	    this.id = id;
	}

	public String getIsbn() {
	    return isbn;
	}

	public void setIsbn(String isbn) {
	    this.isbn = isbn;
	}

	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	public double getPrice() {
	    return price;
	}

	public void setPrice(double price) {
	    this.price = price;
	}

	public String getAuthor() {
	    return author;
	}

	public void setAuthor(String author) {
	    this.author = author;
	}

	public String getPublisher() {
	    return publisher;
	}

	public void setPublisher(String publisher) {
	    this.publisher = publisher;
	}

	public Date getPubDate() {
	    return pubDate;
	}

	public void setPubDate(Date pubDate) {
	    this.pubDate = pubDate;
	}

	public boolean isLended() {
	    return lended;
	}

	public void setLended(boolean lended) {
	    this.lended = lended;
	}

	public int getCounter() {
	    return counter;
	}

	public void setCounter(int counter) {
	    this.counter = counter;
	}
}