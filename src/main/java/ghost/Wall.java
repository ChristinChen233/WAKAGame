package ghost;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;
import java.io.*;

/**
* Manage Wall
*/
public class Wall {
    int x;
    int y;
    PImage wallImage;
    String symbol;

    /**
     * @param symbol  The symbol of this wall(1-6)
     * @param x  The x value of wall's position
     * @param y  The y value of wall's position
     * @param app  Instance of PApplet(App) used for handle graphics
     */
    public Wall(String symbol, int x, int y, PApplet app) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.wallImage = app.loadImage("horizontal.png");
        // default 1
        if(symbol.equals("2")) {
            this.wallImage = app.loadImage("vertical.png");
        }

        if(symbol.equals("3")) {
            this.wallImage = app.loadImage("upLeft.png");
        }

        if(symbol.equals("4")) {
            this.wallImage = app.loadImage("upRight.png");
        }

        if(symbol.equals("5")) {
            this.wallImage = app.loadImage("downLeft.png");
        }

        if(symbol.equals("6")) {
            this.wallImage = app.loadImage("downRight.png");
        }
    }

    /**
    * Draw the wall.
    *
    * @param app  a instance of App class(used for drawing image)
	*/
    public void draw(PApplet app) {
        // habdle graphics
        app.image(this.wallImage, this.x, this.y);
    }
}