package com.example.thirdhw;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.TextViewHolder> {

    final private MainActivity parent;

    final private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = Integer.parseInt(((TextView)v).getText().toString());

            Bundle arguments = new Bundle();
            arguments.putString(ImageDetailFragment.IMAGE_URL_PARAM, Global.urlList.get(pos));
            ImageDetailFragment fragment = new ImageDetailFragment();
            fragment.setArguments(arguments);
            parent.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        }
    };

    ImageListAdapter(MainActivity parent) {
        this.parent = parent;
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
        holder.setText(position);
    }

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.text_list_item, viewGroup, false);
        return new TextViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return Global.urlList.size();
    }

    class TextViewHolder extends RecyclerView.ViewHolder {

        int pos;

        final private TextView textView;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            textView.setOnClickListener(onClickListener);
        }

        public void setText(int pos) {
            this.pos = pos;
            textView.setText(Integer.toString(pos));
        }
    }
}
