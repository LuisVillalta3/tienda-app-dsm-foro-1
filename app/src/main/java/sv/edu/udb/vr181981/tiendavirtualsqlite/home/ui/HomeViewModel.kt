package sv.edu.udb.vr181981.tiendavirtualsqlite.home.ui

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import sv.edu.udb.vr181981.tiendavirtualsqlite.MyApp
import sv.edu.udb.vr181981.tiendavirtualsqlite.db.SessionManager
import sv.edu.udb.vr181981.tiendavirtualsqlite.db.models.Product
import sv.edu.udb.vr181981.tiendavirtualsqlite.home.domain.HomeController
import sv.edu.udb.vr181981.tiendavirtualsqlite.login.data.LoginForm
import sv.edu.udb.vr181981.tiendavirtualsqlite.navigation.Routes

class HomeViewModel : ViewModel() {
    private val _productList = MutableStateFlow(mutableListOf<Product>())
    val productList: StateFlow<List<Product>> = _productList.asStateFlow()

    init {
        populateList()
    }

    private fun populateList() {
        val list = mutableListOf<Product>()
        HomeController.getAllProducts().forEach {
            list.add(it)
        }

        _productList.update { list }
    }

    fun logOut(navController: NavController) {
        val sessionManager = SessionManager(MyApp.instance.applicationContext)
        sessionManager.setLoggedIn(false)

        navController.navigate(Routes.LoginRoute) {
            launchSingleTop = true
            popUpTo(Routes.HomeRoute) { inclusive = true }
        }
    }
}