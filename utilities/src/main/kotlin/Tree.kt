interface Tree<T> {
    val data: T
    val children: List<Tree<T>>
}

fun <T> Tree<T>.isLeaf() = children.isEmpty()

interface MutableTree<T> : Tree<T> {
    fun addChild(child: Tree<T>)
}

data class MutableTreeImpl<T>(override val data: T, override var children: MutableList<Tree<T>>) : MutableTree<T> {
    override fun addChild(child: Tree<T>) {
        children.add(child)
    }
}

fun <T> tree(data: T, init: (MutableTreeImpl<T>.() -> Unit)? = null): MutableTreeImpl<T> {
    // The need to return an instance means we have to hard-code a choice of implementation here.
    val result = MutableTreeImpl(data, ArrayList())
    init?.invoke(result)
    return result
}

fun <T> MutableTreeImpl<T>.tree(data: T, init: (MutableTreeImpl<T>.() -> Unit)? = null) {
    val result = MutableTreeImpl(data, ArrayList())
    init?.invoke(result)
    addChild(result)
    return
}

fun main() {
    // Some mutability is required to use this pattern.
    val tree: Tree<String> = tree("Root") {
        tree("Leaf1")
        tree("Child1") {
            tree("Child2") {
                tree("Leaf2")
            }
            tree("Leaf3")
        }
        tree("Leaf4")
    }

    prettyPrintTree(tree)
//    Output:
//
//    Root
//    ├── Leaf1
//    ├── Child1
//    │   ├── Child2
//    │   │   └── Leaf2
//    │   └── Leaf3
//    └── Leaf4
}

fun <T> prettyPrintTree(tree: Tree<T>, prefix: String = "") {
    if (prefix.isEmpty()) println(tree.data) // Root!

    for (i in 0 until tree.children.count()) {
        val child = tree.children[i]

        if (i == tree.children.count() - 1) {
            println(prefix + "└── " + child.data)

            if (child.children.isNotEmpty()) {
                prettyPrintTree(tree = child, prefix = "$prefix    ")
            }
        } else {
            println(prefix + "├── " + child.data)

            if (child.children.isNotEmpty()) {
                prettyPrintTree(tree = child, prefix = "$prefix│   ")
            }
        }
    }
}
