package ink.ptms.mnemosyne.api

import com.google.common.collect.Lists
import ink.ptms.mnemosyne.internal.data.Address
import io.izzel.taboolib.util.Files
import io.izzel.taboolib.util.IO
import org.bukkit.util.NumberConversions
import java.io.File
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.zip.ZipFile
import kotlin.streams.toList
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.zip.GZIPInputStream



object Parser {

    fun import(file: File): List<Address> {
        return when {
            file.name.endsWith("log.gz") -> importZip(file)
            file.name.endsWith("log") -> {
                val address = Lists.newArrayList<Address>()
                Files.read(file) { it ->
                    address.addAll(it.lines().map { Address.parse(it, System.currentTimeMillis()) }.filter(Objects::nonNull).toList())
                }
                address
            }
            else -> emptyList()
        }
    }

    fun importZip(fileZip: File): List<Address> {
        FileInputStream(fileZip).use { fileInputStream ->
            GZIPInputStream(fileInputStream).use { zip ->
                val date = parseDate(fileZip)
                val address = Lists.newArrayList<Address>()
                val scanner = Scanner(zip)
                while (scanner.hasNextLine()) {
                    address.addAll(scanner.nextLine().split("\r").map { Address.parse(it, date) }.filter(Objects::nonNull).toList())
                }
                return address
            }
        }
    }

    fun parseDate(file: File): Long {
        if (file.name.endsWith("log.gz")) {
            val args = file.name.split("-")
            val cal = Calendar.getInstance()
            cal.set(Calendar.YEAR, NumberConversions.toInt(args[0]))
            cal.set(Calendar.MONTH, NumberConversions.toInt(args[1]))
            cal.set(Calendar.DAY_OF_MONTH, NumberConversions.toInt(args[2]))
            return cal.timeInMillis
        }
        return System.currentTimeMillis()
    }
}