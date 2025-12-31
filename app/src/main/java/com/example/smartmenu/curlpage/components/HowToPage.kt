package eu.wewox.pagecurl.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.smartmenu.curlpage.HowToPageData


/**
 * The simple page to use for demo purposes.
 *
 * @param index The index of the page to show a page number in the bottom.
 * @param page The page data to show.
 * @param modifier The modifier for this composable.
 */
@Composable
fun HowToPage(
    index: Int,
    page: HowToPageData,
    modifier: Modifier = Modifier
) {
//    Box(
//        modifier = modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//    )
//    {
//        Column(
//            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(32.dp)
//        ) {
//            Text(
//                text = page.title,
//                style = MaterialTheme.typography.headlineMedium,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Text(
//                text = page.message,
//                style = MaterialTheme.typography.bodyMedium,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//        Text(
//            text = index.toString(),
//            color = MaterialTheme.colorScheme.background,
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .background(MaterialTheme.colorScheme.onBackground, RoundedCornerShape(topStartPercent = 100))
//                .padding(16.dp)
//        )
//    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = page.link,
            contentDescription = "Full Screen Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds // أو Fit لو مش عايز قص
        )
    }
}
