package main;
import java.sql.*;
import java.util.*;
import java.util.Date;

import bean.Book;
import bean.Reader;
import dao.*;
import common.*;
public class Main {
	private static BookDao bookManager = new BookDao();
	private static ReaderDao readerManager= new ReaderDao();
	
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
	    boolean isRunning = true;    
	    while (isRunning) {
	        int choice;
	        System.out.println("===============================欢迎使用图书管理系统=====================================");
	        System.out.println("=\t\t1.管理专用系统\t\t2.读者专用系统\t\t3.退出系统\t\t=");
	        System.out.println("==================================================================================");
	        do {
	            System.out.print("请选择: ");
	            choice = scanner.nextInt();
	        } while (choice < 1 || choice > 3);
	        switch (choice) {
		        case 1:
		            showReaderSubMenu();
		            break;
		        case 2:
		            showBookSubMenu();
		            break;
		        case 3:
		            System.out.println("感谢使用本系统, 欢迎下次再来");
		            isRunning = false;
		            break;
	        }
	    }
	    scanner.close();
	}

	private static void showReaderSubMenu() {
	    boolean flag = true;
	    int choice;
	    while (flag) {
		    System.out.println("====================================读者专用子系统====================================");
	        System.out.println("=\t\t1.新增读者\t\t2.注销读者\t\t3.修改信息\t\t\t\t=");
	        System.out.println("=\t\t4.新增图书\t\t5.下架图书\t\t6.返回主菜单\t\t\t=");
	        System.out.println("==================================================================================");
	        do {
	            System.out.print("请选择: ");
	            choice = scanner.nextInt();
	        } while (choice < 1 || choice > 6);
	        switch (choice) {
		        case 1:
		            addReader();
		            break;
		        case 2:
		            cancelReader();
		            break;
		        case 3:
		            editReader();
		            break;
		        case 4:
		            addBook();
		            break;
		        case 5:
		            removeBook();
		            break;
		        case 6:
		            flag = false;
		            break;
	        }
	    }
	}

	private static void addReader() {
	    System.out.print("编号: ");
	    int id = scanner.nextInt();
	    System.out.print("姓名: ");
	    String name = scanner.next();
	    System.out.print("性别(1. 男, 0. 女): ");
	    int gender = scanner.nextInt();
	    System.out.print("电话: ");
	    String tel = scanner.next();
	    Reader reader = new Reader();
	    reader.setId(id);
	    reader.setName(name);
	    reader.setGender(gender == 1);
	    reader.setTel(tel);
	    if (readerManager.createNewReader(reader)) {
	        System.out.println("添加读者成功!");
	    } else {
	        System.out.println("操作失败!!!");
	    }
	}

	private static void cancelReader() {
	    System.out.print("编号: ");
	    int id = scanner.nextInt();
	    if (readerManager.cancelReaderById(id)) {
	        System.out.println("注销成功!");
	    } else {
	        System.out.println("注销失败!");
	    }
	}

	private static void editReader() {
	    System.out.print("编号: ");
	    int id = scanner.nextInt();
	    System.out.print("电话: ");
	    String tel = scanner.next();
	    Reader reader = new Reader();
	    reader.setId(id);
	    reader.setTel(tel);
	    if (readerManager.editReaderInfo(reader)) {
	        System.out.println("更新读者电话成功!");
	    } else {
	        System.out.println("更新读者电话失败!");
	    }
	}

	private static void showBookSubMenu() {
//	    System.out.println("====================================管理专用子系统====================================");
	    boolean flag = true;
	    int choice;
	    while (flag) {
		    System.out.println("====================================读者专用子系统====================================");
	        System.out.println("=\t\t1.借出图书\t\t2.归还图书\t\t3.编号查找\t\t\t\t=");
	        System.out.println("=\t\t4.书名查找\t\t5.Top10\t\t6.返回主菜单\t\t\t=");
	        System.out.println("==================================================================================");
	        do {
	            System.out.print("请选择: ");
	            choice = scanner.nextInt();
	        } while (choice < 1 || choice > 6);
	        switch (choice) {        
		        case 1:
		            lendBook();
		            break;
		        case 2:
		            returnBook();
		            break;
		        case 3:
		            getBookById();
		            break;
		        case 4:
		            listBooksByIsbn();
		            break;
		        case 5:
		            listTop10Books();
		            break;
		        case 6:
		            flag = false;
		            break;
	        }
	    }
	}

	private static void addBook() {
	    System.out.print("编号: ");
	    int id = scanner.nextInt();
	    System.out.print("ISBN: ");
	    String isbn = scanner.next();
	    System.out.print("书名: ");
	    String name = scanner.next();
	    System.out.print("价格: ");
	    double price = scanner.nextDouble();
	    System.out.print("作者: ");
	    String author = scanner.next();
	    System.out.print("出版社: ");
	    String publisher = scanner.next();
	    System.out.print("出版日期: ");
	    Date pubDate = CommonUtil.stringToDate(scanner.next());
	    Book book = new Book();
	    book.setId(id);
	    book.setIsbn(isbn);
	    book.setName(name);
	    book.setPrice(price);
	    book.setAuthor(author);
	    book.setPublisher(publisher);
	    book.setPubDate(pubDate);
	    if (bookManager.addNewBook(book)) {
	        System.out.println("新增图书成功!");
	    } else {
	        System.out.println("新增图书失败!");
	    }
	}

	private static void removeBook() {
	    System.out.print("编号: ");
	    int id = scanner.nextInt();
	    if (bookManager.removeBookById(id)) {
	        System.out.println("下架成功!");
	    } else {
	        System.out.println("下架失败!");
	    }
	}

	private static void lendBook() {
	    System.out.print("图书编号: ");
	    int bookId = scanner.nextInt();
	    System.out.print("读者编号: ");
	    int readerId = scanner.nextInt();
	    if (bookManager.lendOut(bookId, readerId)) {
	        System.out.println("借书成功!!!");
	    } else {
	        System.out.println("借书失败!!!");
	    }
	}

	private static void returnBook() {
	    System.out.print("图书编号: ");
	    int bookId = scanner.nextInt();
	    System.out.print("读者编号: ");
	    int readerId = scanner.nextInt();
	    double pulishment = bookManager.returnBack(bookId, readerId);
	    if (pulishment >= 0) {
	        System.out.println("还书成功!!!");
	        if (pulishment > 0) {
	            System.out.printf("请缴纳罚款: %.1f元\n", pulishment);
	        }
	    } else {
	        System.out.println("还书失败!!!");
	    }
	}

	private static void getBookById() {
	    System.out.print("编号: ");
	    int id = scanner.nextInt();
	    Book book = bookManager.searchBookById(id);
	    displayTitle();
	    displayBook(book);
	}

	private static void listBooksByIsbn() {
	    System.out.print("书名: ");
	    String name = scanner.next();
	    List<Book> list = bookManager.searchBookByName(name);
	    displayBookList(list);
	}

	private static void listTop10Books() {
	    List<Book> list = bookManager.searchTop10Books();
	    displayBookList(list);
	}

	private static void displayBookList(List<Book> list) {
	    displayTitle();
	    for (Book book : list) {
	        displayBook(book);
	    }
	}

	private static void displayTitle() {
		System.out.println("***********************************");
	    System.out.printf("\t%s\t%s\t%s\t\n", "书名", "作者", "借阅量");
	}

	private static void displayBook(Book book) {
	    System.out.printf("\t%s\t%s\t%d\t\n", book.getName(),
	            book.getAuthor(), book.getCounter());
	    System.out.println("***********************************");
	}

}
