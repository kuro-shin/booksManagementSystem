package borrowingBooksDisplay;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
 * Servlet implementation class DisplayBorrowingBooksServlet
 */
@WebServlet("/DisplayBorrowingBooksServlet")
public class DisplayBorrowingBooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayBorrowingBooksServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=UTF-8");
		// TODO 必須機能「趣味参照機能」
		// JDBCドライバの準備

		Map<String, String> conInfo = ConnectDb.loadDB();
//		Date date = new Date();
//        System.out.println(date.toString());

        HttpSession session = request.getSession();
		String employeeId = (String) session.getAttribute("employeeId");
        //String employeeId="0001";//sessionの情報持ってこれるようになったら消す

        PrintWriter pw = response.getWriter();
        List<BorrowingBook> BorrowingBooksList = new ArrayList<>();

		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(conInfo.get("url"), conInfo.get("user"), conInfo.get("pass"));

				// SQLの命令文を実行するための準備をおこないます
				PreparedStatement stmt = createPreparedStatement(con,employeeId);
				ResultSet rs1 = stmt.executeQuery();) {

			while(rs1.next()){
				BorrowingBook bb = new BorrowingBook();
				bb.setTitle(rs1.getString("TITLE"));
				bb.setPublisher(rs1.getString("PUBLISHER"));
				bb.setAuthor(rs1.getString("AUTHER"));
				bb.setReturn_due_date(rs1.getString("RETURN_DUE_DATE"));
				bb.setBorrowing_book_id(rs1.getString("BORROWING_BOOK_ID"));

				BorrowingBooksList.add(bb);
			}


		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}
		pw.append(new ObjectMapper().writeValueAsString(BorrowingBooksList));

	}

	private PreparedStatement createPreparedStatement(Connection con,String employeeId) throws SQLException {
		// 実行するSQL文
		String sql ="select \n" +
				"b.TITLE, \n" +
				"b.PUBLISHER, \n" +
				"b.AUTHER, \n" +
				"bb.BORROWING_BOOK_ID, \n" +
				"bb.RETURN_DUE_DATE \n" +
				"from \n" +
				"BORROWING_BOOKS bb, \n" +
				"BOOKS b, \n" +
				"EMPLOYEES e \n" +
				"where 1=1 \n" +
				"and bb.BOOK_ID=b.BOOK_ID \n" +
				"and bb.EMPLOYEE_ID=e.EMPLOYEE_ID \n" +
				"and e.EMPLOYEE_ID='"+employeeId+"'"+
				"and bb.IS_RETURNED=0";

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
