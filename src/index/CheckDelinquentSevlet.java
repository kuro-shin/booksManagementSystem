package index;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connectDB.ConnectDb;

/**
 * Servlet implementation class CheckDelinquentSevlet
 */
@WebServlet("/CheckDelinquentSevlet")
public class CheckDelinquentSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckDelinquentSevlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

		Map<String, String> conInfo = ConnectDb.loadDB();
		Date date = new Date();
        System.out.println(date.toString());

        HttpSession session = request.getSession();
		String employeeId = (String) session.getAttribute("employeeId");

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String createDay = sdf.format(cal.getTime());


		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(conInfo.get("url"), conInfo.get("user"), conInfo.get("pass"));

				// SQLの命令文を実行するための準備をおこないます
				PreparedStatement stmt = createPreparedStatement(con,employeeId,createDay);
				ResultSet rs1 = stmt.executeQuery();) {

			System.out.println("checkDelinquetできた");

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}

	}

	private PreparedStatement createPreparedStatement(Connection con,String employeeId,String createDay) throws SQLException {
		// 実行するSQL文
		String sql ="select \n" +
				"* \n" +
				"from \n" +
				"BORROWING_BOOKS \n" +
				"where 1=1 \n" +
				"and RETURN_DUE_DATE>"+createDay+
				"and EMPLOYEE_ID='"+employeeId+"' \n";

		PreparedStatement stmt = con.prepareStatement(sql);
		return stmt;
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
