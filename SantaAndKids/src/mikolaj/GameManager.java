/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mikolaj;

/**
 *
 * @author Marcin
 */
public class GameManager {
    
    static final int RIGHT = 0;
    static final int LEFT = 2;
    static final int UP = 3;
    static final int DOWN = 1;
    
    final static int CHILD = 1;
    final static int SANTA = 2;
    final static int PRESENT = 3;
    
    final static int BOARD_HEIGHT = 12;
    final static int BOARD_WIDTH = 12;
    
    static Santa santa;
    static final Board board = new Board();
    static BoardPaint boardPaint;
    
    static boolean isRunning;
    
    public GameManager() {}
    
    public final static void startNewGame(){
        endGame();
        isRunning = true;
        board.createNewBoard();
        boardPaint = new BoardPaint();
        startChildren();
    }
    public static void endGame(){
        isRunning = false;
        stopChildren();
        board.createBoard();
        board.deleteChildren();
        board.deletePresents();
    }
    
    private static void startChildren(){
        for(int i = 0; i < GameManager.board.childrenList.size(); i++){
            (new Thread(GameManager.board.childrenList.get(i))).start();
        }
    }
    private static void stopChildren(){
        for(int i = 0; i < GameManager.board.childrenList.size(); i++){
            GameManager.board.childrenList.get(i).stopChild();
        }
    }
    public static boolean isSantaWin(){
        for(int  i = 0; i< board.childrenList.size(); i++){
            if(board.childrenList.get(i).isGotPresent() == false){
                return false;
            }
        }
        return true;
    }
    
    
    
    public boolean isRunning(){
        return isRunning;
    }
    
}
