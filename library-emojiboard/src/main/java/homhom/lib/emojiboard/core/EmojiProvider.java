package homhom.lib.emojiboard.core;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import homhom.lib.emojiboard.bean.Emoji;

/**
 * Created by Linhh on 16/2/4.
 */
public interface EmojiProvider {
    public View onCreateView(Context context,ViewGroup parent);
    public View onShow(View view, Emoji emoji) ;
    public View onShowDelete(View view);
}
