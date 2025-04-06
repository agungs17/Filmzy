package com.devs.filmzy.src.components.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devs.filmzy.src.theme.fontStyle
import com.devs.filmzy.src.utils.NavigationManager
import com.devs.filmzy.src.viewModels.misc.NavigateViewModel

@Composable
fun HeaderComponent(
    modifier: Modifier = Modifier,
    navigation: NavigationManager = hiltViewModel<NavigateViewModel>().navigation,
    bgColor: Color = MaterialTheme.colorScheme.background,

    modifierContent: Modifier = Modifier,
    contentPadding : PaddingValues = PaddingValues(vertical = 15.dp, horizontal = 15.dp),

    useBack : Boolean = false,
    useBottomBorder : Boolean = false,

    modifierLeftContent: Modifier = Modifier,
    contentLeft: @Composable (() -> Unit)? = null,
    titleLeft : String = "",

    modifierCenterContent: Modifier = Modifier,
    contentCenter : @Composable (() -> Unit)? = null,

    modifierRightContent: Modifier = Modifier,
    contentRight : @Composable (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .background(bgColor)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = modifierContent
                    .fillMaxWidth()
                    .padding(contentPadding)
            ) {
                Row (
                    modifier = modifierLeftContent
                        .align(Alignment.CenterStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(contentLeft == null) {
                        if(useBack) {
                            IconComponent(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back Icon",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                sizeIcon = 28.dp,
                                onClick = {
                                    navigation.goBack()
                                }
                            )
                        }
                        if(titleLeft != "") Text(text = titleLeft, style = fontStyle.textMerriwatherBold(
                            TextStyle(fontSize = 16.sp)
                        ), modifier = Modifier.padding(start = 10.dp))
                    } else contentLeft.invoke()
                }

                Box(
                    modifier = modifierCenterContent.align(Alignment.Center)
                ) {
                    contentCenter?.invoke()
                }

                Row (
                    modifier = modifierRightContent
                        .align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(contentRight != null) contentRight.invoke()
                }
            }

            if (useBottomBorder) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}
