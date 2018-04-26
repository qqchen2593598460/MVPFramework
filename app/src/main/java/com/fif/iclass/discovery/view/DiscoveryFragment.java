package com.fif.iclass.discovery.view;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fif.baselib.base.BaseFragment;
import com.fif.iclass.R;
import com.fif.iclass.discovery.contract.DiscoveryContract.DiscoveryInterface;

/**
 * Created by chen
 */

public class DiscoveryFragment extends BaseFragment implements DiscoveryInterface.DiscoveryIView {

    private DiscoveryInterface.DiscoveryIPresenter presenter;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_discovery, container, false);
        return view;
    }

    @Override
    public void retryLoadData() {
        super.retryLoadData();
        loadData();
    }

    @Override
    protected void loadData() {
    }

    @Override
    public BaseFragment getInstance() {
        return this;
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}
