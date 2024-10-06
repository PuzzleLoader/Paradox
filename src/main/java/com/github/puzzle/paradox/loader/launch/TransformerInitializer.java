package com.github.puzzle.paradox.loader.launch;

import com.github.puzzle.paradox.util.PuzzleEntrypointUtil;

public interface TransformerInitializer {
    String ENTRYPOINT_KEY = "transformers";

    void onTransformerInit(PuzzleClassLoader classLoader);

    static void invokeTransformers(PuzzleClassLoader classLoader) {
        PuzzleEntrypointUtil.invoke(ENTRYPOINT_KEY,
                                    TransformerInitializer.class,
                                    transformerInitializer -> {
                    transformerInitializer.onTransformerInit(classLoader);
                });
    }
}
