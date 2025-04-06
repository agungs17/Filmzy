package com.devs.filmzy.src.components.molecules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devs.filmzy.src.components.atoms.ImageComponent
import com.devs.filmzy.src.components.atoms.ShimmerComponent
import com.devs.filmzy.src.models.PersonList.Person
import com.devs.filmzy.src.services.ConfigApi
import com.devs.filmzy.src.theme.fontStyle

@Composable
fun PersonCardVerticalComponent(
    modifier: Modifier = Modifier,
    person: Person? = null,
    isShimmer: Boolean = false
) {
    Column (
        modifier = modifier
            .width(125.dp)
            .run {
                if(isShimmer) {
                    this
                } else {
                    clickable{

                    }
                }
            }
    ) {
        ShimmerComponent(isShimmer) {
            ImageComponent(
                model = (ConfigApi.BASE_URL_IMG + person?.profile_path),
                height = 150.dp,
                width = 125.dp,
                round = 8.dp
            )
        }
        ShimmerComponent(
            isShimmer,
            modifier = Modifier.padding(top = 2.dp)
        ) {
            Text(
                person?.name ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp).fillMaxWidth(),
                style = fontStyle.textMulishSemiBold(TextStyle(fontSize = 14.sp, color = MaterialTheme.colorScheme.onPrimary))
            )
        }
        ShimmerComponent(
            isShimmer,
            modifier = Modifier.padding(top = 2.dp)
        ) {
            Text(
                if(person?.job != null) "As ${person.job}" else "",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp).fillMaxWidth(),
                style = fontStyle.textMulishSemiBold(
                    TextStyle(
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                )
            )
        }
    }
}

//fun PersonCardHorizontalComponent (
//
//) {
//
//}
