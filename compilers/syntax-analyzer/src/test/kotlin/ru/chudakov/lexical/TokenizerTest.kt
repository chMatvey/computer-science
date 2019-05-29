package ru.chudakov.lexical

import org.junit.Test
import ru.chudakov.lexical.token.Tag
import java.lang.Exception

class TokenizerTest {
    private val tokenizer = Tokenizer()

    private val expression = "var x, y\n" +
            "\n" +
            "begin\n" +
            "\tx:= y << 1\n" +
            "\ty:=3\n" +
            "\tif (x>0) y := 1\n" +
            "\telse y := 0\n" +
            "end\n"

    @Test
    fun getTokens() {
        tokenizer.getTokens(expression).forEach {
            println(it)
        }
    }

    @Test
    fun computingTokens() {
        val tokens = tokenizer.getTokens(expression)

        tokens.takeLastWhile { it.attribute != "begin" }.forEach { println(it) }
    }

    @Test
    fun variablesTokens() {
        val tokens = tokenizer.getTokens(expression)

        tokens.takeLastWhile { it.name != Tag.VAR }.takeWhile { it.attribute != "begin" }.forEach { println(it) }
    }

    @Test
    fun program() {
        val tokens = tokenizer.getTokens(expression)

        val varToken = tokens.firstOrNull { it.name == Tag.VAR } ?: throw Exception()
        val beginToken = tokens.firstOrNull() { it.attribute == "begin" } ?: throw Exception()
        val endToken = tokens.lastOrNull { it.attribute == "end" } ?: throw Exception()

        tokens.subList(tokens.indexOf(varToken), tokens.indexOf(beginToken)).forEach { println(it) }
        println()
        tokens.subList(tokens.indexOf(beginToken), tokens.lastIndexOf(endToken) + 1).forEach { println(it) }
    }
}
