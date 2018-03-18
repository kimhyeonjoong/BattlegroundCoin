package gmail.hotjoong.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@WebServlet("/crawlingservlet")
public class crawlingservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		System.out.println(crawiling("fpsapi"));
		doGet(request, response);
		}catch(Exception e) {
			
		}
	}


	public String crawiling(String NickName) throws Exception{
		
		String rst="";

		String opggURL = "https://pubg.op.gg/user/"+ NickName +"?server=kakao";
		Document doc = null;
		String reloadTime="";
		double timeValue=0;
		try {
			doc = Jsoup.connect(opggURL).get();
			Elements reloadTime_E = doc.select("div.matches-item__reload-time");
			Elements timeValue_E = doc.select("div.matches-item__time-value");

			for(int i=0 ; i<20 ; i++) {
				reloadTime = reloadTime_E.get(i).text();
				if(checkReloadTime(reloadTime)) {  //24시간 이내의 게임인지 확인하여 시간 적립.
					timeValue += Integer.parseInt(timeValue_E.get(i).attr("data-game-length"));
				}
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

	//게임시간인지 현재시간에서 24시간 이내인지 확인함
	//이내이면 true 아니면 false 출력
	
	//여기까지 했음. 시간이 포함되어있으면 트루 없으면 펄스!
	private boolean checkReloadTime(String reloadTime) throws Exception{
		if(reloadTime.contains("시간")) {
			System.out.println("true출력");
			return true;
		}else
			return false;
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
