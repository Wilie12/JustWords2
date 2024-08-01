package com.willaapps.justwords2

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.willaapps.auth.presentation.intro.IntroScreenRoot
import com.willaapps.auth.presentation.login.LoginScreenRoot
import com.willaapps.auth.presentation.register.RegisterScreenRoot
import com.willaapps.word.presentation.start.StartScreenRoot

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
    }
}

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
                onShopClick = { /*TODO*/ },
                onBookClick = { bookId ->
                    // TODO
                }
            )
        }
    }
}