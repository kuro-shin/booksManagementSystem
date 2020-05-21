package bookEditOrDelete;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import bookSearchResults.Book;
import connectDB.ConnectDb;

/**
 * Servlet implementation class DisplayBookServlet
 */
@WebServlet("/DisplayBookServlet")
public class DisplayBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());	response.setContentType("text/html; charset=UTF-8");
		// TODO 必須機能「趣味参照機能」
		// JDBCドライバの準備

			Map<String, String> conInfo = ConnectDb.loadDB();
			String book_id = request.getParameter("book_id");

//        HttpSession session = request.getSession();
//		String employeeId = (String) session.getAttribute("employeeId");
        //String employeeId="0001";//sessionの情報持ってこれるようになったら消す
		String employeeRole="manager";


        PrintWriter pw = response.getWriter();
        List<Book>books = new ArrayList<>();
        System.out.println("DisplayBookServletの57行目まではおｋ");
		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(conInfo.get("url"), conInfo.get("user"), conInfo.get("pass"));

				// SQLの命令文を実行するための準備をおこないます
				PreparedStatement stmt = createPreparedStatement(con,book_id);
				ResultSet rs1 = stmt.executeQuery();) {

			if(rs1.next()){
//				Book b = new Book();
//				b.setBookTitle(rs1.getString("TITLE"));
//				b.setBookAuther(rs1.getString("AUTHER"));
//				b.setBookPublisher(rs1.getString("PUBLISHER"));
//				b.setBookGenreName(rs1.getString("GENRE_NAME"));
//				System.out.println("DisplayBookServletのrs1.next()までは通ってるよ");
//				books.add(b);
//				 System.out.println(b);

			}


		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}
		//pw.append(new ObjectMapper().writeValueAsString(books));
		pw.append(new ObjectMapper().writeValueAsString("ok"));

	}

	private PreparedStatement createPreparedStatement(Connection con,String book_id) throws SQLException {
		// 実行するSQL文
		String sql ="select \n" +
				"b.TITLE, \n" +
				"b.PUBLISHER, \n" +
				"b.AUTHER, \n" +
				"g.GENRE_NAME \n" +
				"from \n" +
				"BOOKS b, \n" +
				"GENRES g \n" +
				"where 1=1 \n" +
				"and BOOK_ID='"+book_id+"' \n" +
				"and b.GENRE_ID=g.GENRE_ID \n" ;
		System.out.println(sql);

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
