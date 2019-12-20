package com.yy.mshow.peripherals;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.text.TextUtilsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import com.yy.mshow.R;
import java.util.Locale;

public class PinEntryEditText extends AppCompatEditText {
  private static final String XML_NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android";
  
  protected boolean mAnimate = false;
  
  protected int mAnimatedType = 0;
  
  protected float[] mCharBottom;
  
  protected Paint mCharPaint;
  
  protected float mCharSize;
  
  protected View.OnClickListener mClickListener;
  
  protected ColorStateList mColorStates;
  
  protected int[] mColors;
  
  protected boolean mHasError = false;
  
  protected boolean mIsDigitSquare = false;
  
  protected Paint mLastCharPaint;
  
  protected RectF[] mLineCoords;
  
  protected float mLineStroke = 1.0F;
  
  protected float mLineStrokeSelected = 2.0F;
  
  protected Paint mLinesPaint;
  
  protected String mMask = null;
  
  protected StringBuilder mMaskChars = null;
  
  protected int mMaxLength = 4;
  
  protected float mNumChars = 4.0F;
  
  protected OnChangedListener mOnChangedListener;
  
  protected ColorStateList mOriginalTextColors;
  
  protected Drawable mPinBackground;
  
  protected String mSingleCharHint = null;
  
  protected Paint mSingleCharPaint;
  
  protected float mSpace = 24.0F;
  
  protected int[][] mStates;
  
  protected float mTextBottomPadding = 8.0F;
  
  protected Rect mTextHeight = new Rect();
  
  public PinEntryEditText(Context paramContext) {
    super(paramContext);
    int[] arrayOfInt = { -16842908 };
    this.mStates = new int[][] { { 16842913 }, { 16842914 }, { 16842908 }, arrayOfInt };
    this.mColors = new int[] { -16711936, -65536, -16777216, -7829368 };
    this.mColorStates = new ColorStateList(this.mStates, this.mColors);
  }
  
  public PinEntryEditText(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    this.mStates = new int[][] { { 16842913 }, { 16842914 }, { 16842908 }, { -16842908 } };
    this.mColors = new int[] { -16711936, -65536, -16777216, -7829368 };
    this.mColorStates = new ColorStateList(this.mStates, this.mColors);
    init(paramContext, paramAttributeSet);
  }
  
