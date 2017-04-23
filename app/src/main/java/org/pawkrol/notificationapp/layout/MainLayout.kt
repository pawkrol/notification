package org.pawkrol.notificationapp.layout

import android.view.View
import android.widget.EditText
import org.jetbrains.anko.*
import org.pawkrol.notificationapp.MainActivity
import org.pawkrol.notificationapp.R

/**
 * Created by pawkrol on 2017-04-23.
 */
class MainLayout : AnkoComponent<MainActivity>{

    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui){
        verticalLayout {
            val address = editText {
                id = R.id.address
            }

            button {
                textResource = R.string.setAddress
                onClick { owner.onSetAddress(address) }
            }.lparams(width = matchParent) {
                bottomMargin = dip(20)
            }
            button {
                id = R.id.blinkBtn
                textResource = R.string.blink
                enabled = false
                onClick { owner.onBlink() }
            }
            button {
                id = R.id.checkBtn
                textResource = R.string.check
                enabled = false
                onClick { owner.onCheck() }
            }
            button {
                id = R.id.removeBtn
                textResource = R.string.remove
                enabled = false
                onClick { owner.onRemove() }
            }

            linearLayout {
                button("Mode 0") {
                    id = R.id.modeZeroBtn
                    enabled = false
                    onClick { owner.onMode(10) }
                }
                button("Mode 1") {
                    id = R.id.modeOneBtn
                    enabled = false
                    onClick { owner.onMode(11) }
                }
            }

        }
    }

}