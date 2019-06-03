package ru.chudakov.lexical

import ru.chudakov.lexical.token.Tag
import ru.chudakov.lexical.token.Token

class Tokenizer {
    private val finiteAutomates: MutableMap<Tag, (str: String) -> AutomateState> = mutableMapOf()

    private val isSeparator = Regex("(begin|end|\\(|\\)|,)")
    private val isId = Regex("[a-zA-Z]+\\w*")
    private val isNumber = Regex("[0-9]+")
    private val isWord = Regex("\\w*")
    private val isOperation = Regex("(\\+|-|\\*|/|<|>|<<|>>|:=)")
    private val isIf = Regex("if(\\s|\t|\n|\\()")
    private val isElse = Regex("else(\\s|\t|\n)")

    init {
        finiteAutomates[Tag.SEPARATOR] = { str: String ->
            when {
                isSeparator.matches(str) -> AutomateState.FINAL
                "end".startsWith(str) -> AutomateState.ACTIVE
                "begin".startsWith(str) -> AutomateState.ACTIVE
                else -> AutomateState.ERROR
            }
        }
        finiteAutomates[Tag.VAR] = { str: String ->
            when {
                str == "var " -> AutomateState.FINAL
                "var".startsWith(str) -> AutomateState.ACTIVE
                else -> AutomateState.ERROR
            }
        }
        finiteAutomates[Tag.IF] = { str: String ->
            when {
                isIf.matches(str) -> AutomateState.FINAL
                "if".startsWith(str) -> AutomateState.ACTIVE
                else -> AutomateState.ERROR
            }
        }
        finiteAutomates[Tag.ELSE] = { str: String ->
            when {
                isElse.matches(str) -> AutomateState.FINAL
                "else".startsWith(str) -> AutomateState.ACTIVE
                else -> AutomateState.ERROR
            }
        }
        finiteAutomates[Tag.ID] = { str: String ->
            when {
                isId.matches(str) -> AutomateState.FINAL
                else -> AutomateState.ERROR
            }
        }
        finiteAutomates[Tag.NUMBER] = { str: String ->
            when {
                isNumber.matches(str) -> AutomateState.FINAL
                else -> AutomateState.ERROR
            }
        }
        finiteAutomates[Tag.WORD] = { str: String ->
            when {
                isWord.matches(str) -> AutomateState.FINAL
                else -> AutomateState.ERROR
            }
        }
        finiteAutomates[Tag.OPERATION] = { str: String ->
            when {
                isOperation.matches(str) -> AutomateState.FINAL
                str == ":" -> AutomateState.ACTIVE
                else -> AutomateState.ERROR
            }
        }
    }

    fun getTokens(expression: String): List<Token> {
        val result = mutableListOf<Token>()
        val chars = expression.toCharArray()

        var index = 0
        var isCorrectToken = false

        while (index < chars.size) {
            for (it in finiteAutomates) {
                while (index < chars.size && chars[index].isSpace()) {
                    index++
                }

                if (index == chars.size) break

                var str: String = chars[index].toString()

                var lastState = AutomateState.ERROR
                var thisState = it.value(str)

                while (thisState != AutomateState.ERROR) {
                    lastState = thisState
                    index++
                    if (index == chars.size) break
                    str += chars[index]
                    thisState = it.value(str)
                }

                if (lastState == AutomateState.FINAL) {
                    val attribute = if (index < chars.size) str.substring(0, str.length - 1) else str
                    result.add(Token(it.key, attribute))
                    isCorrectToken = true
                    break
                } else {
                    index -= (str.length - 1)
                }
            }

            if (!isCorrectToken) {
                var str = ""
                while (index < chars.size && !chars[index].isSpace()) {
                    str += chars[index]
                    index++
                }
                result.add(Token(Tag.ERROR, str))
            }

            isCorrectToken = false
        }

        return result
    }

    private fun Char.isSpace(): Boolean = this == ' ' || this == '\t' || this == '\n'
}
