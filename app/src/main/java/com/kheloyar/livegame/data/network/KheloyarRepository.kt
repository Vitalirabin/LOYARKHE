package com.kheloyar.livegame.data.network


class KheloyarRepository(private val api: KheloyarRemoteApi) {

    suspend fun getLinkForAppKheloyar(url: String): Result<String> {
        if (url != "")
            return kotlin.runCatching {
                api.getLinkForAppKheloyar(url).string()
            }
        else if (url.length > 5)
            return kotlin.runCatching {
                api.getLinkForAppKheloyar(url).string()
            }
        else return kotlin.runCatching {
            api.getLinkForAppKheloyar(url).string()
        }
    }
}