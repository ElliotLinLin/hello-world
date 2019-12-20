package com.yy.mshow.peripherals.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.chenglei1986.navigationbar.NavigationBar;
import com.yy.mshow.cores.Bitrate;
import com.yy.mshow.cores.Program;
import com.yy.mshow.peripherals.TitleCheckableViewHolder;
import java.util.Arrays;
import kits.observables.ObservableValue;

public class BitrateActivity extends Activity {
  private BaseAdapter adapter = new BaseAdapter() {
      public int getCount() { return Program.bitrateChoices.length; }
      
      public Object getItem(int param1Int) { return null; }
      
      public long getItemId(int param1Int) { return 0L; }
      
      public View getView(int param1Int, View param1View, ViewGroup param1ViewGroup) {
        TitleCheckableViewHolder titleCheckableViewHolder2 = new TitleCheckableViewHolder();
        if (param1View == null) {
          titleCheckableViewHolder2.inflate(BitrateActivity.this, 44);
        } else {
          titleCheckableViewHolder2.convert(param1View);
        } 
        Bitrate bitrate = Program.bitrateChoices[param1Int];
        int i = Arrays.asList(Program.bitrateChoices).indexOf(MShow.program.bitrate.get());
        TitleCheckableViewHolder titleCheckableViewHolder1 = titleCheckableViewHolder2.setTitle(Bitrate.makeString(bitrate));
        if (param1Int == i) {
          boolean bool1 = true;
          titleCheckableViewHolder1.setChecked(bool1);
          return titleCheckableViewHolder2.root;
        } 
        boolean bool = false;
        titleCheckableViewHolder1.setChecked(bool);
        return titleCheckableViewHolder2.root;
      }
    };
  
  @BindView(2131624150)
  ListView listView;
  
  @BindView(2131624149)
  NavigationBar navigationBar;
  
  private void setupListView() {
    MShow.program.bitrate.hook(this, false, new ObservableValue.Observer<Bitrate>() {
          public void updated(boolean param1Boolean, Bitrate param1Bitrate1, Bitrate param1Bitrate2) { BitrateActivity.this.adapter.notifyDataSetChanged(); }
        });
    this.listView.setAdapter(this.adapter);
    this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) { MShow.program.bitrate.set(Program.bitrateChoices[param1Int]); }
        });
  }
  
  private void setupNavigationBar() { this.navigationBar.setTitle("码率设置").setDisplayBackButton(true).setBackButtonImageResource(2130903040).setOnBackButtonClickListener(new View.OnClickListener() {
          public void onClick(View param1View) { BitrateActivity.this.finish(); }
        }); }
  
  protected void onCreate(@Nullable Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2130968635);
    ButterKnife.bind(this);
    setupNavigationBar();
    setupListView();
  }
  
  protected void onDestroy() {
    super.onDestroy();
    MShow.program.bitrate.unhook(this);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\activity\BitrateActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */