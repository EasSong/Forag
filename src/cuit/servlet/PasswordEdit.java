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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class PasswordEdit
 */
public class PasswordEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordEdit() {
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
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		HttpSession session = request.getSession(true);
		UserService userService = AppUtil.getUserService();
		UserBean userShowInfor = (UserBean)session.getAttribute("userShowInfor");
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		int stateCode = 0;
		
		if (userShowInfor == null){
			response.setHeader("refresh","1;url=" + "/Forag/forag/login.jsp");
		}
		else{
			stateCode = userService.editUserPassword(userShowInfor.getUtId(), oldPassword, newPassword);
		}
		
		if (stateCode != ConstantDeclare.SUCCESS_EDIT_PASSWORD){
			out.println("<h1>密码修改结果</h1>");
			if (stateCode == ConstantDeclare.ERROR_DB_ERROR || stateCode == ConstantDeclare.ERROR_PASSWORD_EDIT){
				out.println("<h2>数据库错误</h2>");
			}
			else if (stateCode == ConstantDeclare.ERROR_USER_UNEXISTS){
				out.println("<h2>用户不存在</h2>");
			}
			else if (stateCode == ConstantDeclare.ERROR_PASSWORD_LOGIN){
				out.println("<h2>密码错误</h2>");
			}
			
			out.println("<br><span>两秒后跳转个人主页<span>");
			response.setHeader("refresh","2;url=" + "/Forag/forag/userProfile.jsp");
		}
		else{
			out.println("<h2>密码修改成功</h2><br><h3>正在跳转。。。</h3>");
			out.println("<br><span>两秒后跳转登录页面<span>");
			request.setAttribute("userShowInfor", null);
			response.setHeader("refresh","2;url=/Forag/forag/login.jsp");
		}
		
		out.println("</body></html>");
	}
}
