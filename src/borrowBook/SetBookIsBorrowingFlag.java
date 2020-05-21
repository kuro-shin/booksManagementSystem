package borrowBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Map;

import connectDB.ConnectDb;

public class SetBookIsBorrowingFlag {

	public static void SetBorrowingFlag(String bookId){
	    String excute_message="";
		//String jsRequest = "depttable";
		String sql ="update BOOKS \n" +
						"set IS_BORROWING = '1' \n" +
						"where BOOK_ID = '"+bookId+"'\n";
		//DBのURL,ID,PASSを取得
		Map<String, String> conInfo = ConnectDb.loadDB();


		try (
			// データベースへ接続します
			Connection con = DriverManager.getConnection(conInfo.get("url"), conInfo.get("user"), conInfo.get("pass"));
			// SQLの命令文を実行するための準備をおこないます
			Statement stmt = con.createStatement();
			// SQLの命令文を実行し、その結果をResultSet型のrsに代入します

		) {

			@SuppressWarnings("unused")
			int rs1 = stmt.executeUpdate(sql);

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}
	}

}
