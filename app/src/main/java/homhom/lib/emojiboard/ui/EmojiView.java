package homhom.lib.emojiboard.ui;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import homhom.lib.emojiboard.R;
import homhom.lib.emojiboard.bean.Emoji;
import homhom.lib.emojiboard.mgr.EmojiBoardFixer;
import homhom.lib.emojiboard.mgr.EmojiManager;

/**
 * 面板上层的gridview
 * Created by linhonghong on 2015/12/31.
 */
public class EmojiView extends RecyclerView implements EmojiManager.EmojiDataChangerListener {

    private GridLayoutManager mGridLayoutManager;

    private EmojiBoardFixer mEmojiBoardFixer;

    private int mId;

    private List<Emoji> mEmojis;

    private boolean mShowDelete = true;

    private int mColumn;

    private EmojiViewAdapter mEmojiViewAdapter;

    public EmojiView(Context context) {
        super(context);
    }

    public EmojiView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmojiView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        initEmojiView(context);
    }

    private void initEmojiView(Context context){
        if(mEmojiBoardFixer == null){
            mEmojiBoardFixer = EmojiBoardFixer.getInstance();
        }
        if(mGridLayoutManager == null){
            mGridLayoutManager = new GridLayoutManager(context, mColumn);
        }

        if(mEmojiViewAdapter == null){
            mEmojiViewAdapter = new EmojiViewAdapter(context);
        }

        mEmojiBoardFixer.getEmojiManager().registerChangeListener(this);

        this.setHasFixedSize(mEmojiBoardFixer.getEmojiViewManager().getHasFixedSize());
        this.setLayoutManager(mGridLayoutManager);
        this.setAdapter(mEmojiViewAdapter);
    }

    public void setEmojisInfo(Context context, int id , List<Emoji> list, int column, boolean showDelete){
        this.mId = id;
        this.mEmojis = list;
        this.mColumn = column;
        this.mShowDelete = showDelete;

        initEmojiView(context);
    }

    @Override
    public void onChange(int packetId) {
        //受到变化的相同包
        if(mEmojiViewAdapter != null && packetId == mId){
            mEmojiViewAdapter.notifyDataSetChanged();
        }
    }

    public class EmojiViewAdapter extends RecyclerView.Adapter<EmojiViewAdapter.ViewHolder>{

        private Context mContext;

        public EmojiViewAdapter(Context context){
            mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(new RelativeLayout(mContext));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if(position == mEmojis.size() && mShowDelete){
                //最后一个是delete
                try {
                    EmojiBoardFixer.getInstance().
                            getEmojiBoardConfiguration().
                            getEmojiProvider().onShowDelete(holder.mView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                holder.mView.setImageResource(R.mipmap.emoji_backspace);
//                holder.mTextView.setText("-1");
            }else{
                try {
                    EmojiBoardFixer.getInstance().
                            getEmojiBoardConfiguration().
                            getEmojiProvider().onShow(holder.mView,mEmojis.get(position));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public int getItemCount() {
            return mEmojis == null ? 0 : (mShowDelete ? mEmojis.size() + 1: mEmojis.size());
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            private View mView;
//            private TextView mTextView;

            public ViewHolder(RelativeLayout itemView) {
                super(itemView);

                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                int size = EmojiBoardFixer.getInstance().getEmojiViewManager().getBoardWidth() / mColumn;
                layoutParams.height = size;
                layoutParams.width = size;
                itemView.setLayoutParams(layoutParams);
                RelativeLayout.LayoutParams itemLayoutParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                itemLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                try {
                    mView = EmojiBoardFixer.getInstance().
                            getEmojiBoardConfiguration().
                            getEmojiProvider().
                            onCreateView(mContext,itemView);
                    mView.setLayoutParams(itemLayoutParams);
//                    mView.setLayoutParams(layoutParams);
                    itemView.addView(mView);
                }catch (Exception e){
                    e.printStackTrace();
                }
//                mView = (ImageView)itemView.findViewById(R.id.iv_emoji);

//                mTextView = (TextView)itemView.findViewById(R.id.tv_emoji_id);
            }
        }
    }
}
