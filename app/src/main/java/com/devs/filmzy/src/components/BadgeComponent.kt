package com.devs.filmzy.src.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.devs.filmzy.src.models.misc.StyleConfig
import com.devs.filmzy.src.theme.fontStyle
import com.devs.filmzy.src.utils.styleConfig

@Composable
fun BadgeComponent (
    modifier: Modifier = Modifier,
    typeBadge: String = "Solid", // "Solid,Outline,Custom"
    borderRadius: Dp = 10.dp,
    text: String = "",
    contentPadding: PaddingValues = PaddingValues(horizontal = 5.dp, vertical = 3.dp),
    badgeColor: Color = MaterialTheme.colorScheme.secondary,
    badgeBorderColor: Color = Color.Unspecified,
    textSize: TextUnit = 11.sp,
    textColor: Color = MaterialTheme.colorScheme.primary,
    textStyle: TextStyle = TextStyle.Default,
    content: @Composable (() -> Unit)? = null,
) {
    val badgeConfig = if(typeBadge == "Custom")  StyleConfig(if(badgeBorderColor != Color.Unspecified) badgeBorderColor else badgeColor, badgeColor, textColor)  else styleConfig(typeBadge, badgeColor, textColor)

    Box (
        modifier = modifier
            .border(
                BorderStroke(width = 1.dp, color = badgeConfig.borderColor),
                shape = RoundedCornerShape(borderRadius)
            )
            .clip(RoundedCornerShape(borderRadius))
            .background(badgeConfig.bgColor)
    ) {
        Box (
            modifier = Modifier.padding(contentPadding)
        ) {
            if(text != "" && content == null) Text(text, style = fontStyle.textMulishBold(TextStyle(color = badgeConfig.textColor, fontSize = textSize).merge(textStyle)))
            else content?.invoke()
        }
    }
}