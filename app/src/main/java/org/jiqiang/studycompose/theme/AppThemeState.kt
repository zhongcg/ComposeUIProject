package org.jiqiang.studycompose.theme

import com.guru.composecookbook.theme.ColorPallet

data class AppThemeState(
    var darkTheme: Boolean = false,
    var pallet: ColorPallet = ColorPallet.GREEN
)