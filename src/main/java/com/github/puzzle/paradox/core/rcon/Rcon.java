package com.github.puzzle.paradox.core.rcon;

import com.github.puzzle.paradox.game.server.ParadoxServerSettings;
import com.google.common.primitives.Ints;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import finalforeach.cosmicreach.server.ServerLauncher;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

//TODO: implement rcon
public class Rcon {


    public static final int MULTI_PACKET_RESPONSE = 0;
    public static final int COMMAND = 2;
    public static final int LOGIN = 3;

    public static class RconAcceptor implements Runnable {

        RconAcceptor(List<RconClient> clients){
            try {
                socket = new ServerSocket(Ints.constrainToRange(ServerSingletons.puzzle.serverConfig.getInt("rcon.port"),1,(Short.MAX_VALUE*2)+1),0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            password = ServerSingletons.puzzle.serverConfig.getString("rcon.password");
            this.clients = clients;
        }
        ServerSocket socket;
        String password;

        public boolean isRunning = true;
        final List<RconClient> clients;
        @Override
        public void run() {
            while (isRunning) {
                try {
                    var clientSocket = socket.accept();
                    synchronized (clients) {
                        clients.add(new RconClient(clientSocket));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public static class RconRunner implements Runnable {
        final List<RconClient> clients = new ArrayList<>();

        public final RconAcceptor acceptor = new RconAcceptor(clients);
        public final Thread acceptorThread = new Thread(acceptor,"Rcon Acceptor");
        public RconRunner(){

        }

        @Override
        public void run() {
            while(ServerLauncher.isRunning()){

               for (var client : this.clients){
                   client.act();
               }

            }
            acceptor.isRunning = false;
            this.clients.forEach(rconClient -> {
                try {
                    rconClient.client.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                this.clients.remove(rconClient);

            });
        }
    }
    /* TODO: implement
    rconThread = new Thread(new Rcon.RconRunner(),"RCON thread");
    rconThread.setDaemon(false);
    rconThread.start();
     */
    public static class RconClient {
        public RconClient(Socket socket){
            this.client = socket;
        }

        Socket client;

        boolean isAuthed = true;

        void act(){
            BufferedInputStream stream = null;
            byte[] buf = new byte[4];
            try {
                stream = new BufferedInputStream(this.client.getInputStream());
                stream.read(buf,0,4);
                int size = ByteBuffer.wrap(buf).order(ByteOrder.LITTLE_ENDIAN).getInt();
                /* TODO: impliment checks
                if(size < 10){
                    client.close();
                }
                */
                if(size > 2048)
                    size = 2048;
                buf = new byte[size];
                stream.read(buf,4,size);
                ByteBuffer packetData = ByteBuffer.wrap(buf).order(ByteOrder.LITTLE_ENDIAN);
                int ID = packetData.getInt();
                int type = packetData.getInt(4);
                ByteBuffer stringBuf =  packetData.slice(8,size-8-1).order(ByteOrder.LITTLE_ENDIAN);
                String body = StandardCharsets.UTF_8.decode(stringBuf).toString();
                packetData.clear();
                switch (type) {
                    case LOGIN:
                        if(body.equals(ParadoxServerSettings.RCONpassword)){
                            client.getOutputStream().write(new byte[]{ 0x0,0x0,0x2,0x0,0x0,0x0,});
                            var bb = ByteBuffer.allocate(4).putInt(ID).flip();
                            client.getOutputStream().write(bb.array(),6,4);
                            client.getOutputStream().write(new byte[]{0xa,0x0,0x0,0x0},10,4);
                            isAuthed = true;
                        }else{
                            client.getOutputStream().write(new byte[]{0x0,0x0,0x2,0x0,0x0,0x0, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,0xa,0x0,0x0,0x0});
                        }
                        client.getOutputStream().flush();
                        break;
                    case COMMAND:
                        //TODO:
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
