package com.example.techmarket.presentation.view.editProfile

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.techmarket.APP_SCOPE
import com.example.techmarket.App
import com.example.techmarket.databinding.EditProfileFragmentBinding
import com.example.techmarket.presentation.presenter.EditProfilePresenter
import com.example.techmarket.presentation.presenter.ProfilePresenter
import com.example.techmarket.presentation.view.base.BaseFragment
import com.example.techmarket.presentation.view.profile.PROFILE_SCOPE
import toothpick.Toothpick

const val EDIT_PROFILE_SCOPE = "EDIT_PROFILE_SCOPE"
class EditProfileFragment : BaseFragment(), EditProfileView {
    private var _binding: EditProfileFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = EditProfileFragment()
    }

    @InjectPresenter
    lateinit var presenter: EditProfilePresenter

    @ProvidePresenter
    fun provideProfilePresenter(): EditProfilePresenter =
        Toothpick.openScopes(APP_SCOPE, EDIT_PROFILE_SCOPE)
            .getInstance(EditProfilePresenter::class.java)
            .also { Toothpick.closeScope(EDIT_PROFILE_SCOPE) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EditProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
        binding.editProfileSaveBt.setOnClickListener { presenter.updateUser(binding.editProfileNameEt.text.toString()) }
    }

    private fun bindData() {
        with(binding) {
            App.currentUser?.let {
                editProfileNameEt.text = Editable.Factory.getInstance().newEditable(it.name)
                editProfileEmailTv.text = it.email
            }
        }
    }
}