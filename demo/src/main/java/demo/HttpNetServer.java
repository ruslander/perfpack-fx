package demo;

import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.net.InetSocketAddress;


public class HttpNetServer
{
    static final int port=8080;

    public static void main(String args[])
    {
        try
        {
            HttpServer server=HttpServer.create(new InetSocketAddress(port), 0);

            server.createContext("/", httpExchange ->
            {
                byte response[]="Hello, World!".getBytes("UTF-8");

                httpExchange.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");

                spin(100);

                httpExchange.sendResponseHeaders(200, response.length);

                OutputStream out=httpExchange.getResponseBody();
                out.write(response);
                out.close();
            });

            server.start();
        }
        catch (Throwable tr)
        {
            tr.printStackTrace();
        }
    }

    private static void spin(int milliseconds) {
        long sleepTime = milliseconds*1000000L; // convert to nanoseconds
        long startTime = System.nanoTime();
        while ((System.nanoTime() - startTime) < sleepTime) {}
    }

}