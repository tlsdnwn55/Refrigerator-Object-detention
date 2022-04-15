package org.tensorflow.demo.sc;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class item extends AppCompatButton {
    String name;
    String link;
    Context context;
    FrameLayout iteminfo;
    public WebView info1;
    private WebSettings info1set;
    int SW=0;

    Button addcomment;
    commentcard commentinfo;

    public item(Context context){
        super(context);
        this.context=context;
    }

    public item(String name, String link, Context context,String comments){
        super(context);
        super.setBackgroundColor(Color.WHITE);
        super.setTextColor(Color.BLACK);
        this.name=name;
        this.link=link;
        this.context=context;
        iteminfo =new FrameLayout(context);
        iteminfo.setVisibility(INVISIBLE);
        info1= new WebView(context);

        addcomment=new Button(context);
        addcomment.setText("댓글");
        addcomment.setLayoutParams(new ViewGroup.LayoutParams(60, ViewGroup.LayoutParams.MATCH_PARENT));
        addcomment.setBackgroundColor(Color.rgb(255,170,0));
        //eo댓글 버튼
        commentinfo=new commentcard(comments,context);
        ///댓글 뷰

        iteminfo.setBackgroundColor(0xffffffff);//배경색
        iteminfo.addView(info1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            info1set=info1.getSettings();
            info1set.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            info1set.setJavaScriptEnabled(true);//자바스크립트 허용
            info1set.setLoadWithOverviewMode(true);
        }
        info1.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
         super.setText(name);
        super.setTextSize(24);
        resize(100);
        setListener();
    }
    void resize(int a){
        LinearLayout.LayoutParams p= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,a);
        p.weight=1;
        super.setLayoutParams(p);

        LinearLayout.LayoutParams p2= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,a);
        p2.weight=5;
        addcomment.setLayoutParams(p2);
        iteminfo.setLayoutParams(new LinearLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,0));
        commentinfo.setLayoutParams(new LinearLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,0));
        /*info1.setLayoutParams(new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,500));*/

    }
    void inforesize(int a){
        ViewGroup.LayoutParams p=iteminfo.getLayoutParams();
        p.height=a;
        p.width=LinearLayout.LayoutParams.MATCH_PARENT;
        iteminfo.setLayoutParams(p);
    }

    void commentinforesize(int a){
        ViewGroup.LayoutParams p=commentinfo.getLayoutParams();
        if(a==-1)p.height=800;
        else p.height=a;
        p.width=LinearLayout.LayoutParams.MATCH_PARENT;
        commentinfo.setLayoutParams(p);
    }

    void inforesize2(){
        ViewGroup.LayoutParams p=iteminfo.getLayoutParams();
        p.height=LinearLayout.LayoutParams.MATCH_PARENT;
        p.width=LinearLayout.LayoutParams.MATCH_PARENT;
        iteminfo.setLayoutParams(p);
/*
        ViewGroup.LayoutParams p2=commentinfo.getLayoutParams();
        p.height=0;
        p.width=LinearLayout.LayoutParams.MATCH_PARENT;
        commentinfo.setLayoutParams(p2);*/
    }

    public void setListener(){
        super.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {/*
                Intent a=new Intent(Intent.ACTION_VIEW);
                a.setData(Uri.parse(link));
                context.startActivity(a);*/
                if(SW==3){
                    inforesize2();
                    iteminfo.setVisibility(VISIBLE);
                    SW=2;
                }
                else if(SW<1){
                    inforesize2();
                    iteminfo.setVisibility(VISIBLE);
                    commentinfo.setVisibility(INVISIBLE);
                    info1.setWebViewClient(new WebViewClient());
                    info1.loadUrl(link);
                    SW=2;
                }
                else if(SW>1){
                    inforesize(0);
                    iteminfo.setVisibility(INVISIBLE);
                    commentinforesize(0);
                    SW=1;
                }
                else if(SW==1){
                    inforesize2();
                    iteminfo.setVisibility(VISIBLE);
                    commentinfo.setVisibility(INVISIBLE);

                    SW=2;
                }
                Log.d("SW:",SW+"");
            }//2 웹뷰 켜짐 //1모두 꺼짐 //3 댓글 켜짐 //0아직 로드 안됨 //-1 로드 안된 상태로 댓글 활성화
        });
        addcomment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SW==-1){
                    commentinfo.setVisibility(INVISIBLE);
                    commentinforesize(0);
                    SW=0;
                }//댓글 켜기
                else if(SW==0){
                    commentinfo.setVisibility(VISIBLE);
                    commentinforesize(-1);
                    SW=-1;
                }//댓글 켜기
                else if(SW==1){
                    commentinfo.setVisibility(VISIBLE);
                    commentinforesize(-1);
                    SW=3;
                }//댓글 켜기
                else if(SW==2){
                    commentinforesize(-1);
                    inforesize(0);
                    iteminfo.setVisibility(INVISIBLE);
                    commentinfo.setVisibility(VISIBLE);
                    SW=3;
                }//댓글 켜기 웹뷰 끄기
                else{
                    commentinfo.setVisibility(INVISIBLE);
                    commentinforesize(0);
                    SW=1;
                }
                Log.d("SW:",SW+"");
            }
        });
    }


}