  public PinEntryEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    this.mStates = new int[][] { { 16842913 }, { 16842914 }, { 16842908 }, { -16842908 } };
    this.mColors = new int[] { -16711936, -65536, -16777216, -7829368 };
    this.mColorStates = new ColorStateList(this.mStates, this.mColors);
    init(paramContext, paramAttributeSet);
  }
  
  private void animateBottomUp(final CharSequence text, final int start) {
    this.mCharBottom[paramInt] = (this.mLineCoords[paramInt]).bottom - this.mTextBottomPadding;
    ValueAnimator valueAnimator1 = ValueAnimator.ofFloat(new float[] { this.mCharBottom[paramInt] + getPaint().getTextSize(), this.mCharBottom[paramInt] });
    valueAnimator1.setDuration(300L);
    valueAnimator1.setInterpolator(new OvershootInterpolator());
    valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
            Float float = (Float)param1ValueAnimator.getAnimatedValue();
            PinEntryEditText.this.mCharBottom[start] = float.floatValue();
            PinEntryEditText.this.invalidate();
          }
        });
    this.mLastCharPaint.setAlpha(255);
    ValueAnimator valueAnimator2 = ValueAnimator.ofInt(new int[] { 0, 255 });
    valueAnimator2.setDuration(300L);
    valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
            Integer integer = (Integer)param1ValueAnimator.getAnimatedValue();
            PinEntryEditText.this.mLastCharPaint.setAlpha(integer.intValue());
          }
        });
    AnimatorSet animatorSet = new AnimatorSet();
    if (this.mOnChangedListener != null)
      animatorSet.addListener(new Animator.AnimatorListener() {
            public void onAnimationCancel(Animator param1Animator) {}
            
            public void onAnimationEnd(Animator param1Animator) {
              boolean bool;
              PinEntryEditText.OnChangedListener onChangedListener = PinEntryEditText.this.mOnChangedListener;
              CharSequence charSequence = text;
              if (text.length() == PinEntryEditText.this.mMaxLength) {
                bool = true;
              } else {
                bool = false;
              } 
              onChangedListener.onChanged(charSequence, bool);
            }
            
            public void onAnimationRepeat(Animator param1Animator) {}
            
            public void onAnimationStart(Animator param1Animator) {}
          }); 
    animatorSet.playTogether(new Animator[] { valueAnimator1, valueAnimator2 });
    animatorSet.start();
  }
  
  private void animatePopIn() {
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(new float[] { 1.0F, getPaint().getTextSize() });
    valueAnimator.setDuration(200L);
    valueAnimator.setInterpolator(new OvershootInterpolator());
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
            PinEntryEditText.this.mLastCharPaint.setTextSize(((Float)param1ValueAnimator.getAnimatedValue()).floatValue());
            PinEntryEditText.this.invalidate();
          }
        });
    if (this.mOnChangedListener != null)
      valueAnimator.addListener(new Animator.AnimatorListener() {
            public void onAnimationCancel(Animator param1Animator) {}
            
            public void onAnimationEnd(Animator param1Animator) {
              boolean bool;
              Editable editable = PinEntryEditText.this.getText();
              PinEntryEditText.OnChangedListener onChangedListener = PinEntryEditText.this.mOnChangedListener;
              if (editable.length() == PinEntryEditText.this.mMaxLength) {
                bool = true;
              } else {
                bool = false;
              } 
              onChangedListener.onChanged(editable, bool);
            }
            
            public void onAnimationRepeat(Animator param1Animator) {}
            
            public void onAnimationStart(Animator param1Animator) {}
          }); 
    valueAnimator.start();
  }
  
  private int getColorForState(int... paramVarArgs) { return this.mColorStates.getColorForState(paramVarArgs, -7829368); }
  
  private CharSequence getFullText() { return (this.mMask == null) ? getText() : getMaskChars(); }
  
  private StringBuilder getMaskChars() {
    if (this.mMaskChars == null)
      this.mMaskChars = new StringBuilder(); 
    int i = getText().length();
    while (this.mMaskChars.length() != i) {
      if (this.mMaskChars.length() < i) {
        this.mMaskChars.append(this.mMask);
        continue;
      } 
      this.mMaskChars.deleteCharAt(this.mMaskChars.length() - 1);
    } 
    return this.mMaskChars;
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet) {
    float f = (paramContext.getResources().getDisplayMetrics()).density;
    this.mLineStroke *= f;
    this.mLineStrokeSelected *= f;
    this.mSpace *= f;
    this.mTextBottomPadding *= f;
    TypedValue typedValue = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PinEntryEditText, 0, 0);
    try {
      TypedValue typedValue1 = new TypedValue();
      typedValue.getValue(0, typedValue1);
      this.mAnimatedType = typedValue1.data;
      this.mMask = typedValue.getString(1);
      this.mSingleCharHint = typedValue.getString(2);
      this.mLineStroke = typedValue.getDimension(3, this.mLineStroke);
      this.mLineStrokeSelected = typedValue.getDimension(4, this.mLineStrokeSelected);
      this.mSpace = typedValue.getDimension(5, this.mSpace);
      this.mTextBottomPadding = typedValue.getDimension(6, this.mTextBottomPadding);
      this.mIsDigitSquare = typedValue.getBoolean(9, this.mIsDigitSquare);
      this.mPinBackground = typedValue.getDrawable(8);
      ColorStateList colorStateList = typedValue.getColorStateList(7);
      if (colorStateList != null)
        this.mColorStates = colorStateList; 
      typedValue.recycle();
      this.mCharPaint = new Paint(getPaint());
      this.mLastCharPaint = new Paint(getPaint());
      this.mSingleCharPaint = new Paint(getPaint());
      this.mLinesPaint = new Paint(getPaint());
      this.mLinesPaint.setStrokeWidth(this.mLineStroke);
      typedValue = new TypedValue();
      paramContext.getTheme().resolveAttribute(2130772153, typedValue, true);
      int i = typedValue.data;
      this.mColors[0] = i;
      if (isInEditMode()) {
        i = -7829368;
      } else {
        i = ContextCompat.getColor(paramContext, 2131558475);
      } 
      this.mColors[1] = i;
      if (isInEditMode()) {
        i = -7829368;
      } else {
        i = ContextCompat.getColor(paramContext, 2131558475);
      } 
      this.mColors[2] = i;
      setBackgroundResource(0);
      this.mMaxLength = paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "maxLength", 4);
      this.mNumChars = this.mMaxLength;
      super.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onActionItemClicked(ActionMode param1ActionMode, MenuItem param1MenuItem) { return false; }
            
            public boolean onCreateActionMode(ActionMode param1ActionMode, Menu param1Menu) { return false; }
            
            public void onDestroyActionMode(ActionMode param1ActionMode) {}
            
            public boolean onPrepareActionMode(ActionMode param1ActionMode, Menu param1Menu) { return false; }
          });
      super.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
              PinEntryEditText.this.setSelection(PinEntryEditText.this.getText().length());
              if (PinEntryEditText.this.mClickListener != null)
                PinEntryEditText.this.mClickListener.onClick(param1View); 
            }
          });
      setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View param1View) {
              PinEntryEditText.this.setSelection(PinEntryEditText.this.getText().length());
              return true;
            }
          });
      if ((getInputType() & 0x80) == 128 && TextUtils.isEmpty(this.mMask)) {
        this.mMask = "●";
      } else if ((getInputType() & 0x10) == 16 && TextUtils.isEmpty(this.mMask)) {
        this.mMask = "●";
      } 
      if (!TextUtils.isEmpty(this.mMask))
        this.mMaskChars = getMaskChars(); 
      getPaint().getTextBounds("|", 0, 1, this.mTextHeight);
      if (this.mAnimatedType > -1) {
        bool = true;
      } else {
        bool = false;
      } 
      return;
    } finally {
      typedValue.recycle();
    } 
  }
  
  public void focus() {
    requestFocus();
    ((InputMethodManager)getContext().getSystemService("input_method")).showSoftInput(this, 0);
  }
  
  public boolean isError() { return this.mHasError; }
  
  protected void onDraw(Canvas paramCanvas) {
    CharSequence charSequence = getFullText();
    int i = charSequence.length();
    float[] arrayOfFloat = new float[i];
    getPaint().getTextWidths(charSequence, 0, i, arrayOfFloat);
    float f2 = 0.0F;
    float f1 = 0.0F;
    if (this.mSingleCharHint != null) {
      float[] arrayOfFloat1 = new float[this.mSingleCharHint.length()];
      getPaint().getTextWidths(this.mSingleCharHint, arrayOfFloat1);
      int j = arrayOfFloat1.length;
      byte b1 = 0;
      while (true) {
        f2 = f1;
        if (b1 < j) {
          f1 += arrayOfFloat1[b1];
          b1++;
          continue;
        } 
        break;
      } 
    } 
    for (byte b = 0; b < this.mNumChars; b++) {
      if (this.mPinBackground != null) {
        boolean bool2;
        boolean bool1;
        if (b < i) {
          bool1 = true;
        } else {
          bool1 = false;
        } 
        if (b == i) {
          bool2 = true;
        } else {
          bool2 = false;
        } 
        updateDrawableState(bool1, bool2);
        this.mPinBackground.setBounds((int)(this.mLineCoords[b]).left, (int)(this.mLineCoords[b]).top, (int)(this.mLineCoords[b]).right, (int)(this.mLineCoords[b]).bottom);
        this.mPinBackground.draw(paramCanvas);
      } 
      f1 = (this.mLineCoords[b]).left + this.mCharSize / 2.0F;
      if (i > b) {
        if (!this.mAnimate || b != i - 1) {
          paramCanvas.drawText(charSequence, b, b + true, f1 - arrayOfFloat[b] / 2.0F, this.mCharBottom[b], this.mCharPaint);
        } else {
          paramCanvas.drawText(charSequence, b, b + 1, f1 - arrayOfFloat[b] / 2.0F, this.mCharBottom[b], this.mLastCharPaint);
        } 
      } else if (this.mSingleCharHint != null) {
        paramCanvas.drawText(this.mSingleCharHint, f1 - f2 / 2.0F, this.mCharBottom[b], this.mSingleCharPaint);
      } 
      if (this.mPinBackground == null) {
        boolean bool;
        if (b <= i) {
          bool = true;
        } else {
          bool = false;
        } 
        updateColorForLines(bool);
        paramCanvas.drawLine((this.mLineCoords[b]).left, (this.mLineCoords[b]).top, (this.mLineCoords[b]).right, (this.mLineCoords[b]).bottom, this.mLinesPaint);
      } 
    } 
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mOriginalTextColors = getTextColors();
    if (this.mOriginalTextColors != null) {
      this.mLastCharPaint.setColor(this.mOriginalTextColors.getDefaultColor());
      this.mCharPaint.setColor(this.mOriginalTextColors.getDefaultColor());
      this.mSingleCharPaint.setColor(getCurrentHintTextColor());
    } 
    paramInt1 = getWidth() - ViewCompat.getPaddingEnd(this) - ViewCompat.getPaddingStart(this);
    if (this.mSpace < 0.0F) {
      this.mCharSize = paramInt1 / (this.mNumChars * 2.0F - 1.0F);
    } else {
      this.mCharSize = (paramInt1 - this.mSpace * (this.mNumChars - 1.0F)) / this.mNumChars;
    } 
    this.mLineCoords = new RectF[(int)this.mNumChars];
    this.mCharBottom = new float[(int)this.mNumChars];
    paramInt4 = getHeight() - getPaddingBottom();
    if (TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == 1) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    } 
    if (paramInt1 != 0) {
      paramInt2 = -1;
      paramInt1 = (int)((getWidth() - ViewCompat.getPaddingStart(this)) - this.mCharSize);
    } else {
      paramInt2 = 1;
      paramInt1 = ViewCompat.getPaddingStart(this);
    } 
    for (paramInt3 = 0; paramInt3 < this.mNumChars; paramInt3++) {
      this.mLineCoords[paramInt3] = new RectF(paramInt1, paramInt4, paramInt1 + this.mCharSize, paramInt4);
      if (this.mPinBackground != null)
        if (this.mIsDigitSquare) {
          (this.mLineCoords[paramInt3]).top = getPaddingTop();
          (this.mLineCoords[paramInt3]).right = paramInt1 + this.mLineCoords[paramInt3].height();
        } else {
          RectF rectF = this.mLineCoords[paramInt3];
          rectF.top -= this.mTextHeight.height() + this.mTextBottomPadding * 2.0F;
        }  
      if (this.mSpace < 0.0F) {
        paramInt1 = (int)(paramInt1 + paramInt2 * this.mCharSize * 2.0F);
      } else {
        paramInt1 = (int)(paramInt1 + paramInt2 * (this.mCharSize + this.mSpace));
      } 
      this.mCharBottom[paramInt3] = (this.mLineCoords[paramInt3]).bottom - this.mTextBottomPadding;
    } 
  }
  
  protected void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
    boolean bool = false;
    setError(false);
    if (this.mLineCoords == null || !this.mAnimate) {
      if (this.mOnChangedListener != null) {
        OnChangedListener onChangedListener = this.mOnChangedListener;
        if (paramCharSequence.length() == this.mMaxLength)
          bool = true; 
        onChangedListener.onChanged(paramCharSequence, bool);
      } 
      return;
    } 
    if (this.mAnimatedType == -1) {
      invalidate();
      return;
    } 
    if (paramInt3 > paramInt2) {
      if (this.mAnimatedType == 0) {
        animatePopIn();
        return;
      } 
      animateBottomUp(paramCharSequence, paramInt1);
      return;
    } 
  }
  
  public void setAnimateText(boolean paramBoolean) { this.mAnimate = paramBoolean; }
  
  public void setCustomSelectionActionModeCallback(ActionMode.Callback paramCallback) { throw new RuntimeException("setCustomSelectionActionModeCallback() not supported."); }
  
  public void setError(boolean paramBoolean) { this.mHasError = paramBoolean; }
  
  public void setMaxLength(int paramInt) {
    this.mMaxLength = paramInt;
    this.mNumChars = paramInt;
    setFilters(new InputFilter[] { new InputFilter.LengthFilter(paramInt) });
    setText(null);
    invalidate();
  }
  
  public void setOnChangedListener(OnChangedListener paramOnChangedListener) { this.mOnChangedListener = paramOnChangedListener; }
  
  public void setOnClickListener(View.OnClickListener paramOnClickListener) { this.mClickListener = paramOnClickListener; }
  
  protected void updateColorForLines(boolean paramBoolean) {
    if (this.mHasError) {
      this.mLinesPaint.setColor(getColorForState(new int[] { 16842914 }));
      return;
    } 
    if (isFocused()) {
      this.mLinesPaint.setStrokeWidth(this.mLineStrokeSelected);
      this.mLinesPaint.setColor(getColorForState(new int[] { 16842908 }));
      if (paramBoolean) {
        this.mLinesPaint.setColor(getColorForState(new int[] { 16842913 }));
        return;
      } 
      return;
    } 
    this.mLinesPaint.setStrokeWidth(this.mLineStroke);
    this.mLinesPaint.setColor(getColorForState(new int[] { -16842908 }));
  }
  
  protected void updateDrawableState(boolean paramBoolean1, boolean paramBoolean2) {
    if (this.mHasError) {
      this.mPinBackground.setState(new int[] { 16842914 });
      return;
    } 
    if (isFocused()) {
      this.mPinBackground.setState(new int[] { 16842908 });
      if (paramBoolean2) {
        this.mPinBackground.setState(new int[] { 16842908, 16842913 });
        return;
      } 
      if (paramBoolean1) {
        this.mPinBackground.setState(new int[] { 16842908, 16842912 });
        return;
      } 
      return;
    } 
    this.mPinBackground.setState(new int[] { -16842908 });
  }
  
  public static interface OnChangedListener {
    void onChanged(CharSequence param1CharSequence, boolean param1Boolean);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\PinEntryEditText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */