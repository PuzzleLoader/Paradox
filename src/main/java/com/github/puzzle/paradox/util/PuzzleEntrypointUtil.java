package com.github.puzzle.paradox.util;

import com.github.puzzle.paradox.loader.plugin.PluginLocator;

import java.util.function.Consumer;

public class PuzzleEntrypointUtil {
    public static <T> void invoke(String key, Class<T> entrypointType, Consumer<? super T> entrypointInvoker) {
        if (PluginLocator.locatedPlugins == null) PluginLocator.getPlugins();
        PluginLocator.locatedPlugins.values().forEach(modContainer -> {
            try {
                modContainer.invokeEntrypoint(key, entrypointType, entrypointInvoker);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}