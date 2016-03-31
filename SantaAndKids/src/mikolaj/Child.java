/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mikolaj;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcin
 */
public class Child implements Runnable {
    
    private int y;
    private int x;
    
    private int speed;
    
    private boolean tired;
    private int strenght;
    
    boolean isLookingSanta;
    
    private int acDirection;
    
    private boolean gotPresent;
    
    Random random = new Random();
    
    
    public Child(int ver, int col){
        this.y = ver*50;
        this.x = col*50;
        this.strenght = 5;
        this.gotPresent = false;
        this.isLookingSanta = false;
        this.tired = false;
        this.acDirection = 5;
    }
    
    public int getVerPx(){
        return this.y;
    }
    public int getColPx(){
        return this.x;
    }
    
    @Override
    public void run() {
        try{
            while(gotPresent != true){
                    if(this.x % 50 == 0 && this.y % 50 == 0){
                        if(tired == true){
                            Thread.sleep(5000);
                            strenght = 5;
                            tired = false;
                        }else{
                            synchronized(GameManager.board){
                                if(isPresentHere() == true){
                                    takePresent();
                                }else if(catchSanta() == true || isSantaHere() == true){
                                    GameManager.santa.isInGame = false;
                                }else if(isPresentClose() == true){
                                    goToPresent();
                                    speed = 2;
                                }else if(isSantaClose() == true){
                                    goToSanta();
                                    speed = 2;
                                }else{
                                    speed = 1;
                                    strenght = 5;
                                }
                            }
                        }
                        if(speed != 2 && gotPresent != true){
                            acDirection = randomDirection();
                            Thread.sleep(3000);
                        }
                    }
                    moving(speed);
                    Thread.sleep(50);
                    if(strenght == 0){
                        tired = true;
                    }
            }
        }catch (InterruptedException ex){
                Logger.getLogger(Child.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isGotPresent(){
        return gotPresent;
    }
    
    public void stopChild(){
        this.gotPresent = true;
    }

    public void moving(int speed){
        switch(acDirection){
            case GameManager.RIGHT:
                if(x + 1 == 11*50 +1){
                    x = 0;
                }else{
                    x = x + speed;
                }
                break;
            case GameManager.LEFT:
                if(x - 1 == -1){
                    x = 11*50;
                }else{
                    x = x - speed;
                }
                break;
            case GameManager.UP:
                if(y - 1 == -1){
                    y = 11*50;
                }else{
                    y = y - speed;
                }
                break;
            case GameManager.DOWN:
                if(y + 1 == 11*50 +1){
                    y = 0;
                }else{
                    y = y + speed;
                }
                break;
        }
        
        if(x % 50 == 0 && y % 50 == 0){
            acDirection = 5;
            if(speed == 2){
                strenght--;
            }
        }
        
    }
    
    public int randomDirection(){
        int direction = random.nextInt(4);
        isLookingSanta = false;
        return direction;
    }
    
    private boolean isSantaHere(){
        if(GameManager.santa.getX() == this.x && GameManager.santa.getY() == this.y){
            return true;
        }
        return false;
    }
    
    private void goToSanta(){
        int xSanta = GameManager.santa.getX();
        int ySanta = GameManager.santa.getY();
        
        int xChild = this.x;
        int yChild = this.y;
        //System.out.println("Sprawdzam!");
        
        /*srodek*/
        if((xChild - 150 >= 0 && xChild + 150 <= (GameManager.BOARD_WIDTH-1)*50) && (yChild >= 0 && yChild + 150 <= (GameManager.BOARD_HEIGHT-1)*50 )){
            if((xSanta >= xChild - 150 && xSanta <= x + 150) && (ySanta >= yChild - 150 && ySanta <= yChild + 150)){
                //System.out.println("Child: " + yChild + " " + xChild + ", Santa: " + ySanta + " " + xSanta);
                int tmpX = xChild - xSanta;
                int tmpY = yChild - ySanta;
                if(tmpX > 0 && xSanta < xChild){
                    acDirection = GameManager.LEFT;
                }else if(tmpX < 0 && xSanta > xChild){
                    acDirection = GameManager.RIGHT;
                }else if(tmpY > 0 && ySanta < yChild){
                    acDirection = GameManager.UP;
                }else if(tmpY < 0 && ySanta > yChild){
                    acDirection = GameManager.DOWN;
                }
                isLookingSanta = true;
            }
        }
        /*przy lewej bandzie */
        if(xChild < 0 + 150){
            //System.out.println("Przy LEWEJ!");
            if(((xSanta >= 0 && xSanta <= x + 150) || (xSanta >= (GameManager.BOARD_WIDTH-1)*50+(xChild - 150) && xSanta <= (GameManager.BOARD_WIDTH-1)*50))
                && ((ySanta >= yChild - 150) && (ySanta <= yChild + 150))){
                int tmpX = xChild - xSanta;
                int tmpY = yChild - ySanta;
                if(tmpX > 0 || tmpX < -400){
                    acDirection = GameManager.LEFT;
                }else if(tmpX < 0){
                    acDirection = GameManager.RIGHT;
                }else if(tmpY > 0 || tmpY < -400){
                    acDirection = GameManager.UP;
                }else if(tmpY < 0 || tmpY > 400){
                    acDirection = GameManager.DOWN;
                }
                isLookingSanta = true;
            }
        }
        /*przy prawej bandzie */
        if(xChild + 150 > (GameManager.BOARD_WIDTH-1)*50){
            //System.out.println("Przy PRAWEJ!");
            if(((xSanta >= xChild - 150 && xSanta <= (GameManager.BOARD_WIDTH-1)*50) || (xSanta >= 0 && xSanta <= (xChild + 150) - (GameManager.BOARD_WIDTH-1)*50))
                && ((ySanta >= yChild - 150) && (ySanta <= yChild + 150))){
                int tmpX = xChild - xSanta;
                int tmpY = yChild - ySanta;
                if(tmpX > 0 && tmpX < 150 ){
                    acDirection = GameManager.LEFT;
                }else if(tmpX < 0 || tmpX > 450){
                    acDirection = GameManager.RIGHT;
                }else if(tmpY > 0 || tmpY < -400){
                    acDirection = GameManager.UP;
                }else if(tmpY < 0 || tmpY > 450){
                    acDirection = GameManager.DOWN;
                }
                isLookingSanta = true;
            }
        } 
        /* przy dolnej bandzie */
        if(yChild + 150 > (GameManager.BOARD_HEIGHT-1)*50){
            if((xSanta >= xChild-150 && xSanta <= x+150) &&
              ((ySanta >= yChild - 150 && ySanta <= (GameManager.BOARD_HEIGHT-1)*50) || (ySanta >= 0 && ySanta <= (yChild + 150) - (GameManager.BOARD_HEIGHT-1)*50))){
                int tmpX = xChild - xSanta;
                int tmpY = yChild - ySanta;
                if(tmpX > 0 ){
                    acDirection = GameManager.LEFT;
                }else if(tmpX < 0){
                    acDirection = GameManager.RIGHT;
                }else if(tmpY > 0 && ySanta >= yChild - 150){
                    acDirection = GameManager.UP;
                }else if(tmpY < 0 || ySanta <= 150){
                    acDirection = GameManager.DOWN;
                }
                isLookingSanta = true;
            }
        }
        
        /*przy gornej bandzie */
        if(yChild - 150 < 0 ){
            if((xSanta >= xChild - 150 && xSanta <= xChild + 150) &&
               ((ySanta >= 0 && ySanta <= yChild + 150) || (ySanta >= (GameManager.BOARD_HEIGHT-1)*50 + (yChild - 150) && ySanta <= (GameManager.BOARD_HEIGHT-1)*50))){
                int tmpX = xChild - xSanta;
                int tmpY = yChild - ySanta;
                if(tmpX > 0){
                    acDirection = GameManager.LEFT;
                }else if(tmpX < 0){
                    acDirection = GameManager.RIGHT;
                }else if(tmpY < 0 && ySanta <= yChild + 150){
                    acDirection = GameManager.DOWN;
                }else if(tmpY > 0 || ySanta >= 400){
                    acDirection = GameManager.UP;
                }
                isLookingSanta = true;
            }
        }
        //return false;
    }
    
    private boolean isPresentHere(){
        
        if(GameManager.board.board[this.y/50][this.x/50] == GameManager.PRESENT){
            //System.out.println("JEST!");
            return true;
        }
        return false;
        
    }
    
    private void takePresent(){
        
        int xPresent;
        int yPresent;
        if(!GameManager.board.presentList.isEmpty()){
            for(int i = 0; i < GameManager.board.presentList.size(); i++){
                xPresent = GameManager.board.presentList.get(i).getX();
                yPresent = GameManager.board.presentList.get(i).getY();
                if(xPresent == this.x && yPresent == this.y){
                    GameManager.board.presentList.remove(i);
                    GameManager.board.board[this.y/50][this.x/50] = GameManager.CHILD;
                    gotPresent = true;
                }
            }
        }
        
    }
    
    private void goToPresent(){
        int xPresent;
        int yPresent;
        
        if(!GameManager.board.presentList.isEmpty()){
            for(int i = 0; i < GameManager.board.presentList.size(); i++){
                xPresent = GameManager.board.presentList.get(i).getX();
                yPresent = GameManager.board.presentList.get(i).getY();
                if(this.x == 0 && this.y != 0 && this.y != (GameManager.BOARD_HEIGHT-1)*50){
                    if(xPresent == (GameManager.BOARD_WIDTH-1)*50 && (yPresent >= this.y - 50 && yPresent <= this.y + 50 )){
                        acDirection = GameManager.LEFT;
                    }else if(xPresent == this.x + 50 && (yPresent >= this.y - 50 && yPresent <= this.y + 50 )){
                        acDirection = GameManager.RIGHT;
                    }else if((xPresent >= this.x - 50 && xPresent <= this.x + 50) && yPresent == this.y - 50){
                        acDirection = GameManager.UP;
                    }else if((xPresent >= this.x - 50 && xPresent <= this.x + 50) && yPresent == this.y+50){
                        acDirection = GameManager.DOWN;
                    }
                }else if(this.x == (GameManager.BOARD_WIDTH-1)*50 && this.y != 0 && this.y != (GameManager.BOARD_HEIGHT-1)*50){
                    if(xPresent == 0 && (yPresent >= this.y - 50 && yPresent <= this.y + 50 )){
                        acDirection = GameManager.RIGHT;
                    }else if(xPresent == this.x - 50 && (yPresent >= this.y - 50 && yPresent <= this.y + 50 ) ){
                        acDirection = GameManager.LEFT;
                    }else if((xPresent >= this.x - 50 && xPresent <= this.x + 50) && yPresent == this.y - 50){
                        acDirection = GameManager.UP;
                    }else if((xPresent >= this.x - 50 && xPresent <= this.x + 50) && yPresent == this.y+50){
                        acDirection = GameManager.DOWN;
                    }
                }else if(this.x != 0 && this.x != (GameManager.BOARD_WIDTH-1)*50 && this.y == 0){
                    if(xPresent == this.x - 50 && (yPresent >= this.y-50 && yPresent <= this.y+50)){
                        acDirection = GameManager.LEFT;
                    }else if(xPresent == this.x + 50 && (yPresent >= 0 && yPresent <= this.y+50)){
                        acDirection = GameManager.RIGHT;
                    }else if((xPresent >= this.x-50 && xPresent <= this.x+50) && yPresent == (GameManager.BOARD_HEIGHT-1)*50){
                        acDirection = GameManager.UP;
                    }else if((xPresent >= this.x-50 && xPresent <= this.x+50) && yPresent == this.y + 50){
                        acDirection = GameManager.DOWN;
                    }   
                }else if(this.x != 0 && this.x != (GameManager.BOARD_WIDTH-1)*50 && this.y == (GameManager.BOARD_HEIGHT-1)*50){
                    if(xPresent == this.x - 50 && (yPresent >= this.y-50 && yPresent <= this.y+50)){
                        acDirection = GameManager.LEFT;
                    }else if(xPresent == this.x + 50 && (yPresent >= this.y-50 && yPresent <= this.y+50)){
                        acDirection = GameManager.RIGHT;
                    }else if((xPresent >= this.x-50 && xPresent <= this.x+50) && yPresent == this.y - 50){
                        acDirection = GameManager.UP;
                    }else if((xPresent >= this.x-50 && xPresent <= this.x+50) && yPresent == 0){
                        acDirection = GameManager.DOWN;
                    }
                }else if( xPresent == this.x - 50 && (yPresent >= this.y - 50 && yPresent <= this.y + 50 )){
                    acDirection = GameManager.LEFT;
                }else if(xPresent == this.x + 50 && (yPresent >= this.y - 50 && yPresent <= this.y + 50 )){
                    acDirection = GameManager.RIGHT;
                }else if((xPresent >= this.x - 50 && xPresent <= this.x + 50) && yPresent == this.y - 50){
                    acDirection = GameManager.UP;
                }else if((xPresent >= this.x - 50 && xPresent <= this.x + 50) && yPresent == this.y+50){
                    acDirection = GameManager.DOWN;
                }
            }
        }
    }
    
    private boolean isPresentClose(){
        int xPresent;
        int yPresent;
        
        if(!GameManager.board.presentList.isEmpty()){
            for(int i = 0; i < GameManager.board.presentList.size(); i++){
                xPresent = GameManager.board.presentList.get(i).getX();
                yPresent = GameManager.board.presentList.get(i).getY();
                if(this.x == 0 && this.y != 0 && this.y != (GameManager.BOARD_HEIGHT-1)*50){
                    if(xPresent == (GameManager.BOARD_WIDTH-1)*50 && (yPresent >= this.y - 50 && yPresent <= this.y + 50 )){
                        return true;
                    }else if(xPresent == this.x + 50 && (yPresent >= this.y - 50 && yPresent <= this.y + 50 )){
                        return true;
                    }else if((xPresent >= this.x - 50 && xPresent <= this.x + 50) && yPresent == this.y - 50){
                        return true;
                    }else if((xPresent >= this.x - 50 && xPresent <= this.x + 50) && yPresent == this.y+50){
                        return true;
                    }
                }else if(this.x == (GameManager.BOARD_WIDTH-1)*50 && this.y != 0 && this.y != (GameManager.BOARD_HEIGHT-1)*50){
                    if(xPresent == 0 && (yPresent >= this.y - 50 && yPresent <= this.y + 50 )){
                        return true;
                    }else if(xPresent == this.x - 50 && (yPresent >= this.y - 50 && yPresent <= this.y + 50 ) ){
                        return true;
                    }else if((xPresent >= this.x - 50 && xPresent <= this.x + 50) && yPresent == this.y - 50){
                        return true;
                    }else if((xPresent >= this.x - 50 && xPresent <= this.x + 50) && yPresent == this.y+50){
                        return true;
                    }
                }else if(this.x != 0 && this.x != (GameManager.BOARD_WIDTH-1)*50 && this.y == 0){
                    if(xPresent == this.x - 50 && (yPresent >= this.y-50 && yPresent <= this.y+50)){
                        return true;
                    }else if(xPresent == this.x + 50 && (yPresent >= 0 && yPresent <= this.y+50)){
                        return true;
                    }else if((xPresent >= this.x-50 && xPresent <= this.x+50) && yPresent == (GameManager.BOARD_HEIGHT-1)*50){
                        return true;
                    }else if((xPresent >= this.x-50 && xPresent <= this.x+50) && yPresent == this.y + 50){
                        return true;
                    }   
                }else if(this.x != 0 && this.x != (GameManager.BOARD_WIDTH-1)*50 && this.y == (GameManager.BOARD_HEIGHT-1)*50){
                    if(xPresent == this.x - 50 && (yPresent >= this.y-50 && yPresent <= this.y+50)){
                        return true;
                    }else if(xPresent == this.x + 50 && (yPresent >= this.y-50 && yPresent <= this.y+50)){
                        return true;
                    }else if((xPresent >= this.x-50 && xPresent <= this.x+50) && yPresent == this.y - 50){
                        return true;
                    }else if((xPresent >= this.x-50 && xPresent <= this.x+50) && yPresent == 0){
                        return true;
                    }
                }else if( xPresent == this.x - 50 && (yPresent >= this.y - 50 && yPresent <= this.y + 50 )){
                    return true;
                }else if(xPresent == this.x + 50 && (yPresent >= this.y - 50 && yPresent <= this.y + 50 )){
                    return true;
                }else if((xPresent >= this.x - 50 && xPresent <= this.x + 50) && yPresent == this.y - 50){
                    return true;
                }else if((xPresent >= this.x - 50 && xPresent <= this.x + 50) && yPresent == this.y+50){
                    return true;
                }
            }
        }
        return false;
    }
    
    
    private boolean isSantaClose(){
        int xSanta = GameManager.santa.getX();
        int ySanta = GameManager.santa.getY();
        
        int xChild = this.x;
        int yChild = this.y;
        
        /*srodek*/
        if((xChild - 150 >= 0 && xChild + 150 <= (GameManager.BOARD_WIDTH-1)*50) && (yChild >= 0 && yChild + 150 <= (GameManager.BOARD_HEIGHT-1)*50 )){
            if((xSanta >= xChild - 150 && xSanta <= x + 150) && (ySanta >= yChild - 150 && ySanta <= yChild + 150)){
                int tmpX = xChild - xSanta;
                int tmpY = yChild - ySanta;
                if(tmpX > 0 && xSanta < xChild){
                    return true;
                }else if(tmpX < 0 && xSanta > xChild){
                    return true;
                }else if(tmpY > 0 && ySanta < yChild){
                    return true;
                }else if(tmpY < 0 && ySanta > yChild){
                    return true;
                }
            }
        }
        /*przy lewej bandzie */
        if(xChild < 0 + 150){
            if(((xSanta >= 0 && xSanta <= x + 150) || (xSanta >= (GameManager.BOARD_WIDTH-1)*50+(xChild - 150) && xSanta <= (GameManager.BOARD_WIDTH-1)*50))
                && ((ySanta >= yChild - 150) && (ySanta <= yChild + 150))){
                int tmpX = xChild - xSanta;
                int tmpY = yChild - ySanta;
                if(tmpX > 0 || tmpX < -400){
                    return true;
                }else if(tmpX < 0){
                    return true;
                }else if(tmpY > 0 || tmpY < -400){
                    return true;
                }else if(tmpY < 0 || tmpY > 400){
                    return true;
                }
            }
        }
        /*przy prawej bandzie */
        if(xChild + 150 > (GameManager.BOARD_WIDTH-1)*50){
            if(((xSanta >= xChild - 150 && xSanta <= (GameManager.BOARD_WIDTH-1)*50) || (xSanta >= 0 && xSanta <= (xChild + 150) - (GameManager.BOARD_WIDTH-1)*50))
                && ((ySanta >= yChild - 150) && (ySanta <= yChild + 150))){
                int tmpX = xChild - xSanta;
                int tmpY = yChild - ySanta;
                if(tmpX > 0 && tmpX < 150 ){
                    return true;
                }else if(tmpX < 0 || tmpX > 450){
                    return true;
                }else if(tmpY > 0 || tmpY < -400){
                    return true;
                }else if(tmpY < 0 || tmpY > 450){
                    return true;
                }
            }
        } 
        /* przy dolnej bandzie */
        if(yChild + 150 > (GameManager.BOARD_HEIGHT-1)*50){
            if((xSanta >= xChild-150 && xSanta <= x+150) &&
              ((ySanta >= yChild - 150 && ySanta <= (GameManager.BOARD_HEIGHT-1)*50) || (ySanta >= 0 && ySanta <= (yChild + 150) - (GameManager.BOARD_HEIGHT-1)*50))){
                int tmpX = xChild - xSanta;
                int tmpY = yChild - ySanta;
                if(tmpX > 0 ){
                    return true;
                }else if(tmpX < 0){
                    return true;
                }else if(tmpY > 0 && ySanta >= yChild - 150){
                    return true;
                }else if(tmpY < 0 || ySanta <= 150){
                    return true;
                }
            }
        }
        
        /*przy gornej bandzie */
        if(yChild - 150 < 0 ){
            if((xSanta >= xChild - 150 && xSanta <= xChild + 150) &&
               ((ySanta >= 0 && ySanta <= yChild + 150) || (ySanta >= (GameManager.BOARD_HEIGHT-1)*50 + (yChild - 150) && ySanta <= (GameManager.BOARD_HEIGHT-1)*50))){
                int tmpX = xChild - xSanta;
                int tmpY = yChild - ySanta;
                if(tmpX > 0){
                    return true;
                }else if(tmpX < 0){
                    return true;
                }else if(tmpY < 0 && ySanta <= yChild + 150){
                    return true;
                }else if(tmpY > 0 || ySanta >= 400){
                    return true;
                }
            }
        }
        return false;
    }
    
    
    private boolean catchSanta(){
        int xSanta = GameManager.santa.getX();
        int ySanta = GameManager.santa.getY();

        if(this.x == 0 && this.y != 0 && this.y != (GameManager.BOARD_HEIGHT-1)*50){
            if(xSanta == (GameManager.BOARD_WIDTH-1)*50 && (ySanta >= this.y - 50 && ySanta <= this.y + 50 )){
                return true;
            }else if(xSanta == this.x + 50 && (ySanta >= this.y - 50 && ySanta <= this.y + 50 )){
                return true;
            }else if((xSanta >= this.x - 50 && xSanta <= this.x + 50) && ySanta == this.y - 50){
                return true;
            }else if((xSanta >= this.x - 50 && xSanta <= this.x + 50) && ySanta == this.y+50){
                return true;
            }
        }else if(this.x == (GameManager.BOARD_WIDTH-1)*50 && this.y != 0 && this.y != (GameManager.BOARD_HEIGHT-1)*50){
            if(xSanta == 0 && (ySanta >= this.y - 50 && ySanta <= this.y + 50 )){
                return true;
            }else if(xSanta == this.x - 50 && (ySanta >= this.y - 50 && ySanta <= this.y + 50 ) ){
                return true;
            }else if((xSanta >= this.x - 50 && xSanta <= this.x + 50) && ySanta == this.y - 50){
                return true;
            }else if((xSanta >= this.x - 50 && xSanta <= this.x + 50) && ySanta == this.y+50){
                return true;
            }
        }else if(this.x != 0 && this.x != (GameManager.BOARD_WIDTH-1)*50 && this.y == 0){
            if(xSanta == this.x - 50 && (ySanta >= this.y-50 && ySanta <= this.y+50)){
                return true;
            }else if(xSanta == this.x + 50 && (ySanta >= 0 && ySanta <= this.y+50)){
                return true;
            }else if((xSanta >= this.x-50 && xSanta <= this.x+50) && ySanta == (GameManager.BOARD_HEIGHT-1)*50){
                return true;
            }else if((xSanta >= this.x-50 && xSanta <= this.x+50) && ySanta == this.y + 50){
                return true;
            }   
        }else if(this.x != 0 && this.x != (GameManager.BOARD_WIDTH-1)*50 && this.y == (GameManager.BOARD_HEIGHT-1)*50){
            if(xSanta == this.x - 50 && (ySanta >= this.y-50 && ySanta <= this.y+50)){
                return true;
            }else if(xSanta == this.x + 50 && (ySanta >= this.y-50 && ySanta <= this.y+50)){
                return true;
            }else if((xSanta >= this.x-50 && xSanta <= this.x+50) && ySanta == this.y - 50){
                return true;
            }else if((xSanta >= this.x-50 && xSanta <= this.x+50) && ySanta == 0){
                return true;
            }
        }else if( xSanta == this.x - 50 && (ySanta >= this.y - 50 && ySanta <= this.y + 50 )){
            return true;
        }else if(xSanta == this.x + 50 && (ySanta >= this.y - 50 && ySanta <= this.y + 50 )){
            return true;
        }else if((xSanta >= this.x - 50 && xSanta <= this.x + 50) && ySanta == this.y - 50){
            return true;
        }else if((xSanta >= this.x - 50 && xSanta <= this.x + 50) && ySanta == this.y+50){
            return true;
        }
        
        return false;

    }


    
    
    
}
