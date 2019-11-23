package com.example.fitme;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Feed_Main_Adapter extends RecyclerView.Adapter<Feed_Main_Adapter.FeedViewHolder> {
    /**
     * 리뷰 피드 (리사이클러뷰) - 어댑터
     * Feed_Main_Activity, Myrevie_Activity, Bookmarked_review_Activity에서 사용됨.
     **/

    //getItemCount, onCreateViewHolder, MyViewHolder, onBindViewholder 순으로 들어오게 된다.
    // 뷰홀더에서 초기세팅해주고 바인드뷰홀더에서 셋텍스트해주는 값이 최종적으로 화면에 출력되는 값

    // 커스텀 리스너 인터페이스(OnItemClickListener) 정의
    public interface OnItemClickListener {
        void onItemClick(View v, int position);

        void onCommentClick(View v, int position);  // 리사이클러뷰를 가지고 있는 피드에 올라오는 리뷰 카드에 댓글 버튼을 누를 때

        void onBookmarkClick(View v, int position);  // 북마크 버튼을 눌렀을 때

        void onReviewClick(View v, int position); // 리뷰 아이템을 눌렀을 때-> 쇼핑몰 url을 눌렀을 때

        // 클릭할게 여러개 일 때 여기에 추가해주기
        // void onButtonClick이런 식으로
    }

    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;  // 전달된 객체를 저장할 변수 mListener 추가
    }

    // 리사이클러뷰에 넣을 ArrayList

    private Context mContext;  // 이미지 Context 를 활용해서 넣기 위해 추가
    private ArrayList<Feed_Main_ItemData> arrayList, searchingarrayList;//


    public Feed_Main_Adapter(ArrayList<Feed_Main_ItemData> arrayList, Context mContext) {   // 생성자

        this.mContext = mContext; // 이미지 Context 를 활용해서 넣기 위해 추가
        this.arrayList = arrayList;

    }


    @NonNull
    @Override
    public Feed_Main_Adapter.FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_review_card, parent, false);
        FeedViewHolder holder = new FeedViewHolder(view);
        mContext = parent.getContext();// 이미지 picasso 라이브러리 사용시 필요


        return holder;
    }

    // 생성된 뷰홀더에 아이템 내용 세팅하는 곳
    // 생성자로 현제 데이터가 들어오지 않기 때문에 position 값을 이용해서 데이터를 가지고 옴.

    @Override
    // 가져온 값을 set한느 곳. position을 통해 가져오도록 한다
    public void onBindViewHolder(@NonNull final FeedViewHolder holder, int position) {

        Feed_Main_ItemData feed_Main_itemData = arrayList.get(position);

        String review_Image = feed_Main_itemData.getImageView_reviewcard_img1();

        Picasso.get()
                .load(arrayList.get(position).getImageView_reviewcard_img1())
                .fit()
                .centerInside()
                .placeholder(R.drawable.review_plz) // 이미지가 없을 때 기본
                .error(R.drawable.review_plz)// 에러가 났을 때
                .into(holder.imageView_reviewcard_img1);

        String review_profile_Image = feed_Main_itemData.getImageView_reviewcard_profile_image();
        Log.e("Feed_Main_Adapter 클래스 이미지 확인중 ", " : -->" + arrayList.get(position).getImageView_reviewcard_img1());

        Picasso.get()
                .load(arrayList.get(position).getImageView_reviewcard_profile_image())
                .fit()
                .centerInside()
                .placeholder(R.drawable.review_plz) // 이미지가 없을 때 기본
                .error(R.drawable.review_plz)// 에러가 났을 때
                .into(holder.imageView_reviewcard_profile_image);


        Log.e("Feed_Main_Adapter 클래스 이미지 확인중 ", " arrayList.get(position).getImageView_reviewcard_img1(): -->" + arrayList.get(position).getImageView_reviewcard_img1());


        //컨텍스트와 함께 열고 > 이미지를 로딩하고 > 원하는 ImageView에 삽입하면 끝 입니다!
        // 이외에도 이미지 로딩이 완료되기전에 보여줄 .placeholder() 메소드,
        //모서리를 둥글게 하거나 완전한 원으로 만들기 위한 .transform() 메소드 등 이미지와 관련된 작업 대부분을 지원하는 라이브러리 입니다.

        // 이미지를 int로 받을 때  // 지금은 picasso 라이브러리 사용중

        holder.textView_mysize.setText(arrayList.get(position).getTextView_mysize());
        holder.textView_nickname.setText(arrayList.get(position).getTextView_nickname());
        holder.textView_shoppingmall_url.setText(arrayList.get(position).getTextView_shoppingmall_url());
        holder.textView_detailed_review_card.setText(arrayList.get(position).getTextView_detailed_review_card());
        holder.float_ratingBar.setRating(arrayList.get(position).getfloat_ratingBar());
        holder.review_date.setText(arrayList.get(position).getReview_date());
        holder.textView_hashtag.setText(arrayList.get(position).getTextView_hashtag());
        holder.textView_review_writer.setText(arrayList.get(position).getTextView_review_writer());
        holder.textView_reviewcard_number.setText(arrayList.get(position).getTextView_reviewcard_number());

        // 더보기 할 TextVIew가 몇 줄인지 구하는 중
        holder.textView_detailed_review_card.setVisibility(View.VISIBLE);

        // Get the number of lines
        holder.textView_detailed_review_card.post(new Runnable() {
            @Override
            public void run() {
                int lineCount = holder.textView_detailed_review_card.getLineCount();

                Log.d("COUNT", String.valueOf(lineCount));
            }
        });


    }// onBindViewHolder 메소드 닫는 중괄호

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public Feed_Main_ItemData getItem(int position) {
        return this.arrayList.get(position);
    }

    @Override
    public int getItemCount() {

        return (arrayList == null) ? 0 : arrayList.size();
//        if (arrayList == null) {
//            arrayList = new ArrayList<>();
//        }
//        return arrayList.size();   // ArrayList의 사이즈만큼
    }

    //리사이클러뷰 필터링 하는 중 -> 검색 기능// 추가적으로 봐야하는 부분
    // -> Review_searching_Activity 클래스에서 확인하기
    public void filterList(ArrayList<Feed_Main_ItemData> filteredList) {
        arrayList = filteredList; /**여기 수정해야할 수도 있음**/
        notifyDataSetChanged();
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {


        TextView review_date, review_card, textView19, textView_mysize, textView_nickname, textView20, textView_shoppingmall_url, textView_hashtag,
                textView_likes_number, textView_likes, textView_review_writer, textView_reviewcard_number, textView_more, textView_hide,
                textView_detailed_review_card;
        ImageView imageView_reviewcard_profile_image, imageView_reviewcard_img1, imageView_reviewcard_img2, imageView_reviewcard_img3, imageView_reviewcard_img4, imageView_reviewcard_img5;
        ImageButton imageButton_like, imageButton_comment, imageButton_bookmark, imageButton_spinner;
        RatingBar float_ratingBar;
        private Feed_Main_ItemData data;
        private int position;
        View mview;


        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView_reviewcard_profile_image = (ImageView) itemView.findViewById(R.id.imageView_reviewcard_profile_image);
            this.textView_review_writer = (TextView) itemView.findViewById(R.id.textView_review_writer);
            this.textView_reviewcard_number = (TextView) itemView.findViewById(R.id.textView_reviewcard_number);
            this.imageView_reviewcard_img1 = (ImageView) itemView.findViewById(R.id.imageView_reviewcard_img1);
            this.review_date = (TextView) itemView.findViewById(R.id.review_date);
            this.imageButton_comment = (ImageButton) itemView.findViewById(R.id.imageButton_comment);
            this.textView_mysize = (TextView) itemView.findViewById(R.id.textView_mysize);
            this.textView_nickname = (TextView) itemView.findViewById(R.id.textView_nickname);
            this.textView_shoppingmall_url = (TextView) itemView.findViewById(R.id.textView_shoppingmall_url);
            this.textView_likes_number = (TextView) itemView.findViewById(R.id.textView_likes_number);
            this.textView_detailed_review_card = (TextView) itemView.findViewById(R.id.textView_detailed_review_card);
            this.imageButton_spinner = (ImageButton) itemView.findViewById(R.id.imageButton_spinner);
            this.imageButton_bookmark = (ImageButton) itemView.findViewById(R.id.imageButton_bookmark);
            this.float_ratingBar = (RatingBar) itemView.findViewById((R.id.ratingBar));
            this.textView_hashtag = (TextView) itemView.findViewById(R.id.textView_hashtag);
            this.textView_more = (TextView) itemView.findViewById(R.id.textView_more);
            this.mview = itemView.findViewById(R.id.review_card);
            this.textView_hide = itemView.findViewById(R.id.textView_hide);


            //여기서부터
            // 리사이클러뷰 안에 들어가는 아이템에 있는 버튼을 눌렀을 때의 클릭 리스너

            imageButton_spinner.setOnClickListener(new View.OnClickListener() {
                //            itemView.setOnClickListener(new View.OnClickListener() {  // 이렇게 하면 해당 버튼을 눌렀다는 코드가 아니라 itemView를 눌렀다는 코드임.
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();  // 여기서 어댑터 Postion을 get하면
                    Log.e("Feed_Main_Activity Adapter 클래스에서 리사이클러뷰 수정 작업중! ", "내가 커스텀한 onItemClick 리스너에서 getAdapterPostion했습니다");

                    // 아이템클릭 이벤트 메서드에서 리스너 객체 메서드 (onItemClick) 호출.
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(view, pos);   // mListenter는 // 리스너 객체 참조를 저장하는 변수
                            Log.e("spinner 버튼이 mLister를 통해", "눌렸나요?");

                        }
                    }
                }
            });

            // 댓글 창으로 이어지는 리사이클러뷰 아이템 내 버튼
            imageButton_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();  // 여기서 어댑터 Postion을 get하면
                    Log.e("Feed_Main_Activity Adapter 클래스에서 ", "내가 커스텀한 클릭 댓글 버튼 onCommentClick 리스너에서 getAdapterPostion 했습니다");

                    // 아이템클릭 이벤트 메서드에서 리스너 객체 메서드 (onCommentClick) 호출.
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {  // 여기서 막히면 객체 이름바꾸기
                            mListener.onCommentClick(view, pos);   // mListenter는 // 리스너 객체 참조를 저장하는 변수
                            Log.e("Comment_Activity 버튼이 mListener를 통해", "눌렸나요?");


                        }
                    }
                }
            });


            // 북마크한 리뷰 창으로 이어지는 리사이클러뷰 아이템 내(안에) 버튼
            imageButton_bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();  // 여기서 어댑터 Postion을 get하면
                    Log.e("Feed_Main_Activity Adapter에서  ", "내가 커스텀한 클릭 댓글 버튼 onBookmarkClick 리스너에서 getAdapterPostion 했습니다 : " + pos);

                    // 아이템클릭 이벤트 메서드에서 리스너 객체 메서드 (onCommentClick) 호출.
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {  // 여기서 막히면 객체 이름바꾸기
                            mListener.onBookmarkClick(view, pos);   // mListenter는 // 리스너 객체 참조를 저장하는 변수
                            Log.e("bookmark 버튼이 mListener를 통해", "눌렸나요?");


                        }
                    }
                }
            });

            // 리뷰 아이템에 쇼핑몰 url을 클릭했을 때
            textView_shoppingmall_url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();  // 여기서 어댑터 Postion을 get하면
                    Log.e("Feed_Main_Activity Adapter에서  ", "내가 커스텀한 클릭 댓글 버튼 onReviewClick 리스너에서 getAdapterPostion 했습니다 : " + pos);

                    // 아이템클릭 이벤트 메서드에서 리스너 객체 메서드 (onCommentClick) 호출.
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {  // 여기서 막히면 객체 이름바꾸기
                            mListener.onReviewClick(view, pos);   // mListenter는 // 리스너 객체 참조를 저장하는 변수
                            Log.e("쇼핑몰 url이 mListener를 통해", "눌렸나요?");


                        }
                    }
                }
            });


            // 더보기 버튼을 누르면 -> 해당 리뷰가 열리도록
            mview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();  // 여기서 어댑터 Postion을 get하면
                    Log.d("Feed_Main_Activity Adapter에서 리사이클러뷰 수정 작업중! ", "내가 커스텀한 onReviewClick 에서 getAdapterPostion 했습니다 : " + pos);

                    // 상세리뷰 , 쇼핑몰 url, 해시태그
                    System.out.println("Show button");
                    textView_more.setVisibility(View.INVISIBLE);
                    textView_hide.setVisibility(View.VISIBLE);
                    textView_detailed_review_card.setMaxLines(Integer.MAX_VALUE);
                    textView_hashtag.setMaxLines(Integer.MAX_VALUE);


                    // 리뷰 아이템 접기
                    textView_hide.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            System.out.println("Hide button");
                            textView_more.setVisibility(View.VISIBLE);
                            textView_hide.setVisibility(View.INVISIBLE);
                            textView_detailed_review_card.setMaxLines(1);
                            textView_hashtag.setMaxLines(1);
                        }
                    });

                    // 쇼핑몰 url을 클릭하면
                    textView_shoppingmall_url.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });


                }
            });


        }// feedVeiwHolder 메소드 닫는 중괄호


    }// feedVeiwHolder 클래스 닫는 중괄호

}//Feed_Main_Adapter 클래스 닫는 중괄호


