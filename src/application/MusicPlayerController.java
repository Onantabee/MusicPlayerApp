package application;


import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

import org.bson.Document;

import com.jfoenix.controls.JFXSlider;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
//import javafx.application.Platform;
//import javafx.beans.binding.Bindings;
//import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
//import javafx.css.converter.DurationConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
//import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
//import javafx.scene.effect.DropShadow;

public class MusicPlayerController implements Initializable{
	
	@FXML
	private AnchorPane homePane;
	
	@FXML
	private TextField searchBar;
	
	@FXML
	private HBox welcomeUser;
	
	@FXML
	private Label topArtistsLabel;
	
	@FXML
	private AnchorPane topArtists;
	
	@FXML
	private Label trendingLabel;
	
	@FXML
	private AnchorPane trendingMusic;
	
	
	
	@FXML
    private Button homeButton;

    @FXML
    private FontAwesomeIconView homeIcon;
	
	@FXML
	private AnchorPane playlistsPane;
	
	@FXML
    private Button playlistsButton;

    @FXML
    private FontAwesomeIconView playlistsIcon;
	
	@FXML
	private AnchorPane artistsPane;
	
	@FXML
    private Button artistsButton;

    @FXML
    private FontAwesomeIconView artistsIcon;
	
	@FXML
	private AnchorPane albumsPane;
	
	@FXML
    private Button albumsButton;

    @FXML
    private FontAwesomeIconView albumsIcon;
	
	@FXML
	private AnchorPane favouritesPane;
	
	@FXML
    private Button favouritesButton;

    @FXML
    private FontAwesomeIconView favouritesIcon;
	
	@FXML
    private JFXSlider musicSlider;

    @FXML
    private ImageView myImg01;

    @FXML
    private ImageView playImage;

    @FXML
    private Button playPauseButton;

    @FXML
    private AnchorPane playerPane;

    @FXML
    private AnchorPane rootAnch;

    @FXML
    private JFXSlider volumeSlider;
    
    @FXML
    private Label songLabel;
    
    @FXML
    private Button nextSong;
    
    @FXML
    private Button previousSong;
    
    @FXML
    private Label totalTimeLabel;
    
    @FXML
    private Label currentTimeLabel;
    
    @FXML
    private ImageView musicImage01;

    @FXML
    private Label artistName01;

    @FXML
    private Label songName01;

    @FXML
    private Label songDuration01;
    
    @FXML
    private ImageView musicImage02;

    @FXML
    private Label artistName02;

    @FXML
    private Label songName02;

    @FXML
    private Label songDuration02;
    
    @FXML
    private ImageView musicImage03;

    @FXML
    private Label artistName03;

    @FXML
    private Label songName03;

    @FXML
    private Label songDuration03;
    
    @FXML
    private ImageView musicImage04;

    @FXML
    private Label artistName04;

    @FXML
    private Label songName04;

    @FXML
    private Label songDuration04;
    
    @FXML
    private Button trendingButton1;
    
    @FXML
    private Button trendingButton2;
    
    @FXML
    private Button trendingButton3;
    
    @FXML
    private Button trendingButton4;
    
    @FXML
    private Button trendingButton5;
    
    @FXML
    private Button trendingButton6;
    
    @FXML
    private ImageView trendingPlayImg1;
    
    @FXML
    private ImageView trendingPlayImg2;
    
    @FXML
    private ImageView trendingPlayImg3;
    
    @FXML
    private ImageView trendingPlayImg4;
    
    @FXML
    private ImageView trendingPlayImg5;
    
    @FXML
    private ImageView trendingPlayImg6;
    
    private Media media;
    private MediaPlayer mediaPlayer;
    
    private Media mediaTrend;
    private MediaPlayer mediaPlayerTrend;
    
    private File directory;
    private File[] files;
    
    private ArrayList<File> songs;
    private int songNumber;
    
    private Timer timer;
    private TimerTask task;
    private boolean running;
    
