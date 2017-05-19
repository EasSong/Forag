package cuit.servlet;

import cuit.model.UserBean;
import cuit.service.UserService;
import cuit.util.AppUtil;
import cuit.util.ConstantDeclare;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class UserRegister
 */
public class UserRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRegister() {
        super();
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
	    response.setContentType("text/html;charset=UTF-8");
		String userName = request.getParameter("userName");
		String userMail = request.getParameter("userMail");
		String password = request.getParameter("password");
		UserService userService = AppUtil.getUserService();
		UserBean userBean = new UserBean();
		userBean.setName(userName);
		userBean.setEmail(userMail);
		userBean.setPass(password);
		int stateCode = userService.registerUser(userBean);

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println("<html><body>");

		//stateCode大于0表示登录成功并此变量为用户ID
		if (stateCode != ConstantDeclare.SUCCESS_USER_REGISTER){
			out.println("<h1>注册结果</h1>");
			if (stateCode == ConstantDeclare.ERROR_DB_ERROR){
				out.println("<h2>数据库错误</h2>");
			}
			else if (stateCode == ConstantDeclare.ERROR_MAIL_EXISTED){
				out.println("<h2>用邮箱已被注册</h2>");
			}

			out.println("<br><span>两秒秒后跳转注册页面<span>");
			response.setHeader("refresh","2;url=" + "/AdminLTE-2.3.11/forag/register.jsp");
		}
		else{
			out.println("<h2>注册成功</h2><br><h3>正在跳转登录页面。。。</h3>");
			response.setHeader("refresh","1;url=" + "/AdminLTE-2.3.11/forag/login.jsp");
		}

		out.println("</body></html>");
	}

}
