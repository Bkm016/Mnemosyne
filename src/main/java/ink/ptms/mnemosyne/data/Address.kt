package ink.ptms.mnemosyne.data

/**
 * @Author sky
 * @Since 2020-03-10 14:13
 */
class Address(val username: String? = null, val address: String? = null, var date: Long = System.currentTimeMillis()) {

    fun isSimilar(other: Address): Boolean {
        return if (username != null && other.username != null && other.username == username) {
            other.address != address
        } else if (address != null && other.address != null) {
            other.address == address
        } else {
            false
        }
    }

    override fun toString(): String {
        return "Address(username='$username', address='$address')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Address) return false
        if (username != other.username) return false
        if (address != other.address) return false
        return true
    }

    override fun hashCode(): Int {
        var result = username?.hashCode() ?: 0
        result = 31 * result + (address?.hashCode() ?: 0)
        return result
    }
}