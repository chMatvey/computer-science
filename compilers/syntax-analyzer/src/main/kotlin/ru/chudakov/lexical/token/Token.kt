package ru.chudakov.lexical.token

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class Token(@field:XmlElement val name: Tag, @field:XmlElement val attribute: String?) {

    constructor() : this(Tag.ERROR, "error")

    override fun toString(): String {
        return "Token: $name; attribute: $attribute"
    }
}
