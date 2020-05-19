package practice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class ItemSearchServlet
 */
@WebServlet("/GetPostSampleServlet")
public class GetPostSampleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPostSampleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("------------------------");
		System.out.println("GETでアクセスされました");
		// リクエストパラメータの取得
	    String q = request.getParameter("q");

		// 画面へレスポンスを返却する処理
		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString("【GET】"+q));

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("------------------------");
		System.out.println("POSTでアクセスされました");
		// リクエストパラメータの取得
	    String q = request.getParameter("q");


		// 画面へレスポンスを返却する処理
		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString("【POST】"+q));

	}


}
