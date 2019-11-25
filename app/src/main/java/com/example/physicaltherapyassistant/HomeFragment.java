package com.example.physicaltherapyassistant;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class HomeFragment extends Fragment {

    private RecyclerView homeRecyclerView;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String androidId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        super.onCreate(savedInstanceState);
        androidId = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        database = FirebaseDatabase.getInstance();
        myRef= database.getInstance().getReference().child(androidId);
        myRef.keepSynced(true);

        homeRecyclerView = view.findViewById(R.id.homerecyclerview);
        homeRecyclerView.setHasFixedSize(true);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<RepLog, RepLogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RepLog, RepLogViewHolder>
                (RepLog.class, R.layout.workout_row, RepLogViewHolder.class, myRef) {
            @Override
            protected void populateViewHolder(RepLogViewHolder repLogViewHolder, RepLog repLog, int i) {
                repLogViewHolder.setTime(repLog.getTimeDate());
                repLogViewHolder.setPercentFacePulls(Integer.toString(repLog.getPercentFacePullReps()));
                repLogViewHolder.setPercentLowerTraps(Integer.toString(repLog.getPercentLowerTrapsReps()));
                repLogViewHolder.setPercentRotatorCuffs(Integer.toString(repLog.getPercentRotatorCuffReps()));
                repLogViewHolder.setPercentSwimmers(Integer.toString(repLog.getPercentSwimmersReps()));
            }
        };

        homeRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    public static class RepLogViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public RepLogViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setTime(String time) {
            TextView post_time=mView.findViewById(R.id.cardTime);
            post_time.setText(time);
        }

        public void setPercentFacePulls(String percentFacePulls) {
            TextView post_percentFacePulls=mView.findViewById(R.id.cardPercentFacePulls);
            post_percentFacePulls.setText("Face Pulls: " + percentFacePulls + "% Correct");
        }

        public void setPercentLowerTraps(String percentLowerTraps) {
            TextView post_percentLowerTraps=mView.findViewById(R.id.cardPercentLowerTraps);
            post_percentLowerTraps.setText("Lower Traps: " + percentLowerTraps + "% Correct");
        }

        public void setPercentRotatorCuffs(String percentRotatorCuffs) {
            TextView post_percentRotatorCuffs=mView.findViewById(R.id.cardPercentRotatorCuffs);
            post_percentRotatorCuffs.setText("Rotator Cuffs: " + percentRotatorCuffs + "% Correct");
        }

        public void setPercentSwimmers(String percentSwimemrs) {
            TextView post_percentSwimmers=mView.findViewById(R.id.cardPercentSwimmers);
            post_percentSwimmers.setText("Swimmers: " + percentSwimemrs + "% Correct");
        }

    }
}
