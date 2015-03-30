package Client_Java_Simple;

import java.net.URLEncoder;

public class UrlMsgPlain {
	private StringBuffer msg;
	
	public UrlMsgPlain(){
		msg = new StringBuffer();
	}
	
	public void add(String key, String value){
		if(msg.length()>1)
			msg.append("&");
		msg.append(URLEncoder.encode(key)+"="+URLEncoder.encode(value));
	
	}

	public String getMsg(){
		return msg.toString();
	}
	
}
