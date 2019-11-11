package ink.ptms.mnemosyne;

import ink.ptms.mnemosyne.api.InternalAPI;
import ink.ptms.mnemosyne.api.MnemosyneAPI;
import io.izzel.taboolib.loader.Plugin;
import io.izzel.taboolib.module.config.TConfig;
import io.izzel.taboolib.module.inject.TInject;
import io.izzel.taboolib.module.locale.logger.TLogger;
import ink.ptms.mnemosyne.internal.storeage.Storage;
import ink.ptms.mnemosyne.internal.storeage.StorageInternal;

@Plugin.Version(5.11)
public final class Mnemosyne extends Plugin {

    @TInject
    public static final TConfig CONF = null;
    @TInject
    public static final TLogger LOGGER = null;
    public static final Storage STORAGE = StorageInternal.INSTANCE;
    public static final MnemosyneAPI API = InternalAPI.INSTANCE;

}
