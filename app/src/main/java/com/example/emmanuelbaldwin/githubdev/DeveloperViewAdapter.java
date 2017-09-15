package com.example.emmanuelbaldwin.githubdev;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by EmmanuelBaldwin on 9/4/2017.
 */

public class DeveloperViewAdapter extends RecyclerView.Adapter<DeveloperViewAdapter.MyCustomViewHolder> {
    private List<Developer> developerList;
    private Context context;

    public DeveloperViewAdapter(Context context, List<Developer> developerList) {
        this.developerList = developerList;
        this.context = context;
    }

    @Override
    public MyCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.developer_list_item, null);
        MyCustomViewHolder myCustomViewHolder = new MyCustomViewHolder(view);
        return myCustomViewHolder;
    }

    @Override
    public void onBindViewHolder(MyCustomViewHolder holder, int position) {
        final Developer developer = developerList.get(position);

        if (!TextUtils.isEmpty(developer.getDeveloperImage())) {
            Picasso.with(context)
                    .load(developer.getDeveloperImage())
                    .into(holder.devAvatarImageView);
            Log.i(DeveloperViewAdapter.class.getName(), "Picasso loaded image: " + String.valueOf(position));
        }
        holder.devUserNameTextView.setText(developer.getDeveloperUserName());
        holder.devLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DevProfileActivity.class);
                intent.setType("text/plain");
                intent.putExtra("image", developer.getDeveloperImage());
                intent.putExtra("url", developer.getDeveloperURL());
                intent.putExtra("username", developer.getDeveloperUserName());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return developerList.size();
    }

    class MyCustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView devUserNameTextView;
        protected ImageView devAvatarImageView;
        protected LinearLayout devLinearLayout;

        public MyCustomViewHolder(final View view) {
            super(view);
            devAvatarImageView = (ImageView) view.findViewById(R.id.devImage);
            devUserNameTextView = (TextView) view.findViewById(R.id.devUserName);
            devLinearLayout = (LinearLayout) view.findViewById(R.id.devListRow);

        }
    }
}


