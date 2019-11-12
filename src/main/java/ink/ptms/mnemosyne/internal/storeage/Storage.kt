package ink.ptms.mnemosyne.internal.storeage

import ink.ptms.mnemosyne.internal.data.Address

/**
 * @Author sky
 * @Since 2019-11-11 22:18
 */
abstract class Storage {

    abstract fun import(address: Address)

    abstract fun import(address: List<Address>)

    abstract fun searchIP(ip: String): Address

    abstract fun searchIP(ip: String, date: Long): Address

    abstract fun searchName(name: String): Address

    abstract fun searchName(name: String, date: Long): Address
}