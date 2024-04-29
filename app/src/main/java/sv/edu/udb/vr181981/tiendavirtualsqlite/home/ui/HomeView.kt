@file:OptIn(ExperimentalMaterial3Api::class)

package sv.edu.udb.vr181981.tiendavirtualsqlite.home.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import sv.edu.udb.vr181981.tiendavirtualsqlite.ui.theme.Orange
import sv.edu.udb.vr181981.tiendavirtualsqlite.ui.theme.poppinsFamily
import androidx.lifecycle.viewmodel.compose.viewModel
import sv.edu.udb.vr181981.tiendavirtualsqlite.db.models.Product
import sv.edu.udb.vr181981.tiendavirtualsqlite.ui.components.CustomSpacer

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeView(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    val productList by viewModel.productList.collectAsState()

    Scaffold(
        topBar = { TopBar(viewModel = viewModel, navController = navController) },
        floatingActionButton = { Fab() },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = Color.White,
    ) {
        CustomSpacer(height = 100.dp)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp)
        ) {
            items(items = productList) {
                ProductCard(product = it)
                CustomSpacer(height = 15.dp)
            }
        }
    }
}

@Composable
private fun TopBar(navController: NavController, viewModel: HomeViewModel) {
    TopAppBar(
        title = {
            Text(
                text = "TechStorm", fontFamily = poppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp, color = Color.White
            )
        },
        actions = {
            IconButton(onClick = { viewModel.logOut(navController) }) {
                Icon(Icons.Filled.ExitToApp, contentDescription = "Logout", tint = Color.White)
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Orange),
    )
}

@Composable
private fun Fab() {
    FloatingActionButton(
        onClick = { },
        containerColor = Orange,
        contentColor = Color.White,
        elevation = FloatingActionButtonDefaults.elevation(8.dp),
        shape = RoundedCornerShape(percent = 50)
    ) {
        Icon(Icons.Default.ShoppingCart, contentDescription = "Shopping Cart")
    }
}

@Composable
private fun ProductCard(product: Product) {
    Column(
        modifier = Modifier
            .shadow(elevation = 10.dp)
            .fillMaxWidth()
            .background(Color.White)
            .padding(15.dp),
    ) {
        Text(
            text = product.name, fontFamily = poppinsFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp, color = Orange
        )
        Text(
            text = "Precio: $${product.price}", fontFamily = poppinsFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp, color = Color.Gray
        )
    }
}