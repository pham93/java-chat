package application;

import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChatController implements Initializable{
	@FXML
	TextArea chatTextField;
	@FXML
	TextField userInputTextField;
	@FXML
	MenuItem btnEnterUser;
	@FXML
	ListView<SecondStage> table;
	private Socket connection;
	private ObservableList<SecondStage> list = FXCollections.observableArrayList();
	//Client object
	private Client C = new Client(this);
	
	public Client getClientObj(){
		return C;
	}
	//All the stages that this object will interact with
	private Map<String, Stage> stages = new HashMap<>();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		

	}
	public void addStage(String name, Stage stage){
		stages.put(name, stage);
	}
	
	public Stage getStage(String name){
		if(stages.containsKey(name)){
			return stages.get(name);
		}else{
			return null;
		}
	}
	
	public void btnEnterOnClicked(ActionEvent event){
		sendMessage();
		
	}


	private void sendMessage(){
		String message = userInputTextField.getText();
		String whiteSpace = message.trim();
		if(whiteSpace.isEmpty()){
			userInputTextField.setText("");
			return;
		}
		//get message to server
		System.out.println(C);
		C.sendText(message);
		userInputTextField.setText("");
		System.out.println(this);
	}
	public void setText(String message){
		chatTextField.appendText(message + "\n");
		
	}
	public void ErrorBox(AnchorPane root){
		Scene scene = new Scene(root, 500, 500);
		stages.get("Error").setScene(scene);
		stages.get("Error").show();
	}
	public void userEnterText(KeyEvent event){
		if(event.getCode().equals(KeyCode.ENTER)){
			System.out.println("sfdf");
			sendMessage();
		}
	}

}
