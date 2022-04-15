package org.tensorflow.demo.sc;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class itemcard extends LinearLayout {
    Context context;
    item a;
    LinearLayout Line;
    LinearLayout buttonContainer;

    public itemcard(String name,String link,Context context,String comments,String ...ings){
        super(context);
        Line=new LinearLayout(context);
        super.setOrientation(LinearLayout.VERTICAL);
        this.context=context;

        a= new item(name, link,context,comments);
        buttonContainer=new LinearLayout(context);
        buttonContainer.setOrientation(LinearLayout.HORIZONTAL);
        buttonContainer.addView(a);
        buttonContainer.addView(a.addcomment);
        super.addView(buttonContainer);// 버튼

        Line.setBackgroundColor(Color.WHITE);
        Line.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,2));
        super.addView(Line);


        final ingre_card a2=new ingre_card(context,ings);
        super.addView(a2);//재료 카드

        super.addView(a.iteminfo);//홈페이지
        super.addView(a.commentinfo);//댓글란


        a2.hart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DBTask DB=new DBTask();
                DB.execute("4",a.name);
                a2.Ing2.setText((Integer.parseInt(a2.Ing2.getText().toString())+1)+"");
            }
        });
    }
}
