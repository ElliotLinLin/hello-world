package com.yy.mshow.peripherals.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cunoraz.gifview.library.GifView;

public class PublisherActivity_ViewBinding implements Unbinder {
  private PublisherActivity target;
  
  @UiThread
  public PublisherActivity_ViewBinding(PublisherActivity paramPublisherActivity) { this(paramPublisherActivity, paramPublisherActivity.getWindow().getDecorView()); }
  
  @UiThread
  public PublisherActivity_ViewBinding(PublisherActivity paramPublisherActivity, View paramView) {
    this.target = paramPublisherActivity;
    paramPublisherActivity.timerTextView = (TextView)Utils.findRequiredViewAsType(paramView, 2131624180, "field 'timerTextView'", TextView.class);
    paramPublisherActivity.previewContainer = (FrameLayout)Utils.findRequiredViewAsType(paramView, 2131624179, "field 'previewContainer'", FrameLayout.class);
    paramPublisherActivity.stateOnPreview = (ImageView)Utils.findRequiredViewAsType(paramView, 2131624181, "field 'stateOnPreview'", ImageView.class);
    paramPublisherActivity.stateTextView = (TextView)Utils.findRequiredViewAsType(paramView, 2131624183, "field 'stateTextView'", TextView.class);
    paramPublisherActivity.liveBulbView = (GifView)Utils.findRequiredViewAsType(paramView, 2131624182, "field 'liveBulbView'", GifView.class);
    paramPublisherActivity.cameraToggleButton = (ImageButton)Utils.findRequiredViewAsType(paramView, 2131624185, "field 'cameraToggleButton'", ImageButton.class);
    paramPublisherActivity.effectorButton = (ImageButton)Utils.findRequiredViewAsType(paramView, 2131624186, "field 'effectorButton'", ImageButton.class);
  }
  
  @CallSuper
  public void unbind() {
    PublisherActivity publisherActivity = this.target;
    if (publisherActivity == null)
      throw new IllegalStateException("Bindings already cleared."); 
    this.target = null;
    publisherActivity.timerTextView = null;
    publisherActivity.previewContainer = null;
    publisherActivity.stateOnPreview = null;
    publisherActivity.stateTextView = null;
    publisherActivity.liveBulbView = null;
    publisherActivity.cameraToggleButton = null;
    publisherActivity.effectorButton = null;
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\activity\PublisherActivity_ViewBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */