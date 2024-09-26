package uz.coder.drawerproject.screen
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import uz.coder.drawerproject.model.Line
import java.io.File
import java.io.FileOutputStream

@Composable
fun DrawingApp(modifier: Modifier = Modifier, paddingValues: PaddingValues) {
    val context = LocalContext.current
    var bitmap: Bitmap? by remember { mutableStateOf(null) }
    val lines = remember { mutableStateListOf<Line>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        lines.add(Line(change.position-dragAmount, change.position))
                    }
                }
        ) {
            Canvas(modifier = modifier.fillMaxSize()) {
                for (line in lines) {
                    drawLine(start = line.start, end = line.end, color = line.color, strokeWidth = line.strokeWidth)
                }

                bitmap = createBitmapFromDrawScope(this, lines)
            }
        }

        Button(onClick = {
            bitmap?.let {
                saveBitmapAsPng(context, it)
            }
        }) {
            Text("Save Drawing")
        }
    }
}

fun createBitmapFromDrawScope(drawScope: DrawScope, lines: List<Line>): Bitmap {
    val width = drawScope.size.width.toInt()
    val height = drawScope.size.height.toInt()

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    val androidCanvas = Canvas(bitmap)

    androidCanvas.apply {
        drawColor(android.graphics.Color.WHITE)

        lines.forEach { line ->
            androidCanvas.drawLine(
                line.start.x, line.start.y,
                line.end.x, line.end.y,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    strokeWidth = line.strokeWidth
                    isAntiAlias = true
                }
            )
        }
    }

    return bitmap
}

fun saveBitmapAsPng(context: Context, bitmap: Bitmap) {
    val filePath = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "user_${System.currentTimeMillis()}_drawing.png")
    try {
        FileOutputStream(filePath).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
