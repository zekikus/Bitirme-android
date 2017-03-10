package com.matas.ats;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.matas.ats.modules.AlarmFragment;
import com.matas.ats.modules.BirimTanimFragment;
import com.matas.ats.modules.DolapTipiFragment;
import com.matas.ats.modules.KullaniciFragment;
import com.matas.ats.modules.STCFragment;
import com.matas.ats.modules.StokBirimFragment;
import com.matas.ats.modules.TuketimNedeniFragment;
import com.matas.ats.modules.UreticiFragment;
import com.matas.ats.modules.UrunFragment;
import com.matas.ats.modules.UrunTipiFragment;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialize.util.UIUtils;

public class MainActivity extends AppCompatActivity {
    private static final int PROFILE_SETTING = 1;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        // Handle Toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.drawer_item_compact_header);

        // Create a few sample profile
        final IProfile profile = new ProfileDrawerItem().withName("Muhammed Akif Taş").withEmail("muhammedakiftas@gmail.com").withIcon(R.drawable.profile);
        //final IProfile profile2 = new ProfileDrawerItem().withName("Max Muster").withEmail("max.mustermann@gmail.com").withIcon(R.drawable.profile2);

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        profile,
                        //profile2,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        //new ProfileSettingDrawerItem().withName("Çıkış").withDescription("Add new GitHub Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_plus).actionBar().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withIdentifier(PROFILE_SETTING)//,
                        new ProfileSettingDrawerItem().withName("Çıkış Yap").withIcon(GoogleMaterial.Icon.gmd_accounts_outline)
                )
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.d_i_home).withIcon(FontAwesome.Icon.faw_home).withIdentifier(0),

                        new ExpandableDrawerItem().withName(R.string.d_i_section_header).withIcon(GoogleMaterial.Icon.gmd_collection_case_play).withIdentifier(19).withSubItems(
                                new SecondaryDrawerItem().withName(R.string.d_i_stok_birim).withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(1),
                                new SecondaryDrawerItem().withName(R.string.d_i_tuketim_nedeni).withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(2),
                                new SecondaryDrawerItem().withName(R.string.d_i_uretici).withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(3),
                                new SecondaryDrawerItem().withName(R.string.d_i_urun).withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(4),
                                new SecondaryDrawerItem().withName(R.string.d_i_alarm).withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(5),
                                new SecondaryDrawerItem().withName(R.string.d_i_birim).withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(6),
                                new SecondaryDrawerItem().withName(R.string.d_i_dolap_type).withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(7),
                                new SecondaryDrawerItem().withName(R.string.d_i_user).withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(8),
                                new SecondaryDrawerItem().withName(R.string.d_i_stc).withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(9),
                                new SecondaryDrawerItem().withName(R.string.d_i_imha).withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(10)
                        )
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {

                            Fragment mFragment = null;
                            FragmentManager mFragmentManager = getSupportFragmentManager();

                            if (drawerItem.getIdentifier() == 0) {
                                //startSupportActionMode(new ActionBarCallBack());
                                //findViewById(R.id.action_mode_bar).setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(MainActivity.this, R.attr.colorPrimary, R.color.material_drawer_primary));
                            }else if (drawerItem.getIdentifier() == 1) {
                                mFragment = new StokBirimFragment();
                            }else if (drawerItem.getIdentifier() == 2) {
                                mFragment = new TuketimNedeniFragment();
                            }else if (drawerItem.getIdentifier() == 3) {
                                mFragment = new UreticiFragment();
                            }else if(drawerItem.getIdentifier() == 4){
                                mFragment = new UrunFragment();
                            }else if(drawerItem.getIdentifier() == 5){
                                mFragment = new AlarmFragment();
                            }else if(drawerItem.getIdentifier() == 6){
                                mFragment = new BirimTanimFragment();
                            }else if(drawerItem.getIdentifier() == 7){
                                mFragment = new DolapTipiFragment();
                            }else if(drawerItem.getIdentifier() == 8){
                                mFragment = new KullaniciFragment();
                            }else if(drawerItem.getIdentifier() == 9){
                                mFragment = new STCFragment();
                            }else if(drawerItem.getIdentifier() == 10){
                                mFragment = new UrunTipiFragment();
                            }

                            if (mFragment != null) {
                                mFragmentManager.beginTransaction().replace(R.id.frame_container, mFragment).commit();
                            }

                        }

                        if (drawerItem instanceof Nameable) {
                            toolbar.setTitle(((Nameable) drawerItem).getName().getText(MainActivity.this));
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        // set the selection to the item with the identifier 5
        if (savedInstanceState == null) {
            result.setSelection(5, false);
        }

        //set the back arrow in the toolbar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(false);


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle the click on the back arrow click
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    class ActionBarCallBack implements ActionMode.Callback {

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(UIUtils.getThemeColorFromAttrOrRes(MainActivity.this, R.attr.colorPrimaryDark, R.color.material_drawer_primary_dark));
            }

            mode.getMenuInflater().inflate(R.menu.cab, menu);
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
    }
}
