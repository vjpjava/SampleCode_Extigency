package com.exigency.exigencycare.exigencycareapp;

import java.util.ArrayList;
import java.util.List;

import com.exigency.exigencycare.util.Utility;
import com.exigency.exigencycare.vo.NavDrawerItem;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentDrawer extends Fragment {

    private static String TAG = FragmentDrawer.class.getSimpleName();

    private ListView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private static String[] titles = null;

    private FragmentDrawerListener drawerListener;
    private String[] _drawertype;
    private int[] _drawerTypeImg;
    ImageView imageView;


    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<NavDrawerItem>();

        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(titles[i]);
            data.add(navItem);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // drawer labels
        _drawertype = getActivity().getResources().getStringArray(
                R.array.nav_drawer_labels);
        _drawerTypeImg = new int[]{R.drawable.home, R.drawable.my_appointment, R.drawable.refer_a_friend, R.drawable.contact_us, R.drawable.signout};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navi_drawer,
                container, false);
        // user image click event
        layout.findViewById(R.id.imgUserDrawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //6 mean user click on profile
                drawerListener.onDrawerItemSelected(v, 7);
                // mDrawerLayout.closeDrawer(containerView);
            }
        });

        imageView = (ImageView) layout.findViewById(R.id.imgUserDrawer);
        // Picasso.with(getActivity()).load(Utility.getPreferences(getActivity(), "USERIMAGE")).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).transform(new CircleTransform()).noFade().into(imageView);

        TextView txtEmailDrawer = (TextView) layout.findViewById(R.id.txtEmailDrawer);
        txtEmailDrawer.setVisibility(View.VISIBLE);
        txtEmailDrawer.setText("USERNAME");


        recyclerView = (ListView) layout.findViewById(R.id.drawerList);

        recyclerView.setAdapter(new DrawerAdapter());

        recyclerView.setOnItemClickListener(new DrawerItemClickListener());
        return layout;
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            recyclerView.setItemChecked(position, true);

            drawerListener.onDrawerItemSelected(view, position);
            mDrawerLayout.closeDrawer(containerView);
        }
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout,
                      final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
                // Picasso.with(getActivity()).load(Utility.getPreferences(getActivity(), "USERIMAGE")).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).transform(new CircleTransform()).noFade().into(imageView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }


    /**
     * adapter for drawer listview
     * add images and name
     */
    public class DrawerAdapter extends BaseAdapter {
        ImageView imgType;
        TextView txtType;
        View dividerLine;

        public DrawerAdapter() {
        }

        @Override
        public int getCount() {
            return _drawerTypeImg.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(final int position, View view, ViewGroup arg2) {
            LayoutInflater inflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.nav_drawer_row, null);

            imgType = (ImageView) view.findViewById(R.id.imgLeftDrawerrow);
            txtType = (TextView) view.findViewById(R.id.txtTypes);

            imgType.setImageResource(_drawerTypeImg[position]);
            // check last position
            if (_drawertype.length == position + 1) {
                // check user is logged or not
                if (Utility.getPreferencesInteger(getActivity(), "USERID") == 0) {
                    txtType.setText("Login");
                } else {
                    txtType.setText(_drawertype[position]);
                }
            } else {
                txtType.setText(_drawertype[position]);
            }


            if (_drawerTypeImg.length - 1 == position) {
                dividerLine = (View) view.findViewById(R.id.divider);
                dividerLine.setVisibility(View.GONE);
            }


            return view;

        }
    }//end adapter-------------------

}// end main class--------