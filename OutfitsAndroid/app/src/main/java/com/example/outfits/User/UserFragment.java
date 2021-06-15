package com.example.outfits.User;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.outfits.Adapter.BlogAdapter;
import com.example.outfits.Adapter.BlogFragmentAdapter;
import com.example.outfits.Adapter.BlogViewPagerAdapter;
import com.example.outfits.Bean.Blog;
import com.example.outfits.CustomView.ScrollableViewPager;
import com.example.outfits.ModifyActivity;
import com.example.outfits.OtherUserActivity;
import com.example.outfits.R;
import com.example.outfits.ShowFansListActivity;
import com.example.outfits.ShowFocusListActivity;
import com.example.outfits.UI.MyBlogFragment;
import com.example.outfits.UI.MyCollectionFragment;
import com.example.outfits.UserInfoActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

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
    public static com.example.outfits.UI.UserFragment newInstance(String param1, String param2){
        com.example.outfits.UI.UserFragment fragment=new com.example.outfits.UI.UserFragment();
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
        mFragmentArray.add(MyBlogFragment.newInstance(null));
        mFragmentArray.add(MyCollectionFragment.newInstance("", ""));
        BlogViewPagerAdapter blogViewPagerAdapter = new BlogViewPagerAdapter(getFragmentManager());
        BlogFragmentAdapter blogFragmentAdapter = new BlogFragmentAdapter(this, null);
        blogViewPagerAdapter.setList(mFragmentArray);
        viewPager2 = view.findViewById(R.id.vp_blog);
        viewPager2.setAdapter(blogFragmentAdapter);

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
                Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                startActivity(intent);
            }
        });

        btn_createBlog.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //和Web嵌入交互
                Intent intent = new Intent(getActivity(), OtherUserActivity.class);
                startActivity(intent);
            }
        });

        blocUser.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //传递关于“我的博客”数据进入
                viewPager2.setCurrentItem(0);
                Toast.makeText(getContext(), "长度为" + mFragmentArray.size(), Toast.LENGTH_LONG).show();
            }
        });

        blocFollow.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //传递关于“我的收藏”数据进入
                viewPager2.setCurrentItem(1);
                Toast.makeText(getContext(), "这是MyCollectionFragment", Toast.LENGTH_LONG).show();
            }
        });
    }
}