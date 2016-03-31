/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mikolaj;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

/**
 *
 * @author Marcin
 */
public class Frame extends JFrame{
    
    static JMenu optionsMenu;
    
    
    String aboutGameString = "SantaAndKids to gra rozgrywana na planszy 12x12 polegająca na rozdaniu prezentów wszystkim dzieciom i nie daniu im się złapać. " + "\n" + "\n" +
                    "W grze wymagana jest zwinność i szybkość podejmowania dobrych decyzji. \n" + "\n";
    
    String aboutAppString = "Aplikacja została utworzona przez studenta UWr - Marcin Barańskiego - w celu zaliczenia zadania z programowania w języku JAVA." + "\n" + "\n" +
                            "Jest to pierwsza moja wersja tej aplikacji i zapewne ostatnia." + "\n" + "\n" +
                            "Aplikacja powstała 12.01.2015";
    
    public Frame(){
        this.setTitle("SantaAndKids");
        this.setSize(607, 650);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        GameManager.startNewGame();
        addJMenuBar();
        addJPanel();
        this.setVisible(true);
    }
    
    private void addJMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenuItem menuItem = null;
        
        addGameMenu(menuBar, menuItem);
        addMovesMenu(menuBar, menuItem);
        addHelpMenu(menuBar, menuItem);

        this.setJMenuBar(menuBar);
    }
    
    private void addJPanel(){
        this.add(GameManager.boardPaint, BorderLayout.CENTER);
    }
    
    /**********/
    
    private void addGameMenu(JMenuBar menuBar, JMenuItem menuItem){
        JMenu gameMenu = new JMenu("Gra");
        GameListener listener = new GameListener();
       
        
        menuBar.add(gameMenu);
        menuItem = gameMenu.add(new JMenuItem("Nowa", 'n'));
        menuItem.addActionListener(listener);
        gameMenu.add(new JSeparator());
        menuItem = gameMenu.add(new JMenuItem("Koniec", 'k'));
        menuItem.addActionListener(listener);
    }
    
    private void addMovesMenu(JMenuBar menuBar, JMenuItem menuItem){
        MovesListener listener = new MovesListener();
        
        JMenu movesMenu = new JMenu("Ruchy");
        menuBar.add(movesMenu);
        menuItem = movesMenu.add(new JMenuItem("Wybierz prawy"));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('d'));
        menuItem.addActionListener(listener);
        
        menuItem = movesMenu.add(new JMenuItem("Wybierz lewy"));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('a'));
        menuItem.addActionListener(listener);
        
        menuItem = movesMenu.add(new JMenuItem("Wybierz górny"));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('w'));
        menuItem.addActionListener(listener);
        
        menuItem = movesMenu.add(new JMenuItem("Wybierz dolny"));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('s'));
        menuItem.addActionListener(listener);
        
        menuItem = movesMenu.add(new JMenuItem("Zostaw prezent"));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('m'));
        menuItem.addActionListener(listener);
        
    }
    
    private void addHelpMenu(JMenuBar menuBar, JMenuItem menuItem){
        JMenu helpMenu = new JMenu("Pomoc");
        HelpListener helpListener = new HelpListener();
        menuBar.add(helpMenu);
        
        menuItem = helpMenu.add(new JMenuItem("O grze"));
        menuItem.addActionListener(helpListener);
        menuItem = helpMenu.add(new JMenuItem("O aplikacji"));
        menuItem.addActionListener(helpListener);
    }
    
    /**********/
    
    private class GameListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Nowa":
                    GameManager.startNewGame();
                    break;
                case "Koniec":
                    GameManager.endGame();
                    break;
            }
        }
        
    }
    
    private class MovesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println(e.getActionCommand());
            switch (e.getActionCommand()) {
                case "Wybierz górny":
                    GameManager.santa.setMoveDirection(GameManager.UP);
                    break;
                case "Wybierz dolny":
                    GameManager.santa.setMoveDirection(GameManager.DOWN);
                    break;
                case "Wybierz prawy":
                    GameManager.santa.setMoveDirection(GameManager.RIGHT);
                    break;
                case "Wybierz lewy":
                    GameManager.santa.setMoveDirection(GameManager.LEFT);
                    break; 
                case "Zostaw prezent":
                    if(GameManager.santa.getY()%50 == 0 && GameManager.santa.getX()%50 == 0){
                        GameManager.santa.leavePresent();
                    }
                    break;
            }
        }

    }
    
    private class HelpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "O grze":
                    showDialogWindow(aboutGameString);
                    break;
                case "O aplikacji":
                    showDialogWindow(aboutAppString);
                    break;
            }
        }
        
    }
    
    
    /**********/
    
    private void showDialogWindow(String string){
        JDialog dialog = new JDialog(this, "O Grze... ", true);
        dialog.setSize(300, 300);
        
        JTextPane field = new JTextPane();
        field.setText(string);
        field.setEditable(false);
        dialog.add(field);
        dialog.show();
    }
    
}
