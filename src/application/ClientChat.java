package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;

public class ClientChat implements Runnable{

	private Socket connection;
	private Scanner input;
	private PrintWriter output;
	private ChatController myCon;
	private String username;
	public ClientChat(Socket connection, ChatController myController, String username) {
		this.connection = connection;
		this.myCon = myController;
		this.username = username;
	}
	public void setUsername(String name){
		username = name;
	}
	public Socket getConnection(){
		return connection;
	}
	@Override
	public void run() {
		
		
		System.out.println("hellotherethreed");
		try{
			try{
                input = new Scanner(connection.getInputStream());
                output = new PrintWriter(connection.getOutputStream());
                while(true){
                	recieve();
                }
			}finally{
				connection.close();
			}
		}catch(IOException e){
			System.out.println("Client error");
			e.printStackTrace();
		}
	}
	private void recieve(){
		
		//getting information from server
		if(input.hasNext()){
			String mess = input.nextLine();
			System.out.println("gf");
			Platform.runLater(new Runnable(){

				@Override
				public void run() {
					System.out.println("From Server:" + mess);
					myCon.chatTextField.appendText(mess + "\n");
					
				}
				
			});
//			
		}
		
	}
	public void send(String message){
		//sending info
		System.out.println("System: " + message);
		output.println(username + ": " +  message);
		output.flush();
	}



}
