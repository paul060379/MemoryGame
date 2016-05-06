package sklee.mygame;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by Paul on 2015/12/24.
 */
public class Initialgame2 extends ActionBarActivity {

    Button easyGame;
    Button midGame;
    Button hardGame;
    Drawable easyTapped, midTapped, hardTapped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial2);

        easyGame = (Button) findViewById(R.id.easyButtonView);
        midGame = (Button) findViewById(R.id.midButtonView);
        hardGame = (Button) findViewById(R.id.hardButtonView);


        easyGame.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:                                           //if c01 button is pressed
                        easyGame.setBackgroundResource(R.drawable.button_easy_pressed);    //change button background to "button_easy_pressed".
                        easyTapped = easyGame.getBackground();
                        easyGame.setBackground(easyTapped);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:                                             //after pressing
                        easyGame.setBackgroundResource(R.drawable.button_easy);            //change button background to "button_easy".
                        easyTapped = easyGame.getBackground();
                        easyGame.setBackground(easyTapped);
                        break;
                }
                return false;
            }
        });

        midGame.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        midGame.setBackgroundResource(R.drawable.button_medium_pressed);
                        midTapped = midGame.getBackground();
                        midGame.setBackground(midTapped);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        midGame.setBackgroundResource(R.drawable.button_medium);
                        midTapped = midGame.getBackground();
                        midGame.setBackground(midTapped);
                        break;
                }
                return false;
            }
        });

        hardGame.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        hardGame.setBackgroundResource(R.drawable.button_hard_pressed);
                        hardTapped = hardGame.getBackground();
                        hardGame.setBackground(hardTapped);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        hardGame.setBackgroundResource(R.drawable.button_hard);
                        hardTapped = hardGame.getBackground();
                        hardGame.setBackground(hardTapped);
                        break;
                }
                return false;
            }
        });

        easyGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(Initialgame2.this, GameUieasy.class);
                startActivity(intent);

            }
        });


        midGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(Initialgame2.this, GameUimedium.class);
                startActivity(intent);

            }
        });



        hardGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(Initialgame2.this, GameUihard.class);
                startActivity(intent);

            }
        });

    }
}


