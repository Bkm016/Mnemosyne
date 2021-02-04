package ink.ptms.mnemosyne.data.db

import ink.ptms.mnemosyne.Mnemosyne
import ink.ptms.mnemosyne.MnemosyneAPI
import ink.ptms.mnemosyne.data.Address
import io.izzel.taboolib.loader.Plugin
import io.izzel.taboolib.module.db.source.DBSource
import io.izzel.taboolib.module.db.sql.*
import io.izzel.taboolib.module.db.sql.query.Where
import io.izzel.taboolib.module.inject.TFunction
import javax.sql.DataSource

/**
 * @Author sky
 * @Since 2019-12-21 15:28
 */
object Database {

    val host = SQLHost(MnemosyneAPI.conf!!.getConfigurationSection("Database"), Mnemosyne.plugin, true)
    val table = SQLTable(MnemosyneAPI.conf!!.getString("Database.table"))
            .column("\$id")
            .column(SQLColumn(SQLColumnType.VARCHAR, 36, "username").columnOptions(SQLColumnOption.KEY))
            .column(SQLColumn(SQLColumnType.VARCHAR, 36, "address"))
            .column(SQLColumn(SQLColumnType.BIGINT, "date"))!!

    val dataSource: DataSource = DBSource.create(host)

    @TFunction.Init
    fun init() {
        try {
            table.create(dataSource)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    fun insert(address: List<Address>) {
        address.forEach { table.insert(null, it.username, it.address, it.date).run(dataSource) }
    }

    fun select(time: Long): List<Address> {
        return ArrayList<Address>().run {
            table.select(Where.moreEqual("date", System.currentTimeMillis() - time)).to(dataSource).resultAutoNext { this.add(Address(it.getString("username"), it.getString("address"), it.getLong("date"))) }.run()
            this
        }
    }
}