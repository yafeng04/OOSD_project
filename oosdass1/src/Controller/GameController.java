package Controller;

/**
 * Created by yangfeng on 27/3/17.
 */

import Model.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Group;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;


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

public class GameController {

    public int move = 0;

    public static GameController instance = null;

    public static final int TILE_SIZE = 70;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 11;

    private TileInterface[][] board = new TileInterface[WIDTH][HEIGHT];


    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    private static ArrayList<PieceType> activityPieces = new ArrayList<>();
    private static ArrayList<PieceType> pathPieces = new ArrayList<>();

    private ArrayList<PieceType> currentDeckPieceType = null;

    ArrayList<Integer> treasureSpots = new ArrayList<>();

    public TurnManager t = null;//turn manager has the responsibility to create player instance and manage the turn
    private int turn = 1;
    private int count = 1;
    private int handcardCount;

    private TextArea ta = new TextArea();

    private boolean winCondition = false;

    private Timer timer = new Timer();


    private ArrayList<Backup> backupPiece1 = new ArrayList<>();

    StoreNExecute sne= new StoreNExecute();

    private ArrayList<PlayerUndo> unDoPlayers = new ArrayList<>();
    private ArrayList<PlayerGiveUp> giveUpPlayers = new ArrayList<>();
    private CardFactory factory;

    private class Backup {

        int newX, newY, x0, y0;
        Piece piece;

        private Backup(int newX1, int newY1, int x01, int y01, Piece p) {

            newX = newX1;
            newY = newY1;
            x0 = x01;
            y0 = y01;
            piece = p;
        }

    }

    public GameController() {

        NewRound.rb.setDisable(true);

        TurnManager oldt;
        if (NewRound.t != null) {
            oldt = NewRound.t;
            t = new TurnManager(oldt.getPlayerNum());
            for (int i = 0; i < t.getPlayerNum(); i++) {
                t.getPlayers().get(i).setScore(oldt.getPlayers().get(i).getScore());
            }
        } else t = new TurnManager(InitialViewController.playerNumber);

        handcardCount = t.getPlayerNum() * 5;

        activityPieces.add(PieceType.BREAKPATH);

        pathPieces.add(PieceType.PATH0);
        pathPieces.add(PieceType.PATH1);
        pathPieces.add(PieceType.PATH2);
        pathPieces.add(PieceType.PATH3);
        pathPieces.add(PieceType.PATH4);
        pathPieces.add(PieceType.PATH5);
        pathPieces.add(PieceType.PATH6);
        pathPieces.add(PieceType.PATH7);
        pathPieces.add(PieceType.PATH8);

        ta.setPrefSize(150, 70);

        ta.setText("Player 1" + "'s turn");
        ta.appendText("\n" + "Your identity is " + t.getPlayers().get(0).getIdentity());
        ta.setEditable(false);
        ta.setWrapText(true);

        treasureSpots.add(0);
        treasureSpots.add(1);
        treasureSpots.add(0);
        Collections.shuffle(treasureSpots);
        currentDeckPieceType = createDeck(27);//create deck of 72 cards

        for (int i = 0; i < t.getPlayers().size(); i++) {
            unDoPlayers.add(new PlayerUndo(t.getPlayers().get(i)));
            giveUpPlayers.add(new PlayerGiveUp(t.getPlayers().get(i)));
        }

    }

    public Timer getTimer() {
        return timer;
    }

    public ArrayList<PieceType> getCurrentDeckPieceType() {
        return currentDeckPieceType;
    }

    public Group getTileGroup() {
        return tileGroup;
    }

    public Group getPieceGroup() {
        return pieceGroup;
    }

    public TurnManager getT() {
        return t;
    }

    public TextArea getTa() {
        return ta;
    }

    public TileInterface[][] getBoard() {
        return board;
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;

    }

    public static void removeInstance() {
        instance = null;
    }

    /**
     * @pre pixel>=0
     * @post return>=0
     */
    private int toBoard(double pixel) {
        return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
    }//Calculate the actual position in the array

