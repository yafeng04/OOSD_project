package Controller;

import Model.NewRoundInterface;
import Model.TurnManager;
import View.BoardView;
import View.Command;
import View.ConcreteMediator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import View.*;

public class NewRound extends Application implements NewRoundInterface,ActionListener {


    private Stage stage;
    BorderPane rootLayout;
    FXMLLoader loader;
    GameController gameController=null;
    static TurnManager t;
    public static int roundNum=0;
    public static final int totalRounds=6;
    public static RadioButton rb;
    Mediator med = new ConcreteMediator();


    @Override
    public void start(Stage primaryStage) {

        stage = primaryStage;
        primaryStage.setTitle("Game");
        initRootLayout();

    }

    public void initRootLayout() {
        try {
            loader = new FXMLLoader();
            loader.setLocation(NewRound.class.getResource("InitialScene.fxml"));
            rootLayout = loader.load();
            InitialViewController controller = loader.getController();
            controller.setNewRound(this);

            Scene root = new Scene(rootLayout);
            stage.setScene(root);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main1(String[] args) {
        launch(args);
    }


    public BorderPane createNewBorderPane() {
        BoardView board = new BoardView();

        Parent parent = board.createContent();//create the actual pane

        BorderPane roottt = new BorderPane(parent);
        gameController = board.getGameCon();
        return roottt;
    }

    public void actionPerformed(ActionEvent ae){
        Command cmd = (Command) ae.getSource();
        cmd.execute();
    }

    public Button createNewButton() {
        return null;
    }

    public Button createUndoOneButton() {
        return null;
    }

    public Button createUndoTwoButton() {
        Button undo = new Button();
        undo.setLayoutX(100);
        undo.setLayoutY(300);
        undo.setText("Undo 2 Rounds");
        return undo;
    }

    public Button createUndoThreeButton() {
        Button undo = new Button();
        undo.setLayoutX(100);
        undo.setLayoutY(400);
        undo.setText("Undo 3 Rounds");
        return undo;
    }

    public Button creategiveupButton() {
        return null;
    }
    public Label createlabel() {
        Label label=new Label();
        label.setLayoutX(100);
        label.setLayoutY(700);
        label.setText("Confirm action done");
        return label;
    }
    public RadioButton createradiobutton() {
        RadioButton rb= new RadioButton();
        rb.setLayoutX(200);
        rb.setLayoutY(800);
        return rb;
    }

    public void createScene() {

        if(gameController!=null){
        if (gameController.t != null) t = gameController.t;}

        if(roundNum==totalRounds){
            gameController.getTa().setText("6 Rounds ended, winner is ");
            int highest=0;
            for(int i=0;i<t.getPlayerNum();i++){
                if(t.getPlayers().get(i).getScore()>highest)
                {highest=t.getPlayers().get(i).getScore();}
            }

            for(int i=0;i<t.getPlayerNum();i++) {
                if(t.getPlayers().get(i).getScore()==highest){
                    gameController.getTa().appendText("\n"+"player "+(i+1)+",");
                }
            }
           return;
        }

        roundNum++;

        rb=createradiobutton();

        VBox left = new VBox();
        BorderPane bp = createNewBorderPane();
        bp.setLeft(left);

        Button go = new BtnNew(this, med);
        GameController.removeInstance();

        Button undo1 = new BtnUndo(this, med);
        Button giveup = new BtnGiveup(this, med);
        Label label=createlabel();

        left.getChildren().addAll(go, undo1,  giveup,label,rb);

        Scene newScene = new Scene(bp);
        stage.setScene(newScene);
        go.setOnAction(event -> createScene());
        undo1.setOnAction(event -> undo1Round());
        giveup.setOnAction(event -> giveup());

        gameController.getTimer().eventHandler();

    }

    public void undo1Round() {
        gameController.undo1Round();
    }

    public void giveup() {
        gameController.giveup();
    }


}