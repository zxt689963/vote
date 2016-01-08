package com.ok8.front.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ok8.common.utils.Result;
import com.ok8.common.utils.StringUtils;
//import com.ok8.common.auth.AuthManager;
import com.ok8.common.global.Constants;
import com.ok8.common.global.ErrorCode;
import com.ok8.common.global.Message;
import com.ok8.common.utils.JacksonUtils;
import com.ok8.front.service.LoginService;

/**
 * 系统——登录、修改密码、退出
 */
@SuppressWarnings("serial")
@WebServlet("/login/*")
public class LoginServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String action = request.getPathInfo().substring(1);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user", request.getParameter("c_usr"));
		params.put("password", request.getParameter("c_pwd"));
		LoginService loginService = new LoginService();
		switch (action) {
		case "login": // 登录
			HashMap<String, Object> data = loginService.login(params);
			String staffCode = (String) data.get("staffCode");
			if (StringUtils.isNotBlank(staffCode)) {
				initSessionAttribute(session, data);
				System.out.println(request.getContextPath());
//				request.getRequestDispatcher("/vote_page/index1.html").forward(request, response);
				response.sendRedirect("/vote/page/index.html");
			}else{
				response.sendRedirect("/vote/manage/login/error.html");
			}
		}
		
	}
	
	/**
	 * 初始化 HTTP Session 用户信息
	 * @param session
	 * @param data
	 * @return
	 */
	private void initSessionAttribute(HttpSession session, HashMap<String, Object> data) {
		session.setAttribute("user", data.get("user"));
		session.setAttribute("tenantId", data.get("tenantId"));
		session.setAttribute("password", data.get("password"));
		session.setAttribute("staffCode", data.get("staffCode"));
		session.setAttribute("staffName", data.get("staffName"));
		String adminFlag = (String) data.get("adminFlag");
		// 将不需要返回前端的数据删除
		data.remove("user");
		data.remove("tenantId");
		data.remove("password");
		data.remove("staffCode");
		if (adminFlag.indexOf("A") > -1) {
			session.setAttribute("adminFlag", "A");
			return;
		}
		if (adminFlag.indexOf("M") > -1) {
			session.setAttribute("adminFlag", "M");
			return;
		}
		if (adminFlag.indexOf("U") > -1) {
			session.setAttribute("adminFlag", "U");
			return;
		}
	}

}
