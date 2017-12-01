package com.open.test.textview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.open.test.R;

/**
 * Created by Administrator on 2017/2/27.
 */

public class TextViewActivity extends Activity {

    private final String TAG = "TextViewActivity";

    private final String [] TEST_STRING_ARRAY = {
            "Mohra film’s famous song to be recreated for Abbas Mustan’s film Machine",

            "Mariah Carey Congratulates Nick Cannon On Birth Of Son, Not Cut Out For Reality",

            "17 churchgoers killed, 62 injured in Meghalaya road mishap",

            "I can’t look plastic all the time, says Alia Bhatt",

            "7 steps to freedom from debt",

            "Nokia 6 first impressions: not a gamechanger, but a good start",

            "Google’s New AI Technology To Fight Abusive ‘Troll’ on Internet",

            "Fun moments from Jio Filmfare Awards East 2017",

            "Nokia 6 first impressions: not a gamechanger, but a good start",

            "2500 Rape Cases Registered In This State Of South India In 2016",

            "9 Killed, 19 Rescued After Boat Mishap off Tamil Nadu’s Tuticorin",

            "Tesla is already showing how the insurance industry will be disrupted by self-driving cars",

            "Not Chimpanzees But Dogs And Kids Are Similar In Social Intelligence"
    };
    private int currint_index = -1;

    private TextView setText_content;
    private TextView setText_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textview);
        initView();
    }

    private void initView(){
        findViewById(R.id.setText).setOnClickListener(clickListener);
        setText_content = (TextView)findViewById(R.id.setText_content);
        setText_time = (TextView)findViewById(R.id.setText_time);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){

                case R.id.setText:
                    if(currint_index >= TEST_STRING_ARRAY.length-1 || currint_index < 0){
                        currint_index = 0;
                    }else{
                        currint_index ++;
                    }

                    long start = System.currentTimeMillis();
                    setText_content.setText(TEST_STRING_ARRAY[currint_index]);
                    long end = System.currentTimeMillis();
                    Log.v(TAG,"cost = " + (end - start));

                    setText_time.setText("cost = " + (end - start));
                    break;
            }
        }
    };
}
