package com.dantrap.devlife.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dantrap.devlife.R
import com.dantrap.devlife.MainViewModel
import com.dantrap.devlife.databinding.FragmentRandomBinding

class RandomFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentRandomBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomBinding.inflate(inflater, container, false)
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

        viewModel.currentRandomGif.observe(viewLifecycleOwner, {
            binding.descriptionTextView.text = it.description
            Glide.with(requireActivity())
                .load(it.gifURL)
                .error(R.drawable.ic_error_outline)
                .placeholder(circularProgress)
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                .into(binding.gifImageView)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}