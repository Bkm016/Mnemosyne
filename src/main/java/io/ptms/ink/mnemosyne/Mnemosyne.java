package io.ptms.ink.mnemosyne;

import io.izzel.taboolib.loader.Plugin;
import io.izzel.taboolib.module.config.TConfig;
import io.izzel.taboolib.module.inject.TInject;
import io.izzel.taboolib.module.locale.logger.TLogger;
import io.ptms.ink.mnemosyne.data.Database;
import io.ptms.ink.mnemosyne.data.InternalDatabase;

@Plugin.Version(5.07)
public final class Mnemosyne extends Plugin {

    @TInject
    public static final TConfig CONF = null;
    @TInject
    public static final TLogger LOGGER = null;
    public static final Database DATABASE = InternalDatabase.INSTANCE;
}
