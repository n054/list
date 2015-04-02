/* The List powered by Creative Commons

   Copyright (C) 2014, 2015 Creative Commons

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU Affero General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU Affero General Public License for more details.

   You should have received a copy of the GNU Affero General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.

*/

package org.creativecommons.thelist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.creativecommons.thelist.R;
import org.creativecommons.thelist.fragments.AddItemFragment;
import org.creativecommons.thelist.fragments.GalleryFragment;
import org.creativecommons.thelist.fragments.MyListFragment;
import org.creativecommons.thelist.fragments.NavigationDrawerFragment;

import java.util.ArrayList;

public class DrawerActivity extends ActionBarActivity implements
        NavigationDrawerFragment.NavigationDrawerListener, GalleryFragment.GalleryListener {
    public static final String TAG = MyListFragment.class.getSimpleName();

    DrawerLayout mDrawerLayout;
    View mDrawerView;

    // --------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        //UI Elements
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerView = findViewById(R.id.navigation_drawer);


        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        drawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout)findViewById(R.id.drawer_layout), toolbar);

        //If there is no savedInstanceState, load in default fragment
        if(savedInstanceState == null){
            MyListFragment listFragment = new MyListFragment();
            //load default view
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_content_container, listFragment)
                    .commit();

        }
    } //onCreate

    // --------------------------------------------------------
    //Drawer Fragment
    // --------------------------------------------------------

    @Override
    public void onDrawerClicked(int position) {
//        <item>My List</item>
//        <item>My Photos</item>
//        <item>Request an Item</item>
//        <item>About The App</item>
//        <item>Settings</item>
//        <item>Log Out</item>

        Fragment fragment = null;
        switch(position) {
            case 0:
                //Go to MainActivity
                Intent mainIntent = new Intent(DrawerActivity.this, MainActivity.class);
                startActivity(mainIntent);
                break;
            case 1:
                fragment = new GalleryFragment();
                break;
            case 2:
                fragment = new AddItemFragment();
                break;
            case 3:
                Intent catIntent = new Intent(DrawerActivity.this, CategoryListActivity.class);
                startActivity(catIntent);
                break;
            case 4:
                Intent aboutIntent = new Intent(DrawerActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
                break;
            case 5:
                Log.v(TAG, "LOG OUT");
                break;
            default:
                break;
        } //switch

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_content_container, fragment)
                    .commit();

            // update selected item and title, then close the drawer
            mDrawerLayout.closeDrawer(mDrawerView);
        } else {
            // error in creating fragment
            Log.e(TAG, "Error in creating fragment");
        }
    } //onDrawerClicked


    // --------------------------------------------------------
    // Gallery Fragment
    // --------------------------------------------------------

    @Override
    public void viewImage(ArrayList<String> urls, int position) {
        //Start detailed view
        Intent intent = new Intent(DrawerActivity.this, ImageActivity.class);
        intent.putExtra("position", position);
        intent.putStringArrayListExtra("urls", urls);
        startActivity(intent);
    } //viewImage



    // --------------------------------------------------------
    // Main Menu
    // --------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}