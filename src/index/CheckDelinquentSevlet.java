package index;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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


		Map<String, String> conInfo = ConnectDb.loadDB();
//	Date date = new Date();
//      System.out.println(date.toString());
//      HttpSession session = request.getSession();
//		String employeeId = (String) session.getAttribute("employeeId");
        String employeeId="0001"; //

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String createDay = sdf.format(cal.getTime());

        PrintWriter pw = response.getWriter();
        String isDelinquent="";

		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(conInfo.get("url"), conInfo.get("user"), conInfo.get("pass"));

				// SQLの命令文を実行するための準備をおこないます
				PreparedStatement stmt = createPreparedStatement(con,employeeId,createDay);
				ResultSet rs1 = stmt.executeQuery();) {

			if(rs1.next()){
			isDelinquent="Delinquent";
			}

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}
		pw.append(new ObjectMapper().writeValueAsString(isDelinquent));
	}

	private PreparedStatement createPreparedStatement(Connection con,String employeeId,String createDay) throws SQLException {
		// 実行するSQL文
		String sql ="select \n" +
				"* \n" +
				"from \n" +
				"BORROWING_BOOKS \n" +
				"where 1=1 \n" +
				"and IS_RETURNED = 0" +
				"and RETURN_DUE_DATE<"+createDay+" "+
				"and EMPLOYEE_ID='"+employeeId+"' ";
		//System.out.println(sql);
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
