package com.yy.mshow.peripherals.localmixer;

import android.util.Log;
import android.util.Size;
import com.mshow.msmediasdk.MSMedia;
import com.mshow.msmediasdk.MSMediaMixer;
import com.mshow.msmediasdk.MSPlayer;
import com.ycloud.live.YCMedia;
import com.ycloud.live.YCMediaRequest;
import com.yy.mshow.cores.KVOPathFromPanelToStreams;
import com.yy.mshow.cores.MonitorPanel;
import com.yy.mshow.cores.Program;
import com.yy.mshow.cores.Resolution;
import com.yy.mshow.cores.Size;
import com.yy.mshow.cores.Slot;
import com.yy.mshow.cores.Stream;
import com.yy.mshow.peripherals.playercenter.LANPlayer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import kits.Statechart;
import kits.observables.ObservableList;
import kits.observables.ObservableValue;

public class LocalMixer implements MSMedia.EncodedListener {
  private static final String TAG = "LocalMixer";
  
  private KVOPathFromPanelToStreams kvoPGM;
  
  public final MixerMode mode;
  
  private final MSMediaMixer msMediaMixer = new MSMediaMixer();
  
  private Resolution resolution;
  
  private final YCMedia ycMedia;
  
  public LocalMixer(final int appID) {
    this.msMediaMixer.init();
    this.msMediaMixer.setMasterVolume(50);
    this.ycMedia = YCMedia.getInstance();
    this.msMediaMixer.setEncodedListener(this);
    this.mode = new MixerMode();
    this.mode.register(this, MixerMode.State.Started, true, new Statechart.StateListener<MixerMode.State>() {
          public void didEnter(LocalMixer.MixerMode.State param1State) {
            Log.i("LocalMixer", "didEnter: started");
            LocalMixer.this.ycMedia.requestMethod(new YCMediaRequest.YCStartEncodedAudioLive(2, 44100));
            Log.i("LocalMixer", "didEnter: ycmedia start audio");
            LocalMixer.this.ycMedia.requestMethod(new YCMediaRequest.YCStartEncodedVideoLive(appID, 1, this.this$0.resolution.width, this.this$0.resolution.height, 25, 1500));
            Log.i("LocalMixer", "didEnter: ycmedia start video");
            LocalMixer.this.msMediaMixer.start();
            Log.i("LocalMixer", "didEnter: msMediaMixer start mixer");
          }
          
          public void willLeave(LocalMixer.MixerMode.State param1State) {
            Log.i("LocalMixer", "willLeave: started");
            LocalMixer.this.ycMedia.requestMethod(new YCMediaRequest.YCStopEncodedVideoLive(appID));
            Log.i("LocalMixer", "willLeave: ycmedia stop video");
            LocalMixer.this.ycMedia.requestMethod(new YCMediaRequest.YCStopEncodedAudioLive());
            Log.i("LocalMixer", "willLeave: ycmedia stop audio");
            LocalMixer.this.msMediaMixer.stop();
            Log.i("LocalMixer", "willLeave: msMediaMixer stop mixer");
          }
        });
  }
  
  private void setAudio(LANPlayer paramLANPlayer, Boolean paramBoolean, int paramInt) {
    byte b;
    MSMediaMixer mSMediaMixer = this.msMediaMixer;
    MSPlayer mSPlayer = paramLANPlayer.msPlayer;
    if (paramBoolean.booleanValue()) {
      b = 1;
    } else {
      b = 0;
    } 
    mSMediaMixer.setAudioSource(mSPlayer, b, paramInt);
  }
  
  private void setVideo(List<MSMediaMixer.MixLayout> paramList) { this.msMediaMixer.setLayout(paramList); }
  
  private void startSynchronizingProgram(final Program program) {
    updateLocalMixer(paramProgram);
    this.kvoPGM = new KVOPathFromPanelToStreams(paramProgram.pgm);
    this.kvoPGM.sync();
    this.kvoPGM.streams.register(this, Boolean.valueOf(false), new ObservableList.Observer<Stream>() {
          public void didAdd(Stream param1Stream) {
            if (param1Stream instanceof LANPlayer)
              LocalMixer.this.updateLocalMixer(program); 
          }
          
          public void willRemove(Stream param1Stream) {
            if (param1Stream instanceof LANPlayer)
              LocalMixer.this.updateLocalMixer(program); 
          }
        });
  }
  
  private void stopSynchronizingProgram() {
    this.kvoPGM.streams.unregister(this);
    this.kvoPGM.unSync();
  }
  
