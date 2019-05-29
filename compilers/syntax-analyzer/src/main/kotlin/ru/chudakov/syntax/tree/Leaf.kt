package ru.chudakov.syntax.tree

import ru.chudakov.lexical.token.Token

class Leaf(val token: Token, override val description: String) : Symbol
