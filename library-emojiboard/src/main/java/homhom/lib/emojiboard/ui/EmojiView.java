package homhom.lib.emojiboard.ui;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

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

    private View mCurrentView;

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

    private void setOnTouchView(View view){
        mCurrentView = view;
    }

    /**
     * 显示Emoji的详细界面
     * @param view
     */
    private void showEmojiDetail(View view){
        Log.i("emoji-view","showEmojiDetail()");
        hideEmojiDetail();
        setOnTouchView(view);
        Emoji emoji;
        if(view.getTag() != null){
            emoji = (Emoji) view.getTag();
            Log.i("showEmoji",emoji.mName);
        }
    }

    private void hideEmojiDetail(){
        Log.i("emoji-view","hideEmojiDetail()");
        setOnTouchView(null);
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
                            getEmojiProvider().onShow(holder.mEmojiPicView,
                            EmojiBoardFixer.getInstance().getEmojiManager().getDeleteBean());
                    holder.mItemView.setTag(EmojiBoardFixer.getInstance().getEmojiManager().getDeleteBean());
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                holder.mView.setImageResource(R.mipmap.emoji_backspace);
//                holder.mTextView.setText("-1");
            }else{
                try {
                    EmojiBoardFixer.getInstance().
                            getEmojiBoardConfiguration().
                            getEmojiProvider().onShow(holder.mEmojiPicView,mEmojis.get(position));
                    holder.mItemView.setTag(mEmojis.get(position));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public int getItemCount() {
            return mEmojis == null ? 0 : (mShowDelete ? mEmojis.size() + 1: mEmojis.size());
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements OnTouchListener,OnClickListener,OnLongClickListener{

            private View mEmojiPicView;

            private View mItemView;
//            private TextView mTextView;

            public ViewHolder(RelativeLayout itemView) {
                super(itemView);
                mItemView = itemView;
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                int size = EmojiBoardFixer.getInstance()
                        .getEmojiViewManager().getBoardWidth() / mColumn;
                layoutParams.height = size;
                layoutParams.width = size;
                itemView.setLayoutParams(layoutParams);
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
                itemView.setOnTouchListener(this);
                int itemSize = size - EmojiBoardFixer.getInstance()
                        .getEmojiViewManager().getViewPadding();
                RelativeLayout.LayoutParams itemLayoutParams = new RelativeLayout.LayoutParams(
                        itemSize,
                        itemSize);
                itemLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                try {
                    mEmojiPicView = EmojiBoardFixer.getInstance().
                            getEmojiBoardConfiguration().
                            getEmojiProvider().
                            onCreateView(mContext,itemView);
                    mEmojiPicView.setLayoutParams(EmojiBoardFixer.getInstance().
                            getEmojiBoardConfiguration().
                            getEmojiProvider().
                            onSetLayoutParams(itemLayoutParams));
//                    mView.setLayoutParams(layoutParams);
                    itemView.addView(mEmojiPicView);
                }catch (Exception e){
                    e.printStackTrace();
                }
//                mView = (ImageView)itemView.findViewById(R.id.iv_emoji);

//                mTextView = (TextView)itemView.findViewById(R.id.tv_emoji_id);
            }

            @Override
            public void onClick(View v) {
                Log.i("emoji-view","onClick");
            }

            @Override
            public boolean onLongClick(View v) {
                Log.i("emoji-view","onLongClick");
                showEmojiDetail(v);
                return false;
            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(mCurrentView != null){
                        //说明是长按的弹起
                        hideEmojiDetail();
                        return true;
                    }
                }
                return false;
            }
        }
    }
}
