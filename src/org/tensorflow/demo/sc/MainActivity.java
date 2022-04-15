package org.tensorflow.demo.sc;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import org.tensorflow.demo.DetectorActivity;
import org.tensorflow.demo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    DBTask DB;
    String rMsg;
    String ingres[];
    Vector<itemcard> itemArray=new Vector<itemcard>(10);
    LinearLayout itemLayout;

    private static final int REQUEST_IMAGE_CAPTURE =672;
    private String imageFilePath;
    private  int[] UsingImage;
    private int[] expandImage;

    BitmapDrawable d;
    Bitmap b;
    int bitmapwidth;
    int bitmapheight;

    String foodname;

    EditText comment;
    AlertDialog BU2;
    itemcard clickeditem;
    Button up;
    ScrollView ScrollV;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode==REQUEST_IMAGE_CAPTURE) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap=(Bitmap) extras.get("data");
   /*         ((ImageView)findViewById(R.id.imageView)).setImageBitmap(imageBitmap);
            d = (BitmapDrawable)((ImageView) findViewById(R.id.imageView)).getDrawable();
            b = d.getBitmap();
            */
            //saveBitmaptoJpeg(b,"zzzz",new SimpleDateFormat("yyyyMMdd_HH").format(new Date()));
            UsingImage=bitmapToByteArray(imageBitmap);
            int[] Mask={0,-1,0,-1,4,-1,0,-1,0};
            //UsingImage=func.makegrayscaleRGB(imageBitmap,bitmapheight*bitmapwidth,bitmapwidth);
            //imageBitmap=byteArrayToBitmap(UsingImage,bitmapwidth,bitmapheight);
            /*expandImage=func.UsingMask(imageBitmap,bitmapwidth,bitmapheight,Mask,3);
            expandImage=func.binary_trasform(expandImage);
            UsingImage=func.hough_transform(expandImage,bitmapwidth,bitmapheight);
            imageBitmap=byteArrayToBitmap(UsingImage,bitmapwidth,bitmapheight);*/
            //((ImageView)findViewById(R.id.imageView)).setImageBitmap(imageBitmap);
           /* String result=temptv.getText().toString();

            try {
                db=new DBTask();
                rMsg=db.execute("3",result).get();
                if(rMsg.equals("no data")) return ;
                StringTokenizer token=new StringTokenizer(rMsg,"%%");
                while(token.hasMoreTokens()){
                    itemcard tempi = new itemcard(token.nextToken(),token.nextToken(),this,
                            token.nextToken(),token.nextToken(),token.nextToken(),token.nextToken());
                    itemLayout.addView(tempi);

                }

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
/*
            if(db.getStatus()==AsyncTask.Status.RUNNING){
                db.cancel(true);
            }*/

        }

    }
    public int[] bitmapToByteArray( Bitmap bitmap ) {

        bitmapwidth=bitmap.getWidth();
        bitmapheight=bitmap.getHeight();
        int[] byteArray=new int[bitmapwidth*bitmapheight];

        for(int i=0;i<bitmapheight;i++){
            for(int j=0;j<bitmapwidth;j++) {
                byteArray[i*bitmapwidth+j] = bitmap.getPixel(j,i);
            }
        }
      /*  ByteArrayOutputStream stream = new ByteArrayOutputStream() ;

        try {
            bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] byteArray = stream.toByteArray() ;*/
        return byteArray ;
    }
    public Bitmap byteArrayToBitmap( int[] byteArray,int x,int y ) {
        Bitmap bitmap=Bitmap.createBitmap(byteArray,x,y,Bitmap.Config.ARGB_8888);
        /*for(int i=0;i<bitmapheight;i++){
            for(int j=0;j<bitmapwidth;j++){
                bitmap.setP
            }
        }*/
         return bitmap ;
    }

    public static void saveBitmaptoJpeg(Bitmap bitmap,String folder, String name){
        String ex_storage =Environment.getExternalStorageDirectory().getAbsolutePath();
        // Get Absolute Path in External Sdcard
        String foler_name = "/"+folder+"/";
        String file_name = name+".jpg";
        String string_path = ex_storage+foler_name;

        File file_path;
        try{
            file_path = new File(string_path);
            if(!file_path.isDirectory()){
                file_path.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(string_path+file_name);
             bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

        }catch(FileNotFoundException exception){
         //   Log.e("FileNotFoundException", exception.getMessage());
        }catch(IOException exception){
           // Log.e("IOException", exception.getMessage());
        }
    }

/*
    private Object rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);

    }

    private int exifOrientationTodegress(int exifOrientation) {
        if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_90) return 90;
        else if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_180) return 180;
        else if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_270) return 270;
        return 0;
    }
*/
@Override
protected  void onDestroy() {
    super.onDestroy();
    for(int i=0;i<itemArray.size();i++) {
        itemArray.elementAt(i).a.info1.onPause();
    }

    Intent intent =new Intent(this, DetectorActivity.class);
    startActivity(intent);
    //기존 액티비티 다시 시작
    //아직 실험중
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        comment=new EditText(this);
        LinearLayout.LayoutParams p= new LinearLayout.LayoutParams(
                400,800);
        //DB=new DBTask();
        itemLayout=findViewById(R.id.itemLayout);
        //데이터 수신
        Intent intent = getIntent();

        ingres= intent.getExtras().getStringArray("ingres");

        up=findViewById(R.id.button2);
        ScrollV=findViewById(R.id.scrollView2);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScrollV.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        final AlertDialog.Builder builder2 =new AlertDialog.Builder(this);
        builder2.setTitle("댓글 달기");
        builder2.setView(comment);
        builder2.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DBTask insert = new DBTask();
                        insert.execute("1","'"+foodname+"','"+comment.getText().toString()+"'","comment");

                        Toast.makeText(getApplicationContext(),"댓글 송신.",Toast.LENGTH_LONG).show();

                        DBTask refresh = new DBTask();
                        try {
                            String comments=refresh.execute("0","'"+foodname+"'").get();
                            clickeditem.a.commentinfo.refresh(comments);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
        builder2.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"취소.",Toast.LENGTH_LONG).show();
                    }
                });
        BU2=builder2.create();
        //--


        String result="";//기본
        for(int i=0;i<ingres.length;i++){
           // if(ingres[i]==null) continue;
            for(int j=0;j<i;j++){
                if(ingres[i]==null) continue;
                if(ingres[i].equals(ingres[j])){
                    i++;
                    j=0;
                }
            }
            if(ingres[i]!=null) result+=ingres[i]+"-";
        }
        try {

            //---------------------타이머
            final Timer m_timer =new Timer();
            TimerTask m_task= new TimerTask() {
                @Override
                public void run() {
                    int temp=0;
                    if(rMsg==null){
                        temp++;
                        Toast.makeText(MainActivity.this,
                                "서버로부터 응답이 없습니다.", Toast.LENGTH_SHORT).show();

                        if(temp==5) {
                            Intent intent = new Intent(MainActivity.this, DetectorActivity.class);
                            startActivity(intent);
                        }//25초가 지났다
                        if(temp==10){
                            m_timer.cancel();
                        }
                    }
                }
            };

            m_timer.schedule(m_task,20000,1000);
            //30초 뒤에도 응답이 없다면 종료해라
            //--------------------------------------------------

            DB=new DBTask();
            DB.DBTaskset(this);
            rMsg=DB.execute("3",result).get();

             Log.e("IOException", ":"+rMsg+"vvvv\n");
            if(rMsg==null){
                Toast.makeText(this,
                        "인터넷 연결 에러입니다.", Toast.LENGTH_LONG).show();
                finish();
            }
            else if(rMsg.equals("null")){
                Toast.makeText(MainActivity.this,
                        "정보가 없습니다.", Toast.LENGTH_LONG).show();
                finish();
            }
            else if(rMsg.equals("no data")){
                    Toast.makeText(MainActivity.this,
                            "정보가 없습니다.", Toast.LENGTH_LONG).show();return ;
            }
            StringTokenizer token=new StringTokenizer(rMsg,"%");
            while(token.hasMoreTokens()){
                final itemcard tempi = new itemcard(token.nextToken(),token.nextToken(), this,
                        token.nextToken(),token.nextToken(),token.nextToken(),token.nextToken(),token.nextToken());
                itemArray.add(tempi);
                itemLayout.addView(tempi);

                LinearLayout Line= new LinearLayout(this);
                Line.setBackgroundColor(Color.rgb(255,170,0));
                Line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,2));


                final Button newcomment=new Button(this);
                newcomment.setText("댓글 달기");
                newcomment.setTextSize(16);
                newcomment.setBackgroundColor(000000);;//배경색
                newcomment.setTextColor(Color.BLACK);
                tempi.a.commentinfo.addView(Line);
                tempi.a.commentinfo.addView(newcomment);
                newcomment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickeditem=tempi;
                        foodname=tempi.a.getText().toString();
                        BU2.show();
                    }
                });
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(DB.getStatus()== AsyncTask.Status.RUNNING){
            DB.cancel(true);
        }
     /*   File f = new File(path);
        File[] files = f.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().toLowerCase(Locale.US).endsWith(".jpg"); //확장자
            }
        });
*/
        //--------------------------------------------------------------------

    }


}

