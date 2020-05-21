package borrowingBooksDisplay;

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
 * Servlet implementation class ReturnBorrowingBookServlet
 */
@WebServlet("/ReturnBorrowingBookServlet")
public class ReturnBorrowingBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReturnBorrowingBookServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String q = request.getParameter("q");

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
				PreparedStatement stmt = createPreparedStatement(con, q);) {
			int resultCount = stmt.executeUpdate();// 1つのSQL文しか実行できない

			System.out.println(resultCount + "件本を返却しました");

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}
	}

	private PreparedStatement createPreparedStatement(Connection con, String q) throws SQLException {
		// 実行するSQL文
		String sql = "update BORROWING_BOOKS  \n" +
				"set IS_RETURNED = '1' \n" +
				"where BORROWING_BOOK_ID=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, q);
		return stmt;
	}

}
