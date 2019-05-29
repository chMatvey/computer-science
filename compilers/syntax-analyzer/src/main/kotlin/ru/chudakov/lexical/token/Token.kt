package ru.chudakov.lexical.token

data class Token(
        val name: Tag,
        val attribute: String?
) {
    override fun toString(): String {
        return "Token: $name; attribute: $attribute"
    }
}
