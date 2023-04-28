import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

actual fun getPlatformName(): String = "Android"

@Composable
fun MainView() {
    TransparentSystemBars()
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.align(Alignment.TopStart)) {
            repeat(5) {
                Text(it.toString())
            }
        }

        Column(Modifier.align(Alignment.BottomStart)) {
            repeat(5) {
                Text(it.toString())
            }
        }
    }
    if(false)Box(
        Modifier
            .background(Color.Yellow)
            .displayCutoutPadding()//cutout
            .background(Color.Blue)
            .navigationBarsPadding()//bottom
            .imePadding()//keyboard
            .background(Color.LightGray)
            .fillMaxSize()
    ) {

    }
}
