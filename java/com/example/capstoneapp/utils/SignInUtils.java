package com.example.capstoneapp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SignInUtils {
    public static String makeHTTPRequest(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        InputStream input = con.getInputStream();


        try{
            Scanner scanner = new Scanner(input);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput)
                return scanner.next();
            else return null;
        }
        finally {
            con.disconnect();
        }

    }
}
