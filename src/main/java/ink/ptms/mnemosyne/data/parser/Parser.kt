package ink.ptms.mnemosyne.data.parser

import ink.ptms.mnemosyne.data.Address
import java.util.regex.Pattern

/**
 * @Author sky
 * @Since 2020-03-10 14:12
 */
abstract class Parser {

    abstract fun regex(): Regex

    abstract fun match(result: MatchResult): Address

}
