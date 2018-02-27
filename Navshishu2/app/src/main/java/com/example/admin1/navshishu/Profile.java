package com.example.admin1.navshishu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button settings;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView name,year,branch,cpi,phone,reg,y,d,m;
    private ImageButton edit;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth auth;
    private StorageReference ref;
    private ImageView im;
    Intent n;
    @Override
    public void setRetainInstance(boolean retain) {
        super.setRetainInstance(retain);
    }

    private OnFragmentInteractionListener mListener;
    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }//((Main2Activity)getActivity()).getSupportActionBar().setTitle("Profile");
        //Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        //toolbar.setTitleTextColor(0xff000000);
        // toolbar.setTitle("Profile");

    }private static SimpleDateFormat sdp=new SimpleDateFormat("dd.MM.yyyy");
     public static long getDayCount(String start, String end)
     { long diff=-1;
         try{
             Date dats=sdp.parse(start);
             Date date=sdp.parse(end);
             diff=Math.round((date.getTime()-dats.getTime())/(double)86400000);

         }
         catch (Exception e)
         {

         }
         return diff;
     }
    //handling the image chooser activity result
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview;
       // final String dd="";
        rootview=inflater.inflate(R.layout.fragment_profile, container, false);
        name=(TextView)rootview.findViewById(R.id.name);
        year=(TextView)rootview.findViewById(R.id.year);

        d=(TextView)rootview.findViewById(R.id.days);
        m=(TextView)rootview.findViewById(R.id.months);
        edit=(ImageButton)rootview.findViewById(R.id.edit);
        im=(ImageView)rootview.findViewById(R.id.imageView2);
        //offert=(TextView)rootview.findViewById(R.id.offer);
        auth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("").child("users").child(auth.getCurrentUser().getUid());

        mDatabaseRef.keepSynced(true);
        // StorageReference refence=ref.child("users_image").child(auth.getCurrentUser().getUid());

        Log.d("harry123", "Do In back 1");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dd;
                for(DataSnapshot post : dataSnapshot.getChildren() ) {
                    Log.d("debo11", "" + post);
                    String strkey=post.getKey().toString();
                    String str = post.getValue().toString();
                    Log.d("harry123",""+strkey+" "+str);
                    if(strkey.equals("name"))
                        name.setText(str);
                    else if(strkey.equals("sdate"))
                    { dd=str;
                        String cd = DateFormat.getDateTimeInstance().format(new Date());
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                        String formattedDate = df.format(c.getTime());
                        Log.d("check",""+formattedDate);
                        //tring ar[]=formattedDate.split(" ");
                        //String are[]=ar[0].split("-");
                      //  int ye=Integer.parseInt(are[0]);
                      //  int da=Integer.parseInt(are[2]);
                      //  int mo=Integer.parseInt(are[1]);
                        //String sd[]=dd.split("-");
                      //  int ye1=Integer.parseInt(sd[2]);
                       // int da1=Integer.parseInt(sd[0]);
                       // int mo1=Integer.parseInt(sd[1]);
                        //int yy=(ye-ye1);
                        long dayc=getDayCount(dd,formattedDate);
                        d.setText(String.valueOf(dayc));
                        //ChronoUnit.DYAS.between(ar[0],dd);
                        //y.setText(Integer.toString(yy));
                        //d.setText(Integer.toString(da-da1));
                        //m.setText(Integer.toString(mo-mo1));
                       // y.setText(cd);
// textView is the TextView view that should display it

                    }

                    else if(strkey.equals("address"))
                        year.setText(str);
                    else if(strkey.equals("pic")&&str!="")
                    {
                        Picasso.with(getActivity())
                                .load(str)
                                .fit()
                                .into(im);
                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.d("harry123", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
        n = new Intent(getActivity(), edit.class);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n.putExtra("category", auth.getCurrentUser().getUid().toString());
                Log.d("harry123_audioFileName",auth.getCurrentUser().getUid().toString());
                startActivity(n);
            }
        });

        return rootview;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
