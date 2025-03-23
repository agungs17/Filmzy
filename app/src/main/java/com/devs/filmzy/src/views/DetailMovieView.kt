package com.devs.filmzy.src.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devs.filmzy.R
import com.devs.filmzy.src.components.BadgeComponent
import com.devs.filmzy.src.components.ButtonComponent
import com.devs.filmzy.src.components.HeaderComponent
import com.devs.filmzy.src.components.IconComponent
import com.devs.filmzy.src.components.ImageComponent
import com.devs.filmzy.src.components.PersonCardHorizontalComponent
import com.devs.filmzy.src.models.MovieDetail.MovieDetail
import com.devs.filmzy.src.models.PersonList.StatePersonList
import com.devs.filmzy.src.services.ConfigApi
import com.devs.filmzy.src.theme.fontStyle
import com.devs.filmzy.src.utils.Constants
import com.devs.filmzy.src.utils.Constants.heightImageBackdropDetailMovie
import com.devs.filmzy.src.utils.getUriFromRes
import com.devs.filmzy.src.utils.roundFloat
import com.devs.filmzy.src.viewModels.movie.MovieDetailViewModel
import com.devs.filmzy.src.viewModels.person.PersonListViewModel
import com.google.accompanist.flowlayout.FlowRow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailMovieView() {
    val movieDetailViewModel: MovieDetailViewModel = hiltViewModel()
    val personListViewModel: PersonListViewModel = hiltViewModel()

    val movieDetailState by movieDetailViewModel.state.collectAsState()
    val personListState by personListViewModel.state.collectAsState()

    val isLoading = movieDetailState.loading
    val movieDetailResult = movieDetailState.results

    if(isLoading) {
        Loader()
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
            Column (
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                ImageComponent(
                    model = ConfigApi.BASE_URL_IMG + if(!movieDetailResult?.backdrop_path.isNullOrEmpty()) movieDetailResult?.backdrop_path else movieDetailResult?.poster_path,
                    modifier = Modifier.fillMaxWidth(),
                    height = heightImageBackdropDetailMovie
                )
                Box(
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.3f))
                ) {
                    Column (
                        modifier = Modifier.offset(y = (-60).dp)
                    ) {
                        if (movieDetailResult != null) HeaderDetailMovie(movieDetailResult)
                        Column(
                            modifier = Modifier.padding(top = if (movieDetailResult?.tagline?.isNotEmpty() == true) 28.dp else 5.dp)
                        ) {
                            if (movieDetailResult != null) ContentDetailDescriptionMovie(movieDetailResult)
                            ContentPersonList(personListState)
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun Loader () {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ImageComponent(
            model = getUriFromRes(R.raw.loading),
            background = Color.Transparent,
            height = 280.dp,
            width = 280.dp
        )
    }
}


@Composable
private fun HeaderDetailMovie (
    movieDetailResult : MovieDetail
) {
    Row(
        modifier = Modifier.padding(horizontal = 22.dp),
    ) {
        ImageComponent(
            model = ConfigApi.BASE_URL_IMG + movieDetailResult.poster_path,
            modifier = Modifier.shadow(3.dp, RoundedCornerShape(8.dp)),
            width = 140.dp,
            height = 200.dp,
            round = 8.dp
        )
        Column(
            modifier = Modifier.padding(top = 68.dp, start = 10.dp)
        ) {
            Text(
                movieDetailResult.title ?: "",
                style = fontStyle.textMulishBold(TextStyle(fontSize = 18.sp)),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                if (movieDetailResult.adult) "(Adult)" else "(General Audience)",
                modifier = Modifier.padding(top = 4.dp),
                style = fontStyle.textMulishSemiBold(
                    TextStyle(
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                )
            )
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                crossAxisSpacing = 4.dp
            ) {
                movieDetailResult.genres.let { genres ->
                    for (genre in genres) {
                        BadgeComponent(text = genre.name, modifier = Modifier.padding(end = 5.dp))
                    }
                }
            }
            Row(
                modifier = Modifier.padding(top = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconComponent(
                    imageVector = Icons.Filled.Star,
                    tint = Color(0xFFFFD700),
                    sizeIcon = 15.dp,
                    modifier = Modifier.padding(end = 3.dp)
                )
                Text(
                    "${roundFloat(movieDetailResult.vote_average.toFloat())}/10",
                    style = fontStyle.textMulish(TextStyle(color = MaterialTheme.colorScheme.onTertiary))
                )
            }
        }
    }
}

@Composable
private fun ContentDetailDescriptionMovie (
    movieDetailResult : MovieDetail
) {
    Column (
        modifier = Modifier.padding(horizontal = 15.dp)
    ) {
        if (movieDetailResult.tagline?.isNotEmpty() == true) {
            Text(
                "“${movieDetailResult.tagline}”",
                style = fontStyle.textMerriwatherBold(TextStyle(fontSize = 15.sp))
            )
        }

        Text(
            "Overview",
            style = fontStyle.textMerriwatherBold(TextStyle(fontSize = 15.sp)),
            modifier = Modifier.padding(top = 18.dp),
        )
        Text(
            "${movieDetailResult.overview}",
            style = fontStyle.textMulishSemiBold(
                TextStyle(
                    fontSize = 14.5.sp,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            ),
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}

@Composable
private fun ContentPersonList(
    personList: StatePersonList
) {
    val personListData = personList.cast + personList.crew
    val isSeeMore = personListData.size > Constants.configList.listHorizontal
    val personListShow = personListData.take(Constants.configList.listHorizontal)

    if(personListData.isNotEmpty() || personList.loading) {
        HeaderComponent(
            contentPadding = PaddingValues(start = 15.dp, end = 15.dp, bottom = 10.dp, top = 18.dp),
            contentLeft = {
                Text(
                    "Cast & Crew",
                    style = fontStyle.textMerriwatherBold(TextStyle(fontSize = 15.sp))
                )
            },
            contentRight = {
                ButtonComponent(
                    typeButton = "Outline",
                    text = "See more",
                    onClick = {

                    }
                )
            }
        )
    }
    LazyRow (
        contentPadding = PaddingValues(horizontal = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        itemsIndexed (
            if (!personList.loading) personListShow else List(Constants.configList.listPlaceholder) { null },
            key = {index, person -> person?.credit_id ?: index }
        ) { _, person ->
            PersonCardHorizontalComponent(person = person, isShimmer = personList.loading )
        }
        item {
            if (!personList.loading && isSeeMore) {
                Column(
                    modifier = Modifier
                        .height(150.dp)
                        .width(125.dp - 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier.clickable {

                        },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .size(35.dp)
                                .shadow(3.dp, shape = CircleShape)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                        ) {
                            IconComponent(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                tint = MaterialTheme.colorScheme.background,
                                modifier = Modifier
                                    .size(24.dp) // Ukuran ikon
                                    .align(Alignment.Center)
                            )
                        }
                        Text(
                            "See more",
                            style = fontStyle.textMulishBold(),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

//@Composable
//private fun ContentRecomendationList () {
//
//}