    //Instead of the real position in the image


    /**
     * @pre newX >=0
     * @pre newY >=0
     */
    private MoveResult tryMove(Piece piece, int newX, int newY) throws Exception {
        int y0 = toBoard(piece.getOldY());

        if (board[newX][newY].hasPiece()) {
            if (activityPieces.contains(piece.getType()) && pathPieces.contains(board[newX][newY].getPiece().getType()) && !winCondition) {
                return new MoveResult(MoveType.BREAK, piece);
            }
        } else if (piece.getType() == PieceType.PEEK && y0 == 9 && newX == 7 && newY >= 2 && newY <= (1 + t.getPlayerNum()) && !winCondition) {
            return new MoveResult(MoveType.PEEK, piece);
        } else if (piece.getType() == PieceType.PEEKIDENTITY && y0 == 9 && newX == 7 && newY >= 2 && newY <= 6 && !winCondition) {
            return new MoveResult(MoveType.PEEKIDENTITY, piece);
        } else if (piece.getType() == PieceType.NEWHANDCARD && y0 == 9 && newX == 7 && newY >= 2 && newY <= 6 && !winCondition) {
            return new MoveResult(MoveType.NEWHANDCARD, piece);
        } else if (y0 == 9 && newX == 5 && newY == 8 && !winCondition) {
            return new MoveResult(MoveType.DISCARD, piece);
        } else if (y0 == 9 && newX == 7 && newY >= 2 && newY <= 7 && piece.getType() == PieceType.PEEKTREASURE && !winCondition) {
            return new MoveResult(MoveType.PEEKTREASURE, piece);
        } else if (y0 == 9 && newX == 7 && newY >= 2 && newY <= 7 && piece.getType() == PieceType.SKIPTURN && !winCondition) {
            return new MoveResult(MoveType.SKIPTURN, piece);
        } else if (y0 == 9 && newY >= 0 && newY < 9 && newX >= 0 && newX <= 4 && !board[newX][newY].hasPiece()
                && checkPath(piece, newX, newY) && !winCondition) {
            return new MoveResult(MoveType.NORMAL, piece);
        }

        return new MoveResult(MoveType.NONE);
    }


