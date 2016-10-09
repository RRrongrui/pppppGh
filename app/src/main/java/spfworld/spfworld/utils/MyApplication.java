package spfworld.spfworld.utils;

import android.app.Application;
import android.content.Context;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import org.xutils.x;

/**
 * Created by guozhengke on 2016/8/30.
 */
public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        //xutils初始化
        x.Ext.init(this);
        context=this;
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration imageLoaderConfiguration=ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(imageLoaderConfiguration);
    }
}
