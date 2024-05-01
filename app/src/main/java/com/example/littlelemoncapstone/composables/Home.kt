package com.example.littlelemoncapstone.composables

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.littlelemoncapstone.MenuItemRoom
import com.example.littlelemoncapstone.MenuViewModel
import com.example.littlelemoncapstone.R
import com.example.littlelemoncapstone.ui.theme.LittleLemonColor
import com.example.littlelemoncapstone.ui.theme.customShapes
import com.example.littlelemoncapstone.ui.theme.secondaryButton


@Composable
fun Home(navController: NavController) {
    HomePage(navController = navController)
}

@Composable
fun HomePage(navController: NavController) {

    val context = LocalContext.current
    val viewModel: MenuViewModel = viewModel()
    val databaseMenuItems = viewModel.getAllDatabaseMenuItems().observeAsState(emptyList()).value
    val searchPhrase = remember {
        mutableStateOf("")
    }
    val category = remember {
        mutableStateOf("")
    }

    var filteredMenuItems = if (searchPhrase.value.isBlank()) {
        databaseMenuItems
    } else {
        databaseMenuItems.filter { menuItem ->
            menuItem.title.contains(searchPhrase.value, ignoreCase = true)
        }
    }

    // Filter  by category
    filteredMenuItems = if (category.value.isBlank()) {
        filteredMenuItems
    } else {
        filteredMenuItems.filter { menuItem ->
            menuItem.category.contains(category.value, ignoreCase = true)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchMenuDataIfNeeded()
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo Image",
                    modifier = Modifier
                        .size(40.dp)
                        .weight(2f)
                        .align(Alignment.CenterVertically)
                )

                Image(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(60.dp)
                        .weight(1f)
                        .clickable {
                            navController.navigate("Profile")
                        }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = LittleLemonColor.green)
                    .padding(horizontal = 16.dp),
            ) {
                    Text(
                        text = stringResource(R.string.title),
                        style = MaterialTheme.typography.displayMedium,
                        color = LittleLemonColor.yellow,
                        modifier = Modifier.fillMaxWidth()
                    )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = LittleLemonColor.green)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top
            ) {

                // Left Column (Location, Description)
                Column(
                    modifier = Modifier.fillMaxWidth(0.6f),
                ) {

                    Text(
                        text = stringResource(R.string.location),
                        style = MaterialTheme.typography.titleLarge,
                        color = LittleLemonColor.cloud,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.description),
                        style = MaterialTheme.typography.bodyMedium,
                        color = LittleLemonColor.cloud,
                        modifier = Modifier
                            .padding(end = 8.dp),
                    )
                }


                Column(
                    verticalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(R.drawable.hero_image),
                        contentDescription = "Hero Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            //.fillMaxWidth(0.6f)
                            .size(120.dp)
                            .clip(shape = customShapes.medium),

                        )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = LittleLemonColor.green)
                    .padding(bottom = 16.dp)
                    .padding(horizontal = 8.dp)
            ) {
                TextField(
                    value = searchPhrase.value,
                    onValueChange = { searchPhrase.value = it },
                    placeholder = {
                        Text(
                            text = "Enter search phrase",
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(x = -20.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .clip(shape = customShapes.medium),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = ""
                        )
                    },
                    singleLine = true,
                    )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 30.dp)
            ) {
                Text(
                    text = stringResource(R.string.order_delivery).uppercase(),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = LittleLemonColor.charcoal
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround

            ) {
                Button(
                    onClick = { // Input validation logic
                        if (category.value == "starters") category.value = ""
                        else
                            category.value = "starters"
                    },
                    contentPadding = PaddingValues(5.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = LittleLemonColor.charcoal,
                        containerColor = if (category.value == "starters") LittleLemonColor.yellow else LittleLemonColor.cloud,
                    ),
                    modifier = Modifier
                        .padding(horizontal = 5.dp),


                    ) {
                    Text(
                        stringResource(R.string.starters),
                        style = secondaryButton
                    )
                }

                Button(
                    onClick = { // Input validation logic
                        if (category.value == "mains") category.value = ""
                        else
                            category.value = "mains"
                    },
                    contentPadding = PaddingValues(5.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = LittleLemonColor.charcoal,
                        containerColor = if (category.value == "mains") LittleLemonColor.yellow else LittleLemonColor.cloud,
                    ),
                    modifier = Modifier
                        .padding(horizontal = 5.dp),
                ) {
                    Text(
                        stringResource(R.string.mains),
                        style = secondaryButton
                    )
                }
                Button(
                    onClick = { // Input validation logic
                        if (category.value == "desserts") category.value = ""
                        else
                            category.value = "desserts"
                    },
                    contentPadding = PaddingValues(5.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = LittleLemonColor.charcoal,
                        containerColor = if (category.value == "desserts") LittleLemonColor.yellow else LittleLemonColor.cloud,
                    ),
                    modifier = Modifier
                        .padding(horizontal = 5.dp),
                ) {
                    Text(
                        stringResource(R.string.desserts),
                        style = secondaryButton
                    )
                }

                Button(
                    onClick = { // Input validation logic
                        if (category.value == "drinks") category.value = ""
                        else
                            category.value = "drinks"
                    },
                    contentPadding = PaddingValues(5.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = LittleLemonColor.charcoal,
                        containerColor = if (category.value == "drinks") LittleLemonColor.yellow else LittleLemonColor.cloud,
                    ),
                    modifier = Modifier
                        .padding(horizontal = 5.dp),
                ) {
                    Text(
                        stringResource(R.string.drinks),
                        style = secondaryButton
                    )
                }

            }
            Spacer(modifier = Modifier.width(16.dp))
            MenuItems(menuItems = filteredMenuItems, context)
        }
    }
}

@Composable
fun MenuItems(menuItems: List<MenuItemRoom>, context: Context) {
    Spacer(
        modifier = Modifier
            .width(20.dp)
            .padding(top = 10.dp, bottom = 10.dp)
    )
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
    ) {
        if(menuItems.isNotEmpty())
        for (menuItem in menuItems) {
            MenuItem(item = menuItem, context)
        }
        else
            Text(
                stringResource(R.string.notfound),
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
    }
}

@Composable
fun MenuItem(item: MenuItemRoom, context: Context) {
    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 5.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth().fillMaxHeight()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.fillMaxWidth(0.7f)) {
            Text(
                text = item.title, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = item.description,
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(text = "$  ${item.price}", style = MaterialTheme.typography.bodyMedium)
        }
        Column( verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight()) {
            AsyncImage(
                model = item.image,
                contentDescription = null,
                modifier = Modifier.size(100.dp, 100.dp),
                contentScale = ContentScale.Crop,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Home(rememberNavController())
}