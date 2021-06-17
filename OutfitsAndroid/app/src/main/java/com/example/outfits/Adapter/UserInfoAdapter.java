package com.example.outfits.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.outfits.Bean.Blog;
import com.example.outfits.Bean.UserInfo;
import com.example.outfits.MyApplication;
import com.example.outfits.OtherUserActivity;
import com.example.outfits.R;
import com.example.outfits.RetrofitStuff.GetBlogRequest;
import com.example.outfits.RetrofitStuff.PostInterfaces;
import com.example.outfits.RetrofitStuff.ResponseModel;
import com.example.outfits.Utils.RetrofitUtil;
import com.example.outfits.Utils.SharedPreferencesUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.outfits.Utils.ConstantUtil.FAILED;
import static com.example.outfits.Utils.ConstantUtil.SUCCESS_CODE;

public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.InnerHolder>{
    private Context context;
    private List<UserInfo> datas;
    public int source=-1;
    public UserInfoAdapter(Context context, List<UserInfo> datas){
        this.datas = datas;
        this.context = context;
    }

    //创建条目界面
    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建条目界面
        //1.得到View
        //2.构造InnerHolder
        View view = View.inflate(parent.getContext(), R.layout.user_list_view, null);
        return new InnerHolder(view);
    }

    //用于绑定holder，一般用来设置数据
    @Override
    public void onBindViewHolder(@NonNull UserInfoAdapter.InnerHolder holder, int position) {
        holder.setData(datas.get(position));
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OtherUserActivity.class);
                //传递参数
                Bundle bundle = new Bundle();
                bundle.putInt("userId", datas.get(position).getUserId());
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
        if(source==-1){
            holder.cancel.setVisibility(View.GONE);
        }else if(source==1){
            holder.cancel.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    PostInterfaces request = RetrofitUtil.retrofit.create(PostInterfaces.class);
                    Call<ResponseModel> call = request.subscribe(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),
                            new GetBlogRequest(datas.get(position).getUserId()));
                    call.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            if(response.body() != null){
                                if(response.body().getCode() == SUCCESS_CODE) {
                                    final PostInterfaces request = RetrofitUtil.retrofit.create(PostInterfaces.class);
                                    Call<ResponseModel<UserInfo[]>> call1 = request.getSubscription(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),
                                            new GetBlogRequest(Integer.parseInt(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"userId"))));
                                    call1.enqueue(new Callback<ResponseModel<UserInfo[]>>() {
                                        @Override
                                        public void onResponse(Call<ResponseModel<UserInfo[]>> call, Response<ResponseModel<UserInfo[]>> response) {
                                            if(response.body() != null){
                                                if(response.body().getCode() == SUCCESS_CODE) {
                                                    datas.clear();
                                                    for(UserInfo i : response.body().getData()){
                                                        datas.add(i);
                                                    }
                                                    notifyDataSetChanged();
                                                }else{
                                                    Toast.makeText(MyApplication.getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseModel<UserInfo[]>> call, Throwable t) {
                                            Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }else{
                                    Toast.makeText(MyApplication.getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else if(source==2){
            holder.cancel.setText("关注");
            holder.cancel.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    PostInterfaces request = RetrofitUtil.retrofit.create(PostInterfaces.class);
                    Call<ResponseModel> call = request.subscribe(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),
                            new GetBlogRequest(datas.get(position).getUserId()));
                    call.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            if(response.body() != null){
                                if(response.body().getCode() == SUCCESS_CODE){
                                    if(holder.cancel.getText().equals("关注")){
                                        holder.cancel.setText("已关注");
                                    }else if(holder.cancel.getText().equals("已关注")){
                                        holder.cancel.setText("关注");
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(datas != null){
            return datas.size();
        }
        return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder{
        private ImageView icon;
        private TextView title;
        private View root;
        private Button cancel;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            //找到条目的控件
            icon = (ImageView)itemView.findViewById(R.id.iv_icon);
            title = (TextView)itemView.findViewById(R.id.tv_title);
            cancel = (Button)itemView.findViewById(R.id.cancel_sub);
            root=itemView.findViewById(R.id.view);
        }

        public void setData(UserInfo UserInfoBean){
            title.setText(UserInfoBean.getUserNickname().toString());
            Glide.with(itemView).asBitmap()
                    .load(UserInfoBean.getUserPic().toString())
                    .centerCrop().into(new BitmapImageViewTarget(icon){
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                            .create(itemView.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    icon.setImageDrawable(circularBitmapDrawable);
                }
            });
        }
    }
}


