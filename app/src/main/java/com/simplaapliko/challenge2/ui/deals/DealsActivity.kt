/*
 * Copyright (C) 2018 Oleg Kan
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

package com.simplaapliko.challenge2.ui.deals

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.simplaapliko.challenge2.R
import com.simplaapliko.challenge2.di.ApplicationComponent
import com.simplaapliko.challenge2.domain.model.Deal
import com.simplaapliko.challenge2.ui.base.BaseActivity
import com.simplaapliko.challenge2.ui.deals.adapter.DealAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_deals.*
import javax.inject.Inject

class DealsActivity : BaseActivity(), DealsContract.View {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, DealsActivity::class.java)
        }
    }

    @Inject
    lateinit var presenter: DealsContract.Presenter

    private lateinit var adapter: DealAdapter

    override val contentView: Int
        get() = R.layout.activity_deals

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViews()

        presenter.init()
    }

    private fun bindViews() {
        adapter = DealAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun injectDependencies(applicationComponent: ApplicationComponent) {
        applicationComponent.plus(DealsComponent.Module(this)).inject(this)
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun setEmptyMessageVisibility(visible: Boolean?) {
    }

    override fun displayDeals(items: List<Deal>) {
        adapter.setItems(items)
        adapter.notifyDataSetChanged();
    }

    override fun showMessage(message: String) {
    }

    override fun onDealClick(): Observable<Deal> {
        return adapter.clickObservable;
    }
}
