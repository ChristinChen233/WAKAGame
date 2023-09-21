package ghost;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;
import java.io.*;
import org.json.simple.JSONObject;

/**
* Manage Gost's behaviour
*/
public class Ghost {

    enum RelativePosition {
        TOPLEFT{public int next_move(int xGapAbs, int yGapAbs, int cur_direct, List<Integer> moves) {
            if(xGapAbs <= yGapAbs) {
                if(moves.contains(2)) {
                    return 2;
                }
                else if (moves.contains(3)) {
                    return 3;
                }
            }
            if(xGapAbs > yGapAbs) {
                if(moves.contains(3)) {
                    return 3;
                }
                else if (moves.contains(2)) {
                    return 2;
                }
            }
            return choose_move(cur_direct, moves);
        }
        },
        DOWNLEFT{public int next_move(int xGapAbs, int yGapAbs, int cur_direct, List<Integer> moves) {
                if(xGapAbs <= yGapAbs) {
                    if(moves.contains(1)) {
                        return 1;
                    }
                    else if (moves.contains(3)) {
                        return 3;
                    }
                }
                if(xGapAbs > yGapAbs) {
                    if(moves.contains(3)) {
                        return 3;
                    }
                    else if (moves.contains(1)) {
                        return 1;
                    }
                }
                return choose_move(cur_direct, moves);
            }
        },
        TOPRIGHT{public int next_move(int xGapAbs, int yGapAbs, int cur_direct, List<Integer> moves) {
            if(xGapAbs <= yGapAbs) {
                if(moves.contains(2)) {
                    return 2;
                }
                else if (moves.contains(4)) {
                    return 4;
                }
            }
            if(xGapAbs > yGapAbs) {
                if(moves.contains(4)) {
                    return 4;
                }
                else if (moves.contains(2)) {
                    return 2;
                }
            }
            return choose_move(cur_direct, moves);
        }},
        DOWNRIGHT{public int next_move(int xGapAbs, int yGapAbs, int cur_direct, List<Integer> moves) {
            if(xGapAbs <= yGapAbs) {
                if(moves.contains(1)) {
                    return 1;
                }
                else if (moves.contains(4)) {
                    return 4;
                }
            }
            if(xGapAbs > yGapAbs) {
                if(moves.contains(4)) {
                    return 4;
                }
                else if (moves.contains(1)) {
                    return 1;
                }
            }
            return choose_move(cur_direct, moves);
        }},
        LEFT{public int next_move(int xGapAbs, int yGapAbs, int cur_direct, List<Integer> moves) {
            if(moves.contains(3)) {
                return 3;
            }
            if(moves.contains(1) && moves.contains(2) && cur_direct == 3) {
                Random rand = new Random();
                int randIndex = rand.nextInt(2);
                return moves.get(randIndex);
            }
            if(cur_direct != 3) {
                return choose_move(cur_direct, moves); //never go back
            }
            if(moves.contains(1)) {
                return 1;
            }
            return 2;
        }},
        RIGHT{public int next_move(int xGapAbs, int yGapAbs, int cur_direct, List<Integer> moves) {
            if(moves.contains(4)) {
                return 4;
            }
            if(moves.contains(1) && moves.contains(2) && cur_direct == 4) {
                Random rand = new Random();
                int randIndex = rand.nextInt(2);
                return moves.get(randIndex);
            }
            if(cur_direct != 4) {
                return choose_move(cur_direct, moves); //never go back
            }
            if(moves.contains(1)) {
                return 1;
            }
            return 2;
        }},
        UP{public int next_move(int xGapAbs, int yGapAbs, int cur_direct, List<Integer> moves) {
            if(moves.contains(2)) {
                return 2;
            }
            if(moves.contains(3) && moves.contains(4) && cur_direct == 2) {
                Random rand = new Random();
                int randIndex = rand.nextInt(2);
                return moves.get(randIndex);
            }
            if(cur_direct != 2) {
                return choose_move(cur_direct, moves); //never go back
            }
            if(moves.contains(4)) {
                return 4;
            }
            return 3;
        }},
        DOWN{public int next_move(int xGapAbs, int yGapAbs, int cur_direct, List<Integer> moves) {
            if(moves.contains(1)) {
                return 1;
            }
            if(moves.contains(3) && moves.contains(4) && cur_direct == 1) {
                Random rand = new Random();
                int randIndex = rand.nextInt(2);
                return moves.get(randIndex);
            }
            if(cur_direct != 1) {
                return choose_move(cur_direct, moves); //never go back
            }
            if(moves.contains(3)) {
                return 3;
            }
            return 4;
        }};
    
