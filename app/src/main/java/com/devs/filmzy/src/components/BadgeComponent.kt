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
import com.devs.filmzy.src.theme.fontStyle

@Composable
fun BadgeComponent (
    borderRadius: Dp = 10.dp,
    text: String = "",
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 5.dp, vertical = 3.dp),
    bgColor: Color = MaterialTheme.colorScheme.secondary,
    textSize: TextUnit = 11.sp,
    textColor: Color = MaterialTheme.colorScheme.primary,
    textStyle: TextStyle = TextStyle.Default,
    content: @Composable (() -> Unit)? = null,
) {
    Box (
        modifier = modifier
            .border(
                BorderStroke(width = 1.dp, color = bgColor),
                shape = RoundedCornerShape(borderRadius)
            )
            .clip(RoundedCornerShape(borderRadius))
            .background(bgColor)
    ) {
        Box (
            modifier = Modifier.padding(contentPadding)
        ) {
            if(text != "" && content == null) Text(text, style = fontStyle.textMulishBold(TextStyle(color = textColor, fontSize = textSize).merge(textStyle)))
            else if (content != null) content?.invoke()
        }
    }
}