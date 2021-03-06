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
import com.dantrap.devlife.databinding.FragmentHotBinding
import com.dantrap.devlife.MainViewModel

class HotFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentHotBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotBinding.inflate(inflater, container, false)

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

        viewModel.currentHotGif.observe(viewLifecycleOwner, {
            binding.descriptionTextView.text = it.description
            Glide.with(requireActivity())
                .load(it.gifURL)
                .error(R.drawable.ic_error_outline)
                .placeholder(circularProgress)
                .into(binding.gifImageView)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}