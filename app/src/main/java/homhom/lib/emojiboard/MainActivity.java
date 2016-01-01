package homhom.lib.emojiboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import homhom.lib.emojiboard.bean.Emoji;
import homhom.lib.emojiboard.bean.EmojiPacket;
import homhom.lib.emojiboard.ui.EmojiViewPager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EmojiViewPager emojiViewPager = (EmojiViewPager) findViewById(R.id.emojiView);
        EmojiPacket emojiPacket = new EmojiPacket();
        emojiPacket.mColumn = 5;
        emojiPacket.mId = 1;
        ArrayList<Emoji> list = new ArrayList<>();
        for(int i = 0; i < 25; i ++demodedemsj){
            Emoji emoji = new Emoji();
            emoji.mId = i;
            list.add(emoji);
        }
        emojiPacket.mEmojis = list;
        emojiViewPager.setEmojiPacket(emojiPacket);
    }
}
