package spfworld.spfworld.activity.Tribune;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import spfworld.spfworld.R;
import spfworld.spfworld.fragment.find.tribune.ReleaseTribuneDateFragment;

/**
 * 论坛
 * 我的发布页
 * Created by Administrator on 2016/9/9.
 */
public class PostTribuneDateActivity extends AppCompatActivity{
    private ReleaseTribuneDateFragment releaseTribuneDateFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tribune_topic_main);
        FragmentManager fragmt = getSupportFragmentManager();
        FragmentTransaction transaction = fragmt.beginTransaction();
        if (null== releaseTribuneDateFragment){
            releaseTribuneDateFragment = new ReleaseTribuneDateFragment();
            transaction.add(R.id.id_frag_tribune_topic_layout, releaseTribuneDateFragment);
            transaction.commit();
        }else {
            transaction.show(releaseTribuneDateFragment);
        }
    }
}
