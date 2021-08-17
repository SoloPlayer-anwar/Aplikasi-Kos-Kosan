package com.example.koskosan.dashboard.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.koskosan.databinding.ActivityCheckoutPutriBinding
import com.example.koskosan.databinding.ActivityCheckoutSuccessPutriBinding
import com.example.koskosan.home.HomeScreenActivity
import com.example.koskosan.model.kosPutri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.ShippingAddress
import com.midtrans.sdk.uikit.SdkUIFlowBuilder

class PaymentPutriActivity : AppCompatActivity() {
    private lateinit var refData : DatabaseReference
    private lateinit var checkoutSuccessPutriBinding: ActivityCheckoutSuccessPutriBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkoutSuccessPutriBinding = ActivityCheckoutSuccessPutriBinding.inflate(layoutInflater)
        setContentView(checkoutSuccessPutriBinding.root)


        val putri = intent.getParcelableExtra<kosPutri>("putri")

        checkoutSuccessPutriBinding.homePutri.setOnClickListener {
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
            finish()
        }

        checkoutSuccessPutriBinding.paynowPutri.setOnClickListener {
            paymentMidtransPutri()
        }
    }

    private fun paymentMidtransPutri() {
        val putri = intent.getParcelableExtra<kosPutri>("putri")
        refData = FirebaseDatabase.getInstance().getReference("CheckoutPutri")
            .child(putri?.putri.toString())
        val orang = putri?.putri
        val price = putri?.harga
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
        val putri = intent.getParcelableExtra<kosPutri>("PutriDetail")
        refData = FirebaseDatabase.getInstance().getReference("CheckoutPutri")
            .child(putri?.putri.toString())
        val tempat = putri?.tempat
        val userId = FirebaseAuth.getInstance().currentUser?.displayName
        val phone = FirebaseAuth.getInstance().currentUser?.phoneNumber
        val email = FirebaseAuth.getInstance().currentUser?.email

        val customerDetails = com.midtrans.sdk.corekit.models.CustomerDetails()
        customerDetails.customerIdentifier = userId
        customerDetails.phone = phone
        customerDetails.firstName = "Putri"
        customerDetails.lastName = "Kos"
        customerDetails.email = email

        val shippingAddress = ShippingAddress()
        shippingAddress.address = "Jalan beruntung Jaya"
        shippingAddress.city = tempat
        shippingAddress.postalCode = "700432"
        customerDetails.shippingAddress = shippingAddress

        val billingAddress = BillingAddress()
        billingAddress.address = "Jalan beruntung Jaya"
        billingAddress.city = tempat
        billingAddress.postalCode = "700432"
        customerDetails.billingAddress = billingAddress
        transactionRequest.customerDetails = customerDetails
    }

}