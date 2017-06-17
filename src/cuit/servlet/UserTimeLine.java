package cuit.servlet;

import cuit.log.UserLog;
import cuit.log.impl.UserLogImpl;
import cuit.model.UserBean;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Esong on 2017/6/17.
 */
public class UserTimeLine extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        UserBean userInfo = (UserBean)request.getSession().getAttribute("userShowInfor");
        UserLogImpl userLog = new UserLogImpl();
        JSONArray logJsonArr = userLog.readUserLogForTimeLine(String.valueOf(userInfo.getUtId()),0,10);
        PrintWriter out = response.getWriter();
        out.print(logJsonArr);
    }
}
