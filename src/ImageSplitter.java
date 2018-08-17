import javafx.application.Application;
import javafx.stage.Stage;

public class ImageSplitter extends Application {
	
	public static void  main (String [] args) {
		
		launch(args);
		
		
		
	}
	

	@Override
	public void start(Stage arg0) throws Exception {

		new SplitImage(arg0).showWindow(null);
		
	}

}
