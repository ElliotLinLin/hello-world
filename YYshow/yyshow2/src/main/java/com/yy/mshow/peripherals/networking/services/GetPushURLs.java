package com.yy.mshow.peripherals.networking.services;

import com.google.gson.JsonElement;
import com.yy.mshow.cores.Program;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.methods.POST;

public class GetPushURLs extends Job {
  private final Completion completion;
  
  private final Program program;
  
  public GetPushURLs(Program paramProgram, Completion paramCompletion) {
    this.program = paramProgram;
    this.completion = paramCompletion;
  }
  
  protected Reactor.Method method() { return new POST(); }
  
  protected void process(Object paramObject) {
    ArrayList arrayList = new ArrayList();
    paramObject = ((JsonElement)paramObject).getAsJsonArray().iterator();
    while (paramObject.hasNext())
      arrayList.add(((JsonElement)paramObject.next()).getAsJsonObject().get("url").getAsString()); 
    this.completion.onSuccess(arrayList);
  }
  
  protected String url() { return String.format(Locale.CHINA, "/v1/programs/%d/get_push_urls_2", new Object[] { this.program.code.get() }); }
  
  public static interface Completion {
    void onSuccess(List<String> param1List);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\GetPushURLs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */