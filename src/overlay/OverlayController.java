package overlay;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import editor.SpeechTab;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import player.VidivoxPlayer;

/**
 * Co-ordinates message sending between the OverlayWindow and the Editor Panel,
 * where the messages are mainly OverlayWindow updating.
 * @author michael
 *
 */
public class OverlayController {
	
	private static OverlayController singletonObject;
	final private ObservableList<Commentary> commentaryList = FXCollections.observableArrayList();
	
	public static OverlayController getOLController(){
		if (singletonObject == null){
			singletonObject = new OverlayController();
		}
		return singletonObject;
	}
	
	private OverlayController(){
		singletonObject = this;
		//initialize the controller
	}
	
	public void addCommentary(Commentary newCommentary){
		commentaryList.add(newCommentary);
		updateTable();
	}
	public void deleteCommentary(Commentary commentary){
		commentaryList.remove(commentary);
		updateTable();
	}
	public void editCommentary(Commentary commentary){
		//takes the commentary from the overlay panel
		//sends it off to speech tab to be processed.
		SpeechTab.getSpeechTab().editCommentary(commentary);
	}
	
	public void updateTable(){
		OverlayPanel.getOverlayPanel().reloadTable();
	}
	
	public void setCommentaryTable(TableView<Commentary> table){
		table.setItems(commentaryList);
	}
	
	public ObservableList<Commentary> getCommentaryList(){
		return commentaryList;
	}
	
	public void commitOverlay(){
		//TODO: creates the mp3/wav/whatever
		// from the list of commentaries
		//and sticks it onto the current video
		
		/* create a duration marker
		 * create the commentary file
		 * from the file, read off times = create an initial blank audio for buffer
		 * create new audio and attach to end
		 * get the total duration and continue to attach blank - new audio - blank - new audio
		 * */
	}
	
	public File createCommentaryFile() throws UnsupportedEncodingException, FileNotFoundException {
		//TODO: creates a formatted commentary file
		//that can be read easily from the command line or bash
		
		//get name of file, add 'commentary' or some extension e.g. 'cmt'
		String originalFileURI = VidivoxPlayer.getVPlayer().getMedia().getSource();
		
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
		new FileOutputStream("filename.txt"), "utf-8"))) {
			bw.write("");
		} catch (IOException e){
			//TODO: do something to handle IOexception
			e.printStackTrace();
		}
		
		//open up a file writer type object
		//create text file
		//for each Commentary in commentaryList
		//string = commentary.toFileFormattedString
		//append this string to the file
		//return the file object
		
		
		return null;
	}
	
	
	
	
}