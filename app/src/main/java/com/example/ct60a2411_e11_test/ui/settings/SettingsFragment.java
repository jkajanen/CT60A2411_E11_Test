package com.example.ct60a2411_e11_test.ui.settings;

import android.bluetooth.BluetoothA2dp;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ct60a2411_e11_test.MyLanguage;
import com.example.ct60a2411_e11_test.MySetting;
import com.example.ct60a2411_e11_test.R;

import java.util.ArrayList;
import java.util.Iterator;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.toUnsignedLong;

public class SettingsFragment extends Fragment {

    private enum Colors {
        Blue,
        Red,
        Black,
        White,
        Green,
        Gray
    }

    EditText textViewWidth;
    EditText textViewHeight;
    EditText eView;

    MyLanguage language = MyLanguage.getInstance(); // Singleton!!!
    Spinner spinnerLanguageSelection;
    Spinner spinnerFontsizeSelection;
    Spinner spinnerBGColorSelection;
    ArrayList<Integer> fontsizeSelection = new ArrayList<>();
    ArrayList<String> bgColorSelection = new ArrayList<>();
    ArrayList<String> languageSelection = new ArrayList<>();

    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        final TextView textView = root.findViewById(R.id.text_settings);
        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // Get settings class
        MySetting setting = MySetting.getInstance();

        // Switch handler

        Switch onOffSwitch = (Switch)  root.findViewById(R.id.switchOnOff);

