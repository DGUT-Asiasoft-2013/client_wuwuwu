package com.example.palmcampusmarket_client.fragment;

import java.io.IOException;
import java.util.ArrayList;  
import java.util.List;  
import java.util.concurrent.Executors;  
import java.util.concurrent.ScheduledExecutorService;  
import java.util.concurrent.TimeUnit;

import com.example.palmcampusmarket_client.R;
import com.example.palmcampusmarket_client.api.Server;
import com.example.palmcampusmarket_client.api.entity.Commodity;
import com.example.palmcampusmarket_client.api.entity.Page;
import com.example.palmcampusmarket_client.api.entity.User;
import com.example.palmcampusmarket_client.collect.CommodityContentActivity;
import com.example.palmcampusmarket_client.fragment.pages.MeFragment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.R.raw;
import android.R.string;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.DownloadManager.Request;
import android.content.Intent;
import android.net.Uri;
import android.app.Fragment;
import android.os.Bundle;  
import android.os.Handler;  
import android.support.v4.view.PagerAdapter;  
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;  
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;


/** 
 * 程序主入口 
 * 
 */  
public class LunboFragment extends Fragment {  

	List<Commodity> current_commodity;

	private ViewPager mViewPaper;  
	private List<ImageView> images;  
	private List<ImageView> dots; 
	private LinearLayout dotLayout;
	private int currentItem;  
	//记录上一次点的位置  
	private int oldPosition = 0;  

	private TextView title;  

	private ScheduledExecutorService scheduledExecutorService;
	private View view;  

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {   
		view = inflater.inflate(R.layout.lunbo,null);                 

		mViewPaper = (ViewPager)view.findViewById(R.id.vp);
		mViewPaper.setAdapter(adapter);
		title = (TextView) view.findViewById(R.id.title);
		dotLayout = (LinearLayout)view.findViewById(R.id.dotLayout);
		dots = new ArrayList<ImageView>();

		OkHttpClient client=Server.getSharedClient();             
		okhttp3.Request request=Server.requestBuilderWithApi("/commodity/pictures").get().build();
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(final Call call, final IOException e) {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						LunboFragment.this.onFailuer(call,e);
					}
				});

			}

			@Override
			public void onResponse(final Call call, final Response response) throws IOException {
				try {
					final String responsString = response.body().string();
					final List<Commodity> commodity = new ObjectMapper().readValue(responsString, new TypeReference<List<Commodity>>() {});  //解析服务器传来的数据
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							LunboFragment.this.onResponse(call,commodity);
						}
					});
				}catch (final Exception e){
					getActivity().runOnUiThread(new Runnable() {                            //--  runOnUiThread 把这些玩意丢进主线程中去， callback 里面的东西都在后台运行
						@Override
						public void run() {
							LunboFragment.this.onFailuer(call,e);
						}
					});


				}

			}

		});
		
		
		mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {    //设置页面改变的监听事件
			@Override  
			public void onPageSelected(int position) {

				
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
		currentItem = 0;
		return view;  
	}  

	/** 
	 * 自定义Adapter 
	 * 
	 */  
	private PagerAdapter adapter = new PagerAdapter(){  //适配器

		@Override  
		public int getCount() {  
			return images==null ? 0 : images.size();  //获取页数，页数为空返回0
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
			view.removeView(images.get(position));  //换掉指定位置的图片
		}  

		@Override  
		public Object instantiateItem(ViewGroup view, final int position) {  
			// TODO Auto-generated method stub  
			view.addView(images.get(position));
			Commodity currentSelectedCommodity = current_commodity.get(position);   //当前页面的商品信息
			title.setText(currentSelectedCommodity.getCommDescribe());  //从商品信息中读取描述
			images.get(position).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					 Commodity comm = current_commodity.get(position);
					 Intent itnt = new Intent(getActivity(),CommodityContentActivity.class);
					 itnt.putExtra("commodity", comm);
                     startActivity(itnt);
				}
			});
			return images.get(position);  //添加指定位置的图片
		}  

	};

//	@Override
//	public void onResume() {
//		super.onResume();
		
//	}

	protected void onResponse(Call call, List<Commodity> data){
		current_commodity = data; //获取服务器来的数据

		images = new ArrayList<ImageView>();

		for(Commodity commodity : data){              //for循环吧图片一张张读取出来
			ImageView imageView = new ImageView(getActivity());  
			imageView.load(Server.serverAddress+commodity.getCommImage());  
			images.add(imageView);
		}
		for(int i=0;i<images.size();i++){                  //根据图片个数动态创建小点
			ImageView dotView = new ImageView(getActivity());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			params.leftMargin=2;
			params.rightMargin=2;
			params.height=7;
			params.width=7;
			if(i==0){
				dotView.setBackgroundResource(R.drawable.dot_focused);
			}
			else{
				dotView.setBackgroundResource(R.drawable.dot_normal);
			}
           
            dotLayout.addView(dotView, params);
            dots.add(dotView);
		}              

		adapter.notifyDataSetChanged();   //通知适配器数据发生了改变
	}

	protected void onFailuer(Call call, Exception ex){

	}

	/*   @Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        // Inflate the menu; this adds items to the action bar if it is present.  
        getMenuInflater().inflate(R.menu.lunbo, menu);  
        return true;  
    }  *


	/** 
	 * 图片轮播任务 
	 * 
	/
	 * 
	 */
	@Override
	public void onStart(){
		super.onStart();
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();  
		scheduledExecutorService.scheduleWithFixedDelay(  
				new ViewPageTask(),   
				2,   
				2,   
				TimeUnit.SECONDS);  
		
	}
	private class ViewPageTask implements Runnable{  

		@Override  
		public void run() {  
			 if(images==null){             //气死人的鬼地方，判断图片是否为空，为空返回
				 return ;
			 }
			currentItem = (currentItem + 1) % images.size();  
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
