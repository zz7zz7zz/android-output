package com.open.test.textview;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.open.test.R;

/**
 * Created by Administrator on 2017/2/27.
 */

public class TextViewLineCountActivity extends Activity {

    private final String TAG = "TextViewActivity";

    private final String [] TEST_STRING_ARRAY = {
            "Mohra film’s famous song to be recreated for Abbas Mustan’s film Machine"
    };
    private int currint_index = -1;

    private TextView setText_content;
    private TextView setText_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textview1);
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

                    setText_content.setText(TEST_STRING_ARRAY[currint_index]);
                    TextPaint paint = new TextPaint();
                    paint.setTextSize(80);
                    StaticLayout mStaticLayout = new StaticLayout(TEST_STRING_ARRAY[currint_index],paint,720, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);

                    setText_time.setText("line = " + (mStaticLayout.getLineCount()));
                    break;
            }
        }
    };
}
