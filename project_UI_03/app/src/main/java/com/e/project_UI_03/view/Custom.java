package com.e.project_UI_03.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.e.project_UI_03.R;

public class Custom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom);
    }

    public void onClick(View view) {
        finish();
    }

    /*

    slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
        override fun onStartTrackingTouch(slider: Slider) {
            // Responds to when slider's touch event is being started
        }

        override fun onStopTrackingTouch(slider: Slider) {
            // Responds to when slider's touch event is being stopped
        }
    })

            slider.addOnChangeListener { slider, value, fromUser ->
        // Responds to when slider's value is changed
    }

     */

}

