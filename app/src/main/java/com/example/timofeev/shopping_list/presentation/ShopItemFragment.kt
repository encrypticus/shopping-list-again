package com.example.timofeev.shopping_list.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.timofeev.shopping_list.databinding.FragmentShopItemBinding
import com.example.timofeev.shopping_list.domain.ShopItem

class ShopItemFragment : Fragment() {
  private lateinit var viewModel: ShopItemViewModel

  private var screenMode: String = MODE_UNKNOWN
  private var shopItemId: Int = ShopItem.UNDEFINED_ID

  private lateinit var onEditingChangeListener: OnEditingChangeListener
  private var _binding: FragmentShopItemBinding? = null
  private val binding: FragmentShopItemBinding
    get() = _binding ?: throw RuntimeException("FragmentShopItemBinding == null")

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is OnEditingChangeListener) {
      onEditingChangeListener = context
    } else {
      throw RuntimeException("Activity must implement OnEditingChangeListener")
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    parseParams()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentShopItemBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
    binding.viewModel = viewModel
    binding.lifecycleOwner = viewLifecycleOwner
    addTextChangeListeners()
    launchRightMode()
    observeViewModel()
  }

  interface OnEditingChangeListener {
    fun onEditingChanged()
  }

  private fun observeViewModel() {
    viewModel.closeScreen.observe(viewLifecycleOwner) {
      onEditingChangeListener.onEditingChanged()
    }
  }

  private fun launchRightMode() {
    when (screenMode) {
      MODE_ADD -> launchAddMode()
      MODE_EDIT -> launchEditMode()
    }
  }

  private fun addTextChangeListeners() {
    binding.etName.addTextChangedListener(object : TextWatcher {
      override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        viewModel.resetInputNameError()
      }

      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
      override fun afterTextChanged(p0: Editable?) {}
    })

    binding.etCount.addTextChangedListener(object : TextWatcher {
      override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        viewModel.resetInputCountError()
      }

      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
      override fun afterTextChanged(p0: Editable?) {}
    })
  }

  private fun launchAddMode() {
    binding.saveButton.setOnClickListener {
      viewModel.addShopItem(binding.etName.text.toString(), binding.etCount.text.toString())
    }
  }

  private fun launchEditMode() {
    viewModel.getShopItem(shopItemId)
    binding.saveButton.setOnClickListener {
      viewModel.editShopItem(binding.etName.text.toString(), binding.etCount.text.toString())
    }
  }

  private fun parseParams() {
    val args = requireArguments()
    if (!args.containsKey(SCREEN_MODE)) {
      throw RuntimeException("Param screen mode is absent")
    }

    val mode = args.getString(SCREEN_MODE)
    if (mode != MODE_EDIT && mode != MODE_ADD) {
      throw RuntimeException("Unknown screen mode $mode")
    }

    screenMode = mode
    if (screenMode == MODE_EDIT) {
      if (!args.containsKey(SHOP_ITEM_ID)) {
        throw RuntimeException("Param shop item id is absent")
      }
      shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
    }
  }

  companion object {
    private const val SCREEN_MODE = "extra_screen_mode"
    private const val SHOP_ITEM_ID = "extra_shop_item_id"
    private const val MODE_EDIT = "mode_edit"
    private const val MODE_ADD = "mode_add"
    private const val MODE_UNKNOWN = ""

    fun newInstanceAddItem(): ShopItemFragment {
      return ShopItemFragment().apply {
        arguments = Bundle().apply {
          putString(SCREEN_MODE, MODE_ADD)
        }
      }
    }

    fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
      return ShopItemFragment().apply {
        arguments = Bundle().apply {
          putString(SCREEN_MODE, MODE_EDIT)
          putInt(SHOP_ITEM_ID, shopItemId)
        }
      }
    }

  }
}