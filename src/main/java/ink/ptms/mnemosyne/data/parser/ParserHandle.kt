package ink.ptms.mnemosyne.data.parser

import ink.ptms.mnemosyne.data.Address
import io.izzel.taboolib.util.Files
import java.io.File
import java.io.FileInputStream
import java.util.zip.GZIPInputStream
import kotlin.streams.toList

object ParserHandle {

    val parsers = arrayOf(ParserAuthMe)

    fun import(file: File): Set<Address> {
        file.lastModified()
        val import = HashSet<Address>()
        if (file.name.endsWith(".log")) {
            Files.read(file) {
                import.addAll(importText(it.lines().toList()))
            }
        }
        if (file.name.endsWith(".log.gz")) {
            FileInputStream(file).use { fileInputStream ->
                GZIPInputStream(fileInputStream).use { gzipInputStream ->
                    Files.read(gzipInputStream) { r ->
                        import.addAll(importText(r.lines().toList()))
                    }
                }
            }
        }
        val date = file.lastModified()
        import.forEach { it.date = date }
        return import
    }

    fun importText(text: List<String>): Set<Address> {
        val import = HashSet<Address>()
        text.forEach { line ->
            parsers.forEach { parser ->
                ParserAuthMe.regex().find(line)?.run {
                    import.add(ParserAuthMe.match(this))
                }
            }
        }
        return import
    }
}