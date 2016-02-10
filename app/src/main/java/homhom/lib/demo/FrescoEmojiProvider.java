package homhom.lib.demo;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import homhom.lib.emojiboard.bean.Emoji;
import homhom.lib.emojiboard.core.EmojiProvider;

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
        FrescoThumbnailView draweeView = (FrescoThumbnailView)view;
        draweeView.loadLocalImageNoshowImageOnLoading(emoji.mPath, R.drawable.ic_launcher);
        return draweeView;
    }

    @Override
    public View onShowDelete(View view) {
        FrescoThumbnailView draweeView = (FrescoThumbnailView)view;
        draweeView.loadView(null,R.drawable.ic_launcher);
        return draweeView;
    }

    @Override
    public RelativeLayout.LayoutParams onSetLayoutParams(RelativeLayout.LayoutParams layoutParams) {
        return layoutParams;
    }
}
