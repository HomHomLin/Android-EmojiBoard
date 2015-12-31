package homhom.lib.emojiboard.bean;

import java.util.ArrayList;

/**
 * Created by linhonghong on 2015/12/31.
 */
public class EmojiPacket implements BaseBean{

    public int mId;

    public String mPacketName;//表情包名字

    public String mPacketIcon;//表情包图标

    public ArrayList<Emoji> mEmojis;//表情

    public EmojiPacket(){
        mId = 0;
        mPacketName = "";
        mPacketIcon = "";
        if(mEmojis == null){
            mEmojis = new ArrayList<>();
        }
    }

    @Override
    public void release() {
        mId = 0;
        mPacketName = null;
        mPacketIcon = null;
        if(mEmojis != null){
            mEmojis.clear();
            mEmojis = null;
        }
    }
}
