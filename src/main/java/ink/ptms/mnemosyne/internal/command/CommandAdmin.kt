package ink.ptms.mnemosyne.internal.command

import com.google.common.collect.Lists
import ink.ptms.mnemosyne.Mnemosyne
import ink.ptms.mnemosyne.internal.data.Address
import io.izzel.taboolib.module.command.base.BaseCommand
import io.izzel.taboolib.module.command.base.BaseMainCommand
import io.izzel.taboolib.module.command.base.SubCommand
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import java.io.File

/**
 * @Author sky
 * @Since 2019-11-12 10:29
 */
@BaseCommand(name = "MnemosyneAdmin", permission = "*")
class CommandAdmin : BaseMainCommand() {

    @SubCommand(description = "从本地导入数据")
    fun import(sender: CommandSender, args: Array<String>) {
        Bukkit.getScheduler().runTaskAsynchronously(Mnemosyne.getPlugin(), Runnable {
            sender.sendMessage("§c[Mnemosyne] §7数据收集...")
            val date = System.currentTimeMillis()
            val address = Lists.newArrayList<Address>()
            for (logFile in File("logs").listFiles()) {
                sender.sendMessage("§c[Mnemosyne] §8  ${logFile.name}")
                address.addAll(Mnemosyne.API.getParser().import(logFile))
            }
            sender.sendMessage("§c[Mnemosyne] §7开始导入...")
            try {
                Mnemosyne.STORAGE.import(address)
            } catch (t: Throwable) {
                t.printStackTrace()
            }
            sender.sendMessage("§c[Mnemosyne] §7导入完成, 耗时 ${System.currentTimeMillis() - date}ms")
        })
    }
}