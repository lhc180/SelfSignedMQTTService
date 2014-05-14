/*
*                                  The MIT License (MIT)
*
* Copyright (c) 2014 - Manuel Domínguez Dorado <ingeniero@ManoloDominguez.com>
*
* Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
* associated documentation files (the "Software"), to deal in the Software without restriction,
* including without limitation the rights to use, copy, modify, merge, publish, distribute,
* sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
*     The above copyright notice and this permission notice shall be included in all copies or
*     substantial portions of the Software.
*
*     THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
*     BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
*     NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
*     DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
*     OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.manolodominguez.SelfSignedMQTTService.example;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.manolodominguez.SelfSignedMQTTService.R;
import com.manolodominguez.SelfSignedMQTTService.TSelfSignedMQTTService;


public class ExampleActivity extends Activity {

    TSelfSignedMQTTService mqttEnterpriseServiceBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!this.serviceIsRunning()) {
            final Intent intent = new Intent(this, TSelfSignedMQTTService.class);
            this.startService(intent);
        }
    }

    private boolean serviceIsRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (TSelfSignedMQTTService.SERVICE_CLASSNAME.equals(service.service.getClassName())) {
                Log.i("[ExampleActivity]", "Service is running");
                return true;
            }
        }
        Log.i("[ExampleActivity]", "Service is not running");
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        final Intent intent = new Intent(this, TSelfSignedMQTTService.class);
        this.stopService(intent);
        Log.i("[ExampleActivity]", "Stopping the service");
        super.onDestroy();
    }
}
