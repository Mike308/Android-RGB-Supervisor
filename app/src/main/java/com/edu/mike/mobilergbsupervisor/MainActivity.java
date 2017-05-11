package com.edu.mike.mobilergbsupervisor;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.edu.mike.mobilergbsupervisor.btcontroller.BTController;
import com.edu.mike.mobilergbsupervisor.btcontroller.BTDeviceModel;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button button;
    Button setAnimationBtn;
    Button connectBtn;


    SeekBar redSlider;
    SeekBar greenSlider;
    SeekBar blueSlider;

    BTController btController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button4);
        setAnimationBtn = (Button)findViewById(R.id.setAnnimation);
        button.setBackgroundColor(Color.BLACK);
        redSlider = (SeekBar)findViewById(R.id.rSlider);
        greenSlider = (SeekBar)findViewById(R.id.gSlider);
        blueSlider = (SeekBar)findViewById(R.id.bSlider);

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

        btController = new BTController(MainActivity.this);





        redSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                redText.setText(Integer.toString(i));
                setProbeColor(redSlider.getProgress(),greenSlider.getProgress(),blueSlider.getProgress());


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
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                greenText.setText(Integer.toString(i));
                setProbeColor(redSlider.getProgress(),greenSlider.getProgress(),blueSlider.getProgress());
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
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                blueText.setText(Integer.toString(i));
                setProbeColor(redSlider.getProgress(),greenSlider.getProgress(),blueSlider.getProgress());
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
                View setAnimationView = getLayoutInflater().inflate(R.layout.set_animation,null);
                Spinner animationsList = (Spinner)setAnimationView.findViewById(R.id.animationsSpinner);
                String [] animationsArray = new String[] {"Circle of Color","Random Color"};
                ArrayAdapter<String> animationsAdapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_spinner_item,animationsArray);

                animationsList.setAdapter(animationsAdapter);



                setAnimationBuilder.setView(setAnimationView);
                AlertDialog setAnimationDialog = setAnimationBuilder.create();
                setAnimationDialog.show();



            }
        });











    }

    private void setProbeColor(int r, int g, int b){

        button.setBackgroundColor(Color.rgb(r, g, b));


    }
}
