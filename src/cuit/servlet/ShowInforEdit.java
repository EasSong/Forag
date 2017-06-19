package cuit.servlet;

import cuit.model.UserBean;
import cuit.service.MessageService;
import cuit.service.UserService;
import cuit.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Servlet implementation class ShowInforEdit
 */
public class ShowInforEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowInforEdit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession(true);

		UserService userService = AppUtil.getUserService();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String type = request.getParameter("type");
		int stateCode = 0;
		if (type.equals("edit")) {
			out.println("<html><body>");
			UserBean userShowInfor = (UserBean)session.getAttribute("userShowInfor");
			if (userShowInfor == null) {
				response.setHeader("refresh", "1;url=" + "/AdminLTE-2.3.11/forag/login.jsp");
			} else {
				userShowInfor.setUtName(request.getParameter("name"));
				userShowInfor.setUtAddr(request.getParameter("location"));
				userShowInfor.setUtPro(request.getParameter("profession"));
				userShowInfor.setUtEdu(request.getParameter("education"));
				userShowInfor.setUtSkill(request.getParameter("skills"));
				userShowInfor.setUtIntro(request.getParameter("intro"));
				stateCode = userService.editUserDetail(userShowInfor);
			}

			if (stateCode != ConstantDeclare.SUCCESS_EDIT_USERHSOWINFOR) {
				out.println("<h1>个人资料修改结果</h1>");
				if (stateCode == ConstantDeclare.ERROR_DB_ERROR) {
					out.println("<h2>数据库错误</h2>");
				}
			} else {
				out.println("<h2>个人资料修改成功</h2><br><h3>正在跳转。。。</h3>");
			}
			out.println("<br><span>两秒后跳转个人主页<span>");
			//reloadUrl = "/AdminLTE-2.3.11/forag/userProfile.jsp";
			response.setHeader("refresh","2;url=" + "/AdminLTE-2.3.11/forag/userProfile.jsp");
			out.println("</body></html>");
		}
		else if (type.equals("loginOut")){
			session.removeAttribute("userShowInfor");
			session.invalidate();
			session.setAttribute("isLogin",false);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("state","success");
			out.print(jsonObject.toString());
		}
		else if(type.equals("checkLogin")){
			if (session.getAttribute("isLogin") == null || !(Boolean) session.getAttribute("isLogin")){
				JSONObject jsonObject = new JSONObject();
                jsonObject.put("state", "notLogin");
				out.print(jsonObject.toString());
			}
			else{
				//封装用户信息，并且置state为登录状态
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("state","isLogin");
				JSONObject jsonUser = new JSONObject();
				UserBean userBean = (UserBean) session.getAttribute("userShowInfor");
				jsonUser.put("uId",userBean.getUtId());
				jsonUser.put("uName",userBean.getUtName());
				jsonUser.put("uEmail",userBean.getUtMail());
				jsonUser.put("uPic",userBean.getuPic());
				jsonUser.put("uProfession",userBean.getUtPro());
				jsonUser.put("uDate",userBean.getUtDate().toString());
				jsonUser.put("uAddr",userBean.getUtAddr());
				jsonUser.put("uEdu",userBean.getUtEdu());
				jsonUser.put("uIntro",userBean.getUtIntro());
				jsonUser.put("uSkill",userBean.getUtSkill());
				jsonUser.put("uInterest",JSONObject.fromObject(userBean.getUtInterest()).getString("tag"));
				jsonObject.put("userInfor",jsonUser);
				out.print(jsonObject.toString());
			}
		}
		else if (type.equals("hotTag")){
			MessageService messageService = AppUtil.getMessageService();
			Date date = new Date(System.currentTimeMillis());
			String nowDateStr = date.toString();
			//获取当前热门的标签的id
			JSONArray jsonArray = MySocket.getHotTags(0,10).getJSONArray("tags");
			out.print(jsonArray);
		}
		else if (type.equals("interest")){
			String userInterest = request.getParameter("interest");
			UserBean userBean = (UserBean) session.getAttribute("userShowInfor");
			MyUtil.setUserInterest(userBean,userInterest);
			TaskTimer.resetTimeDelay();
			TaskTimer.addSubmitCount();
			if (TaskTimer.getSubmitCount() == 1){
				Thread thread = new Thread(new TaskTimer());
				thread.start();
			}
		}
	}

}
