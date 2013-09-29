package com.froy.magicalitem;

import com.froy.magicalitem.MyItemAdapter.ViewHolder;

import android.view.View;
import android.view.View.OnClickListener;

public class MyOnClickListener implements OnClickListener {

	private ViewHolder viewHolder;
    public MyOnClickListener(ViewHolder viewHolder) {
    this.viewHolder = viewHolder;
    }

    @Override
    public void onClick(View v) {
//        switch (((Data) v.getTag()).getBtnPosition()) { 
//
//        case Consts.BUTTON_0:
//        if (!buttonsClickStatus[0]) { // case the btn is gray unclicked
//                setButtonsaBackground(0); // changes the background 
//                buttonsClickStatus[0] = true;
//                buttonsClickStatus[1] = false;
//                buttonsClickStatus[2] = false;
//        } else { // case already green clicked already
//                addOrRemove = false;
//                setButtonsaBackground(3);
//                for (int i = 0; i < buttonsClickStatus.length; i++) {
//                buttonsClickStatus[i] = false;
//            }
//        }
//
//        break;
//
//        case Consts.BUTTON_1:
//            if (!buttonsClickStatus[1]) { // case gray
//                setButtonsaBackground(1);
//                buttonsClickStatus[1] = true;
//                buttonsClickStatus[0] = false;
//                buttonsClickStatus[2] = false;
//            } else { // case already green
//                addOrRemove = false;
//                setButtonsaBackground(3);
//                for (int i = 0; i < buttonsClickStatus.length; i++) {
//                    buttonsClickStatus[i] = false;
//                }
//            }
//            break;
//
//        case Consts.BUTTON_2:
//            if (!buttonsClickStatus[2]) { // case gray
//                setButtonsaBackground(2);
//                buttonsClickStatus[2] = true;
//                buttonsClickStatus[0] = false;
//                buttonsClickStatus[1] = false;
//            } else { // case already green
//                addOrRemove = false;
//                setButtonsaBackground(3);
//                for (int i = 0; i < buttonsClickStatus.length; i++) {
//                buttonsClickStatus[i] = false;
//                }
//            }
//            break;
//
//        default:
//            break;
//        }
//
//                    //call a function to update data only in the activity
//        myActivity.update((Data) v.getTag());


}
}
