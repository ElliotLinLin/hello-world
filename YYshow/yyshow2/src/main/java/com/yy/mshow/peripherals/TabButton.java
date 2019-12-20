package com.yy.mshow.peripherals;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.yy.mshow.R;

public class TabButton extends ConstraintLayout {
  private AttributesParser attributesParser;
  
  private ImageView iconView;
  
  private TextView titleView;
  
  public TabButton(Context paramContext) { this(paramContext, null); }
  
  public TabButton(Context paramContext, AttributeSet paramAttributeSet) { this(paramContext, paramAttributeSet, 0); }
  
  public TabButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet, paramInt);
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    populateAttributes(paramContext, paramAttributeSet, paramInt);
    prepareLayout();
  }
  
  private void populateAttributes(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    typedArray = paramContext.getTheme().obtainStyledAttributes(paramAttributeSet, R.styleable.TabButton, paramInt, 0);
    try {
      this.attributesParser = new AttributesParser(typedArray);
      return;
    } finally {
      typedArray.recycle();
    } 
  }
  
  private void prepareLayout() {
    inflate(getContext(), 2130968606, this);
    setLayoutParams(new ConstraintLayout.LayoutParams(0, 0));
    this.iconView = (ImageView)findViewById(2131624088);
    this.iconView.setImageDrawable(this.attributesParser.getIcon());
    this.titleView = (TextView)findViewById(2131624089);
    this.titleView.setText(this.attributesParser.getTitle());
    this.titleView.setTextColor(this.attributesParser.getColorStates());
  }
  
  public void setOnClickListener(@Nullable final View.OnClickListener l) { super.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (TabButton.this.isSelected())
              return; 
            if (l != null)
              l.onClick(param1View); 
            TabButton.this.setSelected(true);
          }
        }); }
  
  public void setSelected(boolean paramBoolean) {
    super.setSelected(paramBoolean);
    this.iconView.setSelected(paramBoolean);
    this.titleView.setSelected(paramBoolean);
  }
  
  private class AttributesParser {
    private final ColorStateList colorStates;
    
    private final Drawable icon;
    
    private final String title;
    
    AttributesParser(TypedArray param1TypedArray) {
      if (param1TypedArray.hasValue(1)) {
        this.icon = param1TypedArray.getDrawable(1);
        if (param1TypedArray.hasValue(0)) {
          this.title = param1TypedArray.getString(0);
          colorStateList = param1TypedArray.getColorStateList(2);
          if (colorStateList == null) {
            this.colorStates = new ColorStateList(new int[][] { { 16842913 }, , { -16842913 },  }, new int[] { -16777216, -1 });
            return;
          } 
        } else {
          throw new RuntimeException("title attribute is required!");
        } 
      } else {
        throw new RuntimeException("icon attribute is required!");
      } 
      this.colorStates = colorStateList;
    }
    
    private ColorStateList getColorStates() { return this.colorStates; }
    
    private Drawable getIcon() { return this.icon; }
    
    private String getTitle() { return this.title; }
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\TabButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */