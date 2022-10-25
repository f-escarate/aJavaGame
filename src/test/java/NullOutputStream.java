import java.io.IOException;
import java.io.OutputStream;

/**
 * Esta clase se sacó del ppt de la clase 11
 * (No la hice yo)
 */
public class NullOutputStream extends OutputStream {
    public NullOutputStream() { super(); }
    //  Null implementation of inherited abstract method
    public void write(int b) throws IOException { }
}