package homhom.lib.emojiboard.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import homhom.lib.emojiboard.bean.Emoji;
import homhom.lib.emojiboard.bean.EmojiPacket;
import homhom.lib.emojiboard.mgr.EmojiBoardFixer;
import homhom.lib.emojiboard.util.EmojiPacketUtil;

/**
 * Created by Linhh on 16/1/1.
 */
public class EmojiViewPager extends BaseViewPager
        implements ViewTreeObserver.OnGlobalLayoutListener{

    private ArrayList<EmojiView> mEmojiViews;
    private EmojiPacket mEmojiPacket;
    private int mEmojiPacketColumn;
    private int mEmojiPacketId;
    private int mPagerSize;
    private int mPagerItemSize;//内部每个pager的item数量
    private boolean mShowDelete;
    private EmojiViewPagerAdapter mAdapter;
    private HashMap<Integer, List<Emoji>> mViewPagerDataList;
    private Context mContext;

    public EmojiViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEmojiViewPager(context);
    }

    public EmojiViewPager(Context context) {
        super(context);
    }

    public void initEmojiViewPager(Context context){
        this.mContext = context;
        if(mEmojiViews == null){
            mEmojiViews = new ArrayList<>();
        }

        if(mViewPagerDataList == null){
            mViewPagerDataList = new HashMap<>();
        }

        if(mAdapter == null){
            mAdapter = new EmojiViewPagerAdapter();
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
        if(this.getMeasuredHeight() == 0){
            //this view is not init
            return;
        }

        calEmojiPacket(getEmojiPacket());

        setupEmojiViewPager();

    }

    /**
     * 获得当前Pager所使用的表情包
     * @return
     */
    public EmojiPacket getEmojiPacket(){
        return this.mEmojiPacket;
    }

    public boolean isShowDelete(){
        return this.mShowDelete;
    }

    private void calEmojiPacket(EmojiPacket emojiPacket){
        this.mEmojiPacketColumn = emojiPacket.mColumn;
        this.mEmojiPacketId = emojiPacket.mId;

        this.mShowDelete = emojiPacket.mShowDelete;

        this.mPagerItemSize = mShowDelete ?
                EmojiPacketUtil.itemSizeInPagerPreWithDelete(this.mEmojiPacketColumn) :
                EmojiPacketUtil.itemSizeInPager(this.mEmojiPacketColumn);

        //计算需要几个pager，并且设置对应的表情包list
        int preSize = emojiPacket.mEmojis.size() / this.mPagerItemSize;
        this.mPagerSize = emojiPacket.mEmojis.size() % this.mPagerItemSize == 0 ? preSize : preSize + 1;

        this.setOffscreenPageLimit(this.mPagerSize);

        Log.i("EmojiViewPager", "calEmojiPacket");
    }

    //thinking about doing in background
    private void setupEmojiViewPager(){

        if(mViewPagerDataList != null) {
            mViewPagerDataList.clear();
        }

        if(mEmojiViews != null){
            mEmojiViews.clear();
        }

        for(int i = 0 ; i < this.mPagerSize; i ++){
            int start = i * mPagerItemSize;
            int end = start + mPagerItemSize;
            if(start >= mEmojiPacket.mEmojis.size()){
                //如果起始比原来的所有数据都要大，说明出错了
                break;
            }
            if(end >= mEmojiPacket.mEmojis.size()){
                end = mEmojiPacket.mEmojis.size();
            }

            EmojiView emojiView = new EmojiView(mContext);
            emojiView.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));

            mEmojiViews.add(emojiView);

            mViewPagerDataList.put(i, mEmojiPacket.mEmojis.subList(start, end));
        }

        this.setAdapter(mAdapter);
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

        if(getEmojiPacket() == null){
            return;
        }

        calEmojiPacket(getEmojiPacket());

        setupEmojiViewPager();
    }

    private void addOnGlobalLayoutListener(){
        this.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public class EmojiViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mPagerSize;
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
            emojiView.setEmojisInfo(mContext,
                    getEmojiPacketId(),
                    mViewPagerDataList.get(position),
                    getEmojiPacketColum(),
                    isShowDelete()
                    );
            container.addView(emojiView);
            return emojiView;
        }
    }
}
