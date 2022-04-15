package org.tensorflow.demo.sc;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;


public class functions {

    int WHITE=Color.argb(255,255,255,255);

    int[] unexpanding(int[] expandedarray){
        int a = expandedarray.length/3;
        int[] unexpandarray=new int[a];

        for(int i=0;i<a;i++){
            unexpandarray[i]=Color.argb(255,expandedarray[i*3],expandedarray[i*3+1],expandedarray[i*3+2]);
        }
        return unexpandarray;
    }//배열을 축소함.(R,G,B성분을 합쳐 bitmap.color로 변환 가로가 3배 감소) 단 alpha는 255

    int[] UsingMask(Bitmap bitmap,int sizex,int sizey,int[] Mask,int MaskSize){
        int[] Image= new int[sizex*sizey*3];
        int[] output=new int[sizex*sizey*3];

        for(int i=0;i<sizey;i++){
            for(int j=0;j<sizex*3;j+=3){
                int P=bitmap.getPixel(j/3,i);
                Image[i*sizex*3+j]= Color.red(P);
                Image[i*sizex*3+j+1]= Color.green(P);
                Image[i*sizex*3+j+2]= Color.blue(P);
            }
        }

        for(int i=0;i<sizey;i++) {
            for (int j = 0; j < sizex * 3; j++) { //j=x축 i=y축

                int total=0;
                int stack=0;

                for(int k=-(MaskSize/2),y=0;k<=MaskSize/2;k++,y++){
                    for(int l=-(MaskSize/2),x=0;l<=MaskSize/2;l++,x++){

                        if(i+k<0) continue;
                        if(j+l*3<0) continue;
                        if(i+k>=sizey) continue;
                        if(j+l*3>=sizex*3) continue;

                        total+=Mask[x+MaskSize*y]*Image[(i+k)*sizex*3+j+l*3];
                        stack++;
                    }
                }
                output[i*sizex*3+j]=total/stack;
            }
        }
        output=unexpanding(output);
        return output;
    }//(비트맵,비트맵 가로,비트맵 세로,마스크,마스크의 사이즈) 이미지 마스크 사용 단 마스크는 가로 세로가 같아야 함.

    int[] makegrayscaleYUV(Bitmap bitmap, int size,int width){

        int[] Image= new int[size];
        for(int i=0;i<size/width;i++){
            for(int j=0;j<width;j++) {
                    int P = bitmap.getPixel(j, i);
                    int a = (int) (Color.red(P) * 0.2989 + Color.green(P) * 0.5870 + Color.blue(P) * 0.1140);
                    Image[i*width+j]=Color.argb(255,a,a,a);
            }
        }
        return Image;
    }//(비트맵 ,비트맵 사이즈,비트맵 너비) 비트맵을 YUV모델로 변경

    int[] makegrayscaleRGB(Bitmap bitmap,int size,int width){
        int[] Image= new int[size];
        for(int i=0;i<size/width;i++){
            for(int j=0;j<width;j++) {
                int P = bitmap.getPixel(j, i);
                int a = (int) (Color.red(P) + Color.green(P)+ Color.blue(P))/3;
                Image[i*width+j]=Color.argb(255,a,a,a);
            }
        }
        return Image;
    }//(비트맵,비트맵사이즈,비트맵 너비) 비트맵을 RGB모델을 통해 Grayscale로 변경

    int[] makegrayscaleYCRCB(Bitmap bitmap,int size,int width){
        int[] Image= new int[size];
        for(int i=0;i<size/width;i++){
            for(int j=0;j<width;j++) {
                int P = bitmap.getPixel(j, i);
                int a = (int) (Color.red(P) *0.2126+ Color.green(P)*0.7152+ Color.blue(P)*0.0722);
                Image[i*width+j]=Color.argb(255,a,a,a);
            }
        }
        return Image;
    }//(비트맵,비트맵사이즈,비트맵 너비) 비트맵 이미지를 YCrCb모델 색상으로 변경

