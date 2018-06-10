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

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.simplaapliko.challenge2.R
import com.simplaapliko.challenge2.domain.model.Deal
import com.simplaapliko.challenge2.widget.LodgingView
import com.simplaapliko.challenge2.widget.PriceView
import com.simplaapliko.challenge2.widget.TripDetailsView
import com.squareup.picasso.Picasso

class DealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {

    private lateinit var context: Context
    private lateinit var clickListener: ClickListener
    private var lodgingView: LodgingView
    private var priceView: PriceView
    private var tripDetailsView: TripDetailsView

    constructor(context: Context, itemView: View, clickListener: ClickListener): this(itemView) {
        this.context = context
        this.clickListener = clickListener
    }

    interface ClickListener {
        fun onItemClicked(position: Int)
    }

    init {
        itemView.setOnClickListener(this)

        lodgingView = itemView.findViewById(R.id.lodging)
        priceView = itemView.findViewById(R.id.price)
        tripDetailsView = itemView.findViewById(R.id.trip_details)
    }

    override fun onClick(v: View) {
        clickListener.onItemClicked(adapterPosition)
    }

    fun bind(model: Deal) {

        lodgingView.setNights(model.lodging.nights)

        priceView.setCurrency(model.currency.symbol)
        priceView.setPriceText(model.price)

        val flights = String.format("%s - %s - %s", model.outboundStartAirportId,
            model.outboundEndAirportId, model.inboundEndAirportId)

        tripDetailsView.setFlights(flights)
        tripDetailsView.departureDateTime = model.outboundStartDate
        tripDetailsView.returnDateTime = model.inboundEndDate

        val imagePath = model.lodging.hotel.imageUrl
        if (imagePath.isNotBlank()) {
            Picasso.get()
                .load(imagePath)
                .placeholder(R.drawable.ic_hotel_accent)
                .error(R.drawable.ic_hotel_accent)
                .resize(R.dimen.list_item_image_size, R.dimen.list_item_image_size)
                .onlyScaleDown()
                .centerCrop()
                .into(lodgingView.imageView)
        }
    }
}
