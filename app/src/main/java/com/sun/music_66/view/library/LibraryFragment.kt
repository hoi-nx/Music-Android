package com.sun.music_66.view.library

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.sun.music_66.R
import com.sun.music_66.base.view.BaseFragment
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.service.DownloadIntentService
import com.sun.music_66.view.adapter.LibraryAdapter
import com.sun.music_66.view.tab.DownloadContract
import com.sun.music_66.view.tab.DownloadFragment
import com.sun.music_66.view.tab.DownloadPresenter
import kotlinx.android.synthetic.main.fragment_library.*

/**
 * Created by nguyenxuanhoi on 2019-07-19.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class LibraryFragment : BaseFragment(), DownloadContract.View {
    private lateinit var adapter: LibraryAdapter
    private val presenter by lazy {
        DownloadPresenter()
    }
    override fun getContentViewId(): Int = R.layout.fragment_library

    override fun initializeData(savedInstanceState: Bundle?) {
    }

    override fun initializeComponents() {
        presenter.setView(this)
        adapter=LibraryAdapter(context!!,childFragmentManager).apply {
            viewPagerLibrary.adapter = this
        }
        tabLayout.setupWithViewPager(viewPagerLibrary)
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(context!!)
                .registerReceiver(receiver, IntentFilter(DownloadIntentService.DOWNLOAD_COMPLETE))
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(context!!)
                .unregisterReceiver(receiver)
    }
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                when (it.action) {
                    DownloadIntentService.DOWNLOAD_COMPLETE -> {
                        val track = it.getParcelableExtra(DownloadIntentService.EXTRA_TRACK) as? TrackDto
                        track?.let {track->
                            if(presenter.saveDownload(track)){
                                Toast.makeText(context,"Download Success",Toast.LENGTH_LONG).show()
                                if(::adapter.isInitialized){
                                    (adapter.getItem(1) as DownloadFragment).refresh()
                                }
                            }else{
                                Toast.makeText(context,"Save Fail",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    else -> {

                    }
                }
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() = LibraryFragment()
    }

}
