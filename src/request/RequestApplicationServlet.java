package request;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import connectDB.ConnectDb;

/**
 * Servlet implementation class RequestApplicationServlet
 */
@WebServlet("/RequestApplicationServlet")
public class RequestApplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestApplicationServlet() {
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
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String requestEmployeeId = request.getParameter("requestEmployeeId");
		String requestTitle = request.getParameter("requestTitle");
		String requestPublisher = request.getParameter("requestPublisher");
		String requestAuthor = request.getParameter("requestAuthor");
		String requestUrl = request.getParameter("requestUrl");
		String requestDate = sdf.format(cal.getTime());
		// 実行するSQL文
		String sql = "insert into REQUEST_BOOKS   \n" +
				"(REQUEST_BOOK_ID, EMPLOYEE_ID, TITLE, PUBLISHER,AUTHER,URL,STATUS,REQUEST_DATE)  \n" +
				"select  \n" +
				"'RQ'||lpad(MAX(SUBSTR(REQUEST_BOOK_ID,3))+1,6,0), '"+requestEmployeeId+"','"+requestTitle+"','"+requestPublisher+"','"+requestAuthor+"','"+requestUrl+"','0','"+requestDate+"'  \n" +
				"from \n" +
				"REQUEST_BOOKS " ;
				// エラーが発生するかもしれない処理はtry-catchで囲みます
		System.out.println(sql);
		Map<String, String> conInfo = ConnectDb.loadDB();
		// DBへ接続してSQLを実行
		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(conInfo.get("url"), conInfo.get("user"),
						conInfo.get("pass"));
				// SQLの命令文を実行するための準備をおこないます
				Statement stmt = con.createStatement();) {
			// SQLの命令文を実行し、その件数をint型のresultCountに代入します
			int resultCount = stmt.executeUpdate(sql);
		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()), e);
		}
		// アクセスした人に応答するためのJSONを用意する
		PrintWriter pw = response.getWriter();
		// JSONで出力する
		pw.append(new ObjectMapper().writeValueAsString("ok"));

	}

}
