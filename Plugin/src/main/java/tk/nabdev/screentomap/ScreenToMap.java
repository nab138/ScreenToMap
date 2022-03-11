package tk.nabdev.screentomap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import tk.nabdev.screentomap.commands.mapCommand;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.Buffer;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import javax.imageio.ImageIO;

public final class ScreenToMap extends JavaPlugin {
    FileConfiguration config = getConfig();
    public mapCommand mapcmd;
    @Override
    public void onEnable() {
        config.addDefault("webSocketUrl", "ws://localhost:8080");
        config.options().copyDefaults(true);
        saveConfig();

        try {
            ExampleClient c = new ExampleClient(new URI(config.getString("webSocketUrl")));
            c.connect();
        } catch (URISyntaxException e){
            e.printStackTrace();
        }
        mapcmd = new mapCommand();
        getCommand("map").setExecutor(mapcmd);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public class ExampleClient extends WebSocketClient {

        public ExampleClient(URI serverUri, Draft draft) {
            super(serverUri, draft);
        }

        public ExampleClient(URI serverURI) {
            super(serverURI);
        }

        public ExampleClient(URI serverUri, Map<String, String> httpHeaders) {
            super(serverUri, httpHeaders);
        }
        @Override
        public void onOpen(ServerHandshake handshakedata) {
            System.out.println("opened connection");
            // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
        }

        @Override
        public void onMessage(String message) {
            if(mapcmd.renderers[5] != null) {
                if (Objects.equals(message, "hb")) {
                    send("i");
                } else {
                    try {
                        byte[] data = Base64.getMimeDecoder().decode(message.trim());
                        InputStream is = new ByteArrayInputStream(data);
                        BufferedImage image = ImageIO.read(is);
                        mapcmd.renderers[0].image = cropImage(image, 0, 0, 128, 128);
                        mapcmd.renderers[1].image = cropImage(image, 128, 0, 128, 128);
                        mapcmd.renderers[2].image = cropImage(image, 256, 0, 128, 128);
                        mapcmd.renderers[3].image = cropImage(image, 0, 128, 128, 88);
                        mapcmd.renderers[4].image = cropImage(image, 128, 128, 128, 88);
                        mapcmd.renderers[5].image = cropImage(image, 256, 128, 128, 88);
                        send("f");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            // The close codes are documented in class org.java_websocket.framing.CloseFrame
            System.out.println(
                    "Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: "
                            + reason);
        }

        @Override
        public void onError(Exception ex) {
            ex.printStackTrace();
            // if the error is fatal then onClose will be called additionally
        }
        private BufferedImage cropImage(BufferedImage image, Integer startX, Integer startY, Integer endX, Integer endY) throws IOException {
            return image.getSubimage(startX, startY, endX, endY);
        }

    }
}
