package desktopApplication.GUI;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.scene.text.*;

public class DesktopGUI extends Application {
	GridPane root;
	Scene scene;
	String hospitalName = "Hospital Name";
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage appStage) throws Exception{
		root = new GridPane();
		
		Button applyButton = new Button("Apply");
		applyButton.setFont(Font.font("Helvetica",11));
		Button saveButton = new Button("Save and Quit");
		saveButton.setFont(Font.font("Helvetica",11));
		Button closeButton = new Button("Cancel");
		closeButton.setFont(Font.font("Helvetica",11));
		ButtonBar buttons = new ButtonBar();
		buttons.getButtons().addAll(applyButton,saveButton,closeButton);
		buttons.setButtonMinWidth(90);
		
		Text hName = new Text(hospitalName);
		hName.setFont(Font.font("Helvetica",20));
		Text avaBedsText = new Text("Available Beds:");
		avaBedsText.setFont(Font.font("Helvetica",14));
		TextField avaBedsField = new TextField();
		avaBedsField.setFont(Font.font("Helvetica",10));
		avaBedsField.setMaxWidth(50);
		Text totBedsText = new Text("Total Beds:");
		totBedsText.setFont(Font.font("Helvetica",14));
		TextField totBedsField = new TextField();
		totBedsField.setFont(Font.font("Helvetica",10));
		totBedsField.setMaxWidth(50);
		
		HBox avaBeds = new HBox();
		avaBeds.getChildren().addAll(avaBedsText,avaBedsField);
		avaBeds.setSpacing(5);
		HBox totBeds = new HBox();
		totBeds.getChildren().addAll(totBedsText,totBedsField);
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
		
		
		scene = new Scene(root,320,100);
		appStage.setScene(scene);
		appStage.setTitle("Ghana need an ambulance");
		appStage.show();
		
	}
}
