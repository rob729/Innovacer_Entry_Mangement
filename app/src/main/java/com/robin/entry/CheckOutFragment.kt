package com.robin.entry


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.robin.entry.visitorDatabase.VisitorViewModel
import kotlinx.android.synthetic.main.fragment_check_out.*


/**
 * A simple [Fragment] subclass.
 */
class CheckOutFragment : Fragment() {

    val visitorViewModel: VisitorViewModel by lazy {
        ViewModelProviders.of(this).get(VisitorViewModel::class.java)
    }
    val visitorListAdapter: VisitorListAdapter by lazy {
        VisitorListAdapter(visitorViewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_out, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv.adapter = visitorListAdapter
        rv.layoutManager = LinearLayoutManager(context)

        visitorViewModel.allVisitors.observe(this, Observer {

            visitorListAdapter.submitList(it)
        })

    }

}
