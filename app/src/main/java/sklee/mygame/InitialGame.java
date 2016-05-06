package sklee.mygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Paul on 2016/05/05.
 */

public class InitialGame extends ActionBarActivity {

    Button startButton, exitButton;
    Button easyButton, midButton, hardButton;
    LinearLayout mainButtonLayout, chooseLevelLayout;
//    Drawable startButtonTapped, exitButtonTapped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial);
        findViews();
        setButton();





//        startButton.setOnTouchListener(new View.OnTouchListener(){
//            @Override
//            public boolean onTouch(View v, MotionEvent event){
//                int action = event.getAction();
//
//                switch (action){
//                    case MotionEvent.ACTION_DOWN:
//                        startButton.setBackgroundResource(R.drawable.button_game_start_pressed);
//                        startButtonTapped = startButton.getBackground();
//                        startButton.setBackground(startButtonTapped);
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        startButton.setBackgroundResource(R.drawable.button_game_start);
//                        startButtonTapped = startButton.getBackground();
//                        startButton.setBackground(startButtonTapped);
//                        break;
//                }
//                return false;
//            }
//        });
//
//        exitButton.setOnTouchListener(new View.OnTouchListener(){
//            @Override
//            public boolean onTouch(View v, MotionEvent event){
//                int action = event.getAction();
//                switch (action){
//                    case MotionEvent.ACTION_DOWN:
//                        exitButton.setBackgroundResource(R.drawable.button_game_leave_pressed);
//                        exitButtonTapped = exitButton.getBackground();
//                        exitButton.setBackground(exitButtonTapped);
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        exitButton.setBackgroundResource(R.drawable.button_game_leave);
//                        exitButtonTapped = exitButton.getBackground();
//                        exitButton.setBackground(exitButtonTapped);
//                        break;
//                }
//                return false;
//            }
//        });
//
//        startButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent();
//                intent.setClass(InitialGame.this, Initialgame2.class);
//                startActivity(intent);
//
//
//            }
//        });
//
//
//        exitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AlertDialog.Builder(InitialGame.this)
//                        .setTitle("確認視窗")
//                        .setMessage("是否結束?")
//                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
//
//                            }
//                        })
//
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // TODO Auto-generated method stub
//
//                            }
//                        })
//
//                        .show();
//            }
//        });


    }

    private void findViews(){

        mainButtonLayout = (LinearLayout) findViewById(R.id.mainButtonLayout);
        chooseLevelLayout = (LinearLayout) findViewById(R.id.chooseLevelLayout);

        startButton = (Button) findViewById(R.id.startButtonView);
        exitButton = (Button) findViewById(R.id.endButtonView);

        easyButton = (Button) findViewById(R.id.easyButton);
        midButton = (Button) findViewById(R.id.midButton);
        hardButton = (Button) findViewById(R.id.hardButton);

        mainButtonLayout.setVisibility(View.VISIBLE);


    }
    private void setButton(){

        startButton.setOnClickListener(StartToLevel);
        easyButton.setOnClickListener(GoLevel);
        midButton.setOnClickListener(GoLevel);
        hardButton.setOnClickListener(GoLevel);

    }

    private View.OnClickListener StartToLevel = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            mainButtonLayout.setVisibility(view.GONE);
            chooseLevelLayout.setVisibility(view.VISIBLE);
        }
    };

    private View.OnClickListener GoLevel = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()){

                case R.id.easyButton:

                    intent.setClass(InitialGame.this, GameUieasy.class);
                    startActivity(intent);
                    break;

                case R.id.midButton:

                    intent.setClass(InitialGame.this, GameUimedium.class);
                    startActivity(intent);
                    break;

                case R.id.hardButton:

                    intent.setClass(InitialGame.this, GameUihard.class);
                    startActivity(intent);
                    break;

            }


        }
    };
}
