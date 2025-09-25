package com.test.java.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class UserDAO {

	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;

	public UserDAO() {
		try {
			
			Context ctx = new InitialContext();
			Context env = (Context)ctx.lookup("java:comp/env");
			DataSource ds = (DataSource)env.lookup("jdbc/pool");
			
			conn = ds.getConnection();
			stat = conn.createStatement();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String get(String id) {
		//queryparamtokenReturn
		try {

			String sql = "select name from tblUser where id = ?";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);

			rs = pstat.executeQuery();

			if (rs.next()) {

				return rs.getString("name");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
