package com.example.outfits.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.outfits.Bean.SubTypeClothingBean;
import com.example.outfits.Bean.Type;
import com.example.outfits.UI.ClothesFragment;
import com.example.outfits.RetrofitStuff.GetClothingRequest;

import java.util.ArrayList;
import java.util.List;

//
public class ClothesFragmentAdapter extends FragmentStateAdapter{
    List<Type> types;

    public ClothesFragmentAdapter(Fragment fragment,List<Type> types) {
        super(fragment);
        this.types=types;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        List<SubTypeClothingBean> subTypeClothingBeans=new ArrayList<>();

        GetClothingRequest request=new GetClothingRequest(types.get(position).getTypeId());
//        RetrofitUtil.postGetClothing(request,subTypeClothingBeans);
//        subTypeClothingBeans.add(new SubTypeClothingBean(1,null));
        //        Bundle args = new Bundle();
//        // Our object is just an integer :-P
//        args.putInt("position", position + 1);
//        fragment.setArguments(args);
        return ClothesFragment.newInstance(subTypeClothingBeans);
    }

    @Override
    public int getItemCount() {
        return types.size();
    }


}
