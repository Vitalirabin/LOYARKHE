package com.kheloyar.livegame.domain

import com.kheloyar.livegame.data.network.KheloyarRepository


class KheloyarUseCase(private val repositoryKheloyar: KheloyarRepository) {

    suspend fun getLinkForAppKheloyar(urlKheloyar: String): Result<String> {
        if (urlKheloyar != "")
            return repositoryKheloyar.getLinkForAppKheloyar(urlKheloyar)
                .map {
                    if (!it.contains("https:")) return@map ""
                    val resultKheloyar = it.substring(it.indexOf("https:"))
                    resultKheloyar.substring(0, resultKheloyar.indexOf("</body>"))
                }
        else if (urlKheloyar.length > 5)
            return repositoryKheloyar.getLinkForAppKheloyar(urlKheloyar).map {
                if (!it.contains("https:")) return@map ""
                val resultKheloyar = it.substring(it.indexOf("https:"))
                resultKheloyar.substring(0, resultKheloyar.indexOf("</body>"))
            }
        else return repositoryKheloyar.getLinkForAppKheloyar(urlKheloyar).map {
            if (!it.contains("https:")) return@map ""
            val resultKheloyar = it.substring(it.indexOf("https:"))
            resultKheloyar.substring(0, resultKheloyar.indexOf("</body>"))
        }
    }
}