package com.devs.filmzy.src.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devs.filmzy.R
import com.devs.filmzy.src.components.BadgeComponent
import com.devs.filmzy.src.components.HeaderComponent
import com.devs.filmzy.src.components.IconComponent
import com.devs.filmzy.src.components.ImageComponent
import com.devs.filmzy.src.services.ConfigApi
import com.devs.filmzy.src.theme.fontStyle
import com.devs.filmzy.src.utils.Constants.heightImageBackdropDetailMovie
import com.devs.filmzy.src.utils.getUriFromRes
import com.devs.filmzy.src.utils.roundFloat
import com.devs.filmzy.src.viewModels.movie.MovieDetailViewModel
import com.google.accompanist.flowlayout.FlowRow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailMovieView() {
    val movieDetailViewModel: MovieDetailViewModel = hiltViewModel()
    val detailState by movieDetailViewModel.state.collectAsState()
    val isLoading = detailState.loading
    val detailResult = detailState.results

    if(isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ImageComponent(
                model = getUriFromRes(R.raw.loading),
                background = Color.Transparent,
                height = 300.dp,
                width = 300.dp
            )
        }
    } else {
        Scaffold(
            topBar = {
                HeaderComponent(
                    bgColor = Color.Transparent,
                    contentPadding = PaddingValues(top = 40.dp, bottom = 10.dp, start = 15.dp, end = 15.dp),
                    useBack = true,
                )
            },
        ) { _ ->
            ImageComponent(
                model = ConfigApi.BASE_URL_IMG + detailResult?.backdrop_path,
                modifier = Modifier.fillMaxWidth(),
                height = heightImageBackdropDetailMovie
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.3f))
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 15.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(top = heightImageBackdropDetailMovie - 50.dp, start = 10.dp, end = 10.dp),
                    ) {
                        ImageComponent(
                            model = ConfigApi.BASE_URL_IMG + detailResult?.poster_path,
                            modifier = Modifier.shadow(3.dp, RoundedCornerShape(8.dp)),
                            width = 140.dp,
                            height = 200.dp,
                            round = 8.dp
                        )
                        Column(
                            modifier = Modifier.padding(top = 58.dp, start = 10.dp)
                        ) {
                            Text(
                                detailResult?.title ?: "",
                                style = fontStyle.textMulishBold(TextStyle(fontSize = 18.sp)),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                            )
                            if (detailResult?.adult != null) {
                                Text(
                                    if (detailResult.adult) "(Adult)" else "(General Audience)",
                                    modifier = Modifier.padding(top = 4.dp),
                                    style = fontStyle.textMulishSemiBold(
                                        TextStyle(
                                            fontSize = 13.sp,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    )
                                )
                            }
                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                crossAxisSpacing = 4.dp
                            ) {
                                detailResult?.genres?.let { genres ->
                                    for (genre in genres) {
                                        BadgeComponent(text = genre.name, modifier = Modifier.padding(end = 5.dp))
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier.padding(top = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val safeRating = detailResult?.vote_average?.toFloat()
                                if (safeRating != null) {
                                    IconComponent(
                                        imageVector = Icons.Filled.Star,
                                        tint = Color(0xFFFFD700),
                                        sizeIcon = 15.dp,
                                        modifier = Modifier.padding(end = 3.dp)
                                    )
                                    Text(
                                        "${roundFloat(safeRating)}/10",
                                        style = fontStyle.textMulish(TextStyle(color = MaterialTheme.colorScheme.onTertiary))
                                    )
                                }
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.padding(top = if (detailResult?.tagline?.isNotEmpty() == true) 28.dp else 5.dp)
                    ) {

                        if (detailResult?.tagline?.isNotEmpty() == true) {
                            Text(
                                "“${detailResult.tagline}”",
                                style = fontStyle.textMerriwatherBold(TextStyle(fontSize = 15.sp))
                            )
                        }

                        Text(
                            "Overview",
                            style = fontStyle.textMerriwatherBold(TextStyle(fontSize = 15.sp)),
                            modifier = Modifier.padding(top = 18.dp),
                        )
                        Text(
                            "${detailResult?.overview}",
                            style = fontStyle.textMulishSemiBold(
                                TextStyle(
                                    fontSize = 14.5.sp,
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                            ),
                            modifier = Modifier.padding(top = 6.dp)
                        )

//                    Text(
//                        "Cast",
//                        style = fontStyle.textMerriwatherBold(TextStyle(fontSize = 15.sp)),
//                        modifier = Modifier.padding(top = 12.dp)
//                    )

                    }
                }
            }
        }
    }
}