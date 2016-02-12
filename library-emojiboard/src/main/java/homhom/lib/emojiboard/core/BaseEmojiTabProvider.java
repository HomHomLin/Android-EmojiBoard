package homhom.lib.emojiboard.core;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import homhom.lib.emojiboard.R;
import homhom.lib.emojiboard.bean.PacketInfo;
import homhom.lib.emojiboard.mgr.EmojiBoardFixer;

/**
 * Created by Linhh on 16/2/12.
 */
public class BaseEmojiTabProvider implements EmojiTabProvider{
    @Override
    public View onCreateTabView(int position, ViewGroup parent, PacketInfo info) {
        ImageView view = new ImageView(EmojiBoardFixer.getInstance().getContext());
        view.setImageResource(R.drawable.ic_launcher);
        return view;
    }

    @Override
    public boolean getPageIconTextVisible(int position) {
        return true;
    }

    @Override
    public String getPageIconText(int position, View view, PacketInfo info) {
        return "测试";
    }
}
