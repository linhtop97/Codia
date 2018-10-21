package com.example.myteam.codia.screen.search;

/**
 * Created by khanhjm on 21-10-2018.
 */
public class SearchPresenter implements SearchContract.Presenter{

    private SearchContract.ViewModel mViewModel;

    public SearchPresenter(SearchContract.ViewModel mViewModel) {
        this.mViewModel = mViewModel;
    }

    @Override
    public void search(String value) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
