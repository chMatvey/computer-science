package com.github.chMatvey.algorithms.structure.tree.util;

import com.github.chMatvey.algorithms.structure.tree.BinaryNode;
import com.github.chMatvey.algorithms.structure.tree.BinarySearchTree;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BinarySearchTreeUtil {
    public static <Key extends Comparable<Key>, Value> boolean isBinarySearchThree(BinarySearchTree<Key, Value> tree) {
        return isBinarySearchThree(tree.getRoot(), tree.min(), tree.max());
    }

    private static <Key extends Comparable<Key>, Value> boolean isBinarySearchThree(BinaryNode<Key, Value> node, Key min, Key max) {
        if (node == null)
            return true;

        if (node.getKey().compareTo(min) < 0 || node.getKey().compareTo(max) > 0)
            return false;

        return isBinarySearchThree(node.getLeft(), min, node.getKey()) &&
                isBinarySearchThree(node.getRight(), node.getKey(), max);
    }
}
