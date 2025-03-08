package com.devs.filmzy.src.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devs.filmzy.src.models.misc.StyleConfig
import com.devs.filmzy.src.theme.fontStyle
import com.devs.filmzy.src.utils.styleConfig

@Composable
fun ButtonComponent (
    modifier: Modifier = Modifier,
    typeButton: String = "Solid", // "Solid,Outline,Custom"
    text: String = "",
    contentPadding: PaddingValues = PaddingValues(horizontal = 5.dp, vertical = 3.dp),
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    buttonBorderColor: Color = Color.Unspecified,
    textSize: TextUnit = 12.5.sp,
    textColor: Color = Color.White,
    textStyle: TextStyle = TextStyle.Default,
    borderRadius: Dp = 10.dp,
    disabled : Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null
) {
    val isButtonCustom = typeButton == "Custom"
    val typeButtonStatus = if(disabled) "Disabled" else typeButton
    val buttonConfig = if(isButtonCustom && !disabled) StyleConfig(if(buttonBorderColor != Color.Unspecified) buttonBorderColor else buttonColor, buttonColor, textColor) else styleConfig(typeButtonStatus, buttonColor, textColor)

    Box (
        modifier = modifier
            .border(
                BorderStroke(width = 1.dp, color = buttonConfig.borderColor),
                shape = RoundedCornerShape(borderRadius)
            )
            .clip(RoundedCornerShape(borderRadius))
            .background(buttonConfig.bgColor)
            .run {
                if(disabled) {
                    this
                } else {
                    clickable {
                        onClick?.invoke()
                    }
                }
            }
    ) {
        Box (
            modifier = Modifier.padding(contentPadding)
        ) {
            if(text != "" && content == null) Text(text, style = fontStyle.textMulishBold(TextStyle(color = buttonConfig.textColor, fontSize = textSize).merge(textStyle)))
            else content?.invoke()
        }
    }
}
