package com.example.hm01_racingcar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hm01_racingcar.Models.Record;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Fragment fragment;
    private ArrayList<Record> records ;
    private RecordItemClickListener recordItemClockListener;



    public RecordAdapter(Fragment fragment, ArrayList<Record> records) {
        this.fragment = fragment;
        this.records = records;
    }

    public RecordAdapter setRecordItemClickListener(RecordItemClickListener recordItemClockListener) {

        this.recordItemClockListener = recordItemClockListener;
        return this;
    }

    @NonNull
    //@Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from
                (parent.getContext()).inflate
                (R.layout.list_record_item, parent, false);

        return new RecordViewHolder(view);
    }

    //@Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecordViewHolder recordViewHolder = (RecordViewHolder) holder;
        Record record = getItem(position);


        //recordViewHolder.listItem_LBL_lat.setText("" +record.getMyLocation().getLatitube());
       // recordViewHolder.listItem_LBL_long.setText("" +record.getMyLocation().getLongitube());
        recordViewHolder.listItem_LBL_Score.setText("" + record.getScore());

    }

    // @Override
    public int getItemCount() {
        return records.size();
    }

    private Record getItem(int position) {
        return records.get(position);
    }

    public interface RecordItemClickListener {
        void recordItemClick(Record record, int position);

    }


    public class RecordViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView listItem_LBL_long;
        private MaterialTextView listItem_LBL_lat;
        private TextView listItem_LBL_Score;
        private TextView listItem_IMG_record;


        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);

           // listItem_LBL_long = itemView.findViewById(R.id.listItem_LBL_long);
            //listItem_LBL_lat = itemView.findViewById(R.id.listItem_LBL_lat);
            listItem_LBL_Score = itemView.findViewById(R.id.listItem_LBL_Score);
           //listItem_IMG_record = itemView.findViewById(R.id.listItem_IMG_record);

            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recordItemClockListener.recordItemClick
                                    (getItem(getAdapterPosition()), getAdapterPosition());
                        }
                    });
        }
    }

}
