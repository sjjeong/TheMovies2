/*
 * The MIT License (MIT)
 *
 * Designed and developed by 2019 skydoves (Jaewoong Eum)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.skydoves.themovies2.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.skydoves.themovies2.api.RequestInterceptor
import com.skydoves.themovies2.api.client.MovieClient
import com.skydoves.themovies2.api.client.PeopleClient
import com.skydoves.themovies2.api.client.TheDiscoverClient
import com.skydoves.themovies2.api.client.TvClient
import com.skydoves.themovies2.api.service.MovieService
import com.skydoves.themovies2.api.service.PeopleService
import com.skydoves.themovies2.api.service.TheDiscoverService
import com.skydoves.themovies2.api.service.TvService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
  single {
    OkHttpClient.Builder()
      .addInterceptor(RequestInterceptor())
      .addNetworkInterceptor(StethoInterceptor())
      .build()
  }

  single {
    Retrofit.Builder()
      .client(get<OkHttpClient>())
      .baseUrl("https://api.themoviedb.org/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  single(createdAtStart = false) {
    get<Retrofit>().create(TheDiscoverService::class.java)
  }

  single(createdAtStart = false) {
    get<Retrofit>().create(PeopleService::class.java)
  }

  single(createdAtStart = false) {
    get<Retrofit>().create(MovieService::class.java)
  }

  single(createdAtStart = false) {
    get<Retrofit>().create(TvService::class.java)
  }

  single(createdAtStart = false) { TheDiscoverClient(get()) }

  single(createdAtStart = false) { PeopleClient(get()) }

  single(createdAtStart = false) { MovieClient(get()) }

  single(createdAtStart = false) { TvClient(get()) }
}
