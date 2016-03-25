package com.singh.harsukh.zipcarchallenge.fragments;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.singh.harsukh.zipcarchallenge.MainActivity;
import com.singh.harsukh.zipcarchallenge.R;
import com.singh.harsukh.zipcarchallenge.models.ImageModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by harsukh on 3/22/16.
 */
public class GridFragment extends Fragment implements AdapterView.OnItemClickListener {
    private GridView gridView;
    private ArrayList<ImageModel> items = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.grid_layout, container, false);
        gridView = (GridView) v.findViewById(R.id.gridview);
        gridView.setPadding(8,8,8,8);
        gridView.setOnItemClickListener(this);
        if(items == null && savedInstanceState == null)
        {
            items = new ArrayList<>(5);
            for(int i = 0; i<5; ++i)
            {
                ImageModel object = new ImageModel();
                    if(i == 0) {
                        object.setItem_name("The Hillary");
                        object.setItem_image(convertToBitmap(getResources(), R.drawable.p1, 100, 50));
                        object.setItem_price("20.00");

                    }
                    if(i == 1) {
                        object.setItem_name("The Donald");
                        object.setItem_image(convertToBitmap(getResources(), R.drawable.p2, 100, 50));
                        object.setItem_price("20.00");

                    }
                    if(i == 2) {
                        object.setItem_name("The Bernie");
                        object.setItem_image(convertToBitmap(getResources(), R.drawable.p3, 100, 100));
                        object.setItem_price("10.00");

                    }
                    if(i == 3) {
                        object.setItem_name("The Cruz");
                        object.setItem_image(convertToBitmap(getResources(), R.drawable.p4, 100, 75));
                        object.setItem_price("4.00");

                    }
                    if(i == 4) {
                        object.setItem_name("The Rubio");
                        object.setItem_image(convertToBitmap(getResources(), R.drawable.p5, 100, 50));
                        object.setItem_price("1.00");

                    }

                object.setItem_quantity("10");
                items.add( object);
            }
            gridView.setAdapter(new GridAdapter(getContext(), items));
        }
        if(savedInstanceState != null && items == null)
        {
            items = savedInstanceState.getParcelableArrayList("KEY");
            if(items != null)
                gridView.setAdapter(new GridAdapter(getContext(), items));
        }
        else
        {
            Log.e("MainActivity", "null view");
        }
        return v;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap convertToBitmap(Resources res, int resId,
                                         int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }



    public String getPrice(int position)
    {
        return ((ImageModel)gridView.getItemAtPosition(position)).getItem_price();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("KEY", items);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("GridFragment", ""+position);
        ((MainActivity) getActivity()).setPrice(((ImageModel) gridView.getItemAtPosition(position)).getItem_price());
        ((MainActivity) getActivity()).setName(position);
    }

    public int decrement(int position)
    {
        String quantity = ((ImageModel)gridView.getItemAtPosition(position)).getItem_quantity();
        int count = Integer.parseInt(quantity);
        if(count > 0) {
            --count;
            ((ImageModel) gridView.getItemAtPosition(position)).setItem_quantity(Integer.toString(count));
            gridView.invalidateViews();
            gridView.refreshDrawableState();
            return 1;
        }
        else
        {
            Toast.makeText(getContext(), "Sorry no " + ((ImageModel) gridView.getItemAtPosition(position)).getItem_name()+" please choose something else.",Toast.LENGTH_LONG).show();
            return 0;
        }
    }

    public void restock(ArrayList list)
    {
        for(int i= 0; i<items.size(); ++i)
        {
            if(list.contains(items.get(i).getItem_name()))
            {
                restock(items.get(i).getItem_name());
            }
        }
    }
    public void restock(String name)
    {
        for(int i = 0; i<gridView.getChildCount(); ++i)
        {
            if(((ImageModel) gridView.getItemAtPosition(i)).getItem_name().equals(name))
            {
                ((ImageModel) gridView.getItemAtPosition(i)).setItem_quantity(Integer.toString(10));
                gridView.invalidateViews();
                gridView.refreshDrawableState();
                return;
            }
        }
    }

    public interface gridInterface
    {
        String getPrice(int position);
        int decrementProduct(int position);
        void restockList(ArrayList<String> items);
    }

    private class GridAdapter extends BaseAdapter
    {
        private Context context;
        private ArrayList<ImageModel> items;

        public GridAdapter(Context context, ArrayList items)
        {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            if(items != null)
                return items.get(position);
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null)
            {
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.single_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.price.setText(items.get(position).getItem_price());
            holder.quantity.setText(items.get(position).getItem_quantity());
            holder.title.setText(items.get(position).getItem_name());
            holder.image.setImageBitmap(items.get(position).getItem_image());
            return convertView;
        }

        public class ViewHolder
        {
            TextView price;
            ImageView image;
            TextView title;
            TextView quantity;

            public ViewHolder(View v)
            {
                this.price = (TextView) v.findViewById(R.id.item_price);
                this.image = (ImageView) v.findViewById(R.id.item_image);
                this.title = (TextView) v.findViewById(R.id.item_title);
                this.quantity = (TextView) v.findViewById(R.id.item_quantity);
            }
        }
    }
}
