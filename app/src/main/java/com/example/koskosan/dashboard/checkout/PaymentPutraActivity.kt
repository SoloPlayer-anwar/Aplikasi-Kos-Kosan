package com.example.koskosan.dashboard.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.koskosan.chat.ChatFragment
import com.example.koskosan.databinding.ActivityCheckoutSuccessBinding
import com.example.koskosan.home.HomeScreenActivity
import com.example.koskosan.model.kosPutra
import com.example.koskosan.model.kosPutri
import com.example.koskosan.waiting.WaitingFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.ShippingAddress
import com.midtrans.sdk.uikit.SdkUIFlowBuilder

class PaymentPutraActivity : AppCompatActivity() {
    private lateinit var checkoutSuccessBinding: ActivityCheckoutSuccessBinding
    private lateinit var refData : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkoutSuccessBinding = ActivityCheckoutSuccessBinding.inflate(layoutInflater)
        setContentView(checkoutSuccessBinding.root)



        checkoutSuccessBinding.home.setOnClickListener {
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
            finish()
        }

        checkoutSuccessBinding.paynow.setOnClickListener {
            paymentMidtrans()
        }
    }

    private fun paymentMidtrans() {
        val putra = intent.getParcelableExtra<kosPutra>("putra")
        refData = FirebaseDatabase.getInstance().getReference("Checkout")
            .child(putra?.putra.toString())
        val orang = putra?.putra.toString()
        val price = putra?.harga
        val userId = FirebaseAuth.getInstance().currentUser?.displayName
        SdkUIFlowBuilder.init()
            .setClientKey("SB-Mid-client-y470S7IuUZBdtjm3")
            .setContext(applicationContext)
            .setTransactionFinishedCallback {
            }
            .setMerchantBaseUrl("https://icuthamidar.herokuapp.com/index.php/")
            .enableLog(true)
            .setLanguage("id")
            .setColorTheme(CustomColorTheme("#FFE51255", "#B61548", "#FFE51255"))
            .buildSDK()

        val transactionRequest = TransactionRequest("$userId"+System.currentTimeMillis().toShort()+ "",price!!.toDouble())
        val detail = com.midtrans.sdk.corekit.models.ItemDetails("$userId-Id", price.toDouble(),1,orang)
        val itemDetails = ArrayList<com.midtrans.sdk.corekit.models.ItemDetails>()
        itemDetails.add(detail)
        dataDetail(transactionRequest)
        transactionRequest.itemDetails = itemDetails
        MidtransSDK.getInstance().transactionRequest = transactionRequest
        MidtransSDK.getInstance().startPaymentUiFlow(this)
    }

    private fun dataDetail(transactionRequest: TransactionRequest) {
        val putra = intent.getParcelableExtra<kosPutra>("putra")
        refData = FirebaseDatabase.getInstance().getReference("Checkout")
            .child(putra?.putra.toString())
        val tempat = putra?.tempat
        val userId = FirebaseAuth.getInstance().currentUser?.displayName
        val phone = FirebaseAuth.getInstance().currentUser?.phoneNumber
        val email = FirebaseAuth.getInstance().currentUser?.email
        val customerDetails = com.midtrans.sdk.corekit.models.CustomerDetails()
        customerDetails.customerIdentifier = userId
        customerDetails.phone = phone
        customerDetails.firstName = "Bapak"
        customerDetails.lastName = "Kos"
        customerDetails.email = email

        val shippingAddress = ShippingAddress()
        shippingAddress.address = "Jalan vetran km 5.5"
        shippingAddress.city = tempat
        shippingAddress.postalCode = "700023"
        customerDetails.shippingAddress = shippingAddress

        val billingAddress = BillingAddress()
        billingAddress.address = "Jalan vetran km 5.5"
        billingAddress.city = tempat
        billingAddress.postalCode = "700023"
        customerDetails.billingAddress = billingAddress
        transactionRequest.customerDetails = customerDetails
    }

}