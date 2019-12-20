package com.yy.mshow.peripherals.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.test.espresso.core.deps.guava.collect.ImmutableList;
import android.support.test.espresso.core.deps.guava.collect.UnmodifiableIterator;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.chenglei1986.navigationbar.NavigationBar;
import com.sevenheaven.iosswitch.ShSwitchView;
import com.squareup.picasso.Picasso;
import com.yy.mshow.cores.Bitrate;
import com.yy.mshow.cores.RTMPOutputSetting;
import com.yy.mshow.cores.Resolution;
import com.yy.mshow.peripherals.LivePlatforms;
import com.yy.mshow.peripherals.PopupMenu;
import com.yy.mshow.peripherals.PopupRadioButtonsDialog;
import com.yy.mshow.peripherals.RTMPPlatformResources;
import com.yy.mshow.peripherals.TitleDetailsViewHolder;
import com.yy.mshow.peripherals.YYOutputAuth;
import com.yy.mshow.peripherals.networking.YYChannel;
import com.yy.mshow.peripherals.networking.services.GetYYAccountBasics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import junit.framework.Assert;
import kits.observables.ObservableList;
import kits.observables.ObservableValue;
import kits.uikits.Dimens;

public class SettingsActivity extends Activity {
  private static final int compactRowHeight = 12;
  
  private static final int standardRowHeight = 55;
  
  private final HashMap<View, Runnable> cellActions = new HashMap();
  
  @BindView(2131624150)
  ListView listView;
  
  @BindView(2131624149)
  NavigationBar navigationBar;
  
  private ArrayList<View> resCells = new ArrayList();
  
  private ArrayList<View> rtmpCells = new ArrayList();
  
  private ImmutableList<ArrayList<View>> sections = ImmutableList.of(this.yyCells, this.rtmpCells, this.resCells);
  
  private ArrayList<View> yyCells = new ArrayList();
  
  private void addHeaderCell(ArrayList<View> paramArrayList, String paramString, int paramInt) {
    FrameLayout frameLayout = new FrameLayout(this);
    View view = getLayoutInflater().inflate(2130968609, frameLayout, false);
    view.setLayoutParams(new ViewGroup.LayoutParams(-1, Dimens.Pixels.fromDP(paramInt)));
    ((TextView)view.findViewById(2131624032)).setText(paramString);
    view.setOnTouchListener(new View.OnTouchListener() {
          public boolean onTouch(View param1View, MotionEvent param1MotionEvent) { return true; }
        });
    paramArrayList.add(view);
  }
  
  private TitleDetailsViewHolder addTitleCell(ArrayList<View> paramArrayList, String paramString1, String paramString2, Runnable paramRunnable) {
    TitleDetailsViewHolder titleDetailsViewHolder = new TitleDetailsViewHolder();
    titleDetailsViewHolder.inflate(this, 55).setTitle(paramString1).setDetails(paramString2);
    if (paramRunnable != null)
      this.cellActions.put(titleDetailsViewHolder.root, paramRunnable); 
    paramArrayList.add(titleDetailsViewHolder.root);
    return titleDetailsViewHolder;
  }
  
