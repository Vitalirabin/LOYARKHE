package com.kheloyar.livegame.data.network


class KheloyarRepository() {

    suspend fun getLinkForAppKheloyar(url: String): Result<String> {
        if (url != "")
            return kotlin.runCatching {
                KheloyarApiFactory.getApiKheloyar().getLinkForAppKheloyar(url).string()
            }
        else if (url.length > 5)
            return kotlin.runCatching {
                KheloyarApiFactory.getApiKheloyar().getLinkForAppKheloyar(url).string()
            }
        else return kotlin.runCatching {
            KheloyarApiFactory.getApiKheloyar().getLinkForAppKheloyar(url).string()
        }
    }
}