package homhom.lib.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.drawee.backends.pipeline.Fresco;

import homhom.lib.emojiboard.core.BaseEmojiProvider;
import homhom.lib.emojiboard.core.EmojiBoardConfiguration;
import homhom.lib.emojiboard.mgr.EmojiBoardFixer;

/**
 * Created by linhonghong on 2016/1/11.
 */
public class StartActivity extends AppCompatActivity {

    private Button mBtnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
            }
        });

        Fresco.initialize(this);

        EmojiBoardConfiguration configuration = EmojiBoardConfiguration.
                Builder(this).
                setEmojiDirectory(Environment.getExternalStorageDirectory().getAbsolutePath()+"/emojiview/emoji/").
                setEmojiProvider(new FrescoEmojiProvider()).
                build();
        EmojiBoardFixer.getInstance().initConfiguration(configuration);
    }
}