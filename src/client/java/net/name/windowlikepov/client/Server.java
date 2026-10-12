package net.name.windowlikepov.client;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicReference;

public class Server {
    public static final AtomicReference<float[]> pointArrShared = new AtomicReference<>();

    public static void runServer() {
        Thread serverThread = new Thread(() -> {
            try {
                HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

                server.createContext("/", exchange -> {
                    setCorsHeaders(exchange);

                    if ("OPTIONS".equals(exchange.getRequestMethod())) {
                        exchange.sendResponseHeaders(200, -1);
                        return;
                    }

                    // Read request body
                    byte[] body = exchange.getRequestBody().readNBytes(13);
                    if (body.length != 12) {
                        System.out.println("Expected exactly 12 bytes");
                        exchange.sendResponseHeaders(400, -1);
                        return;
                    }

                    ByteBuffer buffer = ByteBuffer.wrap(body).order(ByteOrder.BIG_ENDIAN);

                    float[] values = {
                            buffer.getFloat(),
                            buffer.getFloat(),
                            buffer.getFloat()
                    };

                    // Write to shared memory
                    pointArrShared.set(values);

                    //System.out.println("Received data" );

                    // Send response
                    String response = "ok";
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    exchange.getResponseBody().write(response.getBytes());
                    exchange.close();
                });

                server.setExecutor(null);
                server.start();
                System.out.println("Server running on http://localhost:8000");

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        serverThread.setDaemon(true);
        serverThread.start();
    }

    private static void setCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
    }
}

