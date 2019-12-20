package com.yy.mshow.peripherals.networking.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yy.mshow.cores.Program;
import com.yy.mshow.peripherals.playercenter.RTMPSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import junit.framework.Assert;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.methods.GET;

public class GetProgramSources extends Job {
  private final Completion completion;
  
  private final Program program;
  
  public GetProgramSources(Program paramProgram, Completion paramCompletion) {
    Assert.assertNotNull(paramProgram.code.get());
    this.completion = paramCompletion;
    this.program = paramProgram;
  }
  
  protected Reactor.Method method() { return new GET(); }
  
  protected void process(Object paramObject) {
    ArrayList arrayList = new ArrayList();
    paramObject = ((JsonElement)paramObject).getAsJsonArray();
    for (byte b = 0; b < paramObject.size(); b++) {
      JsonObject jsonObject = paramObject.get(b).getAsJsonObject();
      String str1 = jsonObject.get("type").getAsString();
      int i = jsonObject.get("user_id").getAsInt();
      String str2 = jsonObject.get("url").getAsString();
      if (str1.equals("rtmp-anchor"))
        arrayList.add(RTMPSource.restore(Integer.valueOf(i).intValue(), str2)); 
    } 
    this.completion.onSuccess(arrayList);
  }
  
  protected String url() { return String.format(Locale.CHINA, "/v1/programs/%d/sources/list", new Object[] { this.program.code.get() }); }
  
  public static interface Completion {
    void onSuccess(List<RTMPSource> param1List);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\GetProgramSources.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */