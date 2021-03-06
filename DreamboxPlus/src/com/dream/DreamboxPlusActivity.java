package com.dream;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class DreamboxPlusActivity extends TabActivity  {	
	    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Reusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, SettingsActivity.class);
        
        spec = tabHost.newTabSpec("settings").setIndicator("Settings",
                          res.getDrawable(R.drawable.ic_tab_ic_tab_settings))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, MessengerActivity.class);
        
        spec = tabHost.newTabSpec("messenger").setIndicator("Messenger",
                          res.getDrawable(R.drawable.ic_tab_ic_tab_message))
                      .setContent(intent);
    
        tabHost.addTab(spec);

        tabHost.setCurrentTab(1);
    }
}