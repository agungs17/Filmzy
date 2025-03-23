package com.devs.filmzy.src.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.twotone.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.devs.filmzy.src.components.ButtonComponent
import com.devs.filmzy.src.components.HeaderComponent
import com.devs.filmzy.src.components.IconComponent
import com.devs.filmzy.src.components.MovieCardHorizontalComponent
import com.devs.filmzy.src.components.MovieCardVerticalComponent
import com.devs.filmzy.src.models.MovieList.MovieListParams
import com.devs.filmzy.src.models.MovieList.StateMovieList
import com.devs.filmzy.src.theme.fontStyle
import com.devs.filmzy.src.utils.Constants
import com.devs.filmzy.src.utils.getDate
import com.devs.filmzy.src.viewModels.movie.MovieListPagingViewModel
import com.devs.filmzy.src.viewModels.movie.MovieListViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import org.threeten.bp.LocalDate


//    perbedaan rememberSaveable dan remember, rememberSaveable Menyimpan state walaupun saat Composable keluar dari Composition kalo remember tidak menyimpan data (di reset) saat composition keluar
//    derivedStateOf/mutableStateOf sama seperti useState
//    Pakai derivedStateOf kalau: State bergantung pada state lain dan hanya perlu di-recompute ketika state asal berubah, contoh membaca state (rememberLazyListState) dari LazyColumn/LazyRow.
//    Pakai mutableStateOf kalau: Nilai berubah secara langsung dari user atau event lain yang tidak bergantung pada state lain, contoh input text, tombol, switch, dll.

//    LaunchedEffect seperti useEffect bedanya ketika navigation ini LaunchedEffect akan ketrigger kembali karena direcompose ulang (re-render) jika hanya ingin sekali penggunaan gunakan rememberSaveable
//    DisposableEffect ini juga seperti useEffect bedanya dengan LaunchEffect, LaunchEffect tidak punya Unmount cycle. pada DisposableEffect onDispose itu seperti componentWillUnmount

@Composable
fun HomeView() {
    val nowPlayingViewModel: MovieListViewModel = hiltViewModel()
    val discoveryMovieViewModel: MovieListPagingViewModel = hiltViewModel()

    val nowPlayingMovieState by nowPlayingViewModel.state.collectAsState()
    val discoveryMovieState = discoveryMovieViewModel.state.collectAsLazyPagingItems()

    val listState = rememberLazyListState()
    var useBottomBorder by remember { mutableStateOf(false) }
    var isFetched by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect (Unit) {
        if(!isFetched) {
            discoveryMovieViewModel.updateParams(MovieListParams(sortBy = Constants.SortByType.VOTE_COUNT_DESC))
            nowPlayingViewModel.fetch(MovieListParams(
                withReleaseType = "${Constants.ReleaseType.THEATRICAL}|${Constants.ReleaseType.THEATRICAL_LIMITED}",
                maxDate = getDate(),
                minDate = getDate(LocalDate.now().minusMonths(1))
            ))
            isFetched = true
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 70 } // seperti dependency di useEffect
            .distinctUntilChanged() // Mencegah update kalau value tidak berubah
            .collect { offset ->
                val tempUseBottomBorder = listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 70
                if(tempUseBottomBorder != useBottomBorder) useBottomBorder = tempUseBottomBorder
            }
    }

    Scaffold (
       topBar = {
           HeaderComponent(
               contentPadding = PaddingValues(top = 40.dp, bottom = 10.dp, start = 15.dp, end = 15.dp),
               contentLeft = {
                   IconComponent(
                       imageVector = Icons.TwoTone.Menu,
                       contentDescription = "Drawer Icon",
                       tint = MaterialTheme.colorScheme.onPrimary,
                       onClick = {

                       }
                   )
               },
               contentCenter = {
                   Text(text = "Filmzy", style = fontStyle.textMerriwatherBold(TextStyle(fontSize = 16.sp)))
               },
               contentRight = {
                   IconComponent(
                       imageVector = Icons.Outlined.Notifications,
                       contentDescription = "Notification Icon",
                       tint = MaterialTheme.colorScheme.onPrimary,
                       sizeIcon = 26.dp,
                       onClick = {

                       }
                   )
               },
               useBottomBorder = useBottomBorder
           )
       },
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            LazyColumn (
                state = listState,
                contentPadding = PaddingValues(bottom = 15.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            )
            {
                item { NowShowingList(nowPlayingMovieState.copy(results = nowPlayingMovieState.results.take(Constants.configList.listHorizontal))) }
                item {
                    HeaderComponent(
                        titleLeft = "Discovery",
                        contentPadding = PaddingValues(top = 10.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
                    )
                }
                if (discoveryMovieState.loadState.refresh !is LoadState.Loading && discoveryMovieState.itemCount == 0) {
                    item {
                        Text(
                            text = "No movies found",
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                items(discoveryMovieState.itemCount) { index ->
                    val movie = discoveryMovieState[index]
                    movie?.let {
                        MovieCardHorizontalComponent(
                            movie = movie,
                            modifier = Modifier.padding(horizontal = 15.dp),
                            isShimmer = false
                        )
                    }
                }
                if (discoveryMovieState.loadState.refresh is LoadState.Loading || discoveryMovieState.loadState.append is LoadState.Loading) {
                    items(Constants.configList.listPlaceholder) {
                        MovieCardHorizontalComponent(
                            modifier = Modifier.padding(horizontal = 15.dp),
                            isShimmer = true
                        )
                    }
                }
                if (discoveryMovieState.loadState.append is LoadState.Error) {
                    item {
                        Text(
                            text = "Error loading more movies",
                            color = Color.Red,
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NowShowingList (
    state : StateMovieList
) {
    Column {
        HeaderComponent(
            titleLeft = "Now Showing",
            contentPadding = PaddingValues(vertical = 20.dp, horizontal = 10.dp),
            contentRight = {
                if (!state.loading) {
                    ButtonComponent(
                        typeButton = "Outline",
                        text = "See more",
                        onClick = {

                        }
                    )
                }
            }
        )
        LazyRow (
            contentPadding = PaddingValues(start = 15.dp, end = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            itemsIndexed(
                if (!state.loading) state.results else List(Constants.configList.listPlaceholder) { null },
                key = { index, movie -> movie?.id ?: index }
            ) { index, movie ->
                MovieCardVerticalComponent(movie = movie, isShimmer = state.loading)
            }
            // Footer Last Index
            item {
                if (!state.loading) {
                    Column(
                        modifier = Modifier
                            .height(Constants.ConfigMovieCardVerticalComponentImage.height)
                            .width( Constants.ConfigMovieCardVerticalComponentImage.width - 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier.clickable { },
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
}