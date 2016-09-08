package uk.co.alt236.btlescan.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconDevice;
import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconManufacturerData;
import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.adapters.LeDeviceListAdapter;
import uk.co.alt236.btlescan.containers.BluetoothLeDeviceStore;
import uk.co.alt236.btlescan.util.BluetoothLeScanner;
import uk.co.alt236.btlescan.util.BluetoothUtils;
import uk.co.alt236.btlescan.util.Constants;
import uk.co.alt236.easycursor.objectcursor.EasyObjectCursor;


public class ScanActivity extends AppCompatActivity {

    TextView showname;
    TextView showdistance;
    Button scan;
    boolean isScan = false;

    private BluetoothUtils mBluetoothUtils;
    private BluetoothLeScanner mScanner;
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private BluetoothLeDeviceStore mDeviceStore;
    IBeaconManufacturerData iBeaconData;
    IBeaconDevice iBeacon;
    private final BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {

            final BluetoothLeDevice deviceLe = new BluetoothLeDevice(device, rssi, scanRecord, System.currentTimeMillis());
            mDeviceStore.addDevice(deviceLe);
            final EasyObjectCursor<BluetoothLeDevice> c = mDeviceStore.getDeviceCursor();
            try{
                iBeaconData = new IBeaconManufacturerData(deviceLe);
                iBeacon = new IBeaconDevice(deviceLe);
            }catch (Exception e){iBeaconData= null;}
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(iBeaconData != null) {
                    /*    Bundle extras = getIntent().getExtras();
                        Integer position = extras.getInt("position");*/



                        String[] res = DB.selectbeacon(getApplicationContext(),device.getAddress());
                        if(res[0]!=null) {
                            showname.setText(res[0]);
                            showdistance.setText(Constants.DOUBLE_TWO_DIGIT_ACCURACY.format(iBeacon.getAccuracy()));
                            Log.i("RES", "" + res[0]);
                            Log.i("UUID", device.getAddress());
                        }
                    }
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mBluetoothUtils = new BluetoothUtils(this);
        mScanner = new BluetoothLeScanner(mLeScanCallback, mBluetoothUtils);
        final boolean mIsBluetoothOn = mBluetoothUtils.isBluetoothOn();
        final boolean mIsBluetoothLePresent = mBluetoothUtils.isBluetoothLeSupported();

        mDeviceStore = new BluetoothLeDeviceStore();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        scan = (Button) findViewById(R.id.scan);
        showname = (TextView) findViewById(R.id.show_name_beaconn);
        showdistance = (TextView) findViewById(R.id.show_distance_beacon);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScan();
            }
        });

    }

    private void startScan() {
        final boolean mIsBluetoothOn = mBluetoothUtils.isBluetoothOn();
        final boolean mIsBluetoothLePresent = mBluetoothUtils.isBluetoothLeSupported();
        mDeviceStore.clear();

      /*  mLeDeviceListAdapter = new LeDeviceListAdapter(this, mDeviceStore.getDeviceCursor());
        mList.setAdapter(mLeDeviceListAdapter);*/
        mDeviceStore.getDeviceCursor();

        mBluetoothUtils.askUserToEnableBluetoothIfNeeded();
        if (mIsBluetoothOn && mIsBluetoothLePresent) {
            mScanner.scanLeDevice(-1, true);
        }
    }

}
