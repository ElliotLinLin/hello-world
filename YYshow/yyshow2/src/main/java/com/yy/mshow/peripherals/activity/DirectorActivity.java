package com.yy.mshow.peripherals.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.yy.mshow.cores.MonitorPanel;
import com.yy.mshow.cores.Size;
import com.yy.mshow.cores.Slot;
import com.yy.mshow.cores.Stream;
import com.yy.mshow.peripherals.DirectorFlows;
import com.yy.mshow.peripherals.DurationString;
import com.yy.mshow.peripherals.PopupBottomSheet;
import com.yy.mshow.peripherals.PopupDialog;
import com.yy.mshow.peripherals.PopupMenu;
import com.yy.mshow.peripherals.PopupQRCodeDialog;
import com.yy.mshow.peripherals.PopupRadioButtonsDialog;
import com.yy.mshow.peripherals.PopupShowingURLDialog;
import com.yy.mshow.peripherals.PopupURLEditingDialog;
import com.yy.mshow.peripherals.networking.services.HeartBeat;
import com.yy.mshow.peripherals.playercenter.Player;
import com.yy.mshow.peripherals.playercenter.PlayerCenter;
import com.yy.mshow.peripherals.playercenter.YCRTMPPlayer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Stack;
import junit.framework.Assert;
import kits.Statechart;
import kits.observables.ObservableValue;
import kits.threading.RunInMain;
import kits.uikits.Dimens;
import kits.uikits.DragAndDropGroup;

public final class DirectorActivity extends Activity {
  private static final Class[] panelClasses = { com.yy.mshow.cores.MonitorPanelSole.class, com.yy.mshow.cores.MonitorPanelPIP.class, com.yy.mshow.cores.MonitorPanelHalf.class, com.yy.mshow.cores.MonitorPanelC.class, com.yy.mshow.cores.MonitorPanelGrids.class };
  
  @BindView(2131624123)
  ImageButton applyEditingButton;
  
  @BindView(2131624122)
  ImageButton cancelEditingButton;
  
  @BindView(2131624129)
  ConstraintLayout channelsContainerView;
  
  private DragAndDropGroup<Slot> dnd;
  
  private Stack<Runnable> guards = new Stack();
  
  private ObservableValue<Boolean> isEditing = new ObservableValue(Boolean.valueOf(false));
  
  @BindView(2131624138)
  TabLayout layoutOptionMenu;
  
  @BindView(2131624135)
  ImageButton layoutTriggerButton;
  
  @BindView(2131624130)
  ImageButton liveButton;
  
  @BindView(2131624127)
  ImageButton liveCloseButton;
  
  @BindView(2131624124)
  FrameLayout pgmLayout;
  
  private MonitorPanelView pgmPanelView;
  
  @BindView(2131624126)
  FrameLayout pvwLayout;
  
  private MonitorPanelView pvwPanelView;
  
  @BindView(2131624136)
  ImageButton settingsButton;
  
  private ArrayList<SlotCell> slotCells = new ArrayList();
  
  @BindView(2131624137)
  TextView spanTextView;
  
  @BindView(2131624074)
  RelativeLayout topBar;
  
  private void recoverFromCrashesIfNeeded() { MShow.http.launch(new HeartBeat(MShow.program, new HeartBeat.Completion(this) {
            public void onSuccess(Boolean param1Boolean) {
              MShow.program.restore(param1Boolean);
              if (param1Boolean.booleanValue())
                MShow.directorFlows.schedule(DirectorFlows.Signal.OpResumeFromCrashes); 
            }
          })); }
  
