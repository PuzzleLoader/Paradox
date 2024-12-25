package com.github.puzzle.paradox.core.permissions;

import finalforeach.cosmicreach.savelib.IByteArray;
import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import org.apache.commons.collections.CollectionUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static finalforeach.cosmicreach.io.SaveLocation.getSaveFolderLocation;

//INFO: Not tested, very WIP.
public class GlobalPermissions {
    private static Map<String,PlayerPermissions> loadedPerms = new HashMap<>();

    ////
    //Here for API if internal changes occur
    static PlayerPermissions getPlayerPermissions(String playerID){
        return loadedPerms.get(playerID);
    }
    static void setPlayerPermissions(String playerID,PlayerPermissions permissions){
        loadedPerms.put(playerID,permissions);
    }
    ////
    public static void loadPlayerPermissions(){
        var fileLoc = getSaveFolderLocation() + "/permissions/"  + "playerperms.crbin";
        CRBinDeserializer crBinDeserializer = CRBinDeserializer.getNew();
        try (FileInputStream fis = new FileInputStream(fileLoc)) {
          var data = fis.readAllBytes();
          crBinDeserializer.prepareForRead(ByteBuffer.wrap(data));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var perms = crBinDeserializer.readObjArray("playerperms",PlayerPermissions.class);
        for (var p : perms){
            loadedPerms.put(p.playerID,p);
        }
    }
    public static void savePlayerPermissions(){
        PlayerPermissions[] array = new PlayerPermissions[loadedPerms.size()];
        ArrayList<PlayerPermissions> arrayList = new ArrayList<>(loadedPerms.size());
        arrayList.addAll(loadedPerms.values());
        arrayList.toArray(array);
        CRBinSerializer crbinserializer = CRBinSerializer.getNew();
        crbinserializer.writeObjArray("playerperms",array,0,arrayList.size());
        var fileLoc = getSaveFolderLocation() + "/permissions/"  + "playerperms.crbin";
        IByteArray data = crbinserializer.toByteArray();
        try (FileOutputStream fos = new FileOutputStream(fileLoc)) {
            fos.write(data.items(),0, data.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void savePermissionGroups(){
        //TODO
    }
    public void loadPermissionGroups(){
        //TODO
    }
}
