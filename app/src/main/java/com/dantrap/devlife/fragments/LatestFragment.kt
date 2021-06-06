package com.dantrap.devlife.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.dantrap.devlife.R
import com.dantrap.devlife.databinding.FragmentLatestBinding
import com.dantrap.devlife.MainViewModel

class LatestFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentLatestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLatestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val circularProgress = CircularProgressDrawable(requireActivity())
        circularProgress.apply {
            setColorSchemeColors(R.color.purple_500)
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }

        viewModel.currentLatestGif.observe(viewLifecycleOwner, {
            binding.descriptionTextView.text = it.description
            Glide.with(requireActivity())
                .load(it.gifURL)
                .error(R.drawable.ic_error_outline)
                .placeholder(circularProgress)
                .into(binding.gifImageView)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}