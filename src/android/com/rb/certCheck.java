/**
 */
package com.rb;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Application;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;

import static java.security.CryptoPrimitive.SIGNATURE;

public class certCheck extends CordovaPlugin {
  private static final String TAG = "cdvRootBeer";
  private static final String APP_SIGNATURE = "1038C0E34658923C4192E61B16846";
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    Log.d(TAG, "Initializing cdvRootBeer");
  }
  private String fingerprint = "cdvRootBeer";

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
      Context context = this.cordova.getActivity().getApplicationContext();

      String strResult = "";
      PackageInfo packageInfo = null;
      try {
          Log.d("key", context.getPackageName());
          packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
          //note sample just checks the first signature
          for (Signature signature : packageInfo.signatures) {
              // SHA1 the signature
              String sha1 = getSHA1(signature.toByteArray());
              // check is matches hardcoded value
              Log.d("key", sha1);
              callbackContext.success(sha1);
          }

      } catch (NameNotFoundException e) {
          e.printStackTrace();
          callbackContext.error("catch");
      }

    return true;
  }


    protected static String doFingerprint(byte[] certificateBytes, String algorithm)
            throws Exception {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(certificateBytes);
        byte[] digest = md.digest();

        String toRet = "";
        for (int i = 0; i < digest.length; i++) {
            if (i != 0)
                toRet += ":";
            int b = digest[i] & 0xff;
            String hex = Integer.toHexString(b);
            if (hex.length() == 1)
                toRet += "0";
            toRet += hex;
        }
        return toRet;
    }

  //computed the sha1 hash of the signature
  public static String getSHA1(byte[] sig) {
      MessageDigest digest = null;
      try {
          digest = MessageDigest.getInstance("SHA1");
      } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
      }
      digest.update(sig);
    byte[] hashtext = digest.digest();
    return bytesToHex(hashtext);
}

//util method to convert byte array to hex string
public static String bytesToHex(byte[] bytes) {
  final char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
      '9', 'A', 'B', 'C', 'D', 'E', 'F' };
  char[] hexChars = new char[bytes.length * 2];
  int v;
  for (int j = 0; j < bytes.length; j++) {
    v = bytes[j] & 0xFF;
    hexChars[j * 2] = hexArray[v >>> 4];
    hexChars[j * 2 + 1] = hexArray[v & 0x0F];
  }
  return new String(hexChars);
}

}

class getContext extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        Log.d("plugin", "App started");
        super.onCreate();
        appContext = getApplicationContext();

        /* If you has other classes that need context object to initialize when application is created,
         you can use the appContext here to process. */
    }

    public static Context getAppContext() {
        return appContext;
    }
}
