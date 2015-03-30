package Chat_Servlet;

public class ChatEntry {
	
	private String time;
	private String nickname;
	private String message;
	private String country;
	private boolean read;
	
	public ChatEntry(String nickname, String msg, String time,String country){
		this.nickname = nickname;
		this.time = time;
		this.message = msg;
		this.time = time;
		this.country = country;
		read = false;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public String toString(){
		return "Nickname: "+nickname+
			   "Country: "+country+"\n"+
			   "Time: "+time+"\n"+
			   "Message: "+message;
	}
	
	public String toSendMsg(String seperator){
	return nickname+seperator+country+seperator+time+seperator+message+seperator;
	}
}
