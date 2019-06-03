package ru.chudakov.syntax

import org.junit.Test

import org.junit.Assert.*
import ru.chudakov.lexical.Tokenizer
import ru.chudakov.lexical.token.Tag
import ru.chudakov.writer.XMLEncoder
import java.lang.Exception

class SyntaxAnalyzerTest {

    private val analyzer = SyntaxAnalyzer()

    private val tokenizer = Tokenizer()

    private val encoder = XMLEncoder("result.xml")

    private val expression = "var x, y\n" +
            "\n" +
            "begin\n" +
            "\tx:= y << 1\n" +
            "\ty:=3\n" +
            "\tif (x>0) y := 1\n" +
            "\telse\n" +
            "\t    begin\n" +
            "\t        y := 0\n" +
            "\t        x := 3\n" +
            "\t    end\n" +
            "end"

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

    @Test
    fun parse() {
        val tokens = tokenizer.getTokens(expression)

        val tree = analyzer.parse(tokens)

        assertTrue(tree != null)

        tree?.let { encoder.encode(it) }
    }
}