    /**
     * @pre x >=0
     * @pre y >=0
     * @post handcardCount <=72
     */
    public Piece makePiece(PieceType type, int x, int y) {//x, y are board position for that piece
        factory = new PieceFactory();
        Piece piece = factory.newPiece(type, x, y);

        if (type == PieceType.BLANK) return piece;

        piece.setOnMouseReleased(e -> {
            int newX = toBoard(piece.getLayoutX());//newX, newY are the new board position from the move result pixel position
            int newY = toBoard(piece.getLayoutY());
            MoveResult result = new MoveResult(MoveType.NONE);//get the move type
            try {
                result = tryMove(piece, newX, newY);//try move this own piece and check its move result
            } catch (Exception e1) {

            }

            int x0 = toBoard(piece.getOldX());//x0, y0 are the old board positions, calculated from old pixel position
            int y0 = toBoard(piece.getOldY());

            switch (result.getType()) {

                case NONE:


                    piece.abortMove();

                    break;
                case NORMAL:

                    timer.eventHandler();
                    if(sne.getHistory().size()==0){sne.getHistory().add(new MoveCommand(piece, newX, newY, x0, y0));}
                    else sne.getHistory().set(0, new MoveCommand(piece, newX, newY, x0, y0));
                    backupMove(piece, newX, newY, x0, y0);
                    releaseCard(piece, newX, newY, result);
                    updateHandcard(x0, result);
                    changeOver();
                    showNextPlayerTurnInfo();
                    move++;
                    checkWin(piece, newX, newY);

                    break;
                case DISCARD:

                    timer.eventHandler();
                    if(sne.getHistory().size()==0){sne.getHistory().add(new MoveCommand(piece, newX, newY, x0, y0));}
                    else sne.getHistory().set(0, new MoveCommand(piece, newX, newY, x0, y0));
                    backupMove(piece, newX, newY, x0, y0);
                    releaseCard(piece, newX, newY, result);
                    updateHandcard(x0, result);
                    changeOver();
                    showNextPlayerTurnInfo();
                    move++;
                    checkWin(piece, newX, newY);
                    break;
                case BREAK:
                    timer.eventHandler();

                    backupMove(piece, newX, newY, x0, y0);
                    releaseCard(piece, newX, newY, result);
                    updateHandcard(x0, result);
                    changeOver();
                    showNextPlayerTurnInfo();
                    move++;
                    checkWin(piece, newX, newY);
                    break;
                case PEEK:
                    timer.eventHandler();

                    piece.move(newX, newY);
                    peek(newY, x0, y0, result);
                    move++;
                    checkWin(piece, newX, newY);
                    break;
                case PEEKIDENTITY:
                    timer.eventHandler();

                    piece.move(newX, newY);

                    peekIdentity(x0, y0, result);
                    move++;
                    checkWin(piece, newX, newY);
                    break;
                case NEWHANDCARD:
                    timer.eventHandler();

                    piece.move(newX, newY);
                    newHandCard(x0, y0, result);
                    move++;
                    checkWin(piece, newX, newY);
                    break;
                case PEEKTREASURE:
                    timer.eventHandler();

                    piece.move(newX, newY);
                    peekAtTreasure(x0, y0, result);
                    move++;
                    checkWin(piece, newX, newY);
                    break;
                case SKIPTURN:
                    timer.eventHandler();

                    piece.move(newX, newY);
                    board[newX][newY].setPiece(piece);
                    skipNextPersonsTurn(x0, y0, result);
                    move++;
                    checkWin(piece, newX, newY);
                    break;

            }

        });//add event listener to the piece

        piece.setOnMouseClicked(e -> {
            int newX = toBoard(piece.getLayoutX());//get the real image position first then get board position
            int newY = toBoard(piece.getLayoutY());

            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());

            if (newX == x0 && newY == y0 && y0 == 9 && pathPieces.contains(piece.getType())) {

                rotateImage(piece);
            }

        });

