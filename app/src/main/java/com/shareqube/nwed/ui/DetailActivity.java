package com.shareqube.nwed.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shareqube.nwed.R;
import com.shareqube.nwed.ui.adapter.NwedAdapter;
import com.shareqube.nwed.ui.models.DetailsNwed;

/**
 * Created by Jude Ben on 11/3/2015.
 */
public class DetailActivity   extends AppCompatActivity{

    public static final String BOOK_POSITION = "BOOK_POSITION";
    private DetailsNwed mBookDetails = new DetailsNwed();
    private int mBookPosition;
    private ImageView coverNwed ;
    private TextView titleNwed ;
    private TextView author ;
    private TextView publisher ;
    private TextView description ;
    private Toolbar toolbar ;

    NwedAdapter adapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book_detail);
        setSupportActionBar(toolbar);

        mBookPosition = getIntent().getIntExtra(BOOK_POSITION, 0);

        mBookDetails.setVolumeInfo((MainActivity.adapter.getNweds().get(mBookPosition).getVolumeInfo()));
       // mBookDetails.setVolumeInfo(((DataBindingApplication) getApplication()).getBooks().get(mBookPosition).getVolumeInfo());
       // mBookDetails.setVolumeInfo();
        mBookDetails.setIndex(mBookPosition);

        // initialize the views

        coverNwed = (ImageView) findViewById(R.id.book_thumbnail_imageview) ;
        titleNwed = (TextView) findViewById(R.id.book_title_textview) ;
        author = (TextView) findViewById(R.id.book_authors_textview) ;
        publisher = (TextView) findViewById(R.id.book_authors_textview);
        description = ( TextView) findViewById(R.id.book_description) ;

        // update the views

        Log.e("Detail" , "" +mBookDetails.getVolumeInfo().imageLinks.thumbnail);

       Glide.with(getApplicationContext()).load(mBookDetails.getVolumeInfo().imageLinks.thumbnail)
                .error(R.drawable.no_book_cover).into(coverNwed);

        titleNwed.setText(mBookDetails.getVolumeInfo().title);

        author.setText(mBookDetails.getVolumeInfo().authors.get(0));

        publisher.setText(mBookDetails.getVolumeInfo().publisher);

        description.setText(mBookDetails.getVolumeInfo().description);


        // mBindding.setBookDetails(mBookDetails);
    }

    public void onShowNextBook(View view) {
        mBookDetails.setIndex(++mBookPosition);
       // mBookDetails.setVolumeInfo(((DataBindingApplication) getApplication()).getBooks().get(mBookDetails.getIndex()).getVolumeInfo());
    }

    public void onShowPreviousBook(View view) {
        mBookDetails.setIndex(--mBookPosition);
      //  mBookDetails.setVolumeInfo(((DataBindingApplication) getApplication()).getBooks().get(mBookDetails.getIndex()).getVolumeInfo());
    }
}
