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
import spfworld.spfworld.adapter.User.MyColtTribuneAdapter;

/**
 * 帖子
 * Created by Administrator on 2016/9/28.
 */
public class MyColtTribuneFragment extends Fragment {
    private MyColtTribuneAdapter myColtTribuneAdapter;
    @ViewInject(R.id.tribune_listview)
    private ListView tribune_listview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.my_colt_tribune,container,false);
        x.view().inject(this,view);
        return view;
    }
}
