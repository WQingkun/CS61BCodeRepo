package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean CheckAndMerge(int cnt, int[] ColArray,int col) {
        if (cnt == 0) {
            //a ,a ,b ,b a,b != 0
            if (ColArray[0] == ColArray[1] && ColArray[2] == ColArray[3]) {
                Tile t = board.tile(col, 2);
                board.move(col, 3, t);
                t = board.tile(col, 0);
                board.move(col, 2, t);
                t = board.tile(col, 1);
                board.move(col, 2, t);
                score = score + ColArray[0] * 2 + ColArray[2] * 2;
                return true;
            }
            //a b b c
            if (ColArray[0] != ColArray[1] && ColArray[1] == ColArray[2] && ColArray[2] != ColArray[3]) {
                Tile t = board.tile(col, 1);
                board.move(col, 2, t);
                t = board.tile(col, 0);
                board.move(col, 1, t);
                score += 2 * ColArray[1];
                return true;
            }
            //a b c c
            if (ColArray[0] != ColArray[1] && ColArray[1] != ColArray[2] && ColArray[2] == ColArray[3]) {
                Tile t = board.tile(col, 0);
                board.move(col, 1, t);
                score += 2 * ColArray[2];
                return true;
            }
            //a a b c
            if (ColArray[0] == ColArray[1] && ColArray[2] != ColArray[3]) {
                Tile t = board.tile(col, 2);
                board.move(col, 3, t);
                t = board.tile(col, 1);
                board.move(col, 2, t);
                t = board.tile(col, 0);
                board.move(col, 1, t);
                score += 2 * ColArray[0];
                return true;
            }
            return false;
        }
        if (cnt == 1){
           if (ColArray[0] == 0){
               // 0 a a b
               if(ColArray[1] == ColArray[2]){
                   Tile t = board.tile(col, 2);
                   board.move(col, 3, t);
                   t = board.tile(col, 1);
                   board.move(col, 3, t);
                   t = board.tile(col, 0);
                   board.move(col, 2, t);
                   score += 2*ColArray[2];
                   return true;
               } else if (ColArray[0] == ColArray[1]) {
                   // 0 a b b
                   Tile t = board.tile(col, 2);
                   board.move(col, 3, t);
                   t = board.tile(col, 1);
                   board.move(col,2, t);
                   t = board.tile(col, 0);
                   board.move(col, 2, t);
                   score += 2*ColArray[0];
                   return true;
               }else{
                   //0 a b c
                   Tile t = board.tile(col, 2);
                   board.move(col, 3, t);
                   t = board.tile(col, 1);
                   board.move(col, 2, t);
                   t = board.tile(col, 0);
                   board.move(col, 1, t);
                   return true;
               }
           }
           if (ColArray[1] == 0){
               if(ColArray[0] == ColArray[2]){
                   //a 0 a b
                   Tile t = board.tile(col, 1);
                   board.move(col, 3, t);
                   t = board.tile(col, 0);
                   board.move(col, 2, t);
                   score += 2*ColArray[0];
                   return true;
               }else if(ColArray[2] == ColArray[3]){
                   // a 0 b b
                   Tile t = board.tile(col, 1);
                   board.move(col, 2, t);
                   t = board.tile(col, 0);
                   board.move(col, 2, t);
                   score += 2*ColArray[2];
                   return true;
               }else {
                   // a 0 b c
                   Tile t = board.tile(col, 1);
                   board.move(col, 2, t);
                   t = board.tile(col, 0);
                   board.move(col, 1, t);
                   return true;
               }

           }
           if (ColArray[2] == 0){
               if(ColArray[0] == ColArray[1]){
                   //a a 0 b
                   Tile t = board.tile(col, 2);
                   board.move(col, 3, t);
                   t = board.tile(col, 0);
                   board.move(col, 2, t);
                   score += 2*ColArray[0];
                   return true;
               }else if(ColArray[1] == ColArray[3]){
                   //a b 0 b
                   Tile t = board.tile(col, 0);
                   board.move(col, 2, t);
                   score += 2*ColArray[1];
                   return true;
               }else{
                   //a b 0 c
                   Tile t = board.tile(col, 0);
                   board.move(col, 1, t);
                   return true;
               }
           }
           if (ColArray[3] == 0){
               //a a b 0
               if(ColArray[0] == ColArray[1]){
                   Tile t = board.tile(col, 2);
                   board.move(col, 3, t);
                   t = board.tile(col, 1);
                   board.move(col, 2, t);
                   score += 2*ColArray[0];
                   return true;
               }else if(ColArray[1] == ColArray[2]){
                   //a b b 0
                   Tile t = board.tile(col, 1);
                   board.move(col, 2, t);
                   score += 2*ColArray[1];
                   return true;
               }else{
                   // a b c 0
                   return false;
               }
           }
        }
        if (cnt == 2){
            if(ColArray[0] == ColArray[1] && ColArray[0] == 0){
                if(ColArray[2] == ColArray[3]){
                    // 0 0 a a
                    Tile t = board.tile(col, 1);
                    board.move(col, 3, t);
                    t = board.tile(col, 0);
                    board.move(col, 3, t);
                    score += 2*ColArray[2];
                    return true;
                }else{
                    // 0 0 a b
                    Tile t = board.tile(col, 1);
                    board.move(col, 3, t);
                    t = board.tile(col, 0);
                    board.move(col, 2, t);
                    return true;
                }
            }
            if(ColArray[0] == ColArray[2] && ColArray[0] == 0){
                if(ColArray[1] == ColArray[3]){
                    //0 a 0 a
                    Tile t = board.tile(col, 2);
                    board.move(col, 3, t);
                    t = board.tile(col, 0);
                    board.move(col, 3, t);
                    score += 2*ColArray[1];
                    return true;
                }else{
                    //0 a 0 b
                    Tile t = board.tile(col, 2);
                    board.move(col, 3, t);
                    t = board.tile(col, 0);
                    board.move(col, 2, t);
                    return true;
                }
            }
            if(ColArray[0] == ColArray[3] && ColArray[0] == 0){
                if(ColArray[1] == ColArray[2]){
                    //0 a a 0
                    Tile t = board.tile(col, 2);
                    board.move(col, 3, t);
                    t = board.tile(col, 1);
                    board.move(col, 3, t);
                    score += 2*ColArray[1];
                    return true;
                }else{
                    //0 a b 0
                    Tile t = board.tile(col, 2);
                    board.move(col, 3, t);
                    t = board.tile(col, 1);
                    board.move(col, 2, t);
                    return true;
                }

            }
            if(ColArray[1] == ColArray[2] && ColArray[1] == 0){
                if(ColArray[0] == ColArray[3]){
                    //a 0 0 a
                    Tile t = board.tile(col, 0);
                    board.move(col, 3, t);
                    score += 2*ColArray[0];
                }else{
                    //a 0 0 b
                    Tile t = board.tile(col, 0);
                    board.move(col, 2, t);
                }
                return true;
            }
            if(ColArray[1] == ColArray[3] && ColArray[1] == 0){
                Tile t;
                if(ColArray[0] == ColArray[2]){
                    //a 0 a 0
                    t = board.tile(col, 1);
                    board.move(col, 3, t);
                    score += 2*ColArray[0];
                }else{
                    //a 0 b 0
                    t = board.tile(col, 1);
                    board.move(col, 2, t);
                }
                return true;
            }
            if(ColArray[2] == ColArray[3] && ColArray[2] == 0){
                Tile t;
                if(ColArray[0] == ColArray[1]){
                    //a a 0 0
                    t = board.tile(col, 2);
                    board.move(col, 3, t);
                    score += 2*ColArray[0];
                    return true;
                }else {
                    //a b 0 0
                    return false;
                }
            }

        }
        if (cnt == 3){
            if(ColArray[0] != 0){
                //a 0 0 0
                return false;
            }else {
                for(int i = 1;i < size(); i += 1){
                   if (ColArray[i] != 0){
                       Tile t = board.tile(col, 3 - i);
                       board.move(col, 3, t);
                       return true;
                   }
                }
            }
        }
        return false;
    }


    public boolean ColOperation(int col, int size){
        //Read col values of board
        int cnt = 0;
        int[] ColArray = new int[4];
        for(int i = 0; i < size; i += 1){
            if(board.tile(col, 3 - i) == null){
                ColArray[i] = 0;
                cnt += 1;
            }else{
                ColArray[i] = board.tile(col, 3 - i).value();
            }
        }
        return CheckAndMerge(cnt,ColArray,col);
    }
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;
        board.setViewingPerspective(side);
        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        int i = 0;
        while(i < size()){
            if(ColOperation(i, size())){
                changed = true;

            }
            i += 1;
        }
        checkGameOver();
        board.setViewingPerspective(Side.NORTH);
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        int i = 0;
        int size = b.size();
        while(i < size){
            int j = 0;
            while(j < size){
                if (b.tile(i, j) == null){
                    return true;
                }
                j += 1;
            }
            i += 1;
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        int i = 0;
        int size = b.size();
        while (i < size){
            int j = 0;
            while (j < size){
                if (b.tile(i, j) != null && b.tile(i, j).value() == MAX_PIECE){
                    return  true;
                }
                j += 1;
            }
            i += 1;
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean CheckSides(int row, int col, Board b){
        int size = b.size();
        if (row == 0){
            int j = 0;
            while (j < size){
                if (size >= 2 && b.tile(j,0).value() == b.tile(j, row + 1).value()){
                    return true;
                }
                /*Check if there is same value in row 0*/
                if (j+1 < size && b.tile(j, row).value() == b.tile(j + 1, row).value()){
                    return true;
                }
                j += 1;
            }
        }
        if (row == size - 1){
            int j = 0;
            while (j < size){
                if (size >= 2 && b.tile(j,row).value() == b.tile(j, row - 1).value()){
                    return true;
                }
                if (j+1 < size && b.tile(j, row).value() == b.tile(j + 1, row).value()){
                    return true;
                }
                j += 1;
            }
        }
        if (col == size - 1){
            int j = 0;
            while (j < size){
                if (size >= 2 && b.tile(col,j).value() == b.tile(col - 1, j).value()){
                    return true;
                }
                if (j+1 < size && b.tile(col, j).value() == b.tile(col, j + 1).value()){
                    return true;
                }
                j += 1;
            }
        }
        if (col == 0){
            int j = 0;
            while (j < size){
                if (size >= 2 && b.tile(col,j).value() == b.tile(col + 1, j).value()){
                    return true;
                }
                if (j+1 < size && b.tile(col, j).value() == b.tile(col, j + 1).value()){
                    return true;
                }
                j += 1;
            }
        }
        return false;
    }
    public static boolean TwoAjacent(Board b){
        int i = 0;
        int size = b.size();
        while (i < size){
            int j = 0;
            while (j < size){
                if (i == 0 || i == size - 1 || j == 0 || j == size - 1){
                    if (CheckSides(i , j , b)){
                        return true;
                    }
                }
                else{
                    if (b.tile(i, j).value() == b.tile(i, j - 1).value()){
                        return true;
                    }
                    if (b.tile(i, j).value() == b.tile(i, j + 1).value()){
                        return true;
                    }
                    if (b.tile(i, j).value() == b.tile(i - 1, j).value()){
                        return true;
                    }
                    if (b.tile(i, j).value() == b.tile(i + 1, j).value()){
                        return true;
                    }
                }
                j += 1;
            }
            i += 1;
        }
        return false;
    }
    public static boolean atLeastOneMoveExists(Board b) {
        if (emptySpaceExists((b))){
            return true;
        }
        return TwoAjacent(b);
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