  private void updateLocalMixer(Program paramProgram) {
    MonitorPanel monitorPanel = (MonitorPanel)paramProgram.pgm.get();
    this.resolution = (Resolution)paramProgram.resolution.get();
    ArrayList arrayList = new ArrayList();
    HashSet hashSet = new HashSet();
    byte b2 = 0;
    MonitorPanel.Portion[] arrayOfPortion = monitorPanel.getPortions();
    int j = arrayOfPortion.length;
    byte b1 = 0;
    while (b1 < j) {
      MonitorPanel.Portion portion = arrayOfPortion[b1];
      Stream stream = (Stream)((Slot)portion.linkedSlot.get()).stream.get();
      byte b = b2;
      if (stream != null)
        if (!(stream instanceof LANPlayer)) {
          b = b2;
        } else {
          LANPlayer lANPlayer = (LANPlayer)stream;
          arrayList.add(new MSMediaMixer.MixLayout(b2, monitorPanel.evalRect(new Size(this.resolution.width, this.resolution.height), portion), lANPlayer.msPlayer));
          b = b2 + 1;
          hashSet.add((LANPlayer)stream);
        }  
      b1++;
      b2 = b;
    } 
    setVideo(arrayList);
    Log.i("LocalMixer", "updateLocalMixer: layouts: " + arrayList.toString());
    MixerMode.Event event = paramProgram.slots;
    int i = event.length;
    for (b1 = 0; b1 < i; b1++) {
      Stream stream = (Stream)(event[b1]).stream.get();
      if (stream != null && stream instanceof LANPlayer) {
        LANPlayer lANPlayer = (LANPlayer)stream;
        Boolean bool = Boolean.valueOf(hashSet.contains(lANPlayer));
        if (bool.booleanValue()) {
          b2 = 50;
        } else {
          b2 = 0;
        } 
        setAudio(lANPlayer, bool, b2);
      } 
    } 
    MixerMode mixerMode = this.mode;
    if (hashSet.size() > 0) {
      MixerMode.Event event1 = MixerMode.Event.HasLANinPGM;
    } else {
      event = MixerMode.Event.NoLANinPGM;
    } 
    mixerMode.schedule(event);
  }
  
  protected void finalize() {
    super.finalize();
    this.msMediaMixer.stop();
    this.msMediaMixer.deInit();
  }
  
  public Boolean isMixing() {
    if (this.mode.getState() == MixerMode.State.Started) {
      boolean bool1 = true;
      return Boolean.valueOf(bool1);
    } 
    boolean bool = false;
    return Boolean.valueOf(bool);
  }
  
  public void onAudioEncoded(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3) { this.ycMedia.requestMethod(new YCMediaRequest.YCPushEncodedAudioData(paramArrayOfByte, paramInt1, paramInt2, paramInt3)); }
  
  public void onVideoEncoded(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) { this.ycMedia.requestMethod(new YCMediaRequest.YCPushEncodedVideoData(paramArrayOfByte, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5)); }
  
  public void sync(final Program program) {
    paramProgram.isOnLive.hook(this, true, new ObservableValue.Observer<Boolean>() {
          public void willChange(Boolean param1Boolean1, Boolean param1Boolean2) {
            if (!param1Boolean1.booleanValue() && param1Boolean2.booleanValue()) {
              LocalMixer.this.startSynchronizingProgram(program);
              Log.i("LocalMixer", "willChange: program will start live");
              LocalMixer.this.mode.schedule(LocalMixer.MixerMode.Event.StartLive);
            } 
            if (param1Boolean1.booleanValue() && !param1Boolean2.booleanValue()) {
              Log.i("LocalMixer", "willChange: program will stop live");
              LocalMixer.this.mode.schedule(LocalMixer.MixerMode.Event.StopLive);
              LocalMixer.this.stopSynchronizingProgram();
            } 
          }
        });
    paramProgram.resolution.hook(this, true, new ObservableValue.Observer<Resolution>() {
          public void updated(boolean param1Boolean, Resolution param1Resolution1, Resolution param1Resolution2) { LocalMixer.this.msMediaMixer.setOutputSize(new Size(param1Resolution2.width, param1Resolution2.height)); }
        });
  }
  
  public static class MixerMode extends Statechart<MixerMode.State, MixerMode.Event> {
    MixerMode() { super(State.Idle); }
    
