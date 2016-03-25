package com.singh.harsukh.zipcarchallenge;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.singh.harsukh.zipcarchallenge.fragments.GridFragment;
import com.singh.harsukh.zipcarchallenge.fragments.LogicFragment;
import com.singh.harsukh.zipcarchallenge.fragments.RestockDialog;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements LogicFragment.logicInterface, GridFragment.gridInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment grid_fragment = getSupportFragmentManager().findFragmentById(R.id.grid_frame);
        Fragment logic_fragment = getSupportFragmentManager().findFragmentById(R.id.logic_frame);

        if(grid_fragment == null)
        {
            grid_fragment = new GridFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.grid_frame, grid_fragment).commit();
        }
        if(logic_fragment == null)
        {
            logic_fragment = new LogicFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.logic_frame, logic_fragment).commit();
        }
    }

    @Override
    public void setPrice(String price) {
        LogicFragment logic_fragment = (LogicFragment) getSupportFragmentManager().findFragmentById(R.id.logic_frame);
        logic_fragment.setText(price);
    }

    @Override
    public void setName(int position) {
        LogicFragment logic_fragment = (LogicFragment) getSupportFragmentManager().findFragmentById(R.id.logic_frame);
        logic_fragment.setSpinnerName(position);
    }

    @Override
    public String getPrice(int position) {
        GridFragment grid_fragment = (GridFragment) getSupportFragmentManager().findFragmentById(R.id.grid_frame);
        return grid_fragment.getPrice(position);
    }

    public int decrementProduct(int position)
    {
        GridFragment grid_fragment = (GridFragment) getSupportFragmentManager().findFragmentById(R.id.grid_frame);
        return grid_fragment.decrement(position);
    }

    //get the menu options and inflate
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.restock_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_restock)
        {
            FragmentManager manager = getSupportFragmentManager();
            RestockDialog stock = new RestockDialog();
            stock.show(manager, "RestockDialog");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void restockList(ArrayList<String> items)
    {
        if(items.isEmpty() || items == null)
        {
            return;
        }
        GridFragment grid_fragment = (GridFragment) getSupportFragmentManager().findFragmentById(R.id.grid_frame);
        grid_fragment.restock(items);
    }
}
