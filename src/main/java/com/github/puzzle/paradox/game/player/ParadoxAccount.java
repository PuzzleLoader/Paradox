package com.github.puzzle.paradox.game.player;

import com.badlogic.gdx.utils.Json;
import finalforeach.cosmicreach.accounts.AccountItch;
import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.networking.server.ServerIdentity;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import org.hjson.JsonObject;
import org.hjson.JsonValue;
import org.hjson.Stringify;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;

import static com.github.puzzle.paradox.game.server.ParadoxServerSettings.isOffline;
import static com.github.puzzle.paradox.game.server.ParadoxServerSettings.itAPIkey;

public abstract class ParadoxAccount {
    public String username;
    public String uniqueId;
    public String displayname;
    public boolean isValid = true;
    public boolean tpRequst = false;
    private Player tprPlayer;
    private Player tprToPlayer;

    public void addTpr(ServerIdentity id, Player playerToTp){
        this.tpRequst = true;
        this.tprPlayer = ServerSingletons.getPlayer(id);
        this.tprToPlayer = playerToTp;
    }

    public Player getTprPlayer() {
        return tprPlayer;
    }

    public Player getTprToPlayer() {
        return tprToPlayer;
    }
    public abstract String getPrefix();
    public void validateItchAccount(long userid){
        //damn you zombii
        if(!isOffline) {
            System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");

            HttpsURLConnection httpsurlconnection = null;

            try {
                String apikey = (itAPIkey.isBlank() || itAPIkey.isEmpty()) ? "key" : itAPIkey;
//                httpsurlconnection = (HttpsURLConnection) new URL("https://itch.io/api/1/" + apikey + "/game/2557309/purchases").openConnection();
                httpsurlconnection = (HttpsURLConnection) new URL( "https://api.itch.io/users/"+ userid).openConnection();
                httpsurlconnection.setRequestMethod("GET");
                httpsurlconnection.setRequestProperty("Authorization", "Bearer " + apikey);
            } catch (IOException e) {
            }
            try {
                String s1 = new String(httpsurlconnection.getInputStream().readAllBytes());

                var obj = JsonValue.readJSON(s1).asObject();

//                System.out.println(obj.toString(Stringify.FORMATTED));
                var errors = obj.get("errors");
                if(errors == null && uniqueId.equals("itch:" + userid)){

                    isValid = true;
                    var username = obj.get("user").asObject().get("username").asString();
                    this.displayname = username;
                    this.username = this.getPrefix() + ":" + username;
                    return;
                }

                System.out.println("Invalid userid or itch id: " + uniqueId + " | " + userid);
                isValid = false;

            } catch (IOException e) {
            }
            httpsurlconnection.disconnect();
        }
    }
}
