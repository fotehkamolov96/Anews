/**
 * Contact
 * telegram: @kamolovme
 * email: kamolov-9696@mail.ru
 */
package com.kamolovproducts.anews.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.kamolovproducts.anews.R;
import com.kamolovproducts.anews.adapter.AdapterListNews;
import com.kamolovproducts.anews.constatns.NewsConstants;
import com.kamolovproducts.anews.data.NewsModel;
import com.kamolovproducts.anews.utils.LocaleHelper;
import com.kamolovproducts.anews.utils.Util;
import com.kamolovproducts.anews.viewmd.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.eng)
    ImageView ic_eng;

    private MainViewModel viewModel;
    AdapterListNews adapterListNews;
    List<NewsModel> newsList;
    private final String countryPositionPref = "countryPositionPref";
    SharedPreferences pref;
    private String[] countrys;
    private TypedArray countrysIcons;
    private LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getApplicationContext().getSharedPreferences(Util.APP_NAME, MODE_PRIVATE);
        setContentView(R.layout.activity_main);

        /**
         * default pointers that we add
         */
        //context = this;
        ButterKnife.bind(this);
        countrys = getResources().getStringArray(R.array.countrys);
        countrysIcons = getResources().obtainTypedArray(R.array.countrysIcons);
        if (pref.contains(countryPositionPref))
            ic_eng.setImageResource(countrysIcons.getResourceId(pref.getInt(countryPositionPref, 0), 0));
        newsList = new ArrayList<>();
        adapterListNews = new AdapterListNews(this, newsList);
        llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapterListNews);


        /**
         * All ViewModel Content
         */

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getNewsLiveData().observe(this, newsListUpdateObserver);
        viewModel.setApiKey(NewsConstants.PARS_KEY);
        viewModel.setCountryCode(pref.getString(Util.COUNTRY_PREF, "tr"));
        ic_eng.setOnClickListener(view -> showLanguageDialog());

    }


    /**
     * A handler for selecting languages.
     */
    private void showLanguageDialog() {
        new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle)
                .setCancelable(false)
                .setTitle(NewsConstants.DIALOG_SETTITLE)
                .setSingleChoiceItems(countrys, pref.getInt(countryPositionPref, 0), null)
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setPositiveButton(R.string.ok, (dialog, whichButton) -> {
                    int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                    pref.edit().putInt(countryPositionPref, selectedPosition).apply();
                    pref.edit().putString(Util.COUNTRY_PREF, getResources().getStringArray(R.array.countrysCodes)[selectedPosition]).apply();
                    LocaleHelper.setLocale(MainActivity.this, getResources().getStringArray(R.array.countrysCodes)[selectedPosition]);
                    recreate();
                    dialog.dismiss();
                })
                .show();
    }

    /**
     * If you use a standard toolbar, then you can use the menu
     * to use the date search function. In my case, this feature is disabled.
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        Util.changeMenuIconColor(menu, Color.BLACK);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }
        assert searchView != null;
        searchView.setQueryHint(getString(R.string.search_in_everything));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (viewModel != null) viewModel.searchNews(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    /**
     * in the application there are several parts of the category
     * that the API gives us and with this function we update in our Recyclerview
     */
    public void categoryClicked(View view) {
        viewModel.newsCategoryClick(String.valueOf(view.getTag()));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    /**
     * News List Update Observer
     */
    Observer<List<NewsModel>> newsListUpdateObserver = new Observer<List<NewsModel>>() {
        @Override
        public void onChanged(List<NewsModel> news) {
            newsList.clear();
            if (news != null) {
                newsList.addAll(news);
            }
            adapterListNews.notifyDataSetChanged();
        }
    };


}
