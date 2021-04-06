package com.example.ct60a2411_e11_test.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ct60a2411_e11_test.MySetting;
import com.example.ct60a2411_e11_test.R;

import org.w3c.dom.Text;

import static android.graphics.Color.BLUE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    TextView textViewReadable;
    EditText eView;
    String wText = "";
    String savedText = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_settings);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        TextView tView = root.findViewById(R.id.textViewReadable);
        //EditText eView = root.findViewById(R.id.editTextTextMultiLineWritable);
        MySetting setting = MySetting.getInstance();

        // Writeable editview handling

        eView = (EditText) root.findViewById(R.id.editTextTextMultiLineWritable);

        if (setting.getEditable()) {
            eView.setEnabled(true);
        } else
            eView.setEnabled(false);

        eView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                wText = s.toString();
                //tView.setText(wText);
               setting.setViewText(wText);
            }
        });


        // Editable setting
        if (!setting.getEditable()) {
            tView.setText(eView.getText());
            tView.setText(setting.getViewText());
            System.out.println("Editointitila: " + setting.getEditable() + " " + setting.getViewText());
        }
        else {
            //tView.setText(setting.getViewText());
            System.out.println("Editointitila: " + setting.getEditable() + " " + setting.getViewText());
        }

        // Fontsize setting

        int fontsize = setting.getViewFontsize();

        if ( fontsize > 0)
            tView.setTextSize((float)fontsize);

        // View width and height setting

        int width = setting.getViewWidth();

        if (width > 0 && width < 381) {
            tView.setWidth(setting.getViewWidth());
            System.out.println("Home width: " + setting.getViewWidth());
        }

        int height = setting.getViewHeight();

        if ( height > 0 && height < 201 ) {
            tView.setHeight(setting.getViewHeight());
            System.out.println("Home height: " + setting.getViewHeight());
        }


        // Color setting

        TextView textViewReadable = (TextView) root.findViewById(R.id.textViewReadable);
        textViewReadable.setBackgroundColor(setting.getViewColor());

        return root;
    }
}