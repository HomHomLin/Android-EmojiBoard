package homhom.lib.emojiboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import homhom.lib.emojiboard.bean.Emoji;
import homhom.lib.emojiboard.bean.EmojiPacket;
import homhom.lib.emojiboard.ui.EmojiInterBoard;
import homhom.lib.emojiboard.ui.EmojiViewPager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EmojiInterBoard emojiViewPager = (EmojiInterBoard) findViewById(R.id.emojiView);
        EmojiPacket emojiPacket = new EmojiPacket();
        emojiPacket.mColumn = 4;
        emojiPacket.mId = 1;
        emojiPacket.mShowDelete = true;
        ArrayList<Emoji> list = new ArrayList<>();
        for(int i = 0; i < 25; i ++){
            Emoji emoji = new Emoji();
            emoji.mId = i;
            list.add(emoji);
        }
        emojiPacket.mEmojis = list;
        emojiViewPager.setEmojiPacket(emojiPacket);
    }
}
