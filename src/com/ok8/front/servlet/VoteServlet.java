package com.ok8.front.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ok8.common.global.ErrorCode;
import com.ok8.common.utils.JacksonUtils;
import com.ok8.common.utils.Result;
import com.ok8.front.service.VoteService;

/**
 * 摄影比赛投票系统
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns={"/vote/*"})
public class VoteServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getPathInfo().substring(1);
		String json = req.getParameter("json");
		HashMap<String, Object> params = JacksonUtils.readValueAsHashMapQuietly(json);
		HttpSession session = req.getSession();
		ArrayList<HashMap<String, Object>> results = null;
		HashMap<String, Object> result = null;
		VoteService voteService = new VoteService();
		String savePath = req.getServletContext().getRealPath("/page/images/");
		switch (action) {
		case "read"://读取所有摄影作品
			String staffCode = (String)session.getAttribute("staffCode");
			results = voteService.read(staffCode);
			break;
		case "vote"://投票
			params.put("staffCode",session.getAttribute("staffCode"));//获取制单人code
			result = voteService.vote(params);
			break;
		case "create"://创建作品
			result = voteService.create(req);
			resp.sendRedirect("/vote/page/form.html");
			return;
		case "update"://创建作品
			result = voteService.update(req);
			resp.sendRedirect("/vote/page/form.html");
			return;
		case "deletePic"://创建作品
			result = voteService.deletePic(params,savePath);
			break;
		case "deleteWork"://创建作品
			result = voteService.deleteWork(params,savePath);
			break;
		default:
			result = Result.get(false, ErrorCode.ERR_NO_ACTION);
			break;
		}
		if (results!=null) {
			resp.getWriter().print(JacksonUtils.writeValueAsString(results));
		} else {
			resp.getWriter().print(JacksonUtils.writeValueAsString(result));
		}
	}	

}
