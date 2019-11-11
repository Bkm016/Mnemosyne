package ink.ptms.mnemosyne.internal.data

import org.bukkit.util.NumberConversions
import java.util.*

/**
 * @Author sky
 * @Since 2019-11-11 22:42
 */
data class Address(val date: Long, val name: String, val ip: String) {

    companion object {

        val regex = Regex("\\[(?<hours>\\d+):(?<minutes>\\d+):(?<seconds>\\d+)] \\[.+]: (?<name>\\S+)\\[/(?<ip>\\S+):\\d+]")

        fun parse(source: String, date: Long): Address? {
            val result = regex.find(source) ?: return null
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, NumberConversions.toInt(result.groups["hours"]!!.value))
            cal.set(Calendar.MINUTE, NumberConversions.toInt(result.groups["minutes"]!!.value))
            cal.set(Calendar.SECOND, NumberConversions.toInt(result.groups["seconds"]!!.value))
            return Address(cal.timeInMillis, result.groups["name"]!!.value, result.groups["ip"]!!.value)
        }
    }
}