package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EnterUser implements Initializable, ControllerCom{
	private ChatController chatController;
	@FXML
	TextField UsernameTextField;
	@FXML
	Node root;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	public void btnEnterUsername(ActionEvent event){
		String username = UsernameTextField.getText();
		
		//setting C to Client Object address
		chatController.getClientObj().setUsername(username);
		root.getScene().getWindow().hide();
		
	}
	@Override
	public void getController(ChatController ja) {
		chatController = ja;
	}
	@Override
	public void getController(EnterUser ja) {
		// TODO Auto-generated method stub
		
	}
	

}
