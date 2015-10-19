package com.walmart.android.wk2.googleimagesearcher.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.walamrt.android.wk2.googleimagesearcher.R;
import com.walmart.android.wk2.googleimagesearcher.models.ImageResult;

import java.util.List;

/**
 * Created by dvalia on 10/15/15.
 */
public class ImageResultAdapter extends ArrayAdapter<ImageResult> {

    private static class ViewHolder{

        private TextView tvTitle;
        private ImageView ivImage;

    }
    public ImageResultAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.item_image_result, images);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);

//        //get data from this view
        ImageResult imageResult = getItem(position);

//        System.out.println("getView of user: " + photo.username);

        ViewHolder viewHolder;

        //check if we are using a recycle view; if not we need to inflate
        if(convertView == null){
            //convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo,parent,false);

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_image_result,parent,false);

            //lookup the views in populating data(image, caption, profileImage, username, likes)
            viewHolder.ivImage = (ImageView)convertView.findViewById(R.id.ivImage);
            viewHolder.tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);



            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();


        }

        //clear out imageview incase having an old image
        viewHolder.ivImage.setImageResource(0);
       // viewHolder.linearLayout.removeAllViews();//removing any prior views


        //insert image using picasso
        Picasso.with(getContext()).load(imageResult.getTbUrl()).into(viewHolder.ivImage);


        viewHolder.tvTitle.setText(Html.fromHtml(imageResult.getTitle()));


        //return the created item as a view
        return convertView;

    }

}
