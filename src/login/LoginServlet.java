package login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
	    response.setContentType("text/html; charset=shift-jis");
	    // Servlet 初期化パラメータ情報
	    String empId = request.getParameter("employeeId");
	    String password = Hmac.getHmac(request.getParameter("password"));
	    System.out.println(password+" "+empId);
	    PrintWriter pw = response.getWriter();



	    Map<String, String> conInfo = ConnectDb.loadDB();

	    Map <String, String> responseData = new HashMap<>();
		// DBへ接続してSQLを実行
		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(conInfo.get("url"), conInfo.get("user"), conInfo.get("pass"));
				PreparedStatement stmt = createPreparedStatement(con, empId, password);
				ResultSet rs1 = stmt.executeQuery();
			) {
				//ユーザIDとパスワードが一致しているとき
				if (rs1.next()) {
					HttpSession session = request.getSession(true);
					//セッションオブジェクトに保存する

					String employeeId = rs1.getString("EMPLOYEE_ID");
					String employeeName = rs1.getString("NAME");
					String employeeRole = rs1.getString("EMPLOYEE_ROLE");
				    session.setAttribute("employeeId", employeeId);
				    session.setAttribute("employeeName", employeeName);
				    session.setAttribute("employeeRole", employeeRole);
				    responseData.put("result", "OK");
				}else{
					responseData.put("result", "NG");
				}
				// JSONで出力する
				pw.append(new ObjectMapper().writeValueAsString(responseData));

			} catch (Exception e) {
				throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
			}

	  }

	private PreparedStatement createPreparedStatement(Connection con, String empId, String password) throws
		SQLException {
		// 実行するSQL文
		System.out.println("EMPID"+empId);
		System.out.println("INPUTPASS"+password);
	    String sql="select EMPLOYEE_ID, NAME, EMPLOYEE_ROLE \n" +
	    		"from EMPLOYEES \n" +
	    		"where EMPLOYEES.EMPLOYEE_ID =? \n" +
	    		"and EMPLOYEES.PASSWORD=?\n";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, empId);
		stmt.setString(2, password);
		return stmt;
		}
	}
