package userAuth;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import connectDB.ConnectDb;
import mail.SendMail;

/**
 * Servlet implementation class SendAuthCode
 */
@WebServlet("/SendAuthCode")
public class SendAuthCode extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendAuthCode() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("text/html; charset=shift-jis");
	    HttpSession session = request.getSession(true);
	    // Servlet 初期化パラメータ情報
	    String empId = request.getParameter("employeeId");
	    String empName = request.getParameter("employeeName");
	    System.out.println(empId+" "+empName);
	    String employeeId=null;
	    String employeeName=null;
	    String employeeMail = null;
	    String mailTo = null;
	    String subject = null;
	    String content = null;
	    PrintWriter pw = response.getWriter();



	    Map<String, String> conInfo = ConnectDb.loadDB();

	    Map <String, String> responseData = new HashMap<>();
		// DBへ接続してSQLを実行
		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(conInfo.get("url"), conInfo.get("user"), conInfo.get("pass"));
				PreparedStatement stmt = createPreparedStatement(con, empId, empName);
				ResultSet rs1 = stmt.executeQuery();
			) {
				//ユーザIDとパスワードが一致しているとき
				if (rs1.next()) {

					//セッションオブジェクトに保存する
					long seed = System.currentTimeMillis();
					Random random = new Random(seed);
					int AuthCode = random.nextInt(899999)+100000;
					employeeId = rs1.getString("EMPLOYEE_ID");
					session.setAttribute("employeeId", employeeId);
					employeeName = rs1.getString("NAME");
					employeeMail = rs1.getString("MAIL");
					session.removeAttribute("AuthCode");
				    session.setAttribute("AuthCode",AuthCode );
				    mailTo = employeeMail;
					subject = "パスワード変更認証メール";
				    content = "社員ID:"+employeeId+"\n"
				    		+"氏名:"+employeeName+"\n"
				    		+"認証コード:"+session.getAttribute("AuthCode");
				    System.out.println();

				    responseData.put("result", "OK");
				}else{
					responseData.put("result", "NG");
				}
				SendMail sendMail = new SendMail();
				sendMail.createMail(mailTo, subject, content);
//				MailSender mailSender = new MailSender();
//				mailSender.createMailBy(subject, content);
//				mailSender.send(address);
			} catch (Exception e) {
				throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
			}
		//Mailtest.send(employeeMail, subject, content);

		//SendMail(mailTo,subject,content);
		pw.append(new ObjectMapper().writeValueAsString(responseData));

		}
	private PreparedStatement createPreparedStatement(Connection con, String empId, String empName) throws
		SQLException {
		// 実行するSQL文
		System.out.println("EMPID"+empId);
		System.out.println("INPUTPASS"+empName);
	    String sql="select EMPLOYEE_ID, NAME, MAIL \n" +
	    		"from EMPLOYEES \n" +
	    		"where EMPLOYEES.EMPLOYEE_ID =? \n" +
	    		"and EMPLOYEES.NAME=?\n";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, empId);
		stmt.setString(2, empName);
		return stmt;
		}
	}


