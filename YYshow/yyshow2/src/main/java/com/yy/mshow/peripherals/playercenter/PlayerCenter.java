package com.yy.mshow.peripherals.playercenter;

import android.content.Context;
import com.mshow.msmediasdk.MSMedia;
import com.yy.mshow.cores.KVOPathFromPanelToStreams;
import com.yy.mshow.cores.Program;
import com.yy.mshow.cores.Stream;
import com.yy.mshow.peripherals.networking.HostAddress;
import com.yy.mshow.peripherals.service.Nginx;
import java.util.HashMap;
import java.util.List;
import junit.framework.Assert;
import kits.observables.ObservableList;
import kits.observables.ObservableValue;
import kits.ycmedia.Media;
import kits.ycmedia.StreamIn;
import kits.ycmedia.StreamPlayer;

public class PlayerCenter {
  private static MSMedia lanMedia;
  
  private final HashMap<Integer, YCRTMPPlayer> activeRTMPPlayers = new HashMap();
  
  private final Authorization auth;
  
  public Delegate delegate;
  
  private String localServerIPAndPort = null;
  
  private final Media media = new Media();
  
  private final Nginx nginx;
  
  public final ObservableValue<Boolean> opened = new ObservableValue(Boolean.valueOf(false));
  
  private KVOPathFromPanelToStreams pgmPath;
  
  private final HashMap<Integer, Player> players = new HashMap();
  
  private final Program program;
  
  public PlayerCenter(Authorization paramAuthorization, Program paramProgram, Nginx paramNginx) {
    this.auth = paramAuthorization;
    this.program = paramProgram;
    this.nginx = paramNginx;
  }
  
  private void addPlayer(Player paramPlayer) {
    paramPlayer.setMute(true);
    paramPlayer.play();
    this.players.put(Integer.valueOf(paramPlayer.userId()), paramPlayer);
    Assert.assertTrue(this.program.addStreamIfPossible(paramPlayer));
  }
  
  private void closeLANMedia() {
    lanMedia.stopRTPServer();
    this.localServerIPAndPort = null;
  }
  
  private void closeNginx() { this.nginx.stop(); }
  
  private void closeYCMedia() {
    this.media.close();
    this.media.setStreamsListener(null);
  }
  
  private void createRTMPStream(RTMPSource paramRTMPSource) { addPlayer(new YCRTMPPlayer(paramRTMPSource)); }
  
  public static void deinit() {
    lanMedia.deInit();
    lanMedia = null;
  }
  
  private void destroyRTMPStream(RTMPSource paramRTMPSource) { removePlayer((Player)this.players.get(paramRTMPSource.userId)); }
  
  private boolean existRTMPPlayerWithUID(int paramInt) { return !this.players.containsKey(Integer.valueOf(paramInt)) ? false : ((Player)this.players.get(Integer.valueOf(paramInt)) instanceof YCRTMPPlayer); }
  
  public static void init() {
    MSMedia.loadLibrary();
    lanMedia = new MSMedia();
    lanMedia.init();
  }
  
  private void updatePlayerMutes(List<Stream> paramList) {
    for (Player player : this.players.values()) {
      boolean bool;
      if (!paramList.contains(player)) {
        bool = true;
      } else {
        bool = false;
      } 
      player.setMute(bool);
    } 
  }
  
  public void close() {
    this.pgmPath.streams.unregister(this);
    this.pgmPath.unSync();
    this.program.rtmpSources.unregister(this);
    for (Integer integer : (Integer[])this.players.keySet().toArray(new Integer[0]))
      removePlayer((Player)this.players.get(integer)); 
    closeNginx();
    closeLANMedia();
    closeYCMedia();
    this.opened.set(Boolean.valueOf(false));
  }
  
  public String localServerIPAndPort() { return this.localServerIPAndPort; }
  
