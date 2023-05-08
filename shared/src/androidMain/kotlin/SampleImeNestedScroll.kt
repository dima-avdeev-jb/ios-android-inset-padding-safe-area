import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SampleImeNestedScroll() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize() // fill the window
            .imePadding() // pad out the bottom for the IME
            .imeNestedScroll(), // scroll IME at the bottom
        reverseLayout = true // First item is at the bottom
    ) {
        // content
        items(50) {
            Text("Hello World")
        }
    }
}