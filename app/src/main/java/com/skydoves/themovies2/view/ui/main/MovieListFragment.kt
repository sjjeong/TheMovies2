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

package com.skydoves.themovies2.view.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.skydoves.themovies2.R
import com.skydoves.themovies2.compose.ViewModelFragment
import com.skydoves.themovies2.databinding.MainFragmentMovieBinding
import com.skydoves.themovies2.models.entity.Movie
import com.skydoves.themovies2.view.adapter.MovieListAdapter
import com.skydoves.themovies2.view.ui.details.movie.MovieDetailActivity
import com.skydoves.themovies2.view.viewholder.MovieListViewHolder
import kotlinx.android.synthetic.main.main_fragment_movie.*
import org.jetbrains.anko.support.v4.toast
import org.koin.android.viewmodel.ext.android.viewModel

class MovieListFragment : ViewModelFragment(), MovieListViewHolder.Delegate {

  private val viewModel by viewModel<MainActivityViewModel>()
  private lateinit var binding: MainFragmentMovieBinding

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = binding(inflater, R.layout.main_fragment_movie, container)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initializeUI()
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    loadMore(page = 1)
    observeMessages()
  }

  private fun initializeUI() {
    recyclerView.adapter = MovieListAdapter(this)
    recyclerView.layoutManager = GridLayoutManager(context, 2)
    RecyclerViewPaginator(
      recyclerView = recyclerView,
      isLoading = { false },
      loadMore = { loadMore(it) },
      onLast = { false }
    ).apply {
      threshold = 4
      currentPage = 1
    }
  }

  private fun loadMore(page: Int) = viewModel.postMoviePage(page)

  override fun onItemClick(movie: Movie) =
    MovieDetailActivity.startActivityModel(context, movie)

  private fun observeMessages() =
    this.viewModel.toastLiveData.observe(this) { toast(it) }
}
