package com.europa.sightup.presentation.screens.test.active

import org.jetbrains.compose.resources.DrawableResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.e_down
import sightupkmpapp.composeapp.generated.resources.e_left
import sightupkmpapp.composeapp.generated.resources.e_right
import sightupkmpapp.composeapp.generated.resources.e_up

enum class EChart(val resourceId: DrawableResource) {
    UP(Res.drawable.e_up),
    DOWN(Res.drawable.e_down),
    LEFT(Res.drawable.e_left),
    RIGHT(Res.drawable.e_right);

    companion object {
        private val chartRows = hashMapOf(
            Pair(
                1, hashMapOf(
                    UP to Res.drawable.e_up,
                    DOWN to Res.drawable.e_down,
                    LEFT to Res.drawable.e_left,
                    RIGHT to Res.drawable.e_right
                )
            ),
            Pair(
                2, hashMapOf(
                    UP to Res.drawable.e_up,
                    DOWN to Res.drawable.e_down,
                    LEFT to Res.drawable.e_left,
                    RIGHT to Res.drawable.e_right
                )
            ),
            Pair(
                3, hashMapOf(
                    UP to Res.drawable.e_up,
                    DOWN to Res.drawable.e_down,
                    LEFT to Res.drawable.e_left,
                    RIGHT to Res.drawable.e_right
                )
            ),
            Pair(
                4, hashMapOf(
                    UP to Res.drawable.e_up,
                    DOWN to Res.drawable.e_down,
                    LEFT to Res.drawable.e_left,
                    RIGHT to Res.drawable.e_right
                )
            ),
            Pair(
                5, hashMapOf(
                    UP to Res.drawable.e_up,
                    DOWN to Res.drawable.e_down,
                    LEFT to Res.drawable.e_left,
                    RIGHT to Res.drawable.e_right
                )
            )
        )

        private val rowScores = hashMapOf(
            1 to "20/200",
            2 to "20/100",
            3 to "20/70",
            4 to "20/50",
            5 to "20/40"
        )

        fun getRandomIcon(row: Int): DrawableResource? {
            val directions = chartRows[row]?.keys?.toList()
            val randomDirection = directions?.random()
            return randomDirection?.let { chartRows[row]?.get(it) }
        }

        fun getScoreForRow(row: Int): String? {
            return rowScores[row]
        }
    }
}

