package spfworld.spfworld.fragment.find.tribune;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ab.AllImageActivity;
import ab.bean.ImageFloder;
import ab.util.AbDialogUtil;
import ab.util.AbFileUtil;
import ab.util.AbImageUtil;
import ab.util.AbToastUtil;
import ab.util.ScreenUtils;
import spfworld.spfworld.R;
import spfworld.spfworld.activity.CropImageActivity;
import spfworld.spfworld.adapter.Tribune.GridAdapter3;
import spfworld.spfworld.utils.SharedHelper;
import spfworld.spfworld.utils.XutilsClass;

/**
 * 论坛
 * 我的发布页详情
 * Created by Administrator on 2016/9/9.
 */
public class ReleaseTribuneDateFragment extends Fragment {
    private GridAdapter3 gridAdapter3;
    private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();
    private static final int REQUEST_PICK_PICTURE = 0x1955;
    private static final int REQUEST_TAKE_PICTURE = 0x1956;
    private static final int REQUEST_CROP_PICTURE = 0x1957;
    private Handler handler = new Handler();
    private View viewhead;
    private View view;
    private static final String TAG = "XUtilsHelper";
    private String picPath = null;
    @ViewInject(R.id.iv_up_picture)
    private ImageView imageView;
    @ViewInject(R.id.et_post_title)
    private EditText etTitle;
    @ViewInject(R.id.et_post_essay)
    private EditText etEssay;
    @ViewInject(R.id.layout)
    private RelativeLayout layout;
    private GridView gridView3;
    private File mCurrentPhotoFile;
    private File[]files;
    private Context context;
    private SharedHelper sharedHelper;
    private int uid = 8;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tribune_post_date_activity,container,false);
        x.view().inject(this,view);
        sharedHelper=new SharedHelper(getActivity());
        context = getActivity();
        gridView3 = (GridView) view.findViewById(R.id.gv_post_pic);
//        GridAdapter3 gridAdapter3 = new GridAdapter3(context,ArrayList<ImageFloder> mImageFloders);
        return view;
    }


    @Event(value={R.id.bu_topic_post_finsh,
            R.id.tv_clay,R.id.iv_up_picture,
            R.id.et_post_title,R.id.et_post_essay,R.id.iView,
    R.id.avatar_change_take_picture_layout,
    R.id.avatar_change_choose_image_layout,R.id.ll_cancle,R.id.layout},
            type=View.OnClickListener.class)
    private void mClick(View v){
        switch (v.getId()){
            case R.id.bu_topic_post_finsh://回退
                getActivity().finish();
                break;
            case R.id.tv_clay://完成发布
                GetReleaseTribune();
                getActivity().finish();
                break;
            case R.id.et_post_title://标题
                break;
            case R.id.et_post_essay://正文
                break;
            case R.id.iView://选取图片
                Intent intent = new Intent(getActivity(), AllImageActivity.class);
                startActivity(intent);
//                showSelectPop();
//                Intent intent = new Intent(getActivity(), AllImageActivity.class);
//                startActivityForResult(intent, 1);
                break;
            case R.id.avatar_change_take_picture_layout://拍照
                dismissPop();
                try {
                    String mFileName = System.currentTimeMillis() + ".jpg";
                    mCurrentPhotoFile = new File(
                            AbFileUtil.getImageDownloadDir(context), mFileName);
                    Intent intent2 = new Intent();
                    intent2.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(mCurrentPhotoFile));
                    intent2.putExtra("return-data", true);
                    startActivityForResult(intent2, REQUEST_TAKE_PICTURE);
                } catch (Exception e) {
                    AbToastUtil.showCustomerToast(context, getResources().getString(R.string.system_camera_not_found));
                }
                break;
//            case R.id.avatar_change_choose_image_layout://从相册选
//                dismissPop();
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                try {
//                    startActivityForResult(intent, REQUEST_PICK_PICTURE);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Intent intent = new Intent(getActivity(), AllImageActivity.class);
//                startActivity(intent);
//                break;
            case R.id.ll_cancle:
                dismissPop();
                AbDialogUtil.removeDialog(viewhead);
                break;
            default:
                hideSystemKeyBoard(getActivity(),layout);
                break;
        }
    }
    public static void hideSystemKeyBoard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * 裁剪图片
     *
     * @param path
     */
    private void cropImg(String path) {
        if (!TextUtils.isEmpty(path)) {
            // 去裁剪图片
            Intent intent = new Intent(context, CropImageActivity.class);
            intent.putExtra("type", "type");
            intent.putExtra("PATH", path);
            startActivityForResult(intent, REQUEST_CROP_PICTURE);
        } else {
            AbToastUtil.showCustomerToast(context, getResources().getString(R.string.notfound_img));
        }
    }

    /**
     * 设置图片
     */
    private void setBitmap(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                new File(filePath).delete();
            } catch (Exception e) {
            }
