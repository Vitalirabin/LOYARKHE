package com.kheloyar.livegame.domain

import com.kheloyar.livegame.data.network.KheloyarRepository

class KheloyarUseCase {

    suspend fun getLinkForAppKheloyar(url: String): Result<String> {
        if (url != "")
            return KheloyarRepository().getLinkForAppKheloyar(url)
        else if (url.length > 5)
            return KheloyarRepository().getLinkForAppKheloyar(url)
        else return KheloyarRepository().getLinkForAppKheloyar(url)
    }
}