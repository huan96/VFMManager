package com.haui.huantd.vfmmanager;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Util {
    // Đọc nội dung text của một file nguồn.
    public static String readText(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String s = null;
        while ((s = br.readLine()) != null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String getThoiGian(String thoiGian) {
        if (thoiGian == null) {
            return "null";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.TIME_FORMMAT);
        Calendar cal = Calendar.getInstance();
        String time2 = dateFormat.format(cal.getTime());
        String day1 = thoiGian.substring(0, 10);
        String day2 = time2.substring(0, 10);
        if (!day1.equals(day2)) {
            return thoiGian.substring(0, 10);
        } else {
            return thoiGian.substring(11, 16);
        }
    }

    public static void rateApp(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    public static void shareApp(Context context) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                "http://play.google.com/store/apps/details?id="
                        + context.getPackageName());
        context.startActivity(shareIntent);
    }




}
