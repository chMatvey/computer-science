package ru.chudakov.writer

import ru.chudakov.syntax.tree.Leaf
import ru.chudakov.syntax.tree.Node
import ru.chudakov.syntax.tree.Symbol
import java.io.File
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

class XMLEncoder(private val fileName: String) {

    private val context: JAXBContext = JAXBContext.newInstance(
            Node::class.java,
            Leaf::class.java
    )
    private val marshaller: Marshaller
    private val file: File

    init {
        marshaller = context.createMarshaller()
        val path = File("").absolutePath
        file = File(path, fileName);
    }

    fun encode(tree: Symbol) {
        marshaller.marshal(tree, file)
    }
}
