package com.musicshow.client.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.musicshow.client.R;
import com.musicshow.widget.SlideView;
import com.musicshow.widget.SlideView.OnSlideListener;


public class TopicListFragment extends ListFragment{
	listViewUpdating mCallback;
	ListView mListView;
    private List<MessageItem> mMessageItems = new ArrayList<MessageItem>();
    static String[] Headlines = {
        "Article One",
        "Article Two"
    };
    public interface listViewUpdating {
        public void bindListData(ListView listView);
    }
    

    public void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
        
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
               android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        // Create an array adapter for the list view, using the Ipsum headlines array
        //setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Headlines));
    	//mCallback.bindListData(getListView());
        bindListData();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        return inflater.inflate(R.layout.listview_compat, container, false);
    	//return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    	Log.i("Trace2",     getClass().getName()+ "->" +Thread.currentThread().getStackTrace()[2].getMethodName());
    
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    	Log.i("Trace2",     getClass().getName()+ "->" +Thread.currentThread().getStackTrace()[2].getMethodName());
    	try {
            mCallback = (listViewUpdating) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement listViewUpdating");
        }
    }
    
    
    
    public void bindListData(){

        for (int i = 0; i < 20; i++) {
            MessageItem item = new MessageItem();
            if (i % 3 == 0) {
                item.iconRes = R.drawable.default_qq_avatar;
                item.title = "��Ѷ����";
                item.msg = "�ൺ��ը���£�������Ϻ����";
                item.time = "����18:18";
            } else {
                item.iconRes = R.drawable.wechat_icon;
                item.title = "΢���Ŷ�";
                item.msg = "��ӭ��ʹ��΢��";
                item.time = "12��18��";
            }
            mMessageItems.add(item);
        }
        setListAdapter(new SlideAdapter());
        
	}
	
	 public class MessageItem {
	        public int iconRes;
	        public String title;
	        public String msg;
	        public String time;
			public SlideView slideView;

	    }
	    
	    private static class ViewHolder {
	        public ImageView icon;
	        public TextView title;
	        public TextView msg;
	        public TextView time;

	        ViewHolder(View view) {
	            icon = (ImageView) view.findViewById(R.id.icon);
	            title = (TextView) view.findViewById(R.id.title);
	            msg = (TextView) view.findViewById(R.id.msg);
	            time = (TextView) view.findViewById(R.id.time);
	        }
	    }
	    
	    private class SlideAdapter extends BaseAdapter {

	        private LayoutInflater mInflater;

	        SlideAdapter() {
	            super();
	            mInflater = getActivity().getLayoutInflater();
	        }

	        @Override
	        public int getCount() {
	        	Log.i("size", mMessageItems.size()+"");
	        	
	            return mMessageItems.size();
	        }

	        @Override
	        public Object getItem(int position) {
	        	Log.i("position", position+"");
	            return mMessageItems.get(position);
	        }

	        @Override
	        public long getItemId(int position) {
	            return position;
	        }

			@SuppressLint("InflateParams") 
			@Override
	        public View getView(int position, View convertView, ViewGroup parent) {
	            ViewHolder holder;
	            SlideView slideView = (SlideView) convertView;
	            if (convertView == null) {
//	            	convertView = mInflater.inflate(R.layout.list_item, null);
//	                holder = new ViewHolder(convertView);
//	                convertView.setTag(holder);
	                
	                
	                View itemView = mInflater.inflate(R.layout.topic_list_item, null);

	                slideView = new SlideView(getActivity());
	                slideView.setContentView(itemView);

	                holder = new ViewHolder(slideView);
	                slideView.setOnSlideListener((OnSlideListener) getActivity());
	                slideView.setTag(holder);
	            } else {
//	                holder = (ViewHolder) convertView.getTag();
	                holder = (ViewHolder) slideView.getTag();
	            }
	            
	            
	            MessageItem item = mMessageItems.get(position);
	            item.slideView = slideView;
	            item.slideView.shrink();
	            holder.icon.setImageResource(item.iconRes);
	            holder.title.setText(item.title);
	            holder.msg.setText(item.msg);
	            holder.time.setText(item.time);

	            return slideView;
	        }

	    }
    
}
