package com.jkestwill.test.ui.card.main

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
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.jkestwill.test.App
import com.jkestwill.test.R
import com.jkestwill.test.base.BaseFragment
import com.jkestwill.test.databinding.FragmentMainBinding
import com.jkestwill.test.databinding.LayoutCardBinding
import com.jkestwill.test.model.Card
import com.jkestwill.test.ui.adapter.CardDiffUtil
import com.jkestwill.test.ui.adapter.MarginItemDecorator
import com.jkestwill.test.ui.card.adapter.CardListAdapter
import com.jkestwill.test.ui.utils.PermissionUtil
import com.jkestwill.test.ui.utils.textChanges
import com.jkestwill.test.viewmodel.ViewModelProviderFactory
import dagger.Lazy
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    @Inject
    lateinit var factory: Lazy<ViewModelProviderFactory>
    private val viewModel by viewModels<MainViewModel>() {
        factory.get()
    }

    @Inject
    lateinit var permissionUtil: PermissionUtil

    private var mergedBinding: LayoutCardBinding? = null
    private var adapter = CardListAdapter()

    companion object {
        const val CARD = "card"
        private const val TAG = "MainFragment"
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

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        viewModel.getHistory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeCardNumChange()
        configureRecView()
        viewModel.card.observe(viewLifecycleOwner) {
            setInfo(it)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            println(it)
            mergedBinding?.cardNumber?.error = it.message

        }

        viewModel.cardHistory.observe(viewLifecycleOwner) {
            val difCallback = CardDiffUtil(adapter.getAll(), it)
            val difUtill = DiffUtil.calculateDiff(difCallback)
            adapter.addAll(it)
            difUtill.dispatchUpdatesTo(adapter)
        }
    }

    private fun observeCardNumChange() {
        mergedBinding?.cardNumber
            ?.textChanges()
            ?.debounce(400)
            ?.onEach {
                it?.let { text ->
                    if (text.length == 19) {
                        mergedBinding?.cardNumber?.error =
                            resources.getString(R.string.length_error)
                    }
                    if (text.isNotEmpty())
                        viewModel.search(text.toString().replace(" ", ""))
                    Log.e("ci", "$it")

                }
            }?.launchIn(lifecycleScope)
    }


    private fun setInfo(card: Card) {
        mergedBinding?.let {
            it.cardBankName.text = card.bank?.name ?: "-"
            it.cardCity.text = card.bank?.city ?: "-"
            it.cardBrand.text = card.brand ?: "-"
            it.cardLength.text = (card.number?.length ?: "-").toString()
            it.cardPrepaid.text =
                if (card.prepaid != null && card.prepaid) "Yes" else if (card.prepaid != null && !card.prepaid) "No" else "-"
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

    private fun configureRecView() {
        binding?.cardHistory?.adapter = adapter
        binding?.cardHistory?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding?.cardHistory?.addItemDecoration(MarginItemDecorator(10, 10, 10, 10))

        adapter.setOnItemClick {
            findNavController().navigate(
                R.id.action_mainFragment_to_cardFragment,
                bundleOf(CARD to it)
            )
        }

        adapter.setOnPhoneClick { phone ->
            permissionUtil.permissionPhone(onAccept = {
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone")))
            }, onDenied = {
                Log.e("den", "configureRecView: ")
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(android.Manifest.permission.CALL_PHONE),
                    PermissionUtil.DIAL
                )
            })

        }

        adapter.setOnLinkClick { url ->
            startActivity(Intent(Intent.ACTION_VIEW).also {
                it.data = Uri.parse("https://$url")
            })
        }
    }


}