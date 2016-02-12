package homhom.lib.emojiboard.mgr;

import android.content.Context;

import homhom.lib.emojiboard.core.EmojiBoardConfiguration;

/**
 * Created by linhonghong on 2015/12/31.
 */
public class EmojiBoardFixer extends BaseManager{

    private static EmojiBoardFixer mEmojiBoardFixer;

    private EmojiBoardConfiguration mEmojiBoardConfiguration;

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

    public void initConfiguration(EmojiBoardConfiguration configuration){
        this.mEmojiBoardConfiguration = configuration;
        this.mEmojiBoardConfiguration.getEmojiPacketParser().init();
    }

    public EmojiBoardConfiguration getEmojiBoardConfiguration() throws Exception{
        if(mEmojiBoardConfiguration == null){
            throw new Exception("EmojiBoardConfiguration is null");
        }
        return this.mEmojiBoardConfiguration;
    }

    public Context getContext(){
        return this.mEmojiBoardConfiguration.getContext();
    }

    public EmojiBoardFixer(){
        super();
    }

    @Override
    public void onCreate() {

    }
}
