package ru.chudakov.lexer

import ru.chudakov.lexer.token.Error
import ru.chudakov.lexer.token.Tag
import ru.chudakov.lexer.token.Word

class LexemeTable {
    private val lexemes = mutableListOf<Lexeme>()

    private val error = "Ошибка"
    private val unaryError = "Неправильное использование унарной операции"
    private val binaryError = "Неправильное использование бинарной операции"
    private val assignmentError = "Ошибка присваивания"
    private val varError = "Объявление переменных д.б. в начале программы"

    private var beginCount = 0
    private var endCount = 0
    private var isLastBinaryOperation = false
    private var isLastUnaryOperation = false

    fun add(lexeme: Lexeme) {
        var item = lexeme

        if (lexeme.getValue() == "var" && lexemes.size != 0) {
            item = Lexeme(Error(lexeme.getValue(), varError), lexeme.line)
        } else if (lexeme.getValue() == "begin") {
            beginCount++
        } else if (lexeme.getValue() == "end") {
            endCount++
            if (endCount > beginCount) {
                item = Lexeme(Error(lexeme.getValue(), error), lexeme.line)
            }
        }

        when {
            isLastUnaryOperation -> {
                if (!(lexeme.getTag() == Tag.ID || lexeme.getTag() == Tag.UNARY_OPERATION
                                || lexeme.getTag() == Tag.INT_NUM || lexeme.getTag() == Tag.DOUBLE_NUM)) {
                    val last = lexemes.removeAt(lexemes.size - 1)
                    lexemes.add(Lexeme(Error(last.getValue(), unaryError), last.line))
                }
            }
            isLastBinaryOperation -> {
                if (lexeme.getTag() != Tag.INT_NUM && lexeme.getTag() != Tag.DOUBLE_NUM && lexeme.getTag() != Tag.ID) {
                    val last = lexemes.removeAt(lexemes.size - 1)
                    lexemes.add(Lexeme(Error(last.getValue(), binaryError), last.line))
                }
            }
            lexeme.getValue() == ":=" -> {
                if (lexemes.last().getTag() != Tag.ID) {
                    item = Lexeme(Error(lexeme.getValue(), assignmentError), lexeme.line)
                }
            }
            lexeme.getTag() == Tag.BINARY_OPERATION -> {
                if (lexemes.last().getTag() != Tag.ID && lexemes.last().getTag() != Tag.DOUBLE_NUM &&
                        lexemes.last().getTag() != Tag.INT_NUM) {
                    item = Lexeme(Error(lexeme.getValue(), binaryError), lexeme.line)
                }
            }
        }


        when (item.getTag()) {
            Tag.BINARY_OPERATION -> isLastBinaryOperation = true
            Tag.UNARY_OPERATION -> isLastUnaryOperation = true
            else -> {
                isLastBinaryOperation = false
                isLastUnaryOperation = false
            }
        }

        lexemes.add(item)
    }

    fun add(list: List<Lexeme>) {
        list.forEach { add(it) }
    }

    fun getLexemes(): List<Lexeme> {
        if (beginCount > endCount) {
            var count = beginCount - endCount
            while (count != 0) {
                val index = lexemes.lastIndexOf(Lexeme(Word(Tag.KEY, "begin"), 1))
                val lexeme = lexemes.removeAt(index)
                lexemes.add(index, Lexeme(Error(lexeme.getValue(), error), lexeme.line))
                count--
            }
        }
        return lexemes
    }
}
