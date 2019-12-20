package com.yy.mshow.peripherals.networking.services;

import android.graphics.Rect;

import com.yy.mshow.cores.MonitorPanel;
import com.yy.mshow.cores.Program;
import com.yy.mshow.cores.Resolution;
import com.yy.mshow.cores.Size;
import com.yy.mshow.cores.Slot;
import com.yy.mshow.peripherals.localmixer.LocalMixer;
import com.yy.mshow.peripherals.playercenter.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.bodies.JsonObjectBody;
import kits.reactor.methods.POST;

public class UpdateMixerSettings extends Job {
  private final Authorization auth;
  
  private final LocalMixer mixer;
  
  private final Program program;
  
  public UpdateMixerSettings(Authorization paramAuthorization, Program paramProgram, LocalMixer paramLocalMixer) {
    this.auth = paramAuthorization;
    this.mixer = paramLocalMixer;
    this.program = paramProgram;
  }
  
  protected Reactor.Method method() { return new POST(new JsonObjectBody(this) {
          private void fillParams(ArrayList<Object> param1ArrayList1, ArrayList<Object> param1ArrayList2, int param1Int, String param1String, Rect param1Rect1, Rect param1Rect2) {
            HashMap hashMap2 = new HashMap();
            hashMap2.put("source", Integer.valueOf(param1Int));
            hashMap2.put("stretch", "AspectFill");
            hashMap2.put("left", Integer.valueOf(param1Rect1.left));
            hashMap2.put("top", Integer.valueOf(param1Rect1.top));
            hashMap2.put("width", Integer.valueOf(param1Rect1.width()));
            hashMap2.put("height", Integer.valueOf(param1Rect1.height()));
            hashMap2.put("alpha", Integer.valueOf(1));
            hashMap2.put("layer", Integer.valueOf(-1));
            hashMap2.put("rotate", Integer.valueOf(0));
            hashMap2.put("rotate_x", Integer.valueOf(0));
            hashMap2.put("rotate_y", Integer.valueOf(0));
            if (param1Rect2 != null) {
              HashMap hashMap = new HashMap();
              hashMap.put("left", Integer.valueOf(param1Rect2.left));
              hashMap.put("top", Integer.valueOf(param1Rect2.top));
              hashMap.put("width", Integer.valueOf(param1Rect2.width()));
              hashMap.put("height", Integer.valueOf(param1Rect2.height()));
              hashMap2.put("rectangle", hashMap);
            } 
            param1ArrayList1.add(hashMap2);
            HashMap hashMap1 = new HashMap();
            hashMap1.put("source", Integer.valueOf(param1Int));
            hashMap1.put("source_type", param1String);
            hashMap1.put("volume", Integer.valueOf(50));
            hashMap1.put("mix_left_right", Integer.valueOf(0));
            param1ArrayList2.add(hashMap1);
          }
          
          protected void fill(Map<String, Object> param1Map) {
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList1 = new ArrayList();
            MonitorPanel monitorPanel = (MonitorPanel)this.this$0.program.pgm.get();
            Resolution resolution = (Resolution)this.this$0.program.resolution.get();
            for (MonitorPanel.Portion portion : monitorPanel.getPortions()) {
              Slot slot = (Slot)portion.linkedSlot.get();
              if (slot != null && slot.stream.get() != null) {
                Rect rect = monitorPanel.evalRect(new Size(resolution.width, resolution.height), portion);
                Player player = (Player)slot.stream.get();
                boolean bool1 = player instanceof com.yy.mshow.peripherals.playercenter.YCRTMPPlayer;
                boolean bool2 = player instanceof com.yy.mshow.peripherals.playercenter.YCAnchorPlayer;
                boolean bool3 = player instanceof com.yy.mshow.peripherals.playercenter.LANPlayer;
                if (bool1 || bool2) {
                  fillParams(arrayList2, arrayList1, player.userId(), "YY", rect, null);
                } else if (bool3 && UpdateMixerSettings.this.mixer.isMixing().booleanValue()) {
                  fillParams(arrayList2, arrayList1, UpdateMixerSettings.this.auth.getUID().intValue(), "anchor", rect, rect);
                } 
              } 
            } 
            HashMap hashMap3 = new HashMap();
            hashMap3.put("duration", Integer.valueOf(1200));
            hashMap3.put("name", "cut");
            HashMap hashMap2 = new HashMap();
            hashMap2.put("resolution", Resolution.makeString((Resolution)this.this$0.program.resolution.get()));
            hashMap2.put("transition", hashMap3);
            hashMap2.put("target", arrayList2);
            HashMap hashMap1 = new HashMap();
            hashMap1.put("master_volume", Integer.valueOf(50));
            hashMap1.put("target", arrayList1);
            param1Map.put("video", hashMap2);
            param1Map.put("audio", hashMap1);
          }
        }); }
  
  protected String url() { return String.format(Locale.CHINA, "/v1/programs/%d/mixer_params/update", new Object[] { this.program.code.get() }); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\UpdateMixerSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */