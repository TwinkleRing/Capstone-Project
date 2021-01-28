# 단국대 학생들을 위한 통합 어플리케이션 프로젝트(알단지)

## 프로젝트명
**알단지(알람 + 단국대 + 지도)**<br>
[유튜브에서 확인하기](https://www.youtube.com/watch?v=RQQwxN8pxB0)<br>
[![알단지 - 유튜브](https://img.youtube.com/vi/RQQwxN8pxB0/0.jpg)](https://www.youtube.com/watch?v=RQQwxN8pxB0)
<br>

## 프로젝트 소개
**단국대 학생들을 위한 통합 어플리케이션**<br>
[발표 ppt로 전체 내용 확인하기](./document/2조_3KM_2020년_2학기_발표_PPT_최종.pdf)
<br>

## 기획 의도
단국대 학생들이 주로 사용하는 어플리케이션의 부족한 점을 분석, 보완한 새로운 어플리케이션의 구현
- 알람 기능 : 많은 대학생들이 사용하는 '에브리타임' 어플의 경우 강의 별 알람은 제공 X
- 캠퍼스 맵 기능 : 단국대 공식 어플은 캠퍼스 맵 기능을 제공하지 않으며, 매번 단국대 홈페이지를 들어가야 하는 번거로움
이 기획의도들의 달성을 통해 단국대 학생들의 쾌적한 캠퍼스 라이프를 목표로 함
<br>

## 세부 내용
### 기술 스택 및 개발 환경
![기술 스택](https://user-images.githubusercontent.com/41367134/106094351-30a32c00-6175-11eb-9c3e-234d614c9d2d.PNG)

### 시간표 기능
- 실제 단국대 종합시간표(2020년 2학기)를 크롤링 해 DB 내의 데이터 검색 기능을 수행
- 키워드만 검색해도 검색 내용이 나오도록 설정([FindClassActivity.java](./app/src/main/java/com/example/capstone_ui_1/Service/FindClassActivity.java))
```java
autoCompleteTextView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String s) {
        query = databaseReference.orderByChild("classname").startAt(s).endAt(s + "\uf8ff");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
```

### 알람 기능
- 알람 데이터를 저장할 데이터베이스 필요(SQLite)
- 안드로이드 지원 라이브러리인 NotificationCompat.Builder 객체를 사용해 푸쉬 알람 생성([AlarmReceiver.java](./app/src/main/java/com/example/capstone_ui_1/Alarm/AlarmReceiver.java))
```java
NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
builder.setSmallIcon(R.drawable.alarm_clock3).setContentTitle("알단지").setContentText("강의가 곧 시작합니다.")
    .setWhen(Calendar.getInstance().getTimeInMillis())
    .setContentIntent(pendingIntent).setAutoCancel(true);

notificationManager.notify(0, builder.build());
```
- 백그라운드 실행을 위해 Service 객체 사용([MyService.java](./app/src/main/java/com/example/capstone_ui_1/Service/MyService.java))
```java
AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendarTime, sender[count]);
```
- BroadcastReceiver를 통한 푸쉬 전송 ([AlarmReceiver.java](./app/src/main/java/com/example/capstone_ui_1/Alarm/AlarmReceiver.java))

### 네비게이션 기능
- Mapbox Studio를 활용한 교내 지도 제작
- Directions API를 활용한 경로 및 정보 제공([NavigationFragment.java](./app/src/main/java/com/example/capstone_ui_1/NavigationFragment.java))
```java
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
```
- OpenStreetMap을 활용해 실제 보행 가능 경로 추가
- Directions.prefab을 활용해 AR 경로 제공

### 최종 화면
![최종 화면 1](https://user-images.githubusercontent.com/41367134/106094487-75c75e00-6175-11eb-9040-2879be4b942d.PNG)
![최종 화면 2](https://user-images.githubusercontent.com/41367134/106094500-7bbd3f00-6175-11eb-95b8-e613789eff12.PNG)
<br>

## 결론
- 실제 사용 중인 서비스의 부족한 점을 분석하고 구현
- 구현 과정에 있어 다양한 기술 스택의 사용
- 오픈 소스 기여를 통해 프로젝트를 구현
- 실제 기업과의 소통을 통해 문제점에 대한 해결책을 찾아냄
- 구현 과정에 있어 발생했던 문제점들에 대해 협의를 통한 해결책 모색
- 초기 목표로 했던 바 모두 달성
<br>

## 참고 문헌
- [Mapbox Tutorial](https://docs.mapbox.com/help/tutorials/)
- [Mapbox Documentation](https://docs.mapbox.com/)
- [Android Developer Guide](https://developer.android.com/guide?hl=ko)
- Mapbox와 주고받은 메일