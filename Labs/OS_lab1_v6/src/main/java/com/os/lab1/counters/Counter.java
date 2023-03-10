package com.os.lab1.counters;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public abstract class Counter implements Runnable{
    private String hostname;
    private int port;
    private SocketChannel client;
    private ByteBuffer buffer;

    @Override
    public void run() {
        try {
            client = SocketChannel.open(new InetSocketAddress(hostname, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            String str = readMessage();
            if(str != null && str.matches("count(\\d)")) {
                count(Integer.parseInt(str.replace("count" ,"")));
            }
        }
    }

    public void stop() throws IOException {
        this.client.close();
        this.buffer = null;
    }

    public Counter(String hostname, int port) {
        buffer = ByteBuffer.allocate(256);
        this.hostname = hostname;
        this.port = port;
    }

    public void sendMessage(String msg) {
        buffer = ByteBuffer.wrap(msg.getBytes());
        try {
            client.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer.clear();
    }

    public String readMessage() {
        buffer.clear();

        try {
            client.read(buffer);
        } catch (IOException e) {
            //ToDo
        }

        String response = new String(buffer.array()).trim();
        buffer.flip();
        buffer.clear();
        return response;
    }

    public abstract void count(int i);
}
