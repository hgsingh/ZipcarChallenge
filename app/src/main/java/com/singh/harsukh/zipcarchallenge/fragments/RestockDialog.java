package com.singh.harsukh.zipcarchallenge.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.singh.harsukh.zipcarchallenge.MainActivity;
import com.singh.harsukh.zipcarchallenge.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by harsukh on 3/23/16.
 */
public class RestockDialog extends DialogFragment implements View.OnClickListener, AdapterView.OnItemClickListener{

    private ListView listView;
    private Button dismiss_button;
    private ArrayList<String> items;
    private CheckBox button;
    private RestockAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_layout, container, false);
        listView = (ListView)v.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        adapter = new RestockAdapter(getContext(), new ArrayList<String>(Arrays.asList(new String[]{"The Hillary", "The Donald", "The Bernie", "The Cruz", "The Rubio"})));
        dismiss_button = (Button) v.findViewById(R.id.dismiss_button);
        dismiss_button.setOnClickListener(this);
        items = new ArrayList<>();
        getDialog().setTitle("Restock Presidents");
        listView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onClick(View v) {

    for(int i=0;i<adapter.checkStates.size();i++) {
        if (adapter.isChecked(i)) {
            items.add((String) listView.getItemAtPosition(i));
            System.out.println((String) listView.getItemAtPosition(i));
        }
    }
        ((MainActivity)getActivity()).restockList(items);
        dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }


    private class RestockAdapter extends ArrayAdapter implements CompoundButton.OnCheckedChangeListener {
        private Context context;
        private ArrayList<String> rows;
        private SparseBooleanArray checkStates;

        public RestockAdapter(Context context, ArrayList rows) {
            super(context, R.layout.single_row, rows);
            this.context = context;
            this.rows = rows;
            checkStates = new SparseBooleanArray(rows.size());
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView  = inflater.inflate(R.layout.single_row, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.text.setText(rows.get(position));
            holder.button.setOnCheckedChangeListener(this);
            holder.button.setTag(position);
            holder.button.setChecked(checkStates.get(position, false));
            return convertView;
        }

        public boolean isChecked(int position) {
            return checkStates.get(position, false);
        }

        public void setChecked(int position, boolean isChecked) {
            checkStates.put(position, isChecked);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            setChecked((Integer)buttonView.getTag(), isChecked);
        }

        private class ViewHolder
        {
            CheckBox button;
            TextView text;

            public ViewHolder(View v)
            {
                this.button = (CheckBox) v.findViewById(R.id.checkBox);
                this.text = (TextView) v.findViewById(R.id.product);
            }
        }
    }
}
