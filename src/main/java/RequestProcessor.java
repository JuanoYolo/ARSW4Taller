import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

public class RequestProcessor implements Runnable{

    private final Socket clientSocket;

    public RequestProcessor(Socket clientSocket){
        this.clientSocket = clientSocket;
    }


    public void run(){
        try {
            process();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void process() throws IOException, URISyntaxException {
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
            outLine.salidaPathImage(path,tipo,clientSocket);
        }else{
            System.out.println("Tipo de archivo no admitido");
        }
        in.close();

        clientSocket.close();
    }

}
