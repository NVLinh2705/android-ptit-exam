package com.example.androidptit.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidptit.R;
import com.example.androidptit.interfaces.listener.ItemListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private static String TAG = "MyAdapterTAG";
    private Context mContext;
    private ArrayList<MyItem> mArrayListMyItem;
    private List<Integer> listIcons;
    private ItemListener mItemListener;
    public MyAdapter(ArrayList<MyItem> list, Context context) {
        this.mContext = context;
        this.mArrayListMyItem = list;
    }
    @NonNull
    @Override
    public MyAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyHolder holder, int position) {
        holder.imageViewBookMark.setImageResource(mArrayListMyItem.get(position).getIcon());
        holder.textViewTopicTitle.setText(mArrayListMyItem.get(position).getTopicTitle());
        holder.textViewQuantity.setText(mArrayListMyItem.get(position).getVocabularyQuantity());
        return;
    }

    @Override
    public int getItemCount() {
        return this.mArrayListMyItem.size();
    }
    public void updateAdapter(ArrayList<MyItem> list) {
        this.mArrayListMyItem = list;
        notifyDataSetChanged();
    }

    public void reverseImageList(ArrayList<MyItem> list) {
        Collections.reverse(list);
        notifyDataSetChanged();
    }

    public List<Integer> getListIcons() {
        return listIcons;
    }

    public void setListIcons(List<Integer> listIcons) {
        this.listIcons = listIcons;
    }

    public ItemListener getmItemListener() {
        return mItemListener;
    }

    public void setmItemListener(ItemListener mItemListener) {
        Log.d(TAG, "setmItemListener!!");
        this.mItemListener = mItemListener;
//        if (this.mItemListener == null) {
//            Log.d(TAG, "NULL setmItemListener!!");
//        }
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ImageView imageViewBookMark;
        private TextView textViewTopicTitle, textViewQuantity;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageViewBookMark = itemView.findViewById(R.id.imgBookMark);
            textViewTopicTitle = itemView.findViewById(R.id.txtTopicTitle);
            textViewQuantity = itemView.findViewById(R.id.txtQuantity);

            itemView.findViewById(R.id.cardViewTopicItem).setClickable(true);
            itemView.findViewById(R.id.cardViewTopicItem).setOnClickListener(this);
            itemView.findViewById(R.id.cardViewTopicItem).setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick");
            if (mItemListener != null) {
                mItemListener.onClick(mArrayListMyItem, v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            Log.d(TAG, "onLongClick");
            if (mItemListener != null) {
                mItemListener.onLongClick(mArrayListMyItem, v, getAdapterPosition());
            }
            return true;
        }
    }
}

