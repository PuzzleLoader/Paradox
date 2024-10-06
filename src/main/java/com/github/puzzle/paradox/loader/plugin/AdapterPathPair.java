package com.github.puzzle.paradox.loader.plugin;

public class AdapterPathPair {

    String adapter;
    String value;

    public AdapterPathPair(String adapter, String value) {
        this.adapter = adapter;
        this.value = value;
    }

    public String getAdapter() {
        return adapter;
    }

    public String getValue() {
        return value;
    }
}
