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
public class EmojiInterBoard extends RelativeLayout implements ViewTreeObserver.OnGlobalLayoutListener{
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
        addOnGlobalLayoutListener();
    }

    public void addOnGlobalLayoutListener(){
        this.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
            this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }

        //setting board size
        EmojiBoardFixer.getInstance().
                getEmojiViewManager().
                setBoradSize(this.getMeasuredWidth(), this.getMeasuredHeight());



    }
}
