import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.net.Socket;

public class Write implements Runnable {
    private FileInputStream fileInputStream;
    private OutputStream socketOutputStream;
    private int bufferSize;
    private Socket socket;

    public Write(FileInputStream fileInputStream, Socket socket, int bufferSize, OutputStream socketOutputStream) {
        this.fileInputStream = fileInputStream;
        this.bufferSize = bufferSize;
        this.socketOutputStream = socketOutputStream;
        this.socket = socket;

    }

    public void run() {
        try {
            int numBytes = 0;
            byte[] buff = new byte[bufferSize];

            while ((numBytes = fileInputStream.read(buff)) != -1) {
                socketOutputStream.write(buff, 0, numBytes);
                System.out.println("W " + numBytes); // written to socket
                socketOutputStream.flush();
            }
            socket.shutdownOutput();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
