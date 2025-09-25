package com.test.java;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jni.User;

import com.test.java.model.UserDAO;

@WebServlet(value = "/dbcptest.do")
public class DBCPTest extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//DBCPTest.java
		String param = req.getParameter("no");
		
		if (param != null) {
			int no = Integer.parseInt(param);
			req.setAttribute("no", no + 1);
		} else {
			req.setAttribute("no", 1);
		}
		
		UserDAO dao = new UserDAO();
		
		String name = dao.get("hong");
		
		req.setAttribute("name", name);
		
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/dbcptest.jsp");
		dispatcher.forward(req, resp);
	}

}
