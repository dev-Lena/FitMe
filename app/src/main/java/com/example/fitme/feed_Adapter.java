package com.example.fitme;

import android.content.Context;
import android.net.Uri;
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

public class feed_Adapter extends RecyclerView.Adapter<feed_Adapter.FeedViewHolder> {

    Uri uri; // 전역변수로 Uri를 선언해줘야 클래스 내 다른 메소드 내에서도 사용할 수 있음.

    // 리사이클러뷰에 이미지 넣는 중


    //getItemCount, onCreateViewHolder, MyViewHolder, onBindViewholder 순으로 들어오게 된다.
    // 뷰홀더에서 초기세팅해주고 바인드뷰홀더에서 셋텍스트해주는 값이 최종적으로 화면에 출력되는 값

    // 커스텀 리스너 인터페이스(OnItemClickListener) 정의
    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
        void onCommentClick(View v, int position) ;  // 리사이클러뷰를 가지고 있는 피드에 올라오는 리뷰 카드에 댓글 버튼을 누를 때
        void onBookmarkClick(View v, int position) ;  // 북마크 버튼을 눌렀을 때

        // 클릭할게 여러개 일 때 여기에 추가해주기
        // void onButtonClick이런 식으로
    }

    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null ;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;  // 전달된 객체를 저장할 변수 mListener 추가
    }


    // 리사이클러뷰에 넣을 ArrayList

    private Context mContext;  // 이미지 Context 를 활용해서 넣기 위해 추가
    private ArrayList<feed_MainData> arrayList;//

    public feed_Adapter(ArrayList<feed_MainData> arrayList, Context mContext) {   // 생성자
//    public feed_Adapter(Context context, ArrayList<feed_MainData> arrayList) {   // 생성자

        this.mContext = mContext; // 이미지 Context 를 활용해서 넣기 위해 추가
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public feed_Adapter.FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_review_card, parent, false);
        FeedViewHolder holder = new FeedViewHolder(view);
        mContext= parent.getContext();// 이미지 picasso 라이브러리 사용시 필요

        return holder;
    }

    @Override
    // 생성된 뷰홀더에 아이템 내용 세팅하는 곳
    // 생성자로 현제 데이터가 들어오지 않기 때문에 position 값을 이용해서 데이터를 가지고 옴.


    // 가져온 값을 set한느 곳. position을 통해 가져오도록 한다
    public void onBindViewHolder(@NonNull final FeedViewHolder holder, int position) {

        feed_MainData feed_mainData = arrayList.get(position);

//        feed_MainData data = arrayList.get(position);//위치에 따라서 그에 맞는 데이터를 얻어오게 한다.
//        holder.personalId.setText(data.getId());//앞서 뷰홀더에 세팅해준 것을 각 위치에 맞는 것들로 보여주게 하기 위해서 세팅해준다.

        // 피카소 라이브러리 기본 형식
//        Picasso.get()
//                .load("https://avatars0.githubusercontent.com/u/1?v=4") // 여기에 Now we need a URL to an ImageFile.
//                .placeholder(R.drawable.ic_image_black_24dp) // optional the image to display while the url image is downloading
//                .error(R.drawable.ic_error_black_24dp) //this is also optional if some error has occurred in downloading the image                 //this image would be displayed
//                .into(imageView);

        String review_Image = feed_mainData .getImageView_reviewcard_img1();


//        File f = new File("path-to-file/file.png");
        Picasso.get()
                .load(arrayList.get(position).getImageView_reviewcard_img1())

//                .load(review_Image)
                .fit()
                .centerInside()
//                .placeholder(R.drawable.review_plz) // 이미지가 없을 때 기본
//                .error(R.drawable.review_plz)// 에러가 났을 때
                .into(holder.imageView_reviewcard_img1);






        Log.e("이미지 확인중 ", " : [이전 방식 arrayList position 사용 ]---------------->" + arrayList.get(position).getImageView_reviewcard_img1());
        Log.e("이미지 확인중 ", " : [지금 방식 review_Image]---------------->" + review_Image);


//        String review_imageUrl = feed_mainData.getImageView_reviewcard_profile_image();
//        Picasso.get().load(review_imageUrl).fit().centerInside().into(holder.imageView_reviewcard_profile_image);




        //컨텍스트와 함께 열고 > 이미지를 로딩하고 > 원하는 ImageView에 삽입하면 끝 입니다!

        // 이외에도 이미지 로딩이 완료되기전에 보여줄 .placeholder() 메소드,
        //
        //모서리를 둥글게 하거나 완전한 원으로 만들기 위한 .transform() 메소드 등 이미지와 관련된 작업 대부분을 지원하는 라이브러리 입니다.

        // 이미지를 int로 받을 때  // 지금은 picasso 라이브러리 사용중
//        holder.imageView_reviewcard_profile_image.setImageResource(arrayList.get(position).getImageView_reviewcard_profile_image());

//        holder.imageView_reviewcard_img1.setImageURI(arrayList.get(position).getImageView_reviewcard_img1());

        holder.textView_mysize.setText(arrayList.get(position).getTextView_mysize());
        holder.textView_nickname.setText(arrayList.get(position).getTextView_nickname());
//        holder.textView20.setText(arrayList.get(position).getTextView20());
//        holder.review_date.setText(arrayList.get(position).getTextView_detailed_review_card());
        holder.textView_shoppingmall_url.setText(arrayList.get(position).getTextView_shoppingmall_url());
//        holder.textView_likes_number.setText(arrayList.get(position).getTextView_likes_number());
//        holder.textView_likes.setText(arrayList.get(position).getTextView_likes());
        holder.textView_detailed_review_card.setText(arrayList.get(position).getTextView_detailed_review_card());
        holder.float_ratingBar.setRating(arrayList.get(position).getfloat_ratingBar());
        holder.review_date.setText(arrayList.get(position).getReview_date());
        holder.textView_hashtag.setText(arrayList.get(position).getTextView_hashtag());
        holder.textView_review_writer.setText(arrayList.get(position).getTextView_review_writer());
        holder.textView_reviewcard_number.setText(arrayList.get(position).getTextView_reviewcard_number());

// 예제        holder.imageView.setImageResource(mItems.get(position).image);

//        holder.imageButton_spinner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("spinner 버튼이", "클릭이 되나 봅시다");
//
//            }
//        });
// holder.itemView.setTag(position);  // -> findViewById와 같은 기능

                holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //클릭했을 때 어떤 걸 할건지

            }
        });

