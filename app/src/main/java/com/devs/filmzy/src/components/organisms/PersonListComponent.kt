package com.devs.filmzy.src.components.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devs.filmzy.src.components.atoms.ButtonComponent
import com.devs.filmzy.src.components.atoms.HeaderComponent
import com.devs.filmzy.src.components.atoms.IconComponent
import com.devs.filmzy.src.components.molecules.PersonCardVerticalComponent
import com.devs.filmzy.src.models.PersonList.Person
import com.devs.filmzy.src.theme.fontStyle
import com.devs.filmzy.src.utils.Constants
import com.devs.filmzy.src.viewModels.person.PersonListViewModel

@Composable
fun PersonListComponent(
    movieId: String,
    title: String = "Cast & Crew",
    isVertical: Boolean = false,
    minimumList: Int = Constants.configList.listHorizontal
) {
    var isFetched by rememberSaveable { mutableStateOf(false) }

    val personListViewModel: PersonListViewModel = hiltViewModel()
    val personListState by personListViewModel.state.collectAsState()

    var listData = personListState.cast + personListState.crew
    val isSeeMore = listData.size > minimumList
    listData = listData.take(minimumList)

    LaunchedEffect(Unit) {
        if (!isFetched) {
            personListViewModel.fetch(movieId)
            isFetched = true
        }
    }

    if (isVertical) {
        PersonListVerticalLayout(
//            title = title,
//            listData = listData,
//            loading = personListState.loading,
//            isSeeMore = isSeeMore
        )
    } else {
        PersonListHorizontalLayout(
            title = title,
            listData = listData,
            loading = personListState.loading,
            isSeeMore = isSeeMore
        )
    }
}

@Composable
private fun PersonListHorizontalLayout(
    title: String,
    listData: List<Person?>,
    loading: Boolean,
    isSeeMore: Boolean
) {
    val data = if (!loading) listData else List(Constants.configList.listPlaceholder) { null }

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
        contentPadding = PaddingValues(horizontal = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        itemsIndexed(
            data,
            key = { index, person -> person?.credit_id ?: index }
        ) { _, person ->
            PersonCardVerticalComponent(
                person = person,
                isShimmer = loading
            )
        }

        if (!loading && isSeeMore) {
            item {
                Column(
                    modifier = Modifier
                        .height(150.dp)
                        .width(85.dp)
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
}

@Composable
private fun PersonListVerticalLayout(
//    title: String,
//    listData: List<Person?>,
//    loading: Boolean,
//    isSeeMore: Boolean
) {

}