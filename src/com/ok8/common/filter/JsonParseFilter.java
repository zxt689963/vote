package com.ok8.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import com.ok8.common.global.Message;
import com.ok8.common.utils.JacksonUtils;

/**
 * 当JSON参数格式不正确时，将被直接拦截掉，不再进入对应的Servlet
 * @author caiwl
 */
@WebFilter(urlPatterns = {"/vote/*"})
public class JsonParseFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String content = request.getParameter("json");
		try {
			JacksonUtils.readValueAsHashMap(content);
		} catch (Exception e) {
			response.getWriter().print(Message.ERROR_JSON);
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
