package sklee.mygame;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by wei on 2015/2/1.
 */
public class GameUimedium extends ActionBarActivity {

    private Button[] button =new Button[8];
    private TextView[] textview =new TextView[8];
    int Rx,Ry,BUTTON_WIDTH,BUTTON_HEIGHT,pcount=0;
    private RelativeLayout board;
    private int[] xbuff=new int[8];
    private int[] ybuff=new int[8];
    private int[] pbuff=new int[50];
    private boolean compareprocess=false,done=false;

    //    private TextView tvStatus;
    private TextView continuousTime;
    private Boolean show =true;
    private float touchX,touchY;
    private int Back,continuous;
    private String Status;//遊戲狀態
    private int nowNumber;
    private ProgressBar bar;


    public static int counter;         // ocunter, set the counter to TIME at beginning
    public static int DELAY_ = 1000;
    public static boolean flag = false;  // judge whether succeed
    public Button btn0;
    public TextView TIME_;
    private Thread thread;

    public int gameTime=7;
    public int readyTime=4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /** Called when the activity is first created. */


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ui2);


//        tvStatus = (TextView)findViewById(R.id.tvStatus);
        continuousTime = (TextView)findViewById(R.id.continuous);
        board =(RelativeLayout) this.findViewById(R.id.board);
        nowNumber = 0;
        continuous = 0;
        Status = "notStart";

        //Timeout
        bar = (ProgressBar)findViewById(R.id.progressBar);
        TIME_ = (TextView)findViewById(R.id.textView2);
        btn0 = (Button)findViewById(R.id.ButtonTime);

        Back = R.drawable.back;


        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btn0.setEnabled(false);


                initUI();
                board.setBackgroundColor(Color.BLUE);
                nowNumber = 0;
                counter = gameTime+readyTime;

                if(Status=="gameEnd"){//上一次若為輸 連續過關次數重計算
                    continuous = 0;
                    continuousTime.setText("連續過關次數:"+String.valueOf(continuous));
                }

                // Progressbar initial set
                bar.setVisibility(View.VISIBLE);
                bar.setMax(counter-readyTime);
                bar.setProgress(counter-readyTime);

             /*   TIME_.setBackgroundResource(R.drawable.icon_12);
                TIME_.setTextColor(Color.parseColor("#000000"));
                TIME_.setGravity(Gravity.CENTER);*/
                TIME_.setText(String.valueOf(readyTime));

                btn0.setEnabled(false);
                Status = "notStart";
                thread = new Thread(r1);
                thread.start();


            }
        });
    }

//        board =(RelativeLayout) this.findViewById(R.id.board);
//        board.post(new Runnable(){
//            @Override
//            public void run() {
//                //CompareAndRand();
//                initUI();
//
//
//            }
//        });






    private Handler handler = new Handler(){        // deliver the signal to layout
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            counter--;


            if(counter <=0 || Status=="gameEnd")
            {
                bar.setProgress(counter);
                board.setBackgroundColor(Color.RED);
                TIME_.setText("Game Over");
                btn0.setEnabled(true);


            }
            else if(Status =="gameWin" )
            {

                TIME_.setText("Game Win");
                btn0.setEnabled(true);

            }
            else if(counter<=5 && counter>=0)
            {


                TIME_.setText(String.valueOf(counter));
                bar.setProgress(counter);
                thread = new Thread(r1);
                thread.start();
            }
            else if(counter<gameTime && counter>=5)
            {
                TIME_.setText("TIME");
                bar.setProgress(counter);
                thread = new Thread(r1);
                thread.start();
                TIME_.setText(String.valueOf(counter));
            }
            else if(counter>gameTime)
            {
                TIME_.setText(String.valueOf(counter-gameTime));
                thread = new Thread(r1);
                thread.start();
            }
            else if(counter == gameTime){


                TIME_.setText("START");
                for(int i = 0; i < 8; i++)   //suppose you have 8 buttons
                {
//                String buttonID = "button" + (i + 1);
//                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
//                button[i] = ((Button) findViewById(resID));
                    button[i].setBackgroundResource(Back);

                }


                Status = "start";

                thread = new Thread(r1);
                thread.start();

            }
            else{
                TIME_.setText(String.valueOf(counter-gameTime));
                thread = new Thread(r1);
                thread.start();
            }
        }
    };






    private Runnable r1 = new Runnable(){
        public void run(){
            try{

                thread.sleep(DELAY_);

                //send message
                Message mesg = new Message();
                mesg.what=1;
                handler.sendMessage(mesg);

            }catch(InterruptedException e)
            {
                e.printStackTrace();
            }

        }
    };



    public void CompareAndRand(){
        //DisplayMetrics metrics = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //Vx = metrics.widthPixels;
        //Vy = metrics.heightPixels;
        //RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        //buttonParams.width = BUTTON_WIDTH;
        //buttonParams.height = BUTTON_HEIGHT;
        for(int i = 0; i < 8; i++)   //suppose you have 8 buttons

        {
            String buttonID = "button" + (i + 1);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            button[i] = ((Button) findViewById(resID));
            button[i].setTag(i+1);


            button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickButton(v);
                }
            });



        }
        Random random = new Random();
        for(int i=0; i<8; i++)
        {
            xbuff[i]=random.nextInt(board.getWidth() - button[i].getWidth());
            ybuff[i]=random.nextInt(board.getHeight() - button[i].getHeight());
        }
        while(!done)
        {
            //boolean done = false;
            pcount=0;
            for(int i=0; i<8; i++){
                for(int j = 0; j < 8; j++){
                    if(Math.abs(xbuff[i] - xbuff[j]) <= button[i].getWidth() && Math.abs(ybuff[i] - ybuff[j]) <= button[i].getHeight() && i!=j){

                        pbuff[pcount]=j;
                        pcount++;
                    }
                }
            }
            if(pcount!=0){
                for(int i=0;i<pcount;i++)
                    xbuff[pbuff[i]]=random.nextInt(board.getWidth()-button[0].getWidth());
            }
            else done=true;
        }
        done=false;
