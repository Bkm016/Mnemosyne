package ink.ptms.mnemosyne.listener

import ink.ptms.mnemosyne.data.Address
import ink.ptms.mnemosyne.data.db.Database
import ink.ptms.mnemosyne.data.parser.ParserHandle
import io.izzel.taboolib.loader.Plugin
import io.izzel.taboolib.module.inject.TListener
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.io.File

/**
 * @Author sky
 * @Since 2020-03-15 16:54
 */
@TListener
class ListenerPlayer : Listener {

    @EventHandler
    fun e(e: PlayerJoinEvent) {
        Bukkit.getScheduler().runTaskAsynchronously(Plugin.getPlugin(), Runnable {
            Database.insert(listOf(Address(e.player.name, e.player.address!!.hostName)))
        })
    }
}