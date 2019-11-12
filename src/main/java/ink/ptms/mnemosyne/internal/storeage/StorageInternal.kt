package ink.ptms.mnemosyne.internal.storeage

import io.izzel.taboolib.module.db.source.DBSource
import io.izzel.taboolib.module.db.sql.SQLHost
import io.izzel.taboolib.module.db.sql.SQLTable
import io.izzel.taboolib.module.inject.TSchedule
import ink.ptms.mnemosyne.Mnemosyne
import ink.ptms.mnemosyne.internal.data.Address
import javax.sql.DataSource

object StorageInternal : Storage() {

    private var host: SQLHost? = null
    private var table: SQLTable? = null
    private var dataSource: DataSource? = null

    @TSchedule
    fun active() {
        host = SQLHost(Mnemosyne.CONF.getConfigurationSection("Database"), Mnemosyne.getPlugin(), true)
        table = SQLTable(Mnemosyne.CONF.getString("Database.table")).column("\$id", "text:name", "text:ip", "text:date")
        try {
            dataSource = DBSource.create(host)
            table?.create(dataSource)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    override fun import(address: Address) {
        table!!.insert(null, address.name, address.ip, address.date).run(dataSource)
    }

    override fun import(address: List<Address>) {
        address.forEach { table!!.insert(null, it.name, it.ip, it.date).run(dataSource) }
    }

    override fun searchIP(ip: String): Address {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchIP(ip: String, date: Long): Address {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchName(name: String): Address {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchName(name: String, date: Long): Address {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}