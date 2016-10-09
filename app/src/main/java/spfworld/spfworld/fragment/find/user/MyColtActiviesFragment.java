package spfworld.spfworld.fragment.find.user;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import spfworld.spfworld.R;
import spfworld.spfworld.adapter.User.MyColtActiviesAdapter;

/**
 *活动
 * Created by Administrator on 2016/9/14.
 */
public class MyColtActiviesFragment extends Fragment {
    private MyColtActiviesAdapter myColtActiviesAdapter;
    @ViewInject(R.id.activies_listview)
    private ListView activies_listview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.my_colt_activies,container,false);
        x.view().inject(this,view);
        return view;
    }
}

