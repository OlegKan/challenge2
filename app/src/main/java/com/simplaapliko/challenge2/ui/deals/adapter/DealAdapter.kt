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

package com.simplaapliko.challenge2.ui.deals.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.simplaapliko.challenge2.R
import com.simplaapliko.challenge2.domain.model.Deal
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class DealAdapter : RecyclerView.Adapter<DealViewHolder>() {

    private val clickListener = object : DealViewHolder.ClickListener {
        override fun onItemClicked(position: Int) {
            onClickSubject.onNext(items[position])
        }
    }

    private var items: MutableList<Deal> = ArrayList()

    private val onClickSubject = PublishSubject.create<Deal>()
    private val onItemsChangeSubject = PublishSubject.create<Int>()

    val clickObservable: Observable<Deal>
        get() = onClickSubject

    val onItemsChangeObservable: Observable<Int>
        get() = onItemsChangeSubject.delay(50, TimeUnit.MILLISECONDS)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_deal, parent, false)
        return DealViewHolder(parent.context, view, clickListener)
    }

    override fun onBindViewHolder(holder: DealViewHolder, position: Int) {
        val model = items[position]
        holder.bind(model)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItems(): List<Deal> {
        return ArrayList(items)
    }

    fun setItems(items: List<Deal>) {
        this.items = ArrayList(items)
        notifyItemChangeListener()
    }

    fun addItem(item: Deal) {
        items.add(item)
        val position = items.size - 1
        notifyItemInserted(position)
        notifyItemChangeListener()
    }

    fun deleteItem(item: Deal) {
        if (items.contains(item)) {
            val index = items.indexOf(item)
            items.removeAt(index)
            notifyItemRemoved(index)
        }
        notifyItemChangeListener()
    }

    fun updateItem(item: Deal) {
        if (items.contains(item)) {
            val index = items.indexOf(item)
            items.removeAt(index)
            items.add(index, item)
            notifyItemChanged(index, item)
        }
    }

    private fun notifyItemChangeListener() {
        onItemsChangeSubject.onNext(itemCount)
    }
}
