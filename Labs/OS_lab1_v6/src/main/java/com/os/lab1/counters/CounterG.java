package com.os.lab1.counters;

import spos.lab1.demo.DoubleOps;

public class CounterG extends Counter {

    public CounterG(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public void count(int i) {
        new Thread(() -> {
            double res = -1;
            try {
                res = DoubleOps.funcG(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            sendMessage(Double.toString(res));
        }).start();
    }
}
