package com.ethvotingverifier.fragments.settings.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ethvotingverifier.R;

public class AdapterListSettingsItems extends RecyclerView.Adapter<AdapterListSettingsItems.MyViewHolder> {

    private Context context;
    private String titles[];
    private int images[];

    private ClickOnSettingItemListener clickOnSettingItemListener;

    public AdapterListSettingsItems(Context context, String titles[], int images[], ClickOnSettingItemListener clickOnSettingItemListener) {
        this.context = context;
        this.titles = titles;
        this.images = images;

        this.clickOnSettingItemListener = clickOnSettingItemListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_settings_row, parent, false);
        return new MyViewHolder(view, clickOnSettingItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(titles[position]);
        holder.image.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    /************** CLICK LISTENER ************/
    public interface ClickOnSettingItemListener {
        void onItemClick(int position);
    }

    /************** MY VIEW HOLDER ************/
    /*** received from onCreateViewholder *****/

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView title;
        ClickOnSettingItemListener clickOnSettingItemListener;

        public MyViewHolder(@NonNull View itemView, ClickOnSettingItemListener clickOnSettingItemListener) {
            super(itemView);

            image = (ImageView)itemView.findViewById(R.id.settings_item_icon);
            title = (TextView)itemView.findViewById(R.id.settings_item_title);

            this.clickOnSettingItemListener = clickOnSettingItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickOnSettingItemListener.onItemClick(getAdapterPosition());
        }
    }
}
