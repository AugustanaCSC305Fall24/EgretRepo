package edu.augustana;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class HamRadioClientMain {

    public static void main(String[] args) throws Exception {


//        HamRadioServerClient.createServer("SERV1",0.0,0.0);
        HamRadioServerClient.connectToSever("QWER");
        Thread.sleep(1000);
        Map<String, List<String>> serverClientsMap = HamRadioServerClient.getAvailableServers();
        ((java.util.Map<?, ?>) serverClientsMap).forEach((serverId, clients) -> {
            System.out.println("Server ID: " + serverId);
            System.out.println("Connected Clients: " + clients);
        });

        while(true){
            Thread.sleep(5000);
            HamRadioServerClient.sendMessage("Hello World");
        }

    }

}
