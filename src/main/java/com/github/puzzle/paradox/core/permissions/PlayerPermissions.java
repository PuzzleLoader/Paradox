package com.github.puzzle.paradox.core.permissions;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.ICRBinSerializable;
import org.apache.commons.collections.CollectionUtils;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlayerPermissions implements ICRBinSerializable {
    String playerID;
    List<String> groupNames = new ArrayList<>();

    //should these be prioritised over group perms?
    List<String> individualPerms = new ArrayList<>();
    public PlayerPermissions(){

    }
    public PlayerPermissions(@NotNull List<String> individualPerms, @NotNull List<String> groupPerms){
        groupNames = groupPerms;
        this.individualPerms  = individualPerms;
    }
    public PlayerPermissions(@Nullable String iPerm,@Nullable String gPerm){
        if(iPerm != null && !iPerm.isBlank()){
            individualPerms.add(iPerm);
        }
        if(gPerm != null && !gPerm.isBlank()){
            groupNames.add(gPerm);
        }
    }
    public boolean hasPermission(@NotNull String name){
        for (var p : individualPerms){
            if(p.equals(name))
                return true;
        }
        for (var gn : groupNames){
            var g = GlobalPermissions.getPermissionGroup(gn);
            if(g.contains(name))
                return true;
        }
        return false;
    }
    @Override
    public void read(CRBinDeserializer deserializer) {
       playerID = deserializer.readString("playerID");
       CollectionUtils.addAll(groupNames, deserializer.readStringArray("groupNames"));
       CollectionUtils.addAll(individualPerms, deserializer.readStringArray("individualPerms"));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeString("playerID",playerID);
        serializer.writeStringArray("groupNames",groupNames.toArray(String[]::new), groupNames.size());
        serializer.writeStringArray("individualPerms",individualPerms.toArray(String[]::new),individualPerms.size());
    }
}