        return piece;
    }

    /**
     * @pre num>0
     * @post return.size>=5
     */
    private ArrayList<PieceType> createDeck(int num) {

        ArrayList<PieceType> deck = new ArrayList<PieceType>();
        int numOfEach = num / 9;
        for (int i = 0; i < numOfEach; i++) {
            deck.add(PieceType.PATH0);
            deck.add(PieceType.PATH1);

            deck.add(PieceType.PATH8);
            deck.add(PieceType.PATH8);
            deck.add(PieceType.PATH0);
            deck.add(PieceType.PATH1);

            deck.add(PieceType.PATH6);
            deck.add(PieceType.PATH7);
            deck.add(PieceType.PATH8);
        }

        deck.add(PieceType.NEWHANDCARD);
        deck.add(PieceType.NEWHANDCARD);
        deck.add(PieceType.NEWHANDCARD);
        deck.add(PieceType.NEWHANDCARD);


        deck.add(PieceType.BREAKPATH);
        deck.add(PieceType.BREAKPATH);
        deck.add(PieceType.BREAKPATH);
        deck.add(PieceType.BREAKPATH);


        deck.add(PieceType.PEEK);
        deck.add(PieceType.PEEK);
        deck.add(PieceType.PEEK);

        deck.add(PieceType.PEEKTREASURE);
        deck.add(PieceType.PEEKTREASURE);
        deck.add(PieceType.PEEKTREASURE);
        deck.add(PieceType.PEEKTREASURE);


        deck.add(PieceType.SKIPTURN);
        deck.add(PieceType.SKIPTURN);
        deck.add(PieceType.SKIPTURN);
        deck.add(PieceType.SKIPTURN);


        deck.add(PieceType.PEEKIDENTITY);
        deck.add(PieceType.PEEKIDENTITY);
        deck.add(PieceType.PEEKIDENTITY);


        Collections.shuffle(deck);
        return deck;//create a deck with random piecetype in the arraylist
    }

    /**
     * @pre num>=0
     */
    private void checkWin(Piece piece, int newX, int newY) {

        boolean checkSaboWin = false;

        for (int i = 0; i < t.getPlayerNum(); i++) {
            if (t.getPlayers().get(i).getHandCards().size() != 0) {
                checkSaboWin = true;
                break;
            }
        }
        if (!checkSaboWin) {
            winCondition = true;
            for (int j = 0; j < t.getPlayers().size(); j++) {
                if (t.getPlayers().get(j).getIdentity().equals("saboteur")) {
                    t.getPlayers().get(j).setScore(t.getPlayers().get(j).getScore() + 1);
                }

            }
            ta.setText("Saboteur win");
            ta.appendText("\n" + "current score for each player: ");

            for (int k = 0; k < t.getPlayerNum(); k++) {
                ta.appendText("\n" + "player" + (k + 1) + ": " + t.getPlayers().get(k).getScore());
            }
            return;
        }

        if ((newX == 0 && newY == 1 && piece.isUp()) || (newX == 1 && newY == 0 && piece.isLeft())) {//check top left treasure
            if (treasureSpots.get(0) == 1) {
                Piece spotPiece = makePiece(PieceType.GOLD, 0, 0);
                if (piece != null) {
                    board[0][0].setPiece(spotPiece);//set tile's variable
                    pieceGroup.getChildren().add(spotPiece);
                    winCondition = true;

                }
            } else {
                Piece spotPiece = makePiece(PieceType.STONE, 0, 0);
                if (piece != null) {
                    board[0][0].setPiece(spotPiece);//set tile's variable
                    pieceGroup.getChildren().add(spotPiece);

                }
            }
        }

        if (newX == 1 && newY == 0 && piece.isRight() || (newX == 2 && newY == 1 && piece.isUp()) || (newX == 3 && newY == 0 && piece.isLeft())) {
            //check middle treasure
            if (treasureSpots.get(1) == 1) {
                Piece spotPiece = makePiece(PieceType.GOLD, 2, 0);
                if (piece != null) {
                    board[2][0].setPiece(spotPiece);//set tile's variable
                    pieceGroup.getChildren().add(spotPiece);
                    winCondition = true;

                }
            } else {
                Piece spotPiece = makePiece(PieceType.STONE, 2, 0);
                if (piece != null) {
                    board[2][0].setPiece(spotPiece);//set tile's variable
                    pieceGroup.getChildren().add(spotPiece);

                }
            }
        }


        if (newX == 3 && newY == 0 && piece.isRight() || (newX == 4 && newY == 1 && piece.isUp())) {//check top right treasure
            if (treasureSpots.get(2) == 1) {
                Piece spotPiece = makePiece(PieceType.GOLD, 4, 0);
                if (piece != null) {
                    board[4][0].setPiece(spotPiece);//set tile's variable
                    pieceGroup.getChildren().add(spotPiece);
                    winCondition = true;

                }
            } else {
                Piece spotPiece = makePiece(PieceType.STONE, 4, 0);
                if (piece != null) {
                    board[4][0].setPiece(spotPiece);//set tile's variable
                    pieceGroup.getChildren().add(spotPiece);

                }
            }
        }

        if (winCondition) {
            for (int i = 0; i < t.getPlayers().size(); i++) {
                if (t.getPlayers().get(i).getIdentity().equals("gold digger")) {
                    t.getPlayers().get(i).setScore(t.getPlayers().get(i).getScore() + 1);
                }
            }
            ta.setText("Gold diggers win");
            ta.appendText("\n" + "current score for each player: ");
            for (int k = 0; k < t.getPlayerNum(); k++) {
                ta.appendText("\n" + "player" + (k + 1) + ": " + t.getPlayers().get(k).getScore());
            }
        }
    }

    private boolean checkPath(Piece piece, int x, int y) {//check if its applicable to place certain path card
        boolean up = false, down = false, left = false, right = false;
        if (y - 1 >= 0) {
            if (board[x][y - 1].getPiece() != null) {
                if (piece.isUp() == board[x][y - 1].getPiece().isDown() && piece.isUp()) up = true;
            }
        }
        if (y + 1 <= 9) {
            if (board[x][y + 1].getPiece() != null) {
                if (piece.isDown() == board[x][y + 1].getPiece().isUp() && piece.isDown()) down = true;
            }
        }
        if (x - 1 >= 0) {
            if (board[x - 1][y].getPiece() != null) {
                if (piece.isLeft() == board[x - 1][y].getPiece().isRight() && piece.isLeft()) left = true;
            }
        }
        if (x + 1 <= 4) {
            if (board[x + 1][y].getPiece() != null) {
                if (piece.isRight() == board[x + 1][y].getPiece().isLeft() && piece.isRight()) right = true;
            }
        }
        return up || down || left || right;
    }

    private void rotateImage(Piece piece) {
        BufferedImage b = piece.getB();
        BufferedImage rotatedB = Pathcontroller.rotate180(b);
        piece.setB(rotatedB);
        piece.getChildren().remove(0);
        Image i = SwingFXUtils.toFXImage(rotatedB, null);
        ImageView v = new ImageView(i);
        piece.getChildren().add(v);//rotate buffered image

        if (piece.isUp() && !piece.isDown()) {
            piece.setUp(false);
            piece.setDown(true);
        } else if (!piece.isUp() && piece.isDown()) {
            piece.setDown(false);
            piece.setUp(true);
        }
        //change the variables after changing direction

        if (piece.isLeft() && !piece.isRight()) {
            piece.setLeft(false);
            piece.setRight(true);
        } else if (!piece.isLeft() && piece.isRight()) {
            piece.setRight(false);
            piece.setLeft(true);
        }
        //change the variables after changing direction

    }

    public void backupMove(Piece piece, int newX, int newY, int x0, int y0) {

        if (backupPiece1.size() == 0) {//initiating
            for (int i = 0; i < t.getPlayerNum(); i++) {
                backupPiece1.add(new Backup(0, 0, 0, 0, piece));
            }
        }

        backupPiece1.set(turn - 1, new Backup(newX, newY, x0, y0, piece));//remember one round of move

    }

    private void releaseCard(Piece piece, int newX, int newY, MoveResult rs) {//release the card from hand to board

        if (rs.getType() == MoveType.NORMAL) {
            piece.move(newX, newY);//move the piece
            board[newX][newY].setPiece(piece);//change the attribute of target board position
        } else if (rs.getType() == MoveType.DISCARD) {
            piece.move(newX, newY);//move the piece

        } else if (rs.getType() == MoveType.BREAK) {
            board[newX][newY].getPiece().move(5, 8);
            piece.move(5, 8);
            board[newX][newY].setPiece(null);
        }
    }


    public void updateHandcard(int x0, MoveResult rs) {
        for (int i = 0; i < 5; i++) {
            int y = 9;
            pieceGroup.getChildren().add(makePiece(PieceType.BLANK, i, y));
        }

        if (handcardCount < currentDeckPieceType.size()) {
            t.getPlayers().get(turn - 1).getHandCards().set(x0, currentDeckPieceType.get(handcardCount));

            handcardCount++;//starting from playernum*5; change to next player's turn
        } else {//less than 5 cards on hand for each player

            if (!(rs.getType() == MoveType.SKIP)) {
                if (t.getPlayers().get(turn - 1).getHandCards().get(x0) != null)
                    t.getPlayers().get(turn - 1).getHandCards().remove(x0);
            }

        }
    }

    public void changeOver() {

        for (int i = 0; i < t.getPlayers().get(turn % t.getPlayerNum()).getHandCards().size(); i++) {//make new piece for all handcards for next player
            int y = 9;

            Piece newPiece = makePiece(t.getPlayers().get(turn % t.getPlayerNum()).getHandCards().get(i), i, y);

            pieceGroup.getChildren().add(newPiece);

            board[i][y].setPiece(newPiece);
        }
    }

    public void undo1Round() {

        if (winCondition) ta.setText("\n" + "This round ended, start new round!!");

        if (move < t.getPlayerNum()) {
            ta.appendText("\n" + "can't undo before you place any card");
            return;
        }

        if (!unDoPlayers.get(turn - 1).isUndo()) {
            unDoPlayers.get(turn - 1).setUndo(true);

            for (int i = 0; i < unDoPlayers.size(); i++) {
                handcardCount -= 1;//restore deck

                Backup backup = backupPiece1.get(i);
                Piece piece = backup.piece;
                piece.move(5, 8);//clear board

                if (i == (turn - 1)) {
                    Piece oldPiece = makePiece(piece.getType(), backup.x0, backup.y0);
                    pieceGroup.getChildren().add(oldPiece);
                    board[backup.x0][9].setPiece(oldPiece);
                }

                t.getPlayers().get(i).getHandCards().set(backup.x0, piece.getType());//restore handcard

                board[backup.newX][backup.newY].setPiece(null);//remove piece from board and move card back to player
            }

        } else ta.appendText("\n" + "cannot undo more than once");
    }

    public void giveup() {//confirm all players give up in this round
        if (winCondition) ta.setText("\n" + "This round ended, start new round!!");
        boolean checkSaboWin = false;
        giveUpPlayers.get(turn - 1).setGiveUp(true);
        //t.getPlayers().get(turn - 1).setGiveup(true);
        ta.appendText("\n" + "give up action performed");
        for (int i = 0; i < t.getPlayerNum(); i++) {
            if (!giveUpPlayers.get(i).isGiveUp()) {
                checkSaboWin = true;
            }
        }
        if (!checkSaboWin) {

            if (!winCondition) {
                for (int j = 0; j < t.getPlayers().size(); j++) {
                    if (giveUpPlayers.get(j).getIdentity().equals("saboteur")) {
                        giveUpPlayers.get(j).setScore(giveUpPlayers.get(j).getScore() + 1);
                    }
                }
            }
            winCondition = true;
            ta.setText("Saboteur win");
            ta.appendText("\n" + "current score for each player: ");
            for (int j = 0; j < giveUpPlayers.size(); j++) {
                ta.appendText("\n" + "player" + (j + 1) + ": " + giveUpPlayers.get(j).getScore());
            }
        } else skipTurn();
    }

    public void skipTurn() {
        for (int i = 2; i < 8; i++) {
            if (board[7][i].hasPiece()) {
                if (board[7][i].getPiece().getType() == PieceType.SKIPTURN) {
                    board[7][i].getPiece().move(5, 8);
                    board[7][i].setPiece(null);

                }
            }

        }
        Piece piece = makePiece(PieceType.BLANK, 5, 8);

        updateHandcard(0, new MoveResult(MoveType.SKIP));
        changeOver();
        showNextPlayerTurnInfo();
    }


    public void showNextPlayerTurnInfo() {

        turn = count % t.getPlayerNum() + 1;
        ta.setText("Player " + (turn) + "'s turn");
        ta.appendText("\n" + "Your identity is " + t.getPlayers().get(turn - 1).getIdentity());
        count++;
    }

    public void peek(int newY, int x0, int y0, MoveResult rs) {

        for (int i = 5; i < 5 + t.getPlayers().get(newY - 2).getHandCards().size(); i++) {//peek at other player's handcard
            int y = 0;
            Piece piece = makePiece(t.getPlayers().get(0).getHandCards().get(i - 5), i, y);
            board[i][0].setPiece(piece);
            pieceGroup.getChildren().add(piece);

        }

        NewRound.rb.setDisable(false);

        NewRound.rb.setOnAction(event -> {
            for (int i = 5; i < 5 + t.getPlayers().get(newY - 2).getHandCards().size(); i++) {//peek at other player's handcard

                board[i][0].getPiece().move(5, 8);
                board[i][0].setPiece(null);
            }
            Piece peekpiece = rs.getPiece();
            peekpiece.move(5, 8);

            backupMove(peekpiece, 5, 8, x0, y0);//the move is from hand to discard pile
            updateHandcard(x0, rs);
            changeOver();

            NewRound.rb.setSelected(false);
            NewRound.rb.setDisable(true);
            showNextPlayerTurnInfo();
        });
    }

    public void peekIdentity(int x0, int y0, MoveResult rs) {
        for (int i = 0; i < t.getPlayerNum(); i++) {//peek at other player's handcard
            ta.appendText("\n" + "Player " + (i + 1) + "'s identity: " + t.getPlayers().get(i).getIdentity());
        }

        NewRound.rb.setDisable(false);

        NewRound.rb.setOnAction(event -> {

            Piece peekpiece = rs.getPiece();
            peekpiece.move(5, 8);

            backupMove(peekpiece, 5, 8, x0, y0);//the move is from hand to discard pile
            updateHandcard(x0, rs);
            changeOver();

            NewRound.rb.setSelected(false);
            NewRound.rb.setDisable(true);
            showNextPlayerTurnInfo();
        });
    }

    public void newHandCard(int x0, int y0, MoveResult rs) {//get 4 new cards

        int currentPlayerhandcardsize = t.getPlayers().get(turn - 1).getHandCards().size();

        if (getCurrentDeckPieceType().size() - handcardCount >= 5) {
            for (int i = 0; i < currentPlayerhandcardsize; i++) {

                if (i != x0) {
                    pieceGroup.getChildren().add(makePiece(currentDeckPieceType.get(handcardCount), i, y0));
                    t.getPlayers().get(turn - 1).getHandCards().set(i, currentDeckPieceType.get(handcardCount));
                    handcardCount++;
                }
            }
            ta.appendText("\n" + "cards successfully dealt");
        } else {
            ta.appendText("\n" + "not enough cards in deck, cannot deal new cards");
        }
        NewRound.rb.setDisable(false);

        NewRound.rb.setOnAction(event -> {

            Piece peekpiece = rs.getPiece();
            peekpiece.move(5, 8);

            backupMove(peekpiece, 5, 8, x0, y0);//the move is from hand to discard pile
            updateHandcard(x0, rs);
            changeOver();

            NewRound.rb.setSelected(false);
            NewRound.rb.setDisable(true);
            showNextPlayerTurnInfo();
        });
    }

    public void skipNextPersonsTurn(int x0, int y0, MoveResult rs) {

        NewRound.rb.setDisable(false);

        NewRound.rb.setOnAction(event -> {

            Piece peekpiece = rs.getPiece();
//            peekpiece.move(5, 8);

            backupMove(peekpiece, 5, 8, x0, y0);//the move is from hand to discard pile
            updateHandcard(x0, rs);
            changeOver();

            NewRound.rb.setSelected(false);
            NewRound.rb.setDisable(true);
            showNextPlayerTurnInfo();
            skipTurn();
        });
    }

    public void peekAtTreasure(int x0, int y0, MoveResult rs) {

        for (int i = 0; i < treasureSpots.size(); i++) {//peek at other player's handcard
            if (treasureSpots.get(i) == 1) {
                ta.appendText("\n" + "treasure is at spot" + (i + 1) + "from left side");
                break;
            }
        }

        NewRound.rb.setDisable(false);

        NewRound.rb.setOnAction(event -> {

            Piece peekpiece = rs.getPiece();
            peekpiece.move(5, 8);

            backupMove(peekpiece, 5, 8, x0, y0);//the move is from hand to discard pile
            updateHandcard(x0, rs);
            changeOver();

            NewRound.rb.setSelected(false);
            NewRound.rb.setDisable(true);
            showNextPlayerTurnInfo();
        });
    }


}
