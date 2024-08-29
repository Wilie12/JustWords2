package com.willaapps.justwords2

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.willaapps.auth.presentation.intro.IntroScreenRoot
import com.willaapps.auth.presentation.login.LoginScreenRoot
import com.willaapps.auth.presentation.register.RegisterScreenRoot
import com.willaapps.shop.presentation.shop.ShopScreenRoot
import com.willaapps.shop.presentation.shop_set.ShopSetScreenRoot
import com.willaapps.word.presentation.set_list.SetListScreenRoot
import com.willaapps.word.presentation.start.StartScreenRoot
import com.willaapps.word.presentation.word.WordScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "word" else "auth"
    ) {
        authGraph(navController)
        wordGraph(navController)
        shopGraph(navController)
    }
}
// TODO - animations between screens
// TODO - check auth 401 response - Login
private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(
        startDestination = "intro",
        route = "auth"
    ) {
        composable(route = "intro") {
            IntroScreenRoot(
                onSignUpClick = {
                    navController.navigate("register")
                },
                onSignInClick = {
                    navController.navigate("login")
                }
            )
        }
        composable(route = "register") {
            RegisterScreenRoot(
                onSignInClick = {
                    navController.navigate("login") {
                        popUpTo("register") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onBackClick = {
                    navController.navigateUp()
                },
                onSuccessfulRegistration = {
                    navController.navigate("login") {
                        popUpTo("register") {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = "login") {
            LoginScreenRoot(
                onBackClick = {
                    navController.navigateUp()
                },
                onSignUpClick = {
                    navController.navigate("register") {
                        popUpTo("login") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onLoginSuccess = {
                    navController.navigate("word") {
                        popUpTo("auth") {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

private fun NavGraphBuilder.wordGraph(navController: NavHostController) {
    navigation(
        startDestination = "start",
        route = "word"
    ) {
        composable("start") {
            StartScreenRoot(
                onUserClick = { /*TODO*/ },
                onShopClick = { navController.navigate("shop") },
                onBookClick = { bookId ->
                    navController.navigate("setList/$bookId")
                },
                onPreviousClick = { bookId, bookColor, setId, groupNumber ->
                    navController.navigate("words/$bookId/$bookColor/$setId/$groupNumber")
                }
            )
        }
        composable(
            route = "setList/{bookId}",
            arguments = listOf(
                navArgument(name = "bookId") {
                    type = NavType.StringType
                }
            )
        ) {
            SetListScreenRoot(
                onBackClick = {
                    navController.navigateUp()
                },
                onSelectedGroupClick = { bookId, bookColor, setId, groupNumber ->
                    navController.navigate("words/$bookId/$bookColor/$setId/$groupNumber")
                }
            )
        }
        composable(
            route = "words/{bookId}/{bookColor}/{setId}/{groupNumber}",
            arguments = listOf(
                navArgument(name = "bookId") {
                    type = NavType.StringType
                },
                navArgument(name = "bookColor") {
                    type = NavType.IntType
                },
                navArgument(name = "setId") {
                    type = NavType.StringType
                },
                navArgument(name = "groupNumber") {
                    type = NavType.IntType
                },
            )
        ) {
            WordScreenRoot(
                onBackClick = {
                    navController.navigateUp()
                },
                onFinishClick = { bookId ->
                    navController.navigate("setList/$bookId") {
                        popUpTo("start")
                    }
                }
            )
        }
    }
}

private fun NavGraphBuilder.shopGraph(navController: NavHostController) {
    navigation(
        startDestination = "shopBooks",
        route = "shop"
    ) {
        composable("shopBooks") {
            ShopScreenRoot(
                onBackClick = { navController.navigateUp() },
                onShopBookClick = { bookId ->
                    navController.navigate("shopWordSets/$bookId")
                }
            )
        }
        composable(
            route = "shopWordSets/{bookId}",
            arguments = listOf(
                navArgument(name = "bookId") {
                    type = NavType.StringType
                }
            )
        ) {
            ShopSetScreenRoot(
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}