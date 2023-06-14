package ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SetupContent(onStartClick: (String, String) -> Unit) {
    var chaser by remember { mutableStateOf("") }
    var player by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        TextBox(text = chaser, label = "chaser") {
            chaser = it
        }
        TextBox(text = player, label = "player") {
            player = it
        }
        Spacer(modifier = Modifier.size(20.dp))
        Button(onClick = { onStartClick(chaser, player) }) {
            Text("Start")
        }
    }
}
