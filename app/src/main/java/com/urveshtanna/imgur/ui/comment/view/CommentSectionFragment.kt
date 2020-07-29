package com.urveshtanna.imgur.ui.comment.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.urveshtanna.imgur.R
import com.urveshtanna.imgur.data.local.db.AppDatabase
import com.urveshtanna.imgur.data.model.Comment
import com.urveshtanna.imgur.data.model.GalleryData
import com.urveshtanna.imgur.data.remote.APIHelper
import com.urveshtanna.imgur.data.remote.APIServiceImpl
import com.urveshtanna.imgur.databinding.CommentSectionFragmentBinding
import com.urveshtanna.imgur.ui.base.ViewModelFactory
import com.urveshtanna.imgur.ui.comment.adapter.CommentAdapter
import com.urveshtanna.imgur.ui.comment.navigator.CommentSectionNavigator
import com.urveshtanna.imgur.ui.comment.viewmodel.CommentSectionViewModel

class CommentSectionFragment : Fragment(), CommentSectionNavigator {

    companion object {

        @JvmStatic
        fun newInstance(bundle: Bundle): Fragment {
            val fragment = CommentSectionFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewModel: CommentSectionViewModel
    private lateinit var galleryData: GalleryData
    lateinit var adapter: CommentAdapter
    lateinit var binding: CommentSectionFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.comment_section_fragment, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        galleryData = arguments?.getParcelable(GalleryData::class.java.simpleName)!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUI()
        setupViewModel()
        setupObserver();
    }

    private fun setupObserver() {
        viewModel.getComments(galleryData.id!!)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                APIHelper(APIServiceImpl()),
                activity?.let { it1 -> AppDatabase.getInstance(it1) }!!
            )
        ).get(CommentSectionViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.setNavigator(this)
    }

    private fun setupUI() {
        binding.commentRecyclerview.layoutManager = LinearLayoutManager(activity)
        adapter = CommentAdapter(arrayListOf())
        binding.commentRecyclerview.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(
            binding.commentRecyclerview.getContext(),
            (binding.commentRecyclerview.layoutManager as LinearLayoutManager).getOrientation()
        )
        binding.commentRecyclerview.addItemDecoration(dividerItemDecoration)
        binding.btnSubmit.setOnClickListener {
            if (viewModel.isValidComment(
                    binding.edtUsername.text.toString(),
                    binding.edtComment.text.toString()
                )
            ) {
                viewModel.addComment(
                    binding.edtUsername.text.toString(),
                    binding.edtComment.text.toString(),
                    galleryData.id
                )
            } else {
                Toast.makeText(activity, getString(R.string.enter_all_fields), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun loadComments(comments: List<Comment>) {
        adapter.addData(comments)
        adapter.notifyDataSetChanged()
    }

    override fun newCommentAdded(comment: Comment) {
        Toast.makeText(activity, getString(R.string.comment_added), Toast.LENGTH_LONG)
            .show()
        binding.edtComment.setText(null)
        binding.edtUsername.setText(null)
        adapter.addData(comment)
        adapter.notifyDataSetChanged()
    }

    override fun handleError(throwable: Throwable) {
        Toast.makeText(activity, throwable.message.toString(), Toast.LENGTH_LONG).show()
    }

}