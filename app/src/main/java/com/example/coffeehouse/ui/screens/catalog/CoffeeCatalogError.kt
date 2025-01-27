package com.example.coffeehouse.ui.screens.catalog

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeehouse.R
import com.example.coffeehouse.ui.theme.Coffee40
import com.example.coffeehouse.ui.theme.CoffeeHouseTheme

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(160.dp),
            painter = painterResource(id = R.drawable.very_sad_cup), contentDescription = ""
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "Uh-oh! Something went wrong, please try again.",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            onClick = retryAction,
            modifier = Modifier.widthIn(min = 48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Coffee40
            )
        ) {
            Text(
                text = "Retry",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ErrorScreenPreviewDark() {
    CoffeeHouseTheme {
        Surface {
            ErrorScreen(retryAction = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreviewLight() {
    CoffeeHouseTheme {
        Surface {
            ErrorScreen(retryAction = {})
        }
    }
}
