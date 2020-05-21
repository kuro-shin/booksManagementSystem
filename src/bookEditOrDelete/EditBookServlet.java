package bookEditOrDelete;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connectDB.ConnectDb;

/**
 * Servlet implementation class EditBookServlet
 */
@WebServlet("/EditBookServlet")
public class EditBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String book_id = request.getParameter("book_id");
		String bookTitle = request.getParameter("bookTitle");
		String bookAuther = request.getParameter("bookAuther");
		String bookPublisher = request.getParameter("bookPublisher");
		String bookGenre = request.getParameter("bookGenre");
		// JDBCドライバの準備
		Map<String, String> conInfo = ConnectDb.loadDB();

		// HttpSession session = request.getSession();
		// String employeeId = (String) session.getAttribute("employeeId");
		//String employeeId = "0002";

		// データベースにアクセスするために、データベースのURLとユーザ名とパスワードを指定

		// DBに接続してSQLを実行
		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(conInfo.get("url"), conInfo.get("user"),
						conInfo.get("pass"));

				// SQLの命令文を実行するための準備をおこないます
				PreparedStatement stmt = createPreparedStatement(con, book_id,bookTitle,bookAuther,bookPublisher,bookGenre);) {
			int resultCount = stmt.executeUpdate();// 1つのSQL文しか実行できない

			System.out.println(resultCount + "件本を編集しました");

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}
	}

	private PreparedStatement createPreparedStatement(Connection con, String book_id,String bookTitle,String bookAuther,String bookPublisher,String bookGenre) throws SQLException {
		// 実行するSQL文
		String sql = "UPDATE BOOKS \n" +
				"SET TITLE =?, AUTHER =?, PUBLISHER=?,GENRE_ID=? " +
				"WHERE BOOK_ID = ?" ;

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, bookTitle);
		stmt.setString(2, bookAuther);
		stmt.setString(3, bookPublisher);
		stmt.setString(4, bookGenre);
		stmt.setString(5, book_id);
		return stmt;






	}

}
