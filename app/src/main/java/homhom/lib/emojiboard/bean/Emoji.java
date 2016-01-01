package homhom.lib.emojiboard.bean;

/**
 * Created by linhonghong on 2015/12/31.
 */
public class Emoji extends BaseBean{

    //Emoji name
    public String mName;

    //Emoji path
    public String mPath;

    public Emoji(){
        mId = 0;
        mName = "";
        mPath = "";
    }

    @Override
    public void release() {
        mId = 0;
        mName = null;
        mPath = null;
    }
}
