import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.MutableWindowInsets
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imeAnimationTarget
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.ExperimentalMaterial3Api
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
        Box(
            Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .background(Color.Green.copy(alpha = 0.5f))
        )
        Box(
            Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeGestures)
                .background(Color.Red.copy(alpha = 0.5f))
        )
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

        Text("displayCutout",
            Modifier
                .windowInsetsPadding(WindowInsets.displayCutout)
                .align(Alignment.TopEnd))
        Text("statusBars",
            Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .align(Alignment.TopStart))

        Text("navigationBars",
            Modifier
                .windowInsetsPadding(WindowInsets.navigationBars)
                .align(Alignment.BottomCenter))
        Text(" IME ",
            Modifier
                .background(Color.Yellow)
                .align(Alignment.BottomStart))
        Text(" imeAnimationTarget ",
            Modifier
                .windowInsetsPadding(WindowInsets.imeAnimationTarget)
                .background(Color.Yellow)
                .align(Alignment.BottomCenter))
        Text(" ANIMATED ",
            Modifier
                .windowInsetsPadding(animatedInset)
                .background(Color.Yellow)
                .align(Alignment.BottomEnd))

        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            var textStr by remember { mutableStateOf("TextField to show keyboard") }
            TextField(textStr, { textStr = it })

            Text(
                "safeGestures",
                Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.safeGestures)
                    .background(Color.LightGray)
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

}

@OptIn(ExperimentalLayoutApi::class)
val animatedInset = MutableWindowInsets()
