package uco;

import main.java.com.constants.Constants;
import com.sockets.SocketServer;
import java.io.IOException;
import java.util.HashMap;
import CancelKey.KeyListener;
import org.jnativehook.GlobalScreen;
import java.util.logging.*;
import org.jnativehook.NativeHookException;

import sun.misc.Signal;
import uco.Printy;

public class Main {
    private final SocketServer socketServer = new SocketServer("localhost", 7777);
    private final Printy demo = new Printy();
    private Integer variant = null;
    public KeyListener listener = null;
    public int numer;
    public void main(String[] args) throws Exception {

        while (true) {
            this.interactiveMenu();   //запускаем меню
            this.start();
        }
    }

    public void interactiveMenu() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);
        while (true) {
            this.demo.showVariants();
            this.variant = this.demo.inputVariant();
            try {
                GlobalScreen.registerNativeHook();
                listener = new KeyListener();     // это для получения ввода ескейпа для канцеляции
                GlobalScreen.addNativeKeyListener(listener);
            } catch (NativeHookException ex) {
                numer++;
            }   
            if (this.variant != null) {
                this.socketServer.setVariant(this.variant); // запуск выбраного варианта
                break;
            }
            
            System.out.println("Provided incorrect number of variant, try again!\n");
        }
    }

    public void onExit() {    // при выходе получить и напечатать результат 
        System.out.println("\nExit by user ...");

        HashMap<String, String> results = this.socketServer.getResults();

        if (results.get(Constants.FUNC_F) == null) {
            System.out.println(Constants.FUNC_F + " has not been computed ...");
        }

        if (results.get(Constants.FUNC_G) == null) {
            System.out.println(Constants.FUNC_G + " has not been computed ...");
        }

        System.exit(Constants.EXIT_CODE);
    }

    public void start() throws IOException {  
        Signal.handle(new Signal("INT"), signal -> {
            this.onExit();
        } );
        this.socketServer.startServer(); // запускаем сервер
        System.out.println("Result of computations is: " + this.socketServer.getMultiplication()); // получаем результат  иссчесления от сервера
    }
}


