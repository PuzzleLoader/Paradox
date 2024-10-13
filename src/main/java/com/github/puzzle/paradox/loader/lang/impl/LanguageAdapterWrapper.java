package com.github.puzzle.paradox.loader.lang.impl;


import com.github.puzzle.paradox.loader.lang.LanguageAdapter;
import com.github.puzzle.paradox.loader.lang.LanguageAdapterException;
import com.github.puzzle.paradox.loader.plugin.info.PluginInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LanguageAdapterWrapper implements LanguageAdapter {

    Object clazz;

    public LanguageAdapterWrapper(Object clazz) {
        LOGGER.info("Wrapping Adapter Class \"" + clazz.getClass().getName() + "\"");
        this.clazz = clazz;
    }

    @Override
    public <T> T create(PluginInfo info, String value, Class<T> type) throws LanguageAdapterException {
        Method create = null;
        try {
            create = clazz.getClass().getDeclaredMethod("create", PluginInfo.class, String.class, Class.class);
        } catch (NoSuchMethodException e) {
            throw new LanguageAdapterException(e);
        }
        try {
            return (T) create.invoke(clazz, info, value, type);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new LanguageAdapterException(e);
        }
    }
}
