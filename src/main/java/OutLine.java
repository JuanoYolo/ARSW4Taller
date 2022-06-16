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
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
        outputLine = "HTTP/1.1 200 OK\r\n";
        outputLine += "Content-Type:" + tipo + "\r\n";
        outputLine += "\r\n";
        outputLine += new String(Files.readAllBytes(Paths.get("archivos/" + path)), StandardCharsets.UTF_8);
        out.println(outputLine);
        out.close();
    }

    public static void salidaPathImage(String path, String tipo, Socket clientSocket) throws IOException {
        File image = new File("archivos" + path);
        FileInputStream fileIn = new FileInputStream(image);
        byte[] data = new byte[(int) image.length()];
        fileIn.read(data);
        fileIn.close();
        DataOutputStream binaryOut = new DataOutputStream(clientSocket.getOutputStream());
        binaryOut.writeBytes("HTTP/1.1 200 OK\r\n");
        binaryOut.writeBytes("Content-Type: image/" + tipo + "\r\n");
        binaryOut.writeBytes("Content-Length: " + data.length);
        binaryOut.writeBytes("\r\n\r\n");
        binaryOut.write(data);
        binaryOut.close();

    }
}
