package client;

import main.Game;

import java.io.IOException;
import java.net.Socket;

public class ManagerSocket {
    Socket s;
    Receive receive;
    Send send;
    Game game;
    public ManagerSocket(Game game, String host, int port) throws IOException {
        this.game = game;
        s = new Socket(host, port);
        send = new Send(s, game);
        receive = new Receive(game, s);
    }
    public void close() throws IOException {
        send.close();
        receive.close();
        s.close();
    }

    public Send getSend() {
        return this.send;
    }
}
