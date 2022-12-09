package client;

import main.Game;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import characters.Character;

import static utilz.Constants.*;

public class Receive implements Runnable{
    private Socket s;
    private Thread socThread;
    private DataInputStream din;

    private Game game;
    private int cnt = 0;

    public Receive(Game game, Socket socket) throws IOException{
        this.game = game;
        this.s = socket;
        din = new DataInputStream(s.getInputStream());
        socThread = new Thread(this);
        socThread.start();
    }
    @Override
    public void run() {
        System.out.println("Start Listen");
        while (true){
            try {
                String msg = din.readUTF();
//                System.out.println((int)msg.charAt(0));
                System.out.println("Type msg: "+ (int)msg.charAt(1) + "\tPlayer: "+ (int)msg.charAt(0));
                switch ((int)msg.charAt(1)){
                    case SC_START_GAME:
                        startGame(msg);
                        break;
                    case SC_KEY_INPUT:

                        keyInput(msg);
                        break;
                    case SC_POS:
                        setPos();
                        break;
                    case SC_IS_DEAD:
                        setDead();
                        break;
                }
                System.out.println("("+cnt +")Server says: " );
                showStringInt(msg);
                cnt ++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void showStringInt(String s){
        for (int i=0;i<s.length();i++){
            System.out.print((int)s.charAt(i)+" ");
        }
    }
    private void setDead() {
    }

    private void setPos() {

    }

    private void keyInput(String msg) {
        Character[] player = game.getPlayer();
        int mainPlayer = game.getMainCharacter();
        System.out.println("You are player "+mainPlayer);
        int obj = msg.charAt(0);

        if (obj != mainPlayer) {
            player[obj].setJump(msg.charAt(IP_UP+1)==1);
            player[obj].setDown(msg.charAt(IP_DOWN+1)==1);
            player[obj].setLeft(msg.charAt(IP_LEFT+1)==1);
            player[obj].setRight(msg.charAt(IP_RIGHT+1)==1);

        }
    }

    private void startGame(String msg) {
        this.game.setMainCharacter(msg.charAt(ST_MAIN_PLAYER));
    }

    private void stringToArrChar(char[] arr, String str){
        for(int i=0;i<str.length();i++){
            arr[i] = str.charAt(i);
        }
    }
    public void close() throws IOException{
        din.close();
    }
}
