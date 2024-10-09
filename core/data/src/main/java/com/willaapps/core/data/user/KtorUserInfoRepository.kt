package com.willaapps.core.data.user

import com.willaapps.core.data.networking.get
import com.willaapps.core.data.networking.post
import com.willaapps.core.data.user.dto.UserInfoRequest
import com.willaapps.core.data.user.dto.UserInfoResponse
import com.willaapps.core.data.user.dto.toUserInfo
import com.willaapps.core.data.user.dto.toUserInfoSerializable
import com.willaapps.core.domain.auth.SessionStorage
import com.willaapps.core.domain.user.UserInfo
import com.willaapps.core.domain.user.UserInfoRepository
import com.willaapps.core.domain.user.UserStorage
import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.EmptyResult
import com.willaapps.core.domain.util.Result
import com.willaapps.core.domain.util.asEmptyDataResult
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.catch
import timber.log.Timber
import java.time.ZoneId

class KtorUserInfoRepository(
    private val httpClient: HttpClient,
    private val userStorage: UserStorage,
    private val sessionStorage: SessionStorage
) : UserInfoRepository {
    override suspend fun getUserInfo(userId: String): EmptyResult<DataError.Network> {
        val result = httpClient.get<UserInfoResponse>(
            route = "/getUserInfo",
            queryParameters = mapOf(
                "userId" to userId
            )
        )

        when (result) {
            is Result.Success -> {
                userStorage.get()
                    .catch {
                        Timber.d(it)
                    }
                    .collect { userInfo ->
                        if (userInfo != null) {
                            if (userInfo.userId != sessionStorage.get()?.userId) {
                                userStorage.setUserInfo(result.data.userInfo.toUserInfo())
                                return@collect
                            }
                            if ((result.data.userInfo.toUserInfo().lastPlayedTimestamp
                                    .withZoneSameInstant(ZoneId.of("UTC")).isBefore(
                                        userInfo.lastPlayedTimestamp
                                            .withZoneSameInstant(ZoneId.of("UTC"))
                                    ))
                                || (result.data.userInfo.toUserInfo().lastEditedTimestamp
                                    .withZoneSameInstant(ZoneId.of("UTC")).isBefore(
                                        userInfo.lastEditedTimestamp
                                            .withZoneSameInstant(ZoneId.of("UTC"))
                                    ))

                            ) {
                                setUserInfo(userInfo)
                                return@collect
                            }
                            if ((result.data.userInfo.toUserInfo().lastPlayedTimestamp
                                    .withZoneSameInstant(ZoneId.of("UTC")).isEqual(
                                        userInfo.lastPlayedTimestamp
                                            .withZoneSameInstant(ZoneId.of("UTC"))
                                    ))
                                || (result.data.userInfo.toUserInfo().lastEditedTimestamp
                                    .withZoneSameInstant(ZoneId.of("UTC")).isEqual(
                                        userInfo.lastEditedTimestamp
                                            .withZoneSameInstant(ZoneId.of("UTC"))
                                    ))
                            ) {
                                return@collect
                            }
                        }
                        userStorage.setUserInfo(result.data.userInfo.toUserInfo())
                    }
            }
            else -> Unit
        }
        return result.asEmptyDataResult()
    }

    override suspend fun setUserInfo(userInfo: UserInfo): EmptyResult<DataError.Network> {
        return httpClient.post<UserInfoRequest, Unit>(
            route = "/updateUserInfo",
            body = UserInfoRequest(
                userInfo = userInfo.toUserInfoSerializable()
            )
        )
    }
}