  private void setupEditingApplyButton() { this.applyEditingButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            MShow.program.applyPVWtoPGM();
            DirectorActivity.this.isEditing.set(Boolean.valueOf(false));
          }
        }); }
  
  private void setupEditingCancelButton() { this.cancelEditingButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) { DirectorActivity.this.isEditing.set(Boolean.valueOf(false)); }
        }); }
  
  private void setupLiveButton() {
    MShow.directorFlows.register(this, DirectorFlows.State.Initial, true, new Statechart.StateListener<DirectorFlows.State>() {
          public void didEnter(DirectorFlows.State param1State) { RunInMain.dispatch(new Runnable() {
                  public void run() { DirectorActivity.this.liveButton.setEnabled(true); }
                }); }
          
          public void willLeave(DirectorFlows.State param1State) { RunInMain.dispatch(new Runnable() {
                  public void run() { DirectorActivity.this.liveButton.setEnabled(false); }
                }); }
        });
    this.isEditing.hook(this, true, MShow.asyncMain, new ObservableValue.Observer<Boolean>() {
          public void updated(boolean param1Boolean, Boolean param1Boolean1, Boolean param1Boolean2) {
            byte b1;
            byte b2 = 0;
            if (!((Boolean)MShow.program.isOnLive.get()).booleanValue() && !((Boolean)DirectorActivity.this.isEditing.get()).booleanValue()) {
              b1 = 1;
            } else {
              b1 = 0;
            } 
            ImageButton imageButton = DirectorActivity.this.liveButton;
            if (b1) {
              b1 = b2;
            } else {
              b1 = 4;
            } 
            imageButton.setVisibility(b1);
          }
        });
    MShow.program.isOnLive.hook(this, true, MShow.asyncMain, new ObservableValue.Observer<Boolean>() {
          public void updated(boolean param1Boolean, Boolean param1Boolean1, Boolean param1Boolean2) {
            byte b1;
            byte b2 = 0;
            if (!((Boolean)MShow.program.isOnLive.get()).booleanValue() && !((Boolean)DirectorActivity.this.isEditing.get()).booleanValue()) {
              b1 = 1;
            } else {
              b1 = 0;
            } 
            ImageButton imageButton = DirectorActivity.this.liveButton;
            if (b1) {
              b1 = b2;
            } else {
              b1 = 4;
            } 
            imageButton.setVisibility(b1);
          }
        });
    this.liveButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            Assert.assertFalse("开播时，该按钮应该处于隐藏状态，无法被触选", ((Boolean)MShow.program.isOnLive.get()).booleanValue());
            MShow.directorFlows.schedule(DirectorFlows.Signal.OpStart);
          }
        });
    this.guards.push(new Runnable() {
          public void run() {
            DirectorActivity.this.isEditing.unhook(DirectorActivity.this);
            MShow.program.isOnLive.unhook(DirectorActivity.this);
            MShow.directorFlows.unregister(DirectorActivity.this);
          }
        });
  }
  
  private void setupLiveCloseButton() {
    this.liveCloseButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) { DirectorActivity.this.stopLivingWithConfirmation(null); }
        });
    ObservableValue.Observer<Boolean> observer = new ObservableValue.Observer<Boolean>() {
        public void updated(boolean param1Boolean, Boolean param1Boolean1, Boolean param1Boolean2) {
          byte b1;
          byte b2 = 0;
          if (((Boolean)MShow.program.isOnLive.get()).booleanValue() && !((Boolean)DirectorActivity.this.isEditing.get()).booleanValue()) {
            b1 = 1;
          } else {
            b1 = 0;
          } 
          ImageButton imageButton = DirectorActivity.this.liveCloseButton;
          if (b1) {
            b1 = b2;
          } else {
            b1 = 4;
          } 
          imageButton.setVisibility(b1);
        }
      };
    MShow.program.isOnLive.hook(this, true, MShow.asyncMain, observer);
    this.isEditing.hook(this, true, MShow.asyncMain, observer);
    this.guards.push(new Runnable() {
          public void run() {
            DirectorActivity.this.isEditing.unhook(DirectorActivity.this);
            MShow.program.isOnLive.unhook(DirectorActivity.this);
          }
        });
  }
  
  private void setupPGMPanelView() {
    this.pgmPanelView = new MonitorPanelView(this, 0);
    this.pgmLayout.addView(this.pgmPanelView);
    this.pgmPanelView.post(new Runnable() {
          public void run() { MShow.program.pgm.hook(DirectorActivity.this, true, new ObservableValue.Observer<MonitorPanel>() {
                  public void updated(boolean param2Boolean, MonitorPanel param2MonitorPanel1, MonitorPanel param2MonitorPanel2) { DirectorActivity.null.this.this$0.pgmPanelView.sync(param2MonitorPanel2); }
                  
                  public void willChange(MonitorPanel param2MonitorPanel1, MonitorPanel param2MonitorPanel2) { DirectorActivity.null.this.this$0.pgmPanelView.unSync(); }
                }); }
        });
    this.guards.push(new Runnable() {
          public void run() {
            DirectorActivity.this.pgmPanelView.unSync();
            MShow.program.pgm.unhook(DirectorActivity.this);
          }
        });
  }
  
  private void setupPVWLayoutTriggers() {
    this.isEditing.hook(this, true, MShow.asyncMain, new ObservableValue.Observer<Boolean>() {
          public void updated(boolean param1Boolean, Boolean param1Boolean1, Boolean param1Boolean2) {
            byte b1;
            byte b2 = 0;
            ImageButton imageButton = DirectorActivity.this.layoutTriggerButton;
            if (param1Boolean2.booleanValue()) {
              b1 = 4;
            } else {
              b1 = 0;
            } 
            imageButton.setVisibility(b1);
            TabLayout tabLayout = DirectorActivity.this.layoutOptionMenu;
            if (param1Boolean2.booleanValue()) {
              b1 = b2;
            } else {
              b1 = 4;
            } 
            tabLayout.setVisibility(b1);
          }
        });
    this.layoutTriggerButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            boolean bool;
            int i = Arrays.asList(panelClasses).indexOf(((MonitorPanel)MShow.program.pvw.get()).getClass());
            if (i != -1) {
              bool = true;
            } else {
              bool = false;
            } 
            Assert.assertTrue(bool);
            TabLayout.Tab tab = DirectorActivity.this.layoutOptionMenu.getTabAt(i);
            Assert.assertNotNull(tab);
            tab.select();
            DirectorActivity.this.isEditing.set(Boolean.valueOf(true));
          }
        });
    this.layoutOptionMenu.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
          public void onTabReselected(TabLayout.Tab param1Tab) {}
          
          public void onTabSelected(TabLayout.Tab param1Tab) {
            try {
              MonitorPanel monitorPanel = (MonitorPanel)panelClasses[param1Tab.getPosition()].newInstance();
              MonitorPanel.Portion[] arrayOfPortion = monitorPanel.getPortions();
              for (byte b = 0; b < arrayOfPortion.length; b++)
                (arrayOfPortion[b]).linkedSlot.set(MShow.program.slots[b]); 
              MShow.program.pvw.set(monitorPanel);
              return;
            } catch (IllegalAccessException param1Tab) {
            
            } catch (InstantiationException param1Tab) {}
            param1Tab.printStackTrace();
          }
          
          public void onTabUnselected(TabLayout.Tab param1Tab) {}
        });
    this.guards.push(new Runnable() {
          public void run() { DirectorActivity.this.isEditing.unhook(DirectorActivity.this); }
        });
  }
  
  private void setupPVWPanelView() {
    this.pvwPanelView = new MonitorPanelView(this, ContextCompat.getColor(this, 2131558446));
    this.pvwLayout.addView(this.pvwPanelView);
    this.pvwPanelView.post(new Runnable() {
          public void run() { DirectorActivity.this.isEditing.hook(DirectorActivity.this, true, MShow.asyncMain, new ObservableValue.Observer<Boolean>() {
                  public void updated(boolean param2Boolean, Boolean param2Boolean1, Boolean param2Boolean2) {
                    if (param2Boolean2.booleanValue()) {
                      DirectorActivity.this.pvwLayout.setVisibility(0);
                      MShow.program.pvw.hook(this, true, new Observer<MonitorPanel>() {
                            public void updated(boolean param3Boolean, MonitorPanel param3MonitorPanel1, MonitorPanel param3MonitorPanel2) { DirectorActivity.null.this.this$0.pvwPanelView.sync(param3MonitorPanel2); }
                            
                            public void willChange(MonitorPanel param3MonitorPanel1, MonitorPanel param3MonitorPanel2) { DirectorActivity.null.this.this$0.pvwPanelView.unSync(); }
                          });
                    } 
                  }
                  
                  public void willChange(Boolean param2Boolean1, Boolean param2Boolean2) {
                    if (param2Boolean1.booleanValue() && !param2Boolean2.booleanValue()) {
                      MShow.program.pvw.unhook(this);
                      DirectorActivity.null.this.this$0.pvwPanelView.unSync();
                      DirectorActivity.this.pvwLayout.setVisibility(4);
                    } 
                  }
                }); }
        });
    this.guards.push(new Runnable() {
          public void run() {
            Assert.assertFalse(((Boolean)DirectorActivity.this.isEditing.get()).booleanValue());
            DirectorActivity.this.isEditing.unhook(DirectorActivity.this);
          }
        });
  }
  
  private void setupSettingButton() {
    this.settingsButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) { DirectorActivity.this.startActivity(new Intent(DirectorActivity.this, SettingsActivity.class)); }
        });
    ObservableValue.Observer<Boolean> observer = new ObservableValue.Observer<Boolean>() {
        public void updated(boolean param1Boolean, Boolean param1Boolean1, Boolean param1Boolean2) {
          byte b1;
          byte b2 = 0;
          if (!((Boolean)MShow.program.isOnLive.get()).booleanValue() && !((Boolean)DirectorActivity.this.isEditing.get()).booleanValue()) {
            b1 = 1;
          } else {
            b1 = 0;
          } 
          ImageButton imageButton = DirectorActivity.this.settingsButton;
          if (b1) {
            b1 = b2;
          } else {
            b1 = 4;
          } 
          imageButton.setVisibility(b1);
        }
      };
    MShow.program.isOnLive.hook(this, true, MShow.asyncMain, observer);
    this.isEditing.hook(this, true, MShow.asyncMain, observer);
    this.guards.push(new Runnable() {
          public void run() {
            DirectorActivity.this.isEditing.unhook(DirectorActivity.this);
            MShow.program.isOnLive.unhook(DirectorActivity.this);
          }
        });
  }
  
  private void setupSlotCells() {
    for (Slot slot : MShow.program.slots) {
      SlotCell slotCell = new SlotCell(slot, (FrameLayout)this.channelsContainerView.getChildAt(slot.index - 1));
      slotCell.sync();
      this.slotCells.add(slotCell);
    } 
    this.guards.push(new Runnable() {
          public void run() {
            Iterator iterator = DirectorActivity.this.slotCells.iterator();
            while (iterator.hasNext())
              ((DirectorActivity.SlotCell)iterator.next()).unSync(); 
          }
        });
  }
  
  private void setupSpanText() {
    MShow.program.isOnLive.hook(this, true, MShow.asyncMain, new ObservableValue.Observer<Boolean>() {
          public void updated(boolean param1Boolean, Boolean param1Boolean1, Boolean param1Boolean2) {
            byte b;
            TextView textView = DirectorActivity.this.spanTextView;
            if (param1Boolean2.booleanValue()) {
              b = 0;
            } else {
              b = 4;
            } 
            textView.setVisibility(b);
          }
        });
    MShow.program.liveSpan.hook(this, true, MShow.asyncMain, new ObservableValue.Observer<Integer>() {
          public void updated(boolean param1Boolean, Integer param1Integer1, Integer param1Integer2) { DirectorActivity.this.spanTextView.setText(DurationString.make(param1Integer2.intValue())); }
        });
    this.guards.push(new Runnable() {
          public void run() {
            MShow.program.liveSpan.unhook(DirectorActivity.this);
            MShow.program.isOnLive.unhook(DirectorActivity.this);
          }
        });
  }
  
  private void setupToaster() {
    MShow.directorFlows.delegate = new DirectorFlows.Delegate() {
        public void didOccurFailure(final String reason) { RunInMain.dispatch(new Runnable() {
                public void run() { Toast.makeText(DirectorActivity.null.this.this$0, reason, 0).show(); }
              }); }
      };
    MShow.playerCenter.delegate = new PlayerCenter.Delegate() {
        public void didOccurFailure(final String reason) { RunInMain.dispatch(new Runnable() {
                public void run() { Toast.makeText(DirectorActivity.null.this.this$0, reason, 1).show(); }
              }); }
      };
    this.guards.push(new Runnable() {
          public void run() {
            MShow.playerCenter.delegate = null;
            MShow.directorFlows.delegate = null;
          }
        });
  }
  
  private void setupTopBarForEditing() {
    this.isEditing.hook(this, true, MShow.asyncMain, new ObservableValue.Observer<Boolean>() {
          public void updated(boolean param1Boolean, Boolean param1Boolean1, Boolean param1Boolean2) {
            byte b;
            RelativeLayout relativeLayout = DirectorActivity.this.topBar;
            if (param1Boolean2.booleanValue()) {
              b = 0;
            } else {
              b = 8;
            } 
            relativeLayout.setVisibility(b);
          }
        });
    this.guards.push(new Runnable() {
          public void run() { DirectorActivity.this.isEditing.unhook(DirectorActivity.this); }
        });
  }
  
  private void stopLivingWithConfirmation(final Runnable runnable) { (new PopupDialog(this, true)).setTitle("该操作将终止直播，是否继续？").addButton("取消", null).addButton("终止", new View.OnClickListener() {
          public void onClick(View param1View) {
            MShow.directorFlows.schedule(DirectorFlows.Signal.OpStop);
            if (runnable != null)
              runnable.run(); 
          }
        }).show(); }
  
  public void onBackPressed() {
    if (this.dnd.isDragging())
      return; 
    if (((Boolean)this.isEditing.get()).booleanValue()) {
      this.isEditing.set(Boolean.valueOf(false));
      return;
    } 
    if (((Boolean)MShow.program.isOnLive.get()).booleanValue()) {
      stopLivingWithConfirmation(new Runnable() {
            public void run() { RunInMain.dispatch(new Runnable() {
                    public void run() { DirectorActivity.null.this.this$0.finish(); }
                  }); }
          });
      return;
    } 
    finish();
  }
  
  protected void onCreate(@Nullable Bundle paramBundle) {
    super.onCreate(paramBundle);
    if (MShow.program.code.get() == null) {
      finish();
      return;
    } 
    FrameLayout frameLayout = new FrameLayout(this);
    ViewGroup viewGroup = (ViewGroup)getLayoutInflater().inflate(2130968631, frameLayout, false);
    setContentView(viewGroup);
    ButterKnife.bind(this);
    this.dnd = new DragAndDropGroup(viewGroup, BitmapFactory.decodeResource(getResources(), 2130903065));
    Log.i("Director", "[DirectorActivity] onCreate: \n" + MShow.program);
    setupSettingButton();
    setupLiveCloseButton();
    setupLiveButton();
    setupEditingCancelButton();
    setupEditingApplyButton();
    setupPVWLayoutTriggers();
    setupTopBarForEditing();
    setupPGMPanelView();
    setupPVWPanelView();
    setupSlotCells();
    setupSpanText();
    setupToaster();
    MShow.playerCenter.open(this);
    this.guards.push(new Runnable() {
          public void run() { MShow.playerCenter.close(); }
        });
    recoverFromCrashesIfNeeded();
  }
  
  protected void onDestroy() {
    super.onDestroy();
    while (!this.guards.empty())
      ((Runnable)this.guards.pop()).run(); 
    Log.i("Director", "[DirectorActivity] onDestroy: \n" + MShow.program);
  }
  
  public class MonitorPanelView extends AbsoluteLayout {
    private int borderColor;
    
    private MonitorPanel panel;
    
    private HashMap<MonitorPanel.Portion, DirectorActivity.MonitorPortionView> portionViews = new HashMap();
    
    public MonitorPanelView(Context param1Context, int param1Int) {
      super(param1Context);
      this.borderColor = param1Int;
    }
    
    void sync(MonitorPanel param1MonitorPanel) {
      boolean bool = false;
      MonitorPanel monitorPanel = this.panel;
      if (monitorPanel != null) {
        for (MonitorPanel.Portion portion : monitorPanel.getPortions()) {
          DirectorActivity.MonitorPortionView monitorPortionView = (DirectorActivity.MonitorPortionView)this.portionViews.get(portion);
          DirectorActivity.this.dnd.removeAcceptor(monitorPortionView);
          monitorPortionView.unSync();
          removeView(monitorPortionView);
        } 
        this.portionViews.clear();
      } 
      this.panel = param1MonitorPanel;
      if (param1MonitorPanel != null) {
        MonitorPanel.Portion[] arrayOfPortion = param1MonitorPanel.getPortions();
        int i = arrayOfPortion.length;
        for (byte b = bool; b < i; b++) {
          final MonitorPanel.Portion portion = arrayOfPortion[b];
          Rect rect = param1MonitorPanel.evalRect(new Size(getWidth(), getHeight()), portion);
          DirectorActivity.MonitorPortionView monitorPortionView = new DirectorActivity.MonitorPortionView(DirectorActivity.this, DirectorActivity.this, this.borderColor);
          addView(monitorPortionView);
          monitorPortionView.setLayoutParams(new AbsoluteLayout.LayoutParams(rect.width(), rect.height(), rect.left, rect.top));
          monitorPortionView.sync(portion);
          DirectorActivity.this.dnd.addAcceptor(monitorPortionView, new DragAndDropGroup.AcceptingCallback<Slot>() {
                public void didAccept(Slot param2Slot) {
                  Log.i("DnD/Director", String.format(Locale.getDefault(), "From slot [%d] -> portion (%s)", new Object[] { Integer.valueOf(param2Slot.index), this.val$portion.name }));
                  this.val$portion.linkedSlot.set(param2Slot);
                }
                
                public boolean shouldAccept(Slot param2Slot) { return true; }
              });
          this.portionViews.put(portion, monitorPortionView);
        } 
      } 
    }
    
    void unSync() { sync(null); }
  }
  
  class null extends Object implements DragAndDropGroup.AcceptingCallback<Slot> {
    public void didAccept(Slot param1Slot) {
      Log.i("DnD/Director", String.format(Locale.getDefault(), "From slot [%d] -> portion (%s)", new Object[] { Integer.valueOf(param1Slot.index), this.val$portion.name }));
      this.val$portion.linkedSlot.set(param1Slot);
    }
    
    public boolean shouldAccept(Slot param1Slot) { return true; }
  }
  
  class MonitorPortionView extends FrameLayout {
    private final View borderView;
    
    private MonitorPanel.Portion portion;
    
    private Slot slot;
    
    private Stream stream;
    
    private View streamView;
    
    private final FrameLayout videoView;
    
    MonitorPortionView(Context param1Context, int param1Int) {
      super(param1Context);
      setBackgroundResource(2131558483);
      this.videoView = new FrameLayout(param1Context);
      this.videoView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
      addView(this.videoView);
      this.borderView = new View(param1Context);
      this.borderView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
      this.borderView.setBackground(borderDrawable(param1Int));
      addView(this.borderView);
    }
    
    private Drawable borderDrawable(int param1Int) {
      GradientDrawable gradientDrawable = new GradientDrawable();
      gradientDrawable.setShape(0);
      gradientDrawable.setColor(0);
      gradientDrawable.setStroke(Dimens.Pixels.fromDP(1), param1Int);
      return gradientDrawable;
    }
    
    private void link(Slot param1Slot) {
      Assert.assertNull(this.slot);
      if (param1Slot == null)
        return; 
      param1Slot.stream.hook(this, true, new ObservableValue.Observer<Stream>() {
            public void updated(boolean param2Boolean, Stream param2Stream1, Stream param2Stream2) {
              DirectorActivity.MonitorPortionView.this.unlinkStreamIfNeeded();
              DirectorActivity.MonitorPortionView.this.link(param2Stream2);
            }
          });
      this.slot = param1Slot;
    }
    
    private void link(final Stream toStream) {
      if (param1Stream == null)
        return; 
      DirectorActivity.this.runOnUiThread(new Runnable() {
            public void run() {
              Assert.assertNull(DirectorActivity.MonitorPortionView.this.stream);
              Assert.assertNull(DirectorActivity.MonitorPortionView.this.streamView);
              Player player = (Player)toStream;
              DirectorActivity.MonitorPortionView.access$402(DirectorActivity.MonitorPortionView.this, toStream);
              DirectorActivity.MonitorPortionView.access$502(DirectorActivity.MonitorPortionView.this, player.offerStreamView(DirectorActivity.MonitorPortionView.this.this$0, Player.ScaleMode.AspectFill));
              Assert.assertNotNull(DirectorActivity.MonitorPortionView.this.streamView);
              DirectorActivity.MonitorPortionView.this.videoView.addView(DirectorActivity.MonitorPortionView.this.streamView);
              DirectorActivity.MonitorPortionView.this.streamView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            }
          });
    }
    
    private void unlinkSlotIfNeeded() {
      if (this.slot == null)
        return; 
      unlinkStreamIfNeeded();
      this.slot.stream.unhook(this);
      this.slot = null;
    }
    
    private void unlinkStreamIfNeeded() {
      if (this.stream == null)
        return; 
      final Player player = (Player)this.stream;
      DirectorActivity.this.runOnUiThread(new Runnable() {
            public void run() {
              Assert.assertNotNull(DirectorActivity.MonitorPortionView.this.streamView);
              Assert.assertNotNull(player);
              DirectorActivity.MonitorPortionView.this.videoView.removeView(DirectorActivity.MonitorPortionView.this.streamView);
              player.reclaimStreamView(DirectorActivity.MonitorPortionView.this.streamView);
              DirectorActivity.MonitorPortionView.access$402(DirectorActivity.MonitorPortionView.this, null);
              DirectorActivity.MonitorPortionView.access$502(DirectorActivity.MonitorPortionView.this, null);
            }
          });
    }
    
    void sync(MonitorPanel.Portion param1Portion) {
      Assert.assertNull(this.portion);
      param1Portion.linkedSlot.hook(this, true, new ObservableValue.Observer<Slot>() {
            public void updated(boolean param2Boolean, Slot param2Slot1, Slot param2Slot2) {
              DirectorActivity.MonitorPortionView.this.unlinkSlotIfNeeded();
              DirectorActivity.MonitorPortionView.this.link(param2Slot2);
            }
          });
      this.portion = param1Portion;
    }
    
    void unSync() {
      if (this.portion == null)
        return; 
      unlinkSlotIfNeeded();
      this.portion.linkedSlot.unhook(this);
      this.portion = null;
    }
  }
  
  class null extends ObservableValue.Observer<Slot> {
    null() {}
    
    public void updated(boolean param1Boolean, Slot param1Slot1, Slot param1Slot2) {
      this.this$1.unlinkSlotIfNeeded();
      this.this$1.link(param1Slot2);
    }
  }
  
  class null extends ObservableValue.Observer<Stream> {
    null() {}
    
    public void updated(boolean param1Boolean, Stream param1Stream1, Stream param1Stream2) {
      this.this$1.unlinkStreamIfNeeded();
      this.this$1.link(param1Stream2);
    }
  }
  
  class null implements Runnable {
    public void run() {
      Assert.assertNull(this.this$1.stream);
      Assert.assertNull(this.this$1.streamView);
      Player player = (Player)toStream;
      DirectorActivity.MonitorPortionView.access$402(this.this$1, toStream);
      DirectorActivity.MonitorPortionView.access$502(this.this$1, player.offerStreamView(this.this$1.this$0, Player.ScaleMode.AspectFill));
      Assert.assertNotNull(this.this$1.streamView);
      this.this$1.videoView.addView(this.this$1.streamView);
      this.this$1.streamView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
    }
  }
  
  class null implements Runnable {
    public void run() {
      Assert.assertNotNull(this.this$1.streamView);
      Assert.assertNotNull(player);
      this.this$1.videoView.removeView(this.this$1.streamView);
      player.reclaimStreamView(this.this$1.streamView);
      DirectorActivity.MonitorPortionView.access$402(this.this$1, null);
      DirectorActivity.MonitorPortionView.access$502(this.this$1, null);
    }
  }
  
  private class SlotCell {
    private BorderMakeup borderMakeup;
    
    private final FrameLayout highlightBorder;
    
    private final FrameLayout layout;
    
    private final Slot slot;
    
    private View streamView;
    
    private final FrameLayout videoPanel;
    
    SlotCell(Slot param1Slot, FrameLayout param1FrameLayout) {
      this.slot = param1Slot;
      this.layout = param1FrameLayout;
      this$0.getLayoutInflater().inflate(2130968662, this.layout);
      this.videoPanel = (FrameLayout)this.layout.findViewById(2131624196);
      this.highlightBorder = (FrameLayout)this.layout.findViewById(2131624197);
      setupClickListeners();
    }
    
    private void setupClickListeners() {
      this.videoPanel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param2View) {
              if (((Boolean)DirectorActivity.SlotCell.this.this$0.isEditing.get()).booleanValue()) {
                (((MonitorPanel)MShow.program.pvw.get()).getPortions()[0]).linkedSlot.set(DirectorActivity.SlotCell.this.slot);
                return;
              } 
              (((MonitorPanel)MShow.program.pgm.get()).getPortions()[0]).linkedSlot.set(DirectorActivity.SlotCell.this.slot);
            }
          });
      this.videoPanel.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View param2View) {
              (new PopupMenu(param2View)).add("移除", new View.OnClickListener() {
                    public void onClick(View param3View) {
                      Stream stream = (Stream)this.this$2.this$1.slot.stream.get();
                      if (stream == null)
                        return; 
                      if (stream instanceof YCRTMPPlayer) {
                        MShow.program.deleteRTMPSource(((YCRTMPPlayer)stream).source);
                        return;
                      } 
                      MShow.playerCenter.removePlayer((Player)stream);
                    }
                  }).show();
              return true;
            }
          });
      this.layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param2View) { (new PopupBottomSheet(DirectorActivity.SlotCell.this.this$0)).add("Mshow 手机开播", new OnClickListener() {
                    public void onClick(View param3View) {
                      String str1;
                      String str2 = (str1 = MShow.invitation.makeString(((Integer)MShow.program.code.get()).intValue(), MShow.playerCenter.localServerIPAndPort())).valueOf(MShow.program.number.get());
                      PopupQRCodeDialog.showDialog(DirectorActivity.SlotCell.this.this$0, "邀请好友加入直播", str1, str2);
                    }
                  }).add("RTMP 流视频", new OnClickListener() {
                    public void onClick(View param3View) { (new PopupURLEditingDialog(DirectorActivity.SlotCell.this.this$0)).show(); }
                  }).add("添加设备（推流）", new OnClickListener() {
                    public void onClick(View param3View) {
                      String str1 = MShow.nginx.RTMPAddressPrefix() + "/mshow" + this.this$2.this$1.slot.index;
                      Assert.assertNotNull(MShow.program.WANRTMPPushURLs);
                      String str2 = (String)MShow.program.WANRTMPPushURLs.get(this.this$2.this$1.slot.index - 1);
                      DirectorActivity directorActivity = DirectorActivity.SlotCell.this.this$0;
                      PopupRadioButtonsDialog.Callback callback = new PopupRadioButtonsDialog.Callback() {
                          public void onConfirm(final int selectedIndex) { DirectorActivity.SlotCell.this.this$0.runOnUiThread(new Runnable() {
                                  public void run() { (new PopupShowingURLDialog(DirectorActivity.SlotCell.this.this$0, titles[selectedIndex], URLStrings[selectedIndex])).show(); }
                                }); }
                        };
                      (new PopupRadioButtonsDialog(directorActivity, "选择设备和Mshow连接方式", new String[] { "局域网：设备与Mshow在同一网络下", "互联网：设备与Mshow在不同网络下" }, callback)).show();
                    }
                  }).addSectionBar().add("取消", null).show(); }
          });
    }
    
    protected void finalize() { super.finalize(); }
    
    void setBorderMakeup(BorderMakeup param1BorderMakeup) {
      if (this.borderMakeup != null)
        this.borderMakeup.unsync(); 
      this.borderMakeup = param1BorderMakeup;
      if (param1BorderMakeup != null)
        param1BorderMakeup.sync(); 
    }
    
    void sync() {
      this.slot.stream.hook(this, true, MShow.syncMain, new ObservableValue.Observer<Stream>() {
            public void updated(boolean param2Boolean, Stream param2Stream1, Stream param2Stream2) {
              boolean bool1 = true;
              if (param2Stream2 != null) {
                Player player = (Player)param2Stream2;
                Assert.assertNotNull(player);
                DirectorActivity.SlotCell.access$1002(DirectorActivity.SlotCell.this, player.offerStreamView(DirectorActivity.SlotCell.this.this$0, Player.ScaleMode.AspectFit));
                DirectorActivity.SlotCell.this.videoPanel.addView(DirectorActivity.SlotCell.this.streamView);
                DirectorActivity.SlotCell.this.streamView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
              } 
              if (param2Stream2 == null) {
                param2Boolean = true;
              } else {
                param2Boolean = false;
              } 
              Boolean bool = Boolean.valueOf(param2Boolean);
              FrameLayout frameLayout = DirectorActivity.SlotCell.this.videoPanel;
              if (!bool.booleanValue()) {
                param2Boolean = true;
              } else {
                param2Boolean = false;
              } 
              frameLayout.setClickable(param2Boolean);
              frameLayout = DirectorActivity.SlotCell.this.videoPanel;
              if (!bool.booleanValue()) {
                param2Boolean = bool1;
              } else {
                param2Boolean = false;
              } 
              frameLayout.setLongClickable(param2Boolean);
              DirectorActivity.SlotCell.this.layout.setClickable(bool.booleanValue());
            }
            
            public void willChange(Stream param2Stream1, Stream param2Stream2) {
              if (param2Stream1 == null)
                return; 
              Player player = (Player)param2Stream1;
              Assert.assertNotNull("如果此前有stream，则一定有对应的视图", DirectorActivity.SlotCell.this.streamView);
              DirectorActivity.SlotCell.this.videoPanel.removeView(DirectorActivity.SlotCell.this.streamView);
              player.reclaimStreamView(DirectorActivity.SlotCell.this.streamView);
              DirectorActivity.SlotCell.access$1002(DirectorActivity.SlotCell.this, null);
            }
          });
      DirectorActivity.this.dnd.addSource(this.videoPanel, this.slot, new DragAndDropGroup.StartingCallback() {
            public Bitmap optionalThumbImage() { return ((Player)this.this$1.slot.stream.get()).takeThumbImage(); }
            
            public boolean shouldStart() { return (this.this$1.slot.stream.get() != null); }
          });
      DirectorActivity.this.dnd.addAcceptor(this.layout, new DragAndDropGroup.AcceptingCallback<Slot>() {
            public void didAccept(Slot param2Slot) {
              Log.i("DnD/Director", String.format(Locale.getDefault(), "Swap slots [%d] <-> [%d]", new Object[] { Integer.valueOf(param2Slot.index), Integer.valueOf((DirectorActivity.SlotCell.access$900(this.this$1)).index) }));
              MShow.program.swapStreamInSlots(param2Slot, DirectorActivity.SlotCell.this.slot);
            }
            
            public boolean shouldAccept(Slot param2Slot) { return (!((Boolean)DirectorActivity.SlotCell.this.this$0.isEditing.get()).booleanValue() && param2Slot != DirectorActivity.SlotCell.this.slot); }
          });
      DirectorActivity.this.isEditing.hook(this, true, new ObservableValue.Observer<Boolean>() {
            public void updated(boolean param2Boolean, Boolean param2Boolean1, Boolean param2Boolean2) {
              if (param2Boolean2.booleanValue()) {
                Drawable drawable1 = ContextCompat.getDrawable(DirectorActivity.SlotCell.this.this$0, 2130837646);
                DirectorActivity.SlotCell.this.setBorderMakeup(new DirectorActivity.SlotCell.BorderMakeup(DirectorActivity.SlotCell.this, MShow.program.pvw, drawable1));
                return;
              } 
              Drawable drawable = ContextCompat.getDrawable(DirectorActivity.SlotCell.this.this$0, 2130837647);
              DirectorActivity.SlotCell.this.setBorderMakeup(new DirectorActivity.SlotCell.BorderMakeup(DirectorActivity.SlotCell.this, MShow.program.pgm, drawable));
            }
          });
    }
    
    public String toString() { return String.format(Locale.CHINA, "(SlotCell stream: %s)", new Object[] { this.slot.stream.get() }); }
    
    void unSync() {
      setBorderMakeup(null);
      DirectorActivity.this.isEditing.unhook(this);
      DirectorActivity.this.dnd.removeAcceptor(this.layout);
      DirectorActivity.this.dnd.removeSource(this.videoPanel);
      this.slot.stream.unhook(this);
      this.videoPanel.removeAllViews();
    }
    
    class BorderMakeup {
      private final Drawable drawable;
      
      private final ObservableValue<MonitorPanel> panelSource;
      
      BorderMakeup(ObservableValue<MonitorPanel> param2ObservableValue, Drawable param2Drawable) {
        this.panelSource = param2ObservableValue;
        this.drawable = param2Drawable;
      }
      
      private void makeup() {
        MonitorPanel.Portion[] arrayOfPortion = ((MonitorPanel)this.panelSource.get()).getPortions();
        int i = arrayOfPortion.length;
        for (byte b = 0; b < i; b++) {
          if ((arrayOfPortion[b]).linkedSlot.get() == DirectorActivity.SlotCell.this.slot) {
            DirectorActivity.SlotCell.this.highlightBorder.setBackground(this.drawable);
            return;
          } 
        } 
        DirectorActivity.SlotCell.this.highlightBorder.setBackgroundColor(0);
      }
      
      void sync() { this.panelSource.hook(this, true, new ObservableValue.Observer<MonitorPanel>() {
              public void updated(boolean param3Boolean, MonitorPanel param3MonitorPanel1, MonitorPanel param3MonitorPanel2) {
                Assert.assertNotNull("toPanel不应该为null", param3MonitorPanel2);
                MonitorPanel.Portion[] arrayOfPortion = param3MonitorPanel2.getPortions();
                int i = arrayOfPortion.length;
                byte b;
                for (b = 0; b < i; b++) {
                  (arrayOfPortion[b]).linkedSlot.hook(DirectorActivity.SlotCell.BorderMakeup.this.this$1, true, MShow.asyncMain, new Observer<Slot>() {
                        public void updated(boolean param4Boolean, Slot param4Slot1, Slot param4Slot2) { DirectorActivity.SlotCell.BorderMakeup.null.this.this$2.makeup(); }
                      });
                } 
              }
              
              public void willChange(MonitorPanel param3MonitorPanel1, MonitorPanel param3MonitorPanel2) {
                if (param3MonitorPanel1 != null) {
                  MonitorPanel.Portion[] arrayOfPortion = param3MonitorPanel1.getPortions();
                  int i = arrayOfPortion.length;
                  byte b = 0;
                  while (true) {
                    if (b < i) {
                      (arrayOfPortion[b]).linkedSlot.unhook(DirectorActivity.SlotCell.BorderMakeup.this.this$1);
                      b++;
                      continue;
                    } 
                    return;
                  } 
                } 
              }
            }); }
      
      void unsync() {
        MonitorPanel.Portion[] arrayOfPortion = ((MonitorPanel)this.panelSource.get()).getPortions();
        int i = arrayOfPortion.length;
        for (byte b = 0; b < i; b++)
          (arrayOfPortion[b]).linkedSlot.unhook(DirectorActivity.SlotCell.this); 
        this.panelSource.unhook(this);
      }
    }
    
    class null extends ObservableValue.Observer<MonitorPanel> {
      null() {}
      
      public void updated(boolean param2Boolean, MonitorPanel param2MonitorPanel1, MonitorPanel param2MonitorPanel2) {
        Assert.assertNotNull("toPanel不应该为null", param2MonitorPanel2);
        MonitorPanel.Portion[] arrayOfPortion = param2MonitorPanel2.getPortions();
        int i = arrayOfPortion.length;
        byte b;
        for (b = 0; b < i; b++) {
          (arrayOfPortion[b]).linkedSlot.hook(this.this$2.this$1, true, MShow.asyncMain, new ObservableValue.Observer<Slot>() {
                public void updated(boolean param4Boolean, Slot param4Slot1, Slot param4Slot2) { DirectorActivity.SlotCell.BorderMakeup.null.this.this$2.makeup(); }
              });
        } 
      }
      
      public void willChange(MonitorPanel param2MonitorPanel1, MonitorPanel param2MonitorPanel2) {
        if (param2MonitorPanel1 != null) {
          MonitorPanel.Portion[] arrayOfPortion = param2MonitorPanel1.getPortions();
          int i = arrayOfPortion.length;
          byte b = 0;
          while (true) {
            if (b < i) {
              (arrayOfPortion[b]).linkedSlot.unhook(this.this$2.this$1);
              b++;
              continue;
            } 
            return;
          } 
        } 
      }
    }
    
    class null extends ObservableValue.Observer<Slot> {
      public void updated(boolean param2Boolean, Slot param2Slot1, Slot param2Slot2) { this.this$3.this$2.makeup(); }
    }
  }
  
  class null implements View.OnClickListener {
    null() {}
    
    public void onClick(View param1View) {
      if (((Boolean)this.this$1.this$0.isEditing.get()).booleanValue()) {
        (((MonitorPanel)MShow.program.pvw.get()).getPortions()[0]).linkedSlot.set(this.this$1.slot);
        return;
      } 
      (((MonitorPanel)MShow.program.pgm.get()).getPortions()[0]).linkedSlot.set(this.this$1.slot);
    }
  }
  
  class null implements View.OnLongClickListener {
    null() {}
    
    public boolean onLongClick(View param1View) {
      (new PopupMenu(param1View)).add("移除", new View.OnClickListener() {
            public void onClick(View param3View) {
              Stream stream = (Stream)this.this$2.this$1.slot.stream.get();
              if (stream == null)
                return; 
              if (stream instanceof YCRTMPPlayer) {
                MShow.program.deleteRTMPSource(((YCRTMPPlayer)stream).source);
                return;
              } 
              MShow.playerCenter.removePlayer((Player)stream);
            }
          }).show();
      return true;
    }
  }
  
  class null implements View.OnClickListener {
    public void onClick(View param1View) {
      Stream stream = (Stream)this.this$2.this$1.slot.stream.get();
      if (stream == null)
        return; 
      if (stream instanceof YCRTMPPlayer) {
        MShow.program.deleteRTMPSource(((YCRTMPPlayer)stream).source);
        return;
      } 
      MShow.playerCenter.removePlayer((Player)stream);
    }
  }
  
  class null implements View.OnClickListener {
    null() {}
    
    public void onClick(View param1View) { (new PopupBottomSheet(this.this$1.this$0)).add("Mshow 手机开播", new View.OnClickListener() {
            public void onClick(View param3View) {
              String str1;
              String str2 = (str1 = MShow.invitation.makeString(((Integer)MShow.program.code.get()).intValue(), MShow.playerCenter.localServerIPAndPort())).valueOf(MShow.program.number.get());
              PopupQRCodeDialog.showDialog(this.this$2.this$1.this$0, "邀请好友加入直播", str1, str2);
            }
          }).add("RTMP 流视频", new View.OnClickListener() {
            public void onClick(View param3View) { (new PopupURLEditingDialog(this.this$2.this$1.this$0)).show(); }
          }).add("添加设备（推流）", new View.OnClickListener() {
            public void onClick(View param3View) {
              String str1 = MShow.nginx.RTMPAddressPrefix() + "/mshow" + this.this$2.this$1.slot.index;
              Assert.assertNotNull(MShow.program.WANRTMPPushURLs);
              String str2 = (String)MShow.program.WANRTMPPushURLs.get(this.this$2.this$1.slot.index - 1);
              DirectorActivity directorActivity = this.this$2.this$1.this$0;
              PopupRadioButtonsDialog.Callback callback = new PopupRadioButtonsDialog.Callback() {
                  public void onConfirm(final int selectedIndex) { this.this$3.this$2.this$1.this$0.runOnUiThread(new Runnable() {
                          public void run() { (new PopupShowingURLDialog(this.this$4.this$3.this$2.this$1.this$0, titles[selectedIndex], URLStrings[selectedIndex])).show(); }
                        }); }
                };
              (new PopupRadioButtonsDialog(directorActivity, "选择设备和Mshow连接方式", new String[] { "局域网：设备与Mshow在同一网络下", "互联网：设备与Mshow在不同网络下" }, callback)).show();
            }
          }).addSectionBar().add("取消", null).show(); }
  }
  
  class null implements View.OnClickListener {
    public void onClick(View param1View) {
      String str1 = MShow.nginx.RTMPAddressPrefix() + "/mshow" + this.this$2.this$1.slot.index;
      Assert.assertNotNull(MShow.program.WANRTMPPushURLs);
      String str2 = (String)MShow.program.WANRTMPPushURLs.get(this.this$2.this$1.slot.index - 1);
      DirectorActivity directorActivity = this.this$2.this$1.this$0;
      PopupRadioButtonsDialog.Callback callback = new PopupRadioButtonsDialog.Callback() {
          public void onConfirm(final int selectedIndex) { this.this$3.this$2.this$1.this$0.runOnUiThread(new Runnable() {
                  public void run() { (new PopupShowingURLDialog(this.this$4.this$3.this$2.this$1.this$0, titles[selectedIndex], URLStrings[selectedIndex])).show(); }
                }); }
        };
      (new PopupRadioButtonsDialog(directorActivity, "选择设备和Mshow连接方式", new String[] { "局域网：设备与Mshow在同一网络下", "互联网：设备与Mshow在不同网络下" }, callback)).show();
    }
  }
  
  class null implements PopupRadioButtonsDialog.Callback {
    public void onConfirm(final int selectedIndex) { this.this$3.this$2.this$1.this$0.runOnUiThread(new Runnable() {
            public void run() { (new PopupShowingURLDialog(this.this$4.this$3.this$2.this$1.this$0, titles[selectedIndex], URLStrings[selectedIndex])).show(); }
          }); }
  }
  
  class null implements Runnable {
    public void run() { (new PopupShowingURLDialog(this.this$4.this$3.this$2.this$1.this$0, this.this$4.val$titles[selectedIndex], this.this$4.val$URLStrings[selectedIndex])).show(); }
  }
  
  class null implements View.OnClickListener {
    public void onClick(View param1View) { (new PopupURLEditingDialog(this.this$2.this$1.this$0)).show(); }
  }
  
  class null implements View.OnClickListener {
    public void onClick(View param1View) {
      String str1;
      String str2 = (str1 = MShow.invitation.makeString(((Integer)MShow.program.code.get()).intValue(), MShow.playerCenter.localServerIPAndPort())).valueOf(MShow.program.number.get());
      PopupQRCodeDialog.showDialog(this.this$2.this$1.this$0, "邀请好友加入直播", str1, str2);
    }
  }
  
  class null extends ObservableValue.Observer<Stream> {
    null() {}
    
    public void updated(boolean param1Boolean, Stream param1Stream1, Stream param1Stream2) {
      boolean bool1 = true;
      if (param1Stream2 != null) {
        Player player = (Player)param1Stream2;
        Assert.assertNotNull(player);
        DirectorActivity.SlotCell.access$1002(this.this$1, player.offerStreamView(this.this$1.this$0, Player.ScaleMode.AspectFit));
        this.this$1.videoPanel.addView(this.this$1.streamView);
        this.this$1.streamView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
      } 
      if (param1Stream2 == null) {
        param1Boolean = true;
      } else {
        param1Boolean = false;
      } 
      Boolean bool = Boolean.valueOf(param1Boolean);
      FrameLayout frameLayout = this.this$1.videoPanel;
      if (!bool.booleanValue()) {
        param1Boolean = true;
      } else {
        param1Boolean = false;
      } 
      frameLayout.setClickable(param1Boolean);
      frameLayout = this.this$1.videoPanel;
      if (!bool.booleanValue()) {
        param1Boolean = bool1;
      } else {
        param1Boolean = false;
      } 
      frameLayout.setLongClickable(param1Boolean);
      this.this$1.layout.setClickable(bool.booleanValue());
    }
    
    public void willChange(Stream param1Stream1, Stream param1Stream2) {
      if (param1Stream1 == null)
        return; 
      Player player = (Player)param1Stream1;
      Assert.assertNotNull("如果此前有stream，则一定有对应的视图", this.this$1.streamView);
      this.this$1.videoPanel.removeView(this.this$1.streamView);
      player.reclaimStreamView(this.this$1.streamView);
      DirectorActivity.SlotCell.access$1002(this.this$1, null);
    }
  }
  
  class null implements DragAndDropGroup.StartingCallback {
    null() {}
    
    public Bitmap optionalThumbImage() { return ((Player)this.this$1.slot.stream.get()).takeThumbImage(); }
    
    public boolean shouldStart() { return (this.this$1.slot.stream.get() != null); }
  }
  
  class null extends Object implements DragAndDropGroup.AcceptingCallback<Slot> {
    null() {}
    
    public void didAccept(Slot param1Slot) {
      Log.i("DnD/Director", String.format(Locale.getDefault(), "Swap slots [%d] <-> [%d]", new Object[] { Integer.valueOf(param1Slot.index), Integer.valueOf((DirectorActivity.SlotCell.access$900(this.this$1)).index) }));
      MShow.program.swapStreamInSlots(param1Slot, this.this$1.slot);
    }
    
    public boolean shouldAccept(Slot param1Slot) { return (!((Boolean)this.this$1.this$0.isEditing.get()).booleanValue() && param1Slot != this.this$1.slot); }
  }
  
  class null extends ObservableValue.Observer<Boolean> {
    null() {}
    
    public void updated(boolean param1Boolean, Boolean param1Boolean1, Boolean param1Boolean2) {
      if (param1Boolean2.booleanValue()) {
        Drawable drawable1 = ContextCompat.getDrawable(this.this$1.this$0, 2130837646);
        this.this$1.setBorderMakeup(new DirectorActivity.SlotCell.BorderMakeup(this.this$1, MShow.program.pvw, drawable1));
        return;
      } 
      Drawable drawable = ContextCompat.getDrawable(this.this$1.this$0, 2130837647);
      this.this$1.setBorderMakeup(new DirectorActivity.SlotCell.BorderMakeup(this.this$1, MShow.program.pgm, drawable));
    }
  }
  
  class BorderMakeup {
    private final Drawable drawable;
    
    private final ObservableValue<MonitorPanel> panelSource;
    
    BorderMakeup(ObservableValue<MonitorPanel> param1ObservableValue, Drawable param1Drawable) {
      this.panelSource = param1ObservableValue;
      this.drawable = param1Drawable;
    }
    
    private void makeup() {
      MonitorPanel.Portion[] arrayOfPortion = ((MonitorPanel)this.panelSource.get()).getPortions();
      int i = arrayOfPortion.length;
      for (byte b = 0; b < i; b++) {
        if ((arrayOfPortion[b]).linkedSlot.get() == this.this$1.slot) {
          this.this$1.highlightBorder.setBackground(this.drawable);
          return;
        } 
      } 
      this.this$1.highlightBorder.setBackgroundColor(0);
    }
    
    void sync() { this.panelSource.hook(this, true, new ObservableValue.Observer<MonitorPanel>() {
            public void updated(boolean param3Boolean, MonitorPanel param3MonitorPanel1, MonitorPanel param3MonitorPanel2) {
              Assert.assertNotNull("toPanel不应该为null", param3MonitorPanel2);
              MonitorPanel.Portion[] arrayOfPortion = param3MonitorPanel2.getPortions();
              int i = arrayOfPortion.length;
              byte b;
              for (b = 0; b < i; b++) {
                (arrayOfPortion[b]).linkedSlot.hook(DirectorActivity.SlotCell.BorderMakeup.this.this$1, true, MShow.asyncMain, new Observer<Slot>() {
                      public void updated(boolean param4Boolean, Slot param4Slot1, Slot param4Slot2) { DirectorActivity.SlotCell.BorderMakeup.null.this.this$2.makeup(); }
                    });
              } 
            }
            
            public void willChange(MonitorPanel param3MonitorPanel1, MonitorPanel param3MonitorPanel2) {
              if (param3MonitorPanel1 != null) {
                MonitorPanel.Portion[] arrayOfPortion = param3MonitorPanel1.getPortions();
                int i = arrayOfPortion.length;
                byte b = 0;
                while (true) {
                  if (b < i) {
                    (arrayOfPortion[b]).linkedSlot.unhook(DirectorActivity.SlotCell.BorderMakeup.this.this$1);
                    b++;
                    continue;
                  } 
                  return;
                } 
              } 
            }
          }); }
    
    void unsync() {
      MonitorPanel.Portion[] arrayOfPortion = ((MonitorPanel)this.panelSource.get()).getPortions();
      int i = arrayOfPortion.length;
      for (byte b = 0; b < i; b++)
        (arrayOfPortion[b]).linkedSlot.unhook(this.this$1); 
      this.panelSource.unhook(this);
    }
  }
  
  class null extends ObservableValue.Observer<MonitorPanel> {
    null() {}
    
    public void updated(boolean param1Boolean, MonitorPanel param1MonitorPanel1, MonitorPanel param1MonitorPanel2) {
      Assert.assertNotNull("toPanel不应该为null", param1MonitorPanel2);
      MonitorPanel.Portion[] arrayOfPortion = param1MonitorPanel2.getPortions();
      int i = arrayOfPortion.length;
      byte b;
      for (b = 0; b < i; b++) {
        (arrayOfPortion[b]).linkedSlot.hook(this.this$2.this$1, true, MShow.asyncMain, new ObservableValue.Observer<Slot>() {
              public void updated(boolean param4Boolean, Slot param4Slot1, Slot param4Slot2) { DirectorActivity.SlotCell.BorderMakeup.null.this.this$2.makeup(); }
            });
      } 
    }
    
    public void willChange(MonitorPanel param1MonitorPanel1, MonitorPanel param1MonitorPanel2) {
      if (param1MonitorPanel1 != null) {
        MonitorPanel.Portion[] arrayOfPortion = param1MonitorPanel1.getPortions();
        int i = arrayOfPortion.length;
        byte b = 0;
        while (true) {
          if (b < i) {
            (arrayOfPortion[b]).linkedSlot.unhook(this.this$2.this$1);
            b++;
            continue;
          } 
          return;
        } 
      } 
    }
  }
  
  class null extends ObservableValue.Observer<Slot> {
    public void updated(boolean param1Boolean, Slot param1Slot1, Slot param1Slot2) { this.this$3.this$2.makeup(); }
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\activity\DirectorActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */