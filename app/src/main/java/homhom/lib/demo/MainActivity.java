package homhom.lib.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import homhom.lib.emojiboard.bean.Emoji;
import homhom.lib.emojiboard.bean.EmojiPacket;
import homhom.lib.emojiboard.mgr.EmojiBoardFixer;
import homhom.lib.emojiboard.ui.EmojiBoard;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(EmojiBoardFixer.getInstance().getEmojiManager().getEmojiPackets() != null
                && EmojiBoardFixer.getInstance().getEmojiManager().getEmojiPackets().size() > 0){
            return;
        }
        EmojiBoard emojiViewPager = (EmojiBoard) findViewById(R.id.emojiView);
        ArrayList<EmojiPacket> lists = new ArrayList<>();
        for(int x = 0 ; x < 5; x ++) {
            EmojiPacket emojiPacket = new EmojiPacket();
            emojiPacket.mColumn = 4;
            emojiPacket.mId = 1;
            emojiPacket.mShowDelete = true;
            emojiPacket.mPacketInfo.mPacketName = x + "";
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
