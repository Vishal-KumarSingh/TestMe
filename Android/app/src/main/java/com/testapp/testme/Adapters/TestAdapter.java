package com.testapp.testme.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.testapp.testme.ExamSection;
import com.testapp.testme.R;
import com.testapp.testme.LoadingDialog;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {
    private String button_text;
    private JSONArray jsonArray;
    private Context context;
    private LoadingDialog loadingDialog;
    private Class<?> cls;
    public TestAdapter(JSONArray jsonobj, Context context, String button_text , Class<?> cls) {
        jsonArray = jsonobj;
        this.context = context;
        this.cls = cls;
        this.button_text = button_text;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_layout, parent, false);
        return new TestViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        try {
            holder.test_title.setText((CharSequence) jsonArray.getJSONObject(position).get("name"));
            holder.test_topic.setText((CharSequence) jsonArray.getJSONObject(position).get("topic"));
            holder.test_desc.setText((CharSequence) jsonArray.getJSONObject(position).get("description"));
            holder.test_time.setText((CharSequence) jsonArray.getJSONObject(position).get("time") + " minutes");
            holder.startbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, cls);
                    try {
                        i.putExtra("id", (String) jsonArray.getJSONObject(holder.getAdapterPosition()).get("id"));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    context.startActivity(i);
                }
            });

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class TestViewHolder extends RecyclerView.ViewHolder {
        TextView test_title, test_desc, test_time, test_topic;
        ImageButton startbtn;
        RadioGroup optionset;
        int test_id;

        public TestViewHolder(@NonNull View itemView) {

            super(itemView);
            test_title = itemView.findViewById(R.id.test_title);
            test_desc = itemView.findViewById(R.id.test_desc);
            test_time = itemView.findViewById(R.id.test_time);
            test_topic = itemView.findViewById(R.id.test_topic);
            startbtn = itemView.findViewById(R.id.startbtn);
        }
    }
}
