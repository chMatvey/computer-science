package ru.chudakov.lexical.token

import javax.xml.bind.annotation.XmlEnum
import javax.xml.bind.annotation.XmlType

@XmlType(name = "tag")
@XmlEnum
enum class Tag {
    ID,
    NUMBER,
    WORD,
    OPERATION,
    UNARY_OPERATION,
    SEPARATOR,
    VAR,
    IF,
    ELSE,
    ERROR,
    PROGRAM
}
