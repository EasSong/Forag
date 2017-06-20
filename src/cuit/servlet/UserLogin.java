package cuit.servlet;

import cuit.service.UserService;
import cuit.util.AppUtil;
import cuit.util.ConstantDeclare;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class UserLogin
 */
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLogin() {
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
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String userEmail = request.getParameter("userEmail");
		String userPassword = request.getParameter("userPassword");
		UserService userService = AppUtil.getUserService();
		int stateCode = userService.loginUser(userEmail, userPassword);
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		
		//stateCode大于0表示登录成功并此变量为用户ID
		if (stateCode < 0){
			out.println("<h1>登录结果</h1>");
			if (stateCode == ConstantDeclare.ERROR_DB_ERROR){
				out.println("<h2>数据库错误</h2>");
			}
			else if (stateCode == ConstantDeclare.ERROR_USER_UNEXISTS){
				out.println("<h2>用户不存在</h2>");
			}
			else if (stateCode == ConstantDeclare.ERROR_PASSWORD_LOGIN){
				out.println("<h2>密码错误</h2>");
			}
			
			out.println("<br><span>两秒秒后跳转登录页面<span>");
			response.setHeader("refresh","2;url=" + "/Foarg/forag/login.jsp");
		}
		else{
			HttpSession session = request.getSession(true);
			session.setAttribute("userShowInfor", userService.getUserDetail(stateCode));
			session.setAttribute("isLogin",true);
			out.println("<h2>登录成功</h2><br><h3>正在跳转。。。</h3>");
			response.setHeader("refresh","1;url=" + "/Forag/forag/userProfile.jsp");
		}
		
		out.println("</body></html>");
	}

}
