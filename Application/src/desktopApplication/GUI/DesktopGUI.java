package desktopApplication.GUI;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.scene.text.*;
import net.thegreshams.firebase4j.service.Firebase;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.error.*;

public class DesktopGUI extends Application {
	GridPane root;
	Scene scene;
	GridPane loginRoot;
	Scene loginScene;
	String hospitalName = "Hospital Name";
	static String hospitalId = "Hospital2";
	static int totBeds, emptyBeds;
	static Firebase firebase = null;
	
	public static void main(String[] args) throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException {
		
				
		launch(args);
	}
	
	@Override
	public void start(Stage appStage) throws Exception{
		
		
		root = new GridPane();
		
		Text hName = new Text(hospitalName);
		hName.setFont(Font.font("Helvetica",20));
		Text emptyBedsText = new Text("Available Beds:");
		emptyBedsText.setFont(Font.font("Helvetica",14));
		Spinner<Integer> emptyBedsSpinner = new Spinner<Integer>(new IntegerSpinnerValueFactory(0,1000, emptyBeds));
		emptyBedsSpinner.setEditable(true);
		emptyBedsSpinner.setMaxWidth(85);
		Text totBedsText = new Text("Total Beds:");
		totBedsText.setFont(Font.font("Helvetica",14));
		Spinner<Integer> totBedsSpinner = new Spinner<Integer>(new IntegerSpinnerValueFactory(0,1000, totBeds));
		totBedsSpinner.setEditable(true);
		totBedsSpinner.setMaxWidth(85);
		
		Button applyButton = new Button("Apply");
		applyButton.setFont(Font.font("Helvetica",11));
		Button saveButton = new Button("Save and Quit");
		saveButton.setFont(Font.font("Helvetica",11));
		Button closeButton = new Button("Close");
		closeButton.setFont(Font.font("Helvetica",11));
		ButtonBar buttons = new ButtonBar();
		buttons.getButtons().addAll(applyButton,saveButton,closeButton);
		buttons.setButtonMinWidth(90);
		
		HBox emptyBedsBox = new HBox();
		emptyBedsBox.getChildren().addAll(emptyBedsText,emptyBedsSpinner);
		emptyBedsBox.setSpacing(5);
		HBox totBedsBox = new HBox();
		totBedsBox.getChildren().addAll(totBedsText,totBedsSpinner);
		totBedsBox.setSpacing(5);
		
		// Column constraints
	    ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(25);
	    ColumnConstraints column2 = new ColumnConstraints();
	    column2.setPercentWidth(25);
	    ColumnConstraints column3 = new ColumnConstraints();
	    column3.setPercentWidth(25);
	    ColumnConstraints column4 = new ColumnConstraints();
	    column4.setPercentWidth(25);
	    root.getColumnConstraints().addAll(column1,column2,column3,column4); // each get 50% of width
	    
	    // Row constraints
	    RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(16);
	    RowConstraints row2 = new RowConstraints();
	    row2.setPercentHeight(24);
	    RowConstraints row3 = new RowConstraints();
	    row3.setPercentHeight(18);
	    RowConstraints row4 = new RowConstraints();
	    row4.setPercentHeight(16);
	    RowConstraints row5 = new RowConstraints();
	    row5.setPercentHeight(26);
	    root.getRowConstraints().addAll(row1,row2,row3,row4,row5);
	    
	    // Alignments
	    GridPane.setHalignment(buttons, HPos.RIGHT);
	    GridPane.setValignment(buttons, VPos.BOTTOM);
	    GridPane.setValignment(hName, VPos.TOP);
	    root.setPadding(new Insets(0,5,5,5));
	    root.add(hName, 0, 0);
	    root.add(emptyBedsBox, 0, 2, 2, 1);
	    root.add(totBedsBox, 2, 2, 2, 1);
		root.add(buttons, 3, 4);
		
		scene = new Scene(root,400,100);
		
		//login scene
		
		loginRoot = new GridPane();
		
	    ColumnConstraints loginColumn1 = new ColumnConstraints();
	    loginColumn1.setPercentWidth(50);
	    ColumnConstraints loginColumn2 = new ColumnConstraints();
	    loginColumn2.setPercentWidth(50);
	    loginRoot.getColumnConstraints().addAll(loginColumn1,loginColumn2);
	    
		RowConstraints loginRow1 = new RowConstraints();
	    loginRow1.setPercentHeight(40);
	    RowConstraints loginRow2 = new RowConstraints();
	    loginRow2.setPercentHeight(20);
	    RowConstraints loginRow3 = new RowConstraints();
	    loginRow3.setPercentHeight(40);
	    loginRoot.getRowConstraints().addAll(loginRow1,loginRow2,loginRow3);
	    
	    
	    
		Text hIdRequest = new Text("Enter your hospital ID");
		hIdRequest.setFont(Font.font("Helvetica",20));
		TextField hId = new TextField();
		hId.setMaxWidth(150);
		loginRoot.add(hIdRequest, 0, 0);
		loginRoot.add(hId, 1, 0);
		
		Button loginButton = new Button("Login");
		loginButton.setFont(Font.font("Helvetica",11));
		loginRoot.add(loginButton, 1, 2);
		
		loginRoot.setPadding(new Insets(5));
		
		
		loginScene = new Scene(loginRoot,400,100);
		appStage.setScene(loginScene);
		appStage.setTitle("Ghana need an ambulance");
		appStage.show();
		
		
		applyButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				String putUrl = "/emptyBeds";
				String avaBedsSpinnerString = emptyBedsSpinner.getValue().toString();
				System.out.println(avaBedsSpinnerString);
				try {
					firebase.put(putUrl, avaBedsSpinnerString);
				} catch (UnsupportedEncodingException | FirebaseException e1) {
					e1.printStackTrace();
				}
				putUrl = "/totBeds";
				String totBedsSpinnerString = totBedsSpinner.getValue().toString();
				System.out.println(totBedsSpinnerString);
				try {
					firebase.put(putUrl, totBedsSpinnerString);
				} catch (UnsupportedEncodingException | FirebaseException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				String putUrl = "/emptyBeds";
				String avaBedsSpinnerString = emptyBedsSpinner.getValue().toString();
				System.out.println(avaBedsSpinnerString);
				try {
					firebase.put(putUrl, avaBedsSpinnerString);
				} catch (UnsupportedEncodingException | FirebaseException e1) {
					e1.printStackTrace();
				}
				putUrl = "/totBeds";
				String totBedsSpinnerString = totBedsSpinner.getValue().toString();
				System.out.println(totBedsSpinnerString);
				try {
					firebase.put(putUrl, totBedsSpinnerString);
				} catch (UnsupportedEncodingException | FirebaseException e1) {
					e1.printStackTrace();
				}
				appStage.close();
			}
			
		});

		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				appStage.close();
			}
			
		});

		
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				hospitalId = hId.getText();
				
				System.out.println(hospitalId);
				// get the base-url (ie: 'http://gamma.firebase.com/username')
				String firebase_baseUrl = "https://gnaa-4e1a5.firebaseio.com/Hospital/" + hospitalId;
				
				try {
					firebase = new Firebase( firebase_baseUrl );
				} catch (FirebaseException e2) {
					e2.printStackTrace();
				}
				FirebaseResponse response = null;
				try {
					response = firebase.get();
				} catch (FirebaseException | UnsupportedEncodingException e2) {
					e2.printStackTrace();
				}
				Map<String, Object> database = response.getBody();
				emptyBeds = (Integer) database.get("emptyBeds");
				totBeds = (Integer) database.get("totBeds");
				
				emptyBedsSpinner.getValueFactory().setValue(emptyBeds);
				totBedsSpinner.getValueFactory().setValue(totBeds);
				
				
				appStage.setScene(scene);
			}
		});
		
		
	}
	
	
}
