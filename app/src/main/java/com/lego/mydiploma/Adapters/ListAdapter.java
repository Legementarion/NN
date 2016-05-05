package com.lego.mydiploma.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lego.mydiploma.Objects.SandObject;
import com.lego.mydiploma.R;

import java.util.List;

/**
 * @author Lego on 18.03.2016.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.DataViewHolder>{

    List<SandObject> sandObjects;
    public ListAdapter(List<SandObject> sandObject){
        this.sandObjects = sandObject;
    }

    @Override
    public ListAdapter.DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        DataViewHolder pvh = new DataViewHolder(v);
        return pvh;

    }

    @Override
    public void onBindViewHolder(ListAdapter.DataViewHolder holder, int position) {
        holder.objectName.setText(sandObjects.get(position).name);
        holder.objectValue.setText(sandObjects.get(position).value);
    }

    @Override
    public int getItemCount() {
        return sandObjects.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView objectName;
        TextView objectValue;

        DataViewHolder(View itemView) {
            super(itemView);
            objectName = (TextView)itemView.findViewById(R.id.name);
            objectValue = (TextView)itemView.findViewById(R.id.value);
        }
    }
}
