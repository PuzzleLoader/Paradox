package com.github.puzzle.paradox.core.permissions;

import com.github.puzzle.paradox.core.PuzzlePL;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.ICRBinSerializable;
import finalforeach.cosmicreach.savelib.crbin.SchemaType;
import finalforeach.cosmicreach.savelib.utils.IDynamicArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionGroup implements ICRBinSerializable {
    public PermissionGroup(String name, String... perms){
        this.permissionGroupName = name;
        for(String permString : perms){
            assert(permString == null || permString.isBlank() || permString.isEmpty());
            if(ServerSingletons.puzzle.getPermission(permString) != null)
                permissionList.putIfAbsent(permString,ServerSingletons.puzzle.getPermission(permString));
        }
    }

    public static PermissionGroup DEFAULT_GROUP;
    String permissionGroupName;

    HashMap<String,Permission> permissionList = new HashMap<>();

    public void add(Permission perm){
        if(perm == null)
            throw new RuntimeException("Permission cannot be null");
        permissionList.putIfAbsent(perm.permissionVarString, perm);

    }
    public void remove(String permissionVarString){
        permissionList.remove(permissionVarString);
    }

    public boolean contains(Permission permission){
        return contains(permission.permissionVarString);
    }
    public boolean contains(String permissionVarString){
        return permissionList.get(permissionVarString) != null;
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
//        permissionList =  deserializer.readObj("permissionList",HashMap.class);

        var permissionListStrings = deserializer.readStringArray("permissionListStrings");
        for (var s : permissionListStrings){
//            assert ServerSingletons.puzzle.getPermission(s) != null;
            permissionList.putIfAbsent(s, ServerSingletons.puzzle.getPermission(s));
        }
    }


    @Override
    public void write(CRBinSerializer serializer) {
        String[] array = new String[permissionList.size()];
        var ks = permissionList.keySet().toArray(array);
        serializer.writeStringArray("permissionListStrings",ks,permissionList.size());

    }
}
