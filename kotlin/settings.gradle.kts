rootProject.name = "google-code-jam-kotlin"

sealed class RoundTree {
    class Leaf(val name: String) : RoundTree()
    class Branch(val name: String, val children: List<RoundTree>) : RoundTree()
}

fun RoundTree.toSubprojects(): List<String> = when (this) {
    is RoundTree.Leaf -> listOf(name)
    is RoundTree.Branch -> children.flatMap { it.toSubprojects().map { "${name}:$it" } }
}

fun List<RoundTree>.toSubprojects(): List<String> = flatMap { it.toSubprojects() }

val roundTrees = listOf(
    RoundTree.Branch(
        "2022", listOf(
            RoundTree.Branch(
                "qualification-round", listOf(
                    RoundTree.Leaf("test"),
                    RoundTree.Leaf("punched-cards"),
                    RoundTree.Leaf("3d-printing"),
                    RoundTree.Leaf("d1000000"),
                    RoundTree.Leaf("chain-reactions"),
                    RoundTree.Leaf("twisty-little-passages")
                )
            )
        )
    )
)

include(roundTrees.toSubprojects())
