import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup

enum class Top {
    WhatsApp,
    Telegram,
    Empty,
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun WithScaffold() {
    var topState by remember { mutableStateOf(Top.Telegram) }
    androidx.compose.material3.Scaffold(
        topBar = {
            Box(
                Modifier.background(
                    Brush.horizontalGradient(listOf(Color.Blue.copy(0.4f), Color.Green.copy(0.4f)))
                )
            ) {
                when (topState) {
                    Top.WhatsApp -> {
                        WhatsAppTop()
                    }

                    Top.Telegram -> {
                        TelegramTop()
                    }

                    Top.Empty -> {

                    }
                }
            }
        },
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
            Box(Modifier.fillMaxSize().padding(it)) {
                Box(
                    Modifier.size(50.dp).align(Alignment.BottomEnd)
                        .background(Color.Yellow.copy(0.5f))
                )
                Row(Modifier.align(Alignment.Center).fillMaxWidth()) {
                    KeyboardManipulator()
                    SwitchStates(Top.values(), topState) { topState = it }
                }
            }
            Box(
                Modifier
                    .align(Alignment.BottomCenter)
                    .windowInsetsPadding(WindowInsets.ime)
                    .background(Color.Green.copy(0.5f))
                    .size(140.dp)
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KeyboardManipulator(modifier: Modifier = Modifier) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(modifier) {
        BasicText(
            "Hide keyboard",
            Modifier.clickable { keyboardController?.hide() }.focusable(true)
                .background(Color.Red.copy(0.3f))
        )
        BasicTextField("Show keyboard", {}, Modifier.background(Color.Green.copy(0.3f)))
    }
}

@Composable
fun <T> SwitchStates(states: Array<T>, current: T, onChange: (T) -> Unit) {
    val expanded = remember { mutableStateOf(false) }
    Text(current.toString(), Modifier.clickable { expanded.value = !expanded.value })
    if (expanded.value) {
        Popup(onDismissRequest = { expanded.value = false }) {
            Box(Modifier.fillMaxSize().background(Color.Black.copy(0.5f))) {
                Column(Modifier.background(Color.LightGray).align(Alignment.Center)) {
                    states.forEach {
                        Text(
                            it.toString(),
                            Modifier.clickable {
                                expanded.value = false
                                onChange(it)
                            }.padding(2.dp).border(1.dp, Color.Blue).padding(2.dp)
                                .background(Color.Blue.copy(0.3f))
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhatsAppTop() {
    CenterAlignedTopAppBar(
        navigationIcon = {
            Text("Edit", color = Color.Blue)
        },
        title = { Text("Chats", fontWeight = FontWeight.Bold) },
        actions = {
            val modifier = Modifier.padding(horizontal = 10.dp)
            Icon(Icons.Outlined.Phone, null, modifier, Color.Blue)
            Icon(Icons.Outlined.Edit, null, modifier, Color.Blue)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelegramTop() {
    CenterAlignedTopAppBar(
        navigationIcon = {
            Row {
                Icon(Icons.Default.ArrowBack, null, tint = Color.Blue)
                Text("Back", color = Color.Blue)
            }
        },
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Some Name", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("last seen 3 hours ago", fontSize = 10.sp)
            }
        },
        actions = {
            val modifier = Modifier.padding(horizontal = 10.dp)
            Icon(Icons.Outlined.Phone, null, modifier, Color.Blue)
            Icon(Icons.Outlined.Edit, null, modifier, Color.Blue)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        )
    )
}
