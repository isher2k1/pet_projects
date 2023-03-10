package com.os.lab1.manager;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Manager implements Runnable {
    String hostname;
    int portF;
    int portG;
    private static final String POISON__PILL = "POISON__PILL";
    private Selector selector;
    private ServerSocketChannel serverSocketF;
    private ServerSocketChannel serverSocketG;
    private Process process;
    private boolean inited = false;


    public Manager(String hostname, int portF, int portG) throws IOException, InterruptedException {
        this.hostname = hostname;
        this.portF = portF;
        this.portG = portG;
        this.init();
    }

    private void init() throws IOException {
        selector = Selector.open();

        serverSocketF = ServerSocketChannel.open();
        serverSocketF.bind(new InetSocketAddress(hostname, portF));
        serverSocketF.configureBlocking(false);
        serverSocketF.register(selector, SelectionKey.OP_ACCEPT);

        serverSocketG = ServerSocketChannel.open();
        serverSocketG.bind(new InetSocketAddress(hostname, portG));
        serverSocketG.configureBlocking(false);
        serverSocketG.register(selector, SelectionKey.OP_ACCEPT);
    }

    public double count(int i) throws IOException, InterruptedException {
        ByteBuffer buffer = ByteBuffer.allocate(256);
        if (inited) {
        }else {
            inited = true;
        }
        registerAllToReadAndWrite();
        sendCountRequest(i);
        List<Double> list = getCountResults(buffer);
        double res = list.get(0);
        for (int j = 1; j < list.size(); j++) {
            res *= list.get(j);
        }
        return res;
    }

    private List<Double> getCountResults(ByteBuffer buffer) throws IOException {
        int readed = 0;
        List<Double> list = new ArrayList<>();
        while (readed < 2) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                if (key.isReadable()) {
                    double temp = Double.parseDouble(read(buffer, key));
                    list.add(temp);
                    readed++;
                    if (temp == 0) {
                        readed = 2;
                        iter.remove();
                        break;
                    }
                    if (readed >= 2) {
                        iter.remove();
                        break;
                    }
                }
                iter.remove();
            }
        }
        return list;
    }

    void sendCountRequest(int i) throws IOException {
        int send = 0;
        while (send < 2) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();

                if (key.isWritable()) {
                    write("count" + i, key);
                    send++;
                    if (send >= 2) {
                        iter.remove();
                        break;
                    }
                }
                iter.remove();
            }
        }
    }

    void registerAllToReadAndWrite() throws IOException {
        int registred = 0;
        while (registred < 2) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                if (key.isAcceptable()) {
                    if (((ServerSocketChannel) key.channel()).socket().getLocalPort() == serverSocketF.socket().getLocalPort()) {
                        registerToReadAndWrite(selector, serverSocketF);
                    }
                    if (((ServerSocketChannel) key.channel()).socket().getLocalPort() == serverSocketG.socket().getLocalPort()) {
                        registerToReadAndWrite(selector, serverSocketG);
                    }
                    registred++;
                }
                iter.remove();
            }
        }
    }

    @Override
    public void run() {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = Manager.class.getCanonicalName();

        ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);
        try {
            process = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String read(ByteBuffer buffer, SelectionKey key)
            throws IOException {

        SocketChannel client = (SocketChannel) key.channel();
        client.read(buffer);
        String res = new String(buffer.array()).trim();
        if (res.equals(POISON__PILL)) {
            client.close();
            System.out.println("Not accepting client messages anymore");
        }

        buffer.flip();
        buffer.clear();
        return res;
    }

    private static void write(String str, SelectionKey key) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        write(buffer, key);
    }

    private static void write(ByteBuffer buffer, SelectionKey key)
            throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        client.write(buffer);
        buffer.clear();
    }

    private static void registerToReadAndWrite(Selector selector, ServerSocketChannel serverSocket)
            throws IOException {
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        int interestSet = SelectionKey.OP_WRITE | SelectionKey.OP_READ;
        client.register(selector, interestSet);
    }

    public void stop() {
        process.destroy();
    }

}
