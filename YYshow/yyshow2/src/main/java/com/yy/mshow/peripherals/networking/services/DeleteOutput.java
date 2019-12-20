package com.yy.mshow.peripherals.networking.services;

import com.yy.mshow.cores.Output;
import com.yy.mshow.cores.Program;
import com.yy.mshow.peripherals.networking.LiveOutput;
import java.util.Locale;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.methods.POST;

public class DeleteOutput extends Job {
  private final Output output;
  
  private final Program program;
  
  public DeleteOutput(Program paramProgram, Output paramOutput) {
    this.program = paramProgram;
    this.output = paramOutput;
  }
  
  private int outputId() {
    if (this.output instanceof LiveOutput)
      return ((LiveOutput)this.output).id.intValue(); 
    throw new RuntimeException("Not supported output type: " + this.output.getClass().getSimpleName());
  }
  
  protected Reactor.Method method() { return new POST(); }
  
  protected String url() { return String.format(Locale.CHINA, "/v1/programs/%d/outputs/%d/delete", new Object[] { this.program.code.get(), Integer.valueOf(outputId()) }); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\DeleteOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */