package com.devs.filmzy.src.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmerComponent(
    isShimmer: Boolean,
    modifier: Modifier = Modifier,
    round: Dp = 0.dp,
    width: Dp = Dp.Unspecified,
    height: Dp = Dp.Unspecified,
    paddingShimmer: PaddingValues = PaddingValues(0.dp),
    content: @Composable (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier.padding(paddingShimmer)
    ) {
        content?.invoke() // Menampilkan konten utama dulu

        if (isShimmer) {
            Box(
                modifier = modifier
                    .shimmer()
                    .clip(RoundedCornerShape(round))
                    .background(Color.Gray)
                    .then(
                        if (width != Dp.Unspecified && height != Dp.Unspecified) {
                            Modifier.width(width).height(height)
                        } else {
                            Modifier.matchParentSize()
                        }
                    )
            )
        }
    }
}
