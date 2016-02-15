package homhom.lib.emojiboard.core;

import android.view.View;
import android.view.ViewGroup;

import homhom.lib.emojiboard.bean.EmojiPacket;
import homhom.lib.emojiboard.bean.PacketInfo;
import homhom.lib.emojiboard.ui.BaseEmojiViewPager;

/**
 * Created by Linhh on 16/2/15.
 */
public interface EmojiPagerBoardProvider {

    /**
     * 用于构建在EmojiPagerBoard中的viewpager的页面
     * example：当viewpager的某一个pager需要显示自定义内容时使用
     * 有需要自定义的时候请返回自定义view，没有需要请返回null
     * @param position
     * @param parent
     * @param packet
     * @return
     */
    public View onCreatePagerView(int position, ViewGroup parent, EmojiPacket packet);
}
