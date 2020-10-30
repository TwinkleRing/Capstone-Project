package com.example.capstone_ui_1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.lang.ref.WeakReference;
import java.util.List;

import static android.os.Looper.getMainLooper;
import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class NavigationFragment extends Fragment implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener {
    // variables for adding location layer
    private MapView mapView;
    private MapboxMap mapboxMap;

    // variables for adding location layer
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;

    private static final String TAG = "DirectionsActivity";

    // Variables needed to add the location engine
    private LocationEngine locationEngine;
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L; // 여기 고쳐야 업데이트가 더 빠르려나?
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;

    // variable needed to listen to location update
//    private NavActivityLocationCallback callback = new NavActivityLocationCallback(this);

    double destinationLa; // latitude 목적지 위도
    double destinationLo; // longitude 목적지 경도
    public static double La; //latitude
    public static double Lo; // longitude

    // 학교 중앙 좌표
    // 37.321229, 127.127432
    public static double DKULa = 37.321229;
    public static double DKULo = 127.127432;

    private Button dkuButton, mylocButton;

    // Navigation
    private Marker destinationMarker;
    private Point originPosition;
    private Point destinatonPosition;

    // onCreate가 있어야 지도가 뜸
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(getActivity(), getString(R.string.mapbox_access_token));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigation_fragment, container, false);
        mapView = view.findViewById(R.id.mapView);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            private MapboxMap mapboxMap;
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.addOnMapClickListener(NavigationFragment.this);
                mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/gouz7514/cke8d56tw4y5v19jv8ecm5l7v"), new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
//                        enableLocationComponent(style);
                    }
                });

                dkuButton = view.findViewById(R.id.btnDKU);
                dkuButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e(TAG, "dkuButton clicked");
                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(DKULa, DKULo)) // Sets the new camera position
                                .zoom(16) // Sets the zoom , 줌 정도 숫자가 클수록 더많이 줌함
                                .bearing(180) // Rotate the camera , 카메라 방향(북쪽이 0) 북쪽부터 시계방향으로 측정
                                .tilt(0) // Set the camera tilt , 각도
                                .build(); // Creates a CameraPosition from the builder
                        Log.e(TAG, "DKU position : " + position);

                        // TODO : Attempt to invoke virtual method 해결해야 함
                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 7000);
                        Toast.makeText(getApplicationContext(), String.format("단국대 위치로 이동합니다"), Toast.LENGTH_LONG).show();
                    }
                });

                mylocButton = view.findViewById(R.id.btnMyLoc);
                mylocButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(La, Lo)) // Sets the new camera position
                                .zoom(17) // Sets the zoom , 줌 정도 숫자가 클수록 더많이 줌함
                                .bearing(180) // Rotate the camera , 카메라 방향(북쪽이 0) 북쪽부터 시계방향으로 측정
                                .tilt(0) // Set the camera tilt , 각도
                                .build(); // Creates a CameraPosition from the builder
                        //카메라 움직이기
                        mapboxMap.animateCamera(CameraUpdateFactory
                                .newCameraPosition(position), 7000);

                        // TODO : 내위치 버튼 클릭하면 위도, 경도 대신 실제 주소 띄워보기
                        Toast.makeText(getApplicationContext(), String.format("내 위치로 이동합니다."), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(getApplicationContext(), R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
//            enableLocationComponent(mapboxMap.getStyle());
        } else {
            Toast.makeText(getApplicationContext(), R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        return false;
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
