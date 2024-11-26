package org.example;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;




public class Main {
    public static void main(String[] args) throws IOException {

        PrankGenerator prank=new PrankGenerator(5);
        prank.generateAndSendPranks(3);

        /*test
        List<String> test = new ArrayList<>();
        test.add("<amine.khelfi@gmail.com>");
        test.add("<a.b@gmail.com>");
        SmtpClient client = new SmtpClient("localhost", 1025);
        client.sendEmail("<hurni.romain@gmail.com>",test , "i2jevjier2");*/





    }
}


