package homhom.lib.emojiboard.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by linhonghong on 2015/12/31.
 */
public class BaseViewPager extends ViewPager {
    private boolean mNoFocus = false; //if true, keep View don't move
    public BaseViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public BaseViewPager(Context context){
        this(context,null);
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        try {
            if (mNoFocus) {
                return false;
            }
            return super.onInterceptTouchEvent(event);
        }catch (Exception e){

        }
        return false;
    }

    public void setNoFocus(boolean b){
        mNoFocus = b;
    }
}
