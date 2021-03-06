package com.edu.mike.mobilergbsupervisor;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.edu.mike.mobilergbsupervisor.btcontroller.BTDeviceModel;
import com.edu.mike.mobilergbsupervisor.btcontroller.ConnectionStatus;
import com.edu.mike.mobilergbsupervisor.btcontroller.ReceivedString;
import com.edu.mike.mobilergbsupervisor.colorpicker.HSVColor;
import com.edu.mike.mobilergbsupervisor.colorpicker.HSVColorPickerDialog;
import com.edu.mike.mobilergbsupervisor.maincontroller.MainController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import android.os.Handler;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button button;
    Button setAnimationBtn;
    Button connectBtn;
    Button colorPallete;


    SeekBar redSlider;
    SeekBar greenSlider;
    SeekBar blueSlider;

    MainController rgbController;

    TextView temperatureDisplay;

    EventBus listenerEvent = EventBus.getDefault();

    float [] hsvColor = new float[3];

    Handler poolingModuleHandler;
    Runnable runnable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         button = (Button)findViewById(R.id.button4);
         setAnimationBtn = (Button)findViewById(R.id.setAnnimation);
         colorPallete = (Button)findViewById(R.id.colorPallete);
         connectBtn = (Button)findViewById(R.id.connectBtn);

         redSlider = (SeekBar)findViewById(R.id.rSlider);
         greenSlider = (SeekBar)findViewById(R.id.gSlider);
         blueSlider = (SeekBar)findViewById(R.id.bSlider);

        button.setBackgroundColor(Color.BLACK);
        temperatureDisplay = (TextView)findViewById(R.id.textView8);

        final TextView redText = (TextView)findViewById(R.id.rText);
        final TextView greenText = (TextView)findViewById(R.id.gText);
        final TextView blueText = (TextView)findViewById(R.id.bText);


        redSlider.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        redSlider.getThumb().setColorFilter(Color.RED,PorterDuff.Mode.SRC_IN);
        greenSlider.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        greenSlider.getThumb().setColorFilter(Color.GREEN,PorterDuff.Mode.SRC_IN);
        blueSlider.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        blueSlider.getThumb().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

        connectBtn = (Button)findViewById(R.id.connectBtn);
        rgbController = new MainController(MainActivity.this);

        poolingModuleHandler = new Handler(Looper.getMainLooper());







        redSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int i, boolean b) {

                redText.setText(Integer.toString(i));
                setProbeColor(redSlider.getProgress(),greenSlider.getProgress(),blueSlider.getProgress());
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        rgbController.setRGB(i,greenSlider.getProgress(),blueSlider.getProgress());


                    }
                },1000);






            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        greenSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar,final int i, boolean b) {

                greenText.setText(Integer.toString(i));
                setProbeColor(redSlider.getProgress(),greenSlider.getProgress(),blueSlider.getProgress());
                Handler  handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        rgbController.setRGB(redSlider.getProgress(),i,blueSlider.getProgress());

                    }
                },1000);




            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        blueSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar,final int i, boolean b) {

                blueText.setText(Integer.toString(i));
                setProbeColor(redSlider.getProgress(),greenSlider.getProgress(),blueSlider.getProgress());
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        rgbController.setRGB(redSlider.getProgress(),greenSlider.getProgress(),i);

                    }
                },1000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        setAnimationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder setAnimationBuilder = new AlertDialog.Builder(MainActivity.this);
                final View setAnimationView = getLayoutInflater().inflate(R.layout.set_animation,null);
                final Spinner animationsList = (Spinner)setAnimationView.findViewById(R.id.animationsSpinner);
                Button accept = (Button)setAnimationView.findViewById(R.id.button);
                String [] animationsArray = new String[] {"Circle of Color","Random Color","Breathing","Animation Off"};
                ArrayAdapter<String> animationsAdapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_spinner_item,animationsArray);

                animationsList.setAdapter(animationsAdapter);



                setAnimationBuilder.setView(setAnimationView);
                final AlertDialog setAnimationDialog = setAnimationBuilder.create();

                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        EditText speedEdit = (EditText)setAnimationView.findViewById(R.id.speedEdit);
                        EditText stepEdit = (EditText)setAnimationView.findViewById(R.id.stepEdit);
                        int mode = animationsList.getSelectedItemPosition();
                        int speed = Integer.parseInt(speedEdit.getText().toString());
                        int step  = Integer.parseInt(stepEdit.getText().toString());

                        if (mode != 2) {

                            rgbController.setAnimation(mode, speed, step);


                        }else {

                            int r = redSlider.getProgress();
                            int g = greenSlider.getProgress();
                            int b = blueSlider.getProgress();
                            float [] hsv = new float[3];
                            Color.RGBToHSV(r,g,b,hsv);
                            int s = (int)hsv[1] * 100;
                            int v = (int)hsv[0] * 100;
                            rgbController.setHSV((int)hsv[0],s,v);
                            rgbController.setAnimation(mode, speed, step);


                        }

                        setAnimationDialog.hide();


                    }
                });


                setAnimationDialog.show();



            }
        });

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (!rgbController.getStatus()) {

                    ArrayList<String> deviceAddressList = new ArrayList<>();

                    final Set<BTDeviceModel> deviceSet = rgbController.getControllerAddress();

                    for (BTDeviceModel device : deviceSet) {

                        deviceAddressList.add(device.getDeviceName());

                    }

                    AlertDialog.Builder deviceListBuilder = new AlertDialog.Builder(MainActivity.this);
                    View btDeviceListView = getLayoutInflater().inflate(R.layout.device_list, null);
                    ListView lsBtDevice = (ListView) btDeviceListView.findViewById(R.id.deviceList);
                    ArrayAdapter deviceAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, deviceAddressList);
                    lsBtDevice.setAdapter(deviceAdapter);
                    deviceListBuilder.setView(btDeviceListView);
                    final AlertDialog deviceListDialog = deviceListBuilder.create();
                    deviceListDialog.show();
                    lsBtDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            String deviceAddressStr = rgbController.getDeviceAddressByName(((TextView) view).getText().toString(), deviceSet);
                            rgbController.connectToController(deviceAddressStr);
                            deviceListDialog.hide();
                           connectBtn.setText("DISCONNECT");






                        }
                    });




                }else{

                    try {
                        rgbController.disconnectFromController();
                        connectBtn.setText("CONNECT");
                        poolingModuleHandler.removeCallbacks(pool);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }












            }
        });


        colorPallete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HSVColorPickerDialog colorPicker = new HSVColorPickerDialog(MainActivity.this, 0xFF4488CC, new HSVColorPickerDialog.OnColorSelectedListener() {
                    @Override
                    public void colorSelected(Integer color) {

                        Color.colorToHSV(color,hsvColor);
                        int h = (int)(hsvColor[0]);
                        int s = (int)(hsvColor[1]*100);
                        int v = (int)(hsvColor[2]*100);

                        rgbController.setHSV(h,s,v);

                    }
                });

                colorPicker.setTitle("HSV");
                colorPicker.show();

            }
        });











    }



    private void setProbeColor(int r, int g, int b){

        button.setBackgroundColor(Color.rgb(r, g, b));


    }

    private Runnable pool = new Runnable() {
        @Override
        public void run() {

            rgbController.getTemp();
            poolingModuleHandler.postDelayed(this,5000);
            Log.i("getTemperature","getTemperature");




        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceivedEvent (ReceivedString receivedString){

        temperatureDisplay.setText(receivedString.getReceivedString());
        Log.i("getTemperature",receivedString.getReceivedString());



    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetHsv(HSVColor color){

        int h = (int)color.getH();
        int s = (int)(color.getS()*100);
        int v = (int)(color.getV()*100);
        rgbController.setHSV(h,s,v);



    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetConnectionStatus(ConnectionStatus connectionStatus){

        poolingModuleHandler.postDelayed(pool, 1000);

    }


    @Override
    protected void onStart() {
        super.onStart();
        listenerEvent.register(this);

    }

    @Override
    protected void onStop() {
        listenerEvent.unregister(this);
        super.onStop();
    }
}
