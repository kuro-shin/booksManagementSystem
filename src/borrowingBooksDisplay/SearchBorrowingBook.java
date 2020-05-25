package borrowingBooksDisplay;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import connectDB.ConnectDb;

public class SearchBorrowingBook {
	public static String SearchBorrowingBook(String borrowingBookId) {
		String genreId = null;
		String sql = "select BOOK_ID \n" + "from BORROWING \n" + "where GENRE_NAME = '" + borrowingBookId + "'";

		// DBのURL,ID,PASSを取得
		Map<String, String> conInfo = ConnectDb.loadDB();

		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(conInfo.get("url"), conInfo.get("user"),
						conInfo.get("pass"));
				// SQLの命令文を実行するための準備をおこないます
				Statement stmt = con.createStatement();
				// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
				ResultSet rs1 = stmt.executeQuery(sql);) {
			// SQL実行結果を保持している変数rsから情報を取得
			if (rs1.next()) {
				genreId = rs1.getString("GENRE_ID");
			}
			System.out.println(genreId);
			return genreId;

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}

	}
}
