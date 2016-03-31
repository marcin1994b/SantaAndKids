/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mikolaj;

import java.awt.EventQueue;

/**
 *
 * @author Marcin
 */
public class Mikolaj {

    static Frame ex;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ex = new Frame();
                ex.setVisible(true);
            }
        });
    }
    
}
