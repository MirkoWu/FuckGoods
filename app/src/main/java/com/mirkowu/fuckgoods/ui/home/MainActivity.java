package com.mirkowu.fuckgoods.ui.home;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mirkowu.fuckgoods.R;
import com.mirkowu.fuckgoods.app.Constants;
import com.mirkowu.fuckgoods.base.FragmentBasePagerAdapter;
import com.mirkowu.fuckgoods.base.SimpleActivity;
import com.mirkowu.fuckgoods.ui.girls.GirlsActivity;
import com.mirkowu.fuckgoods.ui.home.fragment.HomeFragment;
import com.mirkowu.fuckgoods.util.ImageUtil;

import butterknife.BindView;

public class MainActivity extends SimpleActivity {

    @BindView(R.id.mDrawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.mNavigationView)
    NavigationView mNavigationView;

    AppCompatImageView img_head;
    AppCompatTextView tv_nickname;

    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_popu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings)
            startActivity(GirlsActivity.class);
        else if (item.getItemId() == android.R.id.home)
            mDrawerLayout.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initialize() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        img_head = (AppCompatImageView) mNavigationView.getHeaderView(0).findViewById(R.id.img_head);
        tv_nickname = (AppCompatTextView) mNavigationView.getHeaderView(0).findViewById(R.id.tv_nickname);

        //
        ImageUtil.loadCircle(img_head, Constants.Image1);

        initViewPager();
    }

    private void initViewPager() {
        String[] title = getResources().getStringArray(R.array.tab_type);
        FragmentBasePagerAdapter adapter = new FragmentBasePagerAdapter(getSupportFragmentManager(), HomeFragment.class, title);

        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(adapter.getCount());
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }

}
