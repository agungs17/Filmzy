package com.devs.filmzy.src.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.twotone.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devs.filmzy.src.components.atoms.HeaderComponent
import com.devs.filmzy.src.components.atoms.IconComponent
import com.devs.filmzy.src.components.organisms.MovieListComponent
import com.devs.filmzy.src.models.MovieList.MovieListParams
import com.devs.filmzy.src.theme.fontStyle
import com.devs.filmzy.src.utils.Constants
import com.devs.filmzy.src.utils.getDate
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
    val listState = rememberLazyListState()
    var useBottomBorder by remember { mutableStateOf(false) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 70 } // seperti dependency di useEffect
            .distinctUntilChanged() // Mencegah update kalau value tidak berubah
            .collect {
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
            MovieListComponent(
                listHeaderComponent = {
                    MovieListComponent(
                        title = "Now Showing",
                        movieParams = MovieListParams(
                            withReleaseType = "${Constants.ReleaseType.THEATRICAL}|${Constants.ReleaseType.THEATRICAL_LIMITED}",
                            maxDate = getDate(),
                            minDate = getDate(LocalDate.now().minusMonths(1))
                        )
                    )
                },
                title = "Discovery",
                movieParams = MovieListParams(sortBy = Constants.SortByType.VOTE_COUNT_DESC),
                state = listState,
                isVertical = true,
                isPaging = true
            )
        }
    }
}