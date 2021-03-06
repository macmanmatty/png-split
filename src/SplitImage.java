import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SplitImage {
	int xPixles;
	int yPixles;
	Stage stage;
	Button selectImage;
	Button quit;
	Button selectDestination;
	Button splitImage;
	File file;
	
	String destnationPath="";
	String fileSource="";
	CheckBox saveTransparency;
	boolean transparency=true;
	
	
	FileChooser fileChooser = new FileChooser();
	DirectoryChooser dChooser = new DirectoryChooser();
	TextField enterX= new TextField();
	TextField enterY= new TextField();
	TextField enterName= new TextField();
	Label imageHeightLabel;
	Label imageWidthLabel;
	String imageWidth="0";
	String imageHeight="0";
	
	
	

	
	
	SplitImage (Stage stage){
		this.stage=stage;
		stage.setMinHeight(400);
		stage.setMinWidth(400);
		saveTransparency=new CheckBox();
		saveTransparency.setSelected(true);
		
		selectImage= new Button ("Select Image");
		selectImage.setOnAction(
		new EventHandler<ActionEvent>() {
            @Override
            public void handle( final ActionEvent e) {
              file = fileChooser.showOpenDialog(stage);
                fileSource=file.getAbsolutePath();

                String imagePath=file.getAbsolutePath();
        	
        	

        		
        	
        		Image image= new Image("file:"+imagePath);
        		imageWidth=Double.toString(image.getWidth());
        		imageHeight=Double.toString(image.getHeight());
        		
        		
            	showWindow(null);


                
            }
        });
		splitImage= new Button ("Split Image!");
		splitImage.setOnAction(
		new EventHandler<ActionEvent>() {
            @Override
            public void handle( final ActionEvent e) {
                if (file != null) {

                    split(file);
                }
            }
        });
		selectDestination= new Button ("Select Output Destnation");
		selectDestination.setOnAction(
		new EventHandler<ActionEvent>() {
            @Override
            public void handle( final ActionEvent e) {
                File file = dChooser.showDialog(stage);
                if (file != null) {
                	destnationPath=file.getAbsolutePath();
                	showWindow(null);
                	
                	
                }

            }
        });
		quit= new Button ("quit");
		quit.setOnAction(
		new EventHandler<ActionEvent>() {
            @Override
            public void handle( final ActionEvent e) {
               
            }
        });
		saveTransparency.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> value,
	            Boolean old, Boolean newValue) {
	        	if (newValue==true) {
	        		setTransparency(true);
	        	}
	        	else {
	        		setTransparency(false);
	        	}
	        }
	    }); 
		
	
	}
	
	
	public void showWindow(Label text){
		Label label= new Label("Select Image To Split" );
		Label labelImagex= new Label(" Image Height Pixels: "+imageHeight );
		Label labelImagey= new Label("Image Width Pixils: "+imageWidth );
		Label labelx= new Label("xPixels" );
		Label labely= new Label("yPixels" );
		Label name= new Label("Image  Prefix Name");
		
		Label check= new Label("Save Image Transparency?");
		Label output = new Label("Output Location: "+destnationPath);
		Label input = new Label("Image To Split: "+fileSource);

	
		HBox nameBox= new HBox(name , enterName);
		
		enterY.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            enterY.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		enterX.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            enterX.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		enterY.setText(Integer.toString(yPixles));
		enterX.setText(Integer.toString(xPixles));
HBox xHBox= new HBox();
HBox yHBox= new HBox();

xHBox.getChildren().add(labelx);
xHBox.getChildren().add(enterX);
yHBox.getChildren().add(labely);
yHBox.getChildren().add(enterY);



		
		VBox pane=new VBox();
		pane.getChildren().add(label);
		pane.getChildren().add(labelImagex);

pane.getChildren().add(labelImagey);

		pane.getChildren().add(xHBox);
		pane.getChildren().add(yHBox);
		pane.getChildren().add(nameBox);
		pane.getChildren().add(check);
		pane.getChildren().add(saveTransparency);
		pane.getChildren().add(input);
		pane.getChildren().add(output);
		
		
		pane.getChildren().add(selectImage);
		pane.getChildren().add(selectDestination);
		pane.getChildren().add(splitImage);
		
		
		if (text!=null) {
			VBox box= new VBox();
			box.getChildren().add(text);
			pane.getChildren().add(box);
			
			
			
			
		}
		
		
		
		stage.setScene(new Scene(pane));;
		stage.show();
		
		


		

		
	}
	public void split(File file) {
		String imagePath=file.getAbsolutePath();
		System.out.println(imagePath);
		String extension = "";
			String prefixName=enterName.getText();
			
		int i = imagePath.lastIndexOf('.');
		if (i > 0) {
		    extension = imagePath.substring(i);
		}
		
	
		xPixles=Integer.parseInt(enterX.getText());
		yPixles=Integer.parseInt(enterY.getText());

		
	
		Image image= new Image("file:"+imagePath);
		double width=image.getWidth();
		double height=image.getHeight();
		int xCut=(int)(width/xPixles);
		int yCut=(int)(height/yPixles);
		if (width%xPixles!=0) {
			 xCut=(int)(width/xPixles)+1;

			
		}
		if (height%yPixles!=0) {
			 yCut=(int)(height/yPixles)+1;

			
		}
		
		
		ImageView imageView= new ImageView(image);
		 SnapshotParameters sp = new SnapshotParameters();

		if (transparency==true) {

		    sp.setFill(Color.TRANSPARENT);
		
		}
		
		for (int countx=0; countx<xCut; countx++) {
			for (int county=0; county<yCut; county++) {

				imageView.setViewport(new javafx.geometry.Rectangle2D (countx*xPixles, county*yPixles, xPixles,yPixles ));
				Image image2=imageView.snapshot(sp,null);
				
				  
				    BufferedImage bImage = SwingFXUtils.fromFXImage(image2, null);
				    try {
				    	
				   String path="/"+prefixName+"x"+countx+"y"+county+extension;
					   path=destnationPath+path;
					   
					  
				   
				   
				   
				   File file2  = new  File(path);
				    
				      ImageIO.write(bImage, "png", file2);
				    } catch (IOException e) {
				   String message= 	e.getMessage();
				    	Label problem = new Label(message);
				    	showWindow(problem);
				    	
				    	
				      
				    }
				  }
				
			}
		
		Label imageSaved= new Label("Image Split!");
		

		showWindow(imageSaved);

			
		}


	public boolean isTransparency() {
		return transparency;
	}


	public void setTransparency(boolean transparency) {
		this.transparency = transparency;
	}
		
		
		
		
		
		
	}
			
			
			
	
	
	
	
	
	


