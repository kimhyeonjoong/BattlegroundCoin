package gmail.hotjoong.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

public class DAO {
	private String url="jdbc:mysql://localhost:3306/bagcoin_db";
	private String db_Id = "hotjoong";
	private String db_Pw = "dkffka";
	private String className = "com.mysql.jdbc.Driver";
	private Connection conn;
	private ResultSet rs;
	private PreparedStatement pst;
	private String sql;
	private int cnt;
	private String moveURL;
	
	public void getConnection() throws Exception{
		Class.forName(className);
		conn = DriverManager.getConnection(url, db_Id, db_Pw);
	}
	
	public void close() throws Exception {
		if(rs != null) rs.close();
		if(conn != null) conn.close();
		if(pst != null) pst.close();
	}
	
//	public AdminDTO admin_login(String admin_id)throws Exception{
//		getConnection();
//		
//		sql = "select * from yaguyo_admin where admin_id=?";
//		
//		pst = conn.prepareStatement(sql);
//		
//		pst.setString(1, admin_id);
//		
//		ResultSet rs = pst.executeQuery();
//		
//		if(rs.next()){
//			admindto = new AdminDTO(rs.getString(1), rs.getString(2));
//		}
//		
//		close();
//		
//		
//		return admindto;
//	}
	
	public String update_wellet(String nickname,int playTime) throws Exception{
		getConnection();
		
		sql = "insert into user_wellet values(?,?)";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, nickname);
		pst.setInt(2, playTime);
		
		
		int cnt = pst.executeUpdate();
		
		if(cnt>0) {
			System.out.println("성공");
			moveURL = "mywallet.jsp";
		}else {
			System.out.println("실패");
			moveURL = "index.html";
		}
		close();
		
		return moveURL;
	}
	
}
