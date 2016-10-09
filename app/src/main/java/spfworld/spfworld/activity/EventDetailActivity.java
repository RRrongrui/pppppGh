package spfworld.spfworld.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import spfworld.spfworld.R;
import spfworld.spfworld.carousel.entity.BannerEntity;
import spfworld.spfworld.carousel.view.Banner;
import spfworld.spfworld.entity.Content;
import spfworld.spfworld.entity.Event;
import spfworld.spfworld.entity.EventDtail;
import spfworld.spfworld.utils.XutilsClass;

/**
 * Created by guozhengke on 2016/9/13.
 */
public class EventDetailActivity extends Activity {
    private List<EventDtail.DataBean> data;

    @ViewInject(R.id.event_detail_back)
    private ImageView event_detail_back;
    @ViewInject(R.id.event_detail_apply)
    private TextView event_detail_apply;
    @ViewInject(R.id.event_detail_adrress)
    private TextView event_detail_adrress;
    @ViewInject(R.id.event_detail_deadTime)
    private TextView event_detail_deadTime;
    @ViewInject(R.id.event_detail_describe)
    private TextView event_detail_describe;
    @ViewInject(R.id.event_detail_limitNum)
    private TextView event_detail_limitNum;
    @ViewInject(R.id.event_detail_longTime)
    private TextView event_detail_longTime;
    @ViewInject(R.id.event_detail_merName)
    private TextView event_detail_merName;
    @ViewInject(R.id.event_detail_mony)
    private TextView event_detail_mony;
    @ViewInject(R.id.event_detail_name)
    private TextView event_detail_name;
    @ViewInject(R.id.event_detail_phone)
    private TextView event_detail_phone;
    @ViewInject(R.id.event_detail_sigNum)
    private TextView event_detail_sigNum;
    @ViewInject(R.id.event_detail_tags)
    private TextView event_detail_tags;
    @ViewInject(R.id.event_detail_img)
    private ImageView event_detail_img;
    @ViewInject(R.id.main_banner)
    private Banner mBanner;
    private List<BannerEntity> mList;
    private int FLAG=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);
        x.view().inject(this);
        GetEventDtail();
        //返回键
        event_detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EventDetailActivity.this,EventActivity.class);
                startActivity(intent);
            }
        });

        //报名
        event_detail_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(0).getBiaoqian()!=null&&!data.get(0).getBiaoqian().equals("")){
                switch (data.get(0).getBiaoqian()){
                    case "0":
                        Toast.makeText(getApplication(),"活动已结束", Toast.LENGTH_SHORT).show();
                        break;
                    case "1":
                        Toast.makeText(getApplication(),"人数已满", Toast.LENGTH_SHORT).show();
                        break;
                    case "2":
                        Intent intent=new Intent(EventDetailActivity.this,PayActivity.class);
                        startActivity(intent);
                        break;
                }
                }

            }
        });
        //收藏
        event_detail_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (FLAG){
                    case 0://判断FLAG进来值为0或1
                        event_detail_img.setImageResource(R.mipmap.collect);
                        GetCollction();
                        FLAG=1;//客服端显示不刷新ui，不进行网络请求
                        break;
                    case 1:
                        event_detail_img.setImageResource(R.mipmap.pond_collect);
                        GetCollctionDel();
                        FLAG=0;
                        break;
                }
            }
        });
    }
    //收藏
    public void GetCollction(){
        XutilsClass.getInstance().postEventCollection("8", Content.act_id, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("::;;;;:::",s);
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
    //取消收藏
    public void GetCollctionDel(){
        XutilsClass.getInstance().postEventCollectionDel("8", Content.act_id, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("++++++++++++++",s);
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
    //活动详情页网络请求
    public void GetEventDtail(){
        XutilsClass.getInstance().getEventDtail(Content.act_id, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("GetEventDtail",s);
                Gson gson=new Gson();
                EventDtail eventDtail=gson.fromJson(s, EventDtail.class);
                data=eventDtail.getData();
                String event_address=data.get(0).getAct_prov()+data.get(0).getAct_city()+data.get(0).getAct_area()+data.get(0).getAddress();
                String event_name=data.get(0).getTheme();
                List<String> listTime=data.get(0).getNewTime();
                List<String> act_time=data.get(0).getAct_time();
                Content.mony=data.get(0).getPrice();
                Content.event_address=event_address;
                Content.event_name=event_name;
                Content.listTime=listTime;
                Content.actTime=act_time;
                getData(event_address,event_name);
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.e("GetEventDtail网络请求失败",throwable.toString());
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getData(String event_address,String event_name) {
        //轮播
            mList=new ArrayList<>();
            for (int i=0;i<data.get(0).getContent().size();i++){
                mList.add(new BannerEntity(i,"http://"+data.get(0).getContent().get(i),null,null));
            }
            mBanner.setList(mList);
            mBanner.setChangeDuration(1000);//设置切换时间
            mBanner.setPauseDuration(500);//设置停留时间

        //收藏判断
        if (data.get(0).getCollection()!=null) {
            switch (data.get(0).getCollection()) {
                case "0":
                    event_detail_img.setImageResource(R.mipmap.pond_collect);
                    FLAG=0;
                    break;
                case "1":
                    event_detail_img.setImageResource(R.mipmap.collect);
                    FLAG=1;
                    break;
            }
        }else {
            event_detail_img.setImageResource(R.mipmap.pond_collect);
            FLAG=0;
        }
        //设置数据
        event_detail_adrress.setText(event_address);
        event_detail_deadTime.setText("截止于"+data.get(0).getDead_time());
        event_detail_describe.setText(data.get(0).getDescribe());
        event_detail_limitNum.setText("/"+data.get(0).getLimit_num());
        event_detail_longTime.setText(data.get(0).getBegin_time()+"至"+data.get(0).getEnd_time());
        event_detail_merName.setText(data.get(0).getMer_name());
        event_detail_mony.setText("￥"+data.get(0).getPrice());
        event_detail_name.setText(event_name);
        event_detail_phone.setText(data.get(0).getPhone());
        if (data.get(0).getSig_num()<=0){
            event_detail_sigNum.setText("0");
        }else {
            event_detail_sigNum.setText(data.get(0).getSig_num());
        }

        switch(data.get(0).getTags()){
            case "1":
                event_detail_tags.setText("官方认证");
                break;
            case "2":
                event_detail_tags.setText("商家认证");
                break;
            case "3":
                event_detail_tags.setText("诚信商家");
                break;
        }
        if (data.get(0).getBiaoqian()!=null&&!data.get(0).getBiaoqian().equals("")){
            switch (data.get(0).getBiaoqian()){
                case "0":
                    event_detail_apply.setBackgroundResource(R.color.listview_item_pressed);
                    event_detail_apply.setText("活动结束");
                    break;
                case "1":
                    event_detail_apply.setBackgroundResource(R.color.listview_item_pressed);
                    event_detail_apply.setText("报名人数已满");
                    break;
                case "2":
                    event_detail_apply.setBackgroundResource(R.color.eventDtail_BM);
                    event_detail_apply.setText("立即报名");
                    break;
            }
        }
    }

}
