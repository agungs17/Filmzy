package com.devs.filmzy.src.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devs.filmzy.src.components.ButtonComponent
import com.devs.filmzy.src.components.HeaderComponent
import com.devs.filmzy.src.components.IconComponent
import com.devs.filmzy.src.components.MovieCardHorizontalComponent
import com.devs.filmzy.src.components.MovieCardVerticalComponent
import com.devs.filmzy.src.models.MovieList.StateMovieList
import com.devs.filmzy.src.theme.fontStyle
import com.devs.filmzy.src.utils.Constants
import com.devs.filmzy.src.viewModels.movie.DiscoveryMovieViewModel
import com.devs.filmzy.src.viewModels.movie.NowPlayingViewModel
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun HomeView() {
    val discoveryMovieViewModel: DiscoveryMovieViewModel = hiltViewModel()
    val nowPlayingViewModel: NowPlayingViewModel = hiltViewModel()

    val nowPlayingMovieState by nowPlayingViewModel.state.collectAsState()
    val discoveryMovieState by discoveryMovieViewModel.state.collectAsState()

    val listState = rememberLazyListState()
    val isFetched = rememberSaveable { mutableStateOf(false) }
    var useBottomBorder by remember { mutableStateOf(false) } // menggunakan versi mutableStateOf lebih optimize (hanya ke trigger bila LaunchedEffect terpenuhi)
    //    perbedaan rememberSaveable dan remember, rememberSaveable Menyimpan state walaupun saat Composable keluar dari Composition kalo remember tidak menyimpan data (di reset) saat composition keluar
    //    derivedStateOf/mutableStateOf sama seperti useState
    //    Pakai derivedStateOf kalau: State bergantung pada state lain dan hanya perlu di-recompute ketika state asal berubah, contoh membaca state (rememberLazyListState) dari LazyColumn/LazyRow.
    //    Pakai mutableStateOf kalau: Nilai berubah secara langsung dari user atau event lain yang tidak bergantung pada state lain, contoh input text, tombol, switch, dll.

    //    LaunchedEffect seperti useEffect bedanya ketika navigation ini LaunchedEffect akan ketrigger kembali karena direcompose ulang (re-render) jika hanya ingin sekali penggunaan gunakan rememberSaveable
    LaunchedEffect(Unit) {
        if (!isFetched.value) {
            discoveryMovieViewModel.fetch()
            nowPlayingViewModel.fetch()
            isFetched.value = true
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
               contentPadding = PaddingValues(top = 30.dp, bottom = 10.dp, start = 15.dp, end = 15.dp),
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
                item { NowShowingList(nowPlayingMovieState.copy(results = nowPlayingMovieState.results.take(Constants.nowShowingListHome))) }
                item {
                    HeaderComponent(
                        titleLeft = "Discovery",
                        contentPadding = PaddingValues(top = 10.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
                    )
                }
                itemsIndexed(
                    if (discoveryMovieState.results.isNotEmpty()) discoveryMovieState.results else List(Constants.placeholderListHome) { null },
                    key = { index, movie -> movie?.id ?: index }
                ) { index, movie ->
                    MovieCardHorizontalComponent(movie = movie, modifier = Modifier.padding(horizontal = 15.dp), isShimmer = discoveryMovieState.loading)
                }
            }
        }
    }
}

@Composable
fun NowShowingList (
    state : StateMovieList
) {
    Column {
        HeaderComponent(
            titleLeft = "Now Showing",
            contentPadding = PaddingValues(vertical = 20.dp, horizontal = 10.dp),
            contentRight = {
                if (state.results.isNotEmpty()) {
                    ButtonComponent(
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
                if (state.results.isNotEmpty()) state.results else List(Constants.placeholderListHome) { null },
                key = { index, movie -> movie?.id ?: index }
            ) { index, movie ->
                MovieCardVerticalComponent(movie = movie, isShimmer = state.loading)
            }
            // Footer Last Index
            item {
                if (state.results.isNotEmpty()) {
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