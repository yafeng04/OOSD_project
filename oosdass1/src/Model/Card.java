package Model;

import Model.PieceType;
import javafx.scene.layout.StackPane;

import java.awt.image.BufferedImage;

public interface Card{
    BufferedImage getB();

    void setB(BufferedImage b);

    PieceType getType();

    double getOldY();

    double getOldX();

    boolean isUp();

    boolean isDown();

    boolean isLeft();

    boolean isRight();

    void setUp(boolean up);

    void setDown(boolean down);

    void setLeft(boolean left);

    void setRight(boolean right);

    void move(int x, int y);

    void abortMove();


}
