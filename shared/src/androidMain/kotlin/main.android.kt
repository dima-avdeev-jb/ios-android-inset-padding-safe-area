import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MainView() {
    MaterialTheme {
        WithScaffold()
//    SampleInsets()
//    SampleImeNestedScroll()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithScaffold() {
    androidx.compose.material.Scaffold(
        bottomBar = {
            BottomAppBar() {
                Box(Modifier.height(50.dp).windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom)).fillMaxWidth().background(Color.White.copy(0.4f)))
            }
        },
//        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.union(WindowInsets.ime)
    ) {
        Box(Modifier.fillMaxSize()) {
            Box(Modifier.fillMaxSize().background(Color.LightGray).padding(it)) {
                Box(Modifier.size(50.dp).align(Alignment.BottomEnd).background(Color.Yellow.copy(0.5f)))

                Column(Modifier.align(Alignment.TopCenter)) {
                    TextField("some TextField", {})
                    Button({}) { Text("Button") }
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WithBackdropScaffold() {
    BackdropScaffold(
        appBar = {
            TopAppBar {
                Text("TopAppBar")
            }
        },
        backLayerContent = {
            Box(Modifier.fillMaxSize().background(Color.LightGray))
        },
        frontLayerContent = {
            Box(Modifier.fillMaxSize().background(Color.Green.copy(alpha = 0.3f)))
        },
    )
}


//    LaunchedEffect(Unit) {
//        snapshotFlow {  }
//    }
