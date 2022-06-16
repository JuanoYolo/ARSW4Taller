import java.io.IOException;
import java.io.PrintWriter;
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
}
