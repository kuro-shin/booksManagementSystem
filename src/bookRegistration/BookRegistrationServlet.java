package bookRegistration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import connectDB.ConnectDb;

/**
 * Servlet implementation class BookRegistrationServlet
 */
@WebServlet("/BookRegistrationServlet")
public class BookRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookRegistrationServlet() {
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
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession(true);
		String employeeName = (String) session.getAttribute("employeeName");
		//一覧表示、追加、編集、削除のどのリクエストかを判断
		String bookTitle = request.getParameter("bookTitle");
		String bookAuther = request.getParameter("bookAuther");
		String bookPublisher = request.getParameter("bookPublisher");
		String bookGenre = request.getParameter("bookGenre");
		String bookId = GenreRegistration.Registration(bookGenre);
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String createDay = sdf.format(cal.getTime());
		//String jsRequest = "depttable";
		String sql = "";
		String excute_message = "";

			sql = "insert into BOOKS \n" +
					"(BOOK_ID, TITLE, PUBLISHER, AUTHER, GENRE_ID, PURCHASE_DATE, PURCHASER, IS_BORROWING) \n" +
					"select \n" +
					"'BK'||lpad(MAX(SUBSTR(BOOK_ID,3))+1,6,0), \n" +
					"'"+bookTitle+"', \n" +
					"'"+bookPublisher+"', \n" +
					"'"+bookAuther+"', \n" +
					"'"+bookId+"', \n" +
					"'"+createDay+"', \n" +
					"'"+employeeName+"', \n" +
					"'0' \n" +
					"from BOOKS \n";

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



			// アクセスした人に応答するためのJSONを用意する
			PrintWriter pw = response.getWriter();

			// JSONで出力する
			pw.append(new ObjectMapper().writeValueAsString(excute_message));

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}
	}
}


