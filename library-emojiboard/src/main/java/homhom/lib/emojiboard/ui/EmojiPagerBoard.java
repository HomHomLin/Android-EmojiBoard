package homhom.lib.emojiboard.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import homhom.lib.emojiboard.R;
import homhom.lib.emojiboard.bean.EmojiPacket;
import homhom.lib.emojiboard.mgr.EmojiBoardFixer;

/**
 * 其实这个viewpager是多余的，但是为了更方便的处理表情数据包，所以多出了这个viewpager
 * Created by linhonghong on 2016/1/5.
 */
public class EmojiPagerBoard extends BaseViewPager{

    private ArrayList<EmojiPacket> mEmojiPackets;

    private int mBoardPagerSize;

    private EmojiPagerBoardAdapter mEmojiPagerBoardAdapter;

    private ArrayList<EmojiViewPager> mEmojiViewPagers;

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
        initEmojiBoard();
    }

    public void initEmojiBoard(){

        if(mEmojiViewPagers == null){
            mEmojiViewPagers = new ArrayList<>();
        }

        mEmojiPackets = EmojiBoardFixer.getInstance().getEmojiManager().getEmojiPackets();

        setupEmojiBoard(mEmojiPackets);
    }

    public void setOnEmojiViewPagerStatusListener(EmojiViewPager.OnEmojiViewPagerStatusListener listener){
        this.mOnEmojiViewPagerStatusListener = listener;
    }

    public void setOnEmojiPagerBoardStatusListener(OnEmojiPagerBoardStatusListener listener){
        this.mOnEmojiPagerBoardStatusListener = listener;
    }


    public EmojiViewPager getEmojiViewPager(int pagerId){
        if(mEmojiViewPagers == null){
            return null;
        }
        return mEmojiViewPagers.get(pagerId);
    }

    public void setupEmojiBoard(ArrayList<EmojiPacket> emojiPackets){

        this.mEmojiPackets = emojiPackets;

        if(mEmojiPackets != null){
            mBoardPagerSize = mEmojiPackets.size();
        }else{
            mBoardPagerSize = 0;
        }

        ViewGroup.LayoutParams layoutParams =
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

        for(int i = 0 ; i < mBoardPagerSize; i ++){
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
    }

    public void setEmojiPackets(ArrayList<EmojiPacket> emojiPackets){

        setupEmojiBoard(emojiPackets);

        mEmojiPagerBoardAdapter.notifyDataSetChanged();

        if(this.mOnEmojiPagerBoardStatusListener != null) {
            this.mOnEmojiPagerBoardStatusListener.onSetEmojiPacket();
        }
    }

    class EmojiPagerBoardAdapter extends PagerAdapter implements EmojiBoardTab.EmojiIconTabProvider {

        @Override
        public int getCount() {
            return mEmojiViewPagers == null ? 0 : mEmojiViewPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            if(mEmojiViewPagers == null){
                return null;
            }
            if(position >= mEmojiViewPagers.size()){
                return null;
            }

            EmojiViewPager emojiViewPager = mEmojiViewPagers.get(position);

            emojiViewPager.setEmojiPacket(mEmojiPackets.get(position));

            container.addView(emojiViewPager);

            return emojiViewPager;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if(object == null){
                return;
            }

            if(position >= mEmojiViewPagers.size()){
                return;
            }

            container.removeView(mEmojiViewPagers.get(position));
        }

        @Override
        public int getPageIcon(int position) {
            return R.drawable.ic_launcher;
        }

        @Override
        public int getPageSelectIcon(int position) {
            return R.drawable.ic_launcher;
        }

        @Override
        public String getPageIconText(int position) {
            return mEmojiPackets == null ? "" : mEmojiPackets.get(position).mPacketName;
        }
    }
}
