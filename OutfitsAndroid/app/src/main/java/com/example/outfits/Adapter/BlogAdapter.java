package com.example.outfits.Adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.outfits.Bean.Blog;
import com.example.outfits.Bean.UserInfo;
import com.example.outfits.LoginActivity;
import com.example.outfits.MyApplication;
import com.example.outfits.R;
import com.example.outfits.RetrofitStuff.DeleteBlogRequest;
import com.example.outfits.RetrofitStuff.GetBlogRequest;
import com.example.outfits.RetrofitStuff.PostInterfaces;
import com.example.outfits.RetrofitStuff.ResponseModel;
import com.example.outfits.UI.BlogDetailActivity;
import com.example.outfits.UI.MyBlogFragment;
import com.example.outfits.UI.UserFragment;
import com.example.outfits.Utils.RetrofitUtil;
import com.example.outfits.Utils.SharedPreferencesUtil;
import com.kongzue.dialog.v2.SelectDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.outfits.Utils.ConstantUtil.FAILED;
import static com.example.outfits.Utils.ConstantUtil.SUCCESS_CODE;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.InnerHolder> {
    Fragment fragment;
    private List<Blog> datas;

    public BlogAdapter(Fragment fragment, List<Blog> datas) {
        this.fragment = fragment;
        this.datas = datas;
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_list_view,parent,false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        holder.setData(datas.get(position));
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
        private ConstraintLayout rootLayout;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            //找到条目的控件
            icon = (ImageView)itemView.findViewById(R.id.iv_icon);
            title = (TextView)itemView.findViewById(R.id.tv_title);
            rootLayout=itemView.findViewById(R.id.root_layout);
        }

        public void setData(Blog blogBean){
            title.setText(blogBean.getBlogTitle().toString());
            Glide.with(itemView)
                    .load(blogBean.getBlogPic().toString())
                    .centerCrop().into(icon);
            rootLayout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent=new Intent(fragment.getActivity(),BlogDetailActivity.class);
                    intent.putExtra("blogId",blogBean.getBlogId());
                    fragment.getActivity().startActivity(intent);
                }
            });
            rootLayout.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    SelectDialog.show(fragment.getContext(), "要删除这篇博客吗？", "", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PostInterfaces request = RetrofitUtil.retrofit.create(PostInterfaces.class);
                            Call<ResponseModel> call2 = request.deleteBlog(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),
                                    new DeleteBlogRequest(blogBean.getBlogId()));
                            call2.enqueue(new Callback<ResponseModel>() {
                                @Override
                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                    if(response.body() != null){
                                        if(response.body().getCode() == SUCCESS_CODE) {
                                            datas.remove(blogBean);
                                            notifyDataSetChanged();
                                            dialog.dismiss();
                                            Toast.makeText(MyApplication.getContext(),"已删除博客",Toast.LENGTH_SHORT).show();
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
                    return false;
                }
            });
        }
    }
}
