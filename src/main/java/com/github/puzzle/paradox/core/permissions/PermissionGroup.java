package com.github.puzzle.paradox.core.permissions;

import finalforeach.cosmicreach.networking.server.ServerSingletons;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionGroup {
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

    Map<String,Permission> permissionList = new HashMap<>();

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
}
