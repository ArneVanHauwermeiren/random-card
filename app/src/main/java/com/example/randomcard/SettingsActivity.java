package com.example.randomcard;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.DropDownPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();

        setupActionBar();
    }

    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    public static class SettingsFragment extends PreferenceFragmentCompat {
        private SwitchPreferenceCompat gameHasJokers;
        private EditTextPreference numberOfJokers;
        private DropDownPreference theme;
        private Activity activity;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            activity = getActivity();
            gameHasJokers = findPreference("game_has_jokers");
            numberOfJokers = findPreference("number_of_jokers");
            theme = findPreference("app_theme");

            setupAppTheme();
            setupGameHasJokers();
            setupNumberOfJokers();
        }

        void setupAppTheme(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
            setAppTheme(preferences.getString("app_theme", "System Default"));

            theme.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    setAppTheme(newValue.toString());
                    return true;
                }
            });
        }

        static void setAppTheme(String theme){
            switch(theme) {
                case "Dark":
                    AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_YES);
                    break;
                case "Light":
                    AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_NO);
                    break;
                case "System default":
                    AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    break;
            }
        }

        private void setupGameHasJokers(){
            numberOfJokers.setVisible(gameHasJokers.isChecked());

            gameHasJokers.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    numberOfJokers.setVisible((boolean) newValue);
                    return true;
                }
            });
        }

        private void setupNumberOfJokers(){
            numberOfJokers.setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
            });
        }
    }
}