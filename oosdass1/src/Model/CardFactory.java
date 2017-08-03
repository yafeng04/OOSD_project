package Model;

import Model.PieceType;

/**
 * Created by Frank on 2017/5/28.
 */
public abstract class CardFactory {
    public String type;

    public CardFactory createFactory(String type) {
        this.type = type;
        switch (type) {
            case "path" : return new PathFactory();
            case "action" : return new ActionFactory();
            case "piece" : return new PieceFactory();
        }
        return null;
    }

    public abstract Piece newPiece(PieceType type, int x, int y);
}