    private final String DATABASE_NAME = "musicdb";
    private final String COLLECTION_NAME = "songs";
    private final String HOST = "localhost";
    private final int PORT = 27017;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	homePane.setVisible(true);
    	playlistsPane.setVisible(false);
    	artistsPane.setVisible(false);
    	albumsPane.setVisible(false);
    	favouritesPane.setVisible(false);
    	//
    	
//    	volumeSlider.setValue(50);
    	musicSlider.setValue(0);
    	
		try {
			songs = new ArrayList<File>();
			
			directory = new File("src/application/music");
			
			files = directory.listFiles();
			
			if(files != null) {
				for(File file : files) {
					songs.add(file);
					System.out.println(file);
				}
			}
			
			media = new Media(songs.get(songNumber).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			songLabel.setText(songs.get(songNumber).getName());
			
		}catch(Exception e) {
			System.out.println("SongException: "+e);
			e.printStackTrace();
		}
		
		mediaPlayer.setOnReady(() -> {
			Duration totalDuration = mediaPlayer.getTotalDuration();
			String totalTime = formatDuration(totalDuration);
			totalTimeLabel.setText(totalTime);
		});
		
		mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
			String currentTime = formatDuration(newValue);
			currentTimeLabel.setText(currentTime);
			
		});
		
		retrieveFromMongo();
		
		
	    volumeSlider.setValue(50);
	    volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
	        @Override
	        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
	            double volume = newValue.doubleValue() / 100.0; 
	            mediaPlayer.setVolume(volume);
	        }
	    });
	    mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
		
	}
    
    public String retrieveFromMongo() {
    	try (MongoClient mongoClient = new MongoClient(HOST,PORT)){
            MongoDatabase db = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);
            try (MongoCursor<Document> cursor = collection.find().iterator()){
            	int count = 0;
            	while (cursor.hasNext() && count < 4) {
            		Document document = cursor.next();
            		if(count == 0) {
                		String musicImagePath = document.getString("musicImagePath");
                		String artistName = document.getString("artistName");
                		String songName = document.getString("songName");
                		String songDuration = document.getString("songDuration");
                		Image img = new Image(getClass().getResourceAsStream(musicImagePath));
                		musicImage01.setImage(img);
                		artistName01.setText(artistName);
                		songName01.setText(songName);
                		songDuration01.setText(songDuration);
                	}else if(count == 1) {
                		String musicImagePath = document.getString("musicImagePath");
                		String artistName = document.getString("artistName");
                		String songName = document.getString("songName");
                		String songDuration = document.getString("songDuration");
                		Image img = new Image(getClass().getResourceAsStream(musicImagePath));
                		musicImage02.setImage(img);
                		artistName02.setText(artistName);
                		songName02.setText(songName);
                		songDuration02.setText(songDuration);
                	}
                	else if(count == 2) {
                		String musicImagePath = document.getString("musicImagePath");
                		String artistName = document.getString("artistName");
                		String songName = document.getString("songName");
                		String songDuration = document.getString("songDuration");
                		Image img = new Image(getClass().getResourceAsStream(musicImagePath));
                		musicImage03.setImage(img);
                		artistName03.setText(artistName);
                		songName03.setText(songName);
                		songDuration03.setText(songDuration);
                	}
                	else if(count == 3) {
                		String musicImagePath = document.getString("musicImagePath");
                		String artistName = document.getString("artistName");
                		String songName = document.getString("songName");
                		String songDuration = document.getString("songDuration");
                		Image img = new Image(getClass().getResourceAsStream(musicImagePath));
                		musicImage04.setImage(img);
                		artistName04.setText(artistName);
                		songName04.setText(songName);
                		songDuration04.setText(songDuration);
                	}
            		count++;
            	}
            }catch(Exception e) {
        		System.out.println("MongoDB Exception-1: "+ e);
        	}
            
    	}catch(Exception e) {
    		System.out.println("MongoDB Exception-2: "+ e);
    	}
    	return null;
    }
    
    public String formatDuration(Duration duration) {
    	long minutes = (long)duration.toMinutes();
    	long seconds = (long)(duration.toSeconds() % 60);
    	return String.format("%02d:%02d", minutes, seconds);
    }
    
    @FXML
    void home(ActionEvent event) {
    	homePane.setVisible(true);
    	playlistsPane.setVisible(false);
    	artistsPane.setVisible(false);
    	albumsPane.setVisible(false);
    	favouritesPane.setVisible(false);
    	new animatefx.animation.SlideInRight(searchBar).setSpeed(2.5).play();
    	new animatefx.animation.SlideInRight(welcomeUser).setSpeed(2.5).play();
    	new animatefx.animation.SlideInRight(topArtistsLabel).setSpeed(2.5).play();
    	new animatefx.animation.SlideInRight(topArtists).setSpeed(2.5).play();
    	new animatefx.animation.SlideInRight(trendingLabel).setSpeed(2.5).play();
    	new animatefx.animation.SlideInRight(trendingMusic).setSpeed(2.5).play();
    }
    @FXML
    void playlists(ActionEvent event) {
    	homePane.setVisible(false);
    	playlistsPane.setVisible(true);
    	artistsPane.setVisible(false);
    	albumsPane.setVisible(false);
    	favouritesPane.setVisible(false);
    }
    @FXML
    void artists(ActionEvent event) {
    	homePane.setVisible(false);
    	playlistsPane.setVisible(false);
    	artistsPane.setVisible(true);
    	albumsPane.setVisible(false);
    	favouritesPane.setVisible(false);
    }
    @FXML
    void albums(ActionEvent event) {
    	homePane.setVisible(false);
    	playlistsPane.setVisible(false);
    	artistsPane.setVisible(false);
    	albumsPane.setVisible(true);
    	favouritesPane.setVisible(false);
    }
    @FXML
    void favourites(ActionEvent event) {
    	homePane.setVisible(false);
    	playlistsPane.setVisible(false);
    	artistsPane.setVisible(false);
    	albumsPane.setVisible(false);
    	favouritesPane.setVisible(true);
    }

    @FXML
    void MusticTime(MouseEvent event) {

    }

    @FXML
    void volume(MouseEvent event) {
    	
    } 
    
    @FXML
    void next(ActionEvent event) {
    	musicSlider.setValue(0);
    	pauseAllTrendingButtons();
    	if (mediaPlayerTrend != null) mediaPlayerTrend.stop();
    	if(songNumber < songs.size() - 1) {
    		songNumber++;
    		
    		mediaPlayer.stop();
    		if(running) {
    			Image image = new Image(getClass().getResourceAsStream("play-64.png"));
        		playImage.setImage(image);
        		play = false;
    			cancelTimer();
    		}
    		media = new Media(songs.get(songNumber).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			songLabel.setText(songs.get(songNumber).getName());
			
			mediaPlayer.setOnReady(() -> {
				Duration totalDuration = mediaPlayer.getTotalDuration();
				String totalTime = formatDuration(totalDuration);
				totalTimeLabel.setText(totalTime);
			});
			
			mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
				String currentTime = formatDuration(newValue);
				currentTimeLabel.setText(currentTime);
			});
    	}else {
    		songNumber = 0;
    		
    		mediaPlayer.stop();
    		if(running) {
    			Image image = new Image(getClass().getResourceAsStream("play-64.png"));
        		playImage.setImage(image);
        		play = false;
    			cancelTimer();
    		}
    		media = new Media(songs.get(songNumber).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			songLabel.setText(songs.get(songNumber).getName());
			mediaPlayer.setOnReady(() -> {
				Duration totalDuration = mediaPlayer.getTotalDuration();
				String totalTime = formatDuration(totalDuration);
				totalTimeLabel.setText(totalTime);
			});
			
			mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
				String currentTime = formatDuration(newValue);
				currentTimeLabel.setText(currentTime);
			});
    	}
    	Image image = new Image(getClass().getResourceAsStream("pause-64.png"));
		playImage.setImage(image);
		play = true;
    	mediaPlayer.play();
    	beginTimer();
    }
    
    @FXML
    void previous(ActionEvent event) {
    	musicSlider.setValue(0);
    	pauseAllTrendingButtons();
    	if (mediaPlayerTrend != null) mediaPlayerTrend.stop();
    	if(songNumber > 0) {
    		songNumber--;
    		
    		mediaPlayer.stop();
    		if(running) {
    			Image image = new Image(getClass().getResourceAsStream("play-64.png"));
        		playImage.setImage(image);
        		play = false;
    			cancelTimer();
    		}
    		
    		media = new Media(songs.get(songNumber).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			songLabel.setText(songs.get(songNumber).getName());
			mediaPlayer.setOnReady(() -> {
				Duration totalDuration = mediaPlayer.getTotalDuration();
				String totalTime = formatDuration(totalDuration);
				totalTimeLabel.setText(totalTime);
			});
			
			mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
				String currentTime = formatDuration(newValue);
				currentTimeLabel.setText(currentTime);
			});
    	}else {
    		songNumber = songs.size() - 1;
    		
    		mediaPlayer.stop();
    		if(running) {
    			Image image = new Image(getClass().getResourceAsStream("play-64.png"));
        		playImage.setImage(image);
        		play = false;
    			cancelTimer();
    		}
    		media = new Media(songs.get(songNumber).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			songLabel.setText(songs.get(songNumber).getName());
			mediaPlayer.setOnReady(() -> {
				Duration totalDuration = mediaPlayer.getTotalDuration();
				String totalTime = formatDuration(totalDuration);
				totalTimeLabel.setText(totalTime);
			});
			
			mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
				String currentTime = formatDuration(newValue);
				currentTimeLabel.setText(currentTime);
			});
    	}
    	Image image = new Image(getClass().getResourceAsStream("pause-64.png"));
		playImage.setImage(image);
		play = true;
    	mediaPlayer.play();
    	beginTimer();
    }
    
    public void beginTimer() {
    	timer = new Timer();
    	task = new TimerTask() {
    		public void run() {
    			running = true;
    			double current = mediaPlayer.getCurrentTime().toSeconds();
    			double end = media.getDuration().toSeconds();
				musicSlider.setMin(0);
				musicSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
				
				musicSlider.setValue(current);
				
				musicSlider.valueChangingProperty().addListener((observable, oldValue, isChanging) -> {
					if (!isChanging) {
						mediaPlayer.seek(Duration.seconds(musicSlider.getValue()));
					}
				});
				
				musicSlider.setOnMouseClicked(event -> {
					mediaPlayer.seek(Duration.seconds(musicSlider.getValue()));
				});
				
				
    			if(current >= end) {
    				Image image = new Image(getClass().getResourceAsStream("play-64.png"));
            		playImage.setImage(image);
            		play = false;
            		running = false;
            		mediaPlayer.seek(Duration.ZERO);
            		mediaPlayer.stop();
            		musicSlider.setValue(0);
    				cancelTimer();
    			}
    		}
    	};
    	timer.scheduleAtFixedRate(task, 0, 1000);
    }
    
    public void cancelTimer() {
    	running = false;
    	timer.cancel();
    }
    
    private boolean play = false;
    
    private boolean trendingPlaying = false;

	 void playingTrending(ImageView imageview) {
	     if (!trendingPlaying) {
	         Image image = new Image(getClass().getResourceAsStream("pause-64.png"));
	         imageview.setImage(image);
	         trendingPlaying = true;
	     } else {
	         pausingTrending(imageview);
	     }
	 }
	
	 void pausingTrending(ImageView imageview) {
	     Image image = new Image(getClass().getResourceAsStream("play-64.png"));
	     imageview.setImage(image);
	     trendingPlaying = false;
	 }
    
    private Button currentlyPlayingButton;

	private String pathOfSongs1; 
	
	private String pathOfSongs2; 
	
	private boolean playingTrend = false;
    

    @FXML
    void playTrending(ActionEvent event) {
    	try (MongoClient mongoClient = new MongoClient(HOST,PORT)){
    		MongoDatabase db = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);
            try (MongoCursor<Document> cursor = collection.find().iterator()){
            	int count = 0;
            	while (cursor.hasNext() && count < 2) {
            		Document document = cursor.next();
            		if(count == 0) {
                		String songPath = document.getString("songPath");
                		this.pathOfSongs1 = songPath;
                	}else if(count == 1) {
                		String songPath = document.getString("songPath");
                		this.pathOfSongs2 = songPath;
                	}
            		count++;
        		}
            }
    	}
        Button clickedButton = (Button) event.getSource();

        if (currentlyPlayingButton == null || currentlyPlayingButton != clickedButton) {
        	if(clickedButton == trendingButton1) {
        		File f = new File(pathOfSongs1);
        		String path = f.toURI().toString();
        		System.out.println(path);
        		mediaTrend = new Media(path);
    			mediaPlayerTrend = new MediaPlayer(mediaTrend);
    			mediaPlayerTrend.play();
    			playingTrend = true;
    			beginTimer();
        	}else if(clickedButton == trendingButton2) {
        		File f = new File(pathOfSongs2);
        		String path = f.toURI().toString();
        		System.out.println(path);
        		mediaTrend = new Media(path);
    			mediaPlayerTrend = new MediaPlayer(mediaTrend);
    			mediaPlayerTrend.play();
    			playingTrend = true;
    			beginTimer();
        	}
        	play = true;
        	togglePlayPause(event);
        	mediaPlayer.stop();
        	musicSlider.setValue(0);
            if (currentlyPlayingButton != null) {
                pausingTrending(getCorrespondingPlayImg(currentlyPlayingButton));
            }
            playingTrending(getCorrespondingPlayImg(clickedButton));
            currentlyPlayingButton = clickedButton;
        } else {
            pausingTrending(getCorrespondingPlayImg(clickedButton));
            currentlyPlayingButton = null;
            if(clickedButton == trendingButton1 && playingTrend) {
            	mediaPlayerTrend.pause();
    			playingTrend = false;
    			cancelTimer();
            }else if(clickedButton == trendingButton2 && playingTrend) {
            	mediaPlayerTrend.pause();
    			playingTrend = false;
    			cancelTimer();
            }
        }
    }
    private ImageView getCorrespondingPlayImg(Button button) {
        if (button == trendingButton1) {
            return trendingPlayImg1;
        } else if (button == trendingButton2) {
            return trendingPlayImg2;
        } else if (button == trendingButton3) {
            return trendingPlayImg3;
        } else if (button == trendingButton4) {
            return trendingPlayImg4;
        } else if (button == trendingButton5) {
            return trendingPlayImg5;
        } else if (button == trendingButton6) {
            return trendingPlayImg6;
        } else if (button == playPauseButton) {
            return playImage;
        }
        return null;
    }


    @FXML
    void togglePlayPause(ActionEvent event) {
    	try {
    		if(!play) {
    			pauseAllTrendingButtons();
    			if (mediaPlayerTrend != null) mediaPlayerTrend.stop();
    			playingTrend = false;
//    			playTrending(event);
    			
        		Image image = new Image(getClass().getResourceAsStream("pause-64.png"));
        		playImage.setImage(image);
        		beginTimer();
        		mediaPlayer.play();
        		play = true;
        	}else {
        		pauseAllTrendingButtons();
        		playingTrend = true;
        		
        		Image image = new Image(getClass().getResourceAsStream("play-64.png"));
        		playImage.setImage(image);
        		cancelTimer();
        		mediaPlayer.pause();
        		play = false;
        	}
    	}catch(Exception e){
    		System.out.println("Errorrrr: "+ e);
    	}
    }
    
    void pauseAllTrendingButtons() {
    	pausingTrending(getCorrespondingPlayImg(trendingButton1));
    	pausingTrending(getCorrespondingPlayImg(trendingButton2));
    	pausingTrending(getCorrespondingPlayImg(trendingButton3));
    	pausingTrending(getCorrespondingPlayImg(trendingButton4));
    	pausingTrending(getCorrespondingPlayImg(trendingButton5));
    	pausingTrending(getCorrespondingPlayImg(trendingButton6));
    }

}
