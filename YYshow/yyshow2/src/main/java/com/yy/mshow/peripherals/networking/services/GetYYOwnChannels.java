package com.yy.mshow.peripherals.networking.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yy.mshow.peripherals.networking.YYChannel;
import java.util.ArrayList;
import java.util.Locale;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.methods.GET;

public class GetYYOwnChannels extends Job {
  private final Completion completion;
  
  private final int uid;
  
  public GetYYOwnChannels(int paramInt, Completion paramCompletion) {
    this.uid = paramInt;
    this.completion = paramCompletion;
  }
  
  protected Reactor.Method method() { return new GET(); }
  
  protected void process(Object paramObject) {
    ArrayList arrayList = new ArrayList();
    paramObject = ((JsonElement)paramObject).getAsJsonObject();
    JsonObject jsonObject = paramObject.get("key_index").getAsJsonObject();
    int i = jsonObject.get("sid").getAsInt();
    int j = jsonObject.get("name").getAsInt();
    paramObject = paramObject.get("dataset").getAsJsonArray().iterator();
    while (paramObject.hasNext()) {
      JsonArray jsonArray = ((JsonElement)paramObject.next()).getAsJsonArray();
      if (jsonArray.size() > 2) {
        int k = Integer.parseInt(jsonArray.get(Integer.valueOf(i).intValue()).getAsString());
        String str = jsonArray.get(Integer.valueOf(j).intValue()).getAsString();
        arrayList.add(new YYChannel(Integer.valueOf(k).intValue(), str));
      } 
    } 
    this.completion.onSuccess(arrayList);
  }
  
  protected String url() { return String.format(Locale.CHINA, "/v1/yyuser/uid/%d/session_list", new Object[] { Integer.valueOf(this.uid) }); }
  
  public static interface Completion {
    void onSuccess(ArrayList<YYChannel> param1ArrayList);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\GetYYOwnChannels.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */