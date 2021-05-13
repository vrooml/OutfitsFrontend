package com.example.outfits.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.outfits.Bean.Blog;
import com.example.outfits.Bean.Collection;
import com.example.outfits.MyApplication;
import com.example.outfits.RetrofitStuff.GetBlogRequest;
import com.example.outfits.UI.MyBlogFragment;
import com.example.outfits.UI.MyCollectionFragment;
import com.example.outfits.Utils.LoadingDialog;
import com.example.outfits.Utils.RetrofitUtil;
import com.example.outfits.Utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class BlogFragmentAdapter extends FragmentStateAdapter {
    List<Blog> blogs;
    Fragment fragment;
    int userId;

    public BlogFragmentAdapter(@NonNull Fragment fragment, List<Blog> blogs, int userId) {
        super(fragment);
        this.blogs = blogs;
        this.fragment = fragment;
        this.userId = userId;
    }

    public BlogFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Blog> blogs, int userId) {
        super(fragmentManager, lifecycle);
        this.blogs = blogs;
        this.userId = userId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0){
            List<Blog> blogList = new ArrayList<>();
            LoadingDialog dialog = new LoadingDialog.Builder(MyApplication.getContext())
                    .setMessage("加载中...")
                    .setCancelable(false)
                    .create();
            //dialog.show();
            GetBlogRequest getBlogRequest = new GetBlogRequest(userId);
            RetrofitUtil.getBlog(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token")
                    ,getBlogRequest, blogList, null);
            return MyBlogFragment.newInstance(blogList);
        }
        else {
            List<Collection> collectionList = new ArrayList<>();
            LoadingDialog dialog = new LoadingDialog.Builder(MyApplication.getContext())
                    .setMessage("加载中...")
                    .setCancelable(false)
                    .create();

            //dialog.show();
            GetBlogRequest getBlogRequest = new GetBlogRequest(userId);
            RetrofitUtil.getCollection(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token")
                    ,getBlogRequest, collectionList, null);
            return MyCollectionFragment.newInstance(collectionList);
        }
    }



    @Override
    public int getItemCount() {
        return 2;
    }
}
