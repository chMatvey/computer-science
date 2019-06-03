package ru.chudakov.lexical

import org.junit.Test

class TokenizerTest {
    private val tokenizer = Tokenizer()

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
    fun getTokens() {
        tokenizer.getTokens(expression).forEach {
            println(it)
        }
    }
}
