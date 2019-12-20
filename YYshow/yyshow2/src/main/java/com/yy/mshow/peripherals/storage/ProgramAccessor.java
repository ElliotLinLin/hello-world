package com.yy.mshow.peripherals.storage;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yy.mshow.cores.Program;
import com.yy.mshow.cores.RTMPOutputSetting;
import com.yy.mshow.peripherals.networking.YYChannel;
import java.util.Iterator;
import junit.framework.Assert;
import kits.PreferencesStorage;

public class ProgramAccessor implements PreferencesStorage.Store, PreferencesStorage.Load {
  private final Program program;
  
  public ProgramAccessor(Program paramProgram) { this.program = paramProgram; }
  
  public void decode(String paramString) {
    JsonObject jsonObject = (new JsonParser()).parse(paramString).getAsJsonObject();
    if (jsonObject.get("code") != null) {
      this.program.code.set(Integer.valueOf(jsonObject.get("code").getAsInt()));
      Assert.assertNotNull(jsonObject.get("number"));
      this.program.number.set(Integer.valueOf(jsonObject.get("number").getAsInt()));
    } 
    JsonElement jsonElement2 = jsonObject.get("yy_output");
    if (jsonElement2 != null) {
      Integer integer;
      Boolean bool2 = Boolean.valueOf(false);
      paramString = null;
      Boolean bool1 = null;
      JsonObject jsonObject1 = jsonElement2.getAsJsonObject();
      JsonElement jsonElement4 = jsonObject1.get("uid");
      if (jsonElement4 != null)
        integer = Integer.valueOf(jsonElement4.getAsInt()); 
      this.program.yyOutputSetting.uid.set(integer);
      jsonElement4 = jsonObject1.get("enabled");
      YYChannel yYChannel = bool2;
      if (jsonElement4 != null)
        yYChannel = Boolean.valueOf(jsonElement4.getAsBoolean()); 
      this.program.yyOutputSetting.enabled.set(yYChannel);
      JsonElement jsonElement3 = jsonObject1.get("channel_id");
      yYChannel = bool1;
      if (jsonElement3 != null) {
        Assert.assertNotNull(jsonObject1.get("channel_name"));
        yYChannel = new YYChannel(jsonElement3.getAsInt(), jsonObject1.get("channel_name").getAsString());
      } 
      this.program.yyOutputSetting.channel.set(yYChannel);
    } 
    this.program.rtmpOutputSettings.clear();
    JsonElement jsonElement1 = jsonObject.get("rtmp_outputs");
    if (jsonElement1 != null) {
      Iterator iterator = jsonElement1.getAsJsonArray().iterator();
      while (iterator.hasNext()) {
        JsonObject jsonObject1 = ((JsonElement)iterator.next()).getAsJsonObject();
        String str = jsonObject1.get("url").getAsString();
        boolean bool = jsonObject1.get("enabled").getAsBoolean();
        this.program.rtmpOutputSettings.add(new RTMPOutputSetting(str, Boolean.valueOf(bool)));
      } 
    } 
    jsonElement1 = jsonObject.get("rtmp_histories");
    if (jsonElement1 != null)
      for (JsonElement jsonElement : jsonElement1.getAsJsonArray())
        this.program.rtmpInputHistories.add(jsonElement.getAsString());  
  }
  
  public String encoded() {
    JsonObject jsonObject1 = new JsonObject();
    if (this.program.code.get() != null)
      jsonObject1.addProperty("code", (Number)this.program.code.get()); 
    if (this.program.number.get() != null)
      jsonObject1.addProperty("number", (Number)this.program.number.get()); 
    JsonObject jsonObject2 = new JsonObject();
    jsonObject2.addProperty("enabled", (Boolean)this.program.yyOutputSetting.enabled.get());
    if (this.program.yyOutputSetting.enabled.get() != null)
      jsonObject2.addProperty("enabled", (Boolean)this.program.yyOutputSetting.enabled.get()); 
    if (this.program.yyOutputSetting.uid.get() != null)
      jsonObject2.addProperty("uid", (Number)this.program.yyOutputSetting.uid.get()); 
    if (this.program.yyOutputSetting.channel.get() != null) {
      jsonObject2.addProperty("channel_id", Integer.valueOf(((YYChannel)this.program.yyOutputSetting.channel.get()).id));
      jsonObject2.addProperty("channel_name", ((YYChannel)this.program.yyOutputSetting.channel.get()).name);
    } 
    jsonObject1.add("yy_output", jsonObject2);
    JsonArray jsonArray = new JsonArray();
    for (RTMPOutputSetting rTMPOutputSetting : this.program.rtmpOutputSettings.get()) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("enabled", (Boolean)rTMPOutputSetting.enabled.get());
      jsonObject.addProperty("url", (String)rTMPOutputSetting.url.get());
      jsonArray.add(jsonObject);
    } 
    jsonObject1.add("rtmp_outputs", jsonArray);
    jsonArray = new JsonArray();
    Iterator iterator = this.program.rtmpInputHistories.get().iterator();
    while (iterator.hasNext())
      jsonArray.add((String)iterator.next()); 
    jsonObject1.add("rtmp_histories", jsonArray);
    return jsonObject1.toString();
  }
  
  public String guard() { return "{}"; }
  
  public String key() { return "program"; }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\storage\ProgramAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */