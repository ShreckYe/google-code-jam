rootProject.name = "google-code-jam-kotlin"

sealed class RoundTree {
    class Leaf(val name: String) : RoundTree()
    class Branch(val name: String, val children: List<RoundTree>) : RoundTree()
}

// TODO: GCJTree
fun RoundTree.toSubprojects(): List<String> = when (this) {
    is RoundTree.Leaf -> listOf(name)
    is RoundTree.Branch -> children.flatMap { it.toSubprojects().map { "${name}:$it" } }
}

fun List<RoundTree>.toSubprojects(): List<String> = flatMap { it.toSubprojects() }

val roundTrees = listOf(
    RoundTree.Branch(
        "2018",
        listOf(
            RoundTree.Branch(
                "practice-session",
                listOf(
                    RoundTree.Leaf("bathroom-stalls"),
                    RoundTree.Leaf("cruise-control"),
                    RoundTree.Leaf("number-guessing"),
                    RoundTree.Leaf("senate-evacuation"),
                ),
            ),
            RoundTree.Branch(
                "qualification-round",
                listOf(
                    RoundTree.Leaf("cubic-ufo"),
                    RoundTree.Leaf("go-gopher"),
                    RoundTree.Leaf("saving-the-universe-again"),
                    RoundTree.Leaf("trouble-sort"),
                ),
            ),
        ),
    ),
    RoundTree.Branch(
        "2019",
        listOf(
            RoundTree.Branch(
                "qualification-round",
                listOf(
                    RoundTree.Leaf("cryptopangrams"),
                    RoundTree.Leaf("foregone-solution"),
                    //RoundTree.Leaf("you-can-go-your-own-way") // done in Scala
                ),
            ),
            RoundTree.Branch(
                "round-1a",
                listOf(
                    RoundTree.Leaf("pylons"),
                ),
            ),
        ),
    ),
    RoundTree.Branch(
        "2020",
        listOf(
            RoundTree.Branch(
                "qualification-round",
                listOf(
                    RoundTree.Leaf("esab-atad"),
                    RoundTree.Leaf("indicium"),
                    RoundTree.Leaf("nesting-depth"),
                    RoundTree.Leaf("parenting-partnering-returns"),
                    RoundTree.Leaf("vestigium"),
                ),
            ),
            RoundTree.Branch(
                "round-1a",
                listOf(
                    RoundTree.Leaf("pascal-walk"),
                    RoundTree.Leaf("pattern-matching"),
                ),
            ),
            RoundTree.Branch(
                "round-1b",
                listOf(
                    RoundTree.Leaf("expogo"),
                    RoundTree.Leaf("join-the-ranks"),
                ),
            ),
            RoundTree.Branch(
                "round-1c",
                listOf(
                    RoundTree.Leaf("overexcited-fan"),
                    RoundTree.Leaf("overrandomized"),
                    RoundTree.Leaf("oversized-pancake-chopper"),
                ),
            ),
        ),
    ),
    RoundTree.Branch(
        "2021",
        listOf(
            RoundTree.Branch(
                "qualification-round",
                listOf(
                    RoundTree.Leaf("cheating-detection"),
                    RoundTree.Leaf("median-sort"),
                    RoundTree.Leaf("moons-and-umbrellas"),
                    RoundTree.Leaf("reversort"),
                    RoundTree.Leaf("reversort-engineering"),
                ),
            ),
            RoundTree.Branch(
                "round-1a",
                listOf(
                    RoundTree.Leaf("append-sort"),
                    RoundTree.Leaf("hacked-exam"),
                    RoundTree.Leaf("prime-time"),
                ),
            ),
            RoundTree.Branch(
                "round-1b",
                listOf(
                    RoundTree.Leaf("broken-clock"),
                    RoundTree.Leaf("subtransmutation"),
                ),
            ),
            RoundTree.Branch(
                "round-1c",
                listOf(
                    RoundTree.Leaf("closest-pick"),
                    RoundTree.Leaf("double-or-noting"),
                    RoundTree.Leaf("roaring-years"),
                ),
            ),
        ),
    ),
    RoundTree.Branch(
        "2022",
        listOf(
            RoundTree.Branch(
                "qualification-round",
                listOf(
                    RoundTree.Leaf("punched-cards"),
                    RoundTree.Leaf("3d-printing"),
                    RoundTree.Leaf("d1000000"),
                    RoundTree.Leaf("chain-reactions"),
                    RoundTree.Leaf("twisty-little-passages"),
                ),
            ),
            RoundTree.Branch(
                "round-1a",
                listOf(
                    RoundTree.Leaf("double-or-one-thing"),
                    RoundTree.Leaf("equal-sum"),
                    RoundTree.Leaf("weightlifting"),
                ),
            ),
            RoundTree.Branch(
                "round-1b",
                listOf(
                    RoundTree.Leaf("1"),
                    RoundTree.Leaf("2"),
                    RoundTree.Leaf("3"),
                ),
            ),
            RoundTree.Branch(
                "round-1c",
                listOf(
                    RoundTree.Leaf("1"),
                    RoundTree.Leaf("2"),
                    RoundTree.Leaf("3"),
                ),
            ),
        ),
    ),
    RoundTree.Branch(
        "2023",
        listOf(
            RoundTree.Branch(
                "round-a",
                listOf(
                    RoundTree.Leaf("1"),
                    RoundTree.Leaf("2"),
                    RoundTree.Leaf("3"),
                    RoundTree.Leaf("4"),
                    RoundTree.Leaf("5"),
                ),
            ),
            RoundTree.Branch(
                "round-b",
                listOf(
                    RoundTree.Leaf("1"),
                ),
            ),
            RoundTree.Branch(
                "round-c",
                listOf(
                    RoundTree.Leaf("1"),
                ),
            ),
            RoundTree.Branch(
                "round-d",
                listOf(
                    RoundTree.Leaf("1"),
                ),
            ),
        ),
    ),
)

include(roundTrees.toSubprojects())
include("templates-and-commons")
