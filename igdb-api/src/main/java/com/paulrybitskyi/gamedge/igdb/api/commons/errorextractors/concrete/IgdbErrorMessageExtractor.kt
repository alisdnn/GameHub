/*
 * Copyright 2020 Paul Rybitskyi, paul.rybitskyi.work@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.paulrybitskyi.gamedge.igdb.api.commons.errorextractors.concrete

import com.paulrybitskyi.hiltbinder.BindType
import com.paulrybitskyi.gamedge.igdb.api.commons.di.qualifiers.ErrorMessageExtractorKey
import com.paulrybitskyi.gamedge.igdb.api.commons.errorextractors.ErrorMessageExtractor
import okhttp3.ResponseBody
import org.json.JSONArray
import javax.inject.Inject


private const val ERROR_MESSAGE_NAME = "title"


@ErrorMessageExtractorKey(ErrorMessageExtractorKey.Type.IGDB)
@BindType(withQualifier = true)
internal class IgdbErrorMessageExtractor @Inject constructor() : ErrorMessageExtractor {


    override fun extract(responseBody: ResponseBody): String {
        val jsonArray = JSONArray(responseBody.string())
        val jsonObject = jsonArray.getJSONObject(0)
        val message = jsonObject.getString(ERROR_MESSAGE_NAME)

        return message
    }


}