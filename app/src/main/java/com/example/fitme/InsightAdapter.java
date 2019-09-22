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

public class InsightAdapter extends RecyclerView.Adapter<InsightAdapter.ExampleViewHolder> {
private Context mContext;
private ArrayList<InsightItemData> insight_arrayList;
private OnItemClickListener mListener;

public interface OnItemClickListener {
    void onItemClick(int position);
}

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public InsightAdapter(Context context, ArrayList<InsightItemData> insightarrayList) {
        mContext = context;
        insight_arrayList = insightarrayList;
    }
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.example_item, parent, false);
        return new ExampleViewHolder(v);
    }




    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        InsightItemData insightItemData = insight_arrayList.get(position);


// 이거 참고해서 바꾸기 : String title, String link, String image, String mallName, int lprice, int hprice

        String title = insightItemData.getTextView_waist();
        String link = insightItemData.getTextView_sero();
        String image = insightItemData.getTextView_long();
        String mallName = insightItemData.getTextView_bugGi();
        String lprice = insightItemData.getTextView_ancle();
        String hprice = insightItemData.getText_view_fitname();
        String image_view = insightItemData.getImage_view();

        holder.text_view_fitname.setText(title);
        holder.textView_waist.setText("링크 : " + link);
        holder.textView_sero.setText(mallName);
        holder.textView_bugGi.setText(lprice);
        holder.textView_long.setText(hprice);
        holder.textView_ancle.setText(hprice);
        Picasso.get().load(image).fit().centerInside().into(holder.image_view);
    }

    @Override
    public int getItemCount() {
        return insight_arrayList.size();
    }

public class ExampleViewHolder extends RecyclerView.ViewHolder {
    //text_view_title
    //text_view_link
    //image_view
    //text_view_mallName
    //text_view_lPrice
    //text_view_HPrice
    public ImageView image_view;
    public TextView text_view_fitname,textView_waist,textView_sero,textView_bugGi,textView_long, textView_ancle;

    public ExampleViewHolder(View itemView) {
        super(itemView);
        image_view = itemView.findViewById(R.id.image_view);
        text_view_fitname = itemView.findViewById(R.id.text_view_fitname);
        textView_waist = itemView.findViewById(R.id.textView_waist);
        textView_sero = itemView.findViewById(R.id.textView_sero);
        textView_bugGi = itemView.findViewById(R.id.textView_bugGi);
        textView_long = itemView.findViewById(R.id.textView_long);
        textView_ancle = itemView.findViewById(R.id.textView_ancle);


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
