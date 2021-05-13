package com.example.outfits.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.outfits.Bean.Occasion;
import com.example.outfits.UI.OutfitTypeFragment;

import java.util.List;

//
public class OutfitFragmentAdapter extends FragmentStateAdapter{
    List<Occasion> occasions;
    Fragment sourceFragment;

    public OutfitFragmentAdapter(Fragment fragment,List<Occasion> occasions) {
        super(fragment);
        this.occasions=occasions;
        this.sourceFragment=fragment;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        OutfitTypeFragment outfitTypeFragment=new OutfitTypeFragment();
        outfitTypeFragment.occasion=occasions.get(position);
        return outfitTypeFragment;
    }

    @Override
    public int getItemCount() {
        if(occasions!=null){
            return occasions.size();
        }
        return 0;
    }

    public void RefreshFragments(List<Occasion> occasions){
//        //避免重复创建fragment
//        if(this.fragments != null){
//            FragmentTransaction ft = sourceFragment.getFragmentManager().beginTransaction();
//            for(Fragment f:this.fragments){
//                ft.remove(f);
//            }
//            ft.commit();
//            ft=null;
//            sourceFragment.getFragmentManager().executePendingTransactions();
//        }
        this.occasions = occasions;
//        this.fragments = new ArrayList<>();
//        initFragments();
        notifyDataSetChanged();
    }


}
