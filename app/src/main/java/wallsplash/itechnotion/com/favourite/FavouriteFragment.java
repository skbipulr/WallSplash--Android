package wallsplash.itechnotion.com.favourite;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wallsplash.itechnotion.com.MainActivity;
import wallsplash.itechnotion.com.bean.FavouriteBean;
import wallsplash.itechnotion.com.bean.PhotosBean;
import wallsplash.itechnotion.com.bean.TrendingBean;
import wallsplash.itechnotion.com.details.DetailFragment;
import wallsplash.itechnotion.com.home.HomeFragment;
import wallsplash.itechnotion.com.home.NewPhotosAdapter;
import wallsplash.itechnotion.com.home.TrendingPhotoByIdAdapter;
import wallsplash.itechnotion.com.retrofit.Config;
import wallsplash.itechnotion.com.retrofit.RestClient;
import wallsplash.itechnotion.com.utils.SharedObjects;
import wallsplash.itechnotion.com.wallsplash.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment implements FavouriteAdapter.OnCategorybyidSelectedListner {


    @BindView(R.id.rvFavourite)
    RecyclerView rvFavourite;
    Unbinder unbinder;
    private FavouriteAdapter favouriteAdapter;
    private ArrayList<FavouriteBean> favouriteList = new ArrayList<>();
    private DatabaseReference databaseReference;
    private AdView mAdView;
    String uid;
    SharedObjects sharedObjects;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        unbinder = ButterKnife.bind(this, view);
        mAdView = view.findViewById(R.id.adView);
        Banner();
        sharedObjects = new SharedObjects(getActivity());
        uid = sharedObjects.getUserID();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.keepSynced(true);

        DatabaseReference userIdRef = databaseReference.child(uid).child("favourites").getRef();
        userIdRef.keepSynced(true);
        favouriteList = new ArrayList<>();
        bindFavouriteData();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                favouriteList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    FavouriteBean post = ds.getValue(FavouriteBean.class);
                    String id = ds.getKey();
                    String url = post.getUrl();
                    favouriteList.add(new FavouriteBean(id, url));
                    // favouriteList.add(ds.getValue(FavouriteBean.class));
                }

                favouriteAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("LogFragment", "loadLog:onCancelled", databaseError.toException());
            }
        };
        userIdRef.addValueEventListener(valueEventListener);
//        favouriteAdapter.notifyDataSetChanged();

        //getFavouritePhotos();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.rvFavourite)
    public void onViewClicked() {
    }


    public void Banner() {
        //mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdLeftApplication() {
            }

            @Override
            public void onAdClosed() {
            }
        });


    }

    private void getAllTask(DataSnapshot dataSnapshot) {

        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

          /*  String id = singleSnapshot.getValue(String.class);
            String url = singleSnapshot.getValue(String.class);

            favouriteList.add(new FavouriteBean(id,url));*/
            favouriteList.add(singleSnapshot.getValue(FavouriteBean.class));
        }

        bindFavouriteData();
    }

    private void bindFavouriteData() {

        favouriteAdapter = new FavouriteAdapter(getActivity(), favouriteList);
        favouriteAdapter.setOnCategorybyidSelectedListner(this);
        rvFavourite.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvFavourite.setAdapter(favouriteAdapter);
    }


    private void loadFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);
        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.fl_container, fragment, null);
            ft.hide(FavouriteFragment.this);
            ft.addToBackStack(backStateName);
            ft.commit();
        } else {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(R.id.fl_container, fragment, null);
            ft.hide(FavouriteFragment.this);
            if (fragment instanceof DetailFragment) {

            } else {
                ft.addToBackStack(backStateName);
            }
            //ft.addToBackStack(backStateName);
            ft.commit();
          /*  for(int entry = 0; entry < fragmentManager.getBackStackEntryCount(); entry++){
                //Log.i(TAG, "Found fragment: " + fragmentManager.getBackStackEntryAt(entry).getId());
                DetailFragment fragment1= (DetailFragment) getChildFragmentManager().findFragmentById(fragmentManager.getBackStackEntryAt(entry).getId());
                fragment1.refreshData(fragment.getArguments());
            }*/
        }
    }

    @Override
    public void setOnCategorybyidSelatedListner(int position, FavouriteBean favouriteBean) {
        DetailFragment newsDetailsFragment = DetailFragment.newInstance(favouriteBean.getId());
        loadFragment(newsDetailsFragment);
    }
}

