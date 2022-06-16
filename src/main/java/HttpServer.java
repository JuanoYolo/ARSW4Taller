import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    static String tipes;

    public static  HttpServer _instance = new HttpServer();

    public HttpServer() {
    }

    public static HttpServer getInstance(){
        return _instance;
    }

    public static void start(String[] args) throws IOException, URISyntaxException {
        ExecutorService poolDeHilos = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
    } catch (IOException e){
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        }
        RequestProcessor requestProcessor = new RequestProcessor(clientSocket);

        poolDeHilos.execute(requestProcessor);
        }

        serverSocket.close();
    }

    private static int getPort(){
        if(System.getenv("PORT") != null){
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 36000;
    }
}
