package org.edwith.webbe.cardmanager.dao;

import org.edwith.webbe.cardmanager.dto.BusinessCard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusinessCardManagerDao {
	private static String dburl = "jdbc:mysql://localhost:3306/connectdb?useSSL=false";
	private static String dbuser = "connectuser";
	private static String dbpassword = "connect123!@#";
	private static String driver = "com.mysql.jdbc.Driver";
	
    public List<BusinessCard> searchBusinessCard(String keyword){
    	List<BusinessCard> list = new ArrayList<>();
    	
    	try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
    	String sql = "SELECT name, phone, companyName FROM BusinessCard WHERE name LIKE ?";
    	
    	try (Connection conn = DriverManager.getConnection(dburl, dbuser, dbpassword);
    			PreparedStatement ps = conn.prepareStatement(sql)){
    		ps.setString(1, "%" + keyword + "%");
			try (ResultSet rs = ps.executeQuery()){
					while(rs.next()) {
					String na = rs.getString(1);
					String ph = rs.getString(2);
					String campany = rs.getString(3);
					BusinessCard businessCard = new BusinessCard(na, ph, campany);
					list.add(businessCard);
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return list;
    }

    public int addBusinessCard(BusinessCard businessCard){

		int insertCount = 0;
				
				Connection conn = null;
				PreparedStatement ps = null;
				
				try {
					Class.forName( driver );
					
					conn = DriverManager.getConnection(dburl, dbuser, dbpassword);
					
					String sql = "INSERT INTO BusinessCard (name, phone, companyName) VALUES ( ?, ?, ? )";
					
					ps = conn.prepareStatement(sql);
					
					ps.setString(1, businessCard.getName());
					ps.setString(2, businessCard.getPhone());
					ps.setString(3, businessCard.getCompanyName());
					
					insertCount = ps.executeUpdate();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if(ps != null) {
						try {
							ps.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if(conn != null) {
						try {
							conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			return insertCount;

    }
}
