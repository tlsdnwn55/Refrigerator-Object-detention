package org.tensorflow.demo.chooser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;


import org.tensorflow.demo.CameraActivity;
import org.tensorflow.demo.DetectorActivity;
import org.tensorflow.demo.R;
import org.tensorflow.demo.sc.MainActivity;

import java.util.Vector;

public class chooseActivity extends AppCompatActivity {

    LinearLayout L1;//메인
    ScrollView L1size;//버튼크기
    String ingres[];
    Vector<choosebutton> BV=new Vector<choosebutton>();
    Vector<LinearLayout> LV=new Vector<LinearLayout>();

    Button submit;

    int SW=0; //종료 스우치ㅣ
    @Override
    protected  void onDestroy() {
        super.onDestroy();

        if(SW==0){
        Intent intent =new Intent(this, DetectorActivity.class);
        startActivity(intent);
        }
        //기존 액티비티 다시 시작
        //아직 실험중
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chooseinter);
        L1=findViewById(R.id.L1);
        L1size=findViewById(R.id.L1size);
        submit=findViewById(R.id.button);
        Intent intent=getIntent();
        ingres=intent.getExtras().getStringArray("ingres");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chooseActivity.this, MainActivity.class);
                String a[]=new String[BV.size()];
                for(int i=0;i<BV.size();i++){
                    a[i]=BV.elementAt(i).name;
                }
                intent.putExtra("ingres",a);
                startActivity(intent);
                SW=1;
                finish();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        makechooseButton();
    }

    private void makechooseButton(){
        choosebutton temp;
        int sw=0;
        for(int i=0;i<ingres.length/2;i++){
            for(int j=i-1;j>=0;j--){
                if(ingres[i*2].equals(ingres[j*2])){
                    if(Double.parseDouble(ingres[i*2+1])>Double.parseDouble(ingres[j*2+1])){
                        temp= findchoosebutton(ingres[j*2]);
                        temp.per=Double.parseDouble(ingres[i*2+1]);
                        temp.changename();
                    }
                    sw=1;
                }//확률이 더 높을 경우
            }//이미 있는 버튼
            if(sw==1){
                sw=0;
                continue;
            }
            final choosebutton a=new choosebutton(ingres[i*2],Double.parseDouble(ingres[i*2+1]),this);
            a.resize(L1size.getWidth()/2,0);

            addchoosebutton(a);
            BV.add(a);
            a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a.setVisibility(View.GONE);
                    BV.remove(a);
                }
            });
        }
    }

    private void addchoosebutton(choosebutton a){
        if(LV.isEmpty()){
            LinearLayout b=new LinearLayout(this);
            b.setOrientation(LinearLayout.HORIZONTAL);
            L1.addView(b);
            LV.add(b);
        }
        else if(LV.lastElement().getChildCount()==2){
            LinearLayout b=new LinearLayout(this);
            b.setOrientation(LinearLayout.HORIZONTAL);
            L1.addView(b);
            LV.add(b);
        }
        LV.lastElement().addView(a);
    }

    private choosebutton findchoosebutton(String name){
        for(int i=0;i<BV.size();i++){
            if(BV.elementAt(i).name.equals(name)) return BV.elementAt(i);
        }
        return null;
    }

}
