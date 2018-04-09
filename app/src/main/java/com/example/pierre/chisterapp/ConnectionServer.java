package com.example.pierre.chisterapp;

/**
 * Created by Adri on 03/04/2018.
 */

import android.net.Uri;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectionServer {

    // Address of the server PHP script
    private String serverURI;

    // Used for file upload
    private final String lineEnd;
    private final String twoHyphens;
    private final String boundary;

    // Variables for connection to the server
    private final int CONNECTION_TIMEOUT;
    private final int READ_TIMEOUT;

    /**
     * ServerConnection(String scriptToConnectTo)
     * This is the constructor of the class.
     * @param scriptToConnectTo: the PHP script to connect to
     */
    public ConnectionServer(String scriptToConnectTo){
        lineEnd = "\r\n";
        twoHyphens = "--";
        boundary = "*****";

        serverURI = "http://app-1522749568.000webhostapp.com/"+scriptToConnectTo;
        //ConnectionServer cs = new ConnectionServer("get_update.php")
        CONNECTION_TIMEOUT = 300000;
        READ_TIMEOUT = 300000;
    }

    private String sendToServer(Uri.Builder builder) throws IOException{
        HttpURLConnection connection = null;
        URL url = null;
        int serverResponseCode = 0;

        try{
            url = new URL(serverURI);

            // Start opening a connection to the address given
            connection = (HttpURLConnection) url.openConnection();

            // Set up the connection parameters
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            // We transfer data using POST method
            connection.setRequestMethod("POST");

            // Disable keepAlive to prevent Connection reset issues
            System.setProperty("http.keepAlive", "false");

            // Build the parameters and variables to send to the PHP script
            String query = builder.build().getEncodedQuery();

            // Set up the server response (echo in PHP)
            OutputStream outputStream = connection.getOutputStream();

            // Make sure the connection actually opened
            if(outputStream!=null){
                // Create a writer to write to the server
                BufferedWriter writer = new BufferedWriter
                        (new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(query);

                // Close down the writer once writing has been done
                // This prevents connections staying open
                writer.flush();
                writer.close();
                outputStream.close();
            }

            connection.connect();

            // Get the response code from the PHP script
            // 200 means OK, 500 means server error, 404 means script not found
            serverResponseCode = connection.getResponseCode();

            // Print out the code in the device Logcat for debug purposes
            System.out.println("serverResponseCode: "+ serverResponseCode);

            // If the script was executed correctly on the server side
            if(serverResponseCode == HttpURLConnection.HTTP_OK){
                // Open the data stream coming back from the server
                // (echo in PHP)
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder resultFromServer = new StringBuilder();
                String line;

                // As long as there is data to read from the server
                while((line = reader.readLine())!=null){
                    resultFromServer.append(line);
                }
                // Print the server response in the console for debug purposes
                System.out.println("serverResponse: "+resultFromServer.toString());
                return(resultFromServer.toString());
            }
            else{
                return ("unsuccessful");
            }
        }catch (MalformedURLException mue){
            mue.printStackTrace();
            System.out.println("MalformedURLException");
        }catch (IOException ioe){
            ioe.printStackTrace();
            System.out.println("IOException");
        }finally {
            // Force close the server connection
            connection.disconnect();
        }
        return "failed";
    }

    String Aff_Match() throws IOException {
        String text = "";
        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("blank", text);


        return sendToServer(builder);
    }

    String Delete_Match(int matchId) throws IOException {
        String matchIdToSend = String.valueOf(matchId);
        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("matchId", matchIdToSend);

        return sendToServer(builder);
    }

    String getCreate_Match(String team1, String team2, int score1, int fautes1 , int score2, int fautes2) throws IOException {
        //String matchIdToSend = String.valueOf(matchId);
        String score1ToSend = String.valueOf(score1);
        String fautes1ToSend = String.valueOf(fautes1);
        String score2ToSend = String.valueOf(score2);
        String fautes2ToSend = String.valueOf(fautes2);

        Uri.Builder builder = new Uri.Builder()
                //.appendQueryParameter("matchId", matchIdToSend)
                .appendQueryParameter("equipe1", team1)
                .appendQueryParameter("equipe2", team2)
                .appendQueryParameter("score1", score1ToSend)
                .appendQueryParameter("fautes1", fautes1ToSend)
                .appendQueryParameter("score2", score2ToSend)
                .appendQueryParameter("fautes2", fautes2ToSend);

        System.out.println(team1);
        System.out.println(team2);

        return sendToServer(builder);
    }

    String Update_Match(int matchId, int score1, int fautes1 , int score2, int fautes2) throws IOException {
        String matchIdToSend = String.valueOf(matchId);
        String score1ToSend = String.valueOf(score1);
        String fautes1ToSend = String.valueOf(fautes1);
        String score2ToSend = String.valueOf(score2);
        String fautes2ToSend = String.valueOf(fautes2);

        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("matchId", matchIdToSend)
                .appendQueryParameter("score1", score1ToSend)
                .appendQueryParameter("fautes1", fautes1ToSend)
                .appendQueryParameter("score2", score2ToSend)
                .appendQueryParameter("fautes2", fautes2ToSend);

        return sendToServer(builder);
    }
}
