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

package com.simplaapliko.challenge2.ui.deal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.simplaapliko.challenge2.R
import com.simplaapliko.challenge2.di.ApplicationComponent
import com.simplaapliko.challenge2.domain.model.Deal
import com.simplaapliko.challenge2.domain.model.DealFull
import com.simplaapliko.challenge2.domain.model.Flight
import com.simplaapliko.challenge2.ui.base.BaseActivity
import com.simplaapliko.challenge2.widget.FlightDetailsView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_deal.*
import javax.inject.Inject

class DealActivity : BaseActivity(), DealContract.View {

    companion object {
        private const val EXTRA_DEAL: String = "simplaapliko.extra.DEAL"

        fun getStartIntent(context: Context, model: Deal): Intent {
            val intent = Intent(context, DealActivity::class.java)
            // ideally model needs to be parcelable
            intent.putExtra(EXTRA_DEAL, model)
            return intent
        }
    }

    @Inject
    lateinit var presenter: DealContract.Presenter

    override val contentView: Int
        get() = R.layout.activity_deal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }

        bindViews()

        presenter.init()
    }

    private fun bindViews() {

    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun injectDependencies(applicationComponent: ApplicationComponent) {
        val deal = intent.getSerializableExtra(EXTRA_DEAL) as Deal
        applicationComponent.plus(DealComponent.Module(this, deal)).inject(this)
    }

    override fun hideProgress() {
    }

    override fun showProgress() {
    }

    override fun displayDeal(deal: DealFull) {
        price.setCurrency(deal.currency.symbol)
        price.setPriceText(deal.price)

        outbound.airline = deal.flights.outbound.airline.name

        displayFlight(outbound, deal.flights.outbound)
        displayFlight(inbound, deal.flights.inbound)

        lodging.nights = deal.lodging.nights
        lodging.rating = deal.lodging.hotel.rating
        lodging.setHotel(deal.lodging.hotel.name)

        val imagePath = deal.lodging.hotel.imageUrl
        if (imagePath.isNotBlank()) {
            Picasso.get()
                .load(imagePath)
                .placeholder(R.drawable.ic_airplane_accent)
                .error(R.drawable.ic_airplane_accent)
                .resize(R.dimen.deal_details_airline_logo_size, R.dimen.deal_details_airline_logo_size)
                .onlyScaleDown()
                .centerCrop()
                .into(lodging.hotelLogoImageView)
        }
    }

    private fun displayFlight(view: FlightDetailsView, flight: Flight) {

        val flightInfo = String.format("%s - %s", flight.start.airport.id, flight.end.airport.id)
        view.flightInfo = flightInfo

        view.airline = flight.airline.name

        val imagePath = flight.airline.imageUrl
        if (imagePath.isNotBlank()) {
            Picasso.get()
                .load(imagePath)
                .placeholder(R.drawable.ic_airplane_accent)
                .error(R.drawable.ic_airplane_accent)
                .resize(R.dimen.deal_details_airline_logo_size, R.dimen.deal_details_airline_logo_size)
                .onlyScaleDown()
                .centerCrop()
                .into(view.airlineLogoImageView)
        }

        view.departureDateTime = flight.start.datetime
        view.departureAirport = flight.start.airport.name
        view.departureCity = flight.start.airport.city
        view.arrivalDateTime = flight.end.datetime
        view.arrivalAirport = flight.end.airport.name
        view.arrivalCity = flight.end.airport.city
    }

    override fun showMessage(message: String) {
    }
}
