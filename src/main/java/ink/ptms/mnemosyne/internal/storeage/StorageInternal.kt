package ink.ptms.mnemosyne.internal.storeage

import io.izzel.taboolib.module.db.source.DBSource
import io.izzel.taboolib.module.db.sql.SQLHost
import io.izzel.taboolib.module.db.sql.SQLTable
import io.izzel.taboolib.module.inject.TSchedule
import ink.ptms.mnemosyne.Mnemosyne
import javax.sql.DataSource

object StorageInternal : Storage() {

    private var host: SQLHost? = null
    private var table: SQLTable? = null
    private var dataSource: DataSource? = null

    @TSchedule
    fun active() {
        host = SQLHost(Mnemosyne.CONF.getConfigurationSection("Database"), Mnemosyne.getPlugin(), true)
        table = SQLTable(Mnemosyne.CONF.getString("Database.table")).column("\$id", "text:name", "text:data")
        try {
            dataSource = DBSource.create(host)
            table?.create(dataSource)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }
}