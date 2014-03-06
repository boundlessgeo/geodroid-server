package org.geodroid.server;

import java.util.Date;
import java.util.Iterator;

import org.geogit.api.GeoGIT;
import org.geogit.api.Remote;
import org.geogit.api.RevCommit;
import org.geogit.api.porcelain.CheckoutOp;
import org.geogit.api.porcelain.CheckoutResult;
import org.geogit.api.porcelain.LogOp;
import org.geogit.api.porcelain.PullOp;
import org.geogit.api.porcelain.PullResult;
import org.geogit.api.porcelain.PushOp;
import org.geogit.api.porcelain.RemoteAddOp;
import org.geogit.api.porcelain.RemoteListOp;
import org.geogit.api.porcelain.RemoteRemoveOp;
import org.geogit.api.porcelain.SynchronizationException;
import org.jeo.data.Handle;
import org.jeo.geogit.GeoGitWorkspace;
import org.ocpsoft.pretty.time.PrettyTime;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GeoGitPage extends PageFragment {

    View progress;

    @Override
    protected void doCreateView(LayoutInflater inflater, ViewGroup container,
            Preferences p, Bundle state) {
        
        View view = inflater.inflate(R.layout.page_geogit, container);

        progress = view.findViewById(R.id.geogit_progress);
        progress.setVisibility(View.INVISIBLE);

        ViewGroup tableRoot = (ViewGroup) view.findViewById(R.id.geogit_table_root);
        TableLayout table = (TableLayout) 
            inflater.inflate(R.layout.geogit_table, tableRoot).findViewById(R.id.geogit_table);

        new LoadRepos(table).execute();
    }

    void createTableRow(GeoGitWorkspace ws, TableLayout table) {
//        ViewGroup table = (ViewGroup) getView().findViewById(R.id.geogit_table_root);
//        if (table == null) {
//            return;
//        }

        final GeoGIT gg = ws.getGeoGIT();

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View row = inflater.inflate(R.layout.geogit_table_row, null);

        TextView titleText = (TextView) row.findViewById(R.id.geogit_table_name);
        titleText.setText(gg.getPlatform().pwd().getName());

        TextView lastmod = (TextView) row.findViewById(R.id.geogit_table_lastmod);
        lastmod.setText(lastModified(gg));
        
        Remote origin = findOrigin(gg);
        //final String url = String.format("http://localhost:%d/apps/%s/", p.getPort(), appDir.getName());

        final TextView originText = (TextView) row.findViewById(R.id.geogit_table_origin);
        updateOriginText(originText, origin);

        originText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Remote origin = findOrigin(gg);
                Context context = getActivity();

                final EditText input = new EditText(context);
                if (origin != null) {
                    input.getText().append(origin.getFetchURL());
                }

                new AlertDialog.Builder(context)
                    .setTitle("Set Origin")
                    .setMessage("Specify the origin url")
                    .setView(input)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String url = input.getText().toString();
                            updateOriginText(originText, setOrigin(gg, url));
                        }
                    }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();
            }
        });

        final TextView syncStatus = (TextView) row.findViewById(R.id.geogit_table_sync_msg);
        syncStatus.setVisibility(View.INVISIBLE);

        final View syncProgress = row.findViewById(R.id.geogit_sync_progress);
        syncProgress.setVisibility(View.GONE);

        final ImageView syncImg = (ImageView) row.findViewById(R.id.geogit_table_sync);
        syncImg.setClickable(origin != null);
        syncImg.setVisibility(origin != null ? View.VISIBLE : View.GONE);
        syncImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Remote origin = findOrigin(gg);
                if (origin != null) {
                    new SyncRepo(syncImg, syncProgress, syncStatus).execute(gg, origin);
                }
            }
        });

        row.requestLayout();
        table.addView(row);

        TableRow div = 
                (TableRow) getActivity().getLayoutInflater().inflate(R.layout.layers_table_div, null);
        table.addView(div);
    }

    Remote findOrigin(GeoGIT gg) {
        for (Remote r : gg.command(RemoteListOp.class).call()) {
            if ("origin".equalsIgnoreCase(r.getName())) {
                return r;
            }
        }

        return null;
    }

    Remote setOrigin(GeoGIT gg, String url) {
        Remote origin = findOrigin(gg);
        if (origin != null) {
            gg.command(RemoteRemoveOp.class).setName("origin").call();
        }

        return gg.command(RemoteAddOp.class).setName("origin").setURL(url).call();
    }

    String lastModified(GeoGIT gg) {
        Iterator<RevCommit> commits = gg.command(LogOp.class).setLimit(1).call();
        if (commits.hasNext()) {
            RevCommit last = commits.next();
            return "Last modified " + new PrettyTime().format(new Date(last.getAuthor().getTimestamp()));
        }
        return "";
    }

    void updateOriginText(TextView originText, Remote origin) {
        if (origin != null) {
            String url = origin.getFetchURL();
            originText.setText(url);
            originText.setBackgroundResource(R.drawable.text_clickable);
        }
        else {
            originText.setBackgroundResource(android.R.drawable.ic_menu_add);
            originText.setClickable(true);
        }
    }
    class LoadRepos extends AsyncTask<Void, Void, Exception> {

        TableLayout table;
    
        public LoadRepos(TableLayout table) {
            this.table = table;
        }
    
        @Override
        protected void onPreExecute() {
            progress.setVisibility(View.VISIBLE);
        }
    
        @Override
        protected void onPostExecute(Exception result) {
            progress.setVisibility(View.INVISIBLE);
            if (result != null) {
                ErrorDialog.show(result, getActivity());
            }
        }
    
        @Override
        protected Exception doInBackground(Void... params) {
            try {
                for (Handle<?> h : getDataRepository().list()) {
                    if (!GeoGitWorkspace.class.isAssignableFrom(h.getDriver().getType())) {
                        continue;
                    }
    
                    final GeoGitWorkspace ws = (GeoGitWorkspace) h.resolve();
                    try {
                        getView().post(new Runnable() {
                            @Override
                            public void run() {
                                createTableRow(ws, table);
                            }
                        });
                    }
                    finally {
                        h.close();
                    }
                }
            }
            catch(Exception e) {
                return e;
            }
            return null;
        }
    
    }

    class SyncRepo extends AsyncTask<Object, Void, Throwable> {

        View syncImg;
        View syncProgress;
        TextView syncStatus;

        SyncRepo(View syncImg, View syncProgress, TextView syncStatus) {
            this.syncImg = syncImg;
            this.syncProgress = syncProgress;
            this.syncStatus = syncStatus;
        }

        @Override
        protected void onPreExecute() {
            syncImg.setVisibility(View.GONE);
            syncProgress.setVisibility(View.VISIBLE);
            syncStatus.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPostExecute(Throwable result) {
            syncImg.setVisibility(View.VISIBLE);
            syncProgress.setVisibility(View.GONE);
            if (result != null) {
                ErrorDialog.show(result, getActivity());
                syncStatus.setText(R.string.sync_failed);
            }
            else {
                syncStatus.setText(R.string.sync_success);
            }
            syncStatus.setVisibility(View.VISIBLE);
        }

        @Override
        protected Throwable doInBackground(Object... params) {
            try {
                GeoGIT gg = (GeoGIT) params[0];
                Remote origin = (Remote) params[1];

                CheckoutResult cr = gg.command(CheckoutOp.class).setSource("master").call();
                PullResult pr = gg.command(PullOp.class).setRemote("origin").call();
                gg.command(PushOp.class).setRemote("origin").call();
                gg.command(CheckoutOp.class).setSource("master").call();
            }
            catch(SynchronizationException e) {
                switch(e.statusCode) {
                case NOTHING_TO_PUSH:
                    // ok
                    break;
                case HISTORY_TOO_SHALLOW:
                case REMOTE_HAS_CHANGES:
                    return e;
                }
            }
            catch(Throwable t) {
                return t;
            }
            return null;
        }
    }
}
