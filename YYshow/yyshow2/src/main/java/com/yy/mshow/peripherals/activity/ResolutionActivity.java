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
import com.yy.mshow.cores.Program;
import com.yy.mshow.cores.Resolution;
import com.yy.mshow.peripherals.TitleCheckableViewHolder;
import java.util.Arrays;
import kits.observables.ObservableValue;

public class ResolutionActivity extends Activity {
  private BaseAdapter adapter = new BaseAdapter() {
      public int getCount() { return Program.resChoices.length; }
      
      public Object getItem(int param1Int) { return null; }
      
      public long getItemId(int param1Int) { return 0L; }
      
      public View getView(int param1Int, View param1View, ViewGroup param1ViewGroup) {
        String str;
        TitleCheckableViewHolder titleCheckableViewHolder2 = new TitleCheckableViewHolder();
        if (param1View == null) {
          titleCheckableViewHolder2.inflate(ResolutionActivity.this, 44);
        } else {
          titleCheckableViewHolder2.convert(param1View);
        } 
        Resolution resolution = Program.resChoices[param1Int];
        if (resolution.isWideScreen()) {
          str = "宽屏模式 ";
        } else {
          str = "";
        } 
        int i = Arrays.asList(Program.resChoices).indexOf(MShow.program.resolution.get());
        TitleCheckableViewHolder titleCheckableViewHolder1 = titleCheckableViewHolder2.setTitle(str + Resolution.makeString(resolution));
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
    this.listView.setAdapter(this.adapter);
    this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) { MShow.program.resolution.set(Program.resChoices[param1Int]); }
        });
  }
  
  private void setupNavigationBar() { this.navigationBar.setTitle("分辨率设置").setDisplayBackButton(true).setBackButtonImageResource(2130903040).setOnBackButtonClickListener(new View.OnClickListener() {
          public void onClick(View param1View) { ResolutionActivity.this.finish(); }
        }); }
  
  protected void onCreate(@Nullable Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2130968635);
    ButterKnife.bind(this);
    setupNavigationBar();
    setupListView();
    MShow.program.resolution.hook(this, true, new ObservableValue.Observer<Resolution>() {
          public void updated(boolean param1Boolean, Resolution param1Resolution1, Resolution param1Resolution2) { ResolutionActivity.this.adapter.notifyDataSetChanged(); }
        });
  }
  
  protected void onDestroy() {
    super.onDestroy();
    MShow.program.resolution.unhook(this);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\activity\ResolutionActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */