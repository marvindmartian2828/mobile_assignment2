package com.example.artspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspaceapp.ui.theme.ArtSpaceAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ArtSpaceApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class Artwork(
    @DrawableRes val imageRes: Int,
    @StringRes val titleRes: Int,
    @StringRes val artistRes: Int,
    val year: String
)

@Composable
fun ArtSpaceApp(modifier: Modifier = Modifier) {
    val artworks = listOf(
        Artwork(R.drawable.img1, R.string.canmore_view, R.string.alberta, "2024"),
        Artwork(R.drawable.img2, R.string.canmore_bridge, R.string.alberta, "2024"),
        Artwork(R.drawable.img3, R.string.calgary_downtown, R.string.alberta, "2024")
    )

    var currentIndex by remember { mutableIntStateOf(0) }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val isLandscape = screenWidth > 600.dp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize().padding(16.dp)
    ) {
        Card(
            shape = CutCornerShape(topStart = 16.dp, bottomEnd = 16.dp),
            border = BorderStroke(4.dp, Color(0xFF1B2A41)),
            modifier = Modifier
                .fillMaxWidth(if (isLandscape) 0.4f else 0.8f)
                .aspectRatio(3f / 4f)
                .shadow(6.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize().background(Color(0xFFF8F8F8))
            ) {
                Image(
                    painter = painterResource(id = artworks[currentIndex].imageRes),
                    contentDescription = stringResource(artworks[currentIndex].titleRes),
                    modifier = Modifier.fillMaxSize(0.9f)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF3D5A80)),
            modifier = Modifier
                .fillMaxWidth(if (isLandscape) 0.35f else 0.7f)
                .fillMaxHeight(if (isLandscape) 0.6f else 0.25f)
                .padding(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(14.dp)
            ) {
                Text(text = stringResource(artworks[currentIndex].titleRes), fontSize = 20.sp, color = Color.White)
                Text(text = "${stringResource(artworks[currentIndex].artistRes)} (${artworks[currentIndex].year})", fontSize = 17.sp, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 48.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    currentIndex = if (currentIndex == 0) artworks.size - 1 else currentIndex - 1
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF98C1D9)) ,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Previous", color = Color(0xFF404040))
            }

            Button(
                onClick = {
                    currentIndex = (currentIndex + 1) % artworks.size
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF98C1D9)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Next", color = Color(0xFF404040))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewArtSpaceApp() {
    ArtSpaceAppTheme {
        ArtSpaceApp()
    }
}