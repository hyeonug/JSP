package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletContext;

public class JDBConnect {
   
    public Connection con;
    public PreparedStatement psmt;
    
    public JDBConnect() {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");

            
            String url = "jdbc:mysql://localhost:3306/musthave"; 
            String id = "root";
            String pwd = "1234";

           
            con = DriverManager.getConnection(url, id, pwd);

            System.out.println("DB 연결 성공(기본 생성자)");
        } catch (Exception e) {
            System.out.println("DB 연결 중 예외 발생");
            e.printStackTrace();
        }
    }

    
    public void close() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("DB 연결 해제");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public JDBConnect(String driver, String url, String id, String pwd) {
    	try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, id, pwd);
			System.out.println("DB 연결 성공(인수 생성자1");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("DB 연결 중 예외 발생");
		}
    }
    public JDBConnect(ServletContext application) {
    	try {
			String driver = application.getInitParameter("MySQLDriver");
					Class.forName(driver);
			
			String url = application.getInitParameter("MySQLURL");
					String id = application.getInitParameter("MySQLid");
					String pwd = application.getInitParameter("MySQLPwd");
					con = DriverManager.getConnection(url,id,pwd);
					
					System.out.println("DB 연결 성공(인수 생성자2");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace()
			;
		}
    }
}