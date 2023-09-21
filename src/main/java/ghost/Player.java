package ghost;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;
import java.io.*;

/**
* Manage Waka
*/
public class Player {
    int x;
    int y;
    int startX;
    int startY;
    PImage playerOpen; //open mounth one
    PImage playerClosed90; // horizontal one
    PImage playerClosed0; // vertical one
    PImage playerUp;
    PImage playerDown;
    PImage playerLeft;
    PImage playerRight;
    int speed;
    int cur_direction;
    int open;
    int playerLives;
    List<Integer> playerInput;
    HashMap<Integer, Integer> movements;
    boolean eatSuperFruit;
    boolean eatSoda;
    boolean playerDie;
    int[][] mark;
    boolean stay;
    boolean ifOpen;
    
    /**
     * @param x  The x value of Waka's born position
     * @param y  The y value of Waka's born position
     * @param speed  The speed of moving Waka
     * @param playerLives  The lives of Waka
     * @param mark  The markMap represented the game map(0 = can go, otherwise, 1)
     */
    public Player(int x, int y, int speed, int playerLives, int[][] mark) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.eatSuperFruit = false;
        this.eatSoda = false;
        this.speed = speed;
        this.open = 8;
        this.playerInput = new ArrayList<Integer>();
        this.movements = new HashMap<Integer, Integer>();
        // 1 = up, 2 = down, 3 = right, 4 = left
        movements.put(38, 1); movements.put(40, 2);
        movements.put(39, 3); movements.put(37, 4);
        this.playerLives = playerLives;
        this.cur_direction = 4;
        this.playerDie = false;
        this.mark = GameMap.copy_markMap(mark);
        this.stay = false;
        this.ifOpen = true;
    }

    /**
    * define Imgae of this player, included 4 facing Images and closed player
    *
    * @param p1  graph of cloased player where used when player moving vertically.
    * @param p2  graph of cloased player where used when player moving horizontally.
    * @param p3  graph of open month player where used when player facing up.
    * @param p4  graph of open month player where used when player facing dowm.
    * @param p5  graph of open month player where used when player facing left.
    * @param p6  graph of open month player where used when player facing right.
	*/
    public void defineImage(PImage p1, PImage p2, PImage p3, PImage p4, PImage p5, PImage p6) {
        this.playerClosed90 = p1;
        this.playerClosed0 = p2;
        this.playerUp = p3;
        this.playerDown = p4;
        this.playerLeft = p5;
        this.playerRight = p6;
    }

    /**
	* Be called when player tap the keyboard. The current input will be collected if valid(arrow keys)
    *
    * @param input  player's input
	* @return	if the input is valid
	*/
    public boolean cur_input(int input) {
        if(this.movements.containsKey(input)){
            this.playerInput.add(this.movements.get(input));
            return true;
        }
        return false;
    }
    
    /**
    * Hadle the logics. decide if player input
    * can be applied for moving Waka, automatically moveing waka,
    * manage Waka's closeing and opening month
	*/
    public void tick() {
        this.stay = false;
        if(this.playerInput.size() != 0){
            int newMove = this.playerInput.get(this.playerInput.size() - 1);
            if(this.can_turn(newMove)) {
                this.cur_direction = newMove;
                this.playerInput.clear();
            }
        }
        if(this.x % 16 == 12 && this.y % 16 == 12 && !valid_move(this.cur_direction)) {
            this.stay = true;
        }
        this.move();
        if(this.open == -7) {
            this.open = 8;
            this.ifOpen = false;
            return;
        }

        if(this.open > 0) {
            this.ifOpen = true;
        }
        else{
            this.ifOpen = false;
        }
        this.open--;
    }

    /**
    * Draw the waka. handle direction that waka facing toward
    *
    * @param app  a instance of App class(used for loadImage())
	*/
    public void draw(PApplet app) {
        // choose graphics first
        if(this.cur_direction == 1) {
            this.playerOpen = this.playerUp;
        }
        if(this.cur_direction == 2) {
            this.playerOpen = this.playerDown;
        }
        if(this.cur_direction == 3) {
            this.playerOpen = this.playerRight; 
        }
        if(this.cur_direction == 4) {
            this.playerOpen = this.playerLeft;
        }
        
        if(this.ifOpen) {
            app.image(this.playerOpen, this.x, this.y);
        }
        else if(this.cur_direction == 3 || this.cur_direction == 4) {
            app.image(this.playerClosed90, this.x, this.y);
        }
        else {
            app.image(this.playerClosed0, this.x, this.y);
        }
    }

    /**
    * Move or stop the waka. Based on curent direction
    * @return	if the ghost moved.
	*/
    public boolean move() {
        if(this.stay) {
            // stay
            return false;
        }
        // 1 = up, 2 = down, 3 = right, 4 = left 
        if(this.cur_direction == 1) {
            this.y -= this.speed;
        }
        if(this.cur_direction == 2) {
            this.y += this.speed;
        }
        if(this.cur_direction == 3) {
            this.x += this.speed;    
        }
        if(this.cur_direction == 4) {
            this.x -= this.speed;
        }
        return true;
    }

    /**
    * Returns if the new direction can go.(if new move is valid)
    *
    * @return	if the new direction can go.
    * @param move	the new posibble direction or new movement
	*/
    public boolean valid_move(int move) {
        int i = this.y/16 + 1;
        int j = this.x/16 + 1;
        // 1 = up, 2 = down, 3 = right, 4 = left 
        if(move == 1 && this.mark[i - 1][j] == 0) {
            //can go up
            return true;
        }
        if(move == 2 && this.mark[i + 1][j] == 0) {
            //can go down
            return true;
        }
        if(move == 4 && this.mark[i][j - 1] == 0) {
            //can go left
            return true;
        }
        if(move == 3 && this.mark[i][j + 1] == 0) {
            //can go right
            return true;
        }
        return false;
    }

    /**
    * Returns if the waka can turn
    *
    * @param newMove	the new posibble direction or new movement
    * @return	if the waka can turn
	*/
    public boolean can_turn(int newMove) {
        // waka can always go back
        // 1 = up, 2 = down, 3 = right, 4 = left
        if(this.cur_direction == 1 && newMove == 2) {
            return true; 
        }
        else if(this.cur_direction == 3 && newMove == 4) {
            return true; 
        }
        else if(this.cur_direction == 2 && newMove == 1) {
            return true; 
        }
        else if(this.cur_direction == 4 && newMove == 3) {
            return true; 
        }
        else if(this.x % 16 == 12 && this.y % 16 == 12 && this.valid_move(newMove)){
            return true;
        }
        else{
            return false;
        }
    }
}