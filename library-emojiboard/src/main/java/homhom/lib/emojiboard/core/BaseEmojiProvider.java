package homhom.lib.emojiboard.core;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import homhom.lib.emojiboard.R;
import homhom.lib.emojiboard.bean.Emoji;
import homhom.lib.emojiboard.mgr.EmojiBoardFixer;
import homhom.lib.emojiboard.mgr.EmojiManager;

/**
 * Created by Linhh on 16/2/4.
 */
public class BaseEmojiProvider implements EmojiProvider{
    @Override
    public View onCreateView(Context context,ViewGroup parent) {
        ImageView view = new ImageView(context);
        return view;
    }

    @Override
    public View onShow(View view, Emoji emoji) {
        //基础现实引擎
        if(emoji.mId == EmojiManager.TAG_DELETE_EMOJI){
            //删除
            ((ImageView) view).setImageResource(R.drawable.emoji_backspace);
        }else {
            ((ImageView) view).setImageResource(R.drawable.ic_launcher);
        }
        return view;
    }

    @Override
    public RelativeLayout.LayoutParams onSetLayoutParams(RelativeLayout.LayoutParams layoutParams) {
        return layoutParams;
    }
}
