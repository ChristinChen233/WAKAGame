package ghost;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;
import java.io.*;

/**
* Manage ghost modes
*/
public class GhostModes{
    int[] ghostModes;
    int cursor;
    String cur_mode;
    int frightenedLength;
    int sodaLength;
    int timeCounter1; // for other modes
    int timeCounter2; // for frightened mode
    int timeCounter3; //for soda-can(invisible ghost)

    /**
     * @param ghostModes  The modes' lengths of ghost(alternate between SCATER and CHASE)
     * @param frightenedLength  The length of ftightened mode of ghost
     * @param sodaLength  The time of keeping ghost invisible
     */
    public GhostModes(int[] ghostModes, int frightenedLength, int sodaLength) {
        this.ghostModes = ghostModes;
        this.cursor = 0;
        this.timeCounter1 = 0;
        this.timeCounter2 = 0;
        this.timeCounter3 = 0;
        this.cur_mode = "SCATTER";
        this.frightenedLength = frightenedLength;
        this.sodaLength = sodaLength;
    }

    /**
	* Returns current mode of given ghost
    *
    * @param ghost	The ghost which need to be calculated the current mode of it
	* @return	Current mode of given ghost
	*/
    public String getMode(Ghost ghost) {
        if(this.cursor % 2 == 1) {
            this.cur_mode = "CHASE";
        }
        if(this.cursor % 2 == 0) {
            this.cur_mode = "SCATTER";
        }
        if(ghost.frightened) {
            this.cur_mode = "frightened";
        }
        return this.cur_mode;
    }

    /**
	* Move the cursor. Let the mode length become next element of the modes List
    *
	* @return	Position of current cursor
	*/
    public int next() {
        if(this.cursor >= ghostModes.length) {
            this.cursor = -1;
        }
        this.cursor++;
        return this.cursor;
    }

    /**
	* Manage modes for given ghost
    *
    * @param ghost	The ghost which need to be managed
    * @param player	 The player(waka) in the same game
    * @param app  A instance of App class(used for counting current frame number)
	* @return	Current mode of given ghost
	*/
    public String time_manager(PApplet app, Ghost ghost, Player player) {
        if(app.frameCount < 60) {
            return this.getMode(ghost);
        }
        if(app.frameCount % 60 == 0) { //calculate second
            if(ghost.frightened) {
                this.timeCounter2++;
            }
            else {
                this.timeCounter1++;
            }
            if(ghost.invisible) {
                this.timeCounter3++;
            }
        }

        if(ghost.frightened && this.timeCounter2 >= this.frightenedLength) { // end this mode
            this.timeCounter2 = 0;
            ghost.frightened = false;
            player.eatSuperFruit = false;
        }
        if(!ghost.frightened && this.timeCounter1 >= this.ghostModes[cursor]) {
            this.timeCounter1 = 0;
            ghost.mark = GameMap.copy_markMap(ghost.initial_mark);
            // when the mode alternate, should initial ghost's mark map
            this.next();
        }
        if(ghost.invisible && this.timeCounter3 >= sodaLength) {
            player.eatSoda = false;
            ghost.invisible = false;
            this.timeCounter3 = 0;
        }
        return this.getMode(ghost);
    }
    
    /**
	* Copy the given GhostModes and return a new GhostModes with different address
    *
    * @param modes	The given GhostModes which need to be copied
	* @return	The copied GhostModes
	*/
    public static GhostModes copy_modes(GhostModes modes) {
        GhostModes retModes = new GhostModes(modes.ghostModes, modes.frightenedLength, modes.sodaLength);
        return retModes;
    }

}