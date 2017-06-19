package cuit.servlet;

import cuit.service.TagService;
import cuit.util.AppUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Esong on 2017/6/17.
 */
public class TagEdit extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String type = request.getParameter("type");
        PrintWriter out = response.getWriter();
        if (type.equals("search")){
            String tagName = request.getParameter("tagName");
            TagService tagService = AppUtil.getTagService();
            ArrayList<String> tagArr = tagService.getTagListByLikeName(tagName);
            System.out.println("Info: user search tag list, tag name: "+tagName);
            out.print(JSONArray.fromObject(tagArr));
        }
    }
}
