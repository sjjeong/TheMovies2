/*
 * Designed and developed by 2019 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skydoves.themovies2.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.skydoves.themovies2.models.Review

open class ReviewListConverter {
  @TypeConverter
  fun fromString(value: String): List<Review>? {
    val listType = object : TypeToken<List<Review>>() {}.type
    return Gson().fromJson<List<Review>>(value, listType)
  }

  @TypeConverter
  fun fromList(list: List<Review>?): String {
    val gson = Gson()
    return gson.toJson(list)
  }
}
