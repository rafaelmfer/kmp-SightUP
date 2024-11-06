package com.europa.sightup.presentation.screens.test.active

import org.jetbrains.compose.resources.DrawableResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.eight_e_down
import sightupkmpapp.composeapp.generated.resources.eight_e_left
import sightupkmpapp.composeapp.generated.resources.eight_e_right
import sightupkmpapp.composeapp.generated.resources.eight_e_up
import sightupkmpapp.composeapp.generated.resources.five_e_down
import sightupkmpapp.composeapp.generated.resources.five_e_left
import sightupkmpapp.composeapp.generated.resources.five_e_right
import sightupkmpapp.composeapp.generated.resources.five_e_up
import sightupkmpapp.composeapp.generated.resources.four_e_down
import sightupkmpapp.composeapp.generated.resources.four_e_left
import sightupkmpapp.composeapp.generated.resources.four_e_right
import sightupkmpapp.composeapp.generated.resources.four_e_up
import sightupkmpapp.composeapp.generated.resources.one_e_down
import sightupkmpapp.composeapp.generated.resources.one_e_left
import sightupkmpapp.composeapp.generated.resources.one_e_right
import sightupkmpapp.composeapp.generated.resources.one_e_up
import sightupkmpapp.composeapp.generated.resources.seven_e_down
import sightupkmpapp.composeapp.generated.resources.seven_e_left
import sightupkmpapp.composeapp.generated.resources.seven_e_right
import sightupkmpapp.composeapp.generated.resources.seven_e_up
import sightupkmpapp.composeapp.generated.resources.six_e_down
import sightupkmpapp.composeapp.generated.resources.six_e_left
import sightupkmpapp.composeapp.generated.resources.six_e_right
import sightupkmpapp.composeapp.generated.resources.six_e_up
import sightupkmpapp.composeapp.generated.resources.three_e_down
import sightupkmpapp.composeapp.generated.resources.three_e_left
import sightupkmpapp.composeapp.generated.resources.three_e_right
import sightupkmpapp.composeapp.generated.resources.three_e_up
import sightupkmpapp.composeapp.generated.resources.two_e_down
import sightupkmpapp.composeapp.generated.resources.two_e_left
import sightupkmpapp.composeapp.generated.resources.two_e_right
import sightupkmpapp.composeapp.generated.resources.two_e_up

enum class EChart {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    companion object {
        private val chartRows = hashMapOf(
            1 to listOf(
                EChartIcon(UP, Res.drawable.one_e_up),
                EChartIcon(DOWN, Res.drawable.one_e_down),
                EChartIcon(LEFT, Res.drawable.one_e_left),
                EChartIcon(RIGHT, Res.drawable.one_e_right)
            ),
            2 to listOf(
                EChartIcon(UP, Res.drawable.two_e_up),
                EChartIcon(DOWN, Res.drawable.two_e_down),
                EChartIcon(LEFT, Res.drawable.two_e_left),
                EChartIcon(RIGHT, Res.drawable.two_e_right)
            ),
            3 to listOf(
                EChartIcon(UP, Res.drawable.three_e_up),
                EChartIcon(DOWN, Res.drawable.three_e_down),
                EChartIcon(LEFT, Res.drawable.three_e_left),
                EChartIcon(RIGHT, Res.drawable.three_e_right)
            ),
            4 to listOf(
                EChartIcon(UP, Res.drawable.four_e_up),
                EChartIcon(DOWN, Res.drawable.four_e_down),
                EChartIcon(LEFT, Res.drawable.four_e_left),
                EChartIcon(RIGHT, Res.drawable.four_e_right)
            ),
            5 to listOf(
                EChartIcon(UP, Res.drawable.five_e_up),
                EChartIcon(DOWN, Res.drawable.five_e_down),
                EChartIcon(LEFT, Res.drawable.five_e_left),
                EChartIcon(RIGHT, Res.drawable.five_e_right)
            ),
            6 to listOf(
                EChartIcon(UP, Res.drawable.six_e_up),
                EChartIcon(DOWN, Res.drawable.six_e_down),
                EChartIcon(LEFT, Res.drawable.six_e_left),
                EChartIcon(RIGHT, Res.drawable.six_e_right)
            ),
            7 to listOf(
                EChartIcon(UP, Res.drawable.seven_e_up),
                EChartIcon(DOWN, Res.drawable.seven_e_down),
                EChartIcon(LEFT, Res.drawable.seven_e_left),
                EChartIcon(RIGHT, Res.drawable.seven_e_right)
            ),
            8 to listOf(
                EChartIcon(UP, Res.drawable.eight_e_up),
                EChartIcon(DOWN, Res.drawable.eight_e_down),
                EChartIcon(LEFT, Res.drawable.eight_e_left),
                EChartIcon(RIGHT, Res.drawable.eight_e_right)
            )
        )

        private val rowScores = hashMapOf(
            1 to "20/200",
            2 to "20/100",
            3 to "20/70",
            4 to "20/50",
            5 to "20/30",
            6 to "20/20",
            7 to "20/15",
            8 to "20/10"
        )

        fun getRandomIcon(row: Int, lastDirection: EChart? = null): EChartIcon? {
            val directions = chartRows[row]?.toMutableList() ?: return null

            // Remove from the list the last direction
            lastDirection?.let {
                directions.removeAll { icon -> icon.direction == lastDirection }
            }
            return directions.randomOrNull()
        }

        fun getScoreForRow(row: Int): String? {
            return rowScores[row]
        }

        fun getRowForScore(score: String): Int {
            return rowScores.entries.find { it.value == score }!!.key
        }
    }
}

data class EChartIcon(val direction: EChart, val resourceId: DrawableResource)
