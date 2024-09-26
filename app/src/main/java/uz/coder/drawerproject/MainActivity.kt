package uz.coder.drawerproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import uz.coder.drawerproject.screen.DrawingApp
import uz.coder.drawerproject.ui.theme.DrawerProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrawerProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    DrawingApp(paddingValues = it)
                }
            }
        }
    }
}