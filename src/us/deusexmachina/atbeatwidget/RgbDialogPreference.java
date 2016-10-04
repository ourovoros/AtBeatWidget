package us.deusexmachina.atbeatwidget;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;

public class RgbDialogPreference extends DialogPreference implements SeekBar.OnSeekBarChangeListener {

    private static SharedPreferences sp;
    private SeekBar barR;
    private SeekBar barG;
    private SeekBar barB;
    private ViewColor cView;

    private int defaultColor = 0;

    public RgbDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        sp = PreferenceManager.getDefaultSharedPreferences(context);

        setDialogLayoutResource(R.layout.rgbdlg);
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);

        barR = (SeekBar)v.findViewById(R.id.SeekBarRed);
        barR.setOnSeekBarChangeListener(this);

        barG = (SeekBar)v.findViewById(R.id.SeekBarGreen);
        barG.setOnSeekBarChangeListener(this);

        barB = (SeekBar)v.findViewById(R.id.SeekBarBlue);
        barB.setOnSeekBarChangeListener(this);

        cView = (ViewColor)v.findViewById(R.id.ColorView);

        barR.setProgress(Color.red(getVal()));
        barG.setProgress(Color.green(getVal()));
        barB.setProgress(Color.blue(getVal()));

        update();
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            setVal(Color.rgb(barR.getProgress(), barG.getProgress(), barB.getProgress()));
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromUser) {
        // TODO Auto-generated method stub

        update();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }
    private void setVal(int value) {
        Editor e = sp.edit();
        e.putInt(getKey(), value);
        e.commit();
    }

   private int getVal() {
    	return sp.getInt(getKey(), defaultColor);
    }

    private void update() {
        cView.setColor(Color.rgb(barR.getProgress(), barG.getProgress(), barB.getProgress()));
        cView.invalidate();
    }

    public void setDefault(int color) {
        defaultColor = color;
    }
}
