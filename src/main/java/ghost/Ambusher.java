package ghost;

import processing.core.PApplet;
import processing.core.PImage;

/**
* Manage Ambbusher
*/
public class Ambusher extends Ghost {

    public Ambusher(int x, int y, int speed, int[][] mark, int frightenedLength, int[] modes, int[] scatterTarMap, int sodaLength) {
        super(x, y, speed, mark, frightenedLength, modes, scatterTarMap, sodaLength);
    }

    /**
	* Returns int[] array target position(for drawing line) of SCATTER mode. The first element is x value, 2nd is y value.
	*
	* @return	Target position of Scatter mode
	*/
    public int[] keepScatterTar() {
        return new int[]{448, 0};
    }

    /**
	* Returns int[] array target position(reachable and closest to real target) of CHASE mode. The first element is x value, 2nd is y value.
	*
	* @return	Target position of CHASE mode
	*/
    public int[] Chase() {
        int[] tar = GridAhead_WAKA(4);
        this.curT[0] = tar[0];
        this.curT[1] = tar[1];
        int tar_i = tar[1]/16;
        int tar_j = tar[0]/16;
        if(tar[0] % 16 > 0) {
            tar_j++;
        }
        if(tar[1] % 16 > 0) {
            tar_i++;
        }
        tar = GameMap.nearest(tar_i, tar_j, this.initial_mark);
        this.tarX = tar[1]*16 - 4;
        this.tarY = tar[0]*16 - 4;
        return new int[]{this.tarX, this.tarY};
    }

    /**
    * define Imgae of this ghost.
    *
    * @param app  a instance of App class(used for loadImage())
	*/
    public void defineImage(PApplet app) {
        this.ghostImage = app.loadImage("ambusher.png");
    }
}