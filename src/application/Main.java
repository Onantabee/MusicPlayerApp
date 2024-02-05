package application;
	
//import com.jfoenix.controls.JFXDecorator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
//import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("musicPlayer.fxml"));
            stage.setTitle("audibleNG");
            stage.setMinWidth(1000);
            stage.setMinHeight(580);
            stage.setScene(new Scene(root));
            stage.show();
            
            stage.setOnCloseRequest(event -> onCloseRequest(stage));
            
        }catch (Exception e){
            System.out.println("Error : "+e);
            e.printStackTrace();
        }
    }
    
    private void onCloseRequest(Stage stage) {
    	Platform.exit();
    	System.exit(0);
	}

    public static void main(String[] args) {
        launch();
    }
}