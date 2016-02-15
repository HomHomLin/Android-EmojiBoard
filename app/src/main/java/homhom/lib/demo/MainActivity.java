package homhom.lib.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import homhom.lib.emojiboard.bean.Emoji;
import homhom.lib.emojiboard.bean.EmojiPacket;
import homhom.lib.emojiboard.core.EmojiPagerBoardProvider;
import homhom.lib.emojiboard.mgr.EmojiBoardFixer;
import homhom.lib.emojiboard.ui.BaseEmojiViewPager;
import homhom.lib.emojiboard.ui.EmojiBoard;

public class MainActivity extends AppCompatActivity {

    private EmojiBoard emojiViewPager;
    private EmojiPacket emojiPacket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emojiViewPager = (EmojiBoard) findViewById(R.id.emojiView);
//        emojiPacket = new EmojiPacket();
//        emojiPacket.mColumn = 4;
//        emojiPacket.mId = 1;
//        emojiPacket.mShowDelete = true;
//        emojiPacket.mPacketInfo.mPacketName = "";
//        ArrayList<Emoji> list = new ArrayList<>();
//        for (int i = 0; i < 15; i++) {
//            Emoji emoji = new Emoji();
//            emoji.mId = i;
//            list.add(emoji);
//        }
//        emojiPacket.mEmojis = list;
//
//        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EmojiBoardFixer.getInstance().getEmojiManager().addEmojiPacket(emojiPacket);
//                emojiViewPager.setEmojiPackets(EmojiBoardFixer.getInstance().getEmojiManager().getEmojiPackets());
//            }
//        });
//        emojiViewPager.setEmojiPagerBoardProvider(new EmojiPagerBoardProvider() {
//            @Override
//            public View onCreatePagerView(int position, ViewGroup parent, EmojiPacket packet) {
//                if(position == 3) {
//                    ImageView imageView = new ImageView(MainActivity.this);
//                    ((ImageView) imageView).setImageResource(homhom.lib.emojiboard.R.drawable.ic_launcher);
//                    return imageView;
//                }
//                return null;
//            }
//        });
//        if(EmojiBoardFixer.getInstance().getEmojiManager().getEmojiPackets() != null
//                && EmojiBoardFixer.getInstance().getEmojiManager().getEmojiPackets().size() > 0){
//            return;
//        }
//
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
