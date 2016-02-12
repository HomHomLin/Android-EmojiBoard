package homhom.lib.emojiboard.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import homhom.lib.emojiboard.R;
import homhom.lib.emojiboard.bean.PacketInfo;

/**
 * Created by linhomhom on 2015/8/10.
 */
public class EmojiBoardTab extends HorizontalScrollView {

    public interface EmojiTabListener{
        public View onCreateTabView(int position, ViewGroup parent);
        //    public View getPageIcon(int position, PacketInfo info, View view);
        public boolean getPageIconTextVisible(int position);
        public String getPageIconText(int position,View view);
    }

    // @formatter:off
    private static final int[] ATTRS = new int[]{
            android.R.attr.textSize,
            android.R.attr.textColor
    };
    // @formatter:on

    private LinearLayout.LayoutParams defaultTabLayoutParams;
    private LinearLayout.LayoutParams expandedTabLayoutParams;

    private final PageListener pageListener = new PageListener();
    public ViewPager.OnPageChangeListener delegatePageListener;

    private LinearLayout tabsContainer;
    private ViewPager pager;

    private int tabCount;

    private int currentPosition = 0;
    private float currentPositionOffset = 0f;

    private Paint rectPaint;
    private Paint dividerPaint;

    private boolean checkedTabWidths = false;

    private int indicatorColor = 0xFF666666;
    private int underlineColor = 0x1A000000;
    private int dividerColor = 0x1A000000;

    private boolean shouldExpand = true;
    private boolean textAllCaps = true;

    private int scrollOffset = 52;
    private int indicatorHeight = 3;
    private int underlineHeight = 2;
    private int dividerPadding = 12;
    private int tabPadding = 24;
    private int tabPaddingTopBottom = 0;
    private int dividerWidth = 1;

    private int tabTextSize = 15;
    private int tabTextColor = 0xFF666666;
    private int tabTextSelectColor = 0xFF666666;
    private Typeface tabTypeface = null;
    private int tabTypefaceStyle = Typeface.NORMAL;
    private int tabDrawMode = DRAW_MODE_NORMAL;

    public static final int DRAW_MODE_NORMAL = 0;
    public static final int DRAW_MODE_TEXT = 1;

    private int lastScrollX = 0;

    private int tabBackgroundResId = R.drawable.emoji_background_tab;
//    private int tabBackgroundResId;

    private Locale locale;

    public EmojiBoardTab(Context context) {
        this(context, null);
    }

    public EmojiBoardTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmojiBoardTab(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setFillViewport(true);
        setWillNotDraw(false);

        tabsContainer = new LinearLayout(context);
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(tabsContainer);

        DisplayMetrics dm = getResources().getDisplayMetrics();

        scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);
        indicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorHeight, dm);
        underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
        dividerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerPadding, dm);
        tabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabPadding, dm);
        dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerWidth, dm);
        tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tabTextSize, dm);

        // get system attrs (android:textSize and android:textColor)

        TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);

        tabTextSize = a.getDimensionPixelSize(0, tabTextSize);
