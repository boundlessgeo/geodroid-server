package org.geodroid.server;

import android.app.Fragment;
import android.util.AndroidRuntimeException;

public enum Page {
    STATUS(StatusPage.class, R.string.status), 
    //DATA(DataFragment.class), 
    LAYERS(LayersPage.class, R.string.layers),
    APPS(AppsPage.class, R.string.apps), 
    LOGS(LogsPage.class, R.string.logs),
    SETTINGS(SettingsPage.class, R.string.settings),
    GEOGIT(GeoGitPage.class, R.string.geogit)
    ;
    final Class<? extends Fragment> fragment;
    final int title;
    
    private Page(Class<? extends Fragment> fragment, int title) {
        this.fragment = fragment;
        this.title = title;
    }

    <T extends Fragment> T newFragment() {
        try {
            return (T) fragment.newInstance();
        } 
        catch(Exception e) {
            throw new AndroidRuntimeException(e);
        }
    }
}
