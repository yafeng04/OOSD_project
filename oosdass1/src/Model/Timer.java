package Model;

/**
 * Created by yangfeng on 25/5/17.
 */
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Timer{

    // private class constant and some variables
    static final Integer STARTTIME = 10;
    Timeline timeline;
    public Label timerLabel = new Label();
    Integer timeSeconds = STARTTIME;

    public Timer(){
        timerLabel.setText(timeSeconds.toString());
        timerLabel.setTextFill(Color.RED);
        timerLabel.setStyle("-fx-font-size: 8em;");
    }

    public void eventHandler(){

            if (timeline != null) {
                timeline.stop();
            }
            timeSeconds = STARTTIME;

            // update timerLabel
            timerLabel.setText(timeSeconds.toString());
            timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            // KeyFrame event handler
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(1),
                            event1 -> {
                                timeSeconds--;
                                // update timerLabel
                                timerLabel.setText(
                                        timeSeconds.toString());
                                if (timeSeconds <= 0) {
                                    timeline.stop();
                                }
                            }));
            timeline.playFromStart();

    }

    public int getTime(){
        return timeSeconds;
    }
}