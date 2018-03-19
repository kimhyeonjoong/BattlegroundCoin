package gmail.hotjoong.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
			
		crawiling("fpsapi");
		doGet(request, response);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//크롤링한 데이터의 사용을 위해 가공하자!
	//2018.03.19.12:03  << 실제 게임시
	//2018.03.18.15:03  << 수집 가능한 데이터 시간
	//21시간 차이가 납니다!
	//21+24=45시간 45시간이상이 현재와 차이 나면 하루전 게임!

	public double crawiling(String NickName) throws Exception{
		String opggURL = "https://pubg.op.gg/user/"+ NickName +"?server=kakao";
		Document doc = null;
		String reloadTime="";
		double timeValue=0;
		try {
			doc = Jsoup.connect(opggURL).get();
			Elements reloadTime_E = doc.select("div.matches-item__reload-time");
			Elements timeValue_E = doc.select("div.matches-item__time-value");

			for(int i=0 ; i<20 ; i++) {
				reloadTime = reloadTime_E.get(i).attr("data-ago-date").replaceAll("[^\\d]", "").substring(0, 12);

				System.out.println(reloadTime);
				if(checkReloadTime(reloadTime)) {  //24시간 이내의 게임인지 확인하여 시간 적립.
					timeValue += Double.parseDouble(timeValue_E.get(i).attr("data-game-length"));
					System.out.println("누적게임 시간 : "+timeValue+" (단위:초)");
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return timeValue;
	}

	//게임시간인지 현재시간에서 24시간 이내인지 확인함
	private boolean checkReloadTime(String reloadTime) throws Exception{
		//현재시간 today
		Date today = new Date (); 
		System.out.println("현재시간 : " + today);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm"); 
		//요청시간을 Date로 parsing 후 time가져오기
		Date playDate = dateFormat.parse(reloadTime);
		long playDateTime = playDate.getTime();
		//현재시간을 요청시간의 형태로 format 후 time 가져오기 
		today = dateFormat.parse(dateFormat.format(today)); 
		long curDateTime = today.getTime(); 
		//분으로 표현 
		long minute = (curDateTime - playDateTime) / 60000;
		
		
		System.out.println("게임시간 : " + playDate);
		System.out.println(minute+"분 차이");

		if(minute <= 2700)
			return true;
		else
			return false;
	}
	
}
