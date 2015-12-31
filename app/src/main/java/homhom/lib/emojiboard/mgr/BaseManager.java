package homhom.lib.emojiboard.mgr;

/**
 * Created by linhonghong on 2015/12/31.
 */
public abstract class BaseManager {

    public BaseManager(){
        this.onCreate();
    }

    public abstract void onCreate();
}
