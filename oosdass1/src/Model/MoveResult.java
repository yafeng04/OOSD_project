package Model;

/**
 * Created by yangfeng on 27/3/17.
 */
public class MoveResult implements MoveResultInterface{
    private MoveType type;
    private Piece piece;

    public MoveType getType(){
        return type;
    }
    public Piece getPiece(){
        return piece;
    }

    public MoveResult(MoveType type){
        this(type, null);
    }
    public MoveResult(MoveType type, Piece piece){
        this.type=type;
        this.piece=piece;
    }
}

