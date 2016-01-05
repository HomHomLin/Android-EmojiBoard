package homhom.lib.emojiboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import homhom.lib.emojiboard.bean.Emoji;
import homhom.lib.emojiboard.bean.EmojiPacket;
import homhom.lib.emojiboard.ui.EmojiBoard;
import homhom.lib.emojiboard.ui.EmojiPagerBoard;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EmojiBoard emojiViewPager = (EmojiBoard) findViewById(R.id.emojiView);
        ArrayList<EmojiPacket> lists = new ArrayList<>();
        for(int x = 0 ; x < 5; x ++) {
            EmojiPacket emojiPacket = new EmojiPacket();
            emojiPacket.mColumn = 4;
            emojiPacket.mId = 1;
            emojiPacket.mShowDelete = true;
            ArrayList<Emoji> list = new ArrayList<>();
            for (int i = 0; i < 15 * (x + 1); i++) {
                Emoji emoji = new Emoji();
                emoji.mId = i;
                list.add(emoji);
            }
            emojiPacket.mEmojis = list;
            lists.add(emojiPacket);
        }
        emojiViewPager.setEmojiPackets(lists);
    }
}
