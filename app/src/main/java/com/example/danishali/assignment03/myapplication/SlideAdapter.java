package com.example.danishali.assignment03.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;

    public SlideAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return lst_titles.length;
    }

    //list of images
    public int [] lst_images = {
            R.drawable.image_1,
            R.drawable.image_2,
            R.drawable.image_3,
            R.drawable.image_4,
    };

    //list of titles
    public String [] lst_titles = {

            "COS",
            "SIN",
            "GAL",
            "ROC"
    };

    //list of descriptions
    public String [] lst_descriptions = {
            "Des 1",
            "Des 2",
            "Des 3",
            "Des 4"
    };

    public int [] lst_background = {

            Color.rgb(55,55,55),
            Color.rgb(239,85,85),
            Color.rgb(110,49,8),
            Color.rgb(1,188,212)
    };

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide,container,false);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.Slidelinearlayout);
        ImageView imageView = (ImageView) view.findViewById(R.id.slideimg);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView desp = (TextView) view.findViewById(R.id.DescritpitonHere);
        linearLayout.setBackgroundColor(lst_background[position]);
        imageView.setImageResource(lst_images[position]);
        title.setText(lst_titles[position ]);
        desp.setText(lst_descriptions[position]);
        container.addView(view);
        return view;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
      container.removeView((LinearLayout)object);
    }
}


