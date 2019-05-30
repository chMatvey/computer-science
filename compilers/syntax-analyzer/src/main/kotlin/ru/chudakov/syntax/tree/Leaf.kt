package ru.chudakov.syntax.tree

import ru.chudakov.lexical.token.Tag
import ru.chudakov.lexical.token.Token
import javax.xml.bind.annotation.*

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class Leaf(@field:XmlElement val token: Token, @XmlAttribute override val description: String) : Symbol {
    constructor() : this(Token(Tag.ERROR, "error"), "leaf")
}
