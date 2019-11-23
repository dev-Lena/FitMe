package com.example.fitme;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public  class Mypage_Adapter extends RecyclerView.Adapter<Mypage_Adapter.FeedViewHolder> {
    /**
     * 마이페이지 (내가 쓴 리뷰 -> 리사이클러뷰) - 어댑터
     **/

    // 커스텀 리스너 인터페이스(OnItemClickListener) 정의
    public interface OnItemClickListener {
        void onItemClick(View v, int position); // 이미지를 클릭하면

        // 클릭할게 여러개 일 때 여기에 추가해주기
        // void onButtonClick이런 식으로
    }

    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public  void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;  // 전달된 객체를 저장할 변수 mListener 추가
    }

    // 리사이클러뷰에 넣을 ArrayList

    private Context mContext;  // 이미지 Context 를 활용해서 넣기 위해 추가
    private ArrayList<Feed_Main_ItemData> mypage_arrayList, searchingarrayList;//


    public Mypage_Adapter(ArrayList<Feed_Main_ItemData> arrayList, Context mContext) {   // 생성자

        this.mContext = mContext; // 이미지 Context 를 활용해서 넣기 위해 추가
        this.mypage_arrayList = arrayList;

    }


    @NonNull
    @Override
    public Mypage_Adapter.FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mypage_myreview_card, parent, false);
        FeedViewHolder holder = new FeedViewHolder(view);
        mContext = parent.getContext();// 이미지 picasso 라이브러리 사용시 필요


        return holder;
    }

    @Override
    // 가져온 값을 set한느 곳. position을 통해 가져오도록 한다
    public void onBindViewHolder(@NonNull final FeedViewHolder holder, int position) {

        Feed_Main_ItemData feed_Main_itemData = mypage_arrayList.get(position);

        String review_Image = feed_Main_itemData.getImageView_reviewcard_img1();

        Picasso.get()
                .load(mypage_arrayList.get(position).getImageView_reviewcard_img1())
                .fit()
                .centerInside()
                .placeholder(R.drawable.review_plz) // 이미지가 없을 때 기본
                .error(R.drawable.review_plz)// 에러가 났을 때
                .into(holder.imageView_reviewcard_img1);


        //컨텍스트와 함께 열고 > 이미지를 로딩하고 > 원하는 ImageView에 삽입하면 끝 입니다!
        // 이외에도 이미지 로딩이 완료되기전에 보여줄 .placeholder() 메소드,
        //모서리를 둥글게 하거나 완전한 원으로 만들기 위한 .transform() 메소드 등 이미지와 관련된 작업 대부분을 지원하는 라이브러리 입니다.

        // 이미지를 int로 받을 때  // 지금은 picasso 라이브러리 사용중


        holder.review_date.setText(mypage_arrayList.get(position).getReview_date());
        holder.textView_review_writer.setText(mypage_arrayList.get(position).getTextView_review_writer());
        holder.textView_reviewcard_number.setText(mypage_arrayList.get(position).getTextView_reviewcard_number());



    }// onBindViewHolder 메소드 닫는 중괄호

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public Feed_Main_ItemData getItem(int position) {
        return this.mypage_arrayList.get(position);
    }

    @Override
    public int getItemCount() {

        return (mypage_arrayList == null) ? 0 : mypage_arrayList.size();
//        if (arrayList == null) {
//            arrayList = new ArrayList<>();
//        }
//        return arrayList.size();   // ArrayList의 사이즈만큼
    }

    //리사이클러뷰 필터링 하는 중 -> 검색 기능// 추가적으로 봐야하는 부분
    // -> Review_searching_Activity 클래스에서 확인하기
    public void filterList(ArrayList<Feed_Main_ItemData> filteredList) {
        mypage_arrayList = filteredList;
        notifyDataSetChanged();
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {


        TextView review_date, textView_review_writer, textView_reviewcard_number;
        ImageView imageView_reviewcard_img1, imageView_reviewcard_img2, imageView_reviewcard_img3, imageView_reviewcard_img4, imageView_reviewcard_img5;
        private Feed_Main_ItemData data;
        private int position;
        CardView mview;


        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView_review_writer = (TextView) itemView.findViewById(R.id.textView_review_writer);
            this.textView_reviewcard_number = (TextView) itemView.findViewById(R.id.textView_reviewcard_number);
            this.imageView_reviewcard_img1 = (ImageView) itemView.findViewById(R.id.imageView_reviewcard_img1);
            this.review_date = (TextView) itemView.findViewById(R.id.review_date);
            this.mview = itemView.findViewById(R.id.mypage_myreview_card);

            //여기서부터
            // 리사이클러뷰 안에 들어가는 아이템에 있는 버튼을 눌렀을 때의 클릭 리스너

            mview.setOnClickListener(new View.OnClickListener() {
                //            itemView.setOnClickListener(new View.OnClickListener() {  // 이렇게 하면 해당 버튼을 눌렀다는 코드가 아니라 itemView를 눌렀다는 코드임.
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();  // 여기서 어댑터 Postion을 get하면
                    Log.e("Mypage_Adapter 클래스에서 리사이클러뷰 수정 작업중! ", "내가 커스텀한 onItemClick 리스너에서 getAdapterPostion했습니다");

                    // 아이템클릭 이벤트 메서드에서 리스너 객체 메서드 (onItemClick) 호출.
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(view, pos);   // mListenter는 // 리스너 객체 참조를 저장하는 변수
                            Log.e("spinner 버튼이 mLister를 통해", "눌렸나요?");


                        }
                    }
                }
            });

        }// feedVeiwHolder 메소드 닫는 중괄호


        }// feedVeiwHolder 클래스 닫는 중괄호

    }//Feed_Main_Adapter 클래스 닫는 중괄호


