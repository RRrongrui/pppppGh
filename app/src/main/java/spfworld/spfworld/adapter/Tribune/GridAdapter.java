package spfworld.spfworld.adapter.Tribune;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import spfworld.spfworld.R;
import spfworld.spfworld.utils.Contants;
import spfworld.spfworld.utils.ImageLoad;

/**
 * Created by Administrator on 2016/8/31.
 */
public class GridAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<String> pic ;

    public GridAdapter(Context context, List<String> pic) {
        this.context=context;
        this.pic=pic;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return pic.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_grid_tribune, parent, false);
            holder.imageView = (ImageView) convertView.findViewById(R.id.ivs);

            convertView.setTag(holder);
        } else {
            holder= (ViewHolder) convertView.getTag();
        }

        ImageLoad imageLoad=new ImageLoad();
        imageLoad.HttpImage(Contants.IMAGE_BASE_URL+pic.get(position),holder.imageView);
        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;

    }

}
