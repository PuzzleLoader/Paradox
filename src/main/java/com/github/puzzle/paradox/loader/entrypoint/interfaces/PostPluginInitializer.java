package com.github.puzzle.paradox.loader.entrypoint.interfaces;


import com.github.puzzle.paradox.util.PuzzleEntrypointUtil;

public interface PostPluginInitializer {
    String ENTRYPOINT_KEY = "postInit";

    void onPostInit();

    static void invokeEntrypoint() {
        PuzzleEntrypointUtil.invoke(ENTRYPOINT_KEY, PostPluginInitializer.class, PostPluginInitializer::onPostInit);
    }

}
