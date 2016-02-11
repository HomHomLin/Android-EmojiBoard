package homhom.lib.emojiboard.bean;

import homhom.lib.emojiboard.mgr.EmojiBoardFixer;
import homhom.lib.emojiboard.mgr.EmojiManager;

/**
 * Created by Linhh on 16/1/1.
 */
public class DeleteBean extends Emoji{

    public int mPath;

    public DeleteBean(){
        super();
        try {
            mPath = EmojiBoardFixer.getInstance().getEmojiBoardConfiguration().getDeleteEmojiResouce();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mId = EmojiManager.TAG_DELETE_EMOJI;
        mName = "删除";
    }
}
