package com.mybaseprojectandroid.utils.system;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.mybaseprojectandroid.R;
import com.mybaseprojectandroid.ui.examples.OnBoarding;
import com.mybaseprojectandroid.ui.examples.auth.AutentikasiActivity;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    SharedPreferences onBoardingScreeen;

    public SliderAdapter(Context context){
        this.context = context;
    }

    int images[] ={
            R.drawable.slide1,
            R.drawable.slide2,
            R.drawable.slide3,
            R.drawable.slide4
    };

    int headings[] ={
            R.string.first_slide_title,
            R.string.second_slide_title,
            R.string.third_slide_title,
            R.string.fourth_slide_title
    };

    int descriptions[] ={
            R.string.first_slide_desc,
            R.string.second_slide_desc,
            R.string.third_slide_desc,
            R.string.fourth_slide_desc
    };

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slides_layout,container,false);

        ImageView imageView = view.findViewById(R.id.slider_image);
        TextView heading = view.findViewById(R.id.slider_heading);
        TextView desc = view.findViewById(R.id.slider_desc);

        imageView.setImageResource(images[position]);
        heading.setText(headings[position]);
        desc.setText(descriptions[position]);

        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }

//    public void test(){
//        onBoardingScreeen = context.getSharedPreferences("onBoardingScreen",Context.MODE_PRIVATE);
//        boolean isFirstTime = onBoardingScreeen.getBoolean("firstTime",true);
//        if (isFirstTime){
//            SharedPreferences.Editor editor = onBoardingScreeen.edit();
//            editor.putBoolean("firstTime",false);
//            editor.commit();
//            Intent intent = new Intent(context.getApplicationContext(), OnBoarding.class);
//            context.startActivity(intent);
//            finish();
//
//        }else{
//            Intent intent = new Intent(context.getApplicationContext(), AutentikasiActivity.class);
//            context.startActivity(intent);
//            finish();
//        }
//
//
//    }
}
