package homhom.lib.emojiboard.bean;

/**
 * Created by Linhh on 16/2/12.
 */
public class PacketInfo {

    public String mPacketName;//表情包名字

    public String mPacketIcon;//表情包图标

    public PacketInfo(){
        mPacketName = "";
        mPacketIcon = "";
    }

    public void release(){
        mPacketName = null;
        mPacketIcon = null;
    }
}
