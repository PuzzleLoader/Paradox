package com.github.puzzle.paradox.core.permissions;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.ICRBinSerializable;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

public class PlayerPermissions implements ICRBinSerializable {
    String playerID;
    List<String> groupNames;

    //should these be prioritised over group perms?
    List<String> individualPerms;

    @Override
    public void read(CRBinDeserializer deserializer) {
       playerID =  deserializer.readString("playerID");
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
