import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MainView() {
    TransparentSystemBars()
    Box(Modifier.fillMaxSize()) {

        Text("displayCutout", Modifier.displayCutoutPadding().align(Alignment.TopEnd))
        Text("statusBars", Modifier.statusBarsPadding().align(Alignment.TopStart))

        Text("navigationBars", Modifier.navigationBarsPadding().align(Alignment.BottomCenter))
        Text("ime", Modifier.imePadding().background(Color.Yellow).align(Alignment.BottomStart))

        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            var textStr by remember { mutableStateOf("TextField to show keyboard") }
            TextField(textStr, { textStr = it })

            Text(
                "safeGestures",
                Modifier.fillMaxWidth().safeGesturesPadding().background(Color.LightGray)
            )
        }
    }

}
