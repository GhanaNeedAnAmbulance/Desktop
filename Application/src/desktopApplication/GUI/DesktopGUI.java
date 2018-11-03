package desktopApplication.GUI;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
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
	String hospitalName = "Hospital Name";
	static String hospitalId = "Hospital1";
	static int totBeds, emptyBeds;
	
	public static void main(String[] args) throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException {
		// get the base-url (ie: 'http://gamma.firebase.com/username')
		String firebase_baseUrl = "https://gnaa-4e1a5.firebaseio.com/Hospital/Hospital2";
		
		
		Firebase firebase = new Firebase( firebase_baseUrl );
		FirebaseResponse response = firebase.get();
		System.out.println( "\n\nResult of GET:\n" + response );
		System.out.println("\n");
		Map<String, Object> database = response.getBody();
		emptyBeds = (Integer) database.get("emptyBeds");
		totBeds = (Integer) database.get("totBeds");
		//System.out.println("object: " + hospitalDatabase.getClass());
		//System.out.println("object: " + hospitalDatabase);
		
		System.out.println(emptyBeds);
		System.out.println(totBeds);
		
		String putUrl = "/emptyBeds";
		System.out.println(putUrl);
		firebase.put(putUrl, "53");
		//LinkedHashMap<Object, Object> hospitalDatabaseHash = LinkedHashMap.class.cast(hospitalDatabase);
		//System.out.println("made it past LinkedHashMap " + hospitalDatabaseHash.getClass());
		//Object emptyBedsObject = hospitalDatabaseHash.get("emptyBeds");
		//System.out.println("empty Beds class: " + emptyBedsObject.getClass());
		
		
		launch(args);
	}
	
	@Override
	public void start(Stage appStage) throws Exception{
		root = new GridPane();
		
		Button applyButton = new Button("Apply");
		applyButton.setFont(Font.font("Helvetica",11));
		Button saveButton = new Button("Save and Quit");
		saveButton.setFont(Font.font("Helvetica",11));
		Button closeButton = new Button("Close");
		closeButton.setFont(Font.font("Helvetica",11));
		ButtonBar buttons = new ButtonBar();
		buttons.getButtons().addAll(applyButton,saveButton,closeButton);
		buttons.setButtonMinWidth(90);
		
		Text hName = new Text(hospitalName);
		hName.setFont(Font.font("Helvetica",20));
		Text avaBedsText = new Text("Available Beds:");
		avaBedsText.setFont(Font.font("Helvetica",14));
		Spinner<Integer> avaBedsSpinner = new Spinner<Integer>(new IntegerSpinnerValueFactory(0,1000, emptyBeds));
		avaBedsSpinner.setEditable(true);
		avaBedsSpinner.setMaxWidth(85);
		Text totBedsText = new Text("Total Beds:");
		totBedsText.setFont(Font.font("Helvetica",14));
		Spinner<Integer> totBedsSpinner = new Spinner<Integer>(new IntegerSpinnerValueFactory(0,1000, totBeds));
		totBedsSpinner.setEditable(true);
		totBedsSpinner.setMaxWidth(85);
		
		HBox avaBeds = new HBox();
		avaBeds.getChildren().addAll(avaBedsText,avaBedsSpinner);
		avaBeds.setSpacing(5);
		HBox totBeds = new HBox();
		totBeds.getChildren().addAll(totBedsText,totBedsSpinner);
		totBeds.setSpacing(5);
		
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
	    root.add(avaBeds, 0, 2, 2, 1);
	    root.add(totBeds, 2, 2, 2, 1);
		root.add(buttons, 3, 4);
		
		scene = new Scene(root,400,100);
		appStage.setScene(scene);
		appStage.setTitle("Ghana need an ambulance");
		appStage.show();
		
	}
}
