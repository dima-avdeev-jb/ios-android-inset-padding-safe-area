import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.MutableWindowInsets
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.random.Random

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SampleInsets() {
    Box(Modifier.fillMaxSize()) {
        if (false) Box(
            Modifier
                .align(Alignment.TopCenter)
                .background(Color.Blue.copy(alpha = 0.3f))
                .windowInsetsPadding(WindowInsets.statusBars.union(WindowInsets.displayCutout))
                .fillMaxWidth()
        ) {
            Row(Modifier.fillMaxWidth()) {
                IconButton({}) {
                    Icon(Icons.Default.ArrowBack, null)
                }
                Text("Application name")
            }
        }
        Text("displayCutout", Modifier.displayCutoutPadding().align(Alignment.TopEnd))
        Text("statusBars", Modifier.statusBarsPadding().align(Alignment.TopStart))

        Text("navigationBars", Modifier.navigationBarsPadding().align(Alignment.BottomCenter))
        Text(" IME ", Modifier.imePadding().background(Color.Yellow).align(Alignment.BottomStart))
        Text(
            " ANIMATED ", Modifier.windowInsetsPadding(animatedInset)
                .background(Color.Yellow).align(Alignment.BottomEnd)
        )

        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            var textStr by remember { mutableStateOf("TextField to show keyboard") }
            TextField(textStr, { textStr = it })

            Text(
                "safeGestures",
                Modifier.fillMaxWidth().safeGesturesPadding().background(Color.LightGray)
            )
        }
    }
    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { }
            animatedInset.insets = object : WindowInsets {
                override fun getBottom(density: Density): Int = Random.nextInt(0, 200)
                override fun getLeft(density: Density, layoutDirection: LayoutDirection): Int = 0
                override fun getRight(density: Density, layoutDirection: LayoutDirection): Int = 0
                override fun getTop(density: Density): Int = 0
            }
        }
    }

    Modifier.windowInsetsPadding(WindowInsets.ime)
    Modifier.windowInsetsPadding(WindowInsets.displayCutout)
    Modifier.windowInsetsPadding(WindowInsets.statusBars)
}

@OptIn(ExperimentalLayoutApi::class)
val animatedInset = MutableWindowInsets()
