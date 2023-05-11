package com.sablot.marsservice.overview

/*
 * Copyright 2019, The Android Open Source Project
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
 *
 */


import android.os.Bundle
import android.view.*
import android.view.View.OnClickListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sablot.marsservice.R
import com.sablot.marsservice.databinding.FragmentOverviewBinding
import com.sablot.marsservice.network.Type

class OverviewFragment : Fragment(), OnClickListener {

    private val viewModel: OverviewViewModel by lazy { ViewModelProvider(this)[OverviewViewModel::class.java] }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentOverviewBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.photosGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener{
            viewModel.displayPropertyDetails(it)
        })
        binding.btnAll.setOnClickListener(this)
        binding.btnBuy.setOnClickListener(this)
        binding.btnRent.setOnClickListener(this)

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                this.findNavController().navigate(
                    OverviewFragmentDirections.actionShowDetail(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_all -> viewModel.updateFilter(Type.ALL)
            R.id.btn_buy -> viewModel.updateFilter(Type.BUY)
            R.id.btn_rent -> viewModel.updateFilter(Type.RENT)
        }
    }
}
