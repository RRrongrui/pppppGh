package spfworld.spfworld.fragment.find;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import spfworld.spfworld.R;
import spfworld.spfworld.entity.Content;
import spfworld.spfworld.entity.Intro;
import spfworld.spfworld.utils.XutilsClass;

/**
 * Created by guozhengke on 2016/9/19.
 * 塘口简介
 */
public class IntroFragment extends Fragment {
    @ViewInject(R.id.intro_text)
    private WebView intro_text;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.pond_intro,container,false);
        x.view().inject(this,view);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden==false){
            GetIntro();
        }
    }


    public void GetIntro(){
        XutilsClass.getInstance().getPondIntro(Content.pond_id, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("GetIntro",s);
                Gson gson=new Gson();
                Intro intro=gson.fromJson(s,Intro.class);
                String content= intro.getData().getContent();
                content=content.replace("/Uploads/","http://m.yundiaoke.cn/Uploads/");
                intro_text.getSettings().setUseWideViewPort(true);
                intro_text.getSettings().setLoadWithOverviewMode(true);
                intro_text.loadData(content,"text/html; charset=UTF-8",null);
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
}
