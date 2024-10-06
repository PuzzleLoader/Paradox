package com.github.puzzle.paradox.loader.providers.api;


import com.github.puzzle.paradox.loader.Version;
import com.github.puzzle.paradox.loader.launch.PuzzleClassLoader;

public interface IGameProvider {
    // Game Names
    String getId();
    String getName();

    // Game Version
    Version getGameVersion();
    String getRawVersion();

    // Extra Data
    String getEntrypoint();

    // Inits
    void registerTransformers(PuzzleClassLoader classLoader);
    void inject(PuzzleClassLoader classLoader);

    void addBuiltinMods();
}
