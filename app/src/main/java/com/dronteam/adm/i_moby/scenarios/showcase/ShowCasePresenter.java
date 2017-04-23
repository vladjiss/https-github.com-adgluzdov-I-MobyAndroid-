package com.dronteam.adm.i_moby.scenarios.showcase;

import android.util.Log;

import com.dronteam.adm.i_moby.UIFactory;
import com.dronteam.adm.i_moby.common.CallBack;
import com.dronteam.adm.i_moby.common.CommonAdapter;
import com.dronteam.adm.i_moby.common.CommonView;
import com.dronteam.adm.i_moby.common.ItemPresenter;
import com.dronteam.adm.i_moby.common.Presenter;
import com.dronteam.adm.i_moby.data.VK.json_response.get.GetResponse;
import com.dronteam.adm.i_moby.common.ViewListener;
import com.dronteam.adm.i_moby.common.ViewManager;
import com.dronteam.adm.i_moby.data.ItemService;
import com.dronteam.adm.i_moby.data.VK.json_response.getAlbums.GetAlbumsResponse;
import com.dronteam.adm.i_moby.scenarios.album.AlbumFragment;
import com.dronteam.adm.i_moby.scenarios.album.AlbumPresenter;
import com.dronteam.adm.i_moby.model.special_offer.SpecialOffer;
import com.dronteam.adm.i_moby.scenarios.search.SearchGoodsFragment;
import com.dronteam.adm.i_moby.scenarios.search.SearchGoodsPresenter;
import com.dronteam.adm.i_moby.scenarios.search.SearchGoodsView;
import com.dronteam.adm.i_moby.scenarios.special_offer.SpecialOfferFragment;
import com.dronteam.adm.i_moby.scenarios.special_offer.SpecialOfferPresenter;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by smb on 13/12/2016.
 */
//Todo: Класс стартует до onCreate View, что не дает работать view в конструкторе. Сделать,чтобы класс стартовал после создания View. Перенести SetAdapter в конструктор.
public class ShowCasePresenter implements Presenter, ViewListener {
    private static final String TAG = "My";
    private ViewManager viewManager;
    private ShowCaseView view;
    private final ItemService itemService;
    private CommonAdapter adapter;
    private SearchGoodsPresenter searchPresenter;
    //private ServiceFactory serviceFactory;

    public ShowCasePresenter(ViewManager viewManager, ShowCaseView view) {
        this.viewManager = viewManager;
        this.view = view;
        this.adapter = new CommonAdapter();
        itemService = viewManager.getServiceFactory().getApi(ItemService.class);
        view.setOnCreateViewListener(this);
    }

    @Override
    public CommonView getView() {
        return view;
    }

    @Override
    public void OnCreateView() {
        view.setOnButtonClick(new CallBack() {
            @Override
            public void call() {
                //Переход на SearchGoods
            }
        });
        view.setList(adapter);
        itemService.SearchSpecialOffers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(onSpecialOffersLoaded(), onError());
        itemService.GetAlbums()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(onAlbumsLoaded(), onError());
    }

    private Action1<? super GetAlbumsResponse> onAlbumsLoaded() {
        return new Action1<GetAlbumsResponse>() {

            @Override
            public void call(final GetAlbumsResponse getAlbumsResponse) {
                Log.d(TAG, "call: success - onAlbumsLoaded()");

                ArrayList<ItemPresenter> itemPresenterList = new ArrayList<ItemPresenter>(){{
                    for (final com.dronteam.adm.i_moby.model.album.Item item :
                            getAlbumsResponse.getResponse().getItems()) {
                        add(new AlbumPresenter(viewManager,item,new AlbumFragment(viewManager.getContext())));
                    }
                }};
                adapter.addItemPresenters(itemPresenterList);
            }
        };
    }

    private Action1<? super GetResponse> onSpecialOffersLoaded() {
        return new Action1<GetResponse>() {
            @Override
            public void call(final GetResponse repo) {
                Log.d(TAG, "call: success - onSpecialOffersLoaded()");
                ArrayList<ItemPresenter> itemPresenterList = new ArrayList<ItemPresenter>(){{
                    for (final com.dronteam.adm.i_moby.model.product.Item item :
                            repo.getResponse().getItems()) {
                        add(new SpecialOfferPresenter(viewManager,new SpecialOffer(item),new SpecialOfferFragment(viewManager.getContext())));
                    }
                }};
                adapter.addItemPresenters(0,itemPresenterList);
            }
        };
    }

    private Action1<Throwable> onError() {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.d(TAG, "call: error in ShowCasePresenter");
            }
        };
    }
}
