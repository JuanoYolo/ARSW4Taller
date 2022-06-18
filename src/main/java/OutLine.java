import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OutLine {

    public static String outputLine;
    public OutLine() {
    }

    public static void salidaPathTxt(String path, String tipo, Socket clientSocket) throws IOException {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            outputLine = "HTTP/1.1 200 OK\r\n";
            outputLine += "Content-Type:" + tipo + "\r\n";
            outputLine += "\r\n";
            outputLine += new String(Files.readAllBytes(Paths.get("archivos/" + path)), StandardCharsets.UTF_8);
            out.println(outputLine);
            out.close();
        } catch(IOException ex){
            salidaPathErrNotFound(clientSocket);
        }
    }

    public static void salidaPathImage(String path, String tipo, Socket clientSocket) throws IOException {
        try {
            File image = new File("archivos/" + path);
            FileInputStream receiveFile = new FileInputStream(image);
            byte[] data = new byte[(int) image.length()];
            receiveFile.read(data);
            receiveFile.close();
            DataOutputStream binaryOut = new DataOutputStream(clientSocket.getOutputStream());
            binaryOut.writeBytes("HTTP/1.1 200 OK\r\n");
            binaryOut.writeBytes("Content-Type: image/" + tipo + "\r\n");
            binaryOut.writeBytes("Content-Length: " + data.length);
            binaryOut.writeBytes("\r\n\r\n");
            binaryOut.write(data);
            binaryOut.close();

        } catch(IOException ex){
            salidaPathErrNotFound(clientSocket);
        }
    }

    public static void salidaPathErrNotFound(Socket clientSocket) throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
        String outputLine = "HTTP/1.1 404 URL Not Found\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<title>Error 404</title>\n"
                + "<head>"
                + "<body>"
                + "<p>Error 404</p>"
                + "</body>"
                + "</html>";

        out.println(outputLine);
        out.close();
    }
}
