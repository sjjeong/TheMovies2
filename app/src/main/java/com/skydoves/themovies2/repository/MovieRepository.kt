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

package com.skydoves.themovies2.repository

import androidx.lifecycle.MutableLiveData
import com.skydoves.themovies2.api.ApiResponse
import com.skydoves.themovies2.api.client.MovieClient
import com.skydoves.themovies2.api.message
import com.skydoves.themovies2.models.Keyword
import com.skydoves.themovies2.models.Review
import com.skydoves.themovies2.models.Video
import com.skydoves.themovies2.room.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class MovieRepository constructor(
  private val movieClient: MovieClient,
  private val movieDao: MovieDao
) : Repository {

  init {
    Timber.d("Injection MovieRepository")
  }

  suspend fun loadKeywordList(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<List<Keyword>>()
    movieClient.fetchKeywords(id) { response ->
      when (response) {
        is ApiResponse.Success -> {
          response.data?.let { data ->
            val movie = movieDao.getMovie(id)
            movie.keywords = data.keywords
            liveData.postValue(data.keywords)
            movieDao.updateMovie(movie)
          }
        }
        is ApiResponse.Failure.Error -> error(response.message())
        is ApiResponse.Failure.Exception -> error(response.message())
      }
    }
    liveData
  }

  suspend fun loadVideoList(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<List<Video>>()
    movieClient.fetchVideos(id) { response ->
      when (response) {
        is ApiResponse.Success -> {
          response.data?.let { data ->
            val movie = movieDao.getMovie(id)
            movie.videos = data.results
            liveData.postValue(data.results)
            movieDao.updateMovie(movie)
          }
        }
        is ApiResponse.Failure.Error -> error(response.message())
        is ApiResponse.Failure.Exception -> error(response.message())
      }
    }
    liveData
  }

  suspend fun loadReviewsList(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<List<Review>>()
    movieClient.fetchReviews(id) { response ->
      when (response) {
        is ApiResponse.Success -> {
          response.data?.let { data ->
            val movie = movieDao.getMovie(id)
            movie.reviews = data.results
            liveData.postValue(data.results)
            movieDao.updateMovie(movie)
          }
        }
        is ApiResponse.Failure.Error -> error(response.message())
        is ApiResponse.Failure.Exception -> error(response.message())
      }
    }
    liveData
  }
}
