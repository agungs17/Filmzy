package com.devs.filmzy.src.components.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.devs.filmzy.src.components.atoms.ButtonComponent
import com.devs.filmzy.src.components.atoms.HeaderComponent
import com.devs.filmzy.src.components.atoms.IconComponent
import com.devs.filmzy.src.components.molecules.MovieCardHorizontalComponent
import com.devs.filmzy.src.components.molecules.MovieCardVerticalComponent
import com.devs.filmzy.src.models.MovieList.Movie
import com.devs.filmzy.src.models.MovieList.MovieListParams
import com.devs.filmzy.src.models.MovieList.StateMovieList
import com.devs.filmzy.src.theme.fontStyle
import com.devs.filmzy.src.utils.Constants
import com.devs.filmzy.src.viewModels.movie.MovieListPagingViewModel
import com.devs.filmzy.src.viewModels.movie.MovieListViewModel

@Composable
fun MovieListComponent(
    movieParams: MovieListParams,
    state: LazyListState = rememberLazyListState(),
    listHeaderComponent: @Composable () -> Unit = {},
    title: String = "Movies",
    isVertical: Boolean = false,
    isPaging: Boolean = false,
    minimumList: Int = Constants.configList.listHorizontal,
    listFooterComponent: @Composable () -> Unit = {},
) {
    var isFetched by rememberSaveable { mutableStateOf(false) }

    val movieListPagingViewModel: MovieListPagingViewModel? = if (isPaging) hiltViewModel() else null
    val movieListViewModel: MovieListViewModel? = if (!isPaging) hiltViewModel() else null

    val moviePagingState: LazyPagingItems<Movie>? = if (isPaging) movieListPagingViewModel?.state?.collectAsLazyPagingItems() else null
    var movieState: StateMovieList? = if (!isPaging) movieListViewModel?.state?.collectAsState()?.value else null

    val loading: Boolean
    var isSeeMore = false

    if (isPaging) {
        loading = moviePagingState?.loadState?.refresh !is LoadState.Loading
    } else {
        loading = movieState?.loading == true
        isSeeMore = movieState?.results?.size!! > minimumList
        movieState = movieState.let {
            if (it.results.size > minimumList) {
                it.copy(results = it.results.take(minimumList))
            } else it
        }
    }

    LaunchedEffect(Unit) {
        if (!isFetched) {
            if (isPaging) movieListPagingViewModel?.updateParams(movieParams)
            else movieListViewModel?.fetch(movieParams)
            isFetched = true
        }
    }

    if (isVertical) {
        VerticalLayout(
            listHeaderComponent = listHeaderComponent,
            title = title,
            loading = loading,
            isSeeMore = isSeeMore,
            state = state,
            listFooterComponent = listFooterComponent,
            nonPagingData = movieState,
            pagingData = moviePagingState,
            isPaging = isPaging
        )
    } else {
        HorizontalLayout(
            listHeaderComponent = listHeaderComponent,
            title = title,
            loading = loading,
            isSeeMore = isSeeMore,
            state = state,
            listFooterComponent = listFooterComponent,
            nonPagingData = movieState,
            pagingData = moviePagingState,
            isPaging = isPaging
        )
    }
}

