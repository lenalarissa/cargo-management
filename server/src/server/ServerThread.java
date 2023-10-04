package server;

import events.NetEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServerThread implements Runnable {

    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final TCPServer tcpServer;

    public ServerThread(ObjectOutputStream out, ObjectInputStream in, TCPServer tcpServer) throws IOException {
        this.out = out;
        this.in = in;
        this.tcpServer = tcpServer;
    }

    @Override
    public void run() {
        tcpServer.addThread(Thread.currentThread().getId(), out);
        try {
            while (true) {
                NetEvent netEvent = (NetEvent) in.readObject();
                tcpServer.findEvent(netEvent);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
