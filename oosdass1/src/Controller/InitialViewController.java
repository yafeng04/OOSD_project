package Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by yangfeng on 21/5/17.
 */
public class InitialViewController implements Initializable {
    int r;
    int c;
    NewRound newRound;
    public static int playerNumber;

    public static ArrayList<RownColumn> obstacles= new ArrayList<>();

    @FXML
    public TextArea textarea;

    @FXML
    public ListView<String> row;

    @FXML
    public ListView<String> column;

    public Button startGame=new Button();
    public Button addObstacle;

    @FXML
    public  ListView<String> playerNum;

    ObservableList<String> playerNumList= FXCollections.observableArrayList( "3", "4", "5", "6");
    ObservableList<String> rowList= FXCollections.observableArrayList("0","1", "2", "3", "4", "5", "6","7","8");
    ObservableList<String> columnList= FXCollections.observableArrayList("0", "1", "2", "3", "4");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        playerNum.setItems(playerNumList);
        playerNum.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                playerNumber=Integer.parseInt(newValue);
            }
        });

        row.setItems(rowList);
        row.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                r=Integer.parseInt(newValue);
            }
        });
        column.setItems(columnList);
        column.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                c=Integer.parseInt(newValue);
            }
        });

    }
    public class RownColumn{
        private RownColumn(int r, int c){
            row=r;
            column=c;
        }
        public int row;
        public int column;
    }

    public void setNewRound(NewRound n) {
        this.newRound = n;
    }
    public void addObstable() {
       obstacles.add(new RownColumn(r, c));
    }
    public void startGame(){
        newRound.createScene();
    }

}
