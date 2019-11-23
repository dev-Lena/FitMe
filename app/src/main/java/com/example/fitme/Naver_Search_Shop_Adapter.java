package com.example.fitme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/** Naver_Search_Shop_Adapter와 Naver_Search_Shop_Item, Naver_Search_Shop_Main, Naver_Search_Shop_Detail 자바 클래스는 네이버 Shop API 한세트임**/

public class Naver_Search_Shop_Adapter extends RecyclerView.Adapter<Naver_Search_Shop_Adapter.ExampleViewHolder> {
private Context mContext;
private ArrayList<Naver_Search_Shop_Item> mExampleList;
private OnItemClickListener mListener;

public interface OnItemClickListener {
    void onItemClick(int position);
}

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public Naver_Search_Shop_Adapter(Context context, ArrayList<Naver_Search_Shop_Item> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.example_item, parent, false);
        return new ExampleViewHolder(v);
    }




    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Naver_Search_Shop_Item currentItem = mExampleList.get(position);


// 이거 참고해서 바꾸기 : String title, String link, String image, String mallName, int lprice, int hprice

        String title = currentItem.getTitle();
        String link = currentItem.getLink();
        String image = currentItem.getImage();
        String mallName = currentItem.getMallName();
        String lprice = currentItem.getLprice();
        String hprice = currentItem.getHprice();

        holder.text_view_title.setText(title);
        holder.text_view_link.setText("링크 : " + link);
        holder.text_view_mallName.setText(mallName);
        holder.text_view_lPrice.setText(lprice);
        holder.text_view_HPrice.setText(hprice);
        Picasso.get().load(image).fit().centerInside().into(holder.image_view);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

public class ExampleViewHolder extends RecyclerView.ViewHolder {

    public ImageView image_view;
    public TextView text_view_link,text_view_title,text_view_mallName,text_view_lPrice,text_view_HPrice;

    public ExampleViewHolder(View itemView) {
        super(itemView);
        image_view = itemView.findViewById(R.id.image_view);
        text_view_link = itemView.findViewById(R.id.text_view_link);
        text_view_title = itemView.findViewById(R.id.text_view_title);
        text_view_mallName = itemView.findViewById(R.id.text_view_mallName);
        text_view_lPrice = itemView.findViewById(R.id.text_view_lPrice);
        text_view_HPrice = itemView.findViewById(R.id.text_view_HPrice);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(position);
                    }
                }
            }
        });
    }
}
}
