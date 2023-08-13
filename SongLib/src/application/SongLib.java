// NIVESH NAYEE : nn395
// MANAN PATEL : mp1885

package application;
	
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class SongLib extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("scene.fxml"));
			primaryStage.setTitle("SongLib");
			primaryStage.setResizable(false);
			AnchorPane root = (AnchorPane)loader.load();
			Controller listController = loader.getController();
			primaryStage.setOnCloseRequest(event -> 
	    	{
	    		try(FileWriter writer = new FileWriter("songList.txt"))
	    		{
	    			for(String list : listController.getItems())
	    			{
	    				writer.write(list + System.lineSeparator());
	    				 
	    			}
	    		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
    	);
			listController.start();
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}