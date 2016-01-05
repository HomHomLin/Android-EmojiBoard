package homhom.lib.emojiboard.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import homhom.lib.emojiboard.bean.EmojiPacket;
import homhom.lib.emojiboard.mgr.EmojiBoardFixer;

/**
 * Created by linhonghong on 2016/1/5.
 */
public class EmojiBoard extends BaseViewPager{

    private ArrayList<EmojiPacket> mEmojiPackets;

    private int mBoardPagerSize;

    private Context mContext;

    private EmojiBoardAdapter mEmojiBoardAdapter;

    public EmojiBoard(Context context) {
        this(context, null);
    }

    public EmojiBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEmojiBoard(context);
    }

    public void initEmojiBoard(Context context){

        mContext = context;

        mEmojiPackets = EmojiBoardFixer.getInstance().getEmojiManager().getEmojiPackets();

        setupEmojiBoard(mEmojiPackets);
    }

    public void setupEmojiBoard(ArrayList<EmojiPacket> emojiPackets){

        this.mEmojiPackets = emojiPackets;

        if(mEmojiPackets != null){
            mBoardPagerSize = mEmojiPackets.size();
        }else{
            mBoardPagerSize = 0;
        }

        if(mEmojiBoardAdapter == null){
            mEmojiBoardAdapter = new EmojiBoardAdapter();
            this.setAdapter(mEmojiBoardAdapter);
        }

        this.setOffscreenPageLimit(mBoardPagerSize);
    }

    public void setEmojiPackets(ArrayList<EmojiPacket> emojiPackets){

        setupEmojiBoard(emojiPackets);

        mEmojiBoardAdapter.notifyDataSetChanged();
    }

    class EmojiBoardAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mBoardPagerSize;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            EmojiInterBoard emojiInterBoard = new EmojiInterBoard(mContext);

            emojiInterBoard.setEmojiPacket(mEmojiPackets.get(position));

            container.addView(emojiInterBoard);

            return emojiInterBoard;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if(object == null){
                return;
            }

            container.removeView((EmojiInterBoard)object);
        }
    }
}
