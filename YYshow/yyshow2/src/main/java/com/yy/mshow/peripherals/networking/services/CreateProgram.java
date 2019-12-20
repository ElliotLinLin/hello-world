package com.yy.mshow.peripherals.networking.services;

import com.google.gson.JsonElement;
import java.util.Map;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.bodies.JsonObjectBody;
import kits.reactor.methods.POST;

public final class CreateProgram extends Job {
  private final Completion completion;
  
  public CreateProgram(Completion paramCompletion) { this.completion = paramCompletion; }
  
  protected Reactor.Method method() { return new POST(new JsonObjectBody(this) {
          protected void fill(Map<String, Object> param1Map) { param1Map.put("program_name", "Android Program"); }
        }); }
  
  protected void process(Object paramObject) {
    int i = ((JsonElement)paramObject).getAsJsonObject().get("program_id").getAsInt();
    this.completion.onSuccess(Integer.valueOf(i));
  }
  
  protected String url() { return "/v1/programs/create"; }
  
  public static interface Completion {
    void onSuccess(Integer param1Integer);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\CreateProgram.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */