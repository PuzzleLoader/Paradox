package com.github.puzzle.paradox.loader.entrypoint.interfaces;


import com.github.puzzle.paradox.util.PuzzleEntrypointUtil;

public interface PluginInitializer {
    String ENTRYPOINT_KEY = "init";

    void onInit();

    static void invokeEntrypoint() {
        PuzzleEntrypointUtil.invoke(ENTRYPOINT_KEY, PluginInitializer.class, PluginInitializer::onInit);
    }
}
