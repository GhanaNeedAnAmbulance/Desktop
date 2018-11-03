package desktopApplication.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DesktopGUI extends Application {
	Pane root;
	Scene scene;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage appStage) throws Exception{
		root = new Pane();
		
		Button button = new Button("Click Me");
		root.getChildren().add(button);
		
		scene = new Scene(root,750,750);
		appStage.setScene(scene);
		appStage.setTitle("Ghana need an ambulance");
		appStage.show();
		
	}
}
