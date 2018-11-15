package com.codecool.grocerylist.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codecool.grocerylist.R;
import com.codecool.grocerylist.model.GroceryListItem;
import com.codecool.grocerylist.services.DataAccess;

import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {

    private Context context;
    private List<GroceryListItem> items;
    private TextView total;
    private DataAccess dao;

    public ListItemAdapter(Context context, List<GroceryListItem> items, TextView total, DataAccess dao){
        this.context = context;
        this.items = items;
        this.total = total;
        this.dao = dao;
    }



    @NonNull
    @Override
    public ListItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);
        total.setText(getTotalAsString() + " HUF");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemAdapter.ViewHolder holder, final int position) {
        final GroceryListItem currentItem = items.get(position);

        holder.itemName.setText(currentItem.getName());
        holder.quantity.setText(String.valueOf(currentItem.getQuantity()));
        holder.price.setText(String.valueOf(currentItem.getPrice()*currentItem.getQuantity()));
        holder.increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroceryListItem item = items.get(position);
                int currentQty = item.getQuantity();
                item.setQuantity(currentQty+1);
                notifyDataSetChanged();
                dao.updateInSQL(item);
                total.setText(getTotalAsString() + " HUF");
            }
        });

        holder.decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroceryListItem item = items.get(position);
                int currentQty = item.getQuantity();
                if(currentQty == 1){
                    items.remove(item);
                    dao.deleteFromSQL(item);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, items.size());
                }else{
                    item.setQuantity(currentQty-1);
                    dao.updateInSQL(item);
                    notifyDataSetChanged();
                }
                total.setText(getTotalAsString() + " HUF");
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private String getTotalAsString(){
        int total = 0;

        for(GroceryListItem item: items){
            total+= item.getPrice()*item.getQuantity();
        }

        return String.valueOf(total);
    }





    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public TextView quantity;
        public TextView price;
        public ImageView increaseButton;
        public ImageView decreaseButton;

        public ViewHolder(View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
            itemName.setSelected(true);
            increaseButton = itemView.findViewById(R.id.increaseButton);
            decreaseButton = itemView.findViewById(R.id.decreaseButton);
        }
    }
}