//        tabTextColor = a.getColor(1, tabTextColor);

        a.recycle();

        // get custom attrs


        a = context.obtainStyledAttributes(attrs, R.styleable.EmojiBoardTab);

        indicatorColor = a.getColor(R.styleable.EmojiBoardTab_tabIndicatorColor, indicatorColor);
        underlineColor = a.getColor(R.styleable.EmojiBoardTab_tabUnderlineColor, underlineColor);
        dividerColor = a.getColor(R.styleable.EmojiBoardTab_tabDividerColor, dividerColor);
        indicatorHeight = a.getDimensionPixelSize(R.styleable.EmojiBoardTab_tabIndicatorHeight, indicatorHeight);
        underlineHeight = a.getDimensionPixelSize(R.styleable.EmojiBoardTab_tabUnderlineHeight, underlineHeight);
        dividerPadding = a.getDimensionPixelSize(R.styleable.EmojiBoardTab_tabDividerPadding, dividerPadding);
        tabPadding = a.getDimensionPixelSize(R.styleable.EmojiBoardTab_tabPaddingLeftRight, tabPadding);
        tabPaddingTopBottom = a.getDimensionPixelSize(R.styleable.EmojiBoardTab_tabPaddingTopBottom, tabPaddingTopBottom);
        tabBackgroundResId = a.getResourceId(R.styleable.EmojiBoardTab_tabBackground, tabBackgroundResId);
        shouldExpand = a.getBoolean(R.styleable.EmojiBoardTab_tabShouldExpand, shouldExpand);
        scrollOffset = a.getDimensionPixelSize(R.styleable.EmojiBoardTab_tabScrollOffset, scrollOffset);
        textAllCaps = a.getBoolean(R.styleable.EmojiBoardTab_tabTextAllCaps, textAllCaps);
        tabTextSelectColor = a.getColor(R.styleable.EmojiBoardTab_tabTextSelectColor, dividerColor);
        tabDrawMode = a.getInteger(R.styleable.EmojiBoardTab_tabDrawMode,DRAW_MODE_NORMAL);

        a.recycle();

        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Style.FILL);

        dividerPaint = new Paint();
        dividerPaint.setAntiAlias(true);
        dividerPaint.setStrokeWidth(dividerWidth);

        defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        expandedTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);

        if (locale == null) {
            locale = getResources().getConfiguration().locale;
        }
    }

    public void setViewPager(ViewPager pager) {
        this.pager = pager;

        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        pager.addOnPageChangeListener(pageListener);

        notifyDataSetChanged();
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.delegatePageListener = listener;
    }

    public void notifyDataSetChanged() {

        tabsContainer.removeAllViews();

        tabCount = pager.getAdapter().getCount();

        for (int i = 0; i < tabCount; i++) {

            if (pager.getAdapter() instanceof EmojiTabListener) {
                addIconTab(i, (EmojiTabListener) pager.getAdapter());
            } else {
//                addIconTab(i, R.drawable.home_categry_icon_n, pager.getAdapter().getPageTitle(i).toString());

                addTextTab(i, pager.getAdapter().getPageTitle(i).toString());
            }

        }

        updateTabStyles();

        checkedTabWidths = false;

        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                currentPosition = pager.getCurrentItem();
                scrollToChild(currentPosition, 0);
            }
        });

        pageListener.onPageSelected(0);//default

    }

    private void addTextTab(final int position, String title) {

        LinearLayout tab = new LinearLayout(getContext());
        tab.setOrientation(LinearLayout.HORIZONTAL);
        tab.setGravity(Gravity.CENTER);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollToChild(position, 0);
                pager.setCurrentItem(position);
            }
        });

        TextView txt = new TextView(getContext());
        txt.setText(title);
        txt.setFocusable(true);
        txt.setGravity(Gravity.CENTER);
        txt.setSingleLine();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.emoji_dot_wh), getResources().getDimensionPixelSize(R.dimen.emoji_dot_wh));
        ImageView dot = new ImageView(getContext());
        dot.setImageResource(R.drawable.emoji_tab_new_tips_bg);
        dot.setLayoutParams(layoutParams);

        LinearLayout dot_layout = new LinearLayout(getContext());
        dot_layout.setGravity(Gravity.TOP);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, getResources().getDimensionPixelSize(R.dimen.emoji_dot_linelayout_h));
        dot_layout.setLayoutParams(layoutParams2);
        dot_layout.addView(dot);

        tab.addView(txt);
        tab.setTag(txt);
        tab.addView(dot_layout);
        dot_layout.setVisibility(View.INVISIBLE);
        tabsContainer.addView(tab);

    }

    public void showDot(int index) {
        LinearLayout tab = (LinearLayout) tabsContainer.getChildAt(index);
        LinearLayout dot_layout = (LinearLayout) tab.getChildAt(1);
        dot_layout.setVisibility(View.VISIBLE);
    }

    public void hideDot(int index) {
        LinearLayout tab = (LinearLayout) tabsContainer.getChildAt(index);
        LinearLayout dot_layout = (LinearLayout) tab.getChildAt(1);
        dot_layout.setVisibility(View.INVISIBLE);
    }

    private void addIconTab(final int position, EmojiTabListener tabProvider) {

        LinearLayout tab = new LinearLayout(getContext());
        tab.setOrientation(LinearLayout.HORIZONTAL);
        tab.setGravity(Gravity.CENTER);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollToChild(position, 0);
                pager.setCurrentItem(position);
            }
        });

        if(tabProvider.getPageIconTextVisible(position)){
            //如果要求有文字
            LinearLayout icon = new LinearLayout(getContext());
            icon.setOrientation(LinearLayout.VERTICAL);
            icon.setGravity(Gravity.CENTER);
            icon.setFocusable(true);

            View view = tabProvider.onCreateTabView(position,icon);
            if(view != null){
                view.setLayoutParams(new LinearLayout.LayoutParams(500,500));
                view.setFocusable(true);
            }


            TextView txt = new TextView(getContext());
            txt.setText(tabProvider.getPageIconText(position,txt));
            txt.setFocusable(true);
            txt.setGravity(Gravity.CENTER);
            txt.setSingleLine();

            icon.addView(view);
            icon.addView(txt);

            tab.addView(txt);

            tab.setTag(txt);
        }else{
            View view = tabProvider.onCreateTabView(position,tab);
            if(view != null){
                view.setFocusable(true);
            }

            tab.addView(view);
        }



        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.emoji_dot_wh), getResources().getDimensionPixelSize(R.dimen.emoji_dot_wh));
        ImageView dot = new ImageView(getContext());
        dot.setImageResource(R.drawable.emoji_tab_new_tips_bg);
        dot.setLayoutParams(layoutParams);

        LinearLayout dot_layout = new LinearLayout(getContext());
        dot_layout.setGravity(Gravity.TOP);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dot_layout.setLayoutParams(layoutParams2);
        dot_layout.addView(dot);

        tab.addView(dot_layout);
        dot_layout.setVisibility(View.INVISIBLE);
        tabsContainer.addView(tab);

