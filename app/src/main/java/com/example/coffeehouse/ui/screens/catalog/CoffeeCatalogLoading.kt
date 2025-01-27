package com.example.coffeehouse.ui.screens.catalog

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coffeehouse.ui.extensions.shimmerEffect
import com.example.coffeehouse.ui.theme.CoffeeHouseTheme

const val TITLE_ASPECT_RATIO = 31f / 3f
const val NUMBER_OF_LOADING_CAROUSELS = 3
const val NUMBER_OF_LOADING_COFFEE_ITEMS = 3

@Composable
fun CoffeeCatalogLoading(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        userScrollEnabled = false
    ) {
        items(NUMBER_OF_LOADING_CAROUSELS) {
            Spacer(modifier = Modifier.height(24.dp))
            CoffeeCatalogCarouselLoading()
        }
    }
}

@Composable
fun CoffeeCatalogCarouselLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        MediumShapeBox(
            modifier = Modifier
                .padding(start = 24.dp)
                .width(200.dp)
                .aspectRatio(TITLE_ASPECT_RATIO),
            thenModifier = Modifier.shimmerEffect()
        ) {}

        Spacer(modifier = Modifier.height(24.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 24.dp),
            userScrollEnabled = false
        ) {
            items(NUMBER_OF_LOADING_COFFEE_ITEMS) {
                CoffeeCardLoading(modifier = Modifier.width(200.dp))
            }
        }
    }
}

@Composable
fun CoffeeCardLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MediumShapeBox(
            modifier = Modifier
                .aspectRatio(1f),
            thenModifier = Modifier.shimmerEffect()
        ) {}

        MediumShapeBox(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(22.dp),
            thenModifier = Modifier.shimmerEffect()
        ) {}
    }
}

@Composable
fun MediumShapeBox(
    modifier: Modifier = Modifier,
    thenModifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit = {}
) = Box(
    modifier = modifier
        .clip(RoundedCornerShape(12.dp))
        .then(thenModifier),
    content = content
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CoffeeCatalogLoadingPreviewDark() {
    CoffeeHouseTheme {
        Surface {
            CoffeeCatalogLoading()
        }
    }
}

@Preview
@Composable
fun CoffeeCatalogLoadingPreviewLight() {
    CoffeeHouseTheme {
        Surface {
            CoffeeCatalogLoading()
        }
    }
}
