package com.example.palmcampusmarket_client.fragment;

import java.util.ArrayList;  
import java.util.List;  
import java.util.concurrent.Executors;  
import java.util.concurrent.ScheduledExecutorService;  
import java.util.concurrent.TimeUnit;

import com.example.palmcampusmarket_client.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;  
import android.os.Handler;  
import android.support.v4.view.PagerAdapter;  
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.ImageView;  
import android.widget.TextView;  
  
/** 
 * 程序主入口 
 * 
 */  
public class LunboFragment extends Fragment {  
  
	private ViewPager mViewPaper;  
    private List<ImageView> images;  
    private List<View> dots;  
    private int currentItem;  
    //记录上一次点的位置  
    private int oldPosition = 0;  
    //存放图片的id  
    private int[] imageIds = new int[]{  
            R.drawable.a,  
            R.drawable.abc,  
            R.drawable.abcd,  
            R.drawable.adress,  
            R.drawable.arrow  
    };  
    //存放图片的标题  
    private String[]  titles = new String[]{  
            "1",   
            "2",    
            "3",     
            "4",      
            "5"  
        };  
    private TextView title;  
    private ViewPagerAdapter adapter;  
    private ScheduledExecutorService scheduledExecutorService;
	private View view;  
  
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {   
    	view = inflater.inflate(R.layout.lunbo,null);  
        mViewPaper = (ViewPager)view.findViewById(R.id.vp);  
          
        //显示的图片  
        images = new ArrayList<ImageView>();  
        for(int i = 0; i < imageIds.length; i++){  
            ImageView imageView = new ImageView(getActivity());  
            imageView.setBackgroundResource(imageIds[i]);  
            images.add(imageView);  
        }  
        //显示的小点  
        dots = new ArrayList<View>();  
        dots.add(view.findViewById(R.id.dot_0));  
        dots.add(view.findViewById(R.id.dot_1));  
        dots.add(view.findViewById(R.id.dot_2));  
        dots.add(view.findViewById(R.id.dot_3));  
        dots.add(view.findViewById(R.id.dot_4));  
          
        title = (TextView) view.findViewById(R.id.title);  
        title.setText(titles[0]);  
          
        adapter = new ViewPagerAdapter();  
        mViewPaper.setAdapter(adapter);  
          
        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {  
              
  
            @Override  
            public void onPageSelected(int position) {  
                title.setText(titles[position]);  
                dots.get(position).setBackgroundResource(R.drawable.dot_focused);  
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);  
                  
                oldPosition = position;  
                currentItem = position;  
            }  
              
            @Override  
            public void onPageScrolled(int arg0, float arg1, int arg2) {  
                  
            }  
              
            @Override  
            public void onPageScrollStateChanged(int arg0) {  
                  
            }  
        });
		return view;  
    }  
  
    /** 
     * 自定义Adapter 
     * 
     */  
    private class ViewPagerAdapter extends PagerAdapter{  
  
        @Override  
        public int getCount() {  
            return images.size();  
        }  
  
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {  
            return arg0 == arg1;  
        }  
  
        @Override  
        public void destroyItem(ViewGroup view, int position, Object object) {  
            // TODO Auto-generated method stub  
//          super.destroyItem(container, position, object);  
//          view.removeView(view.getChildAt(position));  
//          view.removeViewAt(position);  
            view.removeView(images.get(position));  
        }  
  
        @Override  
        public Object instantiateItem(ViewGroup view, int position) {  
            // TODO Auto-generated method stub  
            view.addView(images.get(position));  
            return images.get(position);  
        }  
          
    }  
      
 /*   @Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        // Inflate the menu; this adds items to the action bar if it is present.  
        getMenuInflater().inflate(R.menu.lunbo, menu);  
        return true;  
    }  *
  
    /** 
     * 利用线程池定时执行动画轮播 
     */  
    @Override
	public void onStart() {  
        // TODO Auto-generated method stub  
        super.onStart();  
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();  
        scheduledExecutorService.scheduleWithFixedDelay(  
                new ViewPageTask(),   
                2,   
                2,   
                TimeUnit.SECONDS);  
    }  
      
      
    /** 
     * 图片轮播任务 
     * 
     */  
    private class ViewPageTask implements Runnable{  
  
        @Override  
        public void run() {  
            currentItem = (currentItem + 1) % imageIds.length;  
            mHandler.sendEmptyMessage(0);  
        }  
    }  
      
    /** 
     * 接收子线程传递过来的数据 
     */  
    private Handler mHandler = new Handler(){  
        public void handleMessage(android.os.Message msg) {  
            mViewPaper.setCurrentItem(currentItem);  
        };  
    };  
    @Override
	public void onStop() {  
        // TODO Auto-generated method stub  
        super.onStop();  
        if(scheduledExecutorService != null){  
            scheduledExecutorService.shutdown();  
            scheduledExecutorService = null;  
        }  
    }  
      
}  
