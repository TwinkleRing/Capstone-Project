package com.example.capstone_ui_1;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Camera;
import android.location.Location;
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
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Looper.getMainLooper;
import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class NavigationFragment extends Fragment implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener {
    // variables for adding location layer
    private MapView mapView;
    private MapboxMap mapboxMap;

    // variables for adding location layer
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;

    // variables for calculating and drawing a route
    private DirectionsRoute currentRoute;

    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;

    // Variables needed to add the location engine
    private LocationEngine locationEngine;
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;

    // variable needed to listen to location update
    private LocationCallback callback = new LocationCallback(this);

    double destinationLa; // latitude 목적지 위도
    double destinationLo; // longitude 목적지 경도
    public static double La; //latitude
    public static double Lo; // longitude

    // 학교 중앙 좌표
    // 37.321229, 127.127432
    public static double DKULa = 37.321229;
    public static double DKULo = 127.127432;

    // Navigation & Button
    private MapboxDirections client;
    private Button dkuButton, mylocButton, startButton, arButton, areaButton;

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
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) { ;
                NavigationFragment.this.mapboxMap = mapboxMap;
                mapboxMap.addOnMapClickListener(NavigationFragment.this);
                mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/gouz7514/cke8d56tw4y5v19jv8ecm5l7v"), new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);
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

                        Toast.makeText(getApplicationContext(), String.format("내 위치로 이동합니다."), Toast.LENGTH_LONG).show();
                    }
                });

                startButton = view.findViewById(R.id.btnStartNavigation);
                startButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e(TAG, "startButton clicked");
                        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                .directionsRoute(currentRoute)
                                .build();
                        // Call this method with Context from within an Activity
                        NavigationLauncher.startNavigation(getActivity(), options);
                    }
                });

                // TODO : arButton onCLick, setEnable
                arButton = view.findViewById(R.id.btnStartAR);

                // TODO : areaButton : 학교 구역별 보기, 코드 간략화해보기
                areaButton = view.findViewById(R.id.btnArea);
                areaButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 각 구역별 좌표 저장
                        // 공대, 인문대
                        double[][] latlngs = {{37.320754, 127.126254}, {37.322298, 127.129150}};
                        CameraPosition position0 = new CameraPosition.Builder()
                                                                .target(new LatLng(latlngs[0][0], latlngs[0][1]))
                                                                .zoom(17)
                                                                .bearing(180)
                                                                .tilt(0)
                                                                .build();
                        CameraPosition position1 = new CameraPosition.Builder()
                                                                .target(new LatLng(latlngs[1][0], latlngs[1][1]))
                                                                .zoom(17)
                                                                .bearing(180)
                                                                .tilt(0)
                                                                .build();

                        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                        dialog.setTitle("어디로 가시나요?")
                                .setItems(new CharSequence[]{"공대", "인문대"}, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                mapboxMap.animateCamera(CameraUpdateFactory
                                                        .newCameraPosition(position0), 5000);
                                                break;
                                            case 1:
                                                mapboxMap.animateCamera(CameraUpdateFactory
                                                        .newCameraPosition(position1), 5000);
                                                break;
                                        }
                                    }
                                })
                                .setNegativeButton("그냥 지도로 볼래요", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        dialog.show();
                    }
                });

            }
        });
        return view;
    }

    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        Log.e(TAG,"initLocationEngine 실행");
        locationEngine = LocationEngineProvider.getBestLocationEngine(getApplicationContext());
        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();
        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);
    }

    private static class LocationCallback implements LocationEngineCallback<LocationEngineResult> {
        private final WeakReference<NavigationFragment> activityWeakReference;
        public LocationCallback(NavigationFragment navigationFragment) {
            this.activityWeakReference = new WeakReference<>(navigationFragment);
        }

        @Override
        public void onSuccess(LocationEngineResult result) {
            Log.e(TAG,"LocationCallback onSuccess 실행");
            NavigationFragment activity = activityWeakReference.get();
            if (activity != null) {
                Location location = result.getLastLocation();
                if (location == null) {
                    return;
                }
                // Create a Toast which displays the new location's coordinates
                La = result.getLastLocation().getLatitude();
                Lo = result.getLastLocation().getLongitude();

                // Pass the new location to the Maps SDK's LocationComponent
                if (activity.mapboxMap != null && result.getLastLocation() != null) {
                    activity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
                }
            }
        }

        @Override
        public void onFailure(@NonNull Exception exception) {
            Log.e(TAG,"LocationCallback onFailure 실행");
            NavigationFragment activity = activityWeakReference.get();
            if (activity != null) {
                Toast.makeText(getApplicationContext(), exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

        }
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
            enableLocationComponent(mapboxMap.getStyle());
        } else {
            Toast.makeText(getActivity(), R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            getActivity().finish();
        }
    }

    private void getRoute_walking(Point origin, Point destination) {
        Log.e(TAG,"getRoute_walking 실행");
        client = MapboxDirections.builder()
                .origin(origin)//출발지 위도 경도
                .destination(destination)//도착지 위도 경도
                .overview(DirectionsCriteria.OVERVIEW_FULL)//정보 받는정도 최대
                .profile(DirectionsCriteria.PROFILE_WALKING)//길찾기 방법(도보,자전거,자동차)
                .accessToken(getString(R.string.mapbox_access_token))
                .build();

        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                Log.e(TAG,"onResponse 실행");
                System.out.println(call.request().url().toString());
                // You can get the generic HTTP info about the response
                Log.e(TAG, "Response code: " + response.code());
                if (response.body() == null) {
                    Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Log.e(TAG, "No routes found");
                    return;
                }
                // Print some info about the route
                currentRoute = response.body().routes().get(0);
                Log.e(TAG, "Distance: " + currentRoute.distance());

                int time = (int) (currentRoute.duration()/60);
                //예상 시간을초단위로 받아옴
                double distances = (currentRoute.distance()/1000);

                //목적지까지의 거리를 m로 받아옴
                distances = Math.round(distances*100)/100.0;
                //Math.round() 함수는 소수점 첫째자리에서 반올림하여 정수로 남긴다
                //원래 수에 100곱하고 round 실행 후 다시 100으로 나눈다 -> 둘째자리까지 남김

                Toast.makeText(getApplicationContext(), String.format("예상 시간 : " + String.valueOf(time)+" 분 \n" +
                        "목적지 거리 : " +distances+ " km"), Toast.LENGTH_LONG).show();
                // Draw the route on the map
                // drawRoute(currentRoute);
            }
            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                Log.e(TAG, "Error: " + throwable.getMessage());
                Toast.makeText(getContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRoute_navi_walking (Point origin, Point destinaton) {
        // https://docs.mapbox.com/android/navigation/overview/map-matching/
        NavigationRoute.builder(getActivity()).accessToken(Mapbox.getAccessToken())
                .profile(DirectionsCriteria.PROFILE_WALKING)//도보 길찾기
                .origin(origin)//출발지
                .destination(destinaton).//도착지
                build().
                getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        if (response.body() == null) {
                            return;
                        } else if (response.body().routes().size() ==0) {
                            return;
                        }
                        currentRoute = response.body().routes().get(0);
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }
                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                    }
                });
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(getActivity())) {
            Log.e(TAG, "enableLocationComponent Success");
            // Get an instance of the LocationComponent.
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(getActivity(), loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
            initLocationEngine();
        } else {
            Log.e(TAG, "enableLocationComponent Faliure");
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }


    @Override
    //지도 클릭시 자동 길찾기
    public boolean onMapClick(@NonNull LatLng point) {

        if (destinationMarker != null) {
            mapboxMap.removeMarker(destinationMarker);
        }
        destinationMarker = mapboxMap.addMarker(new MarkerOptions().position(point));//마커 추가
        destinatonPosition = Point.fromLngLat(point.getLongitude(), point.getLatitude());//클릭한곳의 좌표
        Log.e(TAG, "destinationPosition : " + destinatonPosition);
        originPosition = Point.fromLngLat(Lo, La);//현재 좌표
        getRoute_walking(originPosition, destinatonPosition);
        getRoute_navi_walking(originPosition, destinatonPosition);
        startButton.setEnabled(true);   //네비게이션 버튼 활성화
        startButton.setBackgroundResource(R.color.mapboxBlue);
//        arButton.setEnabled(true);
//        arButton.setBackgroundResource(R.color.mapboxBlue);
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
