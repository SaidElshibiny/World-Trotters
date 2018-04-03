package ca.worldtrotter.stclair.worldtrotters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by Dufour on 2018-04-03.
 */

public class DestinationRecyclerViewAdapter extends RecyclerView.Adapter {

    ArrayList<Destination> destinationArrayList;

    Context context;

    public ArrayList<Destination> getDestinationArrayList() {
        return destinationArrayList;
    }

    public void setDestinationArrayList(ArrayList<Destination> destinationArrayList) {
        this.destinationArrayList = destinationArrayList;
    }


    public DestinationRecyclerViewAdapter(ArrayList<Destination> destinationArrayList) {
        this.destinationArrayList = destinationArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_destination_item, parent, false);
        final CustomViewHolder viewHolder = new CustomViewHolder(view);

        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Destination current = destinationArrayList.get(position);

        ((CustomViewHolder) holder).destinationName.setText(current.getName());

        //add the functionality to remove a destination from the trip
        ((CustomViewHolder) holder).cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                destinationArrayList.remove(pos);
                notifyItemRemoved(pos);

            }
        });

        //add the intent to onClick for destinationName
        //makes sure the user cant edit the text of the name themselves
        ((CustomViewHolder)holder).destinationName.setInputType(InputType.TYPE_NULL);
        ((CustomViewHolder)holder).destinationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this is where we edit the destination text field when the user click it - don't know how to do atm
            }
        });

        //give the start date focus
        ((CustomViewHolder) holder).startDateTime.requestFocus();

        //handel the user adding start times and end times for their trip
        ((CustomViewHolder) holder).startDateTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    //the user has left focus of this text field
                    String startDate = ((CustomViewHolder)holder).startDateTime.getText().toString().trim();
                    if( startDate != null){
                        //there is an input
                        current.setStartDateTime(startDate);
                    }
                }
            }
        });


        ((CustomViewHolder) holder).endDateTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    //the user has left focus of this text field
                    String startDate = ((CustomViewHolder)holder).endDateTime.getText().toString().trim();
                    if( startDate != null){
                        //there is an input
                        current.setEndDateTime(startDate);
                    }
                }
            }
        });

        //set up the list view that is going to hold the toDoItems
        ArrayList<String> toDoItemValues = new ArrayList<>();
        toDoItemValues.add("Eat Burger");
        toDoItemValues.add("See Sights");
        toDoItemValues.add("Get a tattoo");

        //create a new array adapter for the list items
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, toDoItemValues);

        //set the adapter to the listview
        


        ((CustomViewHolder)holder).addAgendaItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //handel the action for the add agenda item button
            }
        });
    }


    @Override
    public int getItemCount() {
        return destinationArrayList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected EditText destinationName;
        protected TextView startDateTime;
        protected TextView endDateTime;
        protected TextView cancelButton;
        protected ImageView addAgendaItemButton;
        protected ListView toDoItemListView;

        public CustomViewHolder(View view) {
            super(view);

            destinationName = (EditText) view.findViewById(R.id.add_trip_destination_edit_text);
            cancelButton = (TextView) view.findViewById(R.id.destination_cancel_button);
            startDateTime = (EditText) view.findViewById(R.id.add_trip_start_date);
            endDateTime = (EditText) view.findViewById(R.id.add_trip_end_date);
            addAgendaItemButton = (ImageView) view.findViewById(R.id.add_agenda_item_button);
            toDoItemListView = view.findViewById(R.id.to_do_item_list_view);
        }
    }

}