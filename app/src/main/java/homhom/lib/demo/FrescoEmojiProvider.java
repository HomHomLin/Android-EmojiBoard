package homhom.lib.demo;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

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
        String s = "http://m.tiebaimg.com/timg?wapp&quality=80&size=b150_150&subsize=20480&cut_x=0&cut_w=0&cut_y=0&cut_h=0&sec=1369815402&srctrace&di=1c68ee4ea94de4428e2a63529de53601&wh_rate=null&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fpic%2Fitem%2Fcdbf6c81800a19d83a6b909b36fa828ba71e46c7.jpg";
        FrescoThumbnailView draweeView = (FrescoThumbnailView)view;
        draweeView.loadView(s,R.drawable.ic_launcher);
        return draweeView;
    }

    @Override
    public View onShowDelete(View view) {
        String s = "http://m.tiebaimg.com/timg?wapp&quality=80&size=b150_150&subsize=20480&cut_x=0&cut_w=0&cut_y=0&cut_h=0&sec=1369815402&srctrace&di=1c68ee4ea94de4428e2a63529de53601&wh_rate=null&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fpic%2Fitem%2Fcdbf6c81800a19d83a6b909b36fa828ba71e46c7.jpg";
        FrescoThumbnailView draweeView = (FrescoThumbnailView)view;
        draweeView.loadView(s,R.drawable.ic_launcher);
        return draweeView;
    }
}
