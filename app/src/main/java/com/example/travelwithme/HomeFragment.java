package com.example.travelwithme;


import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Movie;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeAdapterListener {


    public ArrayList<String> cities;
    public String[] temp = {"London", "Vancouver", "New Delhi", "Amsterdam", "Venice", "Los Angeles", "New York", "Paris", "Toronto", "Dubai"};


    private RecyclerView recyclerView;
    private HomeAdapter mAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        cities = new ArrayList<>();
        for (String s : temp)
            cities.add(s);



        recyclerView = view.findViewById(R.id.recycler_view);

        mAdapter = new HomeAdapter(getContext(), cities, this);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);


        return view;
    }






    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item, menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSelected(String item) {
        Toast.makeText(getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }

        /**
         * Converting dp to pixel
         */



    }


    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> implements Filterable {
        private Context context;
        private ArrayList<String> cities;
        private ArrayList<String> filteredItems;
        private HomeAdapterListener listener;
        private City myCity;
        private GetData data;
        private ReceiveData receiveData;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title;
            public ImageView thumbnail;

            public MyViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.title);
                thumbnail = view.findViewById(R.id.thumbnail);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onSelected(filteredItems.get(getAdapterPosition()));
                    }
                });


            }


        }


        public HomeAdapter(Context context, ArrayList<String> cities, HomeAdapterListener listener) {
            this.context = context;
            this.cities = cities;
            this.listener = listener;
            this.filteredItems = cities;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.store_item_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            final String cityName = filteredItems.get(position);
            String locData;
            String placeData;
            myCity = new City();
            receiveData = new ReceiveData();
            data = new GetData();


            locData = data.getData("https://maps.googleapis.com/maps/api/geocode/json?address=" + cityName + "&key=AIzaSyBYNwaeGibgmiD_43QTVQ4F-YkVkWeM00w");

            myCity = receiveData.cityData(locData);


            placeData = data.getData("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + myCity.getLat() + "," + myCity.getLng() + "&radius=5000&type=city&key=AIzaSyBYNwaeGibgmiD_43QTVQ4F-YkVkWeM00w");
            String reference = receiveData.getPhotoReference(placeData);


            holder.title.setText(cityName);


            Glide.with(context)
                    .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + reference + "&key=AIzaSyBYNwaeGibgmiD_43QTVQ4F-YkVkWeM00w")

                    .into(holder.thumbnail);

            holder.thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CityFragment city = new CityFragment();
                    Bundle args = new Bundle();
                    args.putString("city", cities.get(position));
                    city.setArguments(args);

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container, city);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return filteredItems.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    String query = constraint.toString();

                    List<String> filtered = new ArrayList<>();

                    if (query.isEmpty()) {
                        filtered = cities;
                    } else {
                        for (String city : cities) {
                            if (city.toLowerCase().contains(query.toLowerCase())) {
                                filtered.add(city);
                            }
                        }
                    }

                    FilterResults results = new FilterResults();
                    results.count = filtered.size();
                    results.values = filtered;
                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults results) {
                    filteredItems = (ArrayList<String>) results.values;
                    notifyDataSetChanged();
                }
            };
        }


    }

}


