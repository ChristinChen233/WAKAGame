package ghost;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;
import java.io.*;

/**
* Manage SuperFruit
*/
public class SuperFruit extends Fruit {

    public SuperFruit(int x, int y, PApplet app) {
        super(x, y, app);
        this.fruit = app.loadImage("fruitSuper.png");
    }

    /**
    * Eat the super fruit. if eaten, set player.eatSuperFruit as true
    *
    * @return	if the fruit is eaten
    * @param player  the player who eat this fruit
	*/
    public boolean eat_fruit(Player player) {
        double dis = Math.sqrt(Math.pow(player.x - this.x, 2) + Math.pow(player.y - this.y, 2));
        if(dis <= 4 ) {
            player.eatSuperFruit = true;
            return true;
        }
        return false;
    }
}