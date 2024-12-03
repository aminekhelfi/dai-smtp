package org.example;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;




public class main {
    public static void main(String[] args) throws IOException {

        PrankGenerator prank=new PrankGenerator(5); //a passer en paramètre
        prank.generateAndSendPranks(5);//laisser fixe
    }
}


