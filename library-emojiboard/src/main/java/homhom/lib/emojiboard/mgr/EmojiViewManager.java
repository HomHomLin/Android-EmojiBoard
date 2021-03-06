package homhom.lib.emojiboard.mgr;

import android.util.Log;

/**
 * 配置EmojiView的Mgr
 * Created by linhonghong on 2015/12/31.
 */
public class EmojiViewManager extends BaseManager{

    private static EmojiViewManager mEmojiViewManager;

    private int mSpanCount = 5;//defalut = 5

    private int mViewPading = 60;// Emojiview的表情大小控制padding

    private int mBoardWidth,mBoardHeight;//面板的高度和宽度

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

    public void setBoradSize(int width, int height){
        this.mBoardHeight = height;
        this.mBoardWidth = width;
        Log.i(EmojiViewManager.class.getSimpleName(), this.mBoardHeight + ":" + this.mBoardWidth);
    }

    public void setBoardWidth(int width){
        this.mBoardWidth = width;
    }

    public void setBoardHeight(int height){
        this.mBoardHeight = height;
    }

    public int getBoardWidth(){
        return this.mBoardWidth;
    }

    public int getBoardHeight(){
        return this.mBoardHeight;
    }

    public void setHasFixedSize(boolean hasFixedSize){
        this.mHasFixSize = hasFixedSize;
    }

    public boolean getHasFixedSize(){
        return this.mHasFixSize;
    }

    public void setViewPadding(int padding){
        this.mViewPading = padding;
    }

    public int getViewPadding(){
        return this.mViewPading;
    }


    @Deprecated
    public void setSpanCount(int spanCount){
        this.mSpanCount = spanCount;
    }

    @Deprecated
    public int getSpanCount(){
        return this.mSpanCount;
    }
}
