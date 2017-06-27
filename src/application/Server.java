package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.application.Platform;

public class Server {
	private final Integer PORT = 444;
	private Socket connection; 
	private ServerSocket server;
	private List<Socket> Socks = new ArrayList<>();
	private List<String> users = new ArrayList<>();
	private String username;
	
	public void startServer(){
		try{
			server = new ServerSocket(PORT);
			System.out.println("Wating for connection....");
			while(true){
				connection = server.accept();
				//add socket to list
				Socks.add(connection);
				System.out.println("USER " + connection.getInetAddress().getHostAddress() + " has connected");
				
				addUserName(connection);
				ServerChat CHAT = new ServerChat(connection, username, Socks, users);
				Thread X = new Thread(CHAT);
				X.start();
				
			}
		}
		catch(IOException e){
			System.out.println("Error setting up Server");
			e.printStackTrace();
		}
	}
	//ADD USERS
	public void addUserName(Socket con){
		//get input stream from client
		try{
			Scanner input = new Scanner(con.getInputStream());
			username = input.nextLine();
			users.add(username);
			//Update all clients
			System.out.println(Socks.size());
			for(Socket c: Socks){
				System.out.println(c);
				Socket TEMP = c;
				System.out.println(TEMP);
				PrintWriter output = new PrintWriter(TEMP.getOutputStream());
				output.println(users.toString());
				
				output.flush();
			}
		}
		catch(IOException e){
			System.out.println("NO DATA");
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		Server s = new Server();
		s.startServer();
	}
}
