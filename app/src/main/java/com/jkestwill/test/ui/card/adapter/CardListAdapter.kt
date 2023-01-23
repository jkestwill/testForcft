package com.jkestwill.test.ui.card.adapter

import android.graphics.Paint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jkestwill.test.R
import com.jkestwill.test.databinding.ItemCardBinding
import com.jkestwill.test.db.model.CardLocalOutput
import com.jkestwill.test.ui.base.HolderBinder

class CardListAdapter : RecyclerView.Adapter<CardListAdapter.CardViewHolder>() {

    private val itemList = mutableListOf<CardLocalOutput>()

    private var onPhoneClick: ((String) -> Unit)? = null
    private var onLinkClick: ((String) -> Unit)? = null
    private var onItemClick: ((Long) -> Unit)? = null

    fun setOnPhoneClick(onPhoneClick:(String) -> Unit){
        this.onPhoneClick=onPhoneClick
    }
    fun setOnLinkClick(onLinkClick:(String) -> Unit){
        this.onLinkClick=onLinkClick
    }
    fun setOnItemClick(onItemClick:(Long)->Unit){
        this.onItemClick=onItemClick
    }
    fun addAll(list:List<CardLocalOutput>){
        itemList.clear()
        itemList.addAll(list)
    }

    fun getAll()=itemList

    inner class CardViewHolder(private val view: ItemCardBinding) :
        RecyclerView.ViewHolder(view.root),
        HolderBinder<CardLocalOutput> {
        override fun onBind(model: CardLocalOutput) {
            view.cardListBank.text = model.bank?.name?:"-"
            view.cardListNumber.text = model.cardNumber
            view.cardListScheme.text = model.scheme?:"-"
            view.cardListBankSite.text = model.bank?.url?:"-"
            view.cardListBankPhone.text = model.bank?.phone?:"-"

            if (model.bank?.phone!=null ) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    view.cardListBankPhone.setTextColor(
                        view.cardListBankPhone.context.resources.getColor(
                            R.color.firstTextColor,
                            view.cardListBankSite.context.theme
                        )
                    )
                }
                else {
                    view.cardListBankPhone.setTextColor(
                        view.cardListBankPhone.context.resources.getColor(
                            R.color.firstTextColor
                        )
                    )
                }

                view.cardListBankPhone.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                view.cardListBankPhone.setOnClickListener {
                    onPhoneClick?.let { it1 -> it1(model.bank!!.phone!!) }
                }
            }
            if (model.bank?.url!=null) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    view.cardListBankSite.setTextColor(
                        view.cardListBankSite.context.resources.getColor(
                            R.color.firstTextColor,
                            view.cardListBankSite.context.theme
                        )
                    )
                }
                else {
                    view.cardListBankSite.setTextColor(
                        view.cardListBankSite.context.resources.getColor(
                            R.color.firstTextColor
                        )
                    )
                }
                view.cardListBankSite.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                view.cardListBankSite.setOnClickListener {
                    onLinkClick?.let { it(model.bank!!.url!!) }
                }

            }
            view.root.setOnClickListener{
                onItemClick?.let { it1 -> it1(model.id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            ItemCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}