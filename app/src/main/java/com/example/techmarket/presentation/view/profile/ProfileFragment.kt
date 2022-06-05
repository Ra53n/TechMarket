package com.example.techmarket.presentation.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.techmarket.APP_SCOPE
import com.example.techmarket.App
import com.example.techmarket.R
import com.example.techmarket.databinding.ProfileFragmentBinding
import com.example.techmarket.presentation.presenter.ProfilePresenter
import com.example.techmarket.presentation.view.base.BaseFragment
import toothpick.Toothpick

const val PROFILE_SCOPE = "PROFILE_SCOPE"

class ProfileFragment : BaseFragment(), ProfileView {
    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ProfileFragment()
    }

    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    @ProvidePresenter
    fun provideProfilePresenter(): ProfilePresenter =
        Toothpick.openScopes(APP_SCOPE, PROFILE_SCOPE)
            .getInstance(ProfilePresenter::class.java)
            .also { Toothpick.closeScope(PROFILE_SCOPE) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Object()
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileFragmentBtAuthorisation.setOnClickListener {
            presenter.onLoginClick()
        }
        binding.profileFragmentBtCompare.setOnClickListener { presenter.onCompareClick() }
        App.Companion.currentUser?.let {
            with(binding) {
                profileFragmentTvUsername.text =
                    resources.getString(R.string.hello_profile, it.name)
                profileFragmentBtAuthorisation.visibility = View.INVISIBLE
                profileFragmentBtExitProfile.visibility = View.VISIBLE
                profileFragmentBtProfileSettings.visibility = View.VISIBLE
                if (it.seller) profileFragmentBtAddItem.visibility = View.VISIBLE
            }
        }
        binding.profileFragmentBtExitProfile.setOnClickListener { presenter.exitProfile() }
        binding.profileFragmentBtAddItem.setOnClickListener { presenter.addItem() }
        binding.profileFragmentBtProfileSettings.setOnClickListener { presenter.editProfile() }
    }
}