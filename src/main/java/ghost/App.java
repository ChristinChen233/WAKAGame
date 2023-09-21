package ghost;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;

/**
* Drawing and run the game application
*/
public class App extends PApplet {

    static final int WIDTH = 448;
    static final int HEIGHT = 576;
    GameEngine game;
    PFont font;

    public App() {
        this.game = null;
        this.font = null;
    }

    public void setup() {
        frameRate(60);
        this.game = new GameEngine("config.json", this);
        this.font = createFont("PressStart2P-Regular.tff", 40);
    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void keyPressed() {
        game.player.cur_input(keyCode);
        game.drawLine(keyCode);
    }

    /**
	* Returns true if the word is sucessfully displayed.
    *
    * @param x  the x coordinate of the word
    * @param y  the y coordinate of the word
    * @param str  the word
	* @return	whether the word is sucessfully displayed
	*/
    public boolean displayWord(String str, int x, int y) {
        rect(-1, -1, WIDTH, HEIGHT);
        background(0, 0, 0);
        textFont(font);
        text(str, x, y);
        return true;
    }

    /**
	* Draw the game.
	*/
    public void draw() {
        if(game.setUp) {
            game.setUp = false;
            setup();
        }
        game.tick();
        game.draw();
        if(game.loose) {
            displayWord("GAME OVER", 92, 120);
        }
        if(game.win) {
            displayWord("YOU WIN", 125, 120);
        }
    }

    public static void main(String[] args) {
        PApplet.main("ghost.App");
    }
}