    int findOpticalSource(int[] preImage,int[] edgeImage,int width){

        for(int i=0;i< preImage.length;i++){
            if(edgeImage[i]==WHITE){
                int max=WHITE;
                int min=2147483647;
                int maxnum=1;
                int minnum=1;

                for(int y=-3,z=1;y<4;y+=3){
                    for(int x=-3;x<4;x+=3,z++){
                        if(x==0 & y==0){
                            continue;
                        }

                        int temp=0;
                        int stack=0;

                        for(int y2=0;y2<2;y2++) {
                            for (int x2 = 0; x2 < 2; x2++) {
                                try {
                                    temp += preImage[i + x + x2 + (y + y2) * width];
                                    stack++;
                                }
                                catch(ArrayIndexOutOfBoundsException e){
                                    continue;
                                }
                            }
                        }//3칸 떨어진 픽셀 주변 9칸 평균 구하기
                        temp/=stack;
                        if(max>0){
                            if(temp>0) {
                                if (max < temp) {
                                    max = temp;
                                    maxnum = z;
                                }
                            }//두수가 모두 양수이면 더 큰쪽이 밝음
                            else{
                                max = temp;
                                maxnum = z;
                            }//temp만 음수이면 temp가 더 밝음
                        }
                        else{
                            if(max<temp){
                                max = temp;
                                maxnum = z;
                            }//두수 모두 음수이면 큰쪽이 밝음
                        }
                        if(max>temp){
                            max=temp;
                            maxnum=z;
                        }//temp만 음수이면 temp가 더 밝음


                        if(min>0){
                            if(temp>0) {
                                if (min > temp) {
                                    min = temp;
                                    minnum = z;
                                }
                            }//두수가 모두 양수이면 더 작은 쪽이 어두움
                        }
                        else{
                            if(temp>0){
                                min = temp;
                                minnum = z;
                            }//temp만 양수이면 temp 가 더 어두움
                            if(min>temp){
                                min = temp;
                                minnum = z;
                            }//두수 모두 음수이면 큰쪽이 밝음

                        } //가장 0에가까운 음수=max(백) / 가장 0에가까운 양수 =min(흑)
                    }
                }//자신으로부터 3칸씩 떨어진 픽셀들을 구하고 그
                //픽셀 주위9칸의 평균을 구하여 가장 밝은 부분과 가장 어두운 부분을 구한다.

                if(maxnum==1 && minnum>6){
                    if(min>0) return 1;//좌측 상단이 광원
                    return 27;//우측하단이 광원
                }
                if(maxnum==2 && minnum>6){
                    if(min>0) return 2;//뒤쪽 상단이 광원
                    return 26;//전면 하단이 광원
                }


            }
        }

        return 1;
    }//광원 찾기
    //            (뒤)
    // 1 2 3    10 11 12    19  20  21
    // 4 5 6    13    15    22  23  24
    // 7 8 9    16 17 18    25  26  27
    // 맨위        중간        하단



    /*
    int[] findbrightness(int[] edgeImage,int[] preimage,int width){

        int[] b=new int[8];//중간에 연산용으로 씀

        int[] edgeImage_prosessed=new int[edgeImage.length];//각 에지 마스크들의 결과(방향성) 저장
        int[] newsarray=new int[8];//방향성 인자 개수를 저장 중간 연산용으로도 씀

        for(int i=0;i<preimage.length;i++){
            //방향성 확인 구현부
            int news=0; // n부터 1로 ~8까지 배열 순으로
            if(edgeImage[i]==0xffffffff){

                for(int x=-1,z=0;x<2;x++){
                    for(int y=-1;y<2;y++,z++){

                        b[z]=Math.abs(preimage[i]-preimage[i+x+y*width]);
                        if(x==0 & y==0) z--;//자기 자신은 건너 뜀
                    }
                }//주변 8개 픽셀간 차이 검출


                int max=0;
                int j=0;
                int temp=0;
                for(;j<7;j++){
                    for(int k=1+j;k<7;k++){
                        if(b[k]<b[k+1]) max=k+1;
                    }
                    temp=b[max];
                    b[max]=b[j];
                    b[j]=temp;

                    newsarray[j]=max+1;

                }//news배열에 연산 순서 정리(버블 정렬)

                for(int k=1;k<9;k++) {
                    if((news=Tracing(preimage, k))==1) break;
                } //그 방향의 픽셀들이 일정한 값으로 변화하는지 확인

                edgeImage_prosessed[i]=news;//방향 저장
            }//에지검출 된 부분만

        }

        for(int i=0;i<edgeImage_prosessed.length;i++){
            newsarray[edgeImage_prosessed[i]]++;
        }//news배열 생성

        int max=0;
        int j=0;
        int temp=0;
        for(;j<7;j++){
            for(int i=1+j;i<7;i++){
                if(newsarray[i]<newsarray[i+1]) max=i+1;
            }
            temp=newsarray[max];
            newsarray[max]=newsarray[j];
            newsarray[j]=temp;

        }//news배열 정리(버블 정렬)

        return newsarray;
    }//광원 찾기 (에지검출마스크, 광원을 찾을 이미지, 광원을 찾을 이미지의 너비) 광원일 확률이 높은 방향 순으로 리턴

    int Tracing(int[] preImage,int aspect){

        for(int i=0;i<50;i++)


        return 0;
    }*/

