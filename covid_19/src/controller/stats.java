package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import java.time.LocalDate;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class stats extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String api_url="https://data.gov.gr/api/v1/query/mdg_emvolio";
	 private final String auth_token="Token b7e25c4b73ff3d44ae24214261f3c47e35f61fab";
    
    public stats() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO get current date format--> do a request to the api -->get json response --> create session and save json to session-->forward to jsp
		String current_date=LocalDate.now().toString();
		String json_resp=this.api_request(current_date);  //replace with current date!
		//System.out.println(json_resp);  data fetch works fine!!
		String formated_resp="["+this.form_response(json_resp, current_date)+"]";
		HttpSession session=request.getSession();
		session.setAttribute("json",formated_resp);		
		request.getRequestDispatcher("Page.jsp").forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO get dateParam_list --> do a request to the api for every dateParam--> get all responses -->make a json and send it as response string
		String dates_list=(String) request.getParameter("dates");
		//System.out.println("THIS IS THE REQUEST::::"+dates_list);
		String [] dates_arr=dates_list.split(",");
		String ajax_resp="[";
		for (String date:dates_arr) {
			String resp=this.api_request(date);
			//System.out.println("THIS IS THE API RESPONSE:"+resp.substring(0, 300));
			String formed_resp=this.form_response(resp, date);
			ajax_resp+=formed_resp+",";
		}
		ajax_resp=ajax_resp.substring(0, ajax_resp.length()-1); //remove last char its a "," we dont want
		ajax_resp+="]";
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(ajax_resp);
	}
	
	private String api_request(String date) {
		String req=api_url+"?date_from="+date+"&date_to="+date;
		HttpClient client= HttpClient.newHttpClient();
		HttpRequest request= HttpRequest.newBuilder()
				.uri(URI.create(req))
				.header("Authorization", auth_token)
				.build();
		HttpResponse<String> response;
		while(true) {
			try {
				 response=client.send(request, BodyHandlers.ofString());
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
				continue;
			}
			break;
		}
		String json_resp=response.body();
		return json_resp;
	}
	
	private String form_response(String json_resp,String date) {
		String resp="{\"data\":"+json_resp+",\"date\":"+"\""+date+"\""+"}";		
		return resp;
	}

}
