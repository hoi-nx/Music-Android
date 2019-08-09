package com.sun.music_66.view.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sun.music_66.R
import com.sun.music_66.constant.Constants
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.service.DownloadIntentService
import kotlinx.android.synthetic.main.ex_bottom_sheet_layout.*

/**
 * Created by nguyenxuanhoi on 2019-07-29.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class BottomSheetFragment : BottomSheetDialogFragment(), View.OnClickListener {
    private lateinit var track: TrackDto

    override fun onClick(v: View) {
        when (v.id) {
            R.id.textDownload -> {
                Toast.makeText(context, "Download", Toast.LENGTH_LONG).show()
                DownloadIntentService.startActionDownload(context!!, track)
                dismiss()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.ex_bottom_sheet_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeData(savedInstanceState)
        initializeComponents()
        registerListeners()
    }

    private fun registerListeners() {
        textDownload.setOnClickListener(this)

    }

    private fun initializeComponents() {

    }

    private fun initializeData(savedInstanceState: Bundle?) {
        arguments?.let {
            track = it.getParcelable(Constants.ARGUMENT_TRACK) as TrackDto
        }
    }



    companion object {
        @JvmStatic
        fun newInstance(track: TrackDto): BottomSheetFragment {
            val fragment = BottomSheetFragment()
            val args = Bundle()
            args.putParcelable(Constants.ARGUMENT_TRACK, track)
            fragment.arguments = args
            return fragment
        }
    }

}