package com.example.chris.changiappv3;

/**
 * Created by chris on 25/11/2017.
 */
        import android.content.ContentValues;
        import android.content.Context;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.graphics.drawable.Drawable;
        import android.net.Uri;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.bumptech.glide.Glide;

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.List;

public class AdapterLocation extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<DataLocation> data= Collections.emptyList();
    DataLocation current;
    int currentPos=0;


    public LocationDbHelper locationDbHelper;
    public SQLiteDatabase locationDb;


    // create constructor to innitilize context and data sent from MainActivity
    public AdapterLocation(Context context, List<DataLocation> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;


        locationDbHelper=new LocationDbHelper(context);
        locationDb=locationDbHelper.getWritableDatabase();
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_location, parent,false);
        MyHolder holder=new MyHolder(view);


        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // Get current position of item in recyclerview to bind data and assign values from list
        final DataLocation current = data.get(position);
            MyHolder myHolder = (MyHolder) holder;
            myHolder.textLocationName.setText(current.loationName);
            myHolder.Locationtype.setText("Rating: " + current.ratings);
            myHolder.textType.setText("Category: " + current.catName);
            myHolder.textPrice.setText("Price: $" + current.price);
            myHolder.textPrice.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

            // load image into imageview using glide
            //Glide.with(context).load("http://192.168.1.7/test/images/" + current.fishImage)
            Log.i("image", current.loationImage);
//        Glide.with(context).load(current.loationImage)
//              //  .placeholder(R.drawable.ic_img_error)
//               // .error(R.drawable.ic_img_error)
//                .placeholder(R.drawable.ic_launcher_background)
//                .error(R.drawable.ic_launcher_background)
//                .into(myHolder.ivLocation);

            final String name = current.loationName;
            final int amount = current.price;
            int id = context.getResources().getIdentifier(current.loationImage, "drawable", context.getPackageName());


            myHolder.ivLocation.setImageResource(id);
            ((MyHolder) holder).find.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, " you clicked " + name,Toast.LENGTH_SHORT).show();
                    Uri.Builder builder = new Uri.Builder();
                    //In the builder class, the respective function are cascaded to form a modified instance
                    builder.scheme("geo").opaquePart("0,0").appendQueryParameter("q", name);
                    //returns the modified instance of URI
                    Uri geoLocation = builder.build();

                    //TO DO 1.3 - write the intent
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoLocation);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    context.startActivity(mapIntent);

                }
            });

            ((MyHolder) holder).add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // String put="INSERT INTO "+LocationsContract.LocationEntry.TABLE_NAME+" VALUES ("+amount+
                    //         " , "+name+");";

                    ContentValues cv = new ContentValues(); //just to store key value pair
                    cv.put(LocationsContract.LocationEntry.COL_AMOUNT, amount); //"Spending amount" , amoutEntered
                    cv.put(LocationsContract.LocationEntry.COL_LOCATIONNAME, name);//"Remarks" , remarksEntered
                    if(checkdataexist(name)){
                        Toast.makeText(context, name+" has already been added" , Toast.LENGTH_LONG).show();
                    }
                    else {
                        locationDb.insert(LocationsContract.LocationEntry.TABLE_NAME, null, cv);
                        //        locationDb.execSQL(put);
                        Toast.makeText(context, name + " has been added to your plans", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ((MyHolder) holder).remove.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View view) {

                    String delete = "DELETE FROM " + LocationsContract.LocationEntry.TABLE_NAME + " WHERE "
                            + LocationsContract.LocationEntry.COL_LOCATIONNAME
                            + " = '" + name + "'";
                    System.out.println(delete);
                    locationDb.execSQL(delete);
                    // locationDb.delete(LocationsContract.LocationEntry.TABLE_NAME, LocationsContract.LocationEntry.COL_LOCATIONNAME+"="+name,null);
                    Toast.makeText(context, name + " has been deleted your plans", Toast.LENGTH_SHORT).show();
                }
            });

        }

    public boolean checkdataexist(String fieldValue) {
        String Query = "Select * from " + LocationsContract.LocationEntry.TABLE_NAME + " where " + LocationsContract.LocationEntry.COL_LOCATIONNAME + " = '" + fieldValue + "'";
        Cursor cursor = locationDb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }




    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView textLocationName;
        ImageView ivLocation;
        TextView Locationtype;
        TextView textType;
        TextView textPrice;
        RelativeLayout relativeLayout;
        Button find;
        Button add;
        Button remove;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textLocationName= (TextView) itemView.findViewById(R.id.textlocationName);
            ivLocation= (ImageView) itemView.findViewById(R.id.ivLocation);
            Locationtype = (TextView) itemView.findViewById(R.id.textSize);
            textType = (TextView) itemView.findViewById(R.id.textType);
            textPrice = (TextView) itemView.findViewById(R.id.locationPrice);
            relativeLayout=(RelativeLayout) itemView.findViewById(R.id.relativeLayout);
            find=(Button) itemView.findViewById(R.id.Find);
            add=(Button) itemView.findViewById(R.id.Add);
            remove=(Button) itemView.findViewById(R.id.Remove);

        }

    }

}