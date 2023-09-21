package ghost;

import processing.core.PApplet;
import processing.core.PImage;

/**
* Manage Chaser
*/
public class Chaser extends Ghost {

    public Chaser(int x, int y, int speed, int[][] mark, int frightenedLength, int[] modes, int[] scatterTarMap, int sodaLength) {
        super(x, y, speed, mark, frightenedLength, modes, scatterTarMap, sodaLength);
    }

    /**
    * define Imgae of this ghost.
    *
    * @param app  a instance of App class(used for loadImage())
	*/
    public void defineImage(PApplet app) {
        this.ghostImage = app.loadImage("chaser.png");
    }
}