package com.devs.filmzy.src.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devs.filmzy.src.models.MovieList.Movie
import com.devs.filmzy.src.services.ConfigApi
import com.devs.filmzy.src.theme.fontStyle
import com.devs.filmzy.src.utils.Constants
import com.devs.filmzy.src.utils.NavigationManager
import com.devs.filmzy.src.utils.roundFloat
import com.devs.filmzy.src.viewModels.misc.NavigateViewModel
import com.google.accompanist.flowlayout.FlowRow


fun toDetailMovie (
    navigation : NavigationManager,
    movieId : Int? = null
) {
    if(movieId != null) {
        navigation.navigate("DetailMovieView/$movieId")
    }
}


@Composable
fun MovieCardHorizontalComponent(
    modifier: Modifier = Modifier,
    navigation: NavigationManager = hiltViewModel<NavigateViewModel>().navigation,
    movie: Movie? = null,
    isShimmer: Boolean = false
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .run {
                if(isShimmer) this
                else clickable{
                    toDetailMovie(navigation, movie?.id)
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageUrl(
            url = ConfigApi.BASE_URL_IMG + movie?.poster_path,
            Constants.ConfigMovieCardHorizontalComponentImage.width,
            Constants.ConfigMovieCardHorizontalComponentImage.height,
            Constants.ConfigMovieCardHorizontalComponentImage.round,
            isShimmer
        )
        Column (
            modifier = Modifier.padding(start = 10.dp)
        ) {
            ShimmerComponent(
                isShimmer = isShimmer
            ) {
                Text(
                    movie?.title ?: "",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                    style = fontStyle.textMulishBold(TextStyle(fontSize = 15.sp))
                )
            }
            ShimmerComponent(
                isShimmer = isShimmer,
                paddingShimmer = PaddingValues(bottom = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                if(movie?.genre_ids != null) {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        crossAxisSpacing = 4.dp
                    ) {
                        movie.genre.let { genres ->
                            for (genreData in genres) {
                                BadgeComponent(
                                    text = genreData.name,
                                    modifier = Modifier.padding(end = 5.dp)
                                )
                            }
                        }
                    }
                }
            }
            ShimmerComponent(
                isShimmer = isShimmer
            ) {
                Text(
                    movie?.overview ?: "",
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                    style = fontStyle.textMulishSemiBold(TextStyle(fontSize = 13.sp, color = MaterialTheme.colorScheme.onSecondary))
                )
            }
            RatingMovie(
                rating = "${movie?.vote_average}",
                isShimmer
            )
        }
    }
}

@Composable
fun MovieCardVerticalComponent(
    modifier: Modifier = Modifier,
    navigation: NavigationManager = hiltViewModel<NavigateViewModel>().navigation,
    movie: Movie? = null,
    isShimmer: Boolean = false
) {
    Column (
        modifier = modifier
            .width(Constants.ConfigMovieCardVerticalComponentImage.width)
            .run {
                if(isShimmer) this
                else clickable{
                    toDetailMovie(navigation, movie?.id)
                }
            },
    ) {
        ImageUrl(
            url = ConfigApi.BASE_URL_IMG + movie?.poster_path,
            Constants.ConfigMovieCardVerticalComponentImage.width,
            Constants.ConfigMovieCardVerticalComponentImage.height,
            Constants.ConfigMovieCardVerticalComponentImage.round,
            isShimmer
        )
        ShimmerComponent(
            isShimmer,
            paddingShimmer = PaddingValues(top = 4.dp)
        ) {
            Text(
                movie?.title ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
                    .width(Constants.ConfigMovieCardVerticalComponentImage.width)
                    .padding(top = 4.dp),
                style = fontStyle.textMulishBold(TextStyle(fontSize = 15.sp))
            )
        }

        RatingMovie(
            rating = "${movie?.vote_average}",
            isShimmer
        )
    }
}

@Composable
fun RatingMovie (
    rating: String? = null,
    isShimmer: Boolean
) {
    ShimmerComponent(
        isShimmer = isShimmer,
        width = 50.dp,
        height = 20.dp,
        paddingShimmer = PaddingValues(top = 2.dp)
    ) {
        Row (
            modifier = Modifier.padding(top = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val safeRating = rating?.toFloatOrNull()
            if(safeRating != null) {
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

@Composable
fun ImageUrl(
    url: String = "",
    width: Dp,
    height: Dp,
    round: Dp,
    isShimmer: Boolean
) {
    ShimmerComponent(
        isShimmer = isShimmer,
        round = round
    ) {
        ImageComponent(
            model = url,
            height = height,
            width = width,
            round = round,
            modifier = Modifier
                .then(if (isShimmer) Modifier else Modifier.shadow(3.dp, RoundedCornerShape(round)))
        )
    }
}

