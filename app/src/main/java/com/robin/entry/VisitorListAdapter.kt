package com.robin.entry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.robin.entry.databinding.ItemRowBinding
import com.robin.entry.mailSender.Mailer
import com.robin.entry.visitorDatabase.Visitor
import com.robin.entry.visitorDatabase.VisitorViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class VisitorListAdapter(val visitorViewModel: VisitorViewModel) :
    ListAdapter<Visitor, VisitorListAdapter.ViewHolder>(VisitorDiffCallbacks()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRowBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, visitorViewModel)
    }

    class ViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Visitor, visitorViewModel: VisitorViewModel) {
            binding.ProfileName.text = item.name
            binding.btnCheckout.setOnClickListener {
                visitorViewModel.updateStatus(item.visitorId)
                Mailer.sendMail(item.email, "Visit Details", visitorDetails(item))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        }

        fun visitorDetails(visitor: Visitor): String {
            val currentTime: Date = Calendar.getInstance().time
            val address =
                "2nd and 9th Floor, Tower 3, Candor Techspace, Rajat Vihar, Block B, Industrial Area, Sector 62, Noida, Uttar Pradesh 201309"
            return " Visitor Name: ${visitor.name} \n Visitor Phone Number: ${visitor.phoneNumber} \n Check In Time: ${visitor.inTime} \n Check Out Time:${SimpleDateFormat(
                "HH:mm",
                Locale.ENGLISH
            ).format(currentTime.time)} \n Host Name: ${visitor.hostName} \n Address Visited: $address"
        }
    }

    class VisitorDiffCallbacks : DiffUtil.ItemCallback<Visitor>() {
        override fun areItemsTheSame(oldItem: Visitor, newItem: Visitor): Boolean {
            return oldItem.visitorId == newItem.visitorId
        }

        override fun areContentsTheSame(oldItem: Visitor, newItem: Visitor): Boolean {
            return oldItem == newItem
        }
    }

}