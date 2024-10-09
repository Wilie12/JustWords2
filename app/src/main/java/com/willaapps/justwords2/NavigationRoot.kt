package com.willaapps.justwords2

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import com.willaapps.shop.presentation.shop_book.ShopScreenRoot
import com.willaapps.shop.presentation.shop_set.ShopSetScreenRoot
import com.willaapps.user.presentation.edit_profile.EditProfileScreenRoot
import com.willaapps.user.presentation.profile.ProfileScreenRoot
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
        userGraph(navController)
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
        composable(
            route = "register",
            enterTransition = {
                slideInVertically(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    ),
                    initialOffsetY = { it / 2 }
                )
            },
            exitTransition = {
                slideOutVertically(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    ),
                    targetOffsetY = { it / 2 }
                )
            }
        ) {
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
        composable(
            route = "login",
            enterTransition = {
                slideInVertically(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    ),
                    initialOffsetY = { it / 2 }
                )
            },
            exitTransition = {
                slideOutVertically(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    ),
                    targetOffsetY = { it / 2 }
                )
            }
        ) {
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
        composable(
            route = "start",
            enterTransition = {
                fadeIn()
            }
        ) {
            StartScreenRoot(
                onUserClick = { navController.navigate("user") },
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
            ),
            enterTransition = {
                slideInVertically(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 450,
                        easing = LinearEasing
                    )
                )
            },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = {
                slideOutVertically(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 450,
                        easing = LinearEasing
                    )
                )
            }
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
            ),
            enterTransition = {
                scaleIn()
            },
            exitTransition = {
                scaleOut()
            }
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
        composable(
            route = "shopBooks",
            enterTransition = {
                slideInVertically(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 450,
                        easing = LinearEasing
                    )
                )
            },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = {
                slideOutVertically(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 450,
                        easing = LinearEasing
                    )
                )
            }
        ) {
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
            ),
            enterTransition = {
                slideInVertically(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 450,
                        easing = LinearEasing
                    )
                )
            },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = {
                slideOutVertically(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 450,
                        easing = LinearEasing
                    )
                )
            }
        ) {
            ShopSetScreenRoot(
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}

private fun NavGraphBuilder.userGraph(navController: NavHostController) {
    navigation(
        startDestination = "profile",
        route = "user"
    ) {
        composable(
            route = "profile",
            enterTransition = {
                slideInVertically(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 450,
                        easing = LinearEasing
                    )
                )
            },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = {
                slideOutVertically(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 450,
                        easing = LinearEasing
                    )
                )
            }
            ) {
            ProfileScreenRoot(
                onBackClick = { navController.navigateUp() },
                onEditProfileClick = { navController.navigate("editProfile") },
                onLogout = {
                    navController.navigate("auth") {
                        popUpTo("word") {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = "editProfile",
            enterTransition = {
                slideInVertically(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 450,
                        easing = LinearEasing
                    )
                )
            },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = {
                slideOutVertically(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 450,
                        easing = LinearEasing
                    )
                )
            }
        ) {
            EditProfileScreenRoot(
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}