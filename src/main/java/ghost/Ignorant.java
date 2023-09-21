package ghost;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;
import java.io.*;

/**
* Manage Ignorant
*/
public class Ignorant extends Ghost {

    public Ignorant(int x, int y, int speed, int[][] mark, int frightenedLength, int[] modes, int[] scatterTarMap, int sodaLength) {
        super(x, y, speed, mark, frightenedLength, modes, scatterTarMap, sodaLength);
    }

    /**
	* Returns int[] array Target position(for drawing line) of SCATTER mode. The first element is x value, 2nd is y value.
	*
	* @return	Target position of Scatter mode
	*/
    public int[] keepScatterTar() {
        return new int[]{0, 576};
    }

    /**
	* Returns int array of Target position(reachable and closest to real target) of CHASE mode The first element is x value, 2nd is y value.
	*
	* @return	Target position of CHASE mode
	*/
    public int[] Chase() {
        double dis = Math.sqrt(Math.pow(this.player.x - this.x, 2) + Math.pow(this.player.y - this.y, 2));
        if(dis > 8*16) {
            int[] tar = new int[]{this.player.x, this.player.y};
            this.curT[0] = tar[0] + 16;
            this.curT[1] = tar[1] + 16;
            // add 16 to make line clearer when draw line
            this.tarX = tar[0];
            this.tarY = tar[1];
        }
        else{
            this.curT = this.keepScatterTar();
            this.tarX = this.scatterTarMap[0];
            this.tarY = this.scatterTarMap[1];
        }
        return new int[]{this.tarX, this.tarY};
    }

    /**
    * define Imgae of this ghost.
    *
    * @param app  a instance of App class(used for loadImage())
	*/
    public void defineImage(PApplet app) {
        this.ghostImage = app.loadImage("ignorant.png");
    }
}