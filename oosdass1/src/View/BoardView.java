package View;

/**
 * Created by yangfeng on 27/3/17.
 */

import Controller.GameController;
import Controller.InitialViewController;
import Model.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


/**
 * @invariant TILE_SIZE>0
 * @invariant WIDTH>0
 * @invariant HEIGHT>0
 * @invariant tileGroup.size()>0
 * @invariant pieceGroup.size()>0
 * @invariant handcardCount >= 5
 * @invariant currentDeck.size() % 9=0
 * @invariant treasureSpots.size() = 3
 */
public class BoardView {

    GameController gameCon;

    public GameController getGameCon() {
        return gameCon;
    }

    /**
     * @pre checkWin=false
     * @pre handcardCount=5
     * @post board.size > 0
     * @post currentDeck.size() > 0
     */

    public Parent createContent() {
        int HEIGHT = GameController.HEIGHT;
        int WIDTH = GameController.WIDTH;

        BorderPane root = new BorderPane();

        Pane midPane = new Pane();

        BorderPane right = new BorderPane();


        root.setRight(right);
        root.setCenter(midPane);

        gameCon=GameController.getInstance();

        TileInterface[][] board = gameCon.getBoard();

        Group tileGroup = gameCon.getTileGroup();
        Group pieceGroup = gameCon.getPieceGroup();

        TurnManager t = gameCon.getT();
        ArrayList<PieceType> currentDeckPieceType = gameCon.getCurrentDeckPieceType();

        VBox vb= new VBox();
        vb.getChildren().addAll(gameCon.getTa(),gameCon.getTimer().timerLabel);
        right.setBottom(vb);

        midPane.getChildren().addAll(tileGroup, pieceGroup);

        ArrayList<InitialViewController.RownColumn> copy = new ArrayList<>();
        copy.addAll(InitialViewController.obstacles);

        //margin area

        int a = 5;
        int b = 2;

        TileMaker maker= new TileMaker(a, b);
        MarginTile margintile = maker.drawMarginTile();

        board[a][b] = margintile;//x is column, y is row
        tileGroup.getChildren().add(margintile);
        Piece mpiece = gameCon.makePiece(PieceType.MARGIN, a, b);
        margintile.setPiece(mpiece);
        pieceGroup.getChildren().add(mpiece);


        for (int y = 0; y < HEIGHT - 1; y++) {
            for (int x = 0; x < WIDTH; x++) {


                if (((y < 2 || y > 7) && (x >= 6 && x <= 7)) || x == 5||x>=8) {
                    continue;
                }
                TileMaker maker1= new TileMaker(x, y);
                NormalTile normaltile = maker1.drawNormalTile();

                board[x][y] = normaltile;//x is column, y is row
                tileGroup.getChildren().add(normaltile);

                Piece piece = null;
                if ((x == 0 && y == 0) || (x == 2 && y == 0) || (x == 4 && y == 0)) {
                    piece = gameCon.makePiece(PieceType.TREASURE, x, y);
                } else if (x == 2 && y == 8) {
                    piece = gameCon.makePiece(PieceType.STARTPOINT, x, y);
                } else if (x == 6 && y == 2) {
                    piece = gameCon.makePiece(PieceType.P1, x, y);
                } else if (x == 6 && y == 3) {
                    piece = gameCon.makePiece(PieceType.P2, x, y);
                } else if (x == 6 && y == 4) {
                    piece = gameCon.makePiece(PieceType.P3, x, y);
                } else if (x == 6 && y == 5) {
                    piece = gameCon.makePiece(PieceType.P4, x, y);
                } else if (x == 6 && y == 6) {
                    piece = gameCon.makePiece(PieceType.P5, x, y);
                } else if (x == 6 && y == 7) {
                    piece = gameCon.makePiece(PieceType.P6, x, y);
                }

                if (piece != null) {
                    normaltile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
            }//create tile then make piece then setPiece
        }//create main game board

        for (int i = 0; i < copy.size(); i++) {
            int r, c;
            r = copy.get(i).row;
            c = copy.get(i).column;
            if (!board[c][r].hasPiece()) {
                Piece piece = gameCon.makePiece(PieceType.RIVER, c, r);
                board[c][r].setPiece(piece);
                pieceGroup.getChildren().add(piece);
            }
        }

        for (int i = 0; i < gameCon.t.getPlayerNum(); i++)

        {//initialise handcard
            for (int x = 0; x < 5; x++) {//player 2
                t.getPlayers().get(i).
                        getHandCards().
                        set(x, currentDeckPieceType.get(i * 5 + x));//get 5*i+x th card
            }
        }

        for (int x = 0; x < 5; x++) {//player 1
            int y = 9;
            TileMaker maker2= new TileMaker(x, y);
            NormalTile normalTile= maker2.drawNormalTile();
            board[x][y] = normalTile;
            tileGroup.getChildren().add(normalTile);
            Piece piece;
            pieceGroup.getChildren().add(gameCon.makePiece(PieceType.BLANK, x, y));
            piece = gameCon.makePiece(currentDeckPieceType.get(x), x, y);
            if (piece != null) {
                board[x][y].setPiece(piece);//set tile's variable
                pieceGroup.getChildren().add(piece);
            }
        }//create the hand card area


        for (int y = 8; y < 10; y++) {
            int x = 5;//player 1
            TileMaker maker3= new TileMaker(x, y);
            DiscardTile discardTile=maker3.drawDiscardTile();
            board[x][y] = discardTile;
            tileGroup.getChildren().add(discardTile);
            if (y == 8) {
//                Tile tile = new Tile(x, y, "discard");
            } else if (y == 9) {
                pieceGroup.getChildren().add(gameCon.makePiece(PieceType.DISCARDSIGN, x, y));
            }
        }

        for(int x=5;x<10;x++){
            int y=0;
            TileMaker maker4= new TileMaker(x, y);
            DiscardTile discardTile=maker4.drawDiscardTile();
            board[x][y] = discardTile;
            tileGroup.getChildren().add(discardTile);
        }


        return root;
    }


}//create a new game object so that memory is cleaned up and new game board is created




