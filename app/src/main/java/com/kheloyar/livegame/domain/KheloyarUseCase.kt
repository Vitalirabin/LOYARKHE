package com.kheloyar.livegame.domain

import com.kheloyar.livegame.data.network.KheloyarRepository


class KheloyarUseCase(private val repository: KheloyarRepository) {

    suspend fun getLinkForAppKheloyar(url: String): Result<String> {
        if (url != "")
            return repository.getLinkForAppKheloyar(url)
                .map {
                    val result = it.substring(it.indexOf("https:"))
                    result.substring(0, result.indexOf("</body>"))
                }
        else if (url.length > 5)
            return repository.getLinkForAppKheloyar(url).map {
                val result = it.substring(it.indexOf("https:"))
                result.substring(0, result.indexOf("</body>"))
            }
        else return repository.getLinkForAppKheloyar(url).map {
            val result = it.substring(it.indexOf("https:"))
            result.substring(0, result.indexOf("</body>"))
        }
    }
}