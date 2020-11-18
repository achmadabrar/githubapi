package com.achmadabrar.githubapi.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.achmadabrar.gitgubapi.core.base.BaseActivity
import com.achmadabrar.githubapi.R
import com.achmadabrar.githubapi.presentation.fragment.SearchUserFragment

class SearchUserActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_user)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, SearchUserFragment())
        transaction.commit()
    }
}