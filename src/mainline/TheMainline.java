package mainline;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/*******
 * <p> Title: TheMainline Class. </p>
 * 
 * <p> Description: A JavaFX application: This controller class is the entry point for this JavaFX
 * application.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2018-08-28 </p>
 * 
 * @author Lynn Robert Carter, Jaskirat , Sawan
 * 
 * @version 1.00	2018-08-28 Baseline
 * @version 2.00 2018-11-14 Enhanced to Completely Working PKMT Tool
 * @version 3.00 2019-02-05 Enhanced PKMT tool for generic api
 */
public class TheMainline extends Application {

	/**********************************************************************************************

	Class Attributes

	 **********************************************************************************************/

	// Attributes used to establish the display and control panel within the window provided to us
	private static double WINDOW_HEIGHT;
	private static double WINDOW_WIDTH;
	private static double CONTROL_PANEL_HEIGHT;


	public void start(Stage primaryStage) {

		// Determine the actual visual bounds for this display
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		// Set the Stage boundaries so the window is centered but does not totally fill the screen
		WINDOW_WIDTH = primaryScreenBounds.getWidth() - primaryScreenBounds.getMinX() - 100;
		WINDOW_HEIGHT = primaryScreenBounds.getHeight() - primaryScreenBounds.getMinY() - 100;

		// We do not need a window larger than 600 pixels wide or 400 pixels tall
		if (WINDOW_WIDTH > 600) WINDOW_WIDTH = 600;
		if (WINDOW_HEIGHT > 400) WINDOW_HEIGHT = 400;

		// This sets up the point where the control panel begins
		CONTROL_PANEL_HEIGHT = WINDOW_HEIGHT - 110;

		// Set up the title on the application's window
		primaryStage.setTitle("Personal Knowledge Management Tool");

		// Establish the root of the user interface
		Group theRoot = new Group();

		// Establish the root of the user interface tabs
		TabPane tabPane = new TabPane();

		// Establish the user interface and sets up all of the major UI widgets
		new UserInterface(primaryStage, theRoot, tabPane, WINDOW_HEIGHT, WINDOW_WIDTH, CONTROL_PANEL_HEIGHT);

		// Place the scene on the stage
		primaryStage.setScene(new Scene(theRoot, WINDOW_WIDTH, WINDOW_HEIGHT));

		// And make the stage visible which allows the user to start interacting with it
		primaryStage.show();
	}


	/*******************************************************************************************************
	 * This is the method that launches the JavaFX application
	 * 
	 * @param args are the program parameters and they are not used by this program.
	 * 
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}
}