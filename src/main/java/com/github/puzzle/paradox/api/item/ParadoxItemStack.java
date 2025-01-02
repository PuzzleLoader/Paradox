package com.github.puzzle.paradox.api.item;

import finalforeach.cosmicreach.items.ItemStack;

public class ParadoxItemStack {

    ItemStack stack;
    ParadoxItem item;

    public ParadoxItemStack(ItemStack stack){
        this.stack = stack;
        this.item = new ParadoxItem(stack.getItem());
    }

    public int getItemCount(){
       return this.stack.amount;
    }

    public ParadoxItem getItem(){
        return item;
    }
}
