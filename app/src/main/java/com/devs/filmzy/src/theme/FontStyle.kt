package com.devs.filmzy.src.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.devs.filmzy.R

object fontFamily {
    val merriwather = FontFamily(Font(R.font.merriweather))
    val mulish = FontFamily(Font(R.font.mulish))
}

// textStyle when u want override style text
object fontStyle {

    @Composable
    fun textMerriwather (textStyle: TextStyle = TextStyle()) : TextStyle {
        return TextStyle(
            fontFamily = fontFamily.merriwather,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            letterSpacing = 0.5.sp
        ).merge(textStyle)
    }

    @Composable
    fun textMerriwatherSemiBold (textStyle: TextStyle = TextStyle()) : TextStyle {
        return TextStyle(
            fontFamily = fontFamily.merriwather,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            letterSpacing = 0.5.sp
        ).merge(textStyle)
    }

    @Composable
    fun textMerriwatherBold (textStyle: TextStyle = TextStyle()) : TextStyle {
        return TextStyle(
            fontFamily = fontFamily.merriwather,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            letterSpacing = 0.5.sp
        ).merge(textStyle)
    }

    @Composable
    fun textMulish (textStyle: TextStyle = TextStyle()) : TextStyle {
        return TextStyle(
            fontFamily = fontFamily.mulish,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            letterSpacing = 0.5.sp
        ).merge(textStyle)
    }

    @Composable
    fun textMulishSemiBold (textStyle: TextStyle = TextStyle()) : TextStyle {
        return TextStyle(
            fontFamily = fontFamily.mulish,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            letterSpacing = 0.5.sp
        ).merge(textStyle)
    }

    @Composable
    fun textMulishBold (textStyle: TextStyle = TextStyle()) : TextStyle {
        return TextStyle(
            fontFamily = fontFamily.mulish,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            letterSpacing = 0.5.sp
        ).merge(textStyle)
    }
}