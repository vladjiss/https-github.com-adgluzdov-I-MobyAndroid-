package com.dronteam.adm.i_moby;

import com.dronteam.adm.i_moby.common.Presenter;
import com.dronteam.adm.i_moby.common.ViewManager;
import com.dronteam.adm.i_moby.scenarios.goods.GoodsFragment;
import com.dronteam.adm.i_moby.scenarios.goods.GoodsPresenter;
import com.dronteam.adm.i_moby.scenarios.goods.GoodsView;
import com.dronteam.adm.i_moby.scenarios.search.SearchGoodsFragment;
import com.dronteam.adm.i_moby.scenarios.search.SearchGoodsPresenter;
import com.dronteam.adm.i_moby.scenarios.search.SearchGoodsView;
import com.dronteam.adm.i_moby.scenarios.showcase.ShowCaseFragment;
import com.dronteam.adm.i_moby.scenarios.showcase.ShowCasePresenter;
import com.dronteam.adm.i_moby.scenarios.showcase.ShowCaseView;

/**
 * Created by smb on 13/12/2016.
 */
public class UIFactory {
    private static ShowCaseView showCaseView;
    private static GoodsView goodsView;
    private static SearchGoodsView searchGoodsView;

    public static Presenter ShowCase(ViewManager viewManager) {
        if (showCaseView == null) showCaseView = new ShowCaseFragment();
        return  new ShowCasePresenter(viewManager, showCaseView);
    }

    public static Presenter GoodsPresenter(ViewManager viewManager) {
        if (goodsView == null) goodsView = new GoodsFragment();
        return  new GoodsPresenter(viewManager, goodsView);
    }

    public static Presenter SearchGoodsPresenter(ViewManager viewManager) {
        if (searchGoodsView == null) searchGoodsView = new SearchGoodsFragment();
        return  new SearchGoodsPresenter(viewManager, searchGoodsView);
    }
/*
    public static Presenter DetailInfo(ViewManager viewManager) {
        if (viewManager == null) detailInfoView = new detailInfoFragment();
        return new DetailInfoPresenter(viewManager, detailInfoView);
    }
    */
}
