package main;

import characters.Character;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow {
    private JFrame jframe;
    public GameWindow(GamePanel gamePanel){
        jframe = new JFrame();
        jframe.setSize(400,400);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setResizable(false);
        jframe.pack();
        jframe.setVisible(true);
        jframe.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                Character[] player = gamePanel.getGame().getPlayer();
                for(int i=0; i< player.length;i++){
                    player[i].resetDir();
                }
            }
        });
    }

}
