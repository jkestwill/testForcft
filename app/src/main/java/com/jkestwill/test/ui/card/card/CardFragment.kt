package com.jkestwill.test.ui.card.card


import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jkestwill.test.App
import com.jkestwill.test.R
import com.jkestwill.test.base.BaseFragment
import com.jkestwill.test.databinding.FragmentCardBinding
import com.jkestwill.test.databinding.LayoutCardBinding
import com.jkestwill.test.db.model.CardLocalOutput
import com.jkestwill.test.ui.card.main.MainFragment
import com.jkestwill.test.ui.utils.PermissionUtil
import com.jkestwill.test.viewmodel.ViewModelProviderFactory
import dagger.Lazy
import javax.inject.Inject


class CardFragment : BaseFragment<FragmentCardBinding>(FragmentCardBinding::inflate) {
    private var mergedBinding: LayoutCardBinding? = null

    @Inject
    lateinit var permissionUtil: PermissionUtil

    @Inject
    lateinit var factory: Lazy<ViewModelProviderFactory>
    private val viewModel by viewModels<CardViewModel>() {
        factory.get()
    }

    companion object {
        private const val TAG = "CardFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        container?.let {
            mergedBinding = binding?.root?.let { it1 -> LayoutCardBinding.bind(it1) }
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val cardId = it.getLong(MainFragment.CARD)
            viewModel.getCard(cardId)
        }
        Log.e("qwe", "onViewCreated: ${binding?.buttonBack}")

        viewModel.localCardLiveData.observe(viewLifecycleOwner){
            setInfo(it)
        }

        binding?.buttonBack?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setInfo(card: CardLocalOutput) {
        mergedBinding?.let {
            it.cardBankName.text = card.bank?.name ?: "-"
            it.cardNumber.setText(card.cardNumber ?: "-")
            it.cardNumber.isEnabled = false
            it.cardCity.text = card.bank?.city ?: "-"
            it.cardBrand.text = card.brand ?: "-"
            it.cardLength.text = (card.number?.length ?: "-").toString()
            it.cardPrepaid.text =
                if (card.prepaid != null && card.prepaid == true) "Yes" else if (card.prepaid != null && card.prepaid == false) "No" else "-"
            it.cardCountryLat.text = "latitude: ${(card.country?.latitude ?: "-")}"
            it.cardCountryLong.text = "latitude: ${(card.country?.longitude ?: "-")}"
            it.cardCountry.text = card.country?.name ?: "-"
            it.cardBankUrl.text = card.bank?.url ?: "-"
            it.cardBankPhone.text = card.bank?.phone ?: "-"
            it.cardType.text = card.type ?: "-"
            it.cardScheme.text = card.scheme?.uppercase() ?: "-"
            it.cardLuhn.text =
                if (card.number?.luhn == true) "Yes" else if (card.number?.luhn == false) "NO" else "-"

            if (card.bank?.url != null) {
                mergedBinding?.cardBankUrl?.let { url0 ->
                    highlightLinks(url0) { url1 ->
                        startActivity(Intent(Intent.ACTION_VIEW).also { intent ->
                            intent.data = Uri.parse("https://$url0")
                        })
                    }
                }
            }
            if (card.bank?.phone != null) {
                mergedBinding?.cardBankPhone?.let { phone0 ->
                    highlightLinks(phone0) { phone1 ->
                        permissionUtil.permissionPhone(onAccept = {
                            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone1")))
                        }, onDenied = {
                            ActivityCompat.requestPermissions(
                                requireActivity(), arrayOf(android.Manifest.permission.CALL_PHONE),
                                PermissionUtil.DIAL
                            )
                        })
                    }
                }
            }
            mergedBinding?.cardCountryLong?.let { long ->
                configureLocationIntent(
                    card.country?.longitude?.toString(),
                    "geo:${card.country?.latitude ?: ""},${card.country?.longitude ?: ""}",
                    long
                )
            }
            mergedBinding?.cardCountryLat?.let { lat ->
                configureLocationIntent(
                    card.country?.latitude?.toString(),
                    "geo:${card.country?.latitude ?: ""},${card.country?.longitude ?: ""}",
                    lat
                )
            }
            mergedBinding?.cardCountry?.let { country ->
                configureLocationIntent(
                    card.country?.name, "geo:?q=${Uri.encode(card.country?.name ?: "")}",
                    country
                )
            }
            mergedBinding?.cardCity?.let { city ->
                configureLocationIntent(
                    card.bank?.city,
                    "geo:?q=${Uri.encode("${card.country?.name ?: ""}${card.bank?.city ?: ""}")}",
                    city
                )
            }

        }
    }


    private fun configureLocationIntent(text: String?, q: String, view: TextView) {
        Log.e(TAG, "configureLocationIntent: ${text == null}")
        if (text != null) {
            highlightLinks(view) {
                startActivity(Intent(Intent.ACTION_VIEW).also { intent ->
                    intent.data = Uri.parse(q)
                }
                )
            }
        }
    }


    private fun highlightLinks(view: TextView, onClick: (String) -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setTextColor(
                view.context.resources.getColor(
                    R.color.firstTextColor,
                    view.context.theme
                )
            )
        } else {
            view.setTextColor(
                view.context.resources.getColor(
                    R.color.firstTextColor
                )
            )
        }
        view.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        view.setOnClickListener {
            onClick(view.text as String)
        }
    }
}

