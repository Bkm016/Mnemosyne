package ink.ptms.mnemosyne.data.parser

import ink.ptms.mnemosyne.data.Address
import ink.ptms.mnemosyne.data.parser.Parser

object ParserAuthMe : Parser() {

    override fun regex(): Regex = Regex("(.+?)(?<username>\\S+)\\[/(?<address>\\S+):(\\d+)] logged in(.+?)")

    override fun match(result: MatchResult): Address {
        val groupCollection = result.groups as MatchNamedGroupCollection
        return Address(groupCollection["username"]!!.value, groupCollection["address"]!!.value)
    }
}