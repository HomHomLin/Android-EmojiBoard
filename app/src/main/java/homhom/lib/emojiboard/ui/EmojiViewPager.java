package homhom.lib.emojiboard.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import homhom.lib.emojiboard.bean.Emoji;
import homhom.lib.emojiboard.bean.EmojiPacket;

/**
 * Created by Linhh on 16/1/1.
 */
public class EmojiViewPager extends BaseViewPager{

    private ArrayList<EmojiView> mEmojiViews;
    private EmojiPacket mEmojiPacket;

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
    }

    public void setEmojiPacket(EmojiPacket emojipacket){
        this.mEmojiPacket = emojipacket;
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
