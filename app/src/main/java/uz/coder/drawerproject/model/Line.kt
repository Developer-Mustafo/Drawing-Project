package uz.coder.drawerproject.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class Line(
    val start:Offset,
    val end:Offset,
    val color:Color = Color.Black,
    val strokeWidth:Float = 5f,
)