/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mikolaj;

import java.util.Random;

/**
 *
 * @author Marcin
 */
public class Santa {
    
    static final int RIGHT = 0;
    static final int LEFT = 2;
    static final int UP = 3;
    static final int DOWN = 1;
    
    private int x;
    private int y;
    private int moveDirection = 5;
    
    public boolean isInGame;
    
    Random random = new Random();
    
    public Santa(int y, int x){
        this.isInGame = true;
        this.x = x*50;
        this.y = y*50;
    }
    
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setMoveDirection(int d){
        if(x % 50 == 0 && y % 50 == 0){
            moveDirection = d;
        }
    }
    public void moving(){
        switch(moveDirection){
            case RIGHT:
                if(x + 5 == 11*50 +5){
                    x = 0;
                }else{
                    x= x +5;
                }
                break;
            case LEFT:
                if(x - 5 == -5){
                    x = 11*50;
                }else{
                    x = x - 5;
                }
                break;
            case UP:
                if(y - 5 == -5){
                    y = 11*50;
                }else{
                    y = y -5;
                }
                break;
            case DOWN:
                if(y + 5 == 11*50 +5){
                    y = 0;
                }else{
                    y = y +5;
                }
                break;

        }
        if(x % 50 == 0 && y % 50 == 0){
            moveDirection = 5;
        }
    }
    
    public void leavePresent(){
        GameManager.board.presentList.add(new Present(y/50, x/50));
        GameManager.board.board[this.y/50][this.x/50] = GameManager.PRESENT;
    }
    
}
