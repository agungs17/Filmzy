package com.devs.filmzy.src.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconComponent(
    imageVector: ImageVector,
    contentDescription: String = "DefaultIcon",
    tint : Color = MaterialTheme.colorScheme.onPrimary,
    sizeIcon: Dp = 28.dp,
    modifierContainer: Modifier = Modifier,
    modifierContent: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifierContainer
            .clip(CircleShape)
            .run {
                if(onClick == null) this
                else {
                    clickable {
                        onClick?.invoke()
                    }
                }
            }

    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint,
            modifier = modifierContent.size(sizeIcon)
        )
    }
}