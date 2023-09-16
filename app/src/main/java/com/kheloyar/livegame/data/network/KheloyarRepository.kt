package com.kheloyar.livegame.data.network


class KheloyarRepository(private val apiKheloyar: KheloyarRemoteApi) {

    suspend fun getLinkForAppKheloyar(urlKheloyar: String): Result<String> {
        return if (urlKheloyar != "")
            kotlin.runCatching {
                apiKheloyar.getLinkForAppKheloyar(urlKheloyar).string()
            }
        else if (urlKheloyar.length > 5)
            kotlin.runCatching {
                apiKheloyar.getLinkForAppKheloyar(urlKheloyar).string()
            }
        else kotlin.runCatching {
            apiKheloyar.getLinkForAppKheloyar(urlKheloyar).string()
        }
    }
}