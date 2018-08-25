package br.com.wilson.helloworldturbo.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import br.com.wilson.helloworldturbo.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.fragment_preferences);
        getActivity().setTitle("Configurações");
    }
}
