package Model;

import java.awt.image.BufferedImage;

/**
 * Created by yangfeng on 7/4/17.
 */
public interface ActionInterface {

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

