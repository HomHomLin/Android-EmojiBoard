package homhom.lib.emojiboard.mgr;

/**
 * 配置EmojiView的Mgr
 * Created by linhonghong on 2015/12/31.
 */
public class EmojiViewManager extends BaseManager{

    private static EmojiViewManager mEmojiViewManager;

    private int mSpanCount = 5;//defalut = 5

    private boolean mHasFixSize = true;

    public EmojiViewManager(){
        super();
    }

    public static EmojiViewManager getInstance(){
        if(mEmojiViewManager == null){
            mEmojiViewManager = new EmojiViewManager();
        }
        return mEmojiViewManager;
    }

    @Override
    public void onCreate() {

    }

    public void setHasFixedSize(boolean hasFixedSize){
        this.mHasFixSize = hasFixedSize;
    }

    public boolean getHasFixedSize(){
        return this.mHasFixSize;
    }


    public void setSpanCount(int spanCount){
        this.mSpanCount = spanCount;
    }

    public int getSpanCount(){
        return this.mSpanCount;
    }
}
