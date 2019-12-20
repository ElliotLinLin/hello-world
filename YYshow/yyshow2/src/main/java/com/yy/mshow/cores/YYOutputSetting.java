package com.yy.mshow.cores;

import com.yy.mshow.peripherals.networking.YYChannel;
import java.util.Locale;
import kits.observables.ObservableValue;

public final class YYOutputSetting implements OutputSetting {
  public final ObservableValue<YYChannel> channel = new ObservableValue(null);
  
  public final ObservableValue<Boolean> enabled = new ObservableValue(Boolean.valueOf(false));
  
  public final ObservableValue<Integer> uid = new ObservableValue(null);
  
  public String toString() {
    Locale locale = Locale.CHINA;
    ObservableValue observableValue1 = this.enabled;
    ObservableValue observableValue2 = this.uid;
    if (this.channel.get() == null) {
      Object object = null;
      return String.format(locale, "(YYOutputSetting %s, %s, %s)", new Object[] { observableValue1, observableValue2, object });
    } 
    Integer integer = Integer.valueOf(((YYChannel)this.channel.get()).id);
    return String.format(locale, "(YYOutputSetting %s, %s, %s)", new Object[] { observableValue1, observableValue2, integer });
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\cores\YYOutputSetting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */