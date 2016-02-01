package homhom.lib.emojiboard.core;

import android.content.Context;

import homhom.lib.emojiboard.core.parser.BaseEmojiPacketParser;
import homhom.lib.emojiboard.core.parser.JsonEmojiPacketParser;

/**
 * Created by linhonghong on 2016/2/1.
 */
public class EmojiBoardConfiguration {
    private static EmojiBoardConfiguration mEmojiBoardConfiguration;
    private static Context mContext;//全局
    private String mEmojiDirectory;
    public static String EMOJI_PACKET_EXTENSIONS = "hem";//后缀
    public static String EMOJI_PACKET_PARSER_EXTENSIONS = "js";//后缀
    public static String EMOJI_PACKET_PRE_FIX = "hem_";//前缀

    private BaseEmojiPacketParser mBaseEmojiPacketParser;

    public static BaseEmojiPacketParser DefaultParser(){
        return new JsonEmojiPacketParser();
    }

    public static EmojiBoardConfiguration Builder(Context context){
        mContext = context;
        return build();
    }

    public EmojiBoardConfiguration setEmojiDirectory(String path){
        mEmojiDirectory = path;
        return build();
    }

    /**
     * 暂时关闭parser的设置
     * @param parser
     * @return
     */
    private EmojiBoardConfiguration setParser(BaseEmojiPacketParser parser){
        this.mBaseEmojiPacketParser = parser;
        return build();
    }

    public Context getContext(){
        return mContext;
    }

    public String getEmojiDirectory(){
        return this.mEmojiDirectory;
    }

    public BaseEmojiPacketParser getEmojiPacketParser(){
        if(this.mBaseEmojiPacketParser == null){
            this.mBaseEmojiPacketParser = EmojiBoardConfiguration.DefaultParser();
        }
        return this.mBaseEmojiPacketParser;
    }

    public static EmojiBoardConfiguration build(){
        if(mEmojiBoardConfiguration == null) {
            mEmojiBoardConfiguration = new EmojiBoardConfiguration();
        }
        return mEmojiBoardConfiguration;
    }
}
