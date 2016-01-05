package homhom.lib.emojiboard.mgr;

/**
 * Created by linhonghong on 2015/12/31.
 */
public class EmojiBoardFixer extends BaseManager{

    private static EmojiBoardFixer mEmojiBoardFixer;

    private EmojiManager mEmojiManager;

    private EmojiViewManager mEmojiViewManager;

    public static EmojiBoardFixer getInstance(){
        if(mEmojiBoardFixer == null){
            mEmojiBoardFixer = new EmojiBoardFixer();
        }
        return mEmojiBoardFixer;
    }

    public EmojiManager getEmojiManager(){
        if(mEmojiManager == null){
            mEmojiManager = EmojiManager.getInstance();
        }
        return this.mEmojiManager;
    }

    public EmojiViewManager getEmojiViewManager(){
        if(mEmojiViewManager == null){
            mEmojiViewManager = EmojiViewManager.getInstance();
        }
        return this.mEmojiViewManager;
    }

    public EmojiBoardFixer(){
        super();
    }

    @Override
    public void onCreate() {

    }
}