//        ImageButton tab = new ImageButton(getContext());
//        tab.setFocusable(true);
//        tab.setImageResource(resId);
//
//        tab.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //点击设置到这个地方
//                pager.setCurrentItem(position);
//            }
//        });
//
//        tabsContainer.addView(tab);
    }

    public View getTabAt(int pos) {
        if (pos >= tabsContainer.getChildCount()) {
            throw new IllegalStateException("pos is too big.");
        }
        return tabsContainer.getChildAt(pos);
    }

    private void updateTabStyles() {

        for (int i = 0; i < tabCount; i++) {

            View v = tabsContainer.getChildAt(i);

            //v.setLayoutParams(defaultTabLayoutParams);
            //my modify
            v.setLayoutParams(expandedTabLayoutParams);
            v.setBackgroundResource(tabBackgroundResId);
            if (shouldExpand) {
                v.setPadding(0, 0, 0, 0);
            } else {
                v.setPadding(tabPadding, tabPaddingTopBottom, tabPadding, tabPaddingTopBottom);
            }

            if (v instanceof LinearLayout) {

                LinearLayout tab = (LinearLayout) v;
                if(tab.getTag() != null) {
                    TextView tv = (TextView) tab.getTag();
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
                    tv.setTypeface(tabTypeface, tabTypefaceStyle);
                    tv.setTextColor(tabTextColor);

                    // setAllCaps() is only available from API 14, so the upper case is made manually if we are on a
                    // pre-ICS-build
                    if (textAllCaps) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                            tv.setAllCaps(true);
                        } else {
                            tv.setText(tv.getText().toString().toUpperCase(locale));
                        }
                    }
                }
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!shouldExpand || MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            return;
        }

        int myWidth = getMeasuredWidth();
        int childWidth = 0;
        for (int i = 0; i < tabCount; i++) {
            childWidth += tabsContainer.getChildAt(i).getMeasuredWidth();
        }

        if (!checkedTabWidths && childWidth > 0 && myWidth > 0) {

            if (childWidth <= myWidth) {
                for (int i = 0; i < tabCount; i++) {
                    tabsContainer.getChildAt(i).setLayoutParams(expandedTabLayoutParams);
                }
            }

            checkedTabWidths = true;
        }
    }

    private void scrollToChild(int position, int offset) {

        if (tabCount == 0) {
            return;
        }

        int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;

        if (position > 0 || offset > 0) {
            newScrollX -= scrollOffset;
        }

        if (newScrollX != lastScrollX) {
            lastScrollX = newScrollX;
            scrollTo(newScrollX, 0);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode() || tabCount == 0) {
            return;
        }

        if(tabDrawMode == DRAW_MODE_NORMAL) {
            drawTabNormalMode(canvas);
        }else {
            drawTabTextMode(canvas);
        }
    }

    private void drawTabNormalMode(Canvas canvas){
        final int height = getHeight();

        // 设置提示下划线的颜色

        rectPaint.setColor(indicatorColor);

        // default: line below current tab
        View currentTab = tabsContainer.getChildAt(currentPosition);
        float lineLeft = currentTab.getLeft();
        float lineRight = currentTab.getRight();

        // if there is an offset, start interpolating left and right coordinates between current and next tab
        if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {

            View nextTab = tabsContainer.getChildAt(currentPosition + 1);
            final float nextTabLeft = nextTab.getLeft();
            final float nextTabRight = nextTab.getRight();

            lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
            lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);

        }

        //绘制提示下划线
        canvas.drawRect(lineLeft , height - indicatorHeight, lineRight , height, rectPaint);

        // 绘制下划线

        rectPaint.setColor(underlineColor);
        canvas.drawRect(0, height - underlineHeight, tabsContainer.getWidth(), height, rectPaint);

        // 绘制分割线

        dividerPaint.setColor(dividerColor);
        for (int i = 0; i < tabCount - 1; i++) {
            View tab = tabsContainer.getChildAt(i);
            canvas.drawLine(tab.getRight(), dividerPadding, tab.getRight(), height - dividerPadding, dividerPaint);
        }
    }

    private void drawTabTextMode(Canvas canvas){

        final int height = getHeight();

        // 设置提示下划线的颜色

        rectPaint.setColor(indicatorColor);

        // default: line below current tab
        View currentTab = tabsContainer.getChildAt(currentPosition);
        float lineLeft = currentTab.getLeft();
        float lineRight = currentTab.getRight();

        View currentTextView = ((LinearLayout)currentTab).getChildAt(0);
        float currentTextViewLeft = currentTextView.getLeft();
        float currentTextViewRight = currentTextView.getRight();

        // if there is an offset, start interpolating left and right coordinates between current and next tab
        if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {

            View nextTab = tabsContainer.getChildAt(currentPosition + 1);
            final float nextTabLeft = nextTab.getLeft();
            final float nextTabRight = nextTab.getRight();

            lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
            lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);

            View nextTextView = ((LinearLayout)nextTab).getChildAt(0);
            float nextTextViewLeft = nextTextView.getLeft();
            float nextTextViewRight = nextTextView.getRight();

            currentTextViewLeft = (currentPositionOffset * nextTextViewLeft + (1f - currentPositionOffset) * currentTextViewLeft);
            currentTextViewRight = (currentPositionOffset * nextTextViewRight + (1f - currentPositionOffset) * currentTextViewRight);
        }

        //绘制提示下划线
        canvas.drawRect(lineLeft + currentTextViewLeft, height - indicatorHeight, lineLeft + currentTextViewRight , height, rectPaint);


