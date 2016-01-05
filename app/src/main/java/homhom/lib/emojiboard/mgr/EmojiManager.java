package homhom.lib.emojiboard.mgr;

import java.util.ArrayList;

import homhom.lib.emojiboard.R;
import homhom.lib.emojiboard.bean.EmojiPacket;

/**
 * Created by linhonghong on 2015/12/31.
 */
public class EmojiManager extends BaseManager{

    private static EmojiManager mEmojiManager;

    public static final int TAG_DELETE_EMOJI = -1;

    private int mDeleteEmojiRescouce;

    private ArrayList<EmojiPacket> mEmojiPackets;//用于存放读取的Emoji包

    private ArrayList<EmojiDataChangerListener> mEmojiDataChangerListeners;


    public interface EmojiDataChangerListener {
        public void onChange(int packetId);
    }

    public static EmojiManager getInstance(){
        if(mEmojiManager == null){
            mEmojiManager = new EmojiManager();
        }
        return mEmojiManager;
    }

    public EmojiManager(){
        super();
    }

    @Override
    public void onCreate() {

        if(mEmojiPackets == null){
            mEmojiPackets = new ArrayList<EmojiPacket>();
        }

        if(mEmojiDataChangerListeners == null){
            mEmojiDataChangerListeners = new ArrayList<EmojiDataChangerListener>();
        }

        this.mDeleteEmojiRescouce = R.mipmap.emoji_backspace;

    }

    /**
     * 以后可以封装到单独的PacketMgr里，判断list是否存在了表情包之类的操作
     * @param packet
     */
    public void addEmojiPacket(EmojiPacket packet){
        if(packet == null){
            return;
        }
        if(mEmojiPackets != null){
            mEmojiPackets.add(packet);
        }
        notifyDataSetChanged(packet.mId);
    }

    public void removeEmojiPacket(EmojiPacket packet){
        if(packet == null){
            return;
        }
        if(mEmojiPackets != null){
            mEmojiPackets.remove(packet);
        }
        notifyDataSetChanged(packet.mId);
    }

    public ArrayList<EmojiPacket> getEmojiPackets(){
        return this.mEmojiPackets;
    }

    private void notifyDataSetChanged(int id){
        if(mEmojiDataChangerListeners == null){
            return;
        }
        for(int i = 0; i < mEmojiDataChangerListeners.size(); i ++){
            mEmojiDataChangerListeners.get(i).onChange(id);
        }
    }

    public void registerChangeListener(EmojiDataChangerListener listener){
        if(mEmojiDataChangerListeners != null){
            mEmojiDataChangerListeners.add(listener);
        }
    }

    public void unregisterChangeListener(EmojiDataChangerListener listener){
        if(mEmojiDataChangerListeners != null) {
            mEmojiDataChangerListeners.remove(listener);
        }
    }

    public void setDeleteEmojiResouce(int res){
        this.mDeleteEmojiRescouce = res;
    }

    public int getDeleteEmojiResouce(){
        return this.mDeleteEmojiRescouce;
    }
}
