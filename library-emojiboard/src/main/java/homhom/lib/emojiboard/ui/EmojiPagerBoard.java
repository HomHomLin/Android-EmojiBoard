package homhom.lib.emojiboard.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import homhom.lib.emojiboard.R;
import homhom.lib.emojiboard.bean.EmojiPacket;
import homhom.lib.emojiboard.core.EmojiPagerBoardProvider;
import homhom.lib.emojiboard.core.EmojiTabProvider;
import homhom.lib.emojiboard.mgr.EmojiBoardFixer;

/**
 * 其实这个viewpager是多余的，但是为了更方便的处理表情数据包，所以多出了这个viewpager
 * Created by linhonghong on 2016/1/5.
 */
public class EmojiPagerBoard extends BaseViewPager{

    private ArrayList<EmojiPacket> mEmojiPackets;

    private int mBoardPagerSize;

    private EmojiPagerBoardAdapter mEmojiPagerBoardAdapter;

    private EmojiTabProvider mEmojiTabProvider;

    private ArrayList<View> mEmojiViewPagers;

    private EmojiPagerBoardProvider mEmojiPagerBoardProvider;

    private EmojiViewPager.OnEmojiViewPagerStatusListener mOnEmojiViewPagerStatusListener;

    private OnEmojiPagerBoardStatusListener mOnEmojiPagerBoardStatusListener;

    public interface OnEmojiPagerBoardStatusListener{
        public void onSetEmojiPacket();
    }

    public EmojiPagerBoard(Context context) {
        this(context, null);
    }

    public EmojiPagerBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initEmojiBoard(){

        if(mEmojiViewPagers == null){
            mEmojiViewPagers = new ArrayList<>();
        }

        mEmojiPackets = EmojiBoardFixer.getInstance().getEmojiManager().getEmojiPackets();

        try {
            mEmojiTabProvider = EmojiBoardFixer.getInstance().getEmojiBoardConfiguration().getEmojiTabProvider();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setupEmojiBoard(mEmojiPackets);
    }

    public void setOnEmojiViewPagerStatusListener(EmojiViewPager.OnEmojiViewPagerStatusListener listener){
        this.mOnEmojiViewPagerStatusListener = listener;
    }

    public void setOnEmojiPagerBoardStatusListener(OnEmojiPagerBoardStatusListener listener){
        this.mOnEmojiPagerBoardStatusListener = listener;
    }

    public void setEmojiPagerBoardProvider(EmojiPagerBoardProvider emojiPagerBoardProvider){
        this.mEmojiPagerBoardProvider = emojiPagerBoardProvider;
    }


    public View getEmojiViewPager(int pagerId){
        if(mEmojiViewPagers == null){
            return null;
        }
        return mEmojiViewPagers.get(pagerId);
    }

    /**
     * 增加几个viewpager
     * @param emojiPackets
     */
    public void setupEmojiBoard(ArrayList<EmojiPacket> emojiPackets){

        this.mEmojiPackets = emojiPackets;

        if(mEmojiPackets != null){
            mBoardPagerSize = mEmojiPackets.size();
        }else{
            mBoardPagerSize = 0;
        }

        if(mEmojiViewPagers != null){
            mEmojiViewPagers.clear();
        }else{
            mEmojiViewPagers = new ArrayList<>();
        }

        ViewGroup.LayoutParams layoutParams =
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

        for(int i = 0 ; i < mBoardPagerSize; i ++){

            if(mEmojiPagerBoardProvider != null){
                View view =
                        mEmojiPagerBoardProvider.onCreatePagerView(i,this,this.mEmojiPackets.get(i));
                if(view != null){
                    view.setLayoutParams(layoutParams);
                    mEmojiViewPagers.add(view);
                    continue;
                }
            }

            EmojiViewPager emojiViewPager = new EmojiViewPager(getContext());

            emojiViewPager.setPagerId(i);

            emojiViewPager.setOnEmojiViewPagerStatusListener(mOnEmojiViewPagerStatusListener);

            emojiViewPager.setLayoutParams(layoutParams);

            mEmojiViewPagers.add(emojiViewPager);

        }

        if(mEmojiPagerBoardAdapter == null){
            mEmojiPagerBoardAdapter = new EmojiPagerBoardAdapter();
            this.setAdapter(mEmojiPagerBoardAdapter);
        }

        this.setOffscreenPageLimit(mBoardPagerSize);

        mEmojiPagerBoardAdapter.setAdapterEmojiPackets(this.mEmojiPackets);
        mEmojiPagerBoardAdapter.setAdapterEmojiViewPagers(this.mEmojiViewPagers);

        mEmojiPagerBoardAdapter.notifyDataSetChanged();

    }

    public void setEmojiPackets(ArrayList<EmojiPacket> emojiPackets){

        setupEmojiBoard(emojiPackets);

        if(this.mOnEmojiPagerBoardStatusListener != null) {
            this.mOnEmojiPagerBoardStatusListener.onSetEmojiPacket();
        }
    }

    class EmojiPagerBoardAdapter extends PagerAdapter implements EmojiBoardTab.EmojiTabListener {

        private ArrayList<View> mAdapterEmojiViewPagers;

        private ArrayList<EmojiPacket> mAdapterEmojiPackets;

        public void setAdapterEmojiViewPagers(ArrayList<View> list){
            if(mAdapterEmojiViewPagers == null){
                mAdapterEmojiViewPagers = new ArrayList<>();
            }
            mAdapterEmojiViewPagers.clear();
            mAdapterEmojiViewPagers.addAll(list);
        }

        public void setAdapterEmojiPackets(ArrayList<EmojiPacket> list){
            if(mAdapterEmojiPackets == null){
                mAdapterEmojiPackets = new ArrayList<>();
            }
            mAdapterEmojiPackets.clear();
            mAdapterEmojiPackets.addAll(list);
        }

        @Override
        public int getCount() {
            return mAdapterEmojiViewPagers == null ? 0 : mAdapterEmojiViewPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            if(mAdapterEmojiViewPagers == null){
                return null;
            }
            if(position >= mAdapterEmojiViewPagers.size()){
                return null;
            }

            View view = mAdapterEmojiViewPagers.get(position);

            if(view instanceof EmojiViewPager){
                EmojiViewPager emojiViewPager = (EmojiViewPager)view;
                emojiViewPager.setEmojiPacket(mAdapterEmojiPackets.get(position));
            }


            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if(object == null){
                return;
            }

            if(position >= mAdapterEmojiViewPagers.size()){
                return;
            }

            container.removeView(mAdapterEmojiViewPagers.get(position));
        }

        @Override
        public View onCreateTabView(int position, ViewGroup parent) {
            return mEmojiTabProvider == null ? null : mEmojiTabProvider.onCreateTabView(position,parent,mAdapterEmojiPackets.get(position).mPacketInfo);
        }

        @Override
        public boolean getPageIconTextVisible(int position) {
            return false;
//            return mEmojiTabProvider == null ? true : mEmojiTabProvider.getPageIconTextVisible(position);

        }

        @Override
        public String getPageIconText(int position, View view) {
            return mEmojiTabProvider == null ? null : mEmojiTabProvider.getPageIconText(position,view,mAdapterEmojiPackets.get(position).mPacketInfo);
        }
    }
}
