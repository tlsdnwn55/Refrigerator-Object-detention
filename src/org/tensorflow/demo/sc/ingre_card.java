package org.tensorflow.demo.sc;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ingre_card extends LinearLayout {
    TextView Ing1,Ing2,Ing3,Ing4;
    LinearLayout L1,L2;

    Button hart;
    Button download;

    int hartSW=0;//좋아요 눌렀는지 체크

    public ingre_card(Context context, String ...ingres){
        super(context);
        super.setOrientation(LinearLayout.VERTICAL);
      /*  super.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,40));*/

      /* LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,40);
        p.weight=9;
        super.setLayoutParams(p);*/
        String temp=ingres[0];

        L1=new LinearLayout(context);
        L1.setOrientation(LinearLayout.VERTICAL);
        L1.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,40));
        L2=new LinearLayout(context);
        L2.setOrientation(LinearLayout.HORIZONTAL);

        L2.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        super.addView(L1);
        super.addView(L2);

        Ing1=new TextView(context);
        Ing1.setBackgroundColor(Color.rgb(255,170,0));
        for(int i=1;i<ingres.length;i++){
            if(ingres[i].equals("NULL")) continue;
            temp+="  ,  "+ingres[i];
        }

        Ing1.setText(temp);

        Ing1.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        L1.addView(Ing1);
        super.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,130));

       // L2.setBackgroundColor(Color.rgb(255,170,0));
        L2.setGravity(Gravity.RIGHT);
        hart=new Button(context);
        hart.setText("♥");
        hart.setLayoutParams(new LayoutParams(
                80, LayoutParams.MATCH_PARENT));
        L2.addView(hart);
        Ing2=new TextView(context);
        Ing2.setGravity(Gravity.CENTER);
        Ing2.setTextColor(Color.BLACK);
        Ing2.setText("0");
        Ing2.setLayoutParams(new LayoutParams(
                100, LayoutParams.MATCH_PARENT));
        L2.addView(Ing2);

        download=new Button(context);
        download.setText("▼");
        download.setLayoutParams(new LayoutParams(
                80, LayoutParams.MATCH_PARENT));
        L2.addView(download);
       /* if(!ingres[1].equals("NULL")) {
            Ing2 = new TextView(context);
            Ing2.setText(ingres[1]);
            L1.addView(Ing2);
            Ing2.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,40));
        }
        if(!ingres[2].equals("NULL")) {
            Ing3 = new TextView(context);
            Ing3.setText(ingres[2]);
            L1.addView(Ing3);
            Ing3.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,40));
        }
        if(!ingres[3].equals("NULL")) {
            Ing4 = new TextView(context);
            Ing4.setText(ingres[3]);
            L1.addView(Ing4);
            Ing4.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,40));
        }*/
     }


}
