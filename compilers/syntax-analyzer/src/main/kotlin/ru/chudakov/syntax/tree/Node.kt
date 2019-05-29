package ru.chudakov.syntax.tree

class Node(override val description: String) : Symbol {
    val nodes: MutableList<Symbol> = mutableListOf()
}
