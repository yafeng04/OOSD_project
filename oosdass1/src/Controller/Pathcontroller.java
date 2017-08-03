package Controller;

import java.awt.image.BufferedImage;

/**
 * Created by yangfeng on 23/3/17.
 */
public class Pathcontroller {

    public static BufferedImage rotate180( BufferedImage inputImage ) {
        int width = inputImage.getWidth(); //the Width of the original image
        int height = inputImage.getHeight();//the Height of the original image

        BufferedImage returnImage = new BufferedImage( width, height, inputImage.getType()  );
        for( int x = 0; x < width; x++ ) {
            for( int y = 0; y < height; y++ ) {
                returnImage.setRGB( width -x- 1, height - y - 1, inputImage.getRGB( x, y  )  );
            }
        }
        return returnImage;
    }

}
