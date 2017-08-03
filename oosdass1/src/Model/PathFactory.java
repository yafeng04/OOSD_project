package Model;

import Model.PieceType;

/**
 * Created by Frank on 2017/5/28.
 */
public class PathFactory extends CardFactory {
    @Override
    public Piece newPiece(PieceType type, int x, int y) {
        return new Piece(type,x,x);
    }
}
