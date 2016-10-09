package spfworld.spfworld.fragment.find;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import spfworld.spfworld.R;
import spfworld.spfworld.activity.PondActivity;
import spfworld.spfworld.carousel.entity.BannerEntity;
import spfworld.spfworld.carousel.view.Banner;
import spfworld.spfworld.entity.Content;
import spfworld.spfworld.entity.PondDetail;
import spfworld.spfworld.utils.XutilsClass;
import spfworld.spfworld.utils.dialog.CallDialog;

/**
 * Created by guozhengke on 2016/9/6.
 * 钓点详情
 */
public class PondDetailFragment extends Fragment {
    @ViewInject(R.id.pond_collct)
    private ImageView pond_collct;
    @ViewInject(R.id.pond_detail_back)
    private ImageView img_detail_back;
    @ViewInject(R.id.pond_detail_phone)
    private ImageView img_detail_phone;
    @ViewInject(R.id.pondDetail_title)
    private TextView tv_title;
    @ViewInject(R.id.pondDetail_name)
    private TextView tv_name;
    @ViewInject(R.id.pondDetail_adress)
    private TextView tv_adress;
    @ViewInject(R.id.pond_detail_jigging)
    private ImageView img_jigging;
    @ViewInject(R.id.pond_detail_scat)
    private ImageView img_scat;
    @ViewInject(R.id.pond_detail_rud)
    private ImageView img_rud;
    @ViewInject(R.id.pond_detail_bait)
    private ImageView img_bait;
    @ViewInject(R.id.detail_Num)
    private TextView detail_Num;
    @ViewInject(R.id.detail_nNum)
    private TextView detail_nNum;
    @ViewInject(R.id.detail_charge_j)
    private TextView charge_j;
    @ViewInject(R.id.detail_charge_t)
    private TextView charge_t;
    @ViewInject(R.id.daohang)
    private RelativeLayout daohang;
    @ViewInject(R.id.detail_nCharge)
    private TextView nCharge;
    @ViewInject(R.id.detail_intro)
    private ImageView detail_intro;
    @ViewInject(R.id.pond_detail_banner)
    private Banner pond_detail_banner;
    private List<BannerEntity> mList;
    private Handler handler;
    private View diagView;
    private FragmentManager FM;
    FragmentTransaction FT;
    private View view=null;
    private int FLAG=0;

    @Nullable
    //判断页面显示时网络请求
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden == false) {
            GetPondDtail();
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.find_pond_detail, container, false);

            x.view().inject(this, view);
            FM = getFragmentManager();
            FT = FM.beginTransaction();

            diagView = inflater.from(getContext()).inflate(R.layout.call_diag, container, false);
            img_detail_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),PondActivity.class);
                    startActivity(intent);
                }
            });
            //img_detail_phone.setImageBitmap();
            img_detail_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("PondDetailFragment", "点击事件发生");
                    showDialog();
                }
            });


        detail_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("","点击事件发生");
                PondActivity payActivity= (PondActivity) getActivity();
                handler=payActivity.handler;
                Message message=new Message();
                message.what=2;
                handler.sendMessage(message);
            }
        });
        //导航（暂未开放）
        daohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"暂未开放,敬请期待!", Toast.LENGTH_SHORT).show();
            }
        });
        //收藏
        pond_collct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (FLAG){
                    case 0:
                        pond_collct.setImageResource(R.mipmap.collect);
                        PondDetailCollction();
                        FLAG=1;
                        break;
                    case 1:
                        pond_collct.setImageResource(R.mipmap.pond_collect);
                        PondDetailCollctionDel();
                        FLAG=0;
                        break;
                }
            }
        });
        return view;
    }




    //拨打电话弹出框
    public void showDialog(){
        Log.e("PondDetailFragment","调用showDialog");
        CallDialog.Builder builder=new CallDialog.Builder(getActivity());
        builder.setTitle("15821581768");
        builder.setPositiveButton("电话咨询", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(Intent.ACTION_CALL);
                Uri data=Uri.parse("tel:"+"15821581768");
                intent.setData(data);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    //收藏网络请求
    public void PondDetailCollction(){
        XutilsClass.getInstance().postPondCollection("8", Content.pond_id, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("收藏",s);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    //取消收藏网络请求
    public void PondDetailCollctionDel(){
        XutilsClass.getInstance().postPondCollectionDel("8", Content.pond_id, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("取消收藏",s);
            }

            @Override

            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    //详情页网络请求调用数据
    public void GetPondDtail(){
        XutilsClass.getInstance().getPondDtail(Content.pond_id, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("PondDetailFragment+++",s);
                Gson gson=new Gson();
                PondDetail pondDetail=gson.fromJson(s,PondDetail.class);
                tv_title.setText(pondDetail.getData().get(0).getTheme());
                tv_name.setText(pondDetail.getData().get(0).getTheme());
                tv_adress.setText(pondDetail.getData().get(0).getProv()+pondDetail.getData().get(0).getCity()+pondDetail.getData().get(0).getArea()+pondDetail.getData().get(0).getAddress());
                //收藏
                if (pondDetail.getData().get(0).getCollection()!=null&&!pondDetail.getData().get(0).getCollection().equals("")){
                    switch (pondDetail.getData().get(0).getCollection()){
                        case "0":
                            pond_collct.setImageResource(R.mipmap.pond_collect);
                            FLAG=0;
                            break;
                        case "1":
                            pond_collct.setImageResource(R.mipmap.collect);
                            FLAG=1;
                            break;
                    }
                }else {
                    pond_collct.setImageResource(R.mipmap.pond_collect);
                    FLAG=0;
                }
                //钓点情况
                 List<String> img_con=pondDetail.getData().get(0).getType();
                if (img_con!=null){
                    switch (img_con.get(0)) {
                        case "4":
                            img_jigging.setImageResource(R.mipmap.pond_jigging);
                        case "1":
                            img_bait.setImageResource(R.mipmap.pond_bait);
                        case "2":
                            img_scat.setImageResource(R.mipmap.pond_scat);
                        case "3":
                            img_rud.setImageResource(R.mipmap.pond_rud);
                    }
              }
                //设置钓点数量
                switch (pondDetail.getData().get(0).getNum()){
                    case "0":
                        detail_Num.setBackgroundResource(R.color.detail_num);
                        detail_Num.setTextColor(Color.WHITE);
                        break;
                    case "1":
                        detail_nNum.setBackgroundResource(R.color.detail_num);
                        detail_nNum.setTextColor(Color.WHITE);
                        break;
                }
                //设置收费方式
                switch (pondDetail.getData().get(0).getCharge()){
                    case "0":
                        charge_j.setBackgroundResource(R.color.detail_num);
                        charge_j.setTextColor(Color.WHITE);
                        break;
                    case "1":
                        charge_t.setBackgroundResource(R.color.detail_num);
                        charge_t.setTextColor(Color.WHITE);
                        break;
                    case "2":
                        nCharge.setBackgroundResource(R.color.detail_num);
                        nCharge.setTextColor(Color.WHITE);
                        break;
                }
                //轮播
                List<String> img_collction=pondDetail.getData().get(0).getContent();
                for (int i = 0; i < img_collction.size(); i++) {
                    mList = new ArrayList<>();
                    mList.add(new BannerEntity(i, "http://" + img_collction.get(i), null, null));
                }
                pond_detail_banner.setList(mList);
                pond_detail_banner.setChangeDuration(1000);//设置切换时间
                pond_detail_banner.setPauseDuration(500);//设置停留时间
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.e("请求错误",throwable.toString());
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
