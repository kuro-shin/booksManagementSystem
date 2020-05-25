package newMemberRegistration;

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
import login.Hmac;

/**
 * Servlet implementation class NewMemberRegistrationServlet
 */
@WebServlet("/NewMemberRegistrationServlet")
public class NewMemberRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewMemberRegistrationServlet() {
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
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String registEmployeeId = request.getParameter("registEmployeeId");
		String registEmployeeName = request.getParameter("registEmployeeName");
		String registEmployeeNameKana = request.getParameter("registEmployeeNameKana");
		String registEmployeeEmail = request.getParameter("registEmployeeEmail");
		String registLoginPassword = Hmac.getHmac(request.getParameter("registLoginPassword"));
		String registLoginRole = request.getParameter("registLoginRole");
		String createDay = sdf.format(cal.getTime());
		System.out.println("registLoginRole:"+registLoginRole);
		if (registLoginRole == "" || registLoginRole == null) {
			registLoginRole = "user";
		}
		// 実行するSQL文
		String sql = "insert into EMPLOYEES  \n"
				+ "(EMPLOYEE_ID, NAME, NAME_KANA, MAIL,PASSWORD,EMPLOYEE_ROLE,CREATED_DATE) \n" + "values \n" +
				"('"+registEmployeeId+"','"+registEmployeeName+"','"+registEmployeeNameKana+"','"+registEmployeeEmail+"','"+registLoginPassword+"','"+registLoginRole+"','"+createDay+"')";
		// エラーが発生するかもしれない処理はtry-catchで囲みます
		// この場合はDBサーバへの接続に失敗する可能性があります
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
