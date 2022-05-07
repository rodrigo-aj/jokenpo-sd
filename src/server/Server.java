package server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        final int PORT = 9876;
        ServerSocket server;
        Socket client;

        try {
            server = new ServerSocket(PORT);
        } catch (Exception e) {
            System.out.println("Porta em uso...");
            return;
        }

        try {
            while (true) {
                System.out.println("Aguardando conexão...");
                client = server.accept();
                Processa processa = new Processa(client);
                processa.start();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
