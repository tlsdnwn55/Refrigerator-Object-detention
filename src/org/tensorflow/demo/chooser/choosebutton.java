package org.tensorflow.demo.chooser;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.tensorflow.demo.R;

public class choosebutton extends AppCompatButton {

    String name;
    double per;

    public choosebutton(String name,double per,Context context){
        super(context);
        this.name=name;
        this.per=per;
        String perstr;
        if((perstr=Double.toString(per)).length()>7){
            perstr=perstr.substring(0,6);
        }
        super.setText("\n\n\n"+name+"\n"+perstr+" %");
        super.setTextSize(16);

        //super.setBackgroundResource(R.drawable.orangebutton);

    }
    void resize(int a,int b){
        super.setLayoutParams(new ViewGroup.LayoutParams(
                a, 300));
    }
    void changename(){

        String perstr;
        if((perstr=Double.toString(per)).length()>7){
            perstr=perstr.substring(0,6);
        }
        super.setText(name+"\n"+perstr+" %");
    }
}
