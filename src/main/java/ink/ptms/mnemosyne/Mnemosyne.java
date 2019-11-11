package ink.ptms.mnemosyne;

import io.izzel.taboolib.loader.Plugin;
import io.izzel.taboolib.module.config.TConfig;
import io.izzel.taboolib.module.inject.TInject;
import io.izzel.taboolib.module.locale.logger.TLogger;
import ink.ptms.mnemosyne.data.Database;
import ink.ptms.mnemosyne.data.InternalDatabase;

@Plugin.Version(5.11)
public final class Mnemosyne extends Plugin {

    @TInject
    public static final TConfig CONF = null;
    @TInject
    public static final TLogger LOGGER = null;
    public static final Database DATABASE = InternalDatabase.INSTANCE;
}
