package org.geodroid.server;

import org.geodroid.server.GeodroidServer.Status;
import org.jeo.data.DataRepositoryView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("NewApi")
public class GeodroidServerActivity extends Activity implements NavFragment.Callbacks, 
    GeodroidServer.StatusCallback {

    final int TAG_STATUS = 10;
    
    /**
     * tablet vs handset mode
     */
    boolean mTwoPane;

    /** data registry */
    DataRepositoryView repo;

    Button status;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        //initialize preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        
        setContentView(R.layout.activity_nav);
    
        if (findViewById(R.id.detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
    
            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((NavFragment) getFragmentManager().findFragmentById(
                    R.id.nav)).setActivateOnItemClick(true);
        }
    
        GeodroidServer app = app();
        repo = app.createDataRepository();
        app.bind(this);

        FilesHelper.ensureFilesExist(this);
        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * callback to update details pane when navigation changed.
     */
    @Override
    public void onItemSelected(Page page) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            
            Fragment frag = page.newFragment();
            getFragmentManager().beginTransaction().replace(R.id.detail_container, frag).commit();
    
        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, DetailActivity.class);
            detailIntent.putExtra(DetailActivity.ARG_PAGE, page);
            startActivity(detailIntent);
        }
    }

    @Override
    public void onStatusUpdate(final Status s) {
        if (status != null) {
            status.post(new Runnable() {
                @Override
                public void run() {
                    status.setTag(R.id.status_tag, s);
                    status.setText(s.name());
                    status.setEnabled(true);

                    switch(s) {
                    case ONLINE:
                        status.setBackgroundResource(R.drawable.status_btn_on);
                        break;
                    case OFFLINE:
                        status.setBackgroundResource(R.drawable.status_btn_off);
                        break;
                    case ERROR:
                        status.setBackgroundResource(R.drawable.status_btn_err);
                        break;
                    default:
                        status.setEnabled(false);
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        
        initStatusMenuItem(menu);
        initAboutMenuItem(menu);
        //initSettingsMenuItem(menu);

        return true;
    }

    void initStatusMenuItem(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_status);

        status = new Button(this);
        status = new Button(this, null, R.style.TextSmallBold);
        onStatusUpdate(GeodroidServer.get(this).getStatus());

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status.setEnabled(false);
                //status.setBackgroundResource(R.drawable.status_btn_disabled);
                //status.setTextColor(getResources().getColor(R.color.text_darker));

                Status s = (Status) v.getTag(R.id.status_tag);
                if (s != null) {
                    switch(s) {
                    case ONLINE:
                    case ERROR:
                        app().stop();
                        break;
                    case OFFLINE:
                        app().start();
                    }
                }
            }
        });

        item.setActionView(status);
    }

    void initAboutMenuItem(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_about);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GeodroidServerActivity.this);
                builder.setTitle(R.string.about_geodroid);
                builder.setPositiveButton(android.R.string.ok, null);

                BuildInfo bi = GeodroidServer.getBuildInfo(GeodroidServerActivity.this);

                View dialogView = getLayoutInflater().inflate(R.layout.dialog_about, null);
                TextView buildInfo = (TextView) dialogView.findViewById(R.id.about_build_info);
                buildInfo.setText(String.format(getResources().getString(R.string.build_info), 
                    bi.getVersion(), bi.getBuildRevShort(), bi.getBuildTimeHuman()));

                builder.setView(dialogView);

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });
    }
//    void initSettingsMenuItem(Menu menu) {
//        MenuItem item = menu.findItem(R.id.menu_settings);
//        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                // Display the fragment as the main content.
//                
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.detail_container, new SettingsFragment())
//                        .commit();
//                return true;
//            }
//            
//        });
//    }


    public DataRepositoryView getDataRepository() {
        return repo;
    }

    @Override
    protected void onDestroy() {
        repo.close();
        GeodroidServer.get(this).unbind(this);
        super.onDestroy();
    }

    GeodroidServer app() {
        return GeodroidServer.get(this);
    }
}
