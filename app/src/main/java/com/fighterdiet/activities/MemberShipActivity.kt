package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.billingclient.api.*
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityMemberShipBinding
import com.fighterdiet.utils.Constants
import com.fighterdiet.utils.PrefManager
import com.fighterdiet.utils.Utils

class MemberShipActivity : BaseActivity(), View.OnClickListener, PurchasesUpdatedListener{

    private lateinit var mBillingClient: BillingClient
    private lateinit var orderId: String
    private lateinit var transactionId: String
    private lateinit var skuDetailsList: MutableList<SkuDetails>


    private lateinit var binding: ActivityMemberShipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_member_ship)
        skuDetailsList = mutableListOf()
        initialize()
        setupInAppBilling()
    }

    override fun setupUI() {

    }

    override fun setupViewModel() {

    }

    override fun setupObserver() {

    }

    private fun initialize() {
        binding.clMemberShipYear.setOnClickListener(this)
        binding.btnMembershipMonth.setOnClickListener(this)
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
                    finish()
//                    launchPayment(skuDetailsList[0])
//                    startActivity(RecipeInfoActivity.getStartIntent(this))
                }
                R.id.btnMembershipMonth -> {
                    finish()
//                    launchPayment(skuDetailsList[1])

//                    startActivity(RecipeInfoActivity.getStartIntent(this))
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
//                        setupRecyclerView()
                    } else {
//                        Utils.showSnackBar(
//                            btnContinuePlan,
//                            getString(R.string.connection_error_message)
//                        )
                    }
                } else {
                    Log.e(TAG, ">>>>> onBillingSetupFinished Not OK Response")
                }
            }

        })
    }

    private fun queryForInAppProducts() {
        val skuList: MutableList<String> =
            ArrayList()

        skuList.add(Constants.InAppSubsProducts.monthly_test_subscription)
        skuList.add(Constants.InAppSubsProducts.yearly_test_subscription)

        // for subscription set type BillingClient.SkuType.SUBS and for purchase set type BillingClient.SkuType.INAPP
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS)
//        progressBar.visibility = View.VISIBLE
        mBillingClient.querySkuDetailsAsync(
            params.build()
        ) { responseCode: BillingResult, skuDetailsList: List<SkuDetails>? ->
//            progressBar.visibility = View.GONE
            if (responseCode.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                if (skuDetailsList.size > 0) {
                    this.skuDetailsList.clear()
                    this.skuDetailsList.addAll(skuDetailsList)
//                    recyclerAdapter.notifyDataSetChanged()
                } else {
//                    Toast.makeText(mContext, "No Subscription Product found", Toast.LENGTH_SHORT)
//                        .show()
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

        if (!isFinishing()) {

            if (p0?.responseCode == BillingClient.BillingResponseCode.OK) {
                for (purchase in p1!!) {
                    handlePurchase(purchase)
                }
            } else if (p0?.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
                Toast.makeText(
                    this, "User cancelled", Toast.LENGTH_SHORT
                ).show()
            } else if (p0?.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
                Toast.makeText(
                    this,
                    "Already Taken",
                    Toast.LENGTH_LONG
                ).show()
                queryPurchases()
            } else if (p0?.responseCode == BillingClient.BillingResponseCode.ITEM_UNAVAILABLE)
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
        printPurhaseDetails(purchase)
        transactionId = purchase.purchaseToken
        orderId = purchase.orderId
        var purchseTime = purchase.purchaseTime
        var purchaseState = purchase.purchaseState
        if (purchase.purchaseState != Purchase.PurchaseState.PENDING) {
            callAddPaymentDetailApi(purchase)
        }
    }

    private fun callAddPaymentDetailApi(purchase: Purchase) {
        if (Utils.isOnline(this)) {
//            progressBar.visibility = View.VISIBLE
//            subscriptionPlanViewModel.getPaymentDetailResponse(
//                mPrefManager.getKeyAuthToken(),
//                getRequest(purchase)
//            )
//                .observe(this, Observer {
//                    progressBar.visibility = View.GONE
//                    if (it != null && it.code == 200 && it.status) {
//                        mPrefManager.setKeyIsSubscribedUser(true)
//                        acknowledgePurchase(purchase.purchaseToken)
//                    } else {
//                        it?.message.let {
//                            Utils.showSnackBar(btnContinuePlan, it)
//                        }
//                    }
//                })
        } else {
//            progressBar.visibility = View.GONE
//            Utils.showSnackBar(btnContinuePlan, getString(R.string.connection_error_message))
        }
    }

    private fun printPurhaseDetails(purchase: Purchase) {
        Log.e(TAG, ">>>>> Transaction Id ::" + purchase.purchaseToken)
        Log.e(TAG, ">>>>> Order Id ::" + purchase.orderId)
        Log.e(TAG, ">>>>> Purchase Time ::" + purchase.purchaseTime)
        Log.e(TAG, ">>>>> Purchase State ::" + purchase.purchaseState)
        Log.e(TAG, ">>>>> SKU ::" + purchase.sku)
        Log.e(TAG, ">>>>> AutoRenewal ::" + purchase.isAutoRenewing)
        Log.e(TAG, ">>>>> isAcknowledged ::" + purchase.isAcknowledged)
        Log.e(TAG, ">>>>>  JSON ::" + purchase.originalJson)
    }

    private fun acknowledgePurchase(purchaseToken: String) {
        val params = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchaseToken)
            .build()
        mBillingClient.acknowledgePurchase(params) { billingResult ->
            val responseCode = billingResult.responseCode
            val debugMessage = billingResult.debugMessage
            Log.e(">>", ">>>>> Purchase is acknowledged")
            PrefManager.putBoolean(PrefManager.IS_SUBSCRIBED, true)
//            if (isShowView) {
//                finish()
//            } else {
//                startActivity(Intent(mContext, DashboardActivity::class.java))
//            }
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

//    private fun startBillingServiceConnection() {
//        billingClient = BillingClient.newBuilder(this)
//            .enablePendingPurchases()
//            .setListener(this).build()
//
//        connectToBillingService()
//    }
//
//    private fun connectToBillingService() {
//        if (!billingClient.isReady) {
//            billingClient.startConnection(this)
//        }
//    }
//
//    override fun onPurchasesUpdated(
//        billingResult: BillingResult,
//        purchases: MutableList<Purchase>?
//    ) {
//        when (billingResult.responseCode) {
//            BillingClient.BillingResponseCode.OK -> {
//                purchases?.apply { processPurchases(this.toSet()) }
//            }
//            BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED -> {
//                // call queryPurchases to verify and process all owned items
//                queryPurchases()
//            }
//            BillingClient.BillingResponseCode.SERVICE_DISCONNECTED -> {
//                connectToBillingService()
//            }
//            else -> {
//                Log.e("BillingClient", "Failed to onPurchasesUpdated")
//            }
//        }
//    }
//
//
//    private fun processPurchases(purchases: Set<Purchase>) {
//        purchases.forEach { purchase ->
//            if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
//                // Implement server verification
//                // If purchase token is OK, then unlock user access to the content
//                acknowledgePurchase(purchase)
//            }
//        }
//    }
//
//    private fun acknowledgePurchase(purchase: Purchase) {
////        val skuDetails = skusWithSkuDetails[purchase.sku] ?: run {
////            Log.e("BillingClient", "Could not find SkuDetails to acknowledge purchase")
////            return
////        }
////        if (isSkuConsumable(purchase.sku)) {
////            consume(purchase.purchaseToken)
////        } else if (skuDetails.type == BillingClient.SkuType.SUBS && !purchase.isAcknowledged) {
////            acknowledge(purchase.purchaseToken)
////        }
//    }
//
//    private fun isSkuConsumable(sku: String) = CONSUMABLE_SKUS.contains(sku)
//
//
//    private fun consume(purchaseToken: String) {
//        val params = ConsumeParams.newBuilder()
//            .setPurchaseToken(purchaseToken)
//            .build()
//
//        billingClient.consumeAsync(
//            params
//        ) { billingResult, token ->
//            when (billingResult.responseCode) {
//                BillingClient.BillingResponseCode.OK -> {
////                    entitleUserProducts()
//                }
//                else -> {
//                    Log.e("BillingClient", "Failed to consume purchase $billingResult")
//                }
//            }
//        }
//    }
//
//    private fun acknowledge(purchaseToken: String) {
//        val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
//            .setPurchaseToken(purchaseToken)
//            .build()
//
//        billingClient.acknowledgePurchase(
//            acknowledgePurchaseParams
//        ) { billingResult ->
//            when (billingResult.responseCode) {
//                BillingClient.BillingResponseCode.OK -> {
////                    entitleUserProducts()
//                }
//                else -> {
//                    Log.e("BillingClient", "Failed to acknowledge purchase $billingResult")
//                }
//            }
//        }
//    }
//    override fun onBillingServiceDisconnected() {
//        connectToBillingService()
//    }
//
//    override fun onBillingSetupFinished(billingResult: BillingResult) {
//        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
//            // The billing client is ready. Retrieve in-app products and subscriptions details
////            querySkuDetailsAsync(BillingClient.SkuType.INAPP, INAPP_SKUS)
//
//            querySkuDetailsAsync(BillingClient.SkuType.SUBS, SUBS_SKUS)
//
//            // Refresh your application access based on the billing cache
//            queryPurchases()
//        }
//    }
//
//    private suspend fun querySkuDetailsAsync(
//        @BillingClient.SkuType skuType: String,
//        skuList: List<String>
//    ) {
//        val params = SkuDetailsParams
//            .newBuilder()
//            .setSkusList(skuList)
//            .setType(skuType)
//
//        val skuDetailsResult = billingClient.querySkuDetailsAsync(
//                params.build()
//            ) { billingResult, skuDetailsList ->
//                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
//                    for (details in skuDetailsList) {
//                        skusWithSkuDetails[details.sku] = details
//                    }
//                }
//            }
//
//
//        purchase(skuDetailsResult)
//    }
//
//    private fun queryPurchases() {
//        val purchasesResult = HashSet<Purchase>()
//        var result = billingClient.queryPurchases(BillingClient.SkuType.INAPP)
//        result.purchasesList?.apply { purchasesResult.addAll(this) }
//
//        result = billingClient.queryPurchases(BillingClient.SkuType.SUBS)
//        result.purchasesList?.apply { purchasesResult.addAll(this) }
//
//        processPurchases(purchasesResult)
//    }
//
//    private fun purchase(skuDetails: SkuDetails) {
//        val flowParams = BillingFlowParams.newBuilder()
//            .setSkuDetails(skuDetails)
//            .build()
//
//        billingClient.launchBillingFlow(this, flowParams)
//            .takeIf { billingResult -> billingResult.responseCode != BillingClient.BillingResponseCode.OK }
//            ?.let { billingResult ->
//                Log.e("BillingClient", "Failed to launch billing flow $billingResult")
//            }
//    }
//
//    private object GameSku {
//        const val WEEKLY = "weekly"
//        const val ANNUAL = "annual"
//        const val COIN = "coin"
//        const val RACE_TRACK = "race_trake"
//
//        val INAPP_SKUS = listOf(COIN, RACE_TRACK)
//        val SUBS_SKUS = listOf(WEEKLY, ANNUAL)
//        val CONSUMABLE_SKUS = listOf(COIN)
//    }
}