package com.fighterdiet.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fighterdiet.R
import zendesk.chat.Chat
import zendesk.chat.ChatConfiguration
import zendesk.chat.ChatEngine
import zendesk.chat.VisitorInfo
import zendesk.messaging.MessagingActivity

class HelpAndSupportActivity : BaseActivity() {

    private lateinit var chatConfiguration: ChatConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_and_support)
//        initZopim()
        chatConfiguration = ChatConfiguration.builder()
            .withAgentAvailabilityEnabled(false)
            .withOfflineFormEnabled(true)
            .withPreChatFormEnabled(false)
            .build()

        val profileProvider = Chat.INSTANCE.providers()!!.profileProvider()

        val visitorInfo = VisitorInfo.builder()
            .withName("Visitor name")
            .withEmail("Visitor email")
            .withPhoneNumber("Visitor phone number")
            .build()
        profileProvider.setVisitorInfo(visitorInfo, null)

        MessagingActivity.builder()
            .withEngines(ChatEngine.engine())
            .show(this, chatConfiguration)
    }

//    fun initZopim() {
//        Chat.INSTANCE.init(applicationContext, "Your Zendesk Account key", "Your application id")
//    }

    override fun setupUI() {

    }

    override fun setupViewModel() {

    }

    override fun setupObserver() {

    }
}