    protected void performSchedule(Event param1Event) { // Byte code:
      //   0: iconst_0
      //   1: istore_3
      //   2: iconst_0
      //   3: istore_2
      //   4: getstatic com/yy/mshow/peripherals/localmixer/LocalMixer$5.$SwitchMap$com$yy$mshow$peripherals$localmixer$LocalMixer$MixerMode$Event : [I
      //   7: aload_1
      //   8: invokevirtual ordinal : ()I
      //   11: iaload
      //   12: tableswitch default -> 44, 1 -> 45, 2 -> 98, 3 -> 153, 4 -> 189
      //   44: return
      //   45: aload_0
      //   46: getfield state : Ljava/lang/Enum;
      //   49: getstatic com/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State.Idle : Lcom/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State;
      //   52: if_acmpeq -> 65
      //   55: aload_0
      //   56: getfield state : Ljava/lang/Enum;
      //   59: getstatic com/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State.WaitingForLive : Lcom/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State;
      //   62: if_acmpne -> 67
      //   65: iconst_1
      //   66: istore_2
      //   67: iload_2
      //   68: invokestatic assertTrue : (Z)V
      //   71: aload_0
      //   72: getfield state : Ljava/lang/Enum;
      //   75: getstatic com/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State.Idle : Lcom/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State;
      //   78: if_acmpne -> 91
      //   81: getstatic com/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State.WaitingForLAN : Lcom/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State;
      //   84: astore_1
      //   85: aload_0
      //   86: aload_1
      //   87: invokevirtual setState : (Ljava/lang/Enum;)V
      //   90: return
      //   91: getstatic com/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State.Started : Lcom/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State;
      //   94: astore_1
      //   95: goto -> 85
      //   98: aload_0
      //   99: getfield state : Ljava/lang/Enum;
      //   102: getstatic com/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State.Started : Lcom/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State;
      //   105: if_acmpeq -> 120
      //   108: iload_3
      //   109: istore_2
      //   110: aload_0
      //   111: getfield state : Ljava/lang/Enum;
      //   114: getstatic com/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State.WaitingForLAN : Lcom/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State;
      //   117: if_acmpne -> 122
      //   120: iconst_1
      //   121: istore_2
      //   122: iload_2
      //   123: invokestatic assertTrue : (Z)V
      //   126: aload_0
      //   127: getfield state : Ljava/lang/Enum;
      //   130: getstatic com/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State.Started : Lcom/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State;
      //   133: if_acmpne -> 146
      //   136: getstatic com/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State.WaitingForLive : Lcom/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State;
      //   139: astore_1
      //   140: aload_0
      //   141: aload_1
      //   142: invokevirtual setState : (Ljava/lang/Enum;)V
      //   145: return
      //   146: getstatic com/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State.Idle : Lcom/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State;
      //   149: astore_1
      //   150: goto -> 140
      //   153: aload_0
      //   154: getfield state : Ljava/lang/Enum;
      //   157: getstatic com/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State.Idle : Lcom/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State;
      //   160: if_acmpne -> 171
      //   163: aload_0
      //   164: getstatic com/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State.WaitingForLive : Lcom/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State;
      //   167: invokevirtual setState : (Ljava/lang/Enum;)V
      //   170: return
      //   171: aload_0
      //   172: getfield state : Ljava/lang/Enum;
      //   175: getstatic com/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State.WaitingForLAN : Lcom/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State;
      //   178: if_acmpne -> 44
      //   181: aload_0
      //   182: getstatic com/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State.Started : Lcom/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State;
      //   185: invokevirtual setState : (Ljava/lang/Enum;)V
      //   188: return
      //   189: aload_0
      //   190: getfield state : Ljava/lang/Enum;
      //   193: getstatic com/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State.WaitingForLive : Lcom/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State;
      //   196: if_acmpne -> 207
      //   199: aload_0
      //   200: getstatic com/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State.Idle : Lcom/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State;
      //   203: invokevirtual setState : (Ljava/lang/Enum;)V
      //   206: return
      //   207: aload_0
      //   208: getfield state : Ljava/lang/Enum;
      //   211: getstatic com/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State.Started : Lcom/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State;
      //   214: if_acmpne -> 44
      //   217: aload_0
      //   218: getstatic com/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State.WaitingForLAN : Lcom/yy/mshow/peripherals/localmixer/LocalMixer$MixerMode$State;
      //   221: invokevirtual setState : (Ljava/lang/Enum;)V
      //   224: return }
    
    enum Event {
      HasLANinPGM, NoLANinPGM, StartLive, StopLive;
      
      static  {
        HasLANinPGM = new Event("HasLANinPGM", 2);
        NoLANinPGM = new Event("NoLANinPGM", 3);
        $VALUES = new Event[] { StartLive, StopLive, HasLANinPGM, NoLANinPGM };
      }
    }
    
    public enum State {
      Idle, Started, WaitingForLAN, WaitingForLive;
      
      static  {
        WaitingForLAN = new State("WaitingForLAN", 2);
        Started = new State("Started", 3);
        $VALUES = new State[] { Idle, WaitingForLive, WaitingForLAN, Started };
      }
    }
  }
  
  enum Event {
    StartLive, HasLANinPGM, NoLANinPGM, StopLive;
    
    static  {
      HasLANinPGM = new Event("HasLANinPGM", 2);
      NoLANinPGM = new Event("NoLANinPGM", 3);
      $VALUES = new Event[] { StartLive, StopLive, HasLANinPGM, NoLANinPGM };
    }
  }
  
  public enum State {
    Idle, Started, WaitingForLAN, WaitingForLive;
    
    static  {
      WaitingForLAN = new State("WaitingForLAN", 2);
      Started = new State("Started", 3);
      $VALUES = new State[] { Idle, WaitingForLive, WaitingForLAN, Started };
    }
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\localmixer\LocalMixer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */