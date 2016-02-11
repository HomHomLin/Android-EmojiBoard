package homhom.lib.demo;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import homhom.lib.emojiboard.bean.DeleteBean;
import homhom.lib.emojiboard.bean.Emoji;
import homhom.lib.emojiboard.core.EmojiProvider;
import homhom.lib.emojiboard.mgr.EmojiManager;

/**
 * Created by Linhh on 16/2/9.
 */
public class FrescoEmojiProvider implements EmojiProvider{

    @Override
    public View onCreateView(Context context, ViewGroup parent) {
        FrescoThumbnailView draweeView = new FrescoThumbnailView(context);
        return draweeView;
    }

    @Override
    public View onShow(View view, Emoji emoji) {
        Log.i("emoji_test", emoji.mPath);
        FrescoThumbnailView draweeView = (FrescoThumbnailView) view;
        if(emoji.mId == EmojiManager.TAG_DELETE_EMOJI){
            //删除
            draweeView.loadView(null,((DeleteBean)emoji).mPath);
        }else {
            draweeView.loadLocalImageNoshowImageOnLoading(emoji.mPath, R.drawable.ic_launcher);
        }
        return draweeView;
    }

    @Override
    public RelativeLayout.LayoutParams onSetLayoutParams(RelativeLayout.LayoutParams layoutParams) {
        return layoutParams;
    }
}