/*
while(!done) {
    pcount=0;
     for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {

            if (Math.abs(xbuff[i] - xbuff[j]) <= button[i].getWidth() && Math.abs(ybuff[i] - ybuff[j]) <= button[i].getHeight() && i!=j) {

                    pbuff[pcount] = j;
                    pcount++;

            }
        }

     }
    if(pcount!=0){
        for(int i=0;i<pcount;i++)
        xbuff[pbuff[i]]=random.nextInt(board.getWidth()-button[0].getWidth());
    }
     else  done=true;



}
*/
    }




    public void initUI() {



        CompareAndRand();

        for(int i = 0; i < 8; i++)   //suppose you have 8 buttons
        {
//                String buttonID = "button" + (i + 1);
//                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
//                button[i] = ((Button) findViewById(resID));
            button[i].setVisibility(View.VISIBLE);
            switch (i+1){
                case 1:
                    button[i].setBackgroundResource(R.drawable.a1);
                    break;
                case 2:
                    button[i].setBackgroundResource(R.drawable.a2);
                    break;
                case 3:
                    button[i].setBackgroundResource(R.drawable.a3);
                    break;
                case 4:
                    button[i].setBackgroundResource(R.drawable.a4);
                    break;
                case 5:
                    button[i].setBackgroundResource(R.drawable.a5);
                    break;
                case 6:
                    button[i].setBackgroundResource(R.drawable.a6);
                    break;
                case 7:
                    button[i].setBackgroundResource(R.drawable.a7);
                    break;
                case 8:
                    button[i].setBackgroundResource(R.drawable.a8);
                    break;
            }


        }
        for (int i = 0; i < 8; i++)   //suppose you have four buttons
        {
            //String buttonID = "button" + (i + 1);
            //int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Rx=params.leftMargin = xbuff[i];
            Ry=params.topMargin = ybuff[i];
            //button[i] = ((Button) findViewById(resID));
            button[i].setLayoutParams(params);
            Log.e(String.valueOf(i), "X=" + String.valueOf(Rx) + "   Y=" + String.valueOf(Ry));
            //RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            //Random random = new Random();
            // Display display = getWindowManager().getDefaultDisplay();

            //DisplayMetrics metrics = new DisplayMetrics();
            //getWindowManager().getDefaultDisplay().getMetrics(metrics);
            //TextView TextView1 = (TextView)findViewById(R.id.TextView01);
            /****************************************************************
             textview[0] = (TextView) findViewById(R.id.textView1);
             textview[1] = (TextView) findViewById(R.id.textView2);
             textview[2] = (TextView) findViewById(R.id.textView3);
             textview[3] = (TextView) findViewById(R.id.textView4);
             textview[4] = (TextView) findViewById(R.id.textView5);
             textview[5] = (TextView) findViewById(R.id.textView6);
             textview[6] = (TextView) findViewById(R.id.textView7);
             textview[7] = (TextView) findViewById(R.id.textView8);
             textview[0].setText("螢幕範圍 "+metrics.widthPixels+" X "+metrics.heightPixels);
             ****************************************************************/
            //float x=metrics.xdpi;
            //float y=metrics.ydpi;


            //Point size = new Point();
            //display.getSize(size);
            //params = random.nextInt();
            //int width = size.x;
            //int height = size.y;

            //button.setLayoutParams(params);

            //LayoutParams lp = new LinearLayout.LayoutParams(widthOfButtons,LayoutParams.WRAP_CONTENT);
            //Button[] button;

            //button[i].setLeft(Rx);
            //button[i].setTop(Ry);
            //int[] location = new int[2];
            //button[0].getLocationOnScreen(location);
            //int aa = location[0];
            //int bb = location[1];
            //textview[0].setText("設定變化範圍 "+aa+" X "+bb);

            //button[i].setX(Rx);
            //button[i].setY(Ry);
            //textview[i+1].setText("設定變化範圍 "+(i+1)+" "+Rx+" X "+Ry);

            //button[i].setHeight(params.leftMargin);
            //button[i].setWidth(params.topMargin);
            //button[i].setLayoutParams(params.leftMargin);
            //button[i].setLayoutParams(params);
            //button[i].setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

    }

    public void clickButton(View v) {
        Log.e("ANS", String.valueOf(v.getTag()));

        if (Status == "notStart") {
//            tvStatus.setText("遊戲還沒開始了");
        }else if (Status == "start") {

            if (String.valueOf(nowNumber+1).equals(String.valueOf(v.getTag()))) {
//                tvStatus.setText("找到" + String.valueOf(nowNumber+1));
                playSound(R.raw.correct);//播放正確的音樂
                waiting(100);
                button[nowNumber].setVisibility(View.VISIBLE);

                switch (nowNumber+1){
                    case 1:
                        button[nowNumber].setBackgroundResource(R.drawable.a1);
                        break;
                    case 2:
                        button[nowNumber].setBackgroundResource(R.drawable.a2);
                        break;
                    case 3:
                        button[nowNumber].setBackgroundResource(R.drawable.a3);
                        break;
                    case 4:
                        button[nowNumber].setBackgroundResource(R.drawable.a4);
                        break;
                    case 5:
                        button[nowNumber].setBackgroundResource(R.drawable.a5);
                        break;
                    case 6:
                        button[nowNumber].setBackgroundResource(R.drawable.a6);
                        break;
                    case 7:
                        button[nowNumber].setBackgroundResource(R.drawable.a7);
                        break;
                    case 8:
                        button[nowNumber].setBackgroundResource(R.drawable.a8);
                        break;
                }


                nowNumber++;

                if (nowNumber == 8) {

//                    tvStatus.setText("找到最後一個數字遊戲勝利");
                    board.setBackgroundColor(Color.YELLOW);
                    Status = "gameWin";

                    continuous++;
                    continuousTime.setText("連續過關次數:"+String.valueOf(continuous));
                }
            }
            else if(nowNumber+1 > Integer.valueOf(String.valueOf(v.getTag()))){

            }
            else{
                playSound(R.raw.incorrect);//播放正確的音樂
                waiting(100);
                board.setBackgroundColor(Color.RED);
                button[nowNumber].setVisibility(View.VISIBLE);

                switch (nowNumber+1) {
                    case 1:
                        button[0].setBackgroundResource(R.drawable.e1);
                        button[1].setBackgroundResource(R.drawable.e2);
                        button[2].setBackgroundResource(R.drawable.e3);
                        button[3].setBackgroundResource(R.drawable.e4);
                        button[4].setBackgroundResource(R.drawable.e5);
                        button[5].setBackgroundResource(R.drawable.e6);
                        button[6].setBackgroundResource(R.drawable.e7);
                        button[7].setBackgroundResource(R.drawable.e8);
                        break;
                    case 2:
                        button[1].setBackgroundResource(R.drawable.e2);
                        button[2].setBackgroundResource(R.drawable.e3);
                        button[3].setBackgroundResource(R.drawable.e4);
                        button[4].setBackgroundResource(R.drawable.e5);
                        button[5].setBackgroundResource(R.drawable.e6);
                        button[6].setBackgroundResource(R.drawable.e7);
                        button[7].setBackgroundResource(R.drawable.e8);
                        break;
                    case 3:
                        button[2].setBackgroundResource(R.drawable.e3);
                        button[3].setBackgroundResource(R.drawable.e4);
                        button[4].setBackgroundResource(R.drawable.e5);
                        button[5].setBackgroundResource(R.drawable.e6);
                        button[6].setBackgroundResource(R.drawable.e7);
                        button[7].setBackgroundResource(R.drawable.e8);
                        break;
                    case 4:
                        button[3].setBackgroundResource(R.drawable.e4);
                        button[4].setBackgroundResource(R.drawable.e5);
                        button[5].setBackgroundResource(R.drawable.e6);
                        button[6].setBackgroundResource(R.drawable.e7);
                        button[7].setBackgroundResource(R.drawable.e8);
                        break;
                    case 5:
                        button[4].setBackgroundResource(R.drawable.e5);
                        button[5].setBackgroundResource(R.drawable.e6);
                        button[6].setBackgroundResource(R.drawable.e7);
                        button[7].setBackgroundResource(R.drawable.e8);
                        break;
                    case 6:
                        button[5].setBackgroundResource(R.drawable.e6);
                        button[6].setBackgroundResource(R.drawable.e7);
                        button[7].setBackgroundResource(R.drawable.e8);
                        break;
                    case 7:
                        button[6].setBackgroundResource(R.drawable.e7);
                        button[7].setBackgroundResource(R.drawable.e8);
                        break;
                    case 8:
                        button[7].setBackgroundResource(R.drawable.e8);
                        break;
                }

//                tvStatus.setText("沒有找到"+String.valueOf(nowNumber+1));
                Status="gameEnd";

            }
        }else if(Status=="gameEnd"){
//            tvStatus.setText("遊戲已經結束了");
        }else if(Status=="gameWin"){

//            tvStatus.setText("遊戲已經勝利請重新開始");
        }else{

        }

    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        touchX = event.getX();       // 觸控的 X 軸位置
//        touchY = event.getY();  // 觸控的 Y 軸位置
//
//
//        // 判斷觸控動作
//        switch( event.getAction() ) {
//
//            case MotionEvent.ACTION_DOWN:  // 按下
//                Log.e("Touch","X="+String.valueOf(touchX)+" ,Y="+String.valueOf(touchY));
//
//
//                if(Status== "notStart"){
//                    tvStatus.setText("遊戲還沒開始了");
//                }else if(Status == "start") {
//                    //                switch (nowNumber){
////                    case 1:
//                    if(touchX>=button[nowNumber].getLeft() && touchX<=button[nowNumber].getLeft()+button[nowNumber].getWidth()&&
//                            touchY>=button[nowNumber].getTop() && touchY<=button[nowNumber].getTop()+button[nowNumber].getHeight()){
////                           Log.e("找到1",String.valueOf(touchX)+","+String.valueOf(touchY));
//                           tvStatus.setText("找到"+String.valueOf(nowNumber+1));
////                           c(nowNumber);
////                           nowNumber = 2;
//                           playSound(R.raw.correct);//播放正確的音樂
//                           waiting(200);
//                        button[nowNumber].setVisibility(View.VISIBLE);
//                        nowNumber++;
//
//                        if(nowNumber == 7){
//                            tvStatus.setText("找到最後一個數字遊戲勝利");
//                            board.setBackgroundColor(Color.YELLOW);
//                            Status="gameWin";
//                        }
////
//                    }else{
//                        playSound(R.raw.incorrect);//播放正確的音樂
//                        waiting(200);
//                        board.setBackgroundColor(Color.RED);
//                        tvStatus.setText("沒有找到"+String.valueOf(nowNumber+1));
//                        Status="gameEnd";
//                    }
////                        break;
//
//
////                    default:
////                        break;
////                }
//
//
//
//                }
//                else if(Status=="gameEnd"){
//                    tvStatus.setText("遊戲已經結束了");
//                }else if(Status=="gameWin"){
//                    tvStatus.setText("遊戲已經勝利請重新開始");
//                }else{
//
//                }
//
//
//                break;
//
//            case MotionEvent.ACTION_MOVE:  // 拖曳移動
//                break;
//
//            case MotionEvent.ACTION_UP:  // 放開
//
//                break;
//
//        }
//
//
//        // TODO Auto-generated method stub
//        return super.onTouchEvent(event);
//    }

    public void playSound(int sound) {
        MediaPlayer mp = MediaPlayer.create(this, sound);
        mp.setVolume((float) .5, (float) .5);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }

        });
    }

    public static void waiting(int n) {
        long t0, t1;
        t0 = System.currentTimeMillis();
        do {
            t1 = System.currentTimeMillis();
        } while ((t1 - t0) < (n));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_ui, menu);
        return true;
    }




    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

        if (thread != null) {
            if (!thread.isInterrupted()) {
                thread.interrupt();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


