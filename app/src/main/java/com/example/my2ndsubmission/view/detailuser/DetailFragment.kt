package com.example.my2ndsubmission.view.detailuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.my2ndsubmission.R
import com.example.my2ndsubmission.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private var buttonState: Boolean? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        viewModel.detailLiveData.observe(viewLifecycleOwner, Observer{ user ->
            if (user != null){
                val imageWidthHeight = 120
                userNameText.text = user.username
                nameText.text = checkValue(user.name)
                repoValue.text = checkValue(user.repository)
                companyValue.text = checkValue(user.company)
                locationValue.text = checkValue(user.location)
                Glide.with(this)
                    .load(user.avatar)
                    .apply(RequestOptions().override(imageWidthHeight, imageWidthHeight))
                    .placeholder(R.drawable.logo_people)
                    .error(R.drawable.logo_error)
                    .into(avatarImage)
                stopLoading()
            }
        })
        viewModel.isFavorite.observe(viewLifecycleOwner, Observer { isFavorite ->
            if (isFavorite == true) setButton(true)
            else if (isFavorite == false) setButton(false)
        })
        viewModel.finishedUserLoading.observe(viewLifecycleOwner, Observer { loading ->
            if (loading == false){
                detailBar.visibility = View.GONE
                Toast.makeText(activity, getString(R.string.failed_get_data), Toast.LENGTH_SHORT).show()
            }
        })

        favoriteButton.setOnClickListener {
            if (it == favoriteButton) putDatabase()
        }
    }

    private fun stopLoading(){
        detailBar.visibility = View.GONE
        detailConstraint.visibility = View.VISIBLE
    }

    private fun checkValue(value:String?): String? {
        return if (value == "null") ""
        else value
    }

    private fun setButton(boolean: Boolean){
        buttonState = boolean
        if (boolean) favoriteButton.setImageResource(R.drawable.logo_favorite)
        else if (!boolean) favoriteButton.setImageResource(R.drawable.logo_not_favorite)
    }

    private fun putDatabase(){
        if (buttonState == false) {
            viewModel.insertFavoriteUser()
            setButton(true)
        }
        else if (buttonState == true) {
            removeDialog()
        }
    }

    private fun removeDialog(){
        val removeDialogFragment = RemoveDialogFragment()
        val fragmentManager = childFragmentManager
        removeDialogFragment.show(fragmentManager, RemoveDialogFragment::class.java.simpleName)
    }

    internal var removeDialogListener: RemoveDialogFragment.OnRemoveDialogListener = object : RemoveDialogFragment.OnRemoveDialogListener{
        override fun onAgreed(int: Int) {
            if (int == 1) {
                viewModel.deleteFavoriteUser()
                setButton(false)
            }
        }
    }
}