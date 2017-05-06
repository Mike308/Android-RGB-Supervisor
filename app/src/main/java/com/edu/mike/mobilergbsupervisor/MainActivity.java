package com.edu.mike.mobilergbsupervisor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button button;
    SeekBar redSlider;
    SeekBar greenSlider;
    SeekBar blueSlider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button4);
        button.setBackgroundColor(Color.BLACK);
         redSlider = (SeekBar)findViewById(R.id.rSlider);
         greenSlider = (SeekBar)findViewById(R.id.gSlider);
         blueSlider = (SeekBar)findViewById(R.id.bSlider);

        final TextView redText = (TextView)findViewById(R.id.rText);
        final TextView greenText = (TextView)findViewById(R.id.gText);
        final TextView blueText = (TextView)findViewById(R.id.bText);


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











    }

    private void setProbeColor(int r, int g, int b){

        button.setBackgroundColor(Color.rgb(r, g, b));


    }
}