//        // 길게 눌렀을 때 어떤 걸 할건지
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                return true;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();   // ArrayList의 사이즈만큼
    }


    public class FeedViewHolder extends RecyclerView.ViewHolder {


        TextView review_date, review_card, textView19, textView_mysize, textView_nickname, textView20, textView_shoppingmall_url,textView_hashtag,
                textView_likes_number, textView_likes, textView_review_writer, textView_reviewcard_number,
                textView_detailed_review_card, textView_more, textView_hashtag1, textView_hashtag2, textView_hashtag3, textView_hashtag4;
       ImageView imageView_reviewcard_profile_image, imageView_reviewcard_img1, imageView_reviewcard_img2, imageView_reviewcard_img3, imageView_reviewcard_img4, imageView_reviewcard_img5;
        ImageButton imageButton_like, imageButton_comment, imageButton_bookmark, imageButton_spinner;
        RatingBar float_ratingBar;

//        String textView_shoppingmall_url, textView_detailed_review_card, int_ratingBar, textView_hashtag;


        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView_reviewcard_profile_image = (ImageView) itemView.findViewById(R.id.imageView_reviewcard_profile_image);
            this.textView_review_writer = (TextView) itemView.findViewById(R.id.textView_review_writer);
            this.textView_reviewcard_number = (TextView) itemView.findViewById(R.id. textView_reviewcard_number);
            this.imageView_reviewcard_img1 = (ImageView) itemView.findViewById(R.id.imageView_reviewcard_img1);
//            this.imageButton_like = (ImageButton) itemView.findViewById(R.id.imageButton_like);
            this.review_date = (TextView) itemView.findViewById(R.id.review_date);
            this.imageButton_comment = (ImageButton) itemView.findViewById(R.id.imageButton_comment);
//            this.imageButton_bookmark = (ImageButton) itemView.findViewById(R.id.imageButton_bookmark);
//            this.review_card = (TextView) itemView.findViewById(R.id.review_card);
            this.textView_mysize = (TextView) itemView.findViewById(R.id.textView_mysize);
            this.textView_nickname = (TextView) itemView.findViewById(R.id.textView_nickname);
            this.textView_shoppingmall_url = (TextView) itemView.findViewById(R.id.textView_shoppingmall_url);
//            this.textView_likes_number = (TextView) itemView.findViewById(R.id.textView_likes_number);
//            this.textView_likes = (TextView) itemView.findViewById(R.id.textView_likes);
            this.textView_detailed_review_card = (TextView) itemView.findViewById(R.id.textView_detailed_review_card);
            this.imageButton_spinner = (ImageButton) itemView.findViewById(R.id.imageButton_spinner);
            this.imageButton_bookmark = (ImageButton) itemView.findViewById(R.id.imageButton_bookmark);
            this.float_ratingBar = (RatingBar) itemView.findViewById((R.id.ratingBar)) ;
//            this.textView_more = (TextView) itemView.findViewById(R.id.textView_more);
            this.textView_hashtag = (TextView) itemView.findViewById(R.id.textView_hashtag);
//            this.textView_hashtag2 = (TextView) itemView.findViewById(R.id.textView_hashtag2);

// 리사이클러뷰 수정에서 Adapter에 있는 클릭 이벤트를feed에서 구현하기 위해서 ( 인텐트로 데이터 전달 )
//            itemView.setOnClickListener(new View.OnClickListener() {
            // 어댑터 내 뷰홀더에서 아이템 클릭시, 커스텀 이벤트 메서드를 호출하는 코드 작성.



//여기서부터
            // 리사이클러뷰 안에 들어가는 아이템에 있는 버튼을 눌렀을 때의 클릭 리스너

            imageButton_spinner.setOnClickListener(new View.OnClickListener() {
                //            itemView.setOnClickListener(new View.OnClickListener() {  // 이렇게 하면 해당 버튼을 눌렀다는 코드가 아니라 itemView를 눌렀다는 코드임.
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();  // 여기서 어댑터 Postion을 get하면
                    Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중! ", "내가 커스텀한 클릭 리스너에서 getAdapterPostion했습니다");

                    // 아이템클릭 이벤트 메서드에서 리스너 객체 메서드 (onItemClick) 호출.
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(view, pos);   // mListenter는 // 리스너 객체 참조를 저장하는 변수
                            Log.e("spinner 버튼이 mLister를 통해", "눌렸나요?");

//                        feed_MainData.set(pos, "item clicked. pos=" + pos) ;   // 그 위치 pos에 있는 아이템의 정보가 "" 안에 내용으로 set해줌.
//                        notifyItemChanged(pos) ;
                        }
                    }
                }
            });

            // 댓글 창으로 이어지는 리사이클러뷰 아이템 내 버튼
            imageButton_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();  // 여기서 어댑터 Postion을 get하면
                    Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중! ", "내가 커스텀한 클릭 댓글 버튼 onCommentClick 리스너에서 getAdapterPostion 했습니다");

                    // 아이템클릭 이벤트 메서드에서 리스너 객체 메서드 (onCommentClick) 호출.
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {  // 여기서 막히면 객체 이름바꾸기
                            mListener.onCommentClick(view, pos);   // mListenter는 // 리스너 객체 참조를 저장하는 변수
                            Log.e("comment 버튼이 mListener를 통해", "눌렸나요?");

//                        feed_MainData.set(pos, "item clicked. pos=" + pos) ;   // 그 위치 pos에 있는 아이템의 정보가 "" 안에 내용으로 set해줌.
//                        notifyItemChanged(pos) ;

                        }
                    }
                }
            });


            // 북마크한 리뷰 창으로 이어지는 리사이클러뷰 아이템 내(안에) 버튼
            imageButton_bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();  // 여기서 어댑터 Postion을 get하면
                    Log.e("Feed Adapter에서 리사이클러뷰 수정 작업중! ", "내가 커스텀한 클릭 댓글 버튼 imageButton_bookmark 리스너에서 getAdapterPostion 했습니다 : " + pos);

                    // 아이템클릭 이벤트 메서드에서 리스너 객체 메서드 (onCommentClick) 호출.
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {  // 여기서 막히면 객체 이름바꾸기
                            mListener.onBookmarkClick(view, pos);   // mListenter는 // 리스너 객체 참조를 저장하는 변수
                            Log.e("bookmark 버튼이 mListener를 통해", "눌렸나요?");



                        }
                    }
                }
            });


        }
    }


}//feed_Adapter 클래스 닫는 중괄호
