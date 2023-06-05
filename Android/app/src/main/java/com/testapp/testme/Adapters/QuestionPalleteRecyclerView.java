package com.testapp.testme.Adapters;

import android.content.Context;
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

import com.testapp.testme.AnswerKey;
import com.testapp.testme.R;

import org.json.JSONArray;

public class QuestionPalleteRecyclerView extends  RecyclerView.Adapter<QuestionPalleteRecyclerView.QuestionViewHolder> {

    Context context;
    JSONArray arr;
    AnswerKey answer;
    boolean showSolution;

    public QuestionPalleteRecyclerView(Context context, JSONArray arr) {
        this.context = context;
        this.arr = arr;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.question , parent , false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return arr.length();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
