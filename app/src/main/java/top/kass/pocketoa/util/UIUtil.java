package top.kass.pocketoa.util;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import top.kass.pocketoa.R;

public class UIUtil {

    public static void showSnackBar(View view , String str, int length) {
        Snackbar snackbar = Snackbar.make(view, str, length);
        ((TextView) snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#FFFFFF"));
        snackbar.show();
    }

}
