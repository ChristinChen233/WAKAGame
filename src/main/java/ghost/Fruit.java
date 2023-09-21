package ghost;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;
import java.io.*;

/**
* Manage Fruit
*/
public class Fruit {
    int x;
    int y;
    PImage fruit;

    /**
     * @param x  The x value of fruit's position
     * @param y  The y value of fruit's position
     * @param app  Instance of PApplet(App) used for handle graphics
     */
    public Fruit(int x, int y, PApplet app) {
        this.x = x;
        this.y = y;
        this.fruit = app.loadImage("fruit.png");
    }
    
    /**
    * Draw the fruit.
    *
    * @param app  a instance of App class(used for drawing image)
	*/
    public void draw(PApplet app) {
            // habdle graphics
        app.image(this.fruit, this.x, this.y);
    }

    /**
    * Eat the fruit.
    *
    * @return	if the fruit is eaten
    * @param player  the player who eat this fruit
	*/
    public boolean eat_fruit(Player player) {
        double dis = Math.sqrt(Math.pow(player.x - this.x, 2) + Math.pow(player.y - this.y, 2));
        if(dis <= 8 ) {
            //be eaten
            return true;
        }
        return false;
    }
}