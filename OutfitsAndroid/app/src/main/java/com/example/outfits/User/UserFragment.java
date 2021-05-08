package com.example.outfits.User;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.outfits.Adapter.BlogAdapter;
import com.example.outfits.Bean.Blog;
import com.example.outfits.ModifyActivity;
import com.example.outfits.R;
import com.example.outfits.ShowFansListActivity;
import com.example.outfits.ShowFocusListActivity;

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

    private static final String TAG = "UserFragment";
    private RecyclerView list;
    private List<Blog> datas;
    private TextView fllowCount;
    private TextView fansCount;
    private Button btn_modify;
    private Button btn_createBloc;
    private TextView blocUser;
    private TextView blocFollow;

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
        //onCreateView();
        list = (RecyclerView)this.getActivity().findViewById(R.id.id_recyclerview);
        initData();
        //设置默认的显示样式
        showList(true);

        fllowCount = this.getActivity().findViewById(R.id.followCount);
        fansCount = this.getActivity().findViewById(R.id.fansCount);
        btn_modify = this.getActivity().findViewById(R.id.btn_modify);
        btn_createBloc = this.getActivity().findViewById(R.id.btn_createBloc);

        blocUser = this.getActivity().findViewById(R.id.blocUser);
        blocFollow = this.getActivity().findViewById(R.id.blocFollow);

        this.getActivity().findViewById(R.id.followCount).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), ShowFocusListActivity.class);
                startActivity(intent);
            }
        });

        this.getActivity().findViewById(R.id.fansCount).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), ShowFansListActivity.class);
                startActivity(intent);
            }
        });

        this.getActivity().findViewById(R.id.btn_modify).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), ModifyActivity.class);
                startActivity(intent);
            }
        });

        this.getActivity().findViewById(R.id.btn_createBloc).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //和Web嵌入交互
                Intent intent = new Intent(getActivity(), ModifyActivity.class);
                startActivity(intent);
            }
        });

        this.getActivity().findViewById(R.id.blocUser).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //传递关于“我的博客”数据进入
            }
        });

        this.getActivity().findViewById(R.id.blocFollow).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //传递关于“我的收藏”数据进入
            }
        });

    }

    private void initData() {
        //List<DataBean>------>Adapter------>setAdapter---->显示数据
        //创建数据集合
        datas = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Blog data = new Blog();
            data.setBlogPic("这是第" + i + "张图片");
            data.setBlogTitle("这是第" + i + "项");
            data.setBlogIntroduce("11111111111");
            datas.add(data);
        }
    }

    private void showList(boolean isVertical) {
        //RecyclerView需要设置样式，其实就是设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        //设置水平还是垂直
        layoutManager.setOrientation(isVertical? LinearLayoutManager.VERTICAL: LinearLayoutManager.HORIZONTAL);
        list.setLayoutManager(layoutManager);
        //创建适配器
        BlogAdapter adapter = new BlogAdapter(this.getContext(),datas);
        //设置到RecyclerView中
        list.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user,container,false);
    }
}