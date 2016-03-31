/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mikolaj;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Marcin
 */
public class Board extends JPanel{
    
    public ArrayList<Child> childrenList = new ArrayList<>();
    public ArrayList<Present> presentList = new ArrayList<>();
    
    public int[][] board = new int[12][12];
    
    Random random = new Random();
    Timer timer;
    
    
    public Board(){
        createBoard();
        createSanta();
        createChildren(12); 
    }
    
    public void createNewBoard(){
        createBoard();
        createSanta();
        createChildren(12); 
    }
    
    
    public void deleteChildren(){
        childrenList.clear();
    }
    public void deletePresents(){
        presentList.clear();
    }
    
    void createBoard(){
        for(int i = 0; i<GameManager.BOARD_HEIGHT; i++){
            for(int j = 0; j<GameManager.BOARD_WIDTH; j++){
                board[i][j] = 0;
            }
        }
    }
    
    private void createSanta(){
        int y = random.nextInt(10);
        int x = random.nextInt(10);
        GameManager.santa = new Santa(y, x);
        board[y][x] = GameManager.SANTA;
    }
    
    private void createChildren(int tmp){
        while(tmp != 0){
            int ver = random.nextInt(10);
            int col = random.nextInt(10);
            if(board[ver+1][col+1] == 0 && isNoSanta(ver+1, col+1) == true){
                childrenList.add(new Child(ver+1, col+1));
                board[ver+1][col+1] = GameManager.CHILD;
                tmp--;
            }
        }
    }
    
    private boolean isNoSanta(int ver, int col){
        if(board[ver-1][col-1] != GameManager.SANTA && board[ver-1][col] != GameManager.SANTA && board[ver-1][col+1] != GameManager.SANTA &&
            board[ver][col-1] != GameManager.SANTA && board[ver][col] != GameManager.SANTA && board[ver][col+1] != GameManager.SANTA &&
            board[ver+1][col-1] != GameManager.SANTA && board[ver+1][col] != GameManager.SANTA && board[ver+1][col+1] != GameManager.SANTA){
            return true;
        }
        return false;
    }
    
    public void setSantaOnBoard(int y, int x){
        board[y/50][x/50] = GameManager.SANTA;
    }
    public void setPresentOnBoard(int y, int x){
        board[y/50][x/50] = GameManager.PRESENT; 
    }
    
    
    
}
