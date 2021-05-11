package com.example.outfits.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.outfits.Bean.Blog;
import com.example.outfits.RetrofitStuff.GetBlogRequest;
import com.example.outfits.UI.MyBlogFragment;
import com.example.outfits.Utils.LoadingDialog;
import com.example.outfits.Utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

public class BlogFragmentAdapter extends FragmentStateAdapter {
    List<Blog> blogs;
    Fragment fragment;

    public BlogFragmentAdapter(@NonNull Fragment fragment, List<Blog> blogs) {
        super(fragment);
        this.blogs = blogs;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        List<Blog> blogList = new ArrayList<>();
        LoadingDialog dialog = new LoadingDialog.Builder(fragment.getContext())
                .setMessage("加载中...")
                .setCancelable(false)
                .create();
  //      dialog.show();
        GetBlogRequest getBlogRequest = new GetBlogRequest(2);
        RetrofitUtil.getBlog("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzIiwiaWF0IjoxNjIwNTUwNzQ1LCJzdWIiOiIxODk2MDE0NzI3MiIsImlzcyI6InJ1aWppbiIsImV4cCI6MTYyMDgwOTk0NX0.HjCnvUYa6m7MjRUMpMd_hfiTNwE71oMdAaNnzcr_-Wo"
                ,getBlogRequest, blogList, dialog);
        return MyBlogFragment.newInstance(blogList);
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}