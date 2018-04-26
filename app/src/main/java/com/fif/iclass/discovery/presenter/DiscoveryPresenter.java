package com.fif.iclass.discovery.presenter;

import com.fif.baselib.base.BaseFragment;
import com.fif.iclass.discovery.model.DiscoveryModel;
import com.fif.iclass.discovery.contract.DiscoveryContract;

/**
 * Created by chen on 2017-06-20.
 */

public class DiscoveryPresenter implements DiscoveryContract.DiscoveryInterface.DiscoveryIPresenter, DiscoveryContract.DiscoveryInterface.DiscoveryIListener {

    private DiscoveryContract.DiscoveryInterface.DiscoveryIView view;
    private DiscoveryContract.DiscoveryInterface.DiscoveryIModel model;

    public DiscoveryPresenter(DiscoveryContract.DiscoveryInterface.DiscoveryIView view) {
        this.view = view;
        this.model = new DiscoveryModel(this);
    }
    @Override
    public BaseFragment getInstance() {
        return view.getInstance();
    }
}
