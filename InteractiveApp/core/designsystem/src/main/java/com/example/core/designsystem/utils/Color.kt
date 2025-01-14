package com.example.core.designsystem.utils

import androidx.compose.ui.graphics.Color
import com.example.core.designsystem.utils.colorconstant.DARK_GRAY_COLOR
import com.example.core.designsystem.utils.colorconstant.DARK_INACTIVE_COLOR
import com.example.core.designsystem.utils.colorconstant.DARK_MAIN_BACKGROUND_COLOR
import com.example.core.designsystem.utils.colorconstant.DARK_MAIN_ICON_COLOR
import com.example.core.designsystem.utils.colorconstant.DARK_MAIN_TEXT_COLOR
import com.example.core.designsystem.utils.colorconstant.DARK_SECONDARY_COLOR
import com.example.core.designsystem.utils.colorconstant.LIGHT_GRAY_COLOR
import com.example.core.designsystem.utils.colorconstant.LIGHT_INACTIVE_COLOR
import com.example.core.designsystem.utils.colorconstant.LIGHT_MAIN_BACKGROUND_COLOR
import com.example.core.designsystem.utils.colorconstant.LIGHT_MAIN_ICON_COLOR
import com.example.core.designsystem.utils.colorconstant.LIGHT_MAIN_TEXT_COLOR
import com.example.core.designsystem.utils.colorconstant.LIGHT_SECONDARY_COLOR

// System Colors
data class GeneralColors(
    val mainBackgroundColor: Color = Color.Unspecified,
    val mainIconColor: Color = Color.Unspecified,
    val mainTextColor: Color = Color.Unspecified,
    val greyColor: Color = Color.Unspecified,
    val secondaryBackgroundColor: Color = Color.Unspecified,
    val inactiveColor: Color = Color.Unspecified,
) {
    companion object {
        fun forTheme(darkTheme: Boolean) = if (darkTheme) {
            GeneralColors(
                mainBackgroundColor = DARK_MAIN_BACKGROUND_COLOR,
                mainIconColor = DARK_MAIN_ICON_COLOR,
                mainTextColor = DARK_MAIN_TEXT_COLOR,
                greyColor = DARK_GRAY_COLOR,
                secondaryBackgroundColor =  DARK_SECONDARY_COLOR,
                inactiveColor = DARK_INACTIVE_COLOR
            )
        } else {
            GeneralColors(
                mainBackgroundColor = LIGHT_MAIN_BACKGROUND_COLOR,
                mainIconColor = LIGHT_MAIN_ICON_COLOR,
                mainTextColor = LIGHT_MAIN_TEXT_COLOR,
                greyColor = LIGHT_GRAY_COLOR,
                secondaryBackgroundColor = LIGHT_SECONDARY_COLOR,
                inactiveColor = LIGHT_INACTIVE_COLOR
            )
        }
    }
}
