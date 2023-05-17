import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp

@Composable
fun MainView() {
    MaterialTheme {
        WithScaffold()
//    SampleInsets()
//    SampleImeNestedScroll()
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun WithScaffold() {
    androidx.compose.material.Scaffold(
        bottomBar = {
            BottomAppBar() {
                Box(
                    Modifier.height(50.dp)
                        .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom))
                        .fillMaxWidth().background(Color.White.copy(0.4f))
                )
            }
        },
    ) {
        Box(Modifier.fillMaxSize()) {
            Box(Modifier.fillMaxSize().background(Color.LightGray).padding(it)) {
                Box(
                    Modifier.size(50.dp).align(Alignment.BottomEnd)
                        .background(Color.Yellow.copy(0.5f))
                )
                Row(Modifier.align(Alignment.Center).fillMaxWidth()) {
                    KeyboardManipulator()
                }
            }
            Box(
                Modifier
                    .align(Alignment.BottomCenter)
                    .windowInsetsPadding(WindowInsets.ime)
                    .background(Color.Green.copy(0.5f))
                    .size(100.dp)
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KeyboardManipulator(modifier: Modifier = Modifier) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(modifier) {
        BasicText("Hide keyboard",
            Modifier.clickable { keyboardController?.hide() }.focusable(true)
                .background(Color.Red.copy(0.3f))
        )
        BasicTextField("Show keyboard", {}, Modifier.background(Color.Green.copy(0.3f)))
    }
}
