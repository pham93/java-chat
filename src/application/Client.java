package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


public class Client {
	
	private final Integer PORT = 444;
	private final String HOST = "localhost";
	private String username = "Anon" + (int)(Math.random() * 1000);
	
	Scanner input;
	PrintWriter output;
	private ClientChat userChat;
	private Socket connection;
	private ChatController myController;
	private boolean serverState;
	
	public Client(ChatController c){
		myController = c;
	}
	public String returnName(){
		return username;
	}
	public void setUsername(String name){
		this.username = name;
	}
	public boolean serverOn(){
		return serverState;
	}
	
	public void runClient(){
		try {
			connection = new Socket(HOST, PORT);
			System.out.println("Connected " + HOST);
			serverState = true;
			//sending info to Server
			input = new Scanner(connection.getInputStream());
			output = new PrintWriter(connection.getOutputStream());
			output.println(username);
			output.flush();
			
			userChat = new ClientChat(connection, myController, username);
			Thread X = new Thread(userChat);
			X.start();

		}
		catch (IOException e) {
			serverState = false;
			Alert alert = new Alert(AlertType.ERROR, "SEVER IS DOWN", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
				alert.close();
			}
		}
	}
	/**
	 * Client sending text to server
	 * @param mess
	 */
	public void sendText(String mess){
		userChat.send(mess);
	}
	public Socket getConnection(){
		return connection;
	}

}
