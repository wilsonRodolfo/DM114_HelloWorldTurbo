package br.com.wilson.helloworldturbo.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import br.com.wilson.helloworldturbo.R;

public class Tela1Fragment extends Fragment{

    private static String STATE_USER_TEXT = "user_text";
    private static String PREF_CONFIG_1 = "pref_config_1";

    private EditText editText1;
    private Button button1;
    private TextView textView1;
    private CheckBox chkConfig1;

    public Tela1Fragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tela1,
                container, false);

        editText1 = (EditText) rootView.findViewById(R.id.editText1);
        button1 = (Button) rootView.findViewById(R.id.button1);
        textView1 = (TextView) rootView.findViewById(R.id.textView1);
        chkConfig1 = (CheckBox) rootView.findViewById(R.id.checkConfig1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText1.getText().toString().isEmpty())
                    textView1.setText(editText1.getText().toString());
            }
        });

        if (savedInstanceState != null) {
            textView1.setText(savedInstanceState.getString(STATE_USER_TEXT));
        } else {
            textView1.setText("Olá de novo!!!");
        }

        SharedPreferences sharedSettings = getSharedPreferences();

        Boolean config1 = sharedSettings.getBoolean(PREF_CONFIG_1, false);
        chkConfig1.setChecked(config1);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_USER_TEXT,
                textView1.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences sharedSettings = getSharedPreferences();
        SharedPreferences.Editor editor = sharedSettings.edit();
        editor.putBoolean(PREF_CONFIG_1, chkConfig1.isChecked());
        editor.commit();
    }

    private SharedPreferences getSharedPreferences() {
        return getActivity().
                    getSharedPreferences(getActivity().getClass().
                            getSimpleName(), Context.MODE_PRIVATE);
    }
}