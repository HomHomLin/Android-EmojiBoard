package homhom.lib.emojiboard.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.ArrayList;

import homhom.lib.emojiboard.bean.Emoji;
import homhom.lib.emojiboard.bean.EmojiPacket;
import homhom.lib.emojiboard.mgr.EmojiBoardFixer;
import homhom.lib.emojiboard.util.EmojiPacketUtil;

/**
 * Created by Linhh on 16/1/1.
 */
public class EmojiViewPager extends BaseViewPager implements ViewTreeObserver.OnGlobalLayoutListener{

    private ArrayList<EmojiView> mEmojiViews;
    private EmojiPacket mEmojiPacket;
    private int mEmojiPacketColumn;
    private int mEmojiPacketId;
    private int mPagerSize;
    private int mPagerItemSize;//内部每个pager的item数量
    private EmojiViewPagerAdapter mAdapter;

    public EmojiViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEmojiViewPager(context);
    }

    public EmojiViewPager(Context context) {
        super(context);
    }

    public void initEmojiViewPager(Context context){
        if(mEmojiViews == null){
            mEmojiViews = new ArrayList<>();
        }

        addOnGlobalLayoutListener();
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

    public void setEmojiPacket(EmojiPacket emojipacket){
        this.mEmojiPacket = emojipacket;
    }

    /**
     * 获得当前Pager所使用的表情包
     * @return
     */
    public EmojiPacket getEmojiPacket(){
        return this.mEmojiPacket;
    }

    private void calEmojiPacket(EmojiPacket emojiPacket){
        this.mEmojiPacketColumn = emojiPacket.mColumn;
        this.mEmojiPacketId = emojiPacket.mId;

        this.mPagerItemSize = EmojiPacketUtil.itemSizeInPager(this.mEmojiPacketColumn);

        //计算需要几个pager，并且设置对应的表情包list
        int preSize = emojiPacket.mEmojis.size() / this.mPagerItemSize;
        this.mPagerSize = emojiPacket.mEmojis.size() % this.mPagerItemSize == 0 ? preSize : preSize + 1;

        this.setOffscreenPageLimit(this.mPagerSize);
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

        calEmojiPacket(getEmojiPacket());
    }

    public void addOnGlobalLayoutListener(){
        this.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public class EmojiViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if(mEmojiViews == null){
                return;
            }
            if(position >= mEmojiViews.size()){
                return;
            }
            container.removeView(mEmojiViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if(mEmojiViews == null){
                return null;
            }
            if(position >= mEmojiViews.size()){
                return null;
            }
            EmojiView emojiView = mEmojiViews.get(position);
            container.addView(emojiView);
            return emojiView;
        }
    }
}
