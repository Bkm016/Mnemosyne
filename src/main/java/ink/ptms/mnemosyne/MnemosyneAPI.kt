package ink.ptms.mnemosyne

import ink.ptms.mnemosyne.data.Address
import ink.ptms.mnemosyne.data.search.SearchQueue
import io.izzel.taboolib.module.config.TConfig
import io.izzel.taboolib.module.inject.TInject
import java.util.concurrent.TimeUnit

object MnemosyneAPI {

    @TInject
    val conf: TConfig? = null

    fun search(): SearchQueue {
        return SearchQueue()
    }
}