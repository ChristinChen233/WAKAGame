package ghost;

import java.util.*;
import java.io.*;

/**
* Manage map of game and provided some assistant static methods
*/
public class GameMap {
    String[][] map;
    int[][] markMap;
    int graphsize;
    int[] topLeft; //Top left corner
    int[] topRight;
    int[] downLeft; //Bottom left corner
    int[] downRight;
    static int endBorderI; // the border of map
    static int endBorderJ;
    static int startBorderI;
    static int startBorderJ;
    
    public GameMap(String filename) {
        this.map = new String[36][28];
        this.markMap = new int[36][28];
        this.graphsize = 16;
        try{
            File file = new File(filename);
            Scanner reader = new Scanner(file);
            int row_count = 0;
            while(reader.hasNextLine()) {
                String[] line = reader.nextLine().split("");
                this.map[row_count] = line;
                row_count++;   
            }
        }catch(FileNotFoundException e) {
            return;
        }

        // calculate markMap
        for(int i = 0; i < 36; i++) {
            for(int j = 0; j < 28; j++) {
                String symbol = this.map[i][j];
                if(symbol.equals("1") || symbol.equals("2") || symbol.equals("3") || symbol.equals("4") || symbol.equals("5") || symbol.equals("6")) {
                    this.markMap[i][j] = 1; //can't go
                }
                else {
                    this.markMap[i][j] = 0; // can go
                }
            }
        }

        // calculate border
        boolean find = false;
        for(int i = 0; i < 36; i++) {
            for(int j = 0; j < 28; j++) {
                String symbol = this.map[i][j];
                if(symbol.equals("6")) {
                    startBorderI = i;
                    startBorderJ = j;
                    find = true;
                    break;
                }
            }
            if(find) {
                break;
            }
        }

        find = false;
        for(int i = 35; i >= 0; i--) {
            for(int j = 27; j >= 0; j--) {
                String symbol = this.map[i][j];
                if(symbol.equals("3")) {
                    endBorderI = i;
                    endBorderJ = j;
                    find = true;
                    break;
                }
            }
            if(find) {
                break;
            }
        }
    }

    /**
	* Copy the given markMap and return a new same markMap but with different address
    *
    * @param mark	the given markMap which need to be copied
	* @return	the copied markMap
	*/
    public static int[][] copy_markMap(int[][] mark) {
        int[][] ret_mark = new int[36][28];
        for(int i = 0; i < 36; i++) {
            for(int j = 0; j < 28; j++) {
                ret_mark[i][j] = mark[i][j];
            }
        }
        return ret_mark;
    }

    /**
	* Returns reachable and nearest target position to real target position
    *
    * @param mark	the given markMap(with 1 and 0 in it, 1 can't go, 0 can go)
    * @param tar_i  the row number of target position
    * @param tar_j  the colum number of target position
	* @return	reachable and nearest target position to real target position(represented by ith row(1st element in returned array) and jth colum(2nd element in returned array))
	*/
    public static int[] nearest(int tar_i, int tar_j, int[][] mark) {
        int[] retArr = new int[2];
        retArr[0] = 0; // first element is i(row)
        retArr[1] = 0; // 2nd element is j(col)
        double shortestPath = 50;
        for(int i = startBorderI; i < endBorderI; i++) {
            for(int j = startBorderJ; j < endBorderJ; j++) {
                if(mark[i][j] == 0) {
                    double dis = Math.sqrt(Math.pow(tar_i - i, 2) + Math.pow(tar_j - j, 2));
                    if(dis < shortestPath) {
                        shortestPath = dis;
                        retArr[0] = i;
                        retArr[1] = j;
                    }
                }
            }
        }
        return retArr;
    }

    /**
     * calculate nearest and reachable target positions of 4 corners in map of game
     */
    public void ScatterCorner() {
        int x = 0;
        int y = 0;

        topLeft = GameMap.nearest(0, 0, markMap);
        x = topLeft[1]*16 - 4;
        y = topLeft[0]*16 - 4;
        topLeft[0] = x;
        topLeft[1] = y;

        topRight = GameMap.nearest(0, 27, markMap);
        x = topRight[1]*16 - 4;
        y = topRight[0]*16 - 4;
        topRight[0] = x;
        topRight[1] = y;

        downLeft = GameMap.nearest(35, 0 , markMap);
        x = downLeft[1]*16 - 4;
        y = downLeft[0]*16 - 4;
        downLeft[0] = x;
        downLeft[1] = y;

        downRight = GameMap.nearest(35, 27, markMap);
        x = downRight[1]*16 - 4;
        y = downRight[0]*16 - 4;
        downRight[0] = x;
        downRight[1] = y;
    }
}