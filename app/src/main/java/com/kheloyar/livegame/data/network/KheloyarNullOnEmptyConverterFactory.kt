package com.kheloyar.livegame.data.network

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


class KheloyarNullOnEmptyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        typeKheloyar: Type,
        annotationsKheloyar: Array<Annotation>,
        retrofitKheloyar: Retrofit
    ): Converter<ResponseBody, *> {
        val delegate: Converter<ResponseBody, *> =
            retrofitKheloyar.nextResponseBodyConverter<Any>(this, typeKheloyar, annotationsKheloyar)
        return Converter { body -> if (body.contentLength() == 0L) "empty body" else delegate.convert(body) }
    }
}