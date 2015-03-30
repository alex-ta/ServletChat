package Chat_Servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class chatserver
 */



@WebServlet("/chatserver")
public class ChatServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ArrayList<ChatEntry> chathistory;  
	private static String seperator = "<#>";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatServer() {
        super();
        chathistory = new ArrayList<ChatEntry>();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GET");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		for(ChatEntry e: chathistory){
			out.write(e.toSendMsg(seperator));
		}
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST");
		String country = request.getLocale().getCountry();
		Calendar c = Calendar.getInstance(request.getLocale());  
		String time = ""+c.get(c.HOUR_OF_DAY)+":"+c.get(c.MINUTE)+":"+c.get(c.SECOND)+" on "+c.get(c.DAY_OF_MONTH)+" "+c.get(c.MONTH)+" "+c.get(c.YEAR);
		String nick = request.getParameter("nickname");
		String content = request.getParameter("content");
		chathistory.add(new ChatEntry(nick,content,time,country));
	}

}
