package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import java.util.*;
import java.io.*;

class GameEngineTest {

    @Test 
    public void constructorTest() {
        App constructorTestapp = new App();
        PApplet.runSketch(new String[]{""}, constructorTestapp);
        constructorTestapp.setup();
        GameEngine gETest = new GameEngine("config.json", constructorTestapp);
        assertNotNull(gETest);
        assertNotNull(gETest.gameMap);
        assertTrue(Arrays.equals(new int[]{7,20,7,20,5,20,5,1000}, gETest.modes));
        assertEquals((int)1, gETest.speed);
        assertEquals((int)3, gETest.playerLives);
        assertEquals((int)4, gETest.frightenedLength);
        assertEquals("map.txt", gETest.mapName);
        assertFalse(gETest.drawLine(31));
        assertTrue(gETest.drawLine(32));
        GameEngine gENotFoundTest = new GameEngine("what.json", constructorTestapp);
    }

    @Test
    public void drawLineTest() {
        App app = new App();
        PApplet.runSketch(new String[]{""}, app);
        app.setup();
        GameEngine gETest = new GameEngine("config.json", app);
        assertFalse(gETest.drawLine(31));
        assertTrue(gETest.drawLine(32));
    }

    @Test
    public void noGhostTest() {
        App app = new App();
        PApplet.runSketch(new String[]{""}, app);
        app.setup();
        GameEngine noGhostgETest = new GameEngine("config1.json", app);
        assertFalse(noGhostgETest.drawLine(31));
        assertFalse(noGhostgETest.drawLine(32));
    }

    @Test
    public void noWhimTest() {
        App app = new App();
        PApplet.runSketch(new String[]{""}, app);
        app.setup();
        GameEngine noWhimgETest = new GameEngine("config2.json", app);
    }

    @Test
    public void restartTest() {
        App app = new App();
        PApplet.runSketch(new String[]{""}, app);
        app.setup();
        GameEngine gETest2 = new GameEngine("config.json", app);
        gETest2.player.x = 463;
        gETest2.player.y = 481;
        gETest2.player.cur_direction = 3;
        gETest2.ghosts.get(0).x = 24;
        gETest2.ghosts.get(0).y = 36;
        gETest2.restart();
        assertEquals(4, gETest2.player.cur_direction);
        assertEquals(gETest2.player.startX, gETest2.player.x);
        assertEquals(gETest2.player.startY, gETest2.player.y);
        assertEquals(gETest2.ghosts.get(0).startX, gETest2.ghosts.get(0).x);
        assertEquals(gETest2.ghosts.get(0).startY, gETest2.ghosts.get(0).y);
        gETest2.ghosts.get(0).x = 24;
        gETest2.ghosts.get(0).y = 36;
        gETest2.ghosts.get(0).ghostDie = true;
        gETest2.restart();
        assertEquals(24, gETest2.ghosts.get(0).x);
        assertEquals(36, gETest2.ghosts.get(0).y);
        gETest2.restart();
        assertEquals(0, gETest2.player.playerLives);
        assertTrue(gETest2.loose);
    }

    @Test
    public void tickTest1() {
        App app = new App();
        PApplet.runSketch(new String[]{""}, app);
        app.setup();
        GameEngine gETest3 = new GameEngine("config.json", app);
        gETest3.tick();
        gETest3.player.playerDie = true;
        gETest3.tick();
        gETest3.player.playerDie = false;
        gETest3.loose = true;
        gETest3.tick();
        gETest3.loose = false;
        GameEngine gETest4 = new GameEngine("config2.json", app);
        gETest4.tick();
        GameEngine gETest5 = new GameEngine("config1.json", app);
        gETest5.tick();
    }

    @Test
    public void tickTest2() {
        App app = new App();
        PApplet.runSketch(new String[]{""}, app);
        app.setup();
        GameEngine gETest6 = new GameEngine("config3.json", app);
        assertEquals(2, gETest6.fruits.size());
        gETest6.tick();
        gETest6.tick();
        gETest6.tick();
        gETest6.tick();
        gETest6.tick();
        gETest6.tick();
        gETest6.tick();
        gETest6.tick();
        assertEquals(1, gETest6.fruits.size());
        gETest6.fruits.remove(0);
        gETest6.tick();
        assertTrue(gETest6.win);
    }
}
