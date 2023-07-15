package com.dicoding.gituserlite.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.gituserlite.adapter.FollowUsersAdapter
import com.dicoding.gituserlite.ui.viewmodel.MainViewModel
import com.dicoding.gituserlite.databinding.FragmentDetailFollowBinding

class DetailFollowFragment : Fragment() {

    private lateinit var binding: FragmentDetailFollowBinding
    private val mainViewModel = MainViewModel()

    companion object{
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "section_username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var position = arguments?.getInt(ARG_POSITION, 0)
        var username = arguments?.getString(ARG_USERNAME)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        mainViewModel.isLoading.observe(requireActivity()) { showLoading(it) }

        mainViewModel.isError.observe(requireActivity()){
            if(it) {
                Toast.makeText(requireContext(), "Failed to load API", Toast.LENGTH_SHORT).show()
            }
        }

        if (position == 1) {
            mainViewModel.getFollowers(username.toString())
            mainViewModel.followers.observe(viewLifecycleOwner){
                val listFollowers = ArrayList<String>()
                binding.rvFollows.layoutManager = LinearLayoutManager(requireActivity())
                for(user in it){
                    listFollowers.add(
                        """
                            ${user.avatarUrl};${user.login}
                        """.trimIndent())
                }
                if (listFollowers.isEmpty()) {
                    binding.rvFollows.visibility = View.GONE
                    binding.tvEmptyFollowers.visibility = View.VISIBLE
                } else {
                    binding.rvFollows.visibility = View.VISIBLE
                    binding.tvEmptyFollowers.visibility = View.GONE
                    binding.rvFollows.layoutManager = LinearLayoutManager(requireActivity())
                    val adapter = FollowUsersAdapter(listFollowers)
                    binding.rvFollows.adapter = adapter
                }
            }
        } else {
            mainViewModel.getFollowing(username.toString())
            mainViewModel.following.observe(viewLifecycleOwner){
                val listFollowing = ArrayList<String>()
                binding.rvFollows.layoutManager = LinearLayoutManager(requireActivity())
                for (user in it){
                    listFollowing.add(
                        """
                        ${user.avatarUrl};${user.login}
                    """.trimIndent())
                }
                if (listFollowing.isEmpty()) {
                    binding.rvFollows.visibility = View.GONE
                    binding.tvEmptyFollowing.visibility = View.VISIBLE
                } else {
                    binding.rvFollows.visibility = View.VISIBLE
                    binding.tvEmptyFollowing.visibility = View.GONE
                    binding.rvFollows.layoutManager = LinearLayoutManager(requireActivity())
                    val adapter = FollowUsersAdapter(listFollowing)
                    binding.rvFollows.adapter = adapter
                }
                }
            }
        }

    private fun showLoading(isLoading: Boolean) { binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE}
}