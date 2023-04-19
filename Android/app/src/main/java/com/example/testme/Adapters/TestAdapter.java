package com.example.testme.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.testme.ExamSection;
import com.example.testme.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {
    private JSONArray jsonArray;
    private Context context;
    public TestAdapter(JSONArray jsonobj , Context context) {
        jsonArray=jsonobj;
        this.context=context;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_layout , parent , false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        try {
            holder.test_title.setText((CharSequence) jsonArray.getJSONObject(position).get("name"));
            holder.test_topic.setText((CharSequence) jsonArray.getJSONObject(position).get("topic"));
            holder.test_desc.setText((CharSequence) jsonArray.getJSONObject(position).get("description"));
            holder.test_time.setText((CharSequence) jsonArray.getJSONObject(position).get("time"));
            holder.startbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context , ExamSection.class);
                    try {
                        i.putExtra("id",(String)jsonArray.getJSONObject(holder.getAdapterPosition()).get("id"));
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
        TextView test_title , test_desc, test_time , test_topic;
        Button startbtn;
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