//        rectPaint.setColor(underlineColor);
//        canvas.drawRect(0, height - underlineHeight, tabsContainer.getWidth(), height, rectPaint);

//        for(int i = 0 ; i < tabCount - 1 ; i ++){
//            View tab = tabsContainer.getChildAt(i);
//            View tabTextView = ((LinearLayout)tab).getChildAt(0);
//            canvas.drawRect(tab.getLeft() + tabTextView.getLeft(), height - underlineHeight, tab.getLeft() + tabTextView.getRight(), height, rectPaint);
//        }

        // 分割线paint

        dividerPaint.setColor(dividerColor);

        // 下划线paint
        rectPaint.setColor(underlineColor);

        for (int i = 0; i < tabCount; i++) {
            View tab = tabsContainer.getChildAt(i);
            //绘制分割线
            if(i < tabCount - 1) {
                canvas.drawLine(tab.getRight(), dividerPadding, tab.getRight(), height - dividerPadding, dividerPaint);
            }
            View tabTextView = ((LinearLayout)tab).getChildAt(0);
            canvas.drawRect(tab.getLeft() + tabTextView.getLeft(), height - underlineHeight, tab.getLeft() + tabTextView.getRight(), height, rectPaint);
        }
    }

    private class PageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            currentPosition = position;
            currentPositionOffset = positionOffset;

            if(tabsContainer != null  && tabsContainer.getChildAt(position) != null) {

                scrollToChild(position, (int) (positionOffset * tabsContainer.getChildAt(position).getWidth()));
            }

            invalidate();

            if (delegatePageListener != null) {
                delegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(pager.getCurrentItem(), 0);
            }

            if (delegatePageListener != null) {
                delegatePageListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {

            setSelectItem(position);

            if (delegatePageListener != null) {
                delegatePageListener.onPageSelected(position);
            }
        }

    }

    public void setSelectItem(int position){
        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            if (i == position) {
                LinearLayout linearLayout = ((LinearLayout) tabsContainer.getChildAt(i));
                if(linearLayout.getTag() != null) {
                    ((TextView) linearLayout.getTag()).setTextColor(tabTextSelectColor);
                }
                if (pager.getAdapter() instanceof EmojiTabListener) {
                    //被选中状态
                    //Todo:添加；一个背景变色的功能，选中这里背景就变色
//                    ((TextView) linearLayout.getChildAt(0)).setCompoundDrawablesWithIntrinsicBounds(0, ((EmojiIconTabProvider) pager.getAdapter()).getPageSelectIcon(i), 0, 0);
                }
//                    ((TextView) linearLayout.getChildAt(0)).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_categry_icon_f_n, 0, 0);
            } else {
                LinearLayout linearLayout = ((LinearLayout) tabsContainer.getChildAt(i));
                if(linearLayout.getTag() != null) {
                    ((TextView) linearLayout.getTag()).setTextColor(tabTextColor);
                }
                if (pager.getAdapter() instanceof EmojiTabListener) {
                    //取消选中状态
                    //Todo:添加
//                    ((TextView) linearLayout.getChildAt(0)).setCompoundDrawablesWithIntrinsicBounds(0, ((EmojiIconTabProvider) pager.getAdapter()).getPageIcon(i), 0, 0);
                }
//                    ((TextView) linearLayout.getChildAt(0)).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_categry_icon_n, 0, 0);

            }
        }
    }

    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        invalidate();
    }

    public void setIndicatorColorResource(int resId) {
        this.indicatorColor = getResources().getColor(resId);
        invalidate();
    }

    public int getIndicatorColor() {
        return this.indicatorColor;
    }

    public void setIndicatorHeight(int indicatorLineHeightPx) {
        this.indicatorHeight = indicatorLineHeightPx;
        invalidate();
    }

    public int getIndicatorHeight() {
        return indicatorHeight;
    }

    public void setUnderlineColor(int underlineColor) {
        this.underlineColor = underlineColor;
        invalidate();
    }

    public void setUnderlineColorResource(int resId) {
        this.underlineColor = getResources().getColor(resId);
        invalidate();
    }

    public int getUnderlineColor() {
        return underlineColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        invalidate();
    }

    public void setDividerColorResource(int resId) {
        this.dividerColor = getResources().getColor(resId);
        invalidate();
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public void setUnderlineHeight(int underlineHeightPx) {
        this.underlineHeight = underlineHeightPx;
        invalidate();
    }

    public int getUnderlineHeight() {
        return underlineHeight;
    }

    public void setDividerPadding(int dividerPaddingPx) {
        this.dividerPadding = dividerPaddingPx;
        invalidate();
    }

    public int getDividerPadding() {
        return dividerPadding;
    }

    public void setScrollOffset(int scrollOffsetPx) {
        this.scrollOffset = scrollOffsetPx;
        invalidate();
    }

    public int getScrollOffset() {
        return scrollOffset;
    }

    public void setShouldExpand(boolean shouldExpand) {
        this.shouldExpand = shouldExpand;
        requestLayout();
    }

    public boolean getShouldExpand() {
        return shouldExpand;
    }

    public boolean isTextAllCaps() {
        return textAllCaps;
    }

    public void setAllCaps(boolean textAllCaps) {
        this.textAllCaps = textAllCaps;
    }

    public void setTextSize(int textSizePx) {
        this.tabTextSize = textSizePx;
        updateTabStyles();
    }

    public int getTextSize() {
        return tabTextSize;
    }

    public void setTextColor(int textColor) {
        this.tabTextColor = textColor;
        updateTabStyles();
    }

    public void setTextColorResource(int resId) {
        this.tabTextColor = getResources().getColor(resId);
        updateTabStyles();
    }

    public int getTextColor() {
        return tabTextColor;
    }

    public void setTypeface(Typeface typeface, int style) {
        this.tabTypeface = typeface;
        this.tabTypefaceStyle = style;
        updateTabStyles();
    }

    public void setTabBackground(int resId) {
        this.tabBackgroundResId = resId;
    }

    public int getTabBackground() {
        return tabBackgroundResId;
    }

    public void setTabPaddingLeftRight(int paddingPx) {
        this.tabPadding = paddingPx;
        updateTabStyles();
    }

    public int getTabPaddingLeftRight() {
        return tabPadding;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        currentPosition = savedState.currentPosition;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = currentPosition;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        int currentPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

}