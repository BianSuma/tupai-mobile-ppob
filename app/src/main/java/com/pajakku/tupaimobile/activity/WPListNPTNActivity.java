package com.pajakku.tupaimobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.pajakku.tupaimobile.R;
import com.pajakku.tupaimobile.adapter.list.WpNtpnAdapter;
import com.pajakku.tupaimobile.component.AppActionBar;
import com.pajakku.tupaimobile.model.WpNtpn;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class WPListNPTNActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wplist_ntpn);

        AppActionBar actionBar = findViewById(R.id.wplistntpn_actionbar);
        actionBar.init(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WPListNPTNActivity.this.finish();
            }
        }, null);

        // TODO: @test
        List<WpNtpn> items = new ArrayList<>();
        for(int i=0; i<10;i++) items.add(new WpNtpn("PPh 23 (411124)", "300", "Jun 2018", "Rp 200.000", "D363492749DSFSD23"));

        WpNtpnAdapter adapter = new WpNtpnAdapter(this, R.layout.row_wp_ntpn, items);

        ListView listView = findViewById(R.id.wplistntpn_listview);
        listView.setAdapter(adapter);
    }
}
