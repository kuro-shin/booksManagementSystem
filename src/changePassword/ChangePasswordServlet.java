package changePassword;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import connectDB.ConnectDb;
import login.Hmac;

/**
 * Servlet implementation class ChangePasswordServlet
 */
@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePasswordServlet() {
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
		String authCode = request.getParameter("authCode");
		String newPassword = request.getParameter("newPassword");
		String newPasswordConfirm = request.getParameter("newPasswordConfirm");
		String employeeId = (String) session.getAttribute("employeeId");
		int sessionAuthCode = (int) session.getAttribute("AuthCode");
		String sql = null;
		System.out.println("ユーザーの入力 authCode"+authCode+" pass"+newPassword+" passconf"+newPasswordConfirm);
		System.out.println("セッションAUTHCODE:"+sessionAuthCode+" empId"+employeeId);


		if(Integer.parseInt(authCode)!=sessionAuthCode){
			System.out.println("認証コードが違います。"+authCode+":"+sessionAuthCode);
			throw new RuntimeException(String.format("認証コードが違います。"));
		}
		if(newPassword.equals(newPasswordConfirm)){

			String changePassword = Hmac.getHmac(newPassword);

			sql = "update EMPLOYEES \n" +
					"set PASSWORD = '"+changePassword+"' \n" +
					"where EMPLOYEE_ID = '"+employeeId+"'  \n";

			System.out.println(sql);

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


				session.removeAttribute("AuthCode");
				// アクセスした人に応答するためのJSONを用意する
				PrintWriter pw = response.getWriter();

				// JSONで出力する
				pw.append(new ObjectMapper().writeValueAsString("CHANGED"));

			} catch (Exception e) {
				throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
			}
		}else{
			System.out.println("パスワードが違います"+newPassword+":"+newPasswordConfirm);
			throw new RuntimeException(String.format("入力したパスワードが違います。"));
		}

	}

}
