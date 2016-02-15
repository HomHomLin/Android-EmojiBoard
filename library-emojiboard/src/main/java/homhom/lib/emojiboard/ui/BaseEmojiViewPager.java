package homhom.lib.emojiboard.ui;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Linhh on 16/2/15.
 */
public class BaseEmojiViewPager extends BaseViewPager {
    protected int mEmojiPacketColumn;
    protected int mEmojiPacketId;
    protected int mPagerSize;
    protected int mPagerItemSize;//内部每个pager的item数量
    protected int mPagerId;

    public BaseEmojiViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseEmojiViewPager(Context context) {
        super(context);
    }

    public int getEmojiPacketColum(){
        return this.mEmojiPacketColumn;
    }

    public int getEmojiPacketId(){
        return this.mEmojiPacketId;
    }

    /**
     * 获得每个Pager页面内的item数量
     * @return
     */
    public int getPagerItemSize(){
        return this.mPagerItemSize;
    }

    /**
     * 获得该viewpager下的pager页数
     * @return
     */
    public int getPagerSize(){
        return this.mPagerSize;
    }

    public int getPagerId(){
        return this.mPagerId;
    }

    public void setPagerId(int id){
        this.mPagerId = id;
    }
}
