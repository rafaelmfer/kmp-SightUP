package com.europa.sightup.presentation.designsystem.components.data

enum class BottomSheetEnum {
    SHOW, HIDE;

    companion object {
        fun BottomSheetEnum.toggle(): BottomSheetEnum {
            return if (this == SHOW) {
                HIDE
            } else {
                SHOW
            }
        }
    }
}