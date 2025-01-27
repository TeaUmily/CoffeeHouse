package com.example.coffeehouse.ui.screens.catalog

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.coffeehouse.R
import com.example.coffeehouse.ui.theme.CoffeeHouseTheme
import com.example.coffeehouse.ui.theme.Red80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeCatalogResult(
    coffees: List<CatalogCoffee>,
    isRefreshing: Boolean,
    onFavoriteClick: (CatalogCoffee) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    PullToRefreshBox(
        contentAlignment = Alignment.Center,
        modifier = modifier,
        onRefresh = onRefresh,
        isRefreshing = isRefreshing
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            val roastLevels = listOf(
                1 to "Light Roast",
                2 to "Medium Roast",
                3 to "Medium-Dark Roast",
                4 to "Dark Roast",
                5 to "Espresso"
            )
            item {
                Text(
                    text = "It's time to recharge with your perfect brew!",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(roastLevels) { (roastLevel, title) ->
                CoffeeCarousel(
                    roastLevel = (roastLevel to title),
                    coffeeItems = coffees.filter { it.roastLevel == roastLevel },
                    onFavoriteClick = onFavoriteClick
                )
            }
        }
    }
}

@Composable
fun CoffeeCarousel(
    roastLevel: Pair<Int, String>,
    coffeeItems: List<CatalogCoffee>,
    onFavoriteClick: (CatalogCoffee) -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(
                text = roastLevel.second,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.weight(1f))
            for (i in 1..roastLevel.first) {
                Image(
                    painter = painterResource(R.drawable.ic_coffee_bean),
                    contentDescription = "Coffee bean icon",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp)
                )
            }
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(coffeeItems) { coffeeItem ->
                CoffeeItemCard(coffeeItem = coffeeItem, onFavoriteClick = onFavoriteClick)
            }
        }
    }
}

@Composable
fun CoffeeItemCard(
    coffeeItem: CatalogCoffee,
    onFavoriteClick: (CatalogCoffee) -> Unit,
) {
    val currentOnFavoriteClick = remember(coffeeItem) {
        {
            onFavoriteClick(coffeeItem)
        }
    }

    Column(
        modifier = Modifier
            .width(200.dp)
            .padding(4.dp)
            .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(coffeeItem.imageUrl)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.ic_image_loading),
                contentDescription = "Coffee photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            FavoriteButton(
                isFavorited = coffeeItem.isFavorite,
                onClick = currentOnFavoriteClick,
                favoriteDrawable = R.drawable.ic_heart_filled,
                unfavoriteDrawable = R.drawable.ic_heart_empty,
                modifier = Modifier.align(Alignment.TopEnd),
            )
        }

        Text(
            text = coffeeItem.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Start
        )

        Text(
            text = "$${coffeeItem.price}",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun FavoriteButton(
    isFavorited: Boolean,
    onClick: () -> Unit,
    @DrawableRes favoriteDrawable: Int,
    @DrawableRes unfavoriteDrawable: Int,
    modifier: Modifier = Modifier
) {
    val iconResource = if (isFavorited) {
        favoriteDrawable
    } else {
        unfavoriteDrawable
    }

    IconButton(
        onClick = onClick,
        modifier = modifier.testTag(if (isFavorited) "removeFromFavouritesButton" else "addToFavouritesButton"),
    ) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = "favorite",
            tint = if (isFavorited) Red80 else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CoffeeCatalogResultPreviewDark() {
    val coffees = listOf(
        mockedCoffeeItem.copy(id = "1", name = "Ethiopian Yirgache", roastLevel = 1, isFavorite = true),
        mockedCoffeeItem.copy(id = "2", name = "Medium Roast", roastLevel = 2),
        mockedCoffeeItem.copy(id = "3", name = "Medium-Dark Roast", roastLevel = 3),
        mockedCoffeeItem.copy(id = "4", name = "Dark Roast"),
        mockedCoffeeItem.copy(id = "5", name = "Espresso")
    )
    CoffeeHouseTheme {
        CoffeeCatalogResult(
            coffees = coffees,
            onFavoriteClick = {},
            isRefreshing = false,
            onRefresh = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CoffeeCatalogResultPreviewLight() {
    val coffees = listOf(
        mockedCoffeeItem.copy(id = "1", name = "Ethiopian Yirgache", roastLevel = 1, isFavorite = true),
        mockedCoffeeItem.copy(id = "2", name = "Medium Roast", roastLevel = 2),
        mockedCoffeeItem.copy(id = "3", name = "Medium-Dark Roast", roastLevel = 3),
        mockedCoffeeItem.copy(id = "4", name = "Dark Roast"),
        mockedCoffeeItem.copy(id = "5", name = "Espresso")
    )
    CoffeeHouseTheme {
        CoffeeCatalogResult(
            coffees = coffees,
            onFavoriteClick = {},
            isRefreshing = false,
            onRefresh = {}
        )
    }
}

val mockedCoffeeItem = CatalogCoffee(
    id = "123",
    name = "Ethiopian Yirgacheffe",
    imageUrl = "https://images.unsplash.com/photo-1616743800309-4b3b3b3b3b3b",
    price = 12.99,
    roastLevel = 1,
    description = "This is a description",
    isFavorite = false
)
