package borrowBook;

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
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import connectDB.ConnectDb;

/**
 * Servlet implementation class BorrowBookServlet
 */
@WebServlet("/BorrowBookServlet")
public class BorrowBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowBookServlet() {
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
		String employeeId = (String) session.getAttribute("employeeId");
		//一覧表示、追加、編集、削除のどのリクエストかを判断
		String bookId = request.getParameter("bookId");
		int borrowingNum = Integer.parseInt(GetBorrowingBooksNumber.get(employeeId));

		Calendar date = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String checkOutDate = sdf.format(date.getTime());
        date.add(Calendar.DATE, 14);
        String returnDueDate = sdf.format(date.getTime());

        String excute_message="";
		//String jsRequest = "depttable";
        System.out.println("bookId"+bookId);
        if(borrowingNum>9){
        	String errorMsg = "over";
        	PrintWriter pw = response.getWriter();
			pw.append(new ObjectMapper().writeValueAsString(errorMsg));
        }else{

    		SetBookIsBorrowingFlag.SetBorrowingFlag(bookId);

    		String sql = "insert into BORROWING_BOOKS \n" +
    				"(BORROWING_BOOK_ID, BOOK_ID, EMPLOYEE_ID, CHECK_OUT_DATE,  \n" +
    				"	RETURN_DUE_DATE, IS_RETURNED) \n" +
    				"select \n" +
    				"lpad(MAX(SUBSTR(BORROWING_BOOK_ID,0))+1,8,0), \n" +
    				"'"+bookId+"', \n" +
    				"'"+employeeId+"', \n" +
    				"'"+checkOutDate+"', \n" +
    				"'"+returnDueDate+"', \n" +
    				"'0' \n" +
    				"from BORROWING_BOOKS \n";
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


    			// アクセスした人に応答するためのJSONを用意する
    			PrintWriter pw = response.getWriter();

    			// JSONで出力する
    			pw.append(new ObjectMapper().writeValueAsString(excute_message));

    		} catch (Exception e) {
    			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
    		}
        }
	}

}
