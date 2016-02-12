package homhom.lib.emojiboard.bean;

import java.util.ArrayList;

/**
 * Created by linhonghong on 2015/12/31.
 */
public class EmojiPacket extends BaseBean{

    public int mColumn;//表情所展现的列数

    public PacketInfo mPacketInfo;

    public ArrayList<Emoji> mEmojis;//表情

    public boolean mShowDelete;//显示删除？

    public EmojiPacket(){
        mColumn = 5;//default
        mId = 0;
        mShowDelete = true;
        if(mPacketInfo == null){
            mPacketInfo = new PacketInfo();
        }
        if(mEmojis == null){
            mEmojis = new ArrayList<>();
        }
    }

    @Override
    public void release() {
        mColumn = 0;
        mId = 0;
        if(mPacketInfo != null){
            mPacketInfo.release();
            mPacketInfo = null;
        }
        if(mEmojis != null){
            mEmojis.clear();
            mEmojis = null;
        }
    }
}
