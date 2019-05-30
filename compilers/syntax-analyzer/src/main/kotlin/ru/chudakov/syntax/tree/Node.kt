package ru.chudakov.syntax.tree

import javax.xml.bind.annotation.*

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class Node(@XmlAttribute override val description: String) : Symbol {
    constructor() : this("node")

    @XmlAnyElement
    val nodes: MutableList<Symbol> = mutableListOf()
}
