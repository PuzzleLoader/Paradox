package com.github.puzzle.paradox.api.item;

import finalforeach.cosmicreach.accounts.Account;
import finalforeach.cosmicreach.items.Item;

public class ParadoxItem {
    Item item;
    public ParadoxItem(Item item){
        this.item = item;
    }
    /**
     * This the item's name as a String
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @see String
     */
    public String getName(){
        return this.item.getName();
    }

    /**
     * This the item's internal id as a String
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @see String
     */
    public String getID(){
        return this.item.getID();
    }

    /**
     * Avoid using this. Returns Cosmic Reach's internal item class
     * @author repletsin5
     * @since API 1.0.0-Alpha
     * @see Item
     */
    public Item getInternalItem() {
        return item;
    }
}
