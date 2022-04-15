package org.tensorflow.demo.sc;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;

public class comment extends AppCompatTextView {

    public comment(String Msg,Context context){
        super(context);
        super.setText(Msg);
        super.setTextSize(14);
        super.setBackgroundColor(000000);
    }
}
