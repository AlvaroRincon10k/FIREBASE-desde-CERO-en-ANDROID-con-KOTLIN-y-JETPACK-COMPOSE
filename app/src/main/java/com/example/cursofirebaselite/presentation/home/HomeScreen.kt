package com.example.cursofirebaselite.presentation.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.cursofirebaselite.R
import com.example.cursofirebaselite.presentation.model.Artist
import com.example.cursofirebaselite.presentation.model.Player
import com.example.cursofirebaselite.ui.theme.Black
import com.example.cursofirebaselite.ui.theme.Purple40

@Composable
fun HomeScreen(viewModel: HomeViewModel = HomeViewModel()) {

    val artists = viewModel.artist.collectAsState()
    val player by viewModel.player.collectAsState()
    val blockVersion by viewModel.blockVersion.collectAsState()

    if (blockVersion) {
        val context = LocalContext.current
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                        .height(300.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ACTUALIZA",
                        fontSize = 22.sp,
                        color = Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Para poder disfrutar de todo nuestro contenido actualice la app",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = { navigateToPlayStore(context) }) {
                        Text(text = "¡Actualizar!")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        Text(
            "Popular artist",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(16.dp)
        )

        LazyRow {
            items(artists.value) {
                ArtistItem(artist = it, onItemSelected = { viewModel.addPlayer(it) })
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        player?.let {
            PlayerComponent(
                player = it,
                Modifier.navigationBarsPadding(),
                onCancelSelected = { viewModel.onCancelSelected() },
                onPlaySelected = { viewModel.onPlaySelected() })
        }
    }
}

@Composable
fun PlayerComponent(
    player: Player,
    modifier: Modifier = Modifier,
    onPlaySelected: () -> Unit,
    onCancelSelected: () -> Unit
) {
    val icon = if (player.play == true) R.drawable.pause_circle else R.drawable.play_circle
    Row(
        modifier
            .height(50.dp)
            .fillMaxWidth()
            .background(Purple40),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = player.artist?.name.orEmpty(), modifier = Modifier.padding(horizontal = 12.dp),
            color = Color.White
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = icon),
            contentDescription = "play/pause",
            modifier = Modifier
                .size(40.dp)
                .clickable { onPlaySelected() }
        )
        Image(
            painter = painterResource(id = R.drawable.cancel),
            contentDescription = "play/pause",
            modifier = Modifier
                .size(40.dp)
                .clickable { onCancelSelected() }
        )
    }
}


@Composable
fun ArtistItem(artist: Artist, onItemSelected: (Artist) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onItemSelected(artist) }) {
        AsyncImage(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            model = artist.image,
            contentDescription = "Artist image",
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = artist.name.orEmpty(), color = Color.White)
    }
}

@Preview
@Composable
fun ArtistItemPrevuiew() {
    val artist = Artist(
        "Pepe",
        "El mejor",
        "https://images.dog.ceo/breeds/spaniel-irish/n02102973_87.jpg"
    )
    ArtistItem(artist = artist) {}
}

fun navigateToPlayStore(context: Context) {
    val appPackage = context.packageName
    try {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$appPackage")
            )
        )
    } catch (e: Exception) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.como/store/apps/details?id=$appPackage")
            )
        )
    }
}

