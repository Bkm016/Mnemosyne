package ink.ptms.mnemosyne.data.search

import ink.ptms.mnemosyne.data.Address
import java.util.concurrent.TimeUnit

/**
 * @Author sky
 * @Since 2020-03-10 20:19
 */
class SearchQueue {

    var target: Address? = null
    var source: List<Address>? = null

    var time = TimeUnit.DAYS.toMillis(7)

    var block = ArrayList<Address>()
    val result = ArrayList<Address>()

    fun target(target: Address): SearchQueue {
        this.target = target;
        return this
    }

    fun source(source: List<Address>): SearchQueue {
        this.source = source;
        return this
    }

    fun block(vararg block: Address): SearchQueue {
        this.block.addAll(block)
        return this
    }

    fun time(time: Long): SearchQueue {
        this.time = time;
        return this
    }

    fun run(): Set<String> {
        return run(target!!)
    }

    fun run(target: Address): Set<String> {
        if (block.none { it.isSimilar(target) }) {
            result.add(target)
            source!!.filter { target.isSimilar(it) && !result.contains(it) && System.currentTimeMillis() - it.date < time }.forEach { run(it) }
        }
        return result.mapNotNull { it.username }.sorted().toSet()
    }
}