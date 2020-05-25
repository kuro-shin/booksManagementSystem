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
		String requestId = request.getParameter("requestId");
		int page = Integer.parseInt(request.getParameter("page"));
		System.out.println(page);
		int displayPage = 20;
		int getCountEnd = page * displayPage + 1;
		int getCountStart = getCountEnd - 20;
		String sql;
		// 実行するSQL文
		if (requestStatus == null ) {
			if (requestEmployeeId == null||requestEmployeeId.equals("")) {
				sql = "select*from( \n" + "select \n" + "AAA.RN, \n" + "RE.REQUEST_BOOK_ID, \n"
						+ "RE.EMPLOYEE_ID, \n" + "EM.NAME, \n" + "RE.TITLE, \n" + "RE.PUBLISHER, \n" + "RE.AUTHER, \n"
						+ "RE.URL, \n" + "RE.STATUS, \n" + "RE.REJECTED_REASON, \n" + "RE.REQUEST_DATE, \n"
						+ "RE.UPDATED_DATE, \n" + "EMP.NAME as UPDATE_NAME \n" + "from( \n"
						+ "select ROWNUM as rn, RE.REQUEST_BOOK_ID from \n" + "REQUEST_BOOKS RE, \n" + "EMPLOYEES EM, \n"
						+ "EMPLOYEES EMP \n" + "where 1=1 \n" + "and RE.EMPLOYEE_ID=EM.EMPLOYEE_ID(+) \n"
						+ "and RE.UPDATER_ID=EMP.EMPLOYEE_ID(+) \n" + "order by \n" + "RE.REQUEST_BOOK_ID DESC  \n" + ")AAA, \n"
						+ "REQUEST_BOOKS RE, \n" + "EMPLOYEES EM, \n" + "EMPLOYEES EMP \n" + "where 1=1 \n"
						+ "and AAA.REQUEST_BOOK_ID = RE.REQUEST_BOOK_ID \n" + "and RE.EMPLOYEE_ID=EM.EMPLOYEE_ID(+) \n"
						+ "and RE.UPDATER_ID=EMP.EMPLOYEE_ID(+) \n" + " \n" + ") \n" + "where rn between "
						+ getCountStart + " and " + getCountEnd + " order by RN";
			} else {
				sql = "select*from( \n" + "select \n" + "AAA.RN, \n" + "RE.REQUEST_BOOK_ID, \n"
						+ "RE.EMPLOYEE_ID, \n" + "EM.NAME, \n" + "RE.TITLE, \n" + "RE.PUBLISHER, \n" + "RE.AUTHER, \n"
						+ "RE.URL, \n" + "RE.STATUS, \n" + "RE.REJECTED_REASON, \n" + "RE.REQUEST_DATE, \n"
						+ "RE.UPDATED_DATE, \n" + "EMP.NAME as UPDATE_NAME \n" + "from( \n"
						+ "select ROWNUM as rn, RE.REQUEST_BOOK_ID from \n" + "REQUEST_BOOKS RE, \n" + "EMPLOYEES EM, \n"
						+ "EMPLOYEES EMP \n" + "where 1=1 \n" + "and RE.EMPLOYEE_ID=EM.EMPLOYEE_ID \n"
						+ "and RE.UPDATER_ID=EMP.EMPLOYEE_ID(+) \n" + "order by \n" + "RE.REQUEST_BOOK_ID DESC  \n" + ")AAA, \n"
						+ "REQUEST_BOOKS RE, \n" + "EMPLOYEES EM, \n" + "EMPLOYEES EMP \n" + "where 1=1 \n"
						+ "and AAA.REQUEST_BOOK_ID = RE.REQUEST_BOOK_ID \n" + "and RE.EMPLOYEE_ID=EM.EMPLOYEE_ID \n"
						+ "and RE.UPDATER_ID=EMP.EMPLOYEE_ID(+) \n" + "and RE.EMPLOYEE_ID = '"+requestEmployeeId+"' \n"
						+ "\n" + ") \n" + "where rn between "
						+ getCountStart + " and " + getCountEnd + " order by RN";
			}

		} else {
			if (requestEmployeeId == null||requestEmployeeId.equals("")) {
				sql = "select*from( \n" + "select \n" + "AAA.RN, \n" + "RE.REQUEST_BOOK_ID, \n"
						+ "RE.EMPLOYEE_ID, \n" + "EM.NAME, \n" + "RE.TITLE, \n" + "RE.PUBLISHER, \n" + "RE.AUTHER, \n"
						+ "RE.URL, \n" + "RE.STATUS, \n" + "RE.REJECTED_REASON, \n" + "RE.REQUEST_DATE, \n"
						+ "RE.UPDATED_DATE, \n" + "EMP.NAME as UPDATE_NAME \n" + "from( \n"
						+ "select ROWNUM as rn, RE.REQUEST_BOOK_ID from \n" + "REQUEST_BOOKS RE, \n" + "EMPLOYEES EM, \n"
						+ "EMPLOYEES EMP \n" + "where 1=1 \n" + "and RE.EMPLOYEE_ID=EM.EMPLOYEE_ID \n"
						+ "and RE.UPDATER_ID=EMP.EMPLOYEE_ID(+) \n" + "order by \n" + "RE.REQUEST_BOOK_ID DESC \n" + ")AAA, \n"
						+ "REQUEST_BOOKS RE, \n" + "EMPLOYEES EM, \n" + "EMPLOYEES EMP \n" + "where 1=1 \n"
						+ "and AAA.REQUEST_BOOK_ID = RE.REQUEST_BOOK_ID \n" + "and RE.EMPLOYEE_ID=EM.EMPLOYEE_ID \n"
						+ "and RE.UPDATER_ID=EMP.EMPLOYEE_ID(+) \n" + "\n"
						+ "and RE.STATUS = '"+requestStatus+"' \n" + ") \n" + "where rn between "
						+ getCountStart + " and " + getCountEnd + " order by RN";
			} else {
				sql = "select*from( \n" + "select \n" + "AAA.RN, \n" + "RE.REQUEST_BOOK_ID, \n"
						+ "RE.EMPLOYEE_ID, \n" + "EM.NAME, \n" + "RE.TITLE, \n" + "RE.PUBLISHER, \n" + "RE.AUTHER, \n"
						+ "RE.URL, \n" + "RE.STATUS, \n" + "RE.REJECTED_REASON, \n" + "RE.REQUEST_DATE, \n"
						+ "RE.UPDATED_DATE, \n" + "EMP.NAME as UPDATE_NAME \n" + "from( \n"
						+ "select ROWNUM as rn, RE.REQUEST_BOOK_ID from \n" + "REQUEST_BOOKS RE, \n" + "EMPLOYEES EM, \n"
						+ "EMPLOYEES EMP \n" + "where 1=1 \n" + "and RE.EMPLOYEE_ID=EM.EMPLOYEE_ID \n"
						+ "and RE.UPDATER_ID=EMP.EMPLOYEE_ID(+) \n" + "order by \n" + "RE.REQUEST_BOOK_ID DESC\n" + ")AAA, \n"
						+ "REQUEST_BOOKS RE, \n" + "EMPLOYEES EM, \n" + "EMPLOYEES EMP \n" + "where 1=1 \n"
						+ "and AAA.REQUEST_BOOK_ID = RE.REQUEST_BOOK_ID \n" + "and RE.EMPLOYEE_ID=EM.EMPLOYEE_ID \n"
						+ "and RE.UPDATER_ID=EMP.EMPLOYEE_ID(+) \n" + "and RE.EMPLOYEE_ID = '"+requestEmployeeId+"' \n"
						+ "and RE.STATUS = '"+requestStatus+"' \n" + ") \n" + "where rn between "
						+ getCountStart + " and " + getCountEnd + " order by RN";
			}
		}
		if (requestId != null) {
			sql = Request.requestId(requestId);
		}
		System.out.println(sql);

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
				Request a = new Request();
				// SQLの取得結果をインスタンスに代入
				System.out.println(rs1.getString("REQUEST_BOOK_ID"));
				a.setRequestId(rs1.getString("REQUEST_BOOK_ID"));
				System.out.println(a.getRequestId());
				a.setRequestEmployeeId(rs1.getString("EMPLOYEE_ID"));
				a.setRequestTitle(rs1.getString("TITLE"));
				a.setRequestPublisher(rs1.getString("PUBLISHER"));
				a.setRequestAuthor(rs1.getString("AUTHER"));
				a.setRequestApplicantName(rs1.getString("NAME"));
				a.setRequestApplicantDate(rs1.getString("REQUEST_DATE"));
				a.setRequestUrl(rs1.getString("URL"));
				a.setRequestStatus(rs1.getString("STATUS"));
				a.setRequestRejectReason(rs1.getString("REJECTED_REASON"));
				a.setRequestUpdaterName(rs1.getString("UPDATE_NAME"));
				a.setRequestUpdateDate(rs1.getString("UPDATED_DATE"));

				// 値を格納したHobbyインスタンスをリストに追加
				requestList.add(a);

			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}

		// アクセスした人に応答するためのJSONを用意する

		// JSONで出力する
		System.out.println(requestList.get(0));
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
