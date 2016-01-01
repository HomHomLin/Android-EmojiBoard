package homhom.lib.emojiboard.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import homhom.lib.emojiboard.mgr.EmojiBoardFixer;

/**
 * 内部的分页表情面板ViewPager
 * Created by linhonghong on 2015/12/31.
 */
public class EmojiInterBoard extends RelativeLayout{
    public EmojiInterBoard(Context context) {
        super(context);
    }

    public EmojiInterBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmojiInterBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initEmojiInterBoard(context);
    }

    public void initEmojiInterBoard(Context context){

    }
}
