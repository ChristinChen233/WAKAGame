package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import java.util.*;
import java.io.*;

public class FruitTest {
    @Test
    public void SuperFruitTest() {
        App app = new App();
        PApplet.runSketch(new String[]{""}, app);
        app.setup();
        Fruit superFruitTest = new SuperFruit(16 - 8, 4*16 - 8, app);
        GameMap gameMap = new GameMap("map.txt");
        Player playerTest = new Player(8, 60, 1, 3, gameMap.markMap);
        assertTrue(superFruitTest.eat_fruit(playerTest));
        assertTrue(playerTest.eatSuperFruit);
        playerTest.x = 10;
        playerTest.y = 64;
        assertFalse(superFruitTest.eat_fruit(playerTest));
    }

    @Test
    public void SodaTest() {
        App app = new App();
        PApplet.runSketch(new String[]{""}, app);
        app.setup();
        Fruit sodaTest = new Soda(16, 4*16, app);
        GameMap gameMap = new GameMap("map.txt");
        Player playerTest = new Player(16, 4*16 + 8, 1, 3, gameMap.markMap);
        assertTrue(sodaTest.eat_fruit(playerTest));
        assertTrue(playerTest.eatSoda);
        playerTest.x = 25;
        playerTest.y = 4*16;
        assertFalse(sodaTest.eat_fruit(playerTest));
    }

    @Test
    public void fruitTest() {
        App app = new App();
        PApplet.runSketch(new String[]{""}, app);
        app.setup();
        Fruit fruitTest = new Fruit(16, 4*16, app);
        GameMap gameMap = new GameMap("map.txt");
        Player playerTest = new Player(16, 4*16 + 8, 1, 3, gameMap.markMap);
        assertTrue(fruitTest.eat_fruit(playerTest));
        playerTest.x = 25;
        playerTest.y = 4*16;
        assertFalse(fruitTest.eat_fruit(playerTest));
    }
}