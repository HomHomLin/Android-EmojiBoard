package homhom.lib.emojiboard.core;

import android.view.View;
import android.view.ViewGroup;

import homhom.lib.emojiboard.bean.PacketInfo;

/**
 * Created by Linhh on 16/2/12.
 */
public interface EmojiTabProvider {
    public View onCreateTabView(int position, ViewGroup parent, PacketInfo info);
//    public View getPageIcon(int position, PacketInfo info, View view);
    public boolean getPageIconTextVisible(int position);
    public String getPageIconText(int position,View view, PacketInfo info);
}