//            try {
//                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//                if (bitmap!=null){
//                    id_headphoto.setImageBitmap(bitmap);
//                    uploadFile(bitmap);
//                }
//            } catch (Exception e) {
//            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String filePath = "";
        switch (requestCode) {
            case REQUEST_TAKE_PICTURE:// 相机返回结果
                if (data == null){
                    return;
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mCurrentPhotoFile != null) {
                            cropImg(mCurrentPhotoFile.getAbsolutePath());
                        }
                    }
                }, 100);
                break;
            case REQUEST_PICK_PICTURE:// 相册返回结果
                if (data == null) {
                    return;
                }
                Uri uri = data.getData();
                Bitmap bitmap = BitmapFactory.decodeFile(AbImageUtil.getPath(uri,getActivity()));
                if (bitmap!=null){
//                    id_headphoto.setImageBitmap(bitmap);
                }
                setBitmap(AbImageUtil.getPath(uri, getActivity()));
                //cropImg(AbImageUtil.getPath(uri, getActivity()));
                break;
            case REQUEST_CROP_PICTURE:// 获取裁剪后的图片路径
                if (data != null) {
                    filePath = data.getStringExtra("PATH");
                }
                setBitmap(filePath);
                break;
            default:
                break;
        }
    }
    String url;
    public  void uploadFile(Bitmap bm) {
        String url = Bitmap2StrByBase64(bm);
        if (TextUtils.isEmpty(url)) {
            return  ;
        }
    }
    public String Bitmap2StrByBase64(Bitmap bit){
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩
        byte[] bytes=bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    //帖子发布

    public void GetReleaseTribune(){
        String setprov = "直辖市";
        String setcity = "上海市";
        String setarea = "闵行区";
        String setaddress = "万科时代广场";
        String settitle=etTitle.getText().toString();
        String setcomment=etEssay.getText().toString();
        String Userid=sharedHelper.ReadData("String","Userid").toString();
        XutilsClass.getInstance().getReleaseTribune( Userid,settitle,setcomment,
                setprov,setcity,setarea,
                setaddress,"",
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Log.e("oye==",s);
                        Gson gson = new Gson();
                    }
                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Log.e("失败",throwable.toString());
                    }
                    @Override
                    public void onCancelled(CancelledException e) {
                    }
                    @Override
                    public void onFinished() {
                    }
                });
    }

    /**
     * 弹出popwindow
     */
    private View viewpop;
    private PopupWindow pop;
    private void showSelectPop(){
        if(viewpop==null){
            viewpop=getActivity().getLayoutInflater().inflate(R.layout.pop_headphoto, null);
            x.view().inject(this,viewpop);
        }
        pop= AbDialogUtil.showPopWindow2(context, getActivity().getWindow().getDecorView(), viewpop, ScreenUtils.getScreenWidth(context));
    }
    /**
     * 消失pop
     */
    private void dismissPop() {
        if(pop!=null&&pop.isShowing()){
            pop.dismiss();
        }
    }


}



