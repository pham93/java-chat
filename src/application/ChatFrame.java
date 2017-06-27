package application;

import java.io.IOException;
import java.util.Observable;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class ChatFrame extends Application{
	private String username;
	//just letting you know, I got this to work 
	@Override
	public void start(Stage stage) throws Exception {

		FXMLLoader mainLoader = new FXMLLoader(ChatFrame.class.getResource("/fxml/Frame.fxml"));
		Parent root = mainLoader.load();
		ChatController x = mainLoader.getController();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DefineUser.fxml"));
		AnchorPane userRoot = loader.load();
		EnterUser userNameController = loader.getController();
		userNameController.getController(x);
		System.out.println();
		
		x.btnEnterUser.setOnAction((event)->{
			try{
				new SecondStage(userRoot);
			}catch(Exception e){
				System.out.println("FXML missing or not loaded");
				e.printStackTrace();
			}
			
		});
		//FILE MENU OPTION
//		x.btnEnterUser.setOnAction(new EventHandler<ActionEvent>(){
//			@Override
//			public void handle(ActionEvent event) {
//				try{
//					new SecondStage(userRoot);
//				}catch(Exception e){
//					System.out.println("FXML missing or not loaded");
//					e.printStackTrace();
//				}
//			}
//		});
		x.addStage("Error", stage);
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		
		
		Dialog connectBox = new Dialog();
		//Setting the title, buttons and textfield
		connectBox.setTitle("Connect?");
		connectBox.setHeaderText("Enter username and connect");
		ButtonType button1 = new ButtonType("Connect");
		ButtonType btnCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		//Adding buttons
		connectBox.getDialogPane().getButtonTypes().setAll(button1, btnCancel);
		
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		
		TextField userField = new TextField();
		userField.setPromptText("username: ");
		grid.add(new Label("Username: "), 0, 0);
		grid.add(userField, 1, 0);
		Node disableConnect = connectBox.getDialogPane().lookupButton(button1);
		disableConnect.setDisable(true);
		userField.textProperty().addListener((observable, oldValue, newValue) ->{
			disableConnect.setDisable(newValue.trim().isEmpty());
		});
		connectBox.getDialogPane().setContent(grid);
		connectBox.showAndWait();
		
		if(connectBox.getResult() == button1){
			username = userField.getText();
			x.getClientObj().setUsername(username);
			x.getClientObj().runClient();
			if(x.getClientObj().serverOn())
				stage.show();
		}
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent even) {
				try {
					System.out.println("closing");
					x.getClientObj().getConnection().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
		});
	}
	public static void main(String[] args){
		launch(args);
	}

}
