/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mikolaj;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Marcin
 */
public class BoardPaint extends JPanel{
    
    Timer timer;
    JLabel endText = new JLabel("");
    String text;
    
    private BufferedImage happyImage;
    private BufferedImage unHappyImage;
    private BufferedImage presentImage;
    private BufferedImage santaImage;
    
    
    public BoardPaint() {
        this.setSize(600, 600);
        
        try {
            happyImage = ImageIO.read(new File("Happy.jpg"));
            unHappyImage = ImageIO.read(new File("unHappy.jpg"));
            presentImage = ImageIO.read(new File("present.jpg"));
            santaImage = ImageIO.read(new File("santa.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(BoardPaint.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                repaint();
            }
        });
        timer.start();
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        if(GameManager.isRunning == true && GameManager.santa.isInGame == true && GameManager.isSantaWin() == false){
            GameManager.santa.moving();
            drawPresents(g);
            drawChildern(g);
            drawSanta(g);
            drawLines(g);
        }else if(GameManager.isRunning == true){
            if(GameManager.santa.isInGame == false){
                text = "Mikolaj zostal zlapany przez Dzieciaki";
            }else if(GameManager.isSantaWin() == true){
                text = "Brawo Mikołaj rozdał prezenty wszystkim Dzieciakom";
            }
            Font font = new Font("Kupa", Font.BOLD, 15);
            g.setFont(font);
            g.drawString(text, 150, 250);
        }
    }

    private void drawSanta(Graphics g){
        g.drawImage(santaImage, GameManager.santa.getX(), GameManager.santa.getY(), 50, 50, null);
    }
    private void drawPresents(Graphics g){
        for(int i = 0; i < GameManager.board.presentList.size(); i++){
            g.drawImage(presentImage, GameManager.board.presentList.get(i).getX(), GameManager.board.presentList.get(i).getY(), 50, 50, null);
        }
    }
    private void drawChildern(Graphics g){
        for(int i = 0; i < GameManager.board.childrenList.size(); i++){
            if(GameManager.board.childrenList.get(i).isGotPresent() == true){
                g.drawImage(happyImage, GameManager.board.childrenList.get(i).getColPx(), GameManager.board.childrenList.get(i).getVerPx(), 50, 50, null);
            }else{
                g.drawImage(unHappyImage, GameManager.board.childrenList.get(i).getColPx(), GameManager.board.childrenList.get(i).getVerPx(), 50, 50, null);
            }
        }
    }
    private void drawLines(Graphics g){
        for(int i = 0; i < 13; i++){
            g.setColor(Color.BLACK);
            g.drawLine(i*50, 0, i*50, 12*50);
        }
        for(int i = 0; i < 13; i++){
            g.setColor(Color.BLACK);
            g.drawLine(0, i*50, 12*50, i*50);
        }
    }
    
}
