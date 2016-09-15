package com.example.zhaojw.testcustomview.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;
import com.example.zhaojw.testcustomview.R;
import com.example.zhaojw.testcustomview.fragment.TestFragment;
import com.example.zhaojw.testcustomview.view.ScrollTitleBar;

import java.util.ArrayList;

/**
 * Created by zjw on 2016/9/15.
 */
public class ScrollTitleBarActivity extends FragmentActivity {

    private static int PAGE_NUM = 7;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentArrayList= new ArrayList<Fragment>();
    private MyFragmentPagerAdapter adapter;

    private ScrollTitleBar scrollTitleBar;
    private String[] titleNames = {"fragment_0","fragment_1","fragment_2","fragment_3","fragment_4","fragment_5","fragment_6",};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scroll_titie_bar);
        initViewPager();
        initTitleBar();

    }



    private void initViewPager(){
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        for(int i = 0; i < PAGE_NUM ; i++){
            Bundle bundle = new Bundle();
            bundle.putString("fragment_text", "This is Fragment "+ i);
            TestFragment fragment = TestFragment.newInstance(bundle);
            fragmentArrayList.add(fragment);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new MyFragmentPagerAdapter(fragmentManager, fragmentArrayList);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                scrollTitleBar.selectItemAt(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
    }

    private void initTitleBar(){
        scrollTitleBar = (ScrollTitleBar) findViewById(R.id.titleBar);
        ArrayList<String> list = new ArrayList<String>();
        for(String string : titleNames){
            list.add(string);
        }
        scrollTitleBar.setItemSelectedListener(new ScrollTitleBar.ItemSelectedListener(){
            @Override
            public void onItemSelected(int index) {
                viewPager.setCurrentItem(index);
            }
        });
        scrollTitleBar.setDataList(list);
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentArrayList;

        public MyFragmentPagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> list) {
            super(fragmentManager);
            fragmentArrayList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }
    }
}
