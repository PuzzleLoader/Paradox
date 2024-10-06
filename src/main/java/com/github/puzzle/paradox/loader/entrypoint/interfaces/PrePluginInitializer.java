package com.github.puzzle.paradox.loader.entrypoint.interfaces;

import com.github.puzzle.paradox.util.PuzzleEntrypointUtil;

public interface PrePluginInitializer {
    String ENTRYPOINT_KEY = "preInit";

    void onPreInit();

    static void invokeEntrypoint() {
        PuzzleEntrypointUtil.invoke(ENTRYPOINT_KEY, PrePluginInitializer.class, PrePluginInitializer::onPreInit);
    }
}
