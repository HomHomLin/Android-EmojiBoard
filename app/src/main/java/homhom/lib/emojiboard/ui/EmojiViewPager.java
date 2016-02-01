package homhom.lib.emojiboard.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

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

//    private ArrayList<EmojiView> mEmojiViews;
    private int mPagerId;
    private EmojiPacket mEmojiPacket;
    private int mEmojiPacketColumn;
    private int mEmojiPacketId;
    private int mPagerSize;
    private int mPagerItemSize;//内部每个pager的item数量
    private boolean mShowDelete;
    private EmojiViewPagerAdapter mAdapter;
    private HashMap<Integer, List<Emoji>> mViewPagerDataList;
    private OnEmojiViewPagerStatusListener mOnEmojiViewPagerStatusListener;
    private EmojiOnPageChangeListener mOnPageChangeListener;

    public EmojiViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEmojiViewPager();
    }

    public EmojiViewPager(Context context) {
        this(context, null);
    }

    public interface OnEmojiViewPagerStatusListener{
        public void onInit(int pageId);
        public void onCalEmojiPacket(int pageId);
        public void onSetupEmojiViewPager(int pageId);
        public void onPageScrolled(int pageId, int position, float positionOffset, int positionOffsetPixels);
        public void onPageSelected(int pageId, int position);
        public void onPageScrollStateChanged(int pageId, int state);
    }

    public void initEmojiViewPager(){
        Log.i("EmojiViewPager", "initEmojiViewpager");
//        if(mEmojiViews == null){
//            mEmojiViews = new ArrayList<>();
//        }

        if(mViewPagerDataList == null){
            mViewPagerDataList = new HashMap<>();
        }

        if(mOnPageChangeListener == null){
            mOnPageChangeListener = new EmojiOnPageChangeListener();
        }

        if(mAdapter == null){
            mAdapter = new EmojiViewPagerAdapter();
        }

        addOnGlobalLayoutListener();

        if(this.mOnEmojiViewPagerStatusListener != null){
            //call init
            this.mOnEmojiViewPagerStatusListener.onInit(getPagerId());
        }
    }

    public void setOnEmojiViewPagerStatusListener(OnEmojiViewPagerStatusListener listener){
        this.mOnEmojiViewPagerStatusListener = listener;
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

        if(this.mOnEmojiViewPagerStatusListener != null){
            //now you can get almost info from this view pager
            this.mOnEmojiViewPagerStatusListener.onCalEmojiPacket(getPagerId());
        }

        Log.i("EmojiViewPager", "calEmojiPacket");
    }

    //thinking about doing in background
    private void setupEmojiViewPager(){

        if(mViewPagerDataList != null) {
            mViewPagerDataList.clear();
        }

//        if(mEmojiViews != null){
//            mEmojiViews.clear();
//        }

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

//            EmojiView emojiView = new EmojiView(mContext);
//            emojiView.setLayoutParams(
//                    new ViewGroup.LayoutParams(
//                            ViewGroup.LayoutParams.MATCH_PARENT,
//                            ViewGroup.LayoutParams.MATCH_PARENT));
//
//            mEmojiViews.add(emojiView);

            mViewPagerDataList.put(i, mEmojiPacket.mEmojis.subList(start, end));
        }

        this.setAdapter(mAdapter);

        this.addOnPageChangeListener(mOnPageChangeListener);

        if(this.mOnEmojiViewPagerStatusListener != null){
            //now you can get all info from this view pager
            this.mOnEmojiViewPagerStatusListener.onSetupEmojiViewPager(getPagerId());
        }
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

    private class EmojiOnPageChangeListener implements OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(mOnEmojiViewPagerStatusListener != null){
                mOnEmojiViewPagerStatusListener.onPageScrolled(getPagerId(), position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if(mOnEmojiViewPagerStatusListener != null){
                mOnEmojiViewPagerStatusListener.onPageSelected(getPagerId() ,position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if(mOnEmojiViewPagerStatusListener != null){
                mOnEmojiViewPagerStatusListener.onPageScrollStateChanged(getPagerId() ,state);
            }
        }
    }

    class EmojiViewPagerAdapter extends PagerAdapter{

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
            if(object == null){
                return;
            }
//            if(position >= mEmojiViews.size()){
//                return;
//            }
            container.removeView((EmojiView)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            if(mEmojiViews == null){
//                return null;
//            }
//            if(position >= mEmojiViews.size()){
//                return null;
//            }
//            EmojiView emojiView = mEmojiViews.get(position);
            EmojiView emojiView = new EmojiView(getContext());

            emojiView.setEmojisInfo(getContext(),
                    getEmojiPacketId(),
                    mViewPagerDataList.get(position),
                    getEmojiPacketColum(),
                    isShowDelete()
                    );

            emojiView.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));

            container.addView(emojiView);

            return emojiView;
        }
    }
}
