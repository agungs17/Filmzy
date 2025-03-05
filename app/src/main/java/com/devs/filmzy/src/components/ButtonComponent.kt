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
import com.devs.filmzy.src.theme.fontStyle

fun buttonConfig (typeButton : String, buttonColor: Color, textColor: Color) : ButtonConfig {
    return when (typeButton) {
        "Solid" -> {
            ButtonConfig(
                borderButtonColor = buttonColor,
                bgButtonColor = buttonColor,
                textButtonColor = textColor
            )
        }
        "Outline" -> {
            ButtonConfig(
                borderButtonColor = buttonColor,
                bgButtonColor = Color.Transparent,
                textButtonColor = buttonColor
            )
        }
        else -> {
//          Disabled
            ButtonConfig(
                borderButtonColor = Color.LightGray,
                bgButtonColor = Color.LightGray,
                textButtonColor = Color.Gray
            )
        }
    }
}

@Composable
fun ButtonComponent (
    typeButton: String = "Outline",
    text: String = "",
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 5.dp, vertical = 3.dp),
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    textSize: TextUnit = 12.5.sp,
    textColor: Color = Color.White,
    textStyle: TextStyle = TextStyle.Default,
    borderRadius: Dp = 10.dp,
    disabled : Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null
) {
    val typeButtonStatus = if(disabled) "Disabled" else typeButton
    val buttonConfig = buttonConfig(typeButtonStatus, buttonColor, textColor)

    Box (
        modifier = modifier
            .border(
                BorderStroke(width = 1.dp, color = buttonConfig.borderButtonColor),
                shape = RoundedCornerShape(borderRadius)
            )
            .clip(RoundedCornerShape(borderRadius))
            .background(buttonConfig.bgButtonColor)
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
            if(text != "" && content == null) Text(text, style = fontStyle.textMulishBold(TextStyle(color = buttonConfig.textButtonColor, fontSize = textSize).merge(textStyle)))
            else if (content != null) content?.invoke()
        }
    }
}

data class ButtonConfig (
    val borderButtonColor: Color,
    val bgButtonColor: Color,
    val textButtonColor: Color
)
