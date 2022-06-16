import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpServer {

    static String tipes;

    public static  HttpServer _instance = new HttpServer();

    public HttpServer() {
    }

    public static HttpServer getInstance(){
        return _instance;
    }

    public static void start(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
    } catch (IOException e){
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while (running){
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e){
                System.err.println("Accept failed.");
                System.exit(1);
            }
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;

            String tipo = "";
            String path = "";
            boolean firstLine = true;

            while ((inputLine = in.readLine()) != null){
                if(firstLine){
                    path = inputLine.split(" ")[1];
                    tipo = path.split("\\.")[1];
                    System.out.println("Path: " + path);
                    URI resource = new URI(path);
                    System.out.println("Path: " + resource.getPath());
                    firstLine = false;
                }
                System.out.println("Received: " + inputLine);
                if(!in.ready()){
                    break;
                }
            }

            OutLine outLine = new OutLine();

            if (tipo.equals("html") || tipo.equals("js") || tipo.equals("css")){
                outLine.salidaPathTxt(path,tipo,clientSocket);

            } else if (tipo.equals("png") || tipo.equals("jpg")) {
                continue;
            }
            in.close();

            clientSocket.close();
        }
        serverSocket.close();
    }
}
