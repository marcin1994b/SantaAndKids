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
public class Present {
    
    private int y;
    private int x;
    private boolean active;
    
    public Present(int y, int x){
        this.y = y*50;
        this.x = x*50;
        active = true;
    }
    
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public boolean getActive(){
        return this.active;
    }
    
    public void setActive(boolean tmp){
        this.active = tmp;
    }
}
