package Model;

import Controller.GameController;
import Model.PieceType;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

/**
 * Created by Frank on 2017/5/28.
 */
public class Action extends StackPane implements Card {
    public BufferedImage b = null;
    private PieceType type;
    private double mouseX, mouseY;
    private double oldX;
    private double oldY;

    public boolean up = false, down = false, left = false, right = false;

    public BufferedImage getB() {
        return b;
    }

    public void setB(BufferedImage b) {
        this.b = b;
    }

    public PieceType getType() {
        return type;
    }

    public double getOldY() {
        return oldY;
    }

    public double getOldX() {
        return oldX;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    /**
     * @pre
     * @post
     */
//    public Piece(int x, int y) {
//
//        move(x, y);
//
//        String fullpath = null;
//
//
//    }
    public Action(PieceType t, int x, int y) {

        this.type = t;
        String fullpath = null;
        move(x, y);


        if (type == PieceType.TREASURE) {
            fullpath = "src/images/treasure.jpeg";
        } else if (type == PieceType.STARTPOINT) {
            fullpath = "src/images/startpoint.jpeg";
            up = left = right = down = true;
        } else if (type == PieceType.PATH0) {
            fullpath = "src/images/Path0.jpg";
            up = right = down = true;
        } else if (type == PieceType.PATH1) {
            fullpath = "src/images/Path1.jpg";
            left = right = down = true;
        } else if (type == PieceType.PATH2) {
            fullpath = "src/images/Path2.jpg";
            left = down = true;
        } else if (type == PieceType.PATH3) {
            fullpath = "src/images/Path3.jpg";
            down = true;
        } else if (type == PieceType.PATH4) {
            fullpath = "src/images/Path4.jpg";
            down = right = true;
        } else if (type == PieceType.PATH5) {
            fullpath = "src/images/Path5.jpg";
            right = true;
        } else if (type == PieceType.PATH6) {
            fullpath = "src/images/Path6.jpg";
            up = down = true;
        } else if (type == PieceType.PATH7) {
            fullpath = "src/images/Path7.jpg";
            left = right = true;
        } else if (type == PieceType.PATH8) {
            fullpath = "src/images/Path8.jpg";
            up = left = right = down = true;
        } else if (type == PieceType.STONE) {
            fullpath = "src/images/stone.jpg";
        } else if (type == PieceType.GOLD) {
            fullpath = "src/images/gold.jpg";
        }
        else if (type == PieceType.BREAKPATH) {
            fullpath = "src/images/breakpath.jpeg";
        }
        else if (type == PieceType.PEEK) {
            fullpath = "src/images/peek.png";
        }
        else if (type == PieceType.BLANK) {
            fullpath = "src/images/qmark.jpeg";
        }else if (type == PieceType.RIVER) {
            fullpath =  "src/images/river.jpg";
        }else if (type == PieceType.P1) {
            fullpath =  "src/images/P1.png";
        }else if (type == PieceType.P2) {
            fullpath =  "src/images/P2.png";
        }else if (type == PieceType.P3) {
            fullpath =  "src/images/P3.png";
        }else if (type == PieceType.P4) {
            fullpath =  "src/images/P4.png";
        }else if (type == PieceType.P5) {
            fullpath =  "src/images/P5.png";
        }else if (type == PieceType.P6) {
            fullpath =  "src/images/P6.png";
        }else if (type == PieceType.DISCARD) {
            fullpath =  "src/images/discard.png";
        }
        else if (type == PieceType.DISCARDSIGN) {
            fullpath =  "src/images/discardsign.png";
        }
        else if (type == PieceType.MARGIN) {
            fullpath =  "src/images/margin.png";
        } else if (type == PieceType.PEEKIDENTITY) {
            fullpath =  "src/images/peekidentity.jpeg";
        }
        else if (type == PieceType.NEWHANDCARD) {
            fullpath =  "src/images/newcard.jpeg";
        }else if (type == PieceType.PEEKTREASURE) {
            fullpath =  "src/images/peektreasure.jpg";
        }else if (type == PieceType.SKIPTURN) {
            fullpath =  "src/images/skipturn.jpeg";
        }

        try {
            b = ImageIO.read(new File(fullpath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image i = SwingFXUtils.toFXImage(b, null);

        ImageView v = new ImageView(i);
        getChildren().add(v);


        setOnMousePressed(e -> {
            mouseX = e.getSceneX();//method that returns current X of mouse location
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);//mouseX and mouseY equal to the variable defined above
        });
    }

    public void move(int x, int y) {
        oldX = x * GameController.TILE_SIZE;
        oldY = y * GameController.TILE_SIZE;
        relocate(oldX, oldY);
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }
}
