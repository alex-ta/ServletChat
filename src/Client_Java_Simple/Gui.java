package Client_Java_Simple;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.Calendar;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Gui extends JFrame{
	
	
	//
	//Handle Scrollevent
	//Handle Save Event
	//Handle Upate Event
	//
	
	private Container pane = getContentPane();
	private JLabel ulabel = new JLabel("Nickname: ");
	private JTextField user = new InputUser();
	private JLabel input = new InputText();
	private JTextArea output = new OutputText();
	private JButton send = new SendButton();
	private String greetings;
	private Client client;
	private boolean running;
	
	public Gui(int width,int height){
		super();
		this.setLocationRelativeTo(null);
		this.setSize(width, height);
		this.setTitle("Chat Client");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent e)
		{
			super.windowClosing(e);
			running = false;
		}});
		
		
		Container c = new Container();
		c.setLayout(new FlowLayout());
		c.add(ulabel);
		c.add(user);
	
		JScrollPane c2 = new JScrollPane(output, 
	    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
	    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		JScrollPane c3 = new JScrollPane(input, 
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		Container c4 = new Container();
		c4.setLayout(new GridLayout(2,1));
		c4.add(c3);
		c4.add(c2);
		
		pane.add(c,BorderLayout.NORTH);
		pane.add(c4,BorderLayout.CENTER);
		pane.add(send,BorderLayout.PAGE_END);

		this.setVisible(true);
		try{
			client = new Client(new URL("http://localhost:8080/WebApp_v3Chat/chatserver"));
		} catch (Exception e){
			e.printStackTrace();
		}
		running = true;
		new BgThread(this).start();
	
		send.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String msg = "";
				String name ="";
				if(output.getText() != null)
					msg = output.getText();
				output.setText("");
				if(user.getText() != null)
					name = user.getText();
				if(user.isEditable())
					user.setEditable(false);
				sendMsg(name,msg);
			}
		});
	}
	private class SendButton extends JButton{

		public SendButton(){
			super();
			this.setText("Send"); 
		}
	}
	
	private class OutputText extends JTextArea{
		public OutputText(){
			super();
		}
	}
	
	private class InputText extends JLabel{
		public InputText(){
			super();
			Calendar t = Calendar.getInstance();
			String hour =""+t.get(Calendar.HOUR_OF_DAY);
			if(hour.length()<2)
				hour = "0"+hour;
			String minuite =""+t.get(Calendar.MINUTE);
			if(minuite.length()<2)
				minuite = "0"+minuite;
			greetings = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;<font size='7'> Welcom </font> it is now "+hour+":"+minuite+"<br />";
			this.setHorizontalAlignment(SwingConstants.CENTER);
			//setText("<html>"+greetings+"</html>");
		}
	}
	
	private class InputUser extends JTextField{
		public InputUser(){
			super();
			this.setColumns(20);
		}
	}
	
	private void setText(String text){
		input.setText(text);
		input.repaint();
	}
	
	public void addMsgs(String[] name,String[] msg, String[] date, String[] country){	
		StringBuffer content = new StringBuffer();
		content.append(greetings);
		
		for(int i=0; i<name.length; i++){
			content.append("<br /><font size='5'>"+name[i]+"</font><font color='gray'> &nbsp;&nbsp;["+country[i]+
					       "] &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					       +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					       + "&nbsp;&nbsp;&nbsp;</font>"+"<font size='2'>Time: "+date[i]+"</font>");
			content.append("<br />"+msg[i].replace("\n", "<br />"));
		}
		setText("<html>"+content.toString()+"</html>");
	}
	
	
	private void sendMsg(String nickname, String msg){
			client.sendPost(nickname, msg);
	}
	
	private class BgThread extends Thread{
		private String seperator ="<#>";
		private int colums = 4;
		private Gui gui;
		
		public BgThread(Gui gui){
			this.gui =gui;
		}
		
		@Override
		public void run() {
			while(running){
			try{
				String data = client.getGet();
				if(data.length()>seperator.length())
				data = data.substring(0, data.length()-seperator.length());
				System.out.println("data "+data);
				
				String[] datas = data.split(seperator);
				int times = datas.length/colums;
				String[] name = new String[times];
				String[] msg = new String[times];
				String[] country = new String[times];
				String[] time = new String[times];
				for(int i =0; i<times; i++){
					name[i] = datas[i*colums];
					country[i] = datas[i*colums+1];
					time[i] = datas[i*colums+2];	
					msg[i] = datas[i*colums+3];				
				}
				
				gui.addMsgs(name,msg,time,country);
				Thread.sleep(1000);
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		 }

	}
}
