package ru.chudakov.syntax

import org.junit.Test

import org.junit.Assert.*
import ru.chudakov.lexical.Tokenizer
import ru.chudakov.writer.XMLEncoder

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
            "\telse y := 0\n" +
            "end\n"

    @Test
    fun parse() {
        val tokens = tokenizer.getTokens(expression)

        val tree = analyzer.parse(tokens)

        assertTrue(tree != null)

        tree?.let { encoder.encode(it) }
    }
}