        onOffSwitch.setChecked(setting.getEditable());
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", ""+isChecked);
                System.out.println("Switch changed " + isChecked);
                setting.setEditable(isChecked);
            }
        });


        // Language selection with Spinner

        Spinner spinnerLanguageSelection = (Spinner) root.findViewById(R.id.spinnerLanguage);

        //ArrayList<String> languageSelection = new ArrayList<>();

        // Add languages into the array list for Spinner/Adapter
        System.out.println("############## Spinner initialization ################");
        for(int i = 0; i < language.getLanguageCount(); i++) {
            languageSelection.add(language.getLanguage(i));
            System.out.println(language.getLanguage(i));
        }

        System.out.println("############## Spinner check ################");
        for(int i = 0; i < languageSelection.size(); i++) {
            System.out.println(languageSelection.get(i));
        }

            //getApplicationContext()

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapterLanguageSelection = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_spinner_dropdown_item, languageSelection);
        // Specify the layout to use when the list of choices appears
        adapterLanguageSelection.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerLanguageSelection.setAdapter(adapterLanguageSelection);

        // Setting current language in spinner
        spinnerLanguageSelection.setSelection(getLanguagePosition(setting.getViewlanguage()));
        System.out.println(setting.getViewlanguage());

        spinnerLanguageSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selected = parent.getItemAtPosition(pos).toString();
                if (selected.equals("Check")) {
                    //editTextTextMultiLineDisplayText.setText("Select a movie theater to see the program.");
                    System.out.println("Start position");
                }
                else {
                    //editTextTextMultiLineDisplayText.setText(selected);
                    //Change language, replace words
                    //doLanguageReplacement(pos);
                    System.out.println("Language: " + language.getLanguage(pos));
                    setting.setViewLanguage(language.getLanguage(pos));
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
                String textMessage = "Nothing selected!";
                //editTextTextMultiLineDisplayText.setText(textMessage);
            }
        });

        // Fontsize selection with Spinner

        Spinner spinnerFontsizeSelection = (Spinner) root.findViewById(R.id.spinnerFontSize);

        //ArrayList<Integer> fontsizeSelection = new ArrayList<>();

        // Add fontsizes into the array list for Spinner/Adapter
        System.out.println("############## Spinner (Fontsize) initialization ################");
        int minSize = 4;
        int maxSize = 64;
        for(int i = minSize; i < maxSize; i=i+4) {
            fontsizeSelection.add(i);
            System.out.println("Fontsize: " + i + " added");
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<Integer> adapterFontsizeSelection = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_spinner_dropdown_item, fontsizeSelection);
        // Specify the layout to use when the list of choices appears
        adapterFontsizeSelection.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerFontsizeSelection.setAdapter(adapterFontsizeSelection);

        // Setting current fontsize in spinner
        spinnerFontsizeSelection.setSelection(getFontPosition(setting.getViewFontsize()));
        System.out.println(setting.getViewFontsize());

        spinnerFontsizeSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selected = parent.getItemAtPosition(pos).toString();
                if (selected.equals("Check")) {
                    //editTextTextMultiLineDisplayText.setText("Select a movie theater to see the program.");
                    System.out.println("Start position");
                }
                else {
                    //editTextTextMultiLineDisplayText.setText(selected);
                    //doLanguageRaplacement(pos);
                    System.out.println("Fontsize: " + fontsizeSelection.get(pos));
                    setting.setViewFontsize(fontsizeSelection.get(pos));
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
                String textMessage = "Nothing selected!";
                //editTextTextMultiLineDisplayText.setText(textMessage);
            }
        });

        // Background color selection with Spinner

        Spinner spinnerBGColorSelection = (Spinner) root.findViewById(R.id.spinnerBGColor);

        //ArrayList<String> bgColorSelection = new ArrayList<>();

        // Add colors into the array list for Spinner/Adapter
        System.out.println("############## Spinner (BG Color) initialization ################");
        bgColorSelection.add("Blue"); // 0/-16776961
        System.out.println("Color: " + bgColorSelection.get(0).toString() + " " + BLUE + " added");
        bgColorSelection.add("Red"); // 1/-65536
        System.out.println("Color: " + bgColorSelection.get(1).toString() + " " + RED + " added");
        bgColorSelection.add("Black"); // 2/-16777216
        System.out.println("Color: " + bgColorSelection.get(2).toString() + " " + BLACK + " added");
        bgColorSelection.add("White"); // 3/-1
        System.out.println("Color: " + bgColorSelection.get(3).toString() + " " + WHITE + " added");
        bgColorSelection.add("Green"); // 4/-16711936
        System.out.println("Color: " + bgColorSelection.get(4).toString() + " " + GREEN + " added");
        bgColorSelection.add("Gray"); // 5/-7829368
        System.out.println("Color: " + bgColorSelection.get(5).toString() + " " + GRAY + " added");


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapterBGColorSelection = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_spinner_dropdown_item, bgColorSelection);
        // Specify the layout to use when the list of choices appears
        adapterBGColorSelection.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerBGColorSelection.setAdapter(adapterBGColorSelection);

        // Setting current color in spinner
        spinnerBGColorSelection.setSelection(getColorPosition(setting.getViewColor()));
        System.out.println(setting.getViewColor());

        spinnerBGColorSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selected = parent.getItemAtPosition(pos).toString();
                if (selected.equals("Check")) {
                    //editTextTextMultiLineDisplayText.setText("Select a movie theater to see the program.");
                    System.out.println("Start position");
                }
                else {
                    System.out.println("Color: " + bgColorSelection.get(pos) + " selected");
                    //System.out.println("Color: " + Integer.parseInt(bgColorSelection.get(pos)));
                    setting.setViewColor(getColorValue(bgColorSelection.get(pos)));
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
                String textMessage = "Nothing selected!";
                //editTextTextMultiLineDisplayText.setText(textMessage);
            }
        });

        // Textview width and height selections

        // Width field management

        textViewWidth = (EditText) root.findViewById(R.id.editTextTextMultiLineWidth);

        if (setting.getViewWidth() > 0 ) {
            String s = String.valueOf(setting.getViewWidth());
            textViewWidth.setText(s);
            System.out.println("Width: " + setting.getViewWidth());
        }

        textViewWidth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int viewWidth = Integer.parseInt(textViewWidth.getText().toString()); // todo: Null check!!!
                System.out.println("Width: " + viewWidth);
                setting.setViewWidth(viewWidth);
            }
        });

        // Height field management

        textViewHeight = (EditText) root.findViewById(R.id.editTextTextMultiHeight);

        if (setting.getViewHeight() > 0 ) {
            String s = String.valueOf(setting.getViewHeight());
            textViewHeight.setText(s);
            System.out.println("Height: " + setting.getViewHeight());
        }

        textViewHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int viewHeight = Integer.parseInt(textViewHeight.getText().toString()); // todo: Null check!!!
                System.out.println(viewHeight);
                setting.setViewHeight(viewHeight);
            }
        });

        // Settings textview handling

        //EditText eView = root.findViewById(R.id.editTextTextMultiLineDisplayText);

        EditText eView = (EditText) root.findViewById(R.id.editTextTextMultiLineDisplayText);

        eView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //wText = s.toString();
                //tView.setText(wText);
                setting.setViewText(s.toString());
                setting.setViewTextExists(true);
            }
        });

        return root;
    }


    // Set of help methods to manage spinners

    private int getColorValue(String color) {
        int cValue = -1;

        if (color.contains(Colors.Blue.toString()))
            cValue = -16776961;
        else if (color.contains(Colors.Red.toString()))
            cValue = -65536;
        else if (color.contains(Colors.Black.toString()))
            cValue = -16777216;
        else if (color.contains(Colors.White.toString()))
            cValue = -1;
        else if (color.contains(Colors.Green.toString()))
            cValue = -16711936;
        else if (color.contains(Colors.Gray.toString()))
            cValue = -7829368;
      return cValue;
    }


    private int getColorPosition(String color) {
        int cValue = 3;

        if (color.contains(Colors.Blue.toString()))
            cValue = 0;
        else if (color.contains(Colors.Red.toString()))
            cValue = 1;
        else if (color.contains(Colors.Black.toString()))
            cValue = 2;
        else if (color.contains(Colors.White.toString()))
            cValue = 3;
        else if (color.contains(Colors.Green.toString()))
            cValue = 4;
        else if (color.contains(Colors.Gray.toString()))
            cValue = 5;
        return cValue;
    }

    private int getColorPosition(int color) {
        int cValue = 3;

        if (color == BLUE)
            cValue = 0;
        else if (color == RED)
            cValue = 1;
        else if (color == BLACK)
            cValue = 2;
        else if (color == WHITE)
            cValue = 3;
        else if (color == GREEN)
            cValue = 4;
        else if (color == GRAY)
            cValue = 5;
        return cValue;
    }

    private int getFontPosition(int fontsize) {
        int i = 0;
        int fPosition = 2;

        for(int pos:fontsizeSelection) {
            System.out.println(pos);
            if ( pos == fontsize )
                fPosition = i;
            i++;
        }

        return fPosition;
    }

    private int getLanguagePosition(String language) {
        int i = 0;
        int fPosition = 2;

        for(String lang:languageSelection) {
            System.out.println(lang);
            if ( lang.contains(language) )
                fPosition = i;
            i++;
        }

        return fPosition;
    }

}