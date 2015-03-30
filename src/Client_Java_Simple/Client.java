package Client_Java_Simple;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Client {
	private URL url;

	public Client(URL url){
		this.url = url;
	}
	
	public static void main(String[] args){
		Gui gui = new Gui(500,300);
		}
	
	public String getGet(){
		StringBuffer msg = new StringBuffer();
		
		try{
		URLConnection c = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
		
		String ms;
		while((ms = in.readLine()) != null){
		msg.append(ms);		
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return msg.toString()+"";
	}
	
	public InputStream sendPost(String nickname,String msg){
		
		UrlMsgPlain mg = new UrlMsgPlain();
		mg.add("nickname", nickname);
		mg.add("content", msg);
		
		try{
	    URLConnection con = url.openConnection();  
	    con.setDoInput(true);
	    con.setDoOutput(true);
	    con.setUseCaches(false);
	    
	    con.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
	    
	    DataOutputStream out = new DataOutputStream(con.getOutputStream()); 
	    out.writeBytes(mg.getMsg());
	    System.out.println(mg.getMsg());
	    out.flush();
	    out.close();
	    return con.getInputStream();
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
 