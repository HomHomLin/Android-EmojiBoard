package homhom.lib.emojiboard.bean;

import java.util.ArrayList;

/**
 * Created by linhonghong on 2015/12/31.
 */
public class EmojiPacket extends BaseBean{

    public int mColumn;//表情所展现的列数

    public String mPacketName;//表情包名字

    public String mPacketIcon;//表情包图标

    public ArrayList<Emoji> mEmojis;//表情

    public EmojiPacket(){
        mColumn = 5;//default
        mId = 0;
        mPacketName = "";
        mPacketIcon = "";
        if(mEmojis == null){
            mEmojis = new ArrayList<>();
        }
    }

    @Override
    public void release() {
        mColumn = 0;
        mId = 0;
        mPacketName = null;
        mPacketIcon = null;
        if(mEmojis != null){
            mEmojis.clear();
            mEmojis = null;
        }
    }
}
