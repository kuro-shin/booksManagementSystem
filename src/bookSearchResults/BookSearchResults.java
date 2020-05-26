package bookSearchResults;

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
 * Servlet implementation class BookSearchResults
 */
@WebServlet("/BookSearchResults")
public class BookSearchResults extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookSearchResults() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		List<String> searchWordList = new ArrayList<String>();
		//一覧表示、追加、編集、削除のどのリクエストかを判断
		int searchWordLength =Integer.parseInt(request.getParameter("searchWordLength"));
		for(int i=0;i<searchWordLength;i++){
			searchWordList.add(request.getParameterValues("searchWord[]")[i]);
		}
		System.out.println("リスト"+searchWordList);

		int page = Integer.parseInt(request.getParameter("page"));
		System.out.println(page);
		int displayPage = 20;
		int getCountEnd = page*displayPage+1;
		int getCountStart = getCountEnd-20;
		System.out.println(getCountStart+""+getCountEnd);

//		String sql = "select * from ( \n" +
//				" \n" +
//				"select ROWNUM as rn, BK.BOOK_ID, BK.TITLE, BK.PUBLISHER, BK.AUTHER, GE.GENRE_NAME, BK.IS_BORROWING, BB.RETURN_DUE_DATE \n" +
//				"from BOOKS BK, \n" +
//				"GENRES GE, \n" +
//				"BORROWING_BOOKS BB \n" +
//				"where 1=1 \n" +
//				"and GE.GENRE_ID = BK.GENRE_ID \n" +
//				"and BB.BOOK_ID(+)=BK.BOOK_ID \n";
//		String searchWord = null;
//		for(int i=0;i<searchWordLength;i++){
//			searchWord = searchWordList.get(i);
//			sql += "and( \n" +
//					"BK.TITLE LIKE '%"+searchWord+"%' \n" +
//					"or BK.AUTHER LIKE '%"+searchWord+"%' \n" +
//					"or BK.PUBLISHER LIKE '%"+searchWord+"%' \n" +
//					"or GE.GENRE_NAME LIKE '%"+searchWord+"%' \n" +
//					") \n";
//		}
//		sql+="order by BK.BOOK_ID\n" +
//				") \n" +
//				"where rn between "+getCountStart+" and "+getCountEnd+" \n";

		String sql = "select * from ( \n" +
				" \n" +
				"select ROWNUM as rn, BK.BOOK_ID, BK.TITLE, BK.PUBLISHER, BK.AUTHER, GE.GENRE_NAME, BK.IS_BORROWING, BKS.RETURN_DUE_DATE--, BBS.IS_RETURNED \n" +
				"from \n" +
				"	( \n" +
				"select distinct BK.BOOK_ID , MAX(BB.RETURN_DUE_DATE) as RETURN_DUE_DATE from \n" +
				"	BOOKS BK, \n" +
				"	GENRES GE, \n" +
				"	BORROWING_BOOKS BB \n" +
				"	 \n" +
				"	where 1=1 \n" +
				"	and GE.GENRE_ID = BK.GENRE_ID \n" +
				"	and BB.BOOK_ID(+)=BK.BOOK_ID \n" +
				"	group by BK.BOOK_ID \n" +
				"	order by BK.BOOK_ID \n" +
				"	) BKS, \n" +
				"	BOOKS BK, \n" +
				"	GENRES GE \n" +
				"	--(select BOOK_ID,RETURN_DUE_DATE, MIN(IS_RETURNED) as IS_RETURNED from BORROWING_BOOKS group by book_id, RETURN_DUE_DATE order by book_id) BBS \n" +
				"	where 1=1 \n" +
				"	and BKS.BOOK_ID = BK.BOOK_ID \n" +
				"	and GE.GENRE_ID = BK.GENRE_ID \n";
		String searchWord = null;
		for(int i=0;i<searchWordLength;i++){
			searchWord = searchWordList.get(i);
			sql += "and( \n" +
					"BK.TITLE LIKE '%"+searchWord+"%' \n" +
					"or BK.AUTHER LIKE '%"+searchWord+"%' \n" +
					"or BK.PUBLISHER LIKE '%"+searchWord+"%' \n" +
					"or GE.GENRE_NAME LIKE '%"+searchWord+"%' \n" +
					") \n";
		}

		sql+=") \n"
			+ "where rn between "+getCountStart+" and "+getCountEnd+" \n";
		System.out.println(sql);

		//		//DBのURL,ID,PASSを取得
		Map<String, String> conInfo = ConnectDb.loadDB();

		try (
			// データベースへ接続します
			Connection con = DriverManager.getConnection(conInfo.get("url"), conInfo.get("user"), conInfo.get("pass"));
			// SQLの命令文を実行するための準備をおこないます
			Statement stmt = con.createStatement();
			// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
			ResultSet rs1 = stmt.executeQuery(sql);
		) {
			List<Book> bookList = new ArrayList<Book>();
			// SQL実行結果を保持している変数rsから情報を取得

			while (rs1.next()) {
				Book book = new Book();
				book.setBookId(rs1.getString("BOOK_ID"));
				book.setBookTitle(rs1.getString("TITLE"));
				book.setBookPublisher(rs1.getString("PUBLISHER"));
				book.setBookAuther(rs1.getString("AUTHER"));
				book.setBookGenreName(rs1.getString("GENRE_NAME"));
				book.setBookIsBorrowing(rs1.getString("IS_BORROWING"));
				book.setReturnDueDate(rs1.getString("RETURN_DUE_DATE"));

				bookList.add(book);
			}

			// アクセスした人に応答するためのJSONを用意する
			PrintWriter pw = response.getWriter();

			// JSONで出力する
			pw.append(new ObjectMapper().writeValueAsString(bookList));

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
