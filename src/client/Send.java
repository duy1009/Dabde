package client;

import main.Game;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static utilz.Constants.*;

public class Send implements Runnable{
    public static final char SC_NOT_UPDATE = 2589;
    private Packets keyData;
    private Packets posData, startData, endData;
    private Thread socThread;
    private Socket s ;
    private Game game;
    private DataOutputStream dout;
    public Send(Socket socket, Game game) throws IOException{
        this.s = socket;
        this.dout = new DataOutputStream(s.getOutputStream());
        this.game = game;
        initPackets();

        socThread = new Thread(this);
        socThread.start();

    }
    @Override
    public void run() {
        System.out.println("Start send, keyData = "+ keyData);
        while (true){
//            keyData.showDataInt();
//            System.out.println(keyData.justChange);
            synchronized (keyData){
                try {
                    if (keyData.getJustChange()){
//                        System.out.println("Send!!!!!!!!!");
                        sendData(keyData);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    private void initPackets(){
        startData = new Packets(SC_LENGTH_START_GAME);
        keyData = new Packets(SC_LENGTH_KEY_INPUT);
        posData = new Packets(SC_LENGTH_POS);
        endData = new Packets(SC_LENGTH_IS_DEAD);

        startData.setData(0, SC_START_GAME);
        keyData.setData(0, SC_KEY_INPUT);
        posData.setData(0, SC_POS);
        endData.setData(0, SC_IS_DEAD);
    }
    private synchronized void sendData(Packets pack) throws IOException{
        String data_str = (char)game.getPlaying().getMainCharacter()+ ArrCharToString(pack.getData());
        dout.writeUTF(data_str);
        dout.flush();
        pack.noChange();
    }

    private String ArrCharToString(char[] arr){
        String str = "";
        for(int i=0;i<arr.length;i++){
            str+=arr[i];
        }
        return str;
    }


    public void close() throws IOException{
        dout.close();
    }

    public Packets getKeyData() {
        return this.keyData;
    }
}
