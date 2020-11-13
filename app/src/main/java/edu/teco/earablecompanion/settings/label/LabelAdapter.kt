package edu.teco.earablecompanion.settings.label

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.teco.earablecompanion.databinding.AddLabelItemBinding
import edu.teco.earablecompanion.databinding.LabelItemBinding

class LabelAdapter(val entries: MutableList<LabelItem>, private val defaults: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class LabelViewHolder(val binding: LabelItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.labelDelete.setOnClickListener {
                entries.removeAt(bindingAdapterPosition)
                notifyItemRemoved(bindingAdapterPosition)
            }
        }
    }

    inner class AddViewHolder(binding: AddLabelItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.labelAdd.setOnClickListener {
                val item = LabelItem.Label(name = "")
                entries.add(entries.size - 1, item)
                notifyItemInserted(entries.size - 1)
            }
        }
    }

    override fun getItemCount(): Int = entries.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_LABEL -> LabelViewHolder(LabelItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            ITEM_VIEW_TYPE_ADD -> AddViewHolder(AddLabelItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LabelViewHolder -> {
                val item = entries[position]
                holder.binding.label = item as LabelItem.Label
                if (defaults.contains(item.name)) {
                    holder.binding.labelInput.isEnabled = false
                    holder.binding.labelDelete.isEnabled = false
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (entries[position]) {
            is LabelItem.Label -> ITEM_VIEW_TYPE_LABEL
            is LabelItem.Add -> ITEM_VIEW_TYPE_ADD
        }
    }

    companion object {
        private val TAG = LabelAdapter::class.java.simpleName

        private const val ITEM_VIEW_TYPE_LABEL = 0
        private const val ITEM_VIEW_TYPE_ADD = 1
    }
}