package us.deusexmachina.atbeatwidget;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Settings extends PreferenceActivity {

    private static final String KEY_COLOR = "color";
	private static final int DEF_COLOR = Color.rgb(0, 0, 0);
    RgbDialogPreference color;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        color = (RgbDialogPreference)findPreference(KEY_COLOR);
        color.setDefault(DEF_COLOR);
    }

    public static int getColor(Context con) {
        return PreferenceManager.getDefaultSharedPreferences(con).getInt(
                KEY_COLOR, DEF_COLOR);
    }
}
