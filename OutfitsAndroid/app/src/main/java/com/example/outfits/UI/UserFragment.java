package com.example.outfits.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

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
import com.example.outfits.Adapter.BlogViewPagerAdapter;
import com.example.outfits.Bean.Blog;
import com.example.outfits.Bean.UserInfo;
import com.example.outfits.CustomView.ScrollableViewPager;
import com.example.outfits.ModifyActivity;
import com.example.outfits.MyApplication;
import com.example.outfits.OtherUserActivity;
import com.example.outfits.R;
import com.example.outfits.RetrofitStuff.GetBlogRequest;
import com.example.outfits.RetrofitStuff.PostInterfaces;
import com.example.outfits.RetrofitStuff.ResponseModel;
import com.example.outfits.ShowFansListActivity;
import com.example.outfits.ShowFocusListActivity;
import com.example.outfits.UserInfoActivity;
import com.example.outfits.Utils.RetrofitUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.outfits.Utils.ConstantUtil.FAILED;
import static com.example.outfits.Utils.ConstantUtil.SUCCESS_CODE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1="param1";
    private static final String ARG_PARAM2="param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private static final String TAG = "UserFragment";
    private RecyclerView list;
    private List<Blog> datas;
    private TextView fllowCount;
    private TextView fansCount;
    private TextView name;
    private ImageView icon;
    private Button btn_modify;
    private FloatingActionButton btn_createBlog;
    private TextView blocUser;
    private TextView blocFollow;
    private ViewPager2 viewPager2;
    private List<Fragment> mFragmentArray=new ArrayList<>();

    public UserFragment(){
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1,String param2){
        UserFragment fragment=new UserFragment();
        Bundle args=new Bundle();
        args.putString(ARG_PARAM1,param1);
        args.putString(ARG_PARAM2,param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mParam1=getArguments().getString(ARG_PARAM1);
            mParam2=getArguments().getString(ARG_PARAM2);
        }
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
        name = view.findViewById(R.id.user_name);
        icon = view.findViewById(R.id.user_image);
        mFragmentArray.add(MyBlogFragment.newInstance(null));
        mFragmentArray.add(MyCollectionFragment.newInstance(null));
        BlogFragmentAdapter blogFragmentAdapter = new BlogFragmentAdapter(this, null, 2);
        viewPager2 = view.findViewById(R.id.vp_blog);
        viewPager2.setAdapter(blogFragmentAdapter);

        PostInterfaces request = RetrofitUtil.retrofit.create(PostInterfaces.class);

        //获取用户头像和昵称
        Call<ResponseModel<UserInfo>> call1 = request.getIntro("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyIiwiaWF0IjoxNjIwNzM1NjAyLCJzdWIiOiIxNTI2MDAxMTM4NSIsImlzcyI6InJ1aWppbiIsImV4cCI6MTYyMDk5NDgwMn0.SM7ERdR_qw3gSHjwtoYuM9XO2Zjd7IHymHTAHusRYFw",
                new GetBlogRequest(2));
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
                                        .create(getContext().getResources(), resource);
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
        Call<ResponseModel<UserInfo[]>> call2 = request.getSubscriber("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyIiwiaWF0IjoxNjIwNzM1NjAyLCJzdWIiOiIxNTI2MDAxMTM4NSIsImlzcyI6InJ1aWppbiIsImV4cCI6MTYyMDk5NDgwMn0.SM7ERdR_qw3gSHjwtoYuM9XO2Zjd7IHymHTAHusRYFw",
                new GetBlogRequest(2));
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
        Call<ResponseModel<UserInfo[]>> call3 = request.getSubscription("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyIiwiaWF0IjoxNjIwNzM1NjAyLCJzdWIiOiIxNTI2MDAxMTM4NSIsImlzcyI6InJ1aWppbiIsImV4cCI6MTYyMDk5NDgwMn0.SM7ERdR_qw3gSHjwtoYuM9XO2Zjd7IHymHTAHusRYFw",
                new GetBlogRequest(2));
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
            }
        });

        blocUser.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //传递关于“我的博客”数据进入
                viewPager2.setCurrentItem(0);
            }
        });

        blocFollow.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //传递关于“我的收藏”数据进入
                viewPager2.setCurrentItem(1);
            }
        });
    }
}