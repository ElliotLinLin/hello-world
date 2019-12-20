package com.yy.mshow.peripherals.networking.services;

import com.yy.mshow.cores.Bitrate;
import com.yy.mshow.cores.OutputSetting;
import com.yy.mshow.cores.Program;
import com.yy.mshow.cores.RTMPOutputSetting;
import com.yy.mshow.cores.Resolution;
import com.yy.mshow.cores.YYOutputSetting;
import com.yy.mshow.peripherals.networking.YYChannel;
import java.util.Locale;
import java.util.Map;
import junit.framework.Assert;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.bodies.JsonObjectBody;
import kits.reactor.methods.POST;

public final class CreateOutput extends Job {
  private static Integer entId;
  
  private final Program program;
  
  private final OutputSetting setting;
  
  public CreateOutput(Program paramProgram, OutputSetting paramOutputSetting) {
    this.program = paramProgram;
    this.setting = paramOutputSetting;
  }
  
  public static void init(int paramInt) { entId = Integer.valueOf(paramInt); }
  
  protected Reactor.Method method() { return new POST(new JsonObjectBody(this) {
          private void fill(Map<String, Object> param1Map, String param1String1, String param1String2, String param1String3, Boolean param1Boolean) {
            param1Map.put("name", param1String1);
            param1Map.put("type", param1String2);
            param1Map.put("enabled", param1Boolean);
            param1Map.put("url", param1String3);
          }
          
          private void fillRTMPOutput(Map<String, Object> param1Map, RTMPOutputSetting param1RTMPOutputSetting) { fill(param1Map, "RTMP输出", "rtmp", (String)param1RTMPOutputSetting.url.get(), (Boolean)param1RTMPOutputSetting.enabled.get()); }
          
          private void fillYYOutput(Map<String, Object> param1Map, YYOutputSetting param1YYOutputSetting) {
            Integer integer1 = Integer.valueOf(((YYChannel)param1YYOutputSetting.channel.get()).id);
            Integer integer2 = (Integer)param1YYOutputSetting.uid.get();
            Assert.assertNotNull(integer1);
            Assert.assertNotNull(integer2);
            fill(param1Map, "YY输出", "YY", String.format(Locale.CHINA, "%d/%d/%d/%d/%d?reportstatus=0&transcode=1&fullscreen=1", new Object[] { CreateOutput.access$200(), integer2, integer1, integer1, integer1 }), (Boolean)param1YYOutputSetting.enabled.get());
          }
          
          protected void fill(Map<String, Object> param1Map) {
            param1Map.put("video_bitrate", Bitrate.makeString((Bitrate)this.this$0.program.bitrate.get()));
            param1Map.put("video_resolution", Resolution.makeString((Resolution)this.this$0.program.resolution.get()));
            param1Map.put("fullscreen", Boolean.valueOf(true));
            param1Map.put("transcode", Boolean.valueOf(true));
            param1Map.put("video_framerate", "25");
            param1Map.put("audio_bitrate", "24K");
            if (CreateOutput.this.setting instanceof RTMPOutputSetting) {
              fillRTMPOutput(param1Map, (RTMPOutputSetting)CreateOutput.this.setting);
              return;
            } 
            if (CreateOutput.this.setting instanceof YYOutputSetting) {
              fillYYOutput(param1Map, (YYOutputSetting)CreateOutput.this.setting);
              return;
            } 
            throw new RuntimeException("暂不支持的输出设置类型: " + CreateOutput.this.setting.getClass().getSimpleName());
          }
        }); }
  
  protected String url() { return String.format(Locale.CHINA, "/v1/programs/%d/outputs/create", new Object[] { this.program.code.get() }); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\CreateOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */