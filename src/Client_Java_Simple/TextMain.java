package Client_Java_Simple;

import java.net.URL;

public class TextMain {

	public static void main(String[] args){
		Client client = null;
		try{
			client = new Client(new URL("http://localhost:8080/WebApp_v3Chat/chatserver"));
		} catch (Exception e){
			e.printStackTrace();
		}
		client.sendPost("na", "super");
		System.out.println(client.getGet());
		
		
	}
	
}
