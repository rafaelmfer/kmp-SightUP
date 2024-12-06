package com.europa.sightup.presentation.screens.test.active

enum class AstigmatismChart(val angle: Int) {
    DIRECTION_1(30),
    DIRECTION_2(60),
    DIRECTION_3(90),
    DIRECTION_4(120),
    DIRECTION_5(150),
    DIRECTION_6(180),
    DIRECTION_7(150),
    DIRECTION_8(120),
    DIRECTION_9(90),
    DIRECTION_10(60),
    DIRECTION_11(30),
    DIRECTION_12(180);

    companion object {
        fun getAngleForDirection(direction: Int): String? {
            return values().firstOrNull { it.name == "DIRECTION_$direction" }?.angle.toString()
        }
    }
}
