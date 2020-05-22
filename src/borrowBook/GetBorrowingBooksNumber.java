package borrowBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import connectDB.ConnectDb;

public class GetBorrowingBooksNumber {
	public static String get(String employeeId){

		String borrowingNum=null;
		Map<String, String> conInfo = ConnectDb.loadDB();
		String sql = "select count(*) NUM \n" +
				"from BORROWING_BOOKS \n" +
				"where EMPLOYEE_ID = '"+employeeId+"' \n"
						+ "and IS_RETURNED = '0' \n";

		try (
			// データベースへ接続します
			Connection con = DriverManager.getConnection(conInfo.get("url"), conInfo.get("user"), conInfo.get("pass"));
			// SQLの命令文を実行するための準備をおこないます
			Statement stmt = con.createStatement();
			// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
			ResultSet rs1 = stmt.executeQuery(sql);
		) {
			// SQL実行結果を保持している変数rsから情報を取得

			if (rs1.next()) {
				borrowingNum = rs1.getString("NUM");
			}

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}
		return borrowingNum;
	}
}
