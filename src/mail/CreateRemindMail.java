package mail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import connectDB.ConnectDb;

public class CreateRemindMail {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		remindList();
	}

	public static void remindList(){
		Map<String, String> conInfo = ConnectDb.loadDB();
		String sql = "select BOOKS.TITLE, EMPLOYEES.EMPLOYEE_ID, EMPLOYEES.NAME, RETURN_DUE_DATE, EMPLOYEES.MAIL \n" +
				"from BORROWING_BOOKS, \n" +
				"EMPLOYEES, \n" +
				"BOOKS \n" +
				"where RETURN_DUE_DATE < TO_CHAR(SYSTIMESTAMP, 'YYYYMMDD') \n" +
				"and IS_RETURNED = '0' \n" +
				"and BORROWING_BOOKS.EMPLOYEE_ID = EMPLOYEES.EMPLOYEE_ID \n" +
				"and BOOKS.BOOK_ID = BORROWING_BOOKS.BOOK_ID \n" +
				"order by EMPLOYEES.EMPLOYEE_ID \n";

		try (
			// データベースへ接続します
			Connection con = DriverManager.getConnection(conInfo.get("url"), conInfo.get("user"), conInfo.get("pass"));
			// SQLの命令文を実行するための準備をおこないます
			Statement stmt = con.createStatement();
			// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
			ResultSet rs1 = stmt.executeQuery(sql);
		) {
			String empCheck = "first";
			String subject = "【重要】未返却の書籍があります";
			//String content =  null;
			String bookList = "";
			String mailTo = "";
			String empName ="";
			String bookTitle = "";
			String returnDueDate = "";
			String empId="";
			int i = 1;

			while (rs1.next()) {

				empName = rs1.getString("NAME");
				bookTitle = rs1.getString("TITLE");
				returnDueDate = rs1.getString("RETURN_DUE_DATE");

		        // Date型変換

//				System.out.println(empName);
//				System.out.println(bookTitle);
//				System.out.println(returnDueDate);
				//同じ人か確認
				if(empCheck.equals("first")||empName.equals(empCheck)){ //前の人と同じか
					empId = rs1.getString("EMPLOYEE_ID");
					System.out.println("初回もしくは前の人と同じです");
					empCheck = empName; //初回ならempNameをempCheckに入れる。
					bookList += i +".\n"
							+ "【書籍名】"+bookTitle+"\n"
							+ "【返却期限】"+returnDueDate+"\n\n";

					i += 1;
					System.out.println(bookList);
				}else if(empName!=empCheck){
					//
					System.out.println("前の人とは違います");

					String content = "社員ID："+empId+"\n"
							+ "氏名："+empCheck+"さん\n\n下記の書籍が未返却です。\nすみやかに返却してください。\n\n";
					bookList = content + bookList;
					SendMail sendMail = new SendMail();
					sendMail.createMail(mailTo, subject, bookList);
					System.out.println(bookList);

					i=1;
					mailTo="";
					bookList = "";

					//新しいユーザーの
					empCheck = empName; //初回ならempNameをempCheckに入れる。
					bookList += i +".\n"
							+ "【書籍名】"+bookTitle+"\n"
							+ "【返却期限】"+returnDueDate+"\n\n";
				}
				empCheck = empName;
				mailTo = rs1.getString("MAIL");
				empId = rs1.getString("EMPLOYEE_ID");
			}
			try{
				String content = "社員ID："+empId+"\n"
						+ "氏名："+empName+"さん\n\n下記の書籍が未返却です。\nすみやかに返却してください。\n\n";
				bookList = content + bookList;
				SendMail sendMail = new SendMail();
				sendMail.createMail(mailTo, subject, bookList);
			}catch(Exception e){
				throw new RuntimeException(String.format("メール送信者はいません。詳細：[%s]", e.getMessage()), e);
			}

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}
	}

}
