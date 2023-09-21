package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import java.util.*;
import java.io.*;

public class WallTest {
    @Test
    public void wallTest() {
        App app = new App();
        PApplet.runSketch(new String[]{""}, app);
        app.setup();
        Wall wall1 = new Wall("1", 3*16, 0, app);
        Wall wall2 = new Wall("2", 3*16, 0, app);
        Wall wall3 = new Wall("3", 3*16, 0, app);
        Wall wall4 = new Wall("4", 3*16, 0, app);
        Wall wall5 = new Wall("5", 3*16, 0, app);
        Wall wall6 = new Wall("6", 3*16, 0, app);
    }
}
