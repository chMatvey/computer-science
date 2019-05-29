package ru.chudakov

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RegexTest {

    @Test
    fun variable() {
        val regex = Regex("^[a-zA-Z]+\\w*$")

        assertTrue { regex.matches("q_") }
    }

    @Test
    fun number() {
        val regex = Regex("[0-9]+")

        assertTrue { regex.matches("10") }
    }

    @Test
    fun operation() {
        val regex = Regex("(\\+|-|\\*|/|<|>|<<|>>|:=)")

        assertTrue { regex.matches("+") }
        assertTrue { regex.matches("*") }
        assertTrue { regex.matches("-") }
        assertTrue { regex.matches("/") }
        assertTrue { regex.matches("<") }
        assertTrue { regex.matches("<") }
        assertTrue { regex.matches("<<") }
        assertTrue { regex.matches(">>") }
        assertTrue { regex.matches(":=") }
        assertFalse { regex.matches("><<") }
    }

    @Test
    fun separator() {
        val regex = Regex("(begin|end|\\(|\\)|,)")

        assertTrue { regex.matches("begin") }
        assertTrue { regex.matches("end") }
        assertTrue { regex.matches("(") }
        assertTrue { regex.matches(")") }
        assertTrue { regex.matches(",") }
    }

    @Test
    fun varRegex() {
        val regex = Regex("if(\\s|\t|\n|\\()")

        assertTrue { regex.matches("if ") }
        assertTrue { regex.matches("if(") }
        assertTrue { regex.matches("if\n") }
        assertTrue { regex.matches("if\t") }
    }

    @Test
    fun elseRegex() {
        val regex = Regex("else(\\s|\t|\n)")

        assertTrue { regex.matches("else ") }
        assertTrue { regex.matches("else\n") }
        assertTrue { regex.matches("else\t") }
    }
}
