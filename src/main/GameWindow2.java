package main;

import characters.Character;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow2 {
    private JFrame jframe;
    public GameWindow2(GamePanel2 gamePanel){
        jframe = new JFrame();
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
                Character[] player = gamePanel.getGame().getPlaying().getPlayer();
                for(int i=0; i< player.length;i++){
                    if(player[i] != null)
                        player[i].resetDir();
                }
            }
        });
    }

}
