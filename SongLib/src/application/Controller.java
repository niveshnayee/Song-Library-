// NIVESH NAYEE : nn395
// MANAN PATEL : mp1885

package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListCell;
import javafx.util.Callback;

public class Controller {

    @FXML
    private Button btnadd;

    @FXML
    private Button btndel;

    @FXML
    private Button btnedt;
    
    @FXML
    private VBox vb;

    @FXML
    private ListView<String> listView;
    
    private ObservableList<String> data = FXCollections.observableArrayList();
    
    @FXML
    private TextField sn;
    @FXML
    private TextField at;
    @FXML
    private TextField al;
    @FXML
    private TextField ye;
    
    @FXML
    private Label sinfo;
    
    @FXML
    private Label listsn;

    @FXML
    private TextField tfalb;

    @FXML
    private TextField tfart;

    @FXML
    private TextField tfsn;

    @FXML
    private TextField tfy;
    
    private int selectedIndex = -1;
    private String checkEdit = "";
    
    // START
    void start()
    {
    	listView.getItems();
    	vb.setVisible(false);
		sinfo.setVisible(false);
    	
    	File file = new File("songList.txt");
    	if(file.exists())
    	{
    		try(BufferedReader reader = new BufferedReader(new FileReader(file)))
    		{
    			String list;
    			while( (list = reader.readLine()) != null )
    			{
    				data.add(list);
    			}
    			addList(data);
    			if(data.size() > 0) 
    			{
    				listView.getSelectionModel().select(0);
        			vb.setVisible(true);
            		sinfo.setVisible(true);
            		String str = data.get(0);

            		
            		String[] info = str.split("\\|");
            		
            		sn.setText(info[0]);
            		at.setText(info[1]);
            		if(info.length >2 && !info[2].isEmpty())
            			al.setText(info[2]);
            		else al.clear();
	        		
	        		if(info.length > 3)
	        			ye.setText(info[3]);
	        		else ye.clear();
            		
            		checkEdit = info[0] + "|" + info[1];
    			}
    			
    			
    		} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	listView.setOnMouseClicked(event -> {
    		sn.clear();
        	at.clear();
        	al.clear();
        	ye.clear();
    		selectedIndex = listView.getSelectionModel().getSelectedIndex();
        	if(selectedIndex > -1)
        	{
        		//vb.visibleProperty().bind(showVBox);
        		
        		vb.setVisible(true);
        		sinfo.setVisible(true);
        		String str = data.get(selectedIndex);

        		
        		String[] info = str.split("\\|");
        		
        		sn.setText(info[0]);
        		at.setText(info[1]);
        		if(info.length >2 && !info[2].isEmpty())
        			al.setText(info[2]);
        		if(info.length > 3)
        			ye.setText(info[3]);
        		
        		checkEdit = info[0] + "|" + info[1];

        	}
    	});
    	
    }
    
    // OBSERVABLE LIST 
    public ObservableList<String> getItems() {
        return data;
    }
    
    // ADDING TO LISTVIEW
    private void addList(ObservableList<String> list)
    {
    	listView.setItems(list);
		
		listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
		    @Override
		    public ListCell<String> call(ListView<String> param) {
		        return new ListCell<String>() {
		            @Override
		            protected void updateItem(String item, boolean empty) {
		                super.updateItem(item, empty);
		                if (!empty) 
		                {
		                	int index = item.indexOf("|");
		                    setText( item.substring(0, index) + "          " + item.substring(index+1, item.indexOf("|", index +1)) );
		                } else {
		                    setText(null);
		                }
		            }
		        };
		    }
		});
    }
    
    private Boolean isValid(String song, String artist, String album)
    {
    	if(song.indexOf("|") != -1 || artist.indexOf("|") != -1 || album.indexOf("|") != -1 )
    		return false;
		return true;
    }
    
    

    @FXML // ADD EVENT
    void addName(MouseEvent event) {
    	
    	if(tfsn.getText().trim() != "" && tfart.getText().trim() != "")
    	{	
    		String dup = tfsn.getText().trim() + "|" + tfart.getText().trim();
    		if(isValid(tfsn.getText(), tfart.getText(), tfalb.getText()) && !data.stream().map(String::toLowerCase).anyMatch(str -> str.substring(0, str.indexOf("|", str.indexOf("|") + 1)).equals(dup.toLowerCase())) )
    		{
    			try {
    				
	    			if(tfy.getText() == "" || Integer.parseInt(tfy.getText()) > 0)
	    			{
	    				String msg = "Add :\n" + "Song :  " + tfsn.getText() + "\n" +"Artist :  " + tfart.getText() + "\n" + "Album :  " + tfalb.getText() + "\n" +"Year :  " + tfy.getText() ;
	        	    	
	        	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	        	    	alert.setContentText(msg);
	        	    	final Optional<ButtonType> result = alert.showAndWait();

	        	    	if (result.get() == ButtonType.OK) 
	        	    	{
	        	        	data.add(tfsn.getText().trim() + "|" + tfart.getText().trim() + "|" + tfalb.getText() + "|" + tfy.getText());
	        	        	Collections.sort(data, String.CASE_INSENSITIVE_ORDER);
	        	        	selectedIndex = data.indexOf(tfsn.getText().trim() + "|" + tfart.getText().trim() + "|" + tfalb.getText() + "|" + tfy.getText());
	        	        	listView.getSelectionModel().select(selectedIndex);
	        	        	vb.setVisible(true);
	                		sinfo.setVisible(true);
	                		String str = data.get(selectedIndex);

	                		
	                		String[] info = str.split("\\|");
	                		
	                		sn.setText(info[0]);
	                		at.setText(info[1]);
	                		if(info.length >2 && !info[2].isEmpty())
	                			al.setText(info[2]);
	                		else al.clear();
	    	        		
	    	        		if(info.length > 3)
	    	        			ye.setText(info[3]);
	    	        		else ye.clear();
	                		
	                		checkEdit = info[0] + "|" + info[1];
	                		
	                		tfsn.clear();
	        	        	tfart.clear();
	        	        	tfalb.clear();
	        	        	tfy.clear();
	        	    	}
	    			}

    			}
    			catch(NumberFormatException e)
    			{
    				Alert errorAlert = new Alert(AlertType.ERROR);
            		errorAlert.setHeaderText("Input not Valid");
            		errorAlert.setContentText("Year field should be positive Integers");
            		errorAlert.showAndWait();
            		
            		tfy.clear();
    			}
        	
    		}
    		else
    		{
    			Alert errorAlert = new Alert(AlertType.ERROR);
        		errorAlert.setHeaderText("Input not valid");
        		errorAlert.setContentText("Song and Artist name already exists or Can't use '|' ");
        		errorAlert.showAndWait();
        		
        		tfsn.clear();
            	tfart.clear();
            	tfalb.clear();
            	tfy.clear();
    		}
    		
    	}
    	else
    	{
    		if(tfalb.getText() != "" || tfy.getText() != "")
    		{
    			Alert fail= new Alert(AlertType.ERROR);
                fail.setHeaderText("Error");
                fail.setContentText("Song and Artist name must be entered");
                fail.showAndWait();
    		}
    	}

    	
    }

    @FXML // EDIT EVENT 
    void editName(MouseEvent event) {
    	
    	if(sn.getText().trim() != "" && at.getText().trim() != "")
    	{
    		String dup = sn.getText().trim() + "|" + at.getText().trim();
    		if( (isValid(sn.getText(), at.getText(), al.getText()) ) && ( checkEdit.equals(dup) || !data.stream().map(String::toLowerCase).anyMatch(str -> str.substring(0, str.indexOf("|", str.indexOf("|") + 1)).equals(dup.toLowerCase())) ) )
    		{
    			try {
    				
	    			if(ye.getText() == "" || Integer.parseInt(ye.getText()) > 0)
	    			{
	    				String msg = "Change to :\n" + "Song :  " + sn.getText() + "\n" +"Artist :  " + at.getText() + "\n" + "Album :  " + al.getText() + "\n" +"Year :  " + ye.getText() + "?" ;
	        	    	
	        	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	        	    	alert.setContentText(msg);
	        	    	final Optional<ButtonType> result = alert.showAndWait();

	        	    	if (result.get() == ButtonType.OK) 
	        	    	{
	        	        	data.set(selectedIndex, sn.getText().trim() + "|" + at.getText().trim() + "|" + al.getText() + "|" + ye.getText());
	        	        	Collections.sort(data, String.CASE_INSENSITIVE_ORDER);

	        	    	}
	    			}
    			}
    			catch(NumberFormatException e)
    			{
    				Alert errorAlert = new Alert(AlertType.ERROR);
            		errorAlert.setHeaderText("Input not Valid");
            		errorAlert.setContentText("Year field should be positive Integers");
            		errorAlert.showAndWait();
            		

            		String str = data.get(listView.getSelectionModel().getSelectedIndex());

            		
            		String[] info = str.split("\\|");
            		
            		sn.setText(info[0]);
            		at.setText(info[1]);
            		if(info.length >2 && !info[2].isEmpty())
            			al.setText(info[2]);
            		else al.clear();
            		
            		if(info.length > 3)
            			ye.setText(info[3]);
            		else ye.clear();
    			}
    			
    			
    		}
    		else
    		{
    			Alert errorAlert = new Alert(AlertType.ERROR);
        		errorAlert.setHeaderText("Input not valid");
        		errorAlert.setContentText("Song and Artist name already exists or Can't use '|' ");
        		errorAlert.showAndWait();
        		
        		String str = data.get(listView.getSelectionModel().getSelectedIndex());

        		
        		String[] info = str.split("\\|");
        		
        		sn.setText(info[0]);
        		at.setText(info[1]);
        		if(info.length >2 && !info[2].isEmpty())
        			al.setText(info[2]);
        		else al.clear();
        		
        		if(info.length > 3)
        			ye.setText(info[3]);
        		else ye.clear();
    		}
    	}
    	else
    	{
    		Alert fail= new Alert(AlertType.ERROR);
            fail.setHeaderText("Error");
            fail.setContentText("Song and Artist name must be entered");
            fail.showAndWait();	
            
            String str = data.get(listView.getSelectionModel().getSelectedIndex());

    		
    		String[] info = str.split("\\|");
    		
    		sn.setText(info[0]);
    		at.setText(info[1]);
    		if(info.length >2 && !info[2].isEmpty())
    			al.setText(info[2]);
    		else al.clear();
    		
    		if(info.length > 3)
    			ye.setText(info[3]);
    		else ye.clear();
    	}
		
    	 

    }
    
    
    
    @FXML // REMOVE EVENT 
    void removeName(MouseEvent event) {
    	selectedIndex = listView.getSelectionModel().getSelectedIndex();
    	if(selectedIndex > -1)
    	{
    		String msg = "Remove :\n" + "Song :  " + sn.getText() + "\n" +"Artist :  " + at.getText() + "\n" + "Album :  " + al.getText() + "\n" +"Year :  " + ye.getText() + "?" ;
	    	
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setContentText(msg);
	    	final Optional<ButtonType> result = alert.showAndWait();

	    	if (result.get() == ButtonType.OK) 
	    	{
	        	data.remove(selectedIndex);
	        	if(selectedIndex != data.size() && selectedIndex != 0)
	        		listView.getSelectionModel().selectNext();
	        	else
	        		if(selectedIndex > 0) selectedIndex -= 1;
	        	
	        	if(selectedIndex != data.size())
	        	{
	        		String str = data.get(selectedIndex);

	        		
	        		String[] info = str.split("\\|");
	        		
	        		sn.setText(info[0]);
	        		at.setText(info[1]);
	        		if(info.length >2 && !info[2].isEmpty())
	        			al.setText(info[2]);
	        		else al.clear();
	        		
	        		if(info.length > 3)
	        			ye.setText(info[3]);
	        		else ye.clear();
	        		
	        		checkEdit = info[0] + "|" + info[1];
	        	}
	        	else
	        	{
	        		vb.setVisible(false);
	            	sinfo.setVisible(false); 
	        	}
	    	}
        	
    	}	  	
    }

}