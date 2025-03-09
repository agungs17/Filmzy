package com.devs.filmzy.src.components

import android.net.Uri
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest

@Composable
fun ImageComponent (
    model: Any,
    modifier: Modifier = Modifier,
    background: Color = Color.LightGray,
    contentDescription: String = "Image Component",
    contentScale: ContentScale = ContentScale.Crop,
    height: Dp = 50.dp,
    width: Dp = 50.dp,
    round: Dp = 0.dp
) {
    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(round)),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(model)
                .apply {
                    if (model is Uri || (model is String && model.lowercase().endsWith(".gif"))) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            decoderFactory(ImageDecoderDecoder.Factory())
                        } else {
                            decoderFactory(GifDecoder.Factory())
                        }
                    }
                }
                .memoryCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.ENABLED)
                .crossfade(false)
                .build(),
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize().background(background),
            contentScale = contentScale
        )
    }
}
