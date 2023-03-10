package com.os.lab1;

import com.os.lab1.counters.CounterF;
import com.os.lab1.counters.CounterG;
import com.os.lab1.manager.Manager;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Manager manager = new Manager("localhost", 8080, 8000);
        Thread mThread = new Thread(manager);
        mThread.start();

        CounterF counterF = new CounterF("localhost", 8080);
        Thread fThread = new Thread(counterF);
        fThread.start();

        CounterG counterG = new CounterG("localhost", 8000);
        Thread gThread = new Thread(counterG);
        gThread.start();
        System.out.println(manager.count(0));
//        System.out.println(manager.count(1));
//        System.out.println(manager.count(2));
//        System.out.println(manager.count(3));
//        System.out.println(manager.count(4));
//        System.out.println(manager.count(5));
    }
}
