package com.techdmz.lamooss.waterorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.Space;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WaterListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WaterListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaterListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public WaterListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WaterListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WaterListFragment newInstance(String param1, String param2) {
        WaterListFragment fragment = new WaterListFragment();
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
        }
    }



    private ArrayAdapter waterAdapter;
    private Spinner waterSpinner;

    private ListView WaterListView;
    private WaterListAdapter adapter;
    private List<Water> waterList;


    // For Push Message variable Start
    String regId;

    TextView logOutput;
    TextView TextViewSendData;

    RequestQueue queue;

    // For Push Message variable End




    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);


        WaterListView = (ListView) getView().findViewById(R.id.WaterListView);
        waterList = new ArrayList<Water>();
        adapter = new WaterListAdapter(getContext().getApplicationContext(), waterList);
        WaterListView.setAdapter(adapter);

        logOutput = (TextView) getView().findViewById(R.id.LogOutput);
        TextViewSendData = (TextView)getView().findViewById(R.id.TextViewSendData);

        // 주문하기 버튼클릭시 자동 조회
        new BackgroudTask().execute();

        // 굳이 버튼을 두고 할 필요가 있나? 디자인이 이쁘지 않음.
        /*
        Button searchbutton = (Button)getView().findViewById(R.id.search_button);

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroudTask().execute();
            }
        });
        */

        // Push Message start

        // ID 가져오기
        getRegistrationId();

        Button button = (Button) getView().findViewById(R.id.SendOrderButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = TextViewSendData.getText().toString().trim();
                send(data);

                Intent intent = new Intent((Activity)getContext(), MainActivity.class);
                getContext().startActivity(intent);

            }
        });


        queue= Volley.newRequestQueue(getContext().getApplicationContext());

        // getIntent는 Activity에서만 가져오므로... 억지로 해봄..
        Intent passedIntent =  ((Activity)getContext()).getIntent();
        processIntent(passedIntent);
    }



    public void processIntent(Intent intent)
    {
        if(intent != null)
        {
            String from = intent.getStringExtra("from");
            String contents = intent.getStringExtra("contents");

            println("DATA : " + from + ", " + contents);

        }
    }


    public void getRegistrationId()
    {
        regId = FirebaseInstanceId.getInstance().getToken();    // 토큰 정보(등록ID)를 받아온다.
        println("등록 ID -> " + regId);
    }

    public void send(String input)
    {
        JSONObject requestData = new JSONObject();

        try
        {
            requestData.put("priority", "high");

            JSONObject dataObj = new JSONObject();
            dataObj.put("contents", input);
            requestData.put("data", dataObj);

            JSONArray idArray = new JSONArray();
            idArray.put(0, regId);

            requestData.put("registration_ids", idArray);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        sendData(requestData, new SendResponseListener() {
            @Override
            public void onRequestStarted() {
                println("onRequestStarted() 호출됨.");
            }

            @Override
            public void onRequestCompleted() {
                println("onRequestCompleted() 호출됨.");
            }

            @Override
            public void onRequestError(VolleyError error) {
                println("onRequestError() 호출됨.");
            }
        });
    }

    public interface SendResponseListener{
        public void onRequestStarted();
        public void onRequestCompleted();
        public void onRequestError(VolleyError error);
    }

    public  void sendData(JSONObject requestData, final SendResponseListener listener)
    {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "https://fcm.googleapis.com/fcm/send",
                requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onRequestCompleted();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onRequestError(error);
                    }
                }
        )
        {
            @Override
            public String getBodyContentType() {
                //return super.getBodyContentType();
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //return super.getHeaders();
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "key=AAAAx9-2dj4:APA91bHiV8QJWJEs8fKdWGzM2QDsSSQCbABK64DPwkrhLr87l7qLAU2dREnK9PlZROUuXPW2pRWuqsyM44ldhC1VP8L34eeWOvZD8Kn2RbzDNMOLwX_mDk3MQbLX1S1TKxKSWlqS2pnA");

                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //return super.getParams();
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };

        request.setShouldCache(false);
        listener.onRequestStarted();
        queue.add(request);

    }

    public void println(String data)
    {
        logOutput.append(data + "\n");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_water_list, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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


    class BackgroudTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            try
            {
                target = "http://rudgmltndnjs.cafe24.com/wp/WaterList.php";
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }

        @Override
        protected String doInBackground(Void... voids) {
            try
            {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String temp;
                StringBuilder stringBuilder = new StringBuilder();

                while( (temp = bufferedReader.readLine()) != null )
                {
                    stringBuilder.append(temp + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected void onPostExecute(String result) {
            try
            {
                waterList.clear();

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");

                int water_id;
                String water_name;
                int water_size;
                String water_type;
                int water_price;
                String water_useyn;

                int count = 0;
                while (count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);

                    water_id = object.getInt("water_id");
                    water_name = object.getString("water_name");
                    water_size = object.getInt("water_size");
                    water_type = object.getString("water_type");
                    water_price = object.getInt("water_price");
                    water_useyn = object.getString("water_useyn");

                    /*      // for data test
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(WaterListFragment.this.getActivity());
                    dialog = builder.setMessage(water_name)
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    */

                    Water water = new Water (water_id, water_name, water_size, water_type, water_price, water_useyn);
                    waterList.add(water);
                    count++;

                }

                if (count == 0)
                {
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(WaterListFragment.this.getActivity());
                    dialog = builder.setMessage("조회된 주문가능한 리스트가 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }

                // adapter에 변경사항을 알려줘야 한다.
                adapter.notifyDataSetChanged();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        /*
        //  AlertDialog  테스트
        @Override
        protected void onPostExecute(String result) {
            try
            {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(WaterListFragment.this.getContext());

                dialog = builder.setMessage(result)
                        .setPositiveButton("확인", null)
                        .create();

                dialog.show();

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        */

    }
}
