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

import android.util.Log;
import java.util.Date;

public class certCheck extends CordovaPlugin {
  private static final String TAG = "cdvRootBeer";

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    Log.d(TAG, "Initializing cdvRootBeer");    
  }
  private String fingerprint = "cdvRootBeer";

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {

    callbackContext.success(fingerprint);
    return true;
  }

}
