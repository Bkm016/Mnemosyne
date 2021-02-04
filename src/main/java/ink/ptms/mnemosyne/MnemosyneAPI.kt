package ink.ptms.mnemosyne

import ink.ptms.mnemosyne.data.Address
import ink.ptms.mnemosyne.data.db.Database
import ink.ptms.mnemosyne.data.search.SearchQueue
import io.izzel.taboolib.kotlin.Tasks
import io.izzel.taboolib.module.config.TConfig
import io.izzel.taboolib.module.inject.TInject
import org.bukkit.entity.Player

object MnemosyneAPI {

    @TInject
    val conf: TConfig? = null

    fun search(): SearchQueue {
        return SearchQueue()
    }

    fun include(player: Player) {
        Tasks.task(true) {
            Database.insert(listOf(Address(player.name, player.address!!.hostName)))
        }
    }
}