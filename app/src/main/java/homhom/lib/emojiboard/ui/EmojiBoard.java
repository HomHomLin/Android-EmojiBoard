package homhom.lib.emojiboard.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import homhom.lib.emojiboard.R;
import homhom.lib.emojiboard.bean.EmojiPacket;

/**
 * 内部的分页表情面板ViewPager
 * Created by linhonghong on 2015/12/31.
 */
public class EmojiBoard extends RelativeLayout implements EmojiViewPager.OnEmojiViewPagerStatusListener{

    private EmojiPagerBoard mEmojiPagerBoard;

    private LinearLayout mIndicatorLayout;

    private ArrayList<EmojiPacket> mEmojiPackets;

    private boolean mIsAddEmojiPagerBoard = false;
    private boolean mIsAddIndicatorLayout = false;

    private static final int PAGE_ITEM_NONE = -1;

    private int mPagerSize = 0;

    private int mCurrentItem = 0;

    private Context mContext;

    private EmojiBoardOnPageChangeListener mEmojiBoardOnPageChangeListener;

    public EmojiBoard(Context context) {
        this(context, null);
    }

    public EmojiBoard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmojiBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initEmojiInterBoard(context);
    }

    public void initEmojiInterBoard(Context context){
        mContext = context;
        if(mEmojiBoardOnPageChangeListener == null){
            mEmojiBoardOnPageChangeListener = new EmojiBoardOnPageChangeListener();
        }
        addEmojiIndicator(context);
        addEmojiPagerBoard(context);
    }

    public void setEmojiPackets(ArrayList<EmojiPacket> emojipackets){
        this.mEmojiPackets = emojipackets;
        setupEmojiViewPager();
    }

    private void setupEmojiViewPager(){
        if(mEmojiPagerBoard != null) {
            mEmojiPagerBoard.setEmojiPackets(this.mEmojiPackets);
            mEmojiPagerBoard.setOnEmojiViewPagerStatusListener(this);
            mEmojiPagerBoard.addOnPageChangeListener(mEmojiBoardOnPageChangeListener);
        }
    }

    public EmojiPagerBoard getEmojiViewBoard(){
        return this.mEmojiPagerBoard;
    }

    private void setupEmojiIndicator(){
        if(mIndicatorLayout != null){
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
            int margin = mContext.getResources().getDimensionPixelSize(R.dimen.emoji_indicator_iv_margin);
            layoutParams.setMargins(
                    margin,
                    margin,
                    margin,
                    margin);
            mIndicatorLayout.removeAllViews();
            for(int i = 0 ; i < mPagerSize; i ++){
                ImageView indicator = new ImageView(mContext);
                if(i == 0){
                    indicator.setImageResource(R.mipmap.emoji_default_indicator_mark);
                }else {
                    indicator.setImageResource(R.mipmap.emoji_default_indicator_unmark);
                }
                mIndicatorLayout.addView(indicator, layoutParams);
            }
        }
    }

    private void addEmojiPagerBoard(Context context){
        if(mEmojiPagerBoard == null){
            mEmojiPagerBoard = new EmojiPagerBoard(context);
        }

        if(mIsAddEmojiPagerBoard){
            return;
        }

        setupEmojiViewPager();

//        mEmojiPagerBoard.setNoFocus(true);//如果内部的view的页数大于 1 页，静止滑动

        mIsAddEmojiPagerBoard = true;

        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);

        layoutParams.addRule(RelativeLayout.ABOVE, mIndicatorLayout.getId());

        this.addView(mEmojiPagerBoard, layoutParams);

    }

    public int getCurrentItem(){
        return this.mCurrentItem;
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

        mIndicatorLayout.setId(R.id.emoji_view_pager_indicator_id);

        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        this.addView(mIndicatorLayout, layoutParams);

//        setupEmojiIndicator();
    }

    public EmojiViewPager getViewPager(int item){
        if(mEmojiPagerBoard != null) {
            EmojiViewPager viewPager = mEmojiPagerBoard.getEmojiViewPager(item);
            return viewPager;
        }

        return null;
    }

    public int getViewPagerCurrentItem(int item){
        EmojiViewPager emojiViewPager = getViewPager(item);
        if(emojiViewPager != null){
            return emojiViewPager.getCurrentItem();
        }
        return 0;
    }

    public int getViewPagerPageSize(int item){
        EmojiViewPager viewPager = getViewPager(item);
        if(viewPager != null){
            return viewPager.getPagerSize();
            //显示indicator
        }
        return  PAGE_ITEM_NONE;
    }


    class EmojiBoardOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentItem = position;
            int pageSize = getViewPagerPageSize(mCurrentItem);
            if(pageSize == PAGE_ITEM_NONE){
                //没有得到pagesize
            }else{
                mPagerSize = pageSize;
//                if(mPagerSize > 1) {
//                    mEmojiPagerBoard.setNoFocus(true);//如果内部的view的页数大于 1 页，静止滑动
//                }
                //显示indicator
                setupEmojiIndicator();
                updateIndicatorStatus(mCurrentItem, getViewPagerCurrentItem(mCurrentItem));
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void setEmojiViewBoardFocusable(boolean abled){
//        if(mEmojiPagerBoard != null){
//            mEmojiPagerBoard.setNoFocus(abled);//如果内部的view的页数大于 1 页，静止滑动
//        }
    }

    public void updateIndicatorStatus(int pagerId, int position){
        if(pagerId != mCurrentItem){
            //不是当前的item
            return;
        }
        Log.i("EmojiInterBoard","onPageSelected:" + position);
        if(mIndicatorLayout != null){
            for(int i = 0 ; i < mIndicatorLayout.getChildCount(); i ++){
                ImageView indicator = (ImageView)mIndicatorLayout.getChildAt(i);
                if(indicator != null){
                    if(i == position){
                        indicator.setImageResource(R.mipmap.emoji_default_indicator_mark);
                    }else {
                        indicator.setImageResource(R.mipmap.emoji_default_indicator_unmark);
                    }
                }
            }

        }
    }

    @Override
    public void onInit(int pagerId) {

    }

    @Override
    public void onCalEmojiPacket(int pagerId) {
        if(pagerId == 0){
            //第一页初始化完成的回调
            mPagerSize = getViewPagerPageSize(pagerId);
//            if(mPagerSize > 1) {
//                mEmojiPagerBoard.setNoFocus(true);//如果内部的view的页数大于 1 页，静止滑动
//            }
            setupEmojiIndicator();
        }
//        if(mEmojiViewPager != null){
//            mPagerSize = mEmojiViewPager.getPagerSize();
//        }
//        setupEmojiIndicator();
    }

    @Override
    public void onSetupEmojiViewPager(int pagerId) {

    }

    @Override
    public void onPageScrolled(int pagerId ,int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int pagerId ,int position) {
        updateIndicatorStatus(pagerId,position);
        //如果内部滑动到第一页和最后一页，开启外部滑动，佛则关闭外部滑动
        if(position == 0 || position == (getViewPagerPageSize(pagerId) - 1)){
            //开启
            setEmojiViewBoardFocusable(false);
        }else{
            //禁用
            setEmojiViewBoardFocusable(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int pagerId ,int state) {

    }
}
