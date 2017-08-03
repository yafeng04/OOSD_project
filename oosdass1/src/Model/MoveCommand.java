package Model;

import Controller.GameController;

/**
 * Created by yangfeng on 28/5/17.
 */
public class MoveCommand implements Command {
    Piece piece=null;
    public int newX;
    public int newY;
    public int x0;
    public int y0;

    public void execute() {
        piece.move(newX, newY);
    }
    public MoveCommand(Piece p, int newX1, int newY1, int x01, int y01){

        this.piece=p;
        newX=newX1;
        newY=newY1;
        x0=x01;
        y0=y01;
    }

}
