package homhom.lib.emojiboard.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import homhom.lib.emojiboard.R;
import homhom.lib.emojiboard.bean.EmojiPacket;

/**
 * 内部的分页表情面板ViewPager
 * Created by linhonghong on 2015/12/31.
 */
public class EmojiInterBoard extends RelativeLayout implements EmojiViewPager.OnEmojiViewPagerStatusListener{

    private EmojiViewPager mEmojiViewPager;

    private LinearLayout mIndicatorLayout;

    private EmojiPacket mEmojiPacket;

    private boolean mIsAddEmojiViewPager = false;
    private boolean mIsAddIndicatorLayout = false;

    private int mPagerSize = 0;

    private Context mContext;

    public EmojiInterBoard(Context context) {
        this(context, null);
    }

    public EmojiInterBoard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmojiInterBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initEmojiInterBoard(context);
    }

    public void initEmojiInterBoard(Context context){
        mContext = context;
        addEmojiIndicator(context);
        addEmojiViewPager(context);
    }

    public void setEmojiPacket(EmojiPacket emojipacket){
        this.mEmojiPacket = emojipacket;
        setupEmojiViewPager();
    }

    private void setupEmojiViewPager(){
        if(mEmojiViewPager != null) {
            mEmojiViewPager.setEmojiPacket(this.mEmojiPacket);
            mEmojiViewPager.setOnEmojiViewPagerStatusListener(this);
        }
    }

    private void setupEmojiIndicator(){
        if(mIndicatorLayout != null){
            mIndicatorLayout.removeAllViews();
            for(int i = 0 ; i < mPagerSize; i ++){
                ImageView ivIndicator = new ImageView(mContext);
                ivIndicator.setImageResource(R.mipmap.emoji_default_indicator_unmark);
                mIndicatorLayout.addView(ivIndicator);
            }
        }
    }

    private void addEmojiViewPager(Context context){
        if(mEmojiViewPager == null){
            mEmojiViewPager = new EmojiViewPager(context);
        }

        if(mIsAddEmojiViewPager){
            return;
        }

        setupEmojiViewPager();

        mIsAddEmojiViewPager = true;

        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);

        layoutParams.addRule(RelativeLayout.ABOVE, mIndicatorLayout.getId());

        this.addView(mEmojiViewPager, layoutParams);

    }

    private void addEmojiIndicator(Context context){
        if(mIndicatorLayout == null){
            mIndicatorLayout = new LinearLayout(context);
        }

        if(mIsAddIndicatorLayout){
            return;
        }

        mIsAddIndicatorLayout = true;

        mIndicatorLayout.setGravity(Gravity.CENTER);//内容居中

        mIndicatorLayout.setId(R.id.emoji_view_pager_id);

        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        this.addView(mIndicatorLayout, layoutParams);

//        setupEmojiIndicator();
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onCalEmojiPacket() {
        if(mEmojiViewPager != null){
            mPagerSize = mEmojiViewPager.getPagerSize();
        }
        setupEmojiIndicator();
    }

    @Override
    public void onSetupEmojiViewPager() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
