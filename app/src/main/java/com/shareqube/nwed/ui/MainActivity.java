package com.shareqube.nwed.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shareqube.nwed.R;
import com.shareqube.nwed.ui.adapter.NwedAdapter;
import com.shareqube.nwed.ui.api.NwedService;
import com.shareqube.nwed.ui.listener.RecyclerItemClickListener;
import com.shareqube.nwed.ui.models.Nwed;
import com.shareqube.nwed.ui.models.SearchResults;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.Callback;

public class MainActivity extends AppCompatActivity {

    String LOG_TAG = MainActivity.class.getSimpleName() ;

    RecyclerView rvBooks ;


    protected EditText mSearchEditText;

    protected Button searchButton ;


    protected ProgressBar mProgressBar;


    List<Nwed> nwedList  = new ArrayList<>();

    private NwedService nwedService ;
    public static NwedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mSearchEditText = (EditText) findViewById(R.id.search_edit_text) ;

        mProgressBar = (ProgressBar)findViewById(R.id.progress_bar) ;

        searchButton  = (Button) findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSearchRequest();
            }
        });

        adapter = new NwedAdapter(nwedList);

        // Create adapter passing in
        rvBooks = (RecyclerView) findViewById(R.id.rvBooks) ;
        rvBooks.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);



        // Attach the adapter to the recyclerview to populate items
        rvBooks.setLayoutManager(llm);
        rvBooks.setAdapter(adapter);
        rvBooks.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position, float x, float y) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                intent.putExtra(DetailActivity.BOOK_POSITION, position);




                startActivity(intent);
            }
        }));
        init();





       mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
           @Override
           public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
               if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                   handleSearchRequest();
                   return true;
               }
               return false;
           }
       });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init() {

        Log.e(LOG_TAG, " init is called") ;
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://www.googleapis.com")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        nwedService = restAdapter.create(NwedService.class);
    }

    private void handleSearchRequest() {
        String query = mSearchEditText.getText().toString().trim();
        if (TextUtils.isEmpty(query)) {
            mSearchEditText.setError(getString(R.string.required));
        } else {
            searchNwedsByQuery(query);
        }
    }


    private void searchNwedsByQuery(String query) {
        hideKeyboard();
        displayProgress(true);
        nwedService.search(query, mSearchResultsCallback);
    }

    private Callback<SearchResults> mSearchResultsCallback = new Callback<SearchResults>() {
        @Override
        public void success(SearchResults searchResults, Response response) {
            Log.e(LOG_TAG ,"success");
            displayResults(searchResults);
        }

        @Override
        public void failure(RetrofitError error) {
            displayError(error);
        }
    };

    private void displayResults(SearchResults searchResults) {
        Log.e(LOG_TAG, "Total search result " + searchResults.totalItems);
        Log.d( LOG_TAG ,"Got results: " + searchResults.books.size());

        displayProgress(false);

        if (adapter == null) {
            Log.e(LOG_TAG , " adapter is null") ;
            adapter = new NwedAdapter(searchResults.books);
            rvBooks.setAdapter(adapter);
            adapter.notifyDataSetChanged();
           adapter.setBooks(searchResults.books); ;




        } else {
            Log.e(LOG_TAG, "Adapter not null");
            nwedList.addAll(searchResults.books);
            adapter.getNweds().clear();
            adapter.getNweds().addAll(searchResults.books);
            adapter.notifyDataSetChanged();
        }
    }

    private void displayError(RetrofitError error) {
       Log.e(LOG_TAG, "Search failed with error: " + error.getKind());

        displayProgress(false);

        if ( adapter.getNweds() != null) {
            adapter.getNweds().clear();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }

        if (error.getKind().equals(RetrofitError.Kind.NETWORK)) {
            Toast.makeText(this, getString(R.string.msg_error_network), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, getString(R.string.msg_error_generic), Toast.LENGTH_LONG).show();
        }
    }

    private void displayProgress(boolean show) {
        if (show) {
          rvBooks.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
           rvBooks.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
