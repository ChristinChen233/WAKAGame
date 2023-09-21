package ghost;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;
import java.io.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.*;

/**
* Manage interaction of game elements (whole game).
*/
public class GameEngine {

    PApplet app;
    Player player;
    List<Fruit> fruits;
    List<Wall> walls;
    List<Ghost> ghosts;
    PImage playerRight;
    int graphsize;
    int speed;
    int frightenedLength;
    int playerLives;
    String mapName;
    GameMap gameMap;
    boolean win;
    boolean loose;
    boolean has_ghost;
    int[] modes;
    Ghost defaultChaser;
    boolean setUp;
    int sodaLength;
    
    /**
     * @param configFileName  The name of config file
     * @param app  Instance of PApplet(App) used for handle graphics
     */
    @SuppressWarnings("unchecked")
    public GameEngine(String configFileName, PApplet app) {
        this.setUp = false;
        this.has_ghost = false;
        this.app = app;
        this.fruits = new ArrayList<Fruit>();
        this.walls = new ArrayList<Wall>();
        this.ghosts = new ArrayList<Ghost>();
        this.graphsize = 16;
        this.win = false;
        this.loose = false;
        // prepare json
        try{
            FileReader reader = new FileReader(configFileName);
            JSONParser parser = new JSONParser();
            Object content = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) content;
            content = jsonObject.get("speed");
            Long long_num = (Long) content;
            this.speed = long_num.intValue();
            content = jsonObject.get("lives");
            long_num = (Long) content;
            this.playerLives = long_num.intValue();
            content = jsonObject.get("frightenedLength");
            long_num = (Long) content;
            this.frightenedLength = long_num.intValue();
            content = jsonObject.get("sodaLength");
            long_num = (Long) content;
            this.sodaLength = long_num.intValue();
            content = jsonObject.get("map");
            String str = (String) content;
            this.mapName = str;
            content = jsonObject.get("modeLengths");
            JSONArray nums = (JSONArray) content;
            Iterator<Long> iterator = nums.iterator();
            List<Integer> lengths = new ArrayList<Integer>();
            while (iterator.hasNext()) {
                long_num = (Long) iterator.next();
                Integer num = long_num.intValue();
                lengths.add(num);
            }
            this.modes = new int[lengths.size()];
            for(int i = 0; i < modes.length; i++) {
                modes[i] = lengths.get(i);
            }
        }catch(FileNotFoundException e) {
            return; }
        catch(IOException e) {
            return; }
        catch(ParseException e) {
            return;
        }

