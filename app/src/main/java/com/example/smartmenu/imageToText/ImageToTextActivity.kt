package com.example.smartmenu.imageToText

import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartmenu.R
import com.example.smartmenu.ui.theme.SmartMenuTheme

class ImageToTextActivity : ComponentActivity() {
    private val viewModel by viewModels<MenuViewModel>()

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
            viewModel.processImage(bitmap)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartMenuTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("OCR Test") },
                            actions = {
                                IconButton(onClick = {
                                    pickImageLauncher.launch("image/*")
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Pick Image"
                                    )
                                }
                            }
                        )
                    }
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        MenuScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }

}
@Composable
fun MenuScreen(viewModel: MenuViewModel) {

    val state = viewModel.uiState

    when (state) {
        MenuUiState.Idle -> {
            Text("اختار صورة المنيو")
        }

        MenuUiState.Loading -> {
            CircularProgressIndicator()
        }

        is MenuUiState.Success -> {
            LazyColumn {
                items(state.items) { item ->
                    MenuItemCard(item)
                }
            }
        }

        is MenuUiState.Error -> {
            Text(
                text = state.message,
                color = Color.Red
            )
        }
    }
}

@Composable
fun MenuItemCard(item: MenuItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Text(
                text = item.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row {
                item.prices.forEach {
                    Text(
                        text = "$it ج",
                        modifier = Modifier.padding(end = 8.dp),
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

