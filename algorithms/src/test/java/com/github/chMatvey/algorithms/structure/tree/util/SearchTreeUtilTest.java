package com.github.chMatvey.algorithms.structure.tree.util;

import com.github.chMatvey.algorithms.structure.tree.BinarySearchTree;
import org.junit.jupiter.api.Test;

import static com.github.chMatvey.algorithms.structure.tree.BinarySearchTreeImplTest.createBinarySearchTree;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchTreeUtilTest {

    @Test
    void isBinarySearchThree() {
        BinarySearchTree<String, Integer> binarySearchTree = createBinarySearchTree();

        assertTrue(BinarySearchTreeUtil.isBinarySearchThree(binarySearchTree));
    }
}