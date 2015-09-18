package player;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;

public class VidivoxVideoControls extends VBox {

	private VidivoxLauncher launcher;
	private Button playBtn, pauseBtn, stopBtn, skipBackBtn, skipFwdBtn; //Video Playback
	private Button speechBtn, subBtn, mp3Btn; //Video Editting
	private Slider volumeBar;
	
	/*Flags related to application view */
	private boolean currentlyPlaying; //returns true: playBtn shows "||"; false: playBtn shows ">"
	
	final private static double minVolume = 0.0;
	final private static double maxVolume = 10.0;
	final private static double defaultVolume = 5.0;
	
	protected void updateCurrentlyPlaying(){
		Status st = launcher.getCurrentMediaView().getMediaPlayer().getStatus();
		currentlyPlaying = (st == Status.PLAYING);
		return;
	}
	
	public VidivoxVideoControls(VidivoxLauncher vl) {
		super();
		launcher = vl;
		
		//Buttons defined here (e.g. play button, pause button, stop button...)
		playBtn = new Button(">");
		playBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				updateCurrentlyPlaying();
				if (currentlyPlaying)
					launcher.playVideo(playBtn);
				else
					launcher.pauseVideo(playBtn);
				updateCurrentlyPlaying();
			}
		});
		pauseBtn = new Button("||");
		pauseBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				launcher.pauseVideo(playBtn);
			}
		});
		stopBtn = new Button("[]");
		stopBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				launcher.stopVideo();
			}
		});
		skipFwdBtn = new Button(">>");
		skipFwdBtn.setOnAction(new EventHandler<ActionEvent> () {
			public void handle(ActionEvent event) {
				launcher.ffwdVideo();
			}
		});
		skipBackBtn = new Button("<<");
		skipBackBtn.setOnAction(new EventHandler<ActionEvent> () {
			public void handle(ActionEvent event) {
				launcher.rwdVideo();
			}
		});
		speechBtn = new Button("Spch");
		speechBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				launcher.speech();
			}
		});
		subBtn = new Button("Sub");
		subBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				launcher.sub();
			}
		});
		mp3Btn = new Button("mp3");
		mp3Btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				launcher.mp3();
			}
		});

		//Initializing the Volume Slider

		volumeBar = new Slider(minVolume, maxVolume, defaultVolume);


		//ADD IN TOP PANEL
		HBox cp_top = new HBox();
		cp_top.setAlignment(Pos.CENTER);
		cp_top.setSpacing(10);
		cp_top.getChildren().addAll(skipBackBtn, stopBtn, playBtn, pauseBtn, skipFwdBtn);
		
		//ADD IN MIDDLE PANEL
		HBox cp_mid = new HBox();
		cp_mid.setAlignment(Pos.CENTER);
		cp_mid.getChildren().addAll(speechBtn, subBtn, mp3Btn,volumeBar);
		
		//ADD IN BOTTOM PANEL
		HBox cp_bot = new HBox();
		this.setSpacing(10);
		this.getChildren().addAll(cp_top, cp_mid, cp_bot);
		
	}
}

