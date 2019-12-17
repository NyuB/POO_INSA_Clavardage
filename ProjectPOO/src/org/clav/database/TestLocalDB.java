package org.clav.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestLocalDB {
	
	public static void main(String[] args) {
		
		LocalStorage db = new LocalStorage() ;
		ResultSet rs = db.request("select * from test") ;
		try {
			while(rs.next()) {
				System.out.println(rs.getString(1)) ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
