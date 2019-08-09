package com.sun.music_66.view.tab

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sun.music_66.R
import com.sun.music_66.base.view.BaseFragment
import com.sun.music_66.constant.RequestCode
import com.sun.music_66.util.PermissionUtil
import com.sun.music_66.view.adapter.MusicDeviceAdapter
import com.sun.music_66.view.widget.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_my_song.*

/**
 * Created by nguyenxuanhoi on 2019-07-29.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class MySongFragment :BaseFragment(){

    private val presenter by lazy {
        MySongPresenter(MusicExternalManager(context!!))
    }
    override fun getContentViewId(): Int = R.layout.fragment_my_song

    override fun initializeData(savedInstanceState: Bundle?) {
        if(isStoragePermissionGranted()){
            initAdapter()
            return
        }
        PermissionUtil.requestStoragePermission(this)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            RequestCode.PERMISSION_STORAGE -> {
                if (PermissionUtil.isGrantResultsGranted(grantResults)) {
                    initAdapter()
                }else{
                    Toast.makeText(context,getString(R.string.alert_permissions_rationale), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun initAdapter() {
        attachAdapterToRecyclerView(rc_view_item, MusicDeviceAdapter {

        })
    }

    private fun attachAdapterToRecyclerView(recyclerView: RecyclerView, deviceAdapter: MusicDeviceAdapter) {
        deviceAdapter.submitList(presenter.getAllMusic())
        with(recyclerView) {
            addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelSize(R.dimen._8dp)))
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = deviceAdapter
        }

    }
    private fun isStoragePermissionGranted(): Boolean {
        return PermissionUtil.isPermissionsGranted(context!!, PermissionUtil.storagePermissions)
    }


    override fun initializeComponents() {
    }
    companion object{
        @JvmStatic
        fun newInstance():MySongFragment= MySongFragment()
    }

}