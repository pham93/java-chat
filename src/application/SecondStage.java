package application;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SecondStage extends Stage {
	SecondStage(AnchorPane Root){
		System.out.println(this);
		if(Root.getScene() != null){
			this.setScene(Root.getScene());
		}
		else{
			
			this.setScene(new Scene(Root));
		}
		this.show();
	}
}
