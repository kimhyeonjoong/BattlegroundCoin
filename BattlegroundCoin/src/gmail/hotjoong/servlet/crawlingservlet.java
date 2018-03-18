package gmail.hotjoong.servlet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.regex.Matcher; 
import java.util.regex.Pattern;

@WebServlet("/crawlingservlet")
public class crawlingservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(crawiling("fpsapi"));
		doGet(request, response);
	}


	public String crawiling(String NickName) {
		String rst="";

		String opggURL = "https://pubg.op.gg/user/"+ NickName +"?server=kakao";
		Document doc = null;
		String reloadTime="";
		String timeValue="";
		try {
			doc = Jsoup.connect(opggURL).get();
			Elements reloadTime_E = doc.select("div.matches-item__reload-time");
			Elements timeValue_E = doc.select("div.matches-item__time-value");

			for(int i=0 ; i<20 ; i++) {
				reloadTime = removeCharExceptNumber(reloadTime_E.get(i).attr("data-ago-date")).substring(0,12);
				//24시간 이내의 게임인지 확인.
				checkReloadTime(reloadTime);
				
				timeValue = timeValue_E.get(i).attr("data-game-length");
			}
			
			System.out.println(reloadTime);
			System.out.println(timeValue);
			
			// 데이터가 비어있을 경우
			if(reloadTime.equals("")){ 
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return rst;
	}


	private boolean checkReloadTime(String reloadTime) {
		
			
		return true;
	}
	
	//문자열 중에 숫자만 거르는 메소드
	public static String removeCharExceptNumber(String str){ 
		   String numeral = "";
		   if( str == null ){  
		    numeral = null; 
		   }
		   else{
		       String patternStr = "\\d"; //숫자를 패턴으로 지정
		       Pattern pattern = Pattern.compile(patternStr); 
		       Matcher matcher = pattern.matcher(str); 
		       while(matcher.find()) { 
		        numeral += matcher.group(0); 
		       }
		   } 
		   return numeral;
	}


	

	
}
