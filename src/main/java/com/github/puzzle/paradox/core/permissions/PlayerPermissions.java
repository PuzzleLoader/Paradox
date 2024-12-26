package com.github.puzzle.paradox.core.permissions;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.ICRBinSerializable;
import org.apache.commons.collections.CollectionUtils;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerPermissions implements ICRBinSerializable {
    String playerID;
    Set<String> groupNames = new HashSet<>();

    //should these be prioritised over group perms?
    Set<String> individualPerms = new HashSet<>();
    public PlayerPermissions(){

    }
    public PlayerPermissions(@NotNull Set<String> individualPerms, @NotNull   Set<String> groupPerms, @NotNull String plrID){
        playerID = plrID;
        groupNames = groupPerms;
        this.individualPerms  = individualPerms;
    }
    public PlayerPermissions(@Nullable String iPerm,@Nullable String gPerm,@NotNull String plrID){
        playerID = plrID;
        if(iPerm != null && !iPerm.isBlank()){
            individualPerms.add(iPerm);
        }
        if(gPerm != null && !gPerm.isBlank()){
            groupNames.add(gPerm);
        }
    }

    public void addIndividualPermission(Permission permission){
        individualPerms.add(permission.getPermissionVarString());
    }
    public void addGroup(PermissionGroup group){
        groupNames.add(group.getName());
    }
    public void removeIndividualPermission(Permission permission){
        individualPerms.remove(permission.getPermissionVarString());
    }
    public void removeGroup(PermissionGroup group){
        groupNames.remove(group.getName());
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
