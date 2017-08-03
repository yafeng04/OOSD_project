package Model;


/**
 * Created by yangfeng on 28/5/17.
 */
public class TileMaker {
    int x;
    int y;
    private MarginTile mtile;
    private NormalTile ntile;
    private DiscardTile dtile;

    public TileMaker(int x, int y) {
        mtile = new MarginTile();
        ntile = new NormalTile();
        dtile = new DiscardTile();
        this.x = x;
        this.y = y;
    }

    public MarginTile drawMarginTile() {
        mtile.draw(x, y);
        return mtile;
    }

    public NormalTile drawNormalTile() {
        ntile.draw(x, y);
        return ntile;
    }

    public DiscardTile drawDiscardTile() {
        dtile.draw(x, y);
        return dtile;
    }


}
