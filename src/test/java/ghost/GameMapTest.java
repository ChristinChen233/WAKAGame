package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import java.util.*;
import java.io.*;

public class GameMapTest {
    @Test
    public void constructorTest() {
        GameMap gmTest = new GameMap("map.txt");
        assertNotNull(gmTest);
        GameMap gmNotFoundTest = new GameMap("what.txt");
    }
}