  private YYOutputViewHolder addYYCell() {
    YYOutputViewHolder yYOutputViewHolder;
    this.yyCells.add(yYOutputViewHolder.root);
    yYOutputViewHolder.compact.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (!((Boolean)MShow.yyOutputAuth.authorized.get()).booleanValue())
              MShow.yyOutputAuth.loginManually(SettingsActivity.this); 
          }
        });
    yYOutputViewHolder.details.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (MShow.program.yyOutputSetting.channel.get() == null)
              SettingsActivity.this.popupBindChannelDialog((List)MShow.yyOutputAuth.optionalChannels.get()); 
          }
        });
    yYOutputViewHolder.details.setOnLongClickListener(new View.OnLongClickListener() {
          public boolean onLongClick(View param1View) {
            (new PopupMenu(param1View)).add("注销", new View.OnClickListener() {
                  public void onClick(View param2View) { MShow.yyOutputAuth.logout(); }
                }).show();
            return true;
          }
        });
    yYOutputViewHolder.switchView.setOnSwitchStateChangeListener(new ShSwitchView.OnSwitchStateChangeListener() {
          public void onSwitchStateChange(boolean param1Boolean) { MShow.program.yyOutputSetting.enabled.set(Boolean.valueOf(param1Boolean)); }
        });
    return yYOutputViewHolder;
  }
  
  private void popupBindChannelDialog(List<YYChannel> paramList) {
    Assert.assertNotNull(paramList);
    Assert.assertFalse(paramList.isEmpty());
    String[] arrayOfString = new String[paramList.size()];
    for (byte b = 0; b < paramList.size(); b++)
      arrayOfString[b] = ((YYChannel)paramList.get(b)).name; 
    (new PopupRadioButtonsDialog(this, "绑定直播间频道", arrayOfString, new PopupRadioButtonsDialog.Callback(this, paramList) {
          public void onConfirm(int param1Int) {
            int i = ((Integer)MShow.program.yyOutputSetting.uid.get()).intValue();
            MShow.yyOutputAuth.bindChannel(i, (YYChannel)channels.get(param1Int));
          }
        })).show();
  }
  
  private void startEditingRTMPOutputSetting(RTMPOutputSetting paramRTMPOutputSetting) { RTMPEditingActivity.show(this, new RTMPEditingActivity.Editing(paramRTMPOutputSetting)); }
  
  private void syncYYAccountInfo(final YYOutputViewHolder viewHolder) { MShow.yyOutputAuth.account.hook(this, true, MShow.asyncMain, new ObservableValue.Observer<GetYYAccountBasics.YYAccountBasics>() {
          public void updated(boolean param1Boolean, GetYYAccountBasics.YYAccountBasics param1YYAccountBasics1, GetYYAccountBasics.YYAccountBasics param1YYAccountBasics2) {
            if (param1YYAccountBasics2 == null) {
              this.val$viewHolder.avatarView.setImageBitmap(null);
              return;
            } 
            if (param1YYAccountBasics2.avatarUrl == null) {
              this.val$viewHolder.avatarView.setImageResource(2130903065);
              return;
            } 
            Picasso.with(SettingsActivity.this).load(param1YYAccountBasics2.avatarUrl).into(this.val$viewHolder.avatarView);
          }
        }); }
  
  private void syncYYOutputChannel(final YYOutputViewHolder viewHolder) { MShow.program.yyOutputSetting.channel.hook(this, true, MShow.asyncMain, new ObservableValue.Observer<YYChannel>() {
          public void updated(boolean param1Boolean, YYChannel param1YYChannel1, YYChannel param1YYChannel2) {
            if (param1YYChannel2 == null) {
              this.val$viewHolder.channelNameView.setText("点击绑定直播间");
              this.val$viewHolder.channelNameView.setTextColor(-65536);
              this.val$viewHolder.channelNumberView.setVisibility(4);
              this.val$viewHolder.switchView.setVisibility(4);
              return;
            } 
            this.val$viewHolder.channelNameView.setText(param1YYChannel2.name);
            this.val$viewHolder.channelNameView.setTextColor(-1);
            this.val$viewHolder.channelNumberView.setText(String.valueOf(param1YYChannel2.id));
            this.val$viewHolder.channelNumberView.setVisibility(0);
            this.val$viewHolder.switchView.setVisibility(0);
          }
        }); }
  
  private void syncYYOutputEnablingState(final YYOutputViewHolder viewHolder) { MShow.program.yyOutputSetting.enabled.hook(this, true, MShow.asyncMain, new ObservableValue.Observer<Boolean>() {
          public void updated(boolean param1Boolean, Boolean param1Boolean1, Boolean param1Boolean2) { this.val$viewHolder.switchView.setOn(param1Boolean2.booleanValue()); }
        }); }
  
  private void syncYYOutputSetting(final YYOutputViewHolder viewHolder) {
    MShow.yyOutputAuth.authorized.hook(this, true, MShow.asyncMain, new ObservableValue.Observer<Boolean>() {
          public void updated(boolean param1Boolean, Boolean param1Boolean1, Boolean param1Boolean2) {
            if (param1Boolean2.booleanValue()) {
              viewHolder.asDetailsMode();
              return;
            } 
            viewHolder.asCompactMode();
          }
        });
    syncYYOutputChannel(paramYYOutputViewHolder);
    syncYYOutputEnablingState(paramYYOutputViewHolder);
    syncYYAccountInfo(paramYYOutputViewHolder);
  }
  
  private void unsyncYYAccountInfo() { MShow.yyOutputAuth.account.unhook(this); }
  
  private void unsyncYYAuth() {
    unsyncYYAccountInfo();
    unsyncYYOutputEnablingState();
    unsyncYYOutputChannel();
    MShow.yyOutputAuth.authorized.unhook(this);
  }
  
  private void unsyncYYOutputChannel() { MShow.program.yyOutputSetting.channel.unhook(this); }
  
  private void unsyncYYOutputEnablingState() { MShow.program.yyOutputSetting.enabled.unhook(this); }
  
  protected void onCreate(@Nullable Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2130968661);
    ButterKnife.bind(this);
    this.navigationBar.setTitle("设置").setDisplayBackButton(true).setBackButtonImageResource(2130903040).setOnBackButtonClickListener(new View.OnClickListener() {
          public void onClick(View param1View) { SettingsActivity.this.finish(); }
        });
    addHeaderCell(this.yyCells, "输出平台设置", 55);
    YYOutputViewHolder yYOutputViewHolder = addYYCell();
    MShow.yyOutputAuth.delegate = new YYOutputAuth.Delegate() {
        public void selectChannel(final List<YYChannel> channels) { SettingsActivity.this.runOnUiThread(new Runnable() {
                public void run() { SettingsActivity.null.this.this$0.popupBindChannelDialog(channels); }
              }); }
        
        public void showToast(final String content) { SettingsActivity.this.runOnUiThread(new Runnable() {
                public void run() { Toast.makeText(SettingsActivity.null.this.this$0, content, 1).show(); }
              }); }
      };
    MShow.yyOutputAuth.loginAutomatically();
    syncYYOutputSetting(yYOutputViewHolder);
    addHeaderCell(this.rtmpCells, "", 12);
    final TitleDetailsViewHolder rateViewHolder = addTitleCell(this.rtmpCells, "其它平台", "添加", new Runnable() {
          public void run() {
            if (3 - MShow.program.rtmpOutputSettings.get().size() > 0) {
              RTMPEditingActivity.show(SettingsActivity.this, new RTMPEditingActivity.Adding());
              return;
            } 
            Toast.makeText(SettingsActivity.this, "请先移除现有平台后再进行添加", 1).show();
          }
        });
    MShow.program.rtmpOutputSettings.register(this, Boolean.valueOf(true), new ObservableList.Observer<RTMPOutputSetting>() {
          public void updated(boolean param1Boolean, List<RTMPOutputSetting> param1List) {
            int i = 3 - param1List.size();
            if (i > 0) {
              holder.setDetails("还可以添加" + i + "个平台同步直播");
              return;
            } 
            holder.setDetails("已达上限，最多3个平台");
          }
        });
    addHeaderCell(this.resCells, "清晰度", 55);
    titleDetailsViewHolder = addTitleCell(this.resCells, "分辨率", "", new Runnable() {
          public void run() { SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, ResolutionActivity.class)); }
        });
    MShow.program.resolution.hook(this, true, new ObservableValue.Observer<Resolution>() {
          public void updated(boolean param1Boolean, Resolution param1Resolution1, Resolution param1Resolution2) { resViewHolder.setDetails(Resolution.makeString(param1Resolution2)); }
        });
    titleDetailsViewHolder = addTitleCell(this.resCells, "码率", "", new Runnable() {
          public void run() { SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, BitrateActivity.class)); }
        });
    MShow.program.bitrate.hook(this, true, new ObservableValue.Observer<Bitrate>() {
          public void updated(boolean param1Boolean, Bitrate param1Bitrate1, Bitrate param1Bitrate2) { rateViewHolder.setDetails(Bitrate.makeString(param1Bitrate2)); }
        });
    final BaseAdapter adapter = new BaseAdapter() {
        public int getCount() {
          int i = 0;
          UnmodifiableIterator unmodifiableIterator = SettingsActivity.this.sections.iterator();
          while (unmodifiableIterator.hasNext())
            i += ((ArrayList)unmodifiableIterator.next()).size(); 
          return i;
        }
        
        public Object getItem(int param1Int) { return null; }
        
        public long getItemId(int param1Int) { return param1Int; }
        
        public View getView(int param1Int, View param1View, ViewGroup param1ViewGroup) {
          UnmodifiableIterator unmodifiableIterator = SettingsActivity.this.sections.iterator();
          while (unmodifiableIterator.hasNext()) {
            ArrayList arrayList = (ArrayList)unmodifiableIterator.next();
            if (param1Int < arrayList.size())
              return (View)arrayList.get(param1Int); 
            param1Int -= arrayList.size();
          } 
          return null;
        }
      };
    this.listView.setAdapter(baseAdapter);
    this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
            Runnable runnable = (Runnable)SettingsActivity.this.cellActions.get(param1View);
            if (runnable != null)
              runnable.run(); 
          }
        });
    final HashMap setting2cell = new HashMap();
    MShow.program.rtmpOutputSettings.register(this, Boolean.valueOf(true), new ObservableList.Observer<RTMPOutputSetting>() {
          void addCell(final RTMPOutputSetting rtmp, boolean param1Boolean) {
            View view = createCell(param1RTMPOutputSetting);
            SettingsActivity.this.rtmpCells.add(view);
            setting2cell.put(param1RTMPOutputSetting, view);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                  public boolean onLongClick(View param2View) {
                    (new PopupMenu(param2View)).add("编辑", new View.OnClickListener() {
                          public void onClick(View param3View) { SettingsActivity.null.this.this$0.startEditingRTMPOutputSetting(rtmp); }
                        }).add("删除", new View.OnClickListener() {
                          public void onClick(View param3View) { MShow.program.rtmpOutputSettings.remove(rtmp); }
                        }).show();
                    return true;
                  }
                });
            view.setOnClickListener(new View.OnClickListener() {
                  public void onClick(View param2View) { SettingsActivity.null.this.this$0.startEditingRTMPOutputSetting(rtmp); }
                });
            if (param1Boolean)
              adapter.notifyDataSetChanged(); 
          }
          
          View createCell(final RTMPOutputSetting rtmp) {
            FrameLayout frameLayout = new FrameLayout(SettingsActivity.this);
            View view = SettingsActivity.this.getLayoutInflater().inflate(2130968610, frameLayout, false);
            view.setLayoutParams(new ViewGroup.LayoutParams(-1, Dimens.Pixels.fromDP(55)));
            final TextView textView = (TextView)view.findViewById(2131624032);
            final ShSwitchView switchView = (ShSwitchView)view.findViewById(2131624093);
            final ImageView imageView = (ImageView)view.findViewById(2131624031);
            param1RTMPOutputSetting.url.hook(view, true, new ObservableValue.Observer<String>() {
                  public void updated(boolean param2Boolean, String param2String1, String param2String2) {
                    textView.setText((CharSequence)this.val$rtmp.url.get());
                    LivePlatforms.Platform platform = LivePlatforms.fromRTMPString((String)this.val$rtmp.url.get());
                    imageView.setImageResource(RTMPPlatformResources.get(platform));
                  }
                });
            param1RTMPOutputSetting.enabled.hook(view, true, new ObservableValue.Observer<Boolean>() {
                  public void updated(boolean param2Boolean, Boolean param2Boolean1, Boolean param2Boolean2) { switchView.setOn(param2Boolean2.booleanValue(), false); }
                });
            shSwitchView.setOnSwitchStateChangeListener(new ShSwitchView.OnSwitchStateChangeListener() {
                  public void onSwitchStateChange(boolean param2Boolean) { this.val$rtmp.enabled.set(Boolean.valueOf(param2Boolean)); }
                });
            return view;
          }
          
          public void didAdd(RTMPOutputSetting param1RTMPOutputSetting) { addCell(param1RTMPOutputSetting, true); }
          
          void releaseCell(View param1View, RTMPOutputSetting param1RTMPOutputSetting) {
            param1RTMPOutputSetting.enabled.unhook(param1View);
            param1RTMPOutputSetting.url.unhook(param1View);
          }
          
          void removeCell(RTMPOutputSetting param1RTMPOutputSetting) {
            View view = (View)setting2cell.get(param1RTMPOutputSetting);
            releaseCell(view, param1RTMPOutputSetting);
            SettingsActivity.this.rtmpCells.remove(view);
            setting2cell.remove(param1RTMPOutputSetting);
            SettingsActivity.this.cellActions.remove(view);
            adapter.notifyDataSetChanged();
          }
          
          public void updated(boolean param1Boolean, List<RTMPOutputSetting> param1List) {
            if (param1Boolean) {
              Iterator iterator = param1List.iterator();
              while (iterator.hasNext())
                addCell((RTMPOutputSetting)iterator.next(), false); 
              adapter.notifyDataSetChanged();
            } 
          }
          
          public void willRemove(RTMPOutputSetting param1RTMPOutputSetting) { removeCell(param1RTMPOutputSetting); }
        });
  }
  
  protected void onDestroy() {
    super.onDestroy();
    unsyncYYAuth();
    MShow.program.rtmpOutputSettings.unregister(this);
    MShow.program.resolution.unhook(this);
  }
  
  private class YYOutputViewHolder {
    ImageView avatarView;
    
    TextView channelNameView;
    
    TextView channelNumberView;
    
    View compact;
    
    View details;
    
    private FrameLayout root = new FrameLayout(SettingsActivity.this);
    
    ShSwitchView switchView;
    
    YYOutputViewHolder() {
      this.root.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
      load();
    }
    
    private void load() {
      loadCompact();
      loadDetails();
    }
    
    private void loadCompact() {
      this.compact = SettingsActivity.this.getLayoutInflater().inflate(2130968611, this.root, false);
      this.compact.setLayoutParams(new ViewGroup.LayoutParams(-1, Dimens.Pixels.fromDP(55)));
      this.root.addView(this.compact);
      TextView textView1 = (TextView)this.compact.findViewById(2131624032);
      TextView textView2 = (TextView)this.compact.findViewById(2131624095);
      textView1.setText(2131230924);
      textView2.setText(2131230773);
    }
    
    private void loadDetails() {
      this.details = SettingsActivity.this.getLayoutInflater().inflate(2130968612, this.root, false);
      this.details.setLayoutParams(new ViewGroup.LayoutParams(-1, Dimens.Pixels.fromDP(110)));
      this.root.addView(this.details);
      this.avatarView = (ImageView)this.root.findViewById(2131624096);
      this.channelNameView = (TextView)this.root.findViewById(2131624097);
      this.channelNameView.setMaxLines(1);
      this.channelNameView.setEllipsize(TextUtils.TruncateAt.END);
      this.channelNumberView = (TextView)this.root.findViewById(2131624099);
      this.switchView = (ShSwitchView)this.root.findViewById(2131624093);
    }
    
    void asCompactMode() {
      this.compact.setVisibility(0);
      this.details.setVisibility(8);
    }
    
    void asDetailsMode() {
      this.compact.setVisibility(8);
      this.details.setVisibility(0);
    }
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\activity\SettingsActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */