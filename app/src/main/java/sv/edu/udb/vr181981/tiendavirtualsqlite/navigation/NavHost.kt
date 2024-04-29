package sv.edu.udb.vr181981.tiendavirtualsqlite.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import sv.edu.udb.vr181981.tiendavirtualsqlite.MyApp
import sv.edu.udb.vr181981.tiendavirtualsqlite.db.SessionManager
import sv.edu.udb.vr181981.tiendavirtualsqlite.home.ui.HomeView
import sv.edu.udb.vr181981.tiendavirtualsqlite.login.ui.LoginView
import sv.edu.udb.vr181981.tiendavirtualsqlite.register.ui.RegisterView

@Composable
fun NavHost() {
    val navController = rememberNavController()

    val sessionManager = SessionManager(MyApp.instance.applicationContext)

    val startDestination = if (sessionManager.isLoggedIn()) Routes.HomeRoute else Routes.LoginRoute

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.LoginRoute) {
            LoginView(navController)
        }

        composable(Routes.RegisterRoute) {
            RegisterView(navController)
        }

        composable(Routes.HomeRoute) {
            HomeView(navController = navController)
        }
    }
}