package ghost;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;
import java.io.*;

/**
* Manage Whim
*/
public class Whim extends Ghost {

    Ghost chaser;

    public Whim(int x, int y, int speed, int[][] mark, int frightenedLength, int[] modes, int[] scatterTarMap, int sodaLength) {
        super(x, y, speed, mark, frightenedLength, modes, scatterTarMap, sodaLength);
    }

    /**
	* Returns int[] array target position(reachable and closest to real target) of CHASE mode. The first element is x value, 2nd is y value.
	*
	* @return	Target position of CHASE mode
	*/
    public int[] keepScatterTar() {
        return new int[]{448, 576};
    }

    /**
	* Returns int[] array target position(reachable and closest to real target) of CHASE mode. The first element is x value, 2nd is y value.
	*
	* @return	Target position of CHASE mode
	*/
    public int[] Chase() {
        int[] gridAhead = GridAhead_WAKA(2);
        this.tarX = chaser.x + 2*(gridAhead[0] - chaser.x);
        this.tarY = chaser.y + 2*(gridAhead[1] - chaser.y);
        this.curT[0] = this.tarX;
        this.curT[1] = this.tarY;
        int tar_i = this.tarY/16;
        int tar_j = this.tarX/16;
        if(tarX % 16 > 0) {
            tar_j++;
        }
        if(tarY % 16 > 0) {
            tar_i++;
        }
        int[] tar = GameMap.nearest(tar_i, tar_j, this.initial_mark);
        this.tarX = tar[1]*16 - 4;
        this.tarY = tar[0]*16 - 4;
        return new int[]{this.tarX, this.tarY};
    }

    /**
	* Sets the Chaser ghost for Whim ghost to calculate target position of CHASE mode.
    *
    * @param chaser the Chaser ghost for Whim ghost to calculate target position of CHASE mode
	* @return	The set Chaser ghot
	*/
    public Ghost setChaser(Ghost chaser) {
        this.chaser = chaser;
        return this.chaser;
    }

    /**
    * define Imgae of this ghost.
    *
    * @param app  a instance of App class(used for loadImage())
	*/
    public void defineImage(PApplet app) {
        this.ghostImage = app.loadImage("whim.png");
    }
}