package Model;

import java.awt.image.BufferedImage;

/**
 * Created by yangfeng on 28/5/17.
 */
public interface PieceInterface {

    public BufferedImage getB();

    public void setB(BufferedImage b);

    public PieceType getType();

    public double getOldY();

    public double getOldX();

    public boolean isUp();

    public boolean isDown();

    public boolean isLeft();

    public boolean isRight();

    public void setUp(boolean up);

    public void setDown(boolean down);

    public void setLeft(boolean left);

    public void setRight(boolean right);

    public void move(int x, int y) ;

    public void abortMove();


}