        this.gameMap = new GameMap(this.mapName);
        this.gameMap.ScatterCorner(); // set 4 corners used for ghosts
        this.setUp();
        this.defineImage();
    }

    /**
     * Set up the game elements.
     */
    public void setUp() {
        boolean hasWhim = false;
        for(int i = 0; i < 36; i++) {
            for(int j = 0; j < 28; j++) {
                // "0", "1-6" = wall, "7" = fruit, "p" = start, "w" = Whim
                // "8" = Superfruit, "9" = Soda, "c" = chaser, "a" = ambusher
                int x_cor = j*this.graphsize; // x = col
                int y_cor = i*this.graphsize; // y = row
                String symbol = this.gameMap.map[i][j];
                if(symbol.equals("0")) {
                    continue;
                }
                if(symbol.equals("7")) {
                    //fruit
                    Fruit fruit = new Fruit(x_cor, y_cor, this.app);
                    this.fruits.add(fruit);
                    continue;
                }
                if(symbol.equals("8")) {
                    //Super fruit
                    Fruit fruit = new SuperFruit(x_cor - 8, y_cor - 8, this.app);
                    this.fruits.add(fruit);
                    continue;
                }
                if(symbol.equals("9")) {
                    //Soda
                    Fruit fruit = new Soda(x_cor, y_cor, this.app);
                    this.fruits.add(fruit);
                    continue;
                }
                if(symbol.equals("p")) {
                    this.player = new Player(x_cor - 4, y_cor - 4, this.speed, this.playerLives, this.gameMap.markMap);
                    continue;
                }
                if(symbol.equals("c")) {
                    Ghost ghost = new Chaser(x_cor - 4, y_cor - 4, this.speed, this.gameMap.markMap ,this.frightenedLength, this.modes, this.gameMap.topLeft, this.sodaLength);
                    this.ghosts.add(ghost);
                    this.has_ghost = true;
                    this.defaultChaser = ghost;
                    continue;
                }
                if(symbol.equals("a")) {
                    Ghost ghost = new Ambusher(x_cor - 4, y_cor - 4, this.speed, this.gameMap.markMap ,this.frightenedLength, this.modes, this.gameMap.topRight, this.sodaLength);
                    this.ghosts.add(ghost);
                    this.has_ghost = true;
                    continue;
                }
                if(symbol.equals("i")) {
                    Ghost ghost = new Ignorant(x_cor - 4, y_cor - 4, this.speed, this.gameMap.markMap ,this.frightenedLength, this.modes, this.gameMap.downLeft, this.sodaLength);
                    this.ghosts.add(ghost);
                    this.has_ghost = true;
                    continue;
                }
                if(symbol.equals("w")) {
                    Ghost ghost = new Whim(x_cor - 4, y_cor - 4, this.speed, this.gameMap.markMap ,this.frightenedLength, this.modes, this.gameMap.downRight, this.sodaLength);
                    this.ghosts.add(ghost);
                    this.has_ghost = true;
                    hasWhim = true;
                    continue;
                }
                if(symbol.equals("1") || symbol.equals("2") || symbol.equals("3") || symbol.equals("4") || symbol.equals("5") || symbol.equals("6")) {
                    //wall
                    Wall wall = new Wall(symbol, x_cor, y_cor, this.app);
                    this.walls.add(wall);
                }
            }
        }

        if(this.has_ghost) {
            for(Ghost ghost : this.ghosts) {
                ghost.player = this.player;
                if(hasWhim) {
                    ghost.setChaser(this.defaultChaser);
                }
            }
        }
    }

    /**
     * Define images for elements in game
     */
    public void defineImage() {
        if(has_ghost) {
            for(Ghost ghost : ghosts) {
                ghost.defineImage(app);
                ghost.defineFrightImage(app);
            }
        }
        playerRight = app.loadImage("playerRight.png");
        PImage p1 = app.loadImage("playerClosed90.png");
        PImage p2 = app.loadImage("playerClosed.png");
        PImage p3 = app.loadImage("playerUp.png");
        PImage p4 = app.loadImage("playerDown.png");
        PImage p5 = app.loadImage("playerLeft.png");
        player.defineImage(p1, p2, p3, p4, p5, playerRight);
    }

    /**
     * Restart the game(set player and ghost position to thier born position when player die)
     */
    public void restart() {
        for(Ghost ghost : ghosts) {
            if(ghost.ghostDie) {
                continue;
            }
            ghost.x = ghost.startX;
            ghost.y = ghost.startY;
            ghost.mark = GameMap.copy_markMap(ghost.initial_mark);
            ghost.modes = GhostModes.copy_modes(ghost.initial_modes);
        }
        player.x = player.startX;
        player.y = player.startY;
        player.cur_direction = 4;
        if(player.playerLives == 1) {
            loose = true;
            player.playerLives--;
            return;
        }
        player.playerLives--;
        player.playerDie = false;
    }

    /**
     * Timmer: will pause the game for n seconds
     * @param n  n seconds need to be paused
     */
    public void timmer(int n) {
        Date date = new Date();
        double defaultTime = date.getTime()*0.001;
        while(true) {
            Date date2 = new Date();
            double now = date2.getTime()*0.001;
            if(now - defaultTime >= n) {
                break;
            }
        }
        setUp = true;
    }

    /**
     * Draw or not draw a line between ghost's and its target position
     * 
     * @param keyCode  The code of player(keyboard) input
     * @return if the keyCode is valid(if player tap the space)
     */
    public boolean drawLine(int keyCode) {
        if(has_ghost && keyCode == 32) {
            for(Ghost ghost : ghosts) {
                ghost.DEBUG();
            }
            return true;
        }
        return false;
    }

    /**
     * Handle logic of whole game, manage interactions among all elements
     */
    public void tick() {
        playerLives = player.playerLives;
        if(loose || win) {
            timmer(10);
            return;
        }
        if(has_ghost) {
            for(Ghost ghost : ghosts) {
                ghost.tick(app);
                if(player.playerDie) {
                    restart();
                    return;
                }
            }
        }
        player.tick();
        for(Fruit fruit : fruits) {
            if(fruit.eat_fruit(player)) {
                fruits.remove(fruit);
                break;
            }
        }
        if(fruits.size() == 0) {
            win = true;
        }
    }
    
    /**
     * Draw the current game
     */
    public void draw() {
        app.background(0, 0, 0);
        for(Wall wall : walls) {
            wall.draw(app);
        }
        for(Fruit fruit : fruits) {
            fruit.draw(app);
        }
        if(has_ghost) {
            for(Ghost ghost : ghosts) {
                ghost.draw(app);
            }
        }
        player.draw(app);
        if(playerLives <= 0) {
            return;
        }
        if(playerLives == 1) {
            app.image(playerRight, 0, 34*graphsize);
            return;
        }
        app.image(playerRight, 0, 34*graphsize);
        for(int i = 1; i < playerLives; i++) {
            app.image(playerRight, i*(graphsize + 10), 34*graphsize);
        }
    }
}