package Model;

import Controller.GameController;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by yangfeng on 27/3/17.
 */
public class NormalTile extends Rectangle implements TileInterface {

    private Piece piece;

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public NormalTile() {}
    public void draw(int x, int y){
        setWidth(GameController.TILE_SIZE);
        setHeight(GameController.TILE_SIZE);
        relocate(x * GameController.TILE_SIZE, y * GameController.TILE_SIZE);
        setStroke(Color.BLACK);
        setFill(Color.WHITE);
    }


}
