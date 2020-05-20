package request;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import connectDB.ConnectDb;

/**
 * Servlet implementation class RequestDisplayServlet
 */
@WebServlet("/RequestDisplayServlet")
public class RequestDisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RequestDisplayServlet() {
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
		String requestStatus = request.getParameter("requestStatus");
		String requestEmployeeId = request.getParameter("requestEmployeeId");

		String sql;
		// 実行するSQL文
		if (requestStatus == null) {
			if (requestEmployeeId == null) {
				sql = "select \n" + "RE.REQUEST_BOOK_ID, \n" + "RE.EMPLOYEE_ID, \n" + "EM.NAME, \n" + "RE.TITLE, \n"
						+ "RE.PUBLISHER, \n" + "RE.AUTHER, \n" + "RE.URL, \n" + "RE.STATUS, \n" + "RE.REJECTED_REASON, \n"
						+ "RE.REQUEST_DATE, \n" + "RE.UPDATED_DATE, \n" + "EMP.NAME as UPDATE_NAME \n" + "from \n" + "REQUEST_BOOKS RE, \n"
						+ "EMPLOYEES EM, \n" + "EMPLOYEES EMP \n" + "where 1=1 \n" + "and RE.EMPLOYEE_ID=EM.EMPLOYEE_ID \n"
						+ "and RE.UPDATER_ID=EMP.EMPLOYEE_ID(+) \n";
			}else{
				sql = "select \n" + "RE.REQUEST_BOOK_ID, \n" + "RE.EMPLOYEE_ID, \n" + "EM.NAME, \n" + "RE.TITLE, \n"
						+ "RE.PUBLISHER, \n" + "RE.AUTHER, \n" + "RE.URL, \n" + "RE.STATUS, \n" + "RE.REJECTED_REASON, \n"
						+ "RE.REQUEST_DATE, \n" + "RE.UPDATED_DATE, \n" + "EMP.NAME as UPDATE_NAME \n" + "from \n" + "REQUEST_BOOKS RE, \n"
						+ "EMPLOYEES EM, \n" + "EMPLOYEES EMP \n" + "where 1=1 \n" + "and RE.EMPLOYEE_ID=EM.EMPLOYEE_ID \n"
						+ "and RE.UPDATER_ID=EMP.EMPLOYEE_ID(+) \n" + "and EM.EMPLOYEE_ID = '"+requestEmployeeId+"'";
			}

		} else {
			if (requestEmployeeId == null) {
				sql = "select \n" + "RE.REQUEST_BOOK_ID, \n" + "RE.EMPLOYEE_ID, \n" + "EM.NAME, \n" + "RE.TITLE, \n"
						+ "RE.PUBLISHER, \n" + "RE.AUTHER, \n" + "RE.URL, \n" + "RE.STATUS, \n" + "RE.REJECTED_REASON, \n"
						+ "RE.REQUEST_DATE, \n" + "RE.UPDATED_DATE, \n" + "EMP.NAME as UPDATE_NAME \n" + "from \n" + "REQUEST_BOOKS RE, \n"
						+ "EMPLOYEES EM, \n" + "EMPLOYEES EMP \n" + "where 1=1 \n" + "and RE.EMPLOYEE_ID=EM.EMPLOYEE_ID \n"
						+ "and RE.UPDATER_ID=EMP.EMPLOYEE_ID(+) \n"
						+ "and RE.STATUS = '"+requestStatus+"' ";
			} else {
				sql = "select \n" + "RE.REQUEST_BOOK_ID, \n" + "RE.EMPLOYEE_ID, \n" + "EM.NAME, \n" + "RE.TITLE, \n"
						+ "RE.PUBLISHER, \n" + "RE.AUTHER, \n" + "RE.URL, \n" + "RE.STATUS, \n" + "RE.REJECTED_REASON, \n"
						+ "RE.REQUEST_DATE, \n" + "RE.UPDATED_DATE, \n" + "EMP.NAME as UPDATE_NAME \n" + "from \n" + "REQUEST_BOOKS RE, \n"
						+ "EMPLOYEES EM, \n" + "EMPLOYEES EMP \n" + "where 1=1 \n" + "and RE.EMPLOYEE_ID=EM.EMPLOYEE_ID \n"
						+ "and RE.UPDATER_ID=EMP.EMPLOYEE_ID(+) \n" + "and EM.EMPLOYEE_ID = '"+requestEmployeeId+"' \n"
						+ "and RE.STATUS = '"+requestStatus+"' ";
			}
		}

		// 趣味リスト（Hobby型のリスト）
		List<Request> requestList = new ArrayList<>();

		Map<String, String> conInfo = ConnectDb.loadDB();
		// DBへ接続してSQLを実行
		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(conInfo.get("url"), conInfo.get("user"),
						conInfo.get("pass"));
				// SQLの命令文を実行するための準備をおこないます
				Statement stmt = con.createStatement();

				// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
				ResultSet rs1 = stmt.executeQuery(sql);) {
			// SQL実行後の処理内容

			// SQL実行結果を商品リストに追加していく。
			while (rs1.next()) {
				// 一つ分の成績情報を入れるためReSScordインスタンスを生成
				Request Request = new Request();
				// SQLの取得結果をインスタンスに代入
				Request.setRequestId(rs1.getString("REQUEST_BOOK_ID"));
				Request.setRequestEmployeeId(rs1.getString("EMPLOYEE_ID"));
				Request.setRequestTitle(rs1.getString("TITLE"));
				Request.setRequestPublisher(rs1.getString("PUBLISHER"));
				Request.setRequestAuthor(rs1.getString("AUTHER"));
				Request.setRequestApplicantName(rs1.getString("NAME"));
				Request.setRequestApplicantDate(rs1.getString("REQUEST_DATE"));
				Request.setRequestUrl(rs1.getString("URL"));
				Request.setRequestStatus(rs1.getString("STATUS"));
				Request.setRequestRejectReason(rs1.getString("REJECTED_REASON"));
				Request.setRequestUpdaterName(rs1.getString("UPDATE_NAME"));
				Request.setRequestId(rs1.getString("UPDATED_DATE"));

				// 値を格納したHobbyインスタンスをリストに追加
				requestList.add(Request);
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}

		// アクセスした人に応答するためのJSONを用意する

		// JSONで出力する
		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(requestList));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
