package com.seedotech.sdtaidlclient;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seedotech.sdtaidlserver.ISdtUtils;

public class MainActivity extends AppCompatActivity {
    private EditText mNum1;
    private EditText mNum2;
    private EditText mResult;

    private ISdtUtils mUtilsService;
    private ServiceConnection mUtilsServiceConnection;

    private void initConnection() {
        mUtilsServiceConnection = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mUtilsService = null;
                Toast.makeText(getApplicationContext(), "SdtUtils Service Disconnected",
                        Toast.LENGTH_SHORT).show();
                Log.d("[RAYCAD] IRemote: ", "Binding - Service disconnected");
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mUtilsService = ISdtUtils.Stub.asInterface((IBinder) service);
                Toast.makeText(getApplicationContext(),
                        "SdtUtils Service Connected", Toast.LENGTH_SHORT)
                        .show();
                Log.d("[RAYCAD] IRemote: ", "Binding is done - Service connected");
            }
        };

        if (mUtilsService == null) {
            Intent it = new Intent();
            it.setAction("service.SdtUtils");
            // Binding to remote service
            bindService(it, mUtilsServiceConnection, Service.BIND_AUTO_CREATE);
        }
    }

    protected void init() {
        mNum1 = (EditText)findViewById(R.id.edt_num1);
        mNum2 = (EditText)findViewById(R.id.edt_num2);
        mResult = (EditText)findViewById(R.id.edt_result);

        Button btn = (Button)findViewById(R.id.btn_calculate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num1 = Integer.parseInt(mNum1.getText().toString());
                int num2 = Integer.parseInt(mNum2.getText().toString());
                int result = 0;

                try {
                    result = mUtilsService.add(num1, num2);
                    mResult.setText(String.format("%d", result));
                    Log.d("IRemote", "Binding - Add operation");
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Create the service connection
        initConnection();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        init();
    }

    protected void onDestroy() {
        // Unbind the service
        unbindService(mUtilsServiceConnection);
        super.onDestroy();
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
