package com.singh.harsukh.zipcarchallenge.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.singh.harsukh.zipcarchallenge.MainActivity;
import com.singh.harsukh.zipcarchallenge.R;

/**
 * Created by harsukh on 3/23/16.
 */
public class LogicFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private EditText text;
    private TextView current_price;
    private Spinner dropdown;
    private int current_position;
    private String[] items;
    private Button dispense_button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.logic_layout, container, false);
        dropdown = (Spinner)v.findViewById(R.id.list);
        text = (EditText) v.findViewById(R.id.price_enter);
        current_price = (TextView) v.findViewById(R.id.total_price);
        dispense_button = (Button) v.findViewById(R.id.dispense_button);
        dispense_button.setOnClickListener(this);
        dropdown.setOnItemSelectedListener(this);
        if(savedInstanceState == null) {
            current_position = 0;
            items = new String[]{"The Hillary", "The Donald", "The Bernie", "The Cruz", "The Rubio"};
        }
        else {
            current_position = savedInstanceState.getInt("current");
            items = savedInstanceState.getStringArray("items");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setSelection(current_position);
        return v;
    }


    private static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.dispense_button)
        {
            String str = text.getText().toString();
            if(str.isEmpty() || str == null || isNumeric(str) == false)
            {
                Toast.makeText(getContext(), "Please enter the correct amount", Toast.LENGTH_LONG).show();
                return;
            }
            String price = current_price.getText().toString();
            Double float_price = Double.parseDouble(price);
            Double amount_entered = Double.parseDouble(str);
            if( (amount_entered - float_price) < 0)
            {
                Toast.makeText(getContext(), "Please enter the correct amount", Toast.LENGTH_LONG).show();
                return;
            }
            else
            {
                int check  = ((MainActivity) getActivity()).decrementProduct(current_position);
                if(check == 1) {
                    String res  =Double.toString(Math.round((amount_entered - float_price)*100.0)/100.0);
                    Toast.makeText(getContext(), "Please collect your change: " +res, Toast.LENGTH_LONG).show();
                }
                text.setText("");
            }
        }
    }

    public void setText(String text) //sets the price of the item
    {
        current_price.setText(text);
    }

    public void setSpinnerName(int position)
    {
        dropdown.setSelection(position);
        current_position = position;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current", current_position);
        outState.putStringArray("items", items);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        current_position = position;
        String price  = ((MainActivity) getActivity()).getPrice(position);
        current_price.setText(price);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public interface logicInterface
    {
        void setPrice(String name);
        void setName(int position);
    }
}
