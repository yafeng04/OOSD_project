package Model;

import Controller.GameController;

/**
 * Created by yangfeng on 28/5/17.
 */
public class UndoCommand implements Command {
    Piece piece=null;
    public int newX;
    public int newY;
    public int x0;
    public int y0;
    GameController gameCon;

    public void execute() {

        gameCon.undo1Round();
    }

    public UndoCommand (Piece p){
        this.piece=p;
    }
}
