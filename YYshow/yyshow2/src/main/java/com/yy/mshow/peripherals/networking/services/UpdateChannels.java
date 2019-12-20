package com.yy.mshow.peripherals.networking.services;

import com.yy.mshow.cores.MonitorPanel;
import com.yy.mshow.cores.Program;
import com.yy.mshow.cores.Slot;
import com.yy.mshow.cores.Stream;
import com.yy.mshow.peripherals.localmixer.LocalMixer;
import com.yy.mshow.peripherals.playercenter.Player;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.bodies.JsonArrayBody;
import kits.reactor.methods.POST;

public class UpdateChannels extends Job {
  private final Authorization auth;
  
  private final LocalMixer mixer;
  
  private final Program program;
  
  public UpdateChannels(Authorization paramAuthorization, Program paramProgram, LocalMixer paramLocalMixer) {
    this.auth = paramAuthorization;
    this.program = paramProgram;
    this.mixer = paramLocalMixer;
  }
  
  protected Reactor.Method method() { return new POST(new JsonArrayBody(this) {
          private void addLocalMixerChannelIfNeeded(List<Object> param1List) {
            if (UpdateChannels.this.mixer.isMixing().booleanValue())
              param1List.add(makeChannel(254, UpdateChannels.this.auth.getUID().intValue(), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(false))); 
          }
          
          private void addSlotChannels(List<Object> param1List) {
            byte b = 0;
            label21: while (b < this.this$0.program.slots.length) {
              Slot slot = this.this$0.program.slots[b];
              Stream stream = (Stream)slot.stream.get();
              if (stream != null) {
                Boolean bool1 = Boolean.valueOf(false);
                Player player = (Player)stream;
                if (stream instanceof com.yy.mshow.peripherals.playercenter.LANPlayer)
                  bool1 = Boolean.valueOf(true); 
                boolean bool = false;
                MonitorPanel.Portion[] arrayOfPortion = ((MonitorPanel)this.this$0.program.pgm.get()).getPortions();
                int i = arrayOfPortion.length;
                byte b1 = 0;
                while (true) {
                  boolean bool2 = bool;
                  if (b1 < i)
                    if ((arrayOfPortion[b1]).linkedSlot.get() == slot) {
                      bool2 = true;
                    } else {
                      b1++;
                      continue;
                    }  
                  param1List.add(makeChannel(b, player.userId(), Boolean.valueOf(bool2), Boolean.valueOf(false), bool1));
                  b++;
                  continue label21;
                } 
              } 
              continue;
            } 
          }
          
          private HashMap<String, Object> makeChannel(int param1Int1, int param1Int2, Boolean param1Boolean1, Boolean param1Boolean2, Boolean param1Boolean3) {
            HashMap hashMap = new HashMap();
            hashMap.put("channel", Integer.valueOf(param1Int1));
            hashMap.put("source", Integer.valueOf(param1Int2));
            hashMap.put("is_local", param1Boolean3);
            hashMap.put("is_in_pgm", param1Boolean1);
            hashMap.put("is_in_pvw", param1Boolean2);
            return hashMap;
          }
          
          protected void fill(List<Object> param1List) {
            addSlotChannels(param1List);
            addLocalMixerChannelIfNeeded(param1List);
          }
        }); }
  
  protected String url() { return String.format(Locale.CHINA, "/v1/programs/%d/channel_info/update", new Object[] { this.program.code.get() }); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\UpdateChannels.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */