package ink.ptms.mnemosyne.command

import ink.ptms.mnemosyne.Mnemosyne
import ink.ptms.mnemosyne.MnemosyneAPI
import ink.ptms.mnemosyne.data.Address
import ink.ptms.mnemosyne.data.db.Database
import ink.ptms.mnemosyne.data.parser.ParserHandle
import io.izzel.taboolib.loader.Plugin
import io.izzel.taboolib.module.command.base.BaseCommand
import io.izzel.taboolib.module.command.base.BaseMainCommand
import io.izzel.taboolib.module.command.base.SubCommand
import io.izzel.taboolib.module.inject.TInject
import io.izzel.taboolib.util.lite.cooldown.Cooldown
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.util.NumberConversions
import java.io.File
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@BaseCommand(name = "Mnemosyne", aliases = ["me"], permission = "*")
class CommandHandle : BaseMainCommand() {

    @SubCommand(description = "导入日志")
    fun importLogs(sender: CommandSender, args: Array<String>) {
        notify(sender, "正在导入...")
        Bukkit.getScheduler().runTaskAsynchronously(Mnemosyne.plugin, Runnable {
            val time = System.currentTimeMillis();
            Arrays.stream(File("logs").listFiles()).forEachOrdered { file ->
                notify(sender, "  &8${file.name}")
                Database.insert(ParserHandle.import(file).toList())
            }
            notify(sender, "导入完成. (${System.currentTimeMillis() - time}ms)")
        })
    }

    @SubCommand(description = "搜索用户", arguments = ["用户/地址", "时间?", "阻断?"])
    fun searchUser(sender: CommandSender, args: Array<String>) {
        val address = if (args[0].contains(".")) {
            notify(sender, "正在搜索地址 &f${args[0]}")
            Address("*", args[0])
        } else {
            notify(sender, "正在搜索用户 &f${args[0]}")
            Address(args[0], "*")
        }
        val time = if (args.size > 1) {
            notify(sender, "时间 &f${NumberConversions.toLong(args[1])}天 &7内")
            TimeUnit.DAYS.toMillis(NumberConversions.toLong(args[1]))
        } else {
            notify(sender, "时间 &f7天 &7内")
            TimeUnit.DAYS.toMillis(7)
        }
        val block = if (args.size > 2) {
            args[2].split("[,;|]".toRegex()).map {
                if (it.contains(".")) {
                    Address(null, it)
                } else {
                    Address(it, null)
                }
            }.toTypedArray()
        } else {
            emptyArray()
        }
        if (block.isNotEmpty()) {
            notify(sender, "阻断")
            block.forEach {
                notify(sender, "  - &8$it")
            }
        }
        Bukkit.getScheduler().runTaskAsynchronously(Mnemosyne.plugin, Runnable {
            notify(sender, "查询结果")
            MnemosyneAPI.search().source(Database.select(time)).target(address).block(*block).time(time).run().forEach {
                notify(sender, "  - &8$it")
            }
        })
    }

    fun notify(sender: CommandSender, value: String) {
        sender.sendMessage("§c[Mnemosyne] §7${value.replace("&", "§")}")
        if (sender is Player && !Global.cd.isCooldown(sender.name)) {
            sender.playSound(sender.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 2f)
        }
    }

    object Global {

        @TInject
        val cd = Cooldown("command.sound", 50)
    }
}