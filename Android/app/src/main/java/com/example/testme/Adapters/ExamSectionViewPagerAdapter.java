package com.example.testme.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testme.AnswerKey;
import com.example.testme.Debug.Debug;
import com.example.testme.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Objects;

public class ExamSectionViewPagerAdapter extends  RecyclerView.Adapter<ExamSectionViewPagerAdapter.QuestionViewHolder> {
    Context context;
    JSONArray arr;
    AnswerKey answer;

    public ExamSectionViewPagerAdapter(Context context, JSONArray arr, AnswerKey answer) {
        this.context = context;
        this.arr = arr;
        this.answer = answer;
    }


    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.question , parent , false);
        return new QuestionViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        try {
            holder.question.setText(arr.getJSONArray(position).getJSONObject(0).getString("description"));
            holder.optionA.setText("1"+arr.getJSONArray(position).getJSONObject(0).getString("optionA"));
            holder.optionB.setText("2"+arr.getJSONArray(position).getJSONObject(0).getString("optionB"));
            holder.optionC.setText("3"+arr.getJSONArray(position).getJSONObject(0).getString("optionC"));
            holder.optionD.setText("4"+arr.getJSONArray(position).getJSONObject(0).getString("optionD"));
            holder.solution.setText("");
            holder.optionset.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    Debug.log("Question no " + holder.getAdapterPosition()+ " value selected " + i);
                    if(radioGroup.getCheckedRadioButtonId() != -1) {
                        RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                        String s = (String) radioButton.getText();
                        Debug.log(String.valueOf(s.charAt(0)));
                        answer.setAnswer(holder.getAdapterPosition(), s);
                    }
                }
            });
            holder.resetbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.optionset.clearCheck();;
                }
            });
            holder.favourites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!holder.favourite){
                        holder.favourite = true;
                        holder.favourites.setImageResource(R.drawable.baseline_star_24);
                    }else{
                        holder.favourite = false;
                        holder.favourites.setImageResource(R.drawable.star);
                    }
                }
            });
            holder.mark_for_review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!holder.review){
                        holder.review = true;
                        holder.mark_for_review.setImageResource(R.drawable.review_marked);
                    }else{
                        holder.review = false;
                        holder.mark_for_review.setImageResource(R.drawable.note_add);
                    }
                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public int getItemCount() {
        return arr.length();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder{
        public RadioButton optionA , optionB, optionC , optionD;
        public int question_no;
        public RadioGroup optionset;
        public TextView question;
        public ImageButton resetbtn, mark_for_review, favourites;
        public boolean favourite , review;
        public MultiAutoCompleteTextView solution;
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);
            optionset = itemView.findViewById(R.id.optionset);
            question = itemView.findViewById(R.id.question);
            solution  = itemView.findViewById(R.id.solution);
            resetbtn = itemView.findViewById(R.id.resetAnswer);
            mark_for_review = itemView.findViewById(R.id.markforreview);
            favourites = itemView.findViewById(R.id.favourites);
            favourite = false;
            review = false;
        }
    }
}
