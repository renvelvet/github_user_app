package com.dicoding.githubuserapp.sections

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.user.UserAdapter
import com.dicoding.githubuserapp.databinding.FragmentSectionsPagerBinding

class SectionsPagerFragment : Fragment() {

    private var _binding : FragmentSectionsPagerBinding? = null
    private val binding get() = _binding
    private lateinit var sectionsPagerViewModel: SectionsPagerViewModel
    private lateinit var adapter: UserAdapter
//    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sections_pager, container, false)
    }

    companion object {

        private const val ARG_SECTION_TYPE = "section_type"
        private const val ARG_SECTION_USERNAME = "section_username"
        @JvmStatic
        fun newInstance(type: Int, username: String) =
            SectionsPagerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_TYPE, type)
                    putString(ARG_SECTION_USERNAME, username)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //username = arguments?.getString(DetailUserActivity.KEY_USER).toString()
        _binding = FragmentSectionsPagerBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        showLoading(true)

        val listType = arguments?.getInt(ARG_SECTION_TYPE)
        val username = arguments?.getString(ARG_SECTION_USERNAME)
        sectionsPagerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            SectionsPagerViewModel::class.java)
        sectionsPagerViewModel.setData(listType,username)
        sectionsPagerViewModel.getList().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })

        binding?.apply {
            rvViewPager.setHasFixedSize(true)
            rvViewPager.layoutManager = LinearLayoutManager(activity)
            rvViewPager.adapter = adapter
        }
    }

    private fun showLoading(state: Boolean) {
        binding?.apply {
            if (state) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}