package com.pack;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class Test extends HttpServlet {
	
	private DataSource dataSource;
	private Connection connection;
	private Statement statement;
	
	public void init() throws ServletException {
		try {
			// Get DataSource  .....java:comp/env/jdbc/gaming
			//create database name=gaming and table name=GAME;
			
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			//dataSource = (DataSource)envContext.lookup("jdbc/testdb");
		    dataSource = (DataSource)envContext.lookup("jdbc/myoracle");
			//  DataSource ds = (DataSource) envContext.lookup("java:comp/env/jdbc/gaming");

			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		ResultSet resultSet = null;
		try {
			// Get Connection and Statement
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			String query = "SELECT * FROM STUDENT";
		//	String query = "SELECT * FROM EMPLOYEE";
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1) +"  "+ resultSet.getString(2)+""+resultSet.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try { if(null!=resultSet)resultSet.close();} catch (SQLException e) 
			{e.printStackTrace();}
			try { if(null!=statement)statement.close();} catch (SQLException e) 
			{e.printStackTrace();}
			try { if(null!=connection)connection.close();} catch (SQLException e) 
			{e.printStackTrace();}
		}
	}
}