package com.example.fitme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class commentAdapter  extends RecyclerView.Adapter<commentAdapter.CommentViewHolder> {

    public commentAdapter(ArrayList<comment_Data> commentArrayList) {
        this.commentArrayList = commentArrayList;
    }

//    // 커스텀 리스너 인터페이스(OnItemClickListener) 정의
//    public interface OnCommentClickListener {
//        void onCommentMenuClick(View v, int position) ; // 리사이클러뷰 아이템 하나 댓글에 있는 다이얼로그 메뉴창을 눌렀을 때
//
//        // 클릭할게 여러개 일 때 여기에 추가해주기
//        // void onButtonClick이런 식으로
//    }
//
//    // 리스너 객체 참조를 저장하는 변수
//    private commentAdapter.OnCommentClickListener mListener = null ;
//
//    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
//    public void setOnItemClickListener(commentAdapter.OnCommentClickListener listener) {
//        this.mListener = listener ;  // 전달된 객체를 저장할 변수 mListener 추가
//    }


    private ArrayList<comment_Data> commentArrayList;

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView textView_comment_nickname;
        public TextView textView_comment_content;
        public ImageView imageView_comment_profile;
        public ImageButton imageButton_comment_spinner;

        public CommentViewHolder(View itemView) {
            super(itemView);
            this.textView_comment_nickname = itemView.findViewById(R.id.textView_comment_nickname);
            this.textView_comment_content = itemView.findViewById(R.id.textView_comment_content);
            this.imageView_comment_profile = itemView.findViewById(R.id.imageView_comment_profile);
            this.imageButton_comment_spinner = itemView.findViewById(R.id.imageButton_comment_spinner);

        }
        // 리사이클러뷰 안에 들어가는 아이템에 있는 버튼을 눌렀을 때의 클릭 리스너
//imageButton_comment_spinner.setOnClickListener(new View.OnClickListener() {
//            //            itemView.setOnClickListener(new View.OnClickListener() {  // 이렇게 하면 해당 버튼을 눌렀다는 코드가 아니라 itemView를 눌렀다는 코드임.
////            @Override
////            public void onClick(View view) {
////                int pos = getAdapterPosition();  // 여기서 어댑터 Postion을 get하면
////                Log.e("Feed 클래스에서 리사이클러뷰 수정 작업중! ", "내가 커스텀한 클릭 리스너에서 getAdapterPostion했습니다");
////
////                // 아이템클릭 이벤트 메서드에서 리스너 객체 메서드 (onItemClick) 호출.
////                if (pos != RecyclerView.NO_POSITION) {
////                    if (mListener != null) {
////                        mListener.onCommentMenuClick(view, pos);   // mListenter는 // 리스너 객체 참조를 저장하는 변수
////                        Log.e("spinner 버튼이 mLister를 통해", "눌렸나요?");
////
//////                        feed_MainData.set(pos, "item clicked. pos=" + pos) ;   // 그 위치 pos에 있는 아이템의 정보가 "" 안에 내용으로 set해줌.
//////                        notifyItemChanged(pos) ;
////                    }
////                }
////            }
////        });
//
//    }
//
//
//
//    public commentAdapter(ArrayList<comment_Data> exampleList) {
//        commentArrayListList = exampleList;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_comment_card, parent, false);
        CommentViewHolder commentViewHolder = new CommentViewHolder(v);
        return commentViewHolder;
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        comment_Data currentItem = commentArrayList.get(position);

        holder.textView_comment_nickname.setText(commentArrayList.get(position).getTextView_comment_nickname());
        holder.textView_comment_content.setText(commentArrayList.get(position).getTextView_comment_content());
        holder.imageView_comment_profile.setImageResource(commentArrayList.get(position).getImageView_comment_profile());

    }

    @Override
    public int getItemCount() {
        return commentArrayList.size();
    }
}
