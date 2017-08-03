package Model;

/**
 * Created by yangfeng on 6/4/17.
 */
public interface TileInterface {
    boolean hasPiece();
    Piece getPiece();
    void setPiece(Piece piece);
    void draw(int x, int y);
}
