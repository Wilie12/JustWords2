package com.willaapps.core.data.user

import com.willaapps.core.data.networking.get
import com.willaapps.core.data.networking.post
import com.willaapps.core.domain.user.UserInfo
import com.willaapps.core.domain.user.UserInfoRepository
import com.willaapps.core.domain.user.UserStorage
import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.EmptyResult
import com.willaapps.core.domain.util.Result
import com.willaapps.core.domain.util.asEmptyDataResult
import io.ktor.client.HttpClient

class KtorUserInfoRepository(
    private val httpClient: HttpClient,
    private val userStorage: UserStorage
): UserInfoRepository {
    override suspend fun getUserInfo(userId: String): EmptyResult<DataError.Network> {
        val result = httpClient.get<UserInfoResponse>(
            route = "/getUserInfo",
            queryParameters = mapOf(
                "userId" to userId
            )
        )

        when (result) {
            is Result.Success -> {
                userStorage.setUserInfo(result.data.userInfo.toUserInfo())
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