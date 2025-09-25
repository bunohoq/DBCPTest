package com.test.java;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;



@WebServlet(value = "/testconnection.do")
public class ConnectionTest extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//ConnectionTest.java
		
		//DB 연결 방식
		//1. 기존 방식
		//	- 필요 시 > Connection 객체 생성 > 사용 > 소멸
		//2. 새로운 방식
		//	- 커넥션풀 > 위임
		
		try {
			
			Connection conn = null;
			Statement stat = null;
			ResultSet rs = null;
			
			//context.xml 읽기 > JNDI
			Context ctx = new InitialContext();
			Context env = (Context)ctx.lookup("java:comp/env");
			
			DataSource ds = (DataSource)env.lookup("jdbc/pool");
			
			//DB 연결(DBUtil.open() 상황)
			conn = ds.getConnection();
			
			System.out.println(conn.isClosed()); //false
			
			stat = conn.createStatement();
			
			rs = stat.executeQuery("select count(*) as cnt from tblUser");
			
			if (rs.next()) {
				System.out.println(rs.getInt("cnt"));
			}
			
			//커넥션 풀 사용 X > 연결 종료 O
			//커넥션 풀 사용 O > 연결 종료 X > 풀에 반납
			conn.close();
			
			
		} catch (Exception e) {
			System.out.println("ConnectionTest.doGet()");
			e.printStackTrace();
		}
		
	}

}