        /**
	    * Returns the next move of ghost.
        *
        * @param xGapAbs  the absolute difference value of ghost's and target position's x value
        * @param yGapAbs  the absolute difference value of ghost's and target position's y value
        * @param cur_direct  current direction of ghost
        * @param moves  valid movements ghost can make
	    * @return	the next move ghost will do
	    */
        public abstract int next_move(int xGapAbs, int yGapAbs, int cur_direct, List<Integer> moves);
    
        /**
	    * Returns the next move of ghost.
        *
        * @param cur_direct  current direction of ghost
        * @param moves  valid movements ghost can make
	    * @return	the move ghost will do(never go back)
	    */
        public int choose_move(int cur_direct, List<Integer> moves) {
            int invertMove = invert(cur_direct);
            for(int move : moves) {
                if(move != invertMove) {
                    return move;
                }
            }
            return invertMove;
        }
    
        /**
	    * Returns the oposite direction of current direction
        *
        * @param num  current direction of ghost
	    * @return	the oposite direction of current direction
	    */
        public int invert(int num) {
            if(num == 1) {return 2;}
            if(num == 2) {return 1;}
            if(num == 3) {return 4;}
            if(num == 4) {return 3;}
            return 0;
        }
    }

    PImage ghostImage;
    PImage FrightImage;
    int x;
    int y;
    int tarX;
    int tarY;
    int startX;
    int startY;
    int speed;
    int cur_direction;
    GhostModes modes;
    GhostModes initial_modes;
    Player player;
    boolean frightened;
    boolean ghostDie;
    boolean invisible;
    int sodaLength;
    int[][] mark;
    int[][] initial_mark;
    int[] scatterTarMap; // target position of scatter mode in map
    int[] curT; // current target position of current mode
    RelativePosition position;
    boolean drawLine; // used for debug mode
    // 1 = up, 2 = down, 3 = right, 4 = left 

