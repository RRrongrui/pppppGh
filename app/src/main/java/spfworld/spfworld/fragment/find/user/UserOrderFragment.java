package spfworld.spfworld.fragment.find.user;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import spfworld.spfworld.R;
import spfworld.spfworld.adapter.User.UserOrderAdapter;
import spfworld.spfworld.entity.User.UserOrder;
import spfworld.spfworld.utils.SharedHelper;
import spfworld.spfworld.utils.XutilsClass;
import spfworld.spfworld.widget.percent.view.MyListView;

/**
 * Created by Administrator on 2016/9/28.
 */
public class UserOrderFragment extends Fragment{
    private Context ncontext;
    private View view;
    @ViewInject(R.id.lv_order)
    private MyListView orderlist;
    @ViewInject(R.id.swipelayout1)
    private SwipeRefreshLayout swipeLayout1;
    private List<UserOrder.DataBean> userData;
    private SharedHelper sharedHelper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_order, container, false);
        x.view().inject(this, view);
        sharedHelper=new SharedHelper(getActivity());
        ncontext = getActivity();
        GetUserOrder();
        return view;
    }
    @Event(value={R.id.bu_user_data_finsh},
            type=View.OnClickListener.class)
    private void mClick(View v) {
        switch (v.getId()) {
            case R.id.bu_user_data_finsh://回退
                getActivity().finish();
                break;
            default:
                break;
        }
    }
//    private int userid = 8;
    private int page = 1;
    private void GetUserOrder(){
        String Userid=sharedHelper.ReadData("String","Userid").toString();
        XutilsClass.getInstance().getUserOrder(Userid, page, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("oye==成功",s);
                Gson gson = new Gson();
                UserOrder userOrder = gson.fromJson(s,UserOrder.class);
                userData=userOrder.getData();
                isMyList();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.e("oye==失败",throwable.toString());
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
    private void isMyList(){
        UserOrderAdapter userOrderAdapter = new UserOrderAdapter(getActivity(),userData);
        orderlist.setAdapter(userOrderAdapter);
    }
}