@Composable
fun HorizontalLayout(
    listHeaderComponent: @Composable () -> Unit = {},
    title: String,
    nonPagingData: StateMovieList?,
    pagingData: LazyPagingItems<Movie>?,
    state: LazyListState,
    listFooterComponent: @Composable () -> Unit = {},
    loading: Boolean,
    isSeeMore: Boolean,
    isPaging: Boolean
) {
    Column {
        HeaderComponent(
            contentPadding = PaddingValues(15.dp),
            contentLeft = {
                Text(
                    title,
                    style = fontStyle.textMerriwatherBold(TextStyle(fontSize = 15.sp))
                )
            },
            contentRight = {
                if (!loading && isSeeMore) {
                    ButtonComponent(
                        typeButton = "Outline",
                        text = "See more",
                        onClick = {

                        }
                    )
                }
            }
        )

        LazyRow(
            state = state,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item { listHeaderComponent() }

            if (isPaging && pagingData != null) {
                if (pagingData.loadState.refresh !is LoadState.Loading && pagingData.itemCount == 0) {
                    item {
                        Text(
                            text = "No movies found",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                items(pagingData.itemCount) { index ->
                    pagingData[index]?.let {
                        MovieCardVerticalComponent(movie = it, isShimmer = false)
                    }
                }

                if (pagingData.loadState.refresh is LoadState.Loading || pagingData.loadState.append is LoadState.Loading) {
                    items(Constants.configList.listPlaceholder) {
                        MovieCardVerticalComponent(isShimmer = true)
                    }
                }

                if (pagingData.loadState.append is LoadState.Error) {
                    item {
                        Text(
                            text = "Error loading more movies",
                            color = Color.Red,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else if (!isPaging && nonPagingData != null) {
                itemsIndexed(
                    if (!loading) nonPagingData.results else List(Constants.configList.listPlaceholder) { null },
                    key = { index, movie -> movie?.id ?: index }
                ) { _, movie ->
                    MovieCardVerticalComponent(movie = movie, isShimmer = loading)
                }

                if (!loading && isSeeMore) {
                    item {
                        Column(
                            modifier = Modifier
                                .height(Constants.ConfigMovieCardVerticalComponentImage.height)
                                .width(Constants.ConfigMovieCardVerticalComponentImage.width - 40.dp)
                                .clickable {

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
                                        .size(24.dp)
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

            item { listFooterComponent() }
        }
    }
}

@Composable
fun VerticalLayout(
    listHeaderComponent: @Composable () -> Unit = {},
    title: String,
    nonPagingData: StateMovieList?,
    pagingData: LazyPagingItems<Movie>?,
    state: LazyListState,
    listFooterComponent: @Composable () -> Unit = {},
    loading: Boolean,
    isSeeMore: Boolean,
    isPaging: Boolean
) {
    LazyColumn(
        state = state,
        contentPadding = PaddingValues(bottom = 15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        item { listHeaderComponent() }

        item {
            HeaderComponent(
                contentPadding = PaddingValues(top = 10.dp, bottom = 2.dp, start = 15.dp, end = 15.dp),
                contentLeft = {
                    Text(
                        title,
                        style = fontStyle.textMerriwatherBold(TextStyle(fontSize = 15.sp))
                    )
                },
                contentRight = {
                    if (!loading && isSeeMore) {
                        ButtonComponent(
                            typeButton = "Outline",
                            text = "See more",
                            onClick = {

                            }
                        )
                    }
                }
            )
        }

        if (isPaging && pagingData != null) {
            if (pagingData.loadState.refresh !is LoadState.Loading && pagingData.itemCount == 0) {
                item {
                    Text(
                        text = "No movies found",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            items(pagingData.itemCount) { index ->
                pagingData[index]?.let {
                    MovieCardHorizontalComponent(
                        movie = it,
                        modifier = Modifier.padding(horizontal = 15.dp),
                        isShimmer = false
                    )
                }
            }

            if (pagingData.loadState.refresh is LoadState.Loading || pagingData.loadState.append is LoadState.Loading) {
                items(Constants.configList.listPlaceholder) {
                    MovieCardHorizontalComponent(
                        modifier = Modifier.padding(horizontal = 15.dp),
                        isShimmer = true
                    )
                }
            }

            if (pagingData.loadState.append is LoadState.Error) {
                item {
                    Text(
                        text = "Error loading more movies",
                        color = Color.Red,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else if (!isPaging && nonPagingData != null) {
            itemsIndexed(
                if (!loading) nonPagingData.results else List(Constants.configList.listPlaceholder) { null },
                key = { index, movie -> movie?.id ?: index }
            ) { _, movie ->
                MovieCardHorizontalComponent(
                    movie = movie,
                    modifier = Modifier.padding(horizontal = 15.dp),
                    isShimmer = loading
                )
            }

            if (!loading && isSeeMore) {
                item {
                    Text(
                        "See more",
                        style = fontStyle.textMulishBold(
                            TextStyle(fontSize = 15.sp, color = MaterialTheme.colorScheme.primary)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .clickable {

                            }
                    )
                }
            }
        }

        item { listFooterComponent() }
    }
}