    /**
     * @param x  The x value of ghost's born position
     * @param y  The y value of ghost's born position
     * @param speed  The speed of moving ghost
     * @param frightenedLength  The length of ftightened mode of ghost
     * @param sodaLength  The time of keeping ghost invisible
     * @param mark  The markMap represented the game map(0 = can go, otherwise, 1)
     * @param modes  The modes' lengths of ghost(alternate between SCATER and CHASE)
     * @param scatterTarMap  The reachable and closest position of ghost's SCATTER mode
     */
    public Ghost(int x, int y, int speed, int[][] mark, int frightenedLength, int[] modes, int[] scatterTarMap, int sodaLength) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.tarX = 0;
        this.tarY = 0;
        this.cur_direction = 4;
        this.speed = speed;
        this.mark = GameMap.copy_markMap(mark);
        this.sodaLength = sodaLength;
        this.modes = new GhostModes(modes, frightenedLength, sodaLength);
        this.initial_modes = GhostModes.copy_modes(this.modes);
        this.initial_mark = GameMap.copy_markMap(mark);
        this.cur_direction = 4;
        this.ghostDie = false;
        this.position = RelativePosition.LEFT;
        this.curT = new int[2];
        this.scatterTarMap = scatterTarMap;
        this.drawLine = false;
        this.invisible = false;
    }

    /**
    * define Imgae of this ghost.
    *
    * @param app  a instance of App class(used for loadImage())
	*/
    public void defineImage(PApplet app) {
    }

    /**
    * Handle logic of ghost(manage movements/modes/interaction with player)
    *
    * @param app  a instance of App class(used for counting drawn frame)
	*/
    public void tick(PApplet app) {
        if(this.ghostDie){
            return;
        }
        if(this.player.eatSuperFruit) {
            this.frightened = true;
        }
        if(this.player.eatSoda) {
            this.invisible = true;
        }
        if(Math.sqrt(Math.pow(this.player.x - this.x, 2) + Math.pow(this.player.y - this.y, 2)) <= 16) {
            if(this.frightened) {
                this.ghostDie = true;
                return;
            }
            this.player.playerDie = true;
            return;
        }
        
        modes.time_manager(app, this, this.player);
        String cur_mode = modes.getMode(this);

        if(this.x % 16 == 12 && this.y % 16 == 12) {
            // each time if the ghost need to decide his next movements,
            // his cur cordinate should both x % 16 == 12 && y % 16 == 12
            // and should have 3 posible ways to go
            List<Integer> validMoves = this.valid_moves(this.mark);
            if(validMoves.size() == 0) {
                //This situation actually only happens when 4 directions are marked
                // as 1. Then ghost should initial its mark map.
                this.mark = GameMap.copy_markMap(this.initial_mark);
                validMoves = this.valid_moves(this.mark);
            }
            this.next_move(cur_mode, validMoves);
        }
        this.move();
    }

    /**
    * Handle graphics of ghost(manage which graph to display)
    *
    * @param app  a instance of App class(used for drawing)
	*/
    public void draw(PApplet app) {
        if(this.ghostDie){
            return;
        }
        if(this.drawLine) {
            app.stroke(225);
            app.line(this.x + 14, this.y + 14, this.curT[0], this.curT[1]);
        }
        if(this.invisible) {
            return;
        }
        if (this.frightened) {
            app.image(this.FrightImage, this.x, this.y);
            return;
        }
        app.image(this.ghostImage, this.x, this.y);
    }

    /**
    * Move the ghost
	*/
    public void move() {
        if(this.cur_direction == 0) {
            // stay
            return;
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
    }

    /**
    * select the next movemment for ghost
    * @param cur_mode  current mode of ghost
    * @param validMoves  the valid and possible movements ghost can make
	*/
    public void next_move(String cur_mode, List<Integer> validMoves) {
        Random rand = new Random();
        int randIndex = rand.nextInt(validMoves.size());
        
        if(cur_mode.equals("frightened")) {
            this.cur_direction = validMoves.get(randIndex);
            this.curT[0] = 0;
            this.curT[1] = 0;
            return;
        }

        if(cur_mode.equals("SCATTER")) {
            this.curT = this.keepScatterTar();
            this.tarX = scatterTarMap[0];
            this.tarY = scatterTarMap[1];
        }

        if(cur_mode.equals("CHASE")) {
            this.Chase();
        }

        this.choose_move(this.tarX, this.tarY, validMoves);
        //choose best movements that ghost can make
    }

    /**
	* Returns int[] array target position(reachable and closest to real target) of CHASE mode. The first element is x value, 2nd is y value.
	*
	* @return	Target position of CHASE mode
	*/
    public int[] Chase() {
        /* this function is for calculating waka's targert position
        in CHASE mode. will be different in different ghost,
        here the default one is for Chaser */
        int[] tar = new int[]{this.player.x, this.player.y};
        this.curT[0] = tar[0] + 16;
        this.curT[1] = tar[1] + 16;
        this.tarX = tar[0];
        this.tarY = tar[1];
        return new int[]{this.tarX, this.tarY};
    }

    /**
    * select the next and best movemment for ghost
    *
    * @param tarX  x value of target position
    * @param tarY  y value of target position
    * @param validMoves  the valid and possible movements ghost can make
	*/
    public void choose_move(int tarX, int tarY, List<Integer> validMoves) {
        int new_move = 0;

        if(this.x == tarX && this.y == tarY) {
            // if on the closest position to target, stay there.
            this.cur_direction = 0;
            return;
        }

        if(validMoves.size() == 1){
            this.cur_direction = validMoves.get(0);
            this.mark[this.y/16 + 1][this.x/16 + 1] = 1;
            // culdesac, should be marked as "wall"
            return;
        }

        int xGapAbs = Math.abs(tarX - this.x);
        int yGapAbs = Math.abs(tarY - this.y);

        if(tarX - this.x > 0 && tarY == this.y) {
            this.position = RelativePosition.LEFT;
            new_move = position.next_move(0, 0, this.cur_direction, validMoves);
        }
        if(tarX - this.x < 0 && tarY == this.y) {
            this.position = RelativePosition.RIGHT;
            new_move = position.next_move(0, 0, this.cur_direction, validMoves);
        }
        if(tarY - this.y < 0 && tarX == this.x) {
            this.position = RelativePosition.DOWN;
            new_move = position.next_move(0, 0, this.cur_direction, validMoves);
        }
        if(tarY - this.y > 0 && tarX == this.x) {
            this.position = RelativePosition.UP;
            new_move = position.next_move(0, 0, this.cur_direction, validMoves);
        }

        if(tarX - this.x < 0 && tarY - this.y > 0) {
            this.position = RelativePosition.TOPRIGHT;
            new_move = position.next_move(xGapAbs, yGapAbs, this.cur_direction, validMoves);
        }
        if(tarX - this.x < 0 && tarY - this.y < 0) {
            this.position = RelativePosition.DOWNRIGHT;
            new_move = position.next_move(xGapAbs, yGapAbs, this.cur_direction, validMoves);
        }
        if(tarX - this.x > 0 && tarY - this.y < 0) {
            this.position = RelativePosition.DOWNLEFT;
            new_move = position.next_move(xGapAbs, yGapAbs, this.cur_direction, validMoves);
        }
        if(tarX - this.x > 0 && tarY - this.y > 0) {
            this.position = RelativePosition.TOPLEFT;
            new_move = position.next_move(xGapAbs, yGapAbs, this.cur_direction, validMoves);
        }
        
        if(new_move == position.invert(this.cur_direction)) {
            this.mark[this.y/16 + 1][this.x/16 + 1] = 1;
            // ghost will never go back, if it gose back then mark this place as "wall"
        }
        this.cur_direction = new_move;
    }

    /**
	* Returns a List of valid and possible movements ghost can make based on ghost's current position
    *
    * @param curMark  The mark map for find validMoves(2-D array with 1 and 0 in it, 1 means can't go, otherwise, 0)
	* @return	A List of valid and possible movements ghost can make
	*/
    public List<Integer> valid_moves(int[][] curMark) {
        // return a list of next movements that ghosts can go 
        int i = this.y/16 + 1;
        int j = this.x/16 + 1;
        List<Integer> retLs = new ArrayList<Integer>();
        // 1 = up, 2 = down, 3 = right, 4 = left 
        if(curMark[i - 1][j] == 0) {
            //can go up
            retLs.add(1);
        }
        if(curMark[i + 1][j] == 0) {
            //can go down
            retLs.add(2);
        }
        if(curMark[i][j - 1] == 0) {
            //can go left
            retLs.add(4);
        }
        if(curMark[i][j + 1] == 0) {
            //can go right
            retLs.add(3);
        }
        return retLs;
    }

    /**
    * Returns int[] array of n grids ahead Waka based on Waka's current direction, the first element is x value, 2nd is y value.
    * @param grids  The number of grids ahead Waka
	* @return	int[] array of n grids ahead Waka
	*/
    public int[] GridAhead_WAKA(int grids) {
        int tarX = 0;
        int tarY = 0;
        if(this.player.cur_direction == 1) {
            tarY = this.player.y - grids*16;
            tarX = this.player.x;
        }
        if(this.player.cur_direction == 2) {
            tarY = this.player.y + grids*16;
            tarX = this.player.x;
        }
        if(this.player.cur_direction == 3) {
            tarX = this.player.x + grids*16;
            tarY = this.player.y;
        }
        if(this.player.cur_direction == 4) {
            tarX = this.player.x - grids*16;
            tarY = this.player.y;
        }
        return new int[]{tarX, tarY};
    }

    /**
    * Returns if need to draw a line bettween ghost and target position
	* @return	if draw a line bettween ghost and target position
	*/
    public boolean DEBUG() {
        if(this.drawLine) {
            this.drawLine = false;
            return true;
        }
        this.drawLine = true;
        return false;
    }

    /**
	* Returns int[] array target position(for drawing line) of SCATTER mode. The first element is x value, 2nd is y value.
	*
	* @return	Target position of Scatter mode
	*/
    public int[] keepScatterTar() {
        // return target position in SCATTER mode, will differ in different ghosts
        return new int[]{0,0};
    }

    /**
	* Sets the Chaser ghost for Whim ghost to calculate target position of CHASE mode.
    *
    * @param chaser the Chaser ghost for Whim ghost to calculate target position of CHASE mode
	* @return	The set Chaser ghot
	*/
    public Ghost setChaser(Ghost chaser) {
        // this function is only for Whim to set up chaser.
        // do nothing here
        return this;
    }
    
    /**
    * define Imgae of this frightened ghost.
    *
    * @param app  a instance of App class(used for loadImage())
	*/
    public void defineFrightImage(PApplet app) {
        this.FrightImage = app.loadImage("frightened.png");
    }
}