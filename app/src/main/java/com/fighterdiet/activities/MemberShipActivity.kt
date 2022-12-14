package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.android.billingclient.api.*
import com.fighterdiet.R
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.model.requestModel.PaymentRequestModel
import com.fighterdiet.data.repository.MembershipRepository
import com.fighterdiet.databinding.ActivityMemberShipBinding
import com.fighterdiet.utils.*
import com.fighterdiet.viewModel.MembershipViewModel
import com.fighterdiet.viewModel.MembershipViewModelProvider
import com.google.gson.Gson

class MemberShipActivity : BaseActivity(), View.OnClickListener, PurchasesUpdatedListener{

    private var choosenMembership: Int = -1
    private var currentPurchase: Purchase? = null
    private lateinit var mBillingClient: BillingClient
    private lateinit var orderId: String
    private lateinit var transactionId: String
    private lateinit var skuDetailsList: MutableList<SkuDetails>
    private lateinit var viewModel: MembershipViewModel

    private lateinit var binding: ActivityMemberShipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_member_ship)
        setupViewModel()
        setupObserver()
        setupUI()
        skuDetailsList = mutableListOf()
        initialize()
        setupInAppBilling()
    }

    override fun setupUI() {
    }

    override fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            MembershipViewModelProvider(MembershipRepository(RetrofitBuilder.apiService))
        ).get(MembershipViewModel::class.java)
    }

    override fun setupObserver() {
        viewModel.getResources().observe(this,{
            Log.d("Response_subscription", it.data.toString())
            when(it.status){
                Status.SUCCESS -> {
                    ProgressDialog.hideProgressDialog()
                    PrefManager.putBoolean(PrefManager.IS_SUBSCRIBED, true)
                    currentPurchase?.let { currentPurchase ->
                        acknowledgePurchase(currentPurchase.purchaseToken)
                    }
                }

                Status.ERROR -> {
                    ProgressDialog.hideProgressDialog()
                }

                Status.LOADING -> {
                    ProgressDialog.showProgressDialog(this)
                }
            }
        })
    }

    private fun initialize() {
        binding.clMemberShipYear.setOnClickListener(this)
        binding.btnMembershipMonth.setOnClickListener(this)
        binding.tvPrivacyPolicy.setOnClickListener(this)
        binding.tvTermNCondition.setOnClickListener(this)
    }

    companion object {
        const val TAG = "MemberShipActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, MemberShipActivity::class.java)
        }
    }

    override fun onClick(view: View?) {
        view?.id.let {
            when (it) {
                R.id.clMemberShipYear -> {
                    if(skuDetailsList.isNotEmpty())
                        choosenMembership = 1
                        if(skuDetailsList.size>0) {
                            launchPayment(skuDetailsList[1])
                        }
                }
                R.id.btnMembershipMonth -> {
                    choosenMembership = 0
                    if(skuDetailsList.isNotEmpty() && skuDetailsList.size>=1)
                        launchPayment(skuDetailsList[0])
                }
                binding.tvPrivacyPolicy.id -> {
                    val quiz = Intent(this, PrivacyAndTermsActivity::class.java)
                    quiz.putExtra("URL", "https://fighterdiet.com/privacy-policy/")
                    quiz.putExtra("PRIVACY", "PRIVACY POLICY")
                    startActivity(quiz)
                }

                binding.tvTermNCondition.id -> {
                    val quiz = Intent(this, PrivacyAndTermsActivity::class.java)
                    quiz.putExtra("URL", "https://fighterdiet.com/terms-and-conditions/")
                    quiz.putExtra("PRIVACY", "TERMS AND CONDITIONS")
                    startActivity(quiz)
                }
            }
        }
    }

    private fun setupInAppBilling() {
        mBillingClient =
            BillingClient.newBuilder(this).setListener(this).enablePendingPurchases().build()

        mBillingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                Toast.makeText(
                    this@MemberShipActivity, "Google Play Billing Setup Error ", Toast.LENGTH_SHORT
                ).show()
            }

            override fun onBillingSetupFinished(p0: BillingResult) {
                if (p0.responseCode == BillingClient.BillingResponseCode.OK) {
                    Log.e(TAG, ">>>>> onBillingSetupFinished OK Response")

                    if (Utils.isOnline(this@MemberShipActivity)) {
                        queryForInAppProducts()
                    }
//                  else {
//                      Utils.showSnackBar(
//                          btnContinuePlan,
//                          getString(R.string.connection_error_message)
//                      )
//                  }
                } else {
                    Log.e(TAG, ">>>>> onBillingSetupFinished Not OK Response")
                }
            }

        })
    }

    private fun queryForInAppProducts() {
        val skuList: MutableList<String> = ArrayList()
        skuList.add(Constants.InAppSubsProducts.monthly_subscription)
        skuList.add(Constants.InAppSubsProducts.yearly_subscription)
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS)
        mBillingClient.querySkuDetailsAsync(
            params.build()
        ) { responseCode: BillingResult, skuDetailsList: List<SkuDetails>? ->
            if (responseCode.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                if (skuDetailsList.isNotEmpty()) {
                    Log.e(">>>>> ", skuDetailsList.toString())
                    this.skuDetailsList.clear()
                    this.skuDetailsList.addAll(skuDetailsList)
                } else {
                    Toast.makeText(this, "No Subscription Product found", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(
                    this, "Billing Fail", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun launchPayment(skuDetails: SkuDetails) {
        val flowParams: BillingFlowParams = BillingFlowParams.newBuilder()
            .setSkuDetails(skuDetails)
            .build()

        mBillingClient.launchBillingFlow(
            this,
            flowParams
        )
    }

    override fun onPurchasesUpdated(p0: BillingResult, p1: MutableList<Purchase>?) {
        if (!isFinishing) {
            if (p0.responseCode == BillingClient.BillingResponseCode.OK) {
                for (purchase in p1!!) {
                    handlePurchase(purchase)
                }
            } else if (p0.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
                Toast.makeText(
                    this, "User cancelled", Toast.LENGTH_SHORT
                ).show()
            } else if (p0.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
                Toast.makeText(
                    this,
                    "Already Taken",
                    Toast.LENGTH_LONG
                ).show()
            } else if (p0.responseCode == BillingClient.BillingResponseCode.ITEM_UNAVAILABLE)
//                Toast.makeText(
//                    this,
//                    getString(R.string.str_item_unavailable),
//                    Toast.LENGTH_LONG
//                ).show()
            else {
                Toast.makeText(
                    this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        printPurchaseDetails(purchase)
        transactionId = purchase.purchaseToken
        orderId = purchase.orderId
        var purchseTime = purchase.purchaseTime
        var purchaseState = purchase.purchaseState
        val model = Gson().fromJson<PaymentRequestModel>(purchase.originalJson, PaymentRequestModel::class.java)
        model.amount = if(choosenMembership == 0) 14.99.toString() else 79.99.toString()
        if (purchase.purchaseState != Purchase.PurchaseState.PENDING) {
            currentPurchase = purchase
            viewModel.callMembershipApi(model)
        }

    }

    private fun printPurchaseDetails(purchase: Purchase) {
        Log.e(TAG, ">>>>> Transaction Id ::" + purchase.purchaseToken)
        Log.e(TAG, ">>>>> Order Id ::" + purchase.orderId)
        Log.e(TAG, ">>>>> Purchase Time ::" + purchase.purchaseTime)
        Log.e(TAG, ">>>>> Purchase State ::" + purchase.purchaseState)
        Log.e(TAG, ">>>>> SKU ::" + purchase.skus.toString())
        Log.e(TAG, ">>>>> AutoRenewal ::" + purchase.isAutoRenewing)
        Log.e(TAG, ">>>>> isAcknowledged ::" + purchase.isAcknowledged)
        Log.e(TAG, ">>>>>  JSON ::" + purchase.originalJson)
    }

    private fun acknowledgePurchase(purchaseToken: String) {
        Handler(Looper.getMainLooper()).post {
            ProgressDialog.showProgressDialog(this@MemberShipActivity)
        }
        val params = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchaseToken)
            .build()
        mBillingClient.acknowledgePurchase(params) { billingResult ->
            val responseCode = billingResult.responseCode
            val debugMessage = billingResult.debugMessage

            if(billingResult.responseCode == BillingClient.BillingResponseCode.OK){
                Handler(Looper.getMainLooper()).post{
                    startActivity(Intent(this@MemberShipActivity, DashboardActivity::class.java))
                    finishAffinity()
                    ProgressDialog.hideProgressDialog()
                }
                PrefManager.putBoolean(PrefManager.IS_SUBSCRIBED, true)
            }
            else{
                ProgressDialog.hideProgressDialog()
            }
        }
    }

    private fun queryPurchases() {
        val purchasesResults =
            mBillingClient.queryPurchases(BillingClient.SkuType.SUBS)
        val purchasesList = purchasesResults.purchasesList
        if (purchasesList != null) {
            for (purchase in purchasesList) {
                handlePurchase(purchase)
            }
        } else {
            Log.e(TAG, ">>>>> No Purchase found ")
        }
    }

    override fun onStop() {
        super.onStop()
        if (isFinishing)
            mBillingClient.endConnection()
    }
}