package test.composetextstyle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import test.composetextstyle.ui.theme.TestComposeTextStyleTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestComposeTextStyleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TextGrid(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
private fun TextGrid(
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier,
    ) {
        item { TextWithHeight(Texts.WithStyle) }
        item { TextWithHeight(Texts.WithIndividualSettings) }
        item { TextWithHeight(Texts.WithIndividualSettings) }
        item { TextWithHeight(Texts.WithStyle) }
        item { TextWithHeight(Texts.WithStyle) }
        item { TextWithHeight(Texts.WithIndividualSettings) }
    }
}

enum class Texts {
    WithStyle, WithIndividualSettings,
}

@Composable
private fun TextWithHeight(text: Texts) {
    var textHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Row {
        when (text) {
            Texts.WithStyle -> TextWithTextStyle {
                textHeight = with(density) { it.toDp() }
            }
            Texts.WithIndividualSettings -> TextWithIndividualSettings {
                textHeight = with(density) { it.toDp() }
            }
        }

        Text(
            text = "h: ${textHeight.value.roundToInt()}",
            modifier = Modifier.padding(start = 4.dp),
            color = Color.Gray,
        )
    }
}

@Composable
private fun TextWithTextStyle(
    heightUpdate: (Int) -> Unit = {}
) {
    Text(
        modifier = Modifier.onGloballyPositioned { heightUpdate(it.size.height) },
        text = "Lorem ipsulum",
        style = TextStyle(
            lineHeight = 24.sp,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500
        ),
    )
}

@Composable
private fun TextWithIndividualSettings(
    heightUpdate: (Int) -> Unit = {}
) {
    Text(
        modifier = Modifier.onGloballyPositioned { heightUpdate(it.size.height) },
        text = "Lorem ipsulum",
//        style = TextStyle(
            lineHeight = 24.sp,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500
//        ),
    )
}


@Preview(showBackground = true)
@Composable
fun TextGridPreview() {
    TestComposeTextStyleTheme {
        TextGrid()
    }
}