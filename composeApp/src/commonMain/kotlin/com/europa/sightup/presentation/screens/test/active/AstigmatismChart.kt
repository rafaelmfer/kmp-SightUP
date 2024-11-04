package com.europa.sightup.presentation.screens.test.active

enum class AstigmatismChart(val angle: Int) {
    DIRECTION_1(15),
    DIRECTION_2(30),
    DIRECTION_3(45),
    DIRECTION_4(60),
    DIRECTION_5(75),
    DIRECTION_6(90),
    DIRECTION_7(105),
    DIRECTION_8(120),
    DIRECTION_9(135),
    DIRECTION_10(150),
    DIRECTION_11(165),
    DIRECTION_12(180);

    companion object {
        fun getAngleForDirection(direction: Int): String? {
            return values().firstOrNull { it.name == "DIRECTION_$direction" }?.angle.toString()
        }
    }
}
