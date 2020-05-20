package bookSearchResults;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
		int getCountStart = getCountEnd-21;
		System.out.println(getCountStart+""+getCountEnd);

//		String sql = "";
//
//
//		//DBのURL,ID,PASSを取得
//		Map<String, String> conInfo = ConnectDb.loadDB();
//
//
//		try (
//			// データベースへ接続します
//			Connection con = DriverManager.getConnection(conInfo.get("url"), conInfo.get("user"), conInfo.get("pass"));
//			// SQLの命令文を実行するための準備をおこないます
//			Statement stmt = con.createStatement();
//			// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
//			ResultSet rs1 = stmt.executeQuery(sql);
//		) {
//			List<Dept> deptList = new ArrayList<Dept>();
//			// SQL実行結果を保持している変数rsから情報を取得
//			while (rs1.next()) {
//				Dept dept = new Dept();
//				dept.setDeptId(rs1.getString("DEPT_ID"));
//				dept.setDeptName(rs1.getString("DEPT_NAME"));
//				deptList.add(dept);
//			}
//
//			// アクセスした人に応答するためのJSONを用意する
//			PrintWriter pw = response.getWriter();
//
//			// JSONで出力する
//			pw.append(new ObjectMapper().writeValueAsString(deptList));
//
//		} catch (Exception e) {
//			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
//		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
