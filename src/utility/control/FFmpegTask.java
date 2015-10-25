package utility.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.beans.property.DoubleProperty;
import javafx.concurrent.Task;
import editor.MediaConverter;

/**
 * Utility class to create FFMPEG Tasks. This particular Task is package
 * protected and should not be called elsewhere.
 * 
 * @author adav194
 * 
 */
public class FFmpegTask extends Task<Void> {

	private DoubleProperty progress;
	private Double totalWork;
	private String input;

	protected FFmpegTask(DoubleProperty progress, String input,
			Double finalDuration) {
		super();
		this.progress = progress;
		totalWork = finalDuration;
		this.input = input;
	}

	protected void updateProgress(double workDone, double totalWork) {
		super.updateProgress(workDone, totalWork);
		progress.setValue(workDone / totalWork);
	}

	@Override
	protected Void call() throws Exception {

		ProcessBuilder procBulder = new ProcessBuilder("/bin/bash", "-c", input);
		procBulder.redirectErrorStream(true);
		try {
			Process process = procBulder.start();
			currentlyProcessed(process.getInputStream());

		} catch (Exception e) {
			System.out.println("Failed to process FFMPEG command.");
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * A helper function which processes the output stream from an FFMPEG
	 * command. Parsing of this output allows an indication of progress to be
	 * made.
	 */
	private void currentlyProcessed(InputStream in) {
		BufferedReader bin = new BufferedReader(new InputStreamReader(in));
		String line;
		Boolean processingStarted = false;
		try {
			while ((line = bin.readLine()) != null) {
				;
				if (line.equals("Press [q] to stop, [?] for help")) {
					processingStarted = true;
				} else if (processingStarted & (line.indexOf("time=") != -1)) {
					line = line.substring(line.indexOf("time=") + 5,
							line.indexOf(" bitrate"));
					this.updateProgress(MediaConverter.timeToSeconds(line),
							totalWork);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
}
