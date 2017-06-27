package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ServerChat implements Runnable {

	private Socket connection;
	private List<Socket> Socks;
	private Scanner input;
	private PrintWriter output;
	private List<String> users;
	private String username;


	public ServerChat(Socket connection, String user, List<Socket> Socks, List<String> users) {
		this.connection = connection;
		this.Socks = Socks;
		this.users = users;
		this.username = user;
	}
	
	private void checkAvailible(){
		if(!connection.isConnected()){
			System.out.println("user dis");
			for(Socket c: Socks){
				if(c == connection){
					System.out.println(connection.getInetAddress().getHostAddress() + " is disconnected");
					Socks.remove(c);
				}
			}
			for(Socket c: Socks){
				try {
					Socket TEMP = c;
					PrintWriter Out = new PrintWriter(TEMP.getOutputStream());
					Out.println();
					Out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public void run() {
		try{
			input = new Scanner(connection.getInputStream());
			output = new PrintWriter(connection.getOutputStream());
			//SENDING USING NAME TO SERVER

			try{
				while(true){
					//If someone get disconnect
					checkAvailible();
					System.out.println("::::::");
					if(!input.hasNextLine()){
						return;
					}
					// READ in information from client
					String message = input.nextLine();
					System.out.println("Client: " + message);
					//Broadcast message to all clients
					for(Socket c: Socks){
						Socket TEMP = c;
						PrintWriter Out = new PrintWriter(TEMP.getOutputStream());
						Out.println(message);
						Out.flush();
					}
					
				}
			}finally{
				connection.close();
				if(connection.isClosed()){
					System.out.println("Closing Connnnnnection");
					System.out.println(username + " disconnected");
					output.println(username + " is disconnected");
					output.flush();
					users.remove(username);
					Socks.remove(connection);
				}
			}
		}
		catch(IOException e){
			System.out.println("ERROR");
			e.printStackTrace();
		}
	}

}
