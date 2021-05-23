package com.gprosper.stridetest.ui.strides

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.gprosper.stridetest.MainActivity
import com.gprosper.stridetest.R
import com.gprosper.stridetest.databinding.FragmentStridesBinding

class StridesFragment : Fragment() {
    private val viewModel: StridesViewModel by viewModels()
    private lateinit var binding: FragmentStridesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStridesBinding.inflate(inflater, container, false)

        setupColors()

        binding.webView.setWebViewClient(WebViewClient())
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(viewModel.startingUrl)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.browser_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.back -> {
                binding.webView.goBack()
                true
            }
            R.id.refresh -> {
                binding.webView.reload()
                true
            }
            R.id.forward -> {
                binding.webView.goForward()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setStatusBarColor(color: Int) {
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        requireActivity().window.statusBarColor = color
    }

    private fun setupColors() {
        val greenColor = Color.parseColor("#66E0B9")
        val colorDrawable = ColorDrawable(greenColor)
        (requireActivity() as MainActivity).supportActionBar?.setBackgroundDrawable(colorDrawable)
        setStatusBarColor(color = greenColor)
    }
}