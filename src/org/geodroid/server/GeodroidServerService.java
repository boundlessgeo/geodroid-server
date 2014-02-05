package org.geodroid.server;

import static org.geodroid.server.GeodroidServer.TAG;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.geodroid.app.GeoApplication;
import org.jeo.android.graphics.Renderer;
import org.jeo.data.DataRepositoryView;
import org.jeo.map.Map;
import org.jeo.map.View;
import org.jeo.nano.AppsHandler;
import org.jeo.nano.FeatureHandler;
import org.jeo.nano.Handler;
import org.jeo.nano.MapRenderer;
import org.jeo.nano.NanoServer;
import org.jeo.nano.StyleHandler;
import org.jeo.nano.TileHandler;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
//import android.support.v4.app.NotificationCompat;
//import android.support.v4.app.TaskStackBuilder;
//import android.support.v4.app.NotificationCompat;
//import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import java.util.Properties;
import org.jeo.nano.NanoHTTPD;

public class GeodroidServerService extends Service {

    DataRepositoryView repo;
    NanoServer server;

    @Override
    public void onCreate() {
        Preferences p = new Preferences(this);

        FilesHelper.ensureFilesExist(this);

        LocationManager locMgr = 
            (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        List<Handler> handlers = new ArrayList<Handler>();
        handlers.add(new RootHandler(this));
        handlers.add(new CurrentLocationHandler(locMgr));
        handlers.add(new TileHandler());
        handlers.add(new FeatureHandler( new MapRenderer() {
            @Override
            public void render(Map map, OutputStream out) throws IOException {
                View view = map.getView();
                final Bitmap img = 
                    Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);

                Renderer r = new Renderer(new Canvas(img));
                r.init(view);
                r.render();

                img.compress(Bitmap.CompressFormat.PNG, 90, out);
            }
        }));

        handlers.add(new StyleHandler());
        handlers.add(new AppsHandler(p.getAppsDirectory()));

        // to enable tracing, set the tag level to DEBUG:
        // $ adb shell setprop log.tag.GeodroidServerTracing DEBUG
        // the service must be restarted to take effect
        final boolean enableTracing = Log.isLoggable(TAG + "Tracing", Log.DEBUG);

        try {
            repo = GeoApplication.get(this).createDataRepository();
            server = new NanoServer(p.getPort(), p.getWebDirectory(), p.getNumThreads(), repo, handlers) {

                @Override
                protected void notifyStarted() {
                    GeodroidServerService.this.notifyStarted();
                }

                @Override
                protected void notifyStopped() {
                    GeodroidServerService.this.notifyStopped();
                }

                @Override
                public NanoHTTPD.Response serve(String uri, String method, Properties header, Properties parms, Properties files) {
                    if (enableTracing) {
                        Debug.startMethodTracing(TAG, 64*1024*1024);
                    }
                    NanoHTTPD.Response response = super.serve(uri, method, header, parms, files);
                    if (enableTracing) {
                        Debug.stopMethodTracing();
                    }
                    return response;
                }

            };
        }
        catch(IOException e) {
            Log.wtf(TAG, "NanoHTTPD did not start", e);
        }
    }

    private void notifyStarted() {
        Preferences p = new Preferences(this);
        
        Intent intent = new Intent(Intent.ACTION_VIEW, 
            Uri.parse(String.format("http://localhost:%d/", p.getPort())));
        
        PendingIntent pending = 
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Resources res = getResources();
        Notification.Builder nBuilder = new Notification.Builder(this)
            .setContentTitle(res.getText(R.string.app_name))
            .setContentText(res.getText(R.string.server_online))
            .setSmallIcon(R.drawable.ic_stat_service).setContentIntent(pending);

        NotificationManager nMgr = 
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.notify(1, nBuilder.getNotification());
        Log.i(TAG, "GeoDroid Server started");

        GeodroidServer.get(GeodroidServerService.this).setStatus(GeodroidServer.Status.ONLINE);
    }

    private void notifyStopped() {
        Resources res = getResources();
        Notification.Builder nBuilder = new Notification.Builder(this)
            .setContentTitle(res.getText(R.string.app_name_short))
            .setContentText(res.getText(R.string.server_offline))
            .setSmallIcon(R.drawable.ic_stat_service);
    
        NotificationManager nMgr = 
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.notify(2, nBuilder.getNotification());
        Log.i(TAG, "GeoDroid Server stopped");

        GeodroidServer.get(GeodroidServerService.this).setStatus(GeodroidServer.Status.OFFLINE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (server != null) {
            server.stop();
        }

        repo.close();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }
}