  public void open(final Context context) {
    this.program.rtmpSources.register(this, Boolean.valueOf(true), new ObservableList.Observer<RTMPSource>() {
          public void didAdd(RTMPSource param1RTMPSource) { PlayerCenter.this.createRTMPStream(param1RTMPSource); }
          
          public void updated(boolean param1Boolean, List<RTMPSource> param1List) {
            if (param1Boolean)
              for (RTMPSource rTMPSource : param1List)
                PlayerCenter.this.createRTMPStream(rTMPSource);  
          }
          
          public void willRemove(RTMPSource param1RTMPSource) { PlayerCenter.this.destroyRTMPStream(param1RTMPSource); }
        });
    this.pgmPath = new KVOPathFromPanelToStreams(this.program.pgm);
    this.pgmPath.sync();
    this.pgmPath.streams.register(this, Boolean.valueOf(true), new ObservableList.Observer<Stream>() {
          public void updated(boolean param1Boolean, List<Stream> param1List) { PlayerCenter.this.updatePlayerMutes(param1List); }
        });
    this.media.setStreamsListener(new Media.StreamsListener() {
          public void didConnect(StreamIn param1StreamIn) {
            if (!PlayerCenter.this.program.hasEmptySlot() && !PlayerCenter.this.existRTMPPlayerWithUID(param1StreamIn.userId())) {
              if (PlayerCenter.this.delegate != null)
                PlayerCenter.this.delegate.didOccurFailure("有新的视频接入，但通道已满"); 
              return;
            } 
            StreamPlayer streamPlayer = new StreamPlayer(context, param1StreamIn);
            if (RTMPSource.containsId(param1StreamIn.userId())) {
              if (PlayerCenter.this.players.containsKey(Integer.valueOf(param1StreamIn.userId()))) {
                YCRTMPPlayer yCRTMPPlayer = (YCRTMPPlayer)PlayerCenter.this.players.get(Integer.valueOf(param1StreamIn.userId()));
                yCRTMPPlayer.bindPlayer(streamPlayer);
                PlayerCenter.this.activeRTMPPlayers.put(Integer.valueOf(param1StreamIn.userId()), yCRTMPPlayer);
                return;
              } 
              Assert.assertTrue(true);
              return;
            } 
            PlayerCenter.this.addPlayer(new YCAnchorPlayer(context, param1StreamIn));
          }
          
          public void didDisconnect(StreamIn param1StreamIn) {
            if (RTMPSource.containsId(param1StreamIn.userId())) {
              if (PlayerCenter.this.activeRTMPPlayers.containsKey(Integer.valueOf(param1StreamIn.userId()))) {
                ((YCRTMPPlayer)PlayerCenter.this.activeRTMPPlayers.get(Integer.valueOf(param1StreamIn.userId()))).unbindPlayer();
                PlayerCenter.this.activeRTMPPlayers.remove(Integer.valueOf(param1StreamIn.userId()));
              } 
              return;
            } 
            Player player = (Player)PlayerCenter.this.players.get(Integer.valueOf(param1StreamIn.userId()));
            if (player != null) {
              PlayerCenter.this.removePlayer(player);
              return;
            } 
          }
        });
    this.media.open(this.auth.getUID(), (Integer)this.program.code.get(), null);
    lanMedia.setRTPListener(new MSMedia.RTPListener() {
          public void didConnect(int param1Int) {
            if (!PlayerCenter.this.program.hasEmptySlot()) {
              if (PlayerCenter.this.delegate != null)
                PlayerCenter.this.delegate.didOccurFailure("有新的局域网视频接入，但通道已满"); 
              return;
            } 
            PlayerCenter.this.addPlayer(new LANPlayer(param1Int));
          }
          
          public void didDisconnect(int param1Int) {
            Player player = (Player)PlayerCenter.this.players.get(Integer.valueOf(param1Int));
            if (player != null)
              PlayerCenter.this.removePlayer(player); 
          }
          
          public void willDisconnect(int param1Int) {}
        });
    String str = HostAddress.find();
    if (str != null) {
      if (lanMedia.startRTPServer(str, 30000) == 0) {
        str = str + ":" + '田';
      } else {
        str = null;
      } 
      this.localServerIPAndPort = str;
    } 
    this.nginx.setRTMPListener(new Nginx.RTMPListener() {
          public Boolean onArrive(String param1String) {
            if (!PlayerCenter.this.program.hasEmptySlot()) {
              if (PlayerCenter.this.delegate != null)
                PlayerCenter.this.delegate.didOccurFailure("有新的局域网视频接入，但通道已满"); 
              return Boolean.valueOf(false);
            } 
            PlayerCenter.this.addPlayer(new LANPlayer(param1String));
            return Boolean.valueOf(true);
          }
          
          public void onLeave(String param1String) {
            Player player = (Player)PlayerCenter.this.players.get(Integer.valueOf(param1String.hashCode()));
            if (player == null)
              return; 
            PlayerCenter.this.removePlayer(player);
          }
        });
    this.opened.set(this.nginx.start().valueOf(true));
  }
  
  public void removePlayer(Player paramPlayer) {
    this.program.removeStream(paramPlayer);
    this.players.remove(Integer.valueOf(paramPlayer.userId()));
    paramPlayer.stop();
  }
  
  public static interface Delegate {
    void didOccurFailure(String param1String);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\playercenter\PlayerCenter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */