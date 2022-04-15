package org.tensorflow.demo.sc;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.StringTokenizer;

public class commentcard extends LinearLayout {
    Context context;
    LinearLayout comments;

    public commentcard(String Msgs,Context context){
        super(context);
        super.setOrientation(LinearLayout.VERTICAL);
        this.context=context;

        comments=new LinearLayout(context);
        comments.setOrientation(LinearLayout.VERTICAL);

        /*ViewGroup.LayoutParams p=comments.getLayoutParams();
        p.height= LayoutParams.WRAP_CONTENT;
        p.width=LinearLayout.LayoutParams.MATCH_PARENT;
        comments.setLayoutParams(p);*/
        super.addView(comments);

        if(Msgs.equals("no comments")){
            comment com=new comment("댓글이 없습니다.",context);
            comments.addView(com);
            com.setTextColor(Color.BLACK);
        }
        else {
            StringTokenizer Msg = new StringTokenizer(Msgs, "_");
            while (Msg.hasMoreTokens()) {
                final comment com = new comment(Msg.nextToken(), context);
                comments.addView(com);
                com.setTextColor(Color.BLACK);
            }
        }
    }

    public void refresh(String Msgs){
        comments.removeAllViews();
        if(Msgs.equals("no comments")){
            comment com=new comment("댓글이 없습니다.",context);
            comments.addView(com);
            com.setTextColor(Color.BLACK);
        }
        else {
            StringTokenizer Msg = new StringTokenizer(Msgs, "_");
            while (Msg.hasMoreTokens()) {
                final comment com = new comment(Msg.nextToken(), context);
                comments.addView(com);
                com.setTextColor(Color.BLACK);
            }
        }

    }


}
