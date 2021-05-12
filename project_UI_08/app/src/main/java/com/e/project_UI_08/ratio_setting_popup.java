package com.e.project_UI_08;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;


public class ratio_setting_popup extends Activity implements View.OnClickListener {
    int default_ratio = 1;
    private InputMethodManager imm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ratio_setting_popup);

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.ratio_radioGroup);


        View popup_permissionSetting = (View) findViewById(R.id.popup_permission_setting);
        Intent intent = getIntent();

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        EditText editText = (EditText) findViewById(R.id.editText_inputBox);
        SeekBar seekBar = findViewById(R.id.seekBar);
        editText.setText(String.valueOf(seekBar.getProgress()));
//        seekBar.setProgress(Integer.valueOf(editText.getText().toString()));

        setUseableSeekBar(seekBar, false);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_30per:
                    case R.id.radio_50per:
                    case R.id.radio_70per:
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        setUseableEditText(editText, false);
                        setUseableSeekBar(seekBar, false);
                        break;
                    case R.id.radio_self:
                        setUseableEditText(editText, true);
                        setUseableSeekBar(seekBar, true);
                        break;
                }
            }
        });

        //seekBar 움직일 때
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                editText.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //editText 숫자 입력할 때
        editText.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
                CharSequence getEditText = editText.getText().toString();
                if (TextUtils.isEmpty(getEditText)){
                    seekBar.setProgress(0);
                }
                else{
                    seekBar.setProgress(Integer.valueOf(editText.getText().toString()));
                }
                editText.setSelection(editText.length());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //editText 확인 눌렀을 때
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
                    editText.clearFocus();
                }
            return true;
            }
        });
    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {}
    }

    public void mOnClose(View v){
        Intent intent = new Intent();
        intent.putExtra("result","Close Popup");
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setUseableEditText(EditText editText, boolean useable) {
        editText.setClickable(useable);
        editText.setEnabled(useable);
        editText.setFocusable(useable);
        editText.setFocusableInTouchMode(useable);
    }
    private void setUseableSeekBar(SeekBar seekBar, boolean useable){
        seekBar.setClickable(useable);
        seekBar.setEnabled(useable);
        seekBar.setFocusable(useable);
        seekBar.setFocusableInTouchMode(useable);
    }

}