    int[] MakeSurfaceMask(int[] edgeImage, int[] preImage,int aspect){
        int[] SurfaceMask=new int[preImage.length];

        int difference_r=40;//색 차이
        int difference_g=40;//색 차이
        int difference_b=40;//색 차이

        //--------------------------------------------------------------------------


        //-----------------------------------------------------------------------

        for(int i=0;i<preImage.length;i++){
            if(edgeImage[i]==-1){

            }
        }

        return SurfaceMask;
    }//(edge마스크 ,YUV이미지, 광원의 방향(8방))
/*
    int[] makeentityMask(int[] edgeImage,int width, int height){
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){

                for(int y=-1;y<2;y++){
                    for(int x=-1;x<2;x++){

                    }
                }
            }
        }
    }
    */

    int[] binary_trasform(int[] inputImage){
        for(int i=0;i<inputImage.length;i++){
            if(inputImage[i]<0&&inputImage[i]>-13421772) inputImage[i]=Color.argb(255, 255, 255, 255);
            else{
                inputImage[i]=Color.argb(255, 0, 0, 0);;
            }
        }
        return inputImage;
    }

    int[] hough_transform(int[] inputImage,int width,int height){

        int diagonal=(int)Math.sqrt(width*width+height*height);
        int SHETA=270;
        int[] output=new int[width*height];//허프 변환 이미지지
        float pi = 3.141592654f/180.0f;
        int[][] H=new int[diagonal][SHETA];
        int[][] transformed = new int[width][height];
        for(int i=0;i<diagonal;i++){
            for(int j=0;j<SHETA;j++){
                H[i][j]=0;
            }
        }
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++) transformed[i][j]=0;
        }
        //초기화-----------------------------------------------------------------

        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                if(/*inputImage[i*width+j]>-400000&&*/inputImage[i*width+j]==WHITE){
                    for(int k=0;k<SHETA;k++){
                        int temp=(int)(j*Math.cos(k)+i*Math.sin(k));
                        if(temp>=0&&temp<diagonal){
                            H[temp][k]+=1;
                        }
                    }
                }
            }
        }
        for(int i=0;i<diagonal;i++){
            for(int j=0;j<SHETA;j++){
                if(H[i][j]>250) Log.d("z",H[i][j]+":"+i+":"+j);
            }
        }
        int[] tempstack;
        int dots;
        if(height>width) {
            tempstack = new int[height];
        }
        else{
            tempstack = new int[width];
        }
        int blackcount=11;

        for(int i=1;i< diagonal;i++) {//거리
            for (int j = 0; j < SHETA; j++) {//각도
                if(j<45||(j>135&&j<225)){
                    dots=width*7/10;
                    if (H[i][j] > dots)//dots개 이상의 점이 한 줄에 있음
                    {
                        blackcount=11;//------------------------------------------------------------

                        for(int x=0;x<width;x++){
                            int y=(int)((i-x*Math.cos(j))/Math.sin(j));
                            if(y*width+x>=width*height||y<0) continue;
                            try {
                                output[y *width + x] = Color.argb(255, 255, 0, 0);
                                tempstack[x]=y;
                                if(inputImage[y*width+x]==WHITE){
                                    blackcount=0;
                                    transformed[y][x]+=1;
                                }
                                else{
                                    blackcount++;//--------------------------------------------------------------
                                }
                                if(blackcount==10){
                                    blackcount=0;

                                    //break;
                                }//이정도면 이어진 선이 아니다.
                            }
                            catch (ArrayIndexOutOfBoundsException e){
                            }
                        }
                    }
                }
                else{
                    dots=height*7/10;
                    if (H[i][j] > dots)//dots개 이상의 점이 한 줄에 있음
                    {
                        for(int x=0;x<width;x++){
                            int y=(int)((i-x*Math.sin(j))/Math.cos(j));
                            if(y+width*x>=width*height||y<0) continue;
                            try {
                                output[y +width * x] = Color.argb(255, 255, 0, 0);
                                tempstack[x]=y;
                                if(inputImage[y+width*x]==WHITE){
                                    blackcount=0;
                                    transformed[x][y]+=1;
                                }
                                else blackcount++;
                            }
                            catch (ArrayIndexOutOfBoundsException e){
                            }
                        }
                    }
                }
            }
        }

        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                if(transformed[i][j]>2)
                inputImage[j +width * i] = Color.argb(255, 255, 255, 0);
            }
        }
    /*
      윤곽선을 검출을 하게되면 실제로 두껍게 감지되기 때문에,
      직선도 여러줄이 감지가 되게 된다.
      여러줄이 감지가 되는것을 방지하고 감지된 직선을 얇게 하기 위해서 밑의 코드를 삽입
    */


    /*
                    for(int l=-4;l<4;l++)
                    {
                        for(int m=-4;m<4;m++)
                        {
                            if(d+l>=0 && d+l< diagonal && k+m >= 0 && k+m < 180)
                            {
                                if(H[d+l][k+m] > max)
                                    max = H[d+l][k+m];
                            }
                        }
                    }

                    if(max > H[d][k]) continue;
                    //if((k <= 45 && k <= 315) || (k >= 135 && k <= 225))
                    {
                        for(int j=0;j<width;j++)
                        {
                            int i = (int)((d-j*LUT_SIN[k])/LUT_COS[k]);

                            if(i < height && i > 0)
                            {
                                if(inputImage[width*j+i] > SHARP_THRESHOLD)
                                    w++;
                                else
                                {
                                    if(w > max_w)
                                        max_w = w;
                                    w=0;
                                }
                            }
                        }
                        if(max_w > LINE_THRESHOLD)
                        {
                            for(j=0;j<nW;j++)
                            {
                                i = (int)((d-j*LUT_SIN[k])/LUT_COS[k]);

                                if(i < nH && i >= 0)
                                {
                                    //if(inputImage[i][j] > SHARP_THRESHOLD)
                                    outImage[i][j] += 1;
                                }
                            }
                        }
                    }
*/

                    //if(LUT_COT[k] <= 3.141592/4 && LUT_COT[k] >= 3.141592*5/4)
                    //else
                    /*{
                        w = 0;
                        max_w = w;
                        for(i=0;i<nH;i++)
                        {
                            j = (int)((d-i*LUT_COS[k])/LUT_SIN[k]);

                            if(j < nW && j > 0)
                            {
                                if(inputImage[i][j] > SHARP_THRESHOLD)
                                    w++;
                                else
                                {
                                    if(w > max_w)
                                        max_w = w;
                                    w=0;
                                }
                            }
                        }
                        if(max_w > LINE_THRESHOLD)
                        {
                            for(i=0;i<nH;i++)
                            {
                                j = (int)((d-i*LUT_COS[k])/LUT_SIN[k]);

                                if(j < nW && j >= 0)
                                {
                                    //if(inputImage[i][j] > SHARP_THRESHOLD)
                                    outImage[i][j] += 1;

                                }

                            }
                        }
                    }

                }
            }
        }*/

       return inputImage;
    }

}//2진화 이미지를 이용할 것
