package ghost;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;
import java.io.*;

/**
* Manage Soda
*/
public class Soda extends Fruit {

    public Soda(int x, int y, PApplet app) {
        super(x, y, app);
        this.fruit = app.loadImage("soda.png");
    }
    
    /**
    * Eat the soda. if can eat, then eat it, and set player.eatSoda as true
    *
    * @return	if the fruit is eaten
    * @param player  the player who eat this fruit
	*/
    public boolean eat_fruit(Player player) {
        double dis = Math.sqrt(Math.pow(player.x - this.x, 2) + Math.pow(player.y - this.y, 2));
        if(dis <=  8) {
            player.eatSoda = true;
            return true;
        }
        return false;
    }
}