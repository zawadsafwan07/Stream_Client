import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Read implements Runnable {

    private InputStream socketInputStream;
    private FileOutputStream fileOutputStream;
    private int bufferSize;

    public Read(InputStream socketInputStream, FileOutputStream fileOutputStream, int bufferSize) {
        this.socketInputStream = socketInputStream;
        this.fileOutputStream = fileOutputStream;
        this.bufferSize = bufferSize;
    }

    public void run() {
        try {
            int numBytes;
            byte[] buff = new byte[bufferSize];
            while ((numBytes = socketInputStream.read(buff)) != -1) {
                System.out.println("R " + numBytes); // Read from socket
                fileOutputStream.write(buff, 0, numBytes);
                fileOutputStream.flush();
            }
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
