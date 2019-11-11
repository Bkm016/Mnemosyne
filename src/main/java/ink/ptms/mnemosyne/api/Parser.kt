package ink.ptms.mnemosyne.api

import io.izzel.taboolib.util.Files
import io.izzel.taboolib.util.IO
import java.io.File
import java.nio.charset.StandardCharsets
import java.util.zip.ZipFile

object Parser {

    fun importFile(file: File): Any = importText(Files.readFromFile(file))

    fun importZip(fileZip: File): Any = ZipFile(fileZip).use { importText(IO.readFully(it.getInputStream(it.entries().nextElement()), StandardCharsets.UTF_8)) }

    fun importText(content: String): Any {
        return 0
    }
}