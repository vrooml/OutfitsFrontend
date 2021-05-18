package com.example.outfits.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.outfits.Adapter.BlogFragmentAdapter;
import com.example.outfits.Bean.Blog;
import com.example.outfits.Bean.SubTypeClothingBean;
import com.example.outfits.Bean.UserInfo;
import com.example.outfits.LoginActivity;
import com.example.outfits.ModifyActivity;
import com.example.outfits.MyApplication;
import com.example.outfits.PostBlogActivity;
import com.example.outfits.R;
import com.example.outfits.RetrofitStuff.DeleteClothingRequest;
import com.example.outfits.RetrofitStuff.GetBlogRequest;
import com.example.outfits.RetrofitStuff.PostInterfaces;
import com.example.outfits.RetrofitStuff.ResponseModel;
import com.example.outfits.ShowFansListActivity;
import com.example.outfits.ShowFocusListActivity;
import com.example.outfits.Utils.RetrofitUtil;
import com.example.outfits.Utils.SharedPreferencesUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kongzue.dialog.v2.SelectDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.outfits.Utils.ConstantUtil.FAILED;
import static com.example.outfits.Utils.ConstantUtil.SUCCESS_CODE;

public class UserFragment extends Fragment{

    private View view;
    private static final String TAG = "UserFragment";
    private TextView fllowCount;
    private TextView fansCount;
    private TextView name;
    private ImageView icon;
    private Button btn_modify;
    private FloatingActionButton btn_createBlog;
    private TextView blocUser;
    private TextView blocFollow;
    private ConstraintLayout rootLayout;
    private ViewPager2 viewPager2;
    private List<Fragment> mFragmentArray=new ArrayList<>();

    public UserFragment(){
        // Required empty public constructor
    }
    public static UserFragment newInstance(String param1,String param2){
        UserFragment fragment=new UserFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user,container,false);
        if(view != null){
            initView();
        }
        return view;
    }

    private void initView(){
        fllowCount = view.findViewById(R.id.followCount);
        fansCount = view.findViewById(R.id.fansCount);
        btn_modify = view.findViewById(R.id.btn_modify);
        btn_createBlog = view.findViewById(R.id.btn_createBlog);
        blocUser = view.findViewById(R.id.blog_user);
        blocFollow = view.findViewById(R.id.blog_follow);
        rootLayout=view.findViewById(R.id.root_layout);
        name = view.findViewById(R.id.user_name);
        icon = view.findViewById(R.id.user_image);
        mFragmentArray.add(MyBlogFragment.newInstance(null));
        mFragmentArray.add(MyCollectionFragment.newInstance(null));
        BlogFragmentAdapter blogFragmentAdapter = new BlogFragmentAdapter(this, null, 6);
        viewPager2 = view.findViewById(R.id.vp_blog);
        viewPager2.setAdapter(blogFragmentAdapter);

        PostInterfaces request = RetrofitUtil.retrofit.create(PostInterfaces.class);

        icon.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){
                SelectDialog.show(UserFragment.this.getContext(), "要退出登录吗？", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"token",null);
                        Intent intent = new Intent(getActivity(),LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                return false;
            }
        });

        //获取用户头像和昵称
        Call<ResponseModel<UserInfo>> call1 = request.getInfo(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),
                new GetBlogRequest(
//                        Integer.parseInt(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"userId"))
                        6
                ));
        call1.enqueue(new Callback<ResponseModel<UserInfo>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserInfo>> call, Response<ResponseModel<UserInfo>> response) {
                if(response.body() != null){
                    if(response.body().getCode() == SUCCESS_CODE) {
                        name.setText(response.body().getData().getUserNickname());
                        Glide.with(getContext()).asBitmap()
                                .load(response.body().getData().getUserPic())
                                .centerCrop().into(new BitmapImageViewTarget(icon){
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                                        .create(MyApplication.getContext().getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                icon.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                    }else{
                        Toast.makeText(MyApplication.getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserInfo>> call, Throwable t) {
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });

        //获取用户粉丝数
        Call<ResponseModel<UserInfo[]>> call2 = request.getSubscriber(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),
                new GetBlogRequest(
//                        Integer.parseInt(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"userId"))
                        Integer.parseInt(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"userId"))
                ));
        call2.enqueue(new Callback<ResponseModel<UserInfo[]>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserInfo[]>> call, Response<ResponseModel<UserInfo[]>> response) {
                if(response.body() != null){
                    if(response.body().getCode() == SUCCESS_CODE) {
                        fansCount.setText(String.valueOf(response.body().getData().length));
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

        //获取用户关注数
        Call<ResponseModel<UserInfo[]>> call3 = request.getSubscription(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),
                new GetBlogRequest(
//                        Integer.parseInt(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"userId"))
                        6
                ));
        call3.enqueue(new Callback<ResponseModel<UserInfo[]>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserInfo[]>> call, Response<ResponseModel<UserInfo[]>> response) {
                if(response.body() != null){
                    if(response.body().getCode() == SUCCESS_CODE) {
                        fllowCount.setText(String.valueOf(response.body().getData().length));
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



        fllowCount.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), ShowFocusListActivity.class);
                startActivity(intent);
            }
        });

        fansCount.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), ShowFansListActivity.class);
                startActivity(intent);
            }
        });

        btn_modify.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), ModifyActivity.class);
                startActivity(intent);
            }
        });

        btn_createBlog.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), PostBlogActivity.class);
                startActivity(intent);
            }
        });

        blocUser.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //传递关于“我的博客”数据进入
                Transition transition = new ChangeBounds();
                transition.setDuration(1000);
                TransitionManager.beginDelayedTransition(rootLayout,transition);
                viewPager2.setCurrentItem(0);
                blocUser.setTextColor(getActivity().getResources().getColor(R.color.main_color));
                blocFollow.setTextColor(getActivity().getResources().getColor(R.color.black));
            }
        });

        blocFollow.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //传递关于“我的收藏”数据进入
                Transition transition = new ChangeBounds();
                transition.setDuration(1000);
                TransitionManager.beginDelayedTransition(rootLayout,transition);
                viewPager2.setCurrentItem(1);
                blocFollow.setTextColor(getActivity().getResources().getColor(R.color.main_color));
                blocUser.setTextColor(getActivity().getResources().getColor(R.color.black));
            }
        });
    }
}