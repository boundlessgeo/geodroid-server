package org.geodroid.server;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ErrorDialog {

    public static void show(final Throwable e, final Context context) {
        AlertDialog.Builder db = new AlertDialog.Builder(context);
        db.setIcon(R.drawable.ic_dialog_alert_holo_dark);
        db.setTitle(R.string.oops);
        db.setMessage(R.string.error_occurred);
        db.setPositiveButton(android.R.string.ok, null);
        db.setNeutralButton(android.R.string.copy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClipboardManager clipMgr = 
                    (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipMgr.setPrimaryClip(ClipData.newPlainText("error", ErrorDialog.toString(e)));
            }
        });

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_error, null);
        db.setView(view);

        TextView stackText = (TextView) view.findViewById(R.id.error_stack);
        stackText.setText(toString(e));

        db.create().show();
    }

    static String toString(Throwable e) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(bout));
        return new String(bout.toByteArray());
    }
}

