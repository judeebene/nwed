package com.shareqube.nwed.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shareqube.nwed.R;
import com.shareqube.nwed.ui.models.Nwed;

import java.util.List;

/**
 * Created by Jude Ben on 11/2/2015.
 */
public class NwedAdapter extends RecyclerView.Adapter<NwedAdapter.ViewHolder> {

    private List<Nwed> nwed;
    Context context;
    @Override
    public NwedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.eachrow, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;

    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        // Get the data model based on position
        Nwed mNwed = nwed.get(position);

        // Set item views based on the data model
        Glide.with(context).load(mNwed.getVolumeInfo().imageLinks.thumbnail).error(R.drawable.no_book_cover).into(holder.coverNwed);



       holder.titleNwed.setText(mNwed.getVolumeInfo().title);



        holder.awedNwed.setText(mNwed.getVolumeInfo().authors.get(0));




    }

    @Override
    public int getItemCount() {
        return nwed == null ? 0 : nwed.size();
    }

    public NwedAdapter( List<Nwed> nwed){
        this.nwed = nwed ;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row

        public ImageView coverNwed ;
        public TextView titleNwed;
        public TextView awedNwed ;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            coverNwed = (ImageView) itemView.findViewById(R.id.coverNwed) ;
            titleNwed = (TextView) itemView.findViewById(R.id.titleNwed) ;
            awedNwed = (TextView) itemView.findViewById(R.id.awedNwed) ;
        }
    }



    public List<Nwed> getNweds() {
        return nwed;
    }

    public void setBooks(List<Nwed> mResultsist) {
        this.nwed = mResultsist;
    }

}
