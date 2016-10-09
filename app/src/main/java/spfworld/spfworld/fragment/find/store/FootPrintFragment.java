package spfworld.spfworld.fragment.find.store;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import spfworld.spfworld.R;
import spfworld.spfworld.activity.StoreActivity;

/**
 * Created by guozhengke on 2016/9/4.
 */
public class FootPrintFragment extends Fragment {
    @ViewInject(R.id.footprint_back)
    private ImageView img_back;

    private Handler handler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.store_footprint,container,false);
        x.view().inject(this,view);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreActivity storeActivity= (StoreActivity) getActivity();
                handler=storeActivity.handler;
                Message message=new Message();
                message.what=0;
                handler.sendMessage(message);
            }
        });
        return  view;
    }
}
