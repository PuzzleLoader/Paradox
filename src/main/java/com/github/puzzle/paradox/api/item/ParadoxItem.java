package com.github.puzzle.paradox.api.item;

import finalforeach.cosmicreach.items.Item;

public class ParadoxItem {
    Item item;
    public ParadoxItem(Item item){
        this.item = item;
    }

    public String getName(){
        return this.item.getName();
    }
    public String getID(){
        return this.item.getID();
    }

    public Item getInternalItem() {
        return item;
    }
}
