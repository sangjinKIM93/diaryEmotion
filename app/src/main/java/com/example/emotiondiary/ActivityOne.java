package com.example.emotiondiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emotiondiary.ListVO.MemoData;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivityOne extends AppCompatActivity implements View.OnClickListener {

    private PieChart pieChart, pieChartBig;
    private String userId;

    private BarChart stackedChart;

    private Button btn1, btn2, btn3;
    private ImageView chart_replace;

    DatabaseReference db;
    List<MemoData> datas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        btn1 = findViewById(R.id.statisticBtn1);
        btn2 = findViewById(R.id.statisticBtn2);
        btn3 = findViewById(R.id.statisticBtn3);
        chart_replace = findViewById(R.id.chart_replacement);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        userId = sp.getString("checkId", " ");

        //네비게이션 바 클릭시 이벤트 처리
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_dashboard);    //선택된 메뉴 색깔 바꾸기
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(ActivityOne.this, MemoListActivity.class);
                        startActivity(a);
                        finish();
                        break;

                    case R.id.navigation_dashboard:
                        break;

                    case R.id.navigation_notifications:
                        Intent b = new Intent(ActivityOne.this, ActivityTwo.class);
                        startActivity(b);
                        finish();
                        break;
                }

                return false;
            }
        });

        //감정 통계 내기

        db = FirebaseDatabase.getInstance().getReference();
        datas = new ArrayList<>();

        db.child("memodata").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataR : dataSnapshot.getChildren()){
                    MemoData memoData = dataR.getValue(MemoData.class);
                    if(memoData.getUser().equals(userId)) {
                        datas.add(memoData);
                        //일단 잘 받아오는지 확인하자.
                        String[] emotions = new String[]{
                                "분노", "툴툴대는", "짜증내는", "좌절한", "방어적인", "노여워하는", "성가신",
                                "슬픈", "실망한", "후회되는", "염세적인", "우울한", "눈물이 나는", "낭패한",
                                "불안", "두려운", "헷갈리는", "스트레스", "걱정스러운", "조심스러운", "신경쓰이는",
                                "상처", "질투하는", "버려진", "배신당한", "충격받은", "희생된", "억울한",
                                "당황", "격리된", "열등한", "시선 의식하는", "죄책감의", "부끄러운", "한심한",
                                "기쁜", "감사하는", "만족한", "편안한", "신이 난", "안도하는", "자신하는"
                        };

                        //소분류 통계
                        int[] num = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

                        int[] sun = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                        int[] mon = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                        int[] tue = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                        int[] wed = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                        int[] thur = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                        int[] fri = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                        int[] sat = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


                        for (MemoData data : datas) {

                            //날짜 뽑기
                            String time = data.getTime();
                            String dateSelected = " ";
                            try {
                                dateSelected = getDateDay(time);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            for (int i = 0; i < emotions.length; i++) {
                                String pre_emotion = emotions[i];

                                //소분류 통계 카운팅
                                if (pre_emotion.equals(data.getTv_emotion())) {
                                    num[i] += 1;
                                    //여기다 날짜 조건을 걸어주면 날짜별 카운팅
                                    if(dateSelected.equals("일")){
                                        sun[i] += 1;
                                    } else if(dateSelected.equals("월")){
                                        mon[i] += 1;
                                    } else if(dateSelected.equals("화")){
                                        tue[i] += 1;
                                    } else if(dateSelected.equals("수")){
                                        wed[i] += 1;
                                    } else if(dateSelected.equals("목")){
                                        thur[i] += 1;
                                    } else if(dateSelected.equals("금")){
                                        fri[i] += 1;
                                    } else if(dateSelected.equals("토")){
                                        sat[i] += 1;
                                    }

                                }
                            }
                        }

                        //대분류 통계
                        int angryNum = 0;
                        int sadNum = 0;
                        int anxiousNum = 0;
                        int hurtNum = 0;
                        int embarrassedNum = 0;
                        int happyNum = 0;
                        for (int k = 0; k < num.length; k++) {
                            if (k < 7) {
                                angryNum += num[k];
                            } else if (k >= 7 && k < 14) {
                                sadNum += num[k];
                            } else if (k >= 14 && k < 21) {
                                anxiousNum += num[k];
                            } else if (k >= 21 && k < 28) {
                                hurtNum += num[k];
                            } else if (k >= 28 && k < 35) {
                                embarrassedNum += num[k];
                            } else if (k >= 35 && k < 42) {
                                happyNum += num[k];
                            }
                        }

                        //날짜별 통계
                        float [] sunday = new float[]{0,0,0,0,0,0};
                        for (int k = 0; k < num.length; k++) {
                            if (k < 7) {
                                sunday[0] += sun[k];
                            } else if (k >= 7 && k < 14) {
                                sunday[1] += sun[k];
                            } else if (k >= 14 && k < 21) {
                                sunday[2] += sun[k];
                            } else if (k >= 21 && k < 28) {
                                sunday[3] += sun[k];
                            } else if (k >= 28 && k < 35) {
                                sunday[4] += sun[k];
                            } else if (k >= 35 && k < 42) {
                                sunday[5] += sun[k];
                            }
                        }

                        float [] monday = new float[]{0,0,0,0,0,0};
                        for (int k = 0; k < num.length; k++) {
                            if (k < 7) {
                                monday[0] += mon[k];
                            } else if (k >= 7 && k < 14) {
                                monday[1] += mon[k];
                            } else if (k >= 14 && k < 21) {
                                monday[2] += mon[k];
                            } else if (k >= 21 && k < 28) {
                                monday[3] += mon[k];
                            } else if (k >= 28 && k < 35) {
                                monday[4] += mon[k];
                            } else if (k >= 35 && k < 42) {
                                monday[5] += mon[k];
                            }
                        }

                        float [] tuesday = new float[]{0,0,0,0,0,0};
                        for (int k = 0; k < num.length; k++) {
                            if (k < 7) {
                                tuesday[0] += tue[k];
                            } else if (k >= 7 && k < 14) {
                                tuesday[1] += tue[k];
                            } else if (k >= 14 && k < 21) {
                                tuesday[2] += tue[k];
                            } else if (k >= 21 && k < 28) {
                                tuesday[3] += tue[k];
                            } else if (k >= 28 && k < 35) {
                                tuesday[4] += tue[k];
                            } else if (k >= 35 && k < 42) {
                                tuesday[5] += tue[k];
                            }
                        }

                        float [] wednesdy = new float[]{0,0,0,0,0,0};
                        for (int k = 0; k < num.length; k++) {
                            if (k < 7) {
                                wednesdy[0] += wed[k];
                            } else if (k >= 7 && k < 14) {
                                wednesdy[1] += wed[k];
                            } else if (k >= 14 && k < 21) {
                                wednesdy[2] += wed[k];
                            } else if (k >= 21 && k < 28) {
                                wednesdy[3] += wed[k];
                            } else if (k >= 28 && k < 35) {
                                wednesdy[4] += wed[k];
                            } else if (k >= 35 && k < 42) {
                                wednesdy[5] += wed[k];
                            }
                        }

                        float [] thursday = new float[]{0,0,0,0,0,0};
                        for (int k = 0; k < num.length; k++) {
                            if (k < 7) {
                                thursday[0] += thur[k];
                            } else if (k >= 7 && k < 14) {
                                thursday[1] += thur[k];
                            } else if (k >= 14 && k < 21) {
                                thursday[2] += thur[k];
                            } else if (k >= 21 && k < 28) {
                                thursday[3] += thur[k];
                            } else if (k >= 28 && k < 35) {
                                thursday[4] += thur[k];
                            } else if (k >= 35 && k < 42) {
                                thursday[5] += thur[k];
                            }
                        }

                        float [] friday = new float[]{0,0,0,0,0,0};
                        for (int k = 0; k < num.length; k++) {
                            if (k < 7) {
                                friday[0] += fri[k];
                            } else if (k >= 7 && k < 14) {
                                friday[1] += fri[k];
                            } else if (k >= 14 && k < 21) {
                                friday[2] += fri[k];
                            } else if (k >= 21 && k < 28) {
                                friday[3] += fri[k];
                            } else if (k >= 28 && k < 35) {
                                friday[4] += fri[k];
                            } else if (k >= 35 && k < 42) {
                                friday[5] += fri[k];
                            }
                        }

                        float [] saturday = new float[]{0,0,0,0,0,0};
                        for (int k = 0; k < num.length; k++) {
                            if (k < 7) {
                                saturday[0] += sat[k];
                            } else if (k >= 7 && k < 14) {
                                saturday[1] += sat[k];
                            } else if (k >= 14 && k < 21) {
                                saturday[2] += sat[k];
                            } else if (k >= 21 && k < 28) {
                                saturday[3] += sat[k];
                            } else if (k >= 28 && k < 35) {
                                saturday[4] += sat[k];
                            } else if (k >= 35 && k < 42) {
                                saturday[5] += sat[k];
                            }
                        }

                        //pieChart(소분류)
                        pieChart = (PieChart) findViewById(R.id.pieChart);

                        pieChart.setUsePercentValues(true);
                        pieChart.getDescription().setEnabled(false);
                        pieChart.setExtraOffsets(5, 10, 5, 5);

                        pieChart.setDragDecelerationFrictionCoef(0.95F);

                        pieChart.setDrawHoleEnabled(false);
                        pieChart.setHoleColor(Color.WHITE);
                        pieChart.setTransparentCircleRadius(61F);

                        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

                        for (int j = 0; j < emotions.length; j++) {
                            if (num[j] > 0) {
                                yValues.add(new PieEntry(num[j], emotions[j]));
                            }
                        }

                        Description description = new Description();
                        description.setText("Small Category");
                        description.setTextSize(15);
                        pieChart.setDescription(description);

                        //pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

                        PieDataSet dataSet = new PieDataSet(yValues, "emotion");
                        dataSet.setSliceSpace(2f);
                        dataSet.setSelectionShift(3f);
                        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

                        PieData data = new PieData((dataSet));
                        data.setValueTextSize(10f);
                        data.setValueTextColor(Color.YELLOW);


                        pieChart.setData(data);

                        //barChart
                        int[] colorClassArray = new int[]{Color.RED, Color.CYAN, Color.GREEN, Color.BLUE, Color.GRAY, Color.MAGENTA};

                        ArrayList<BarEntry> dataVals = new ArrayList<>();
                        dataVals.add(new BarEntry(0,monday));
                        dataVals.add(new BarEntry(1,tuesday));
                        dataVals.add(new BarEntry(2,wednesdy));
                        dataVals.add(new BarEntry(3,thursday));
                        dataVals.add(new BarEntry(4,friday));
                        dataVals.add(new BarEntry(5,saturday));
                        dataVals.add(new BarEntry(6,sunday));

                        stackedChart = findViewById(R.id.stacked_barchart);
                        stackedChart.setDrawGridBackground(false);

                        BarDataSet barDataSet = new BarDataSet(dataVals, "Emotions");
                        barDataSet.setColors(colorClassArray);
                        barDataSet.setStackLabels(new String[]{"분노", "슬픔", "불안", "상처", "당황", "기쁨"});

                        // x축 설정
                        final List list_x_axis_name = new ArrayList<>();
                        list_x_axis_name.add("월");
                        list_x_axis_name.add("화");
                        list_x_axis_name.add("수");
                        list_x_axis_name.add("목");
                        list_x_axis_name.add("금");
                        list_x_axis_name.add("토");
                        list_x_axis_name.add("일");

                        YAxis yAxis = stackedChart.getAxisLeft();
                        yAxis.setAxisMinimum(0);
                        yAxis.setAxisMaximum(10);

                        YAxis yAxisR = stackedChart.getAxisRight();
                        yAxisR.setDrawLabels(false); // no axis labels
                        yAxisR.setDrawGridLines(false);

                        XAxis xAxis = stackedChart.getXAxis();
                        xAxis.setGranularity(1f);
                        //xAxis.setCenterAxisLabels(true);
                        //xAxis.setLabelRotationAngle(-90);
                        xAxis.setValueFormatter(new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                if(value >= 0){
                                    if(value <= list_x_axis_name.size() -1 ){
                                        return (String) list_x_axis_name.get((int)value);
                                    }
                                    return "";
                                }
                                return "";
                            }
                        });

                        BarData barData = new BarData(barDataSet);
                        stackedChart.setData(barData);


                        //pieChart(대분류)
                        pieChartBig = (PieChart) findViewById(R.id.bigPieChart);

                        pieChartBig.setUsePercentValues(true);
                        pieChartBig.getDescription().setEnabled(false);
                        pieChartBig.setExtraOffsets(5, 10, 5, 5);

                        pieChartBig.setDragDecelerationFrictionCoef(0.95F);

                        pieChartBig.setDrawHoleEnabled(false);
                        pieChartBig.setHoleColor(Color.WHITE);
                        pieChartBig.setTransparentCircleRadius(61F);

                        ArrayList<PieEntry> yValuesBig = new ArrayList<PieEntry>();

                        if (angryNum > 0) {
                            yValuesBig.add(new PieEntry(angryNum, "분노"));
                        }
                        if (sadNum > 0) {
                            yValuesBig.add(new PieEntry(sadNum, "슬픔"));
                        }
                        if (anxiousNum > 0) {
                            yValuesBig.add(new PieEntry(anxiousNum, "불안"));
                        }
                        if (embarrassedNum > 0) {
                            yValuesBig.add(new PieEntry(embarrassedNum, "당황"));
                        }
                        if (hurtNum > 0) {
                            yValuesBig.add(new PieEntry(hurtNum, "상처"));
                        }
                        if (happyNum > 0) {
                            yValuesBig.add(new PieEntry(happyNum, "기쁨"));
                        }

                        Description descriptionBig = new Description();
                        descriptionBig.setText("Big Category");
                        descriptionBig.setTextSize(15);
                        pieChartBig.setDescription(descriptionBig);

                        //pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

                        PieDataSet dataSetBig = new PieDataSet(yValuesBig, "emotion");
                        dataSetBig.setSliceSpace(2f);
                        dataSetBig.setSelectionShift(3f);
                        dataSetBig.setColors(ColorTemplate.PASTEL_COLORS);

                        PieData dataBig = new PieData((dataSetBig));
                        dataBig.setValueTextSize(10f);
                        dataBig.setValueTextColor(Color.YELLOW);


                        pieChartBig.setData(dataBig);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        String[] emotions = new String[]{
//                "분노", "툴툴대는", "짜증내는", "좌절한", "방어적인", "노여워하는", "성가신",
//                "슬픈", "실망한", "후회되는", "염세적인", "우울한", "눈물이 나는", "낭패한",
//                "불안", "두려운", "헷갈리는", "스트레스", "걱정스러운", "조심스러운", "신경쓰이는",
//                "상처", "질투하는", "버려진", "배신당한", "충격받은", "희생된", "억울한",
//                "당황", "격리된", "열등한", "시선 의식하는", "죄책감의", "부끄러운", "한심한",
//                "기쁜", "감사하는", "만족한", "편안한", "신이 난", "안도하는", "자신하는"
//        };
//
//        //소분류 통계
//        int[] num = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//
//        int[] sun = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        int[] mon = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        int[] tue = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        int[] wed = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        int[] thur = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        int[] fri = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        int[] sat = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//
//
//        for (MemoData data : datas) {
//
//            //날짜 뽑기
//            String time = data.getTime();
//            String dateSelected = " ";
//            try {
//                dateSelected = getDateDay(time);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            for (int i = 0; i < emotions.length; i++) {
//                String pre_emotion = emotions[i];
//
//                //소분류 통계 카운팅
//                if (pre_emotion.equals(data.getTv_emotion())) {
//                    num[i] += 1;
//                    //여기다 날짜 조건을 걸어주면 날짜별 카운팅
//                    if(dateSelected.equals("일")){
//                        sun[i] += 1;
//                    } else if(dateSelected.equals("월")){
//                        mon[i] += 1;
//                    } else if(dateSelected.equals("화")){
//                        tue[i] += 1;
//                    } else if(dateSelected.equals("수")){
//                        wed[i] += 1;
//                    } else if(dateSelected.equals("목")){
//                        thur[i] += 1;
//                    } else if(dateSelected.equals("금")){
//                        fri[i] += 1;
//                    } else if(dateSelected.equals("토")){
//                        sat[i] += 1;
//                    }
//
//                }
//            }
//        }
//
//        //대분류 통계
//        int angryNum = 0;
//        int sadNum = 0;
//        int anxiousNum = 0;
//        int hurtNum = 0;
//        int embarrassedNum = 0;
//        int happyNum = 0;
//        for (int k = 0; k < num.length; k++) {
//            if (k < 7) {
//                angryNum += num[k];
//            } else if (k >= 7 && k < 14) {
//                sadNum += num[k];
//            } else if (k >= 14 && k < 21) {
//                anxiousNum += num[k];
//            } else if (k >= 21 && k < 28) {
//                hurtNum += num[k];
//            } else if (k >= 28 && k < 35) {
//                embarrassedNum += num[k];
//            } else if (k >= 35 && k < 42) {
//                happyNum += num[k];
//            }
//        }
//
//        //날짜별 통계
//        float [] sunday = new float[]{0,0,0,0,0,0};
//        for (int k = 0; k < num.length; k++) {
//            if (k < 7) {
//                sunday[0] += sun[k];
//            } else if (k >= 7 && k < 14) {
//                sunday[1] += sun[k];
//            } else if (k >= 14 && k < 21) {
//                sunday[2] += sun[k];
//            } else if (k >= 21 && k < 28) {
//                sunday[3] += sun[k];
//            } else if (k >= 28 && k < 35) {
//                sunday[4] += sun[k];
//            } else if (k >= 35 && k < 42) {
//                sunday[5] += sun[k];
//            }
//        }
//
//        float [] monday = new float[]{0,0,0,0,0,0};
//        for (int k = 0; k < num.length; k++) {
//            if (k < 7) {
//                monday[0] += mon[k];
//            } else if (k >= 7 && k < 14) {
//                monday[1] += mon[k];
//            } else if (k >= 14 && k < 21) {
//                monday[2] += mon[k];
//            } else if (k >= 21 && k < 28) {
//                monday[3] += mon[k];
//            } else if (k >= 28 && k < 35) {
//                monday[4] += mon[k];
//            } else if (k >= 35 && k < 42) {
//                monday[5] += mon[k];
//            }
//        }
//
//        float [] tuesday = new float[]{0,0,0,0,0,0};
//        for (int k = 0; k < num.length; k++) {
//            if (k < 7) {
//                tuesday[0] += tue[k];
//            } else if (k >= 7 && k < 14) {
//                tuesday[1] += tue[k];
//            } else if (k >= 14 && k < 21) {
//                tuesday[2] += tue[k];
//            } else if (k >= 21 && k < 28) {
//                tuesday[3] += tue[k];
//            } else if (k >= 28 && k < 35) {
//                tuesday[4] += tue[k];
//            } else if (k >= 35 && k < 42) {
//                tuesday[5] += tue[k];
//            }
//        }
//
//        float [] wednesdy = new float[]{0,0,0,0,0,0};
//        for (int k = 0; k < num.length; k++) {
//            if (k < 7) {
//                wednesdy[0] += wed[k];
//            } else if (k >= 7 && k < 14) {
//                wednesdy[1] += wed[k];
//            } else if (k >= 14 && k < 21) {
//                wednesdy[2] += wed[k];
//            } else if (k >= 21 && k < 28) {
//                wednesdy[3] += wed[k];
//            } else if (k >= 28 && k < 35) {
//                wednesdy[4] += wed[k];
//            } else if (k >= 35 && k < 42) {
//                wednesdy[5] += wed[k];
//            }
//        }
//
//        float [] thursday = new float[]{0,0,0,0,0,0};
//        for (int k = 0; k < num.length; k++) {
//            if (k < 7) {
//                thursday[0] += thur[k];
//            } else if (k >= 7 && k < 14) {
//                thursday[1] += thur[k];
//            } else if (k >= 14 && k < 21) {
//                thursday[2] += thur[k];
//            } else if (k >= 21 && k < 28) {
//                thursday[3] += thur[k];
//            } else if (k >= 28 && k < 35) {
//                thursday[4] += thur[k];
//            } else if (k >= 35 && k < 42) {
//                thursday[5] += thur[k];
//            }
//        }
//
//        float [] friday = new float[]{0,0,0,0,0,0};
//        for (int k = 0; k < num.length; k++) {
//            if (k < 7) {
//                friday[0] += fri[k];
//            } else if (k >= 7 && k < 14) {
//                friday[1] += fri[k];
//            } else if (k >= 14 && k < 21) {
//                friday[2] += fri[k];
//            } else if (k >= 21 && k < 28) {
//                friday[3] += fri[k];
//            } else if (k >= 28 && k < 35) {
//                friday[4] += fri[k];
//            } else if (k >= 35 && k < 42) {
//                friday[5] += fri[k];
//            }
//        }
//
//        float [] saturday = new float[]{0,0,0,0,0,0};
//        for (int k = 0; k < num.length; k++) {
//            if (k < 7) {
//                saturday[0] += sat[k];
//            } else if (k >= 7 && k < 14) {
//                saturday[1] += sat[k];
//            } else if (k >= 14 && k < 21) {
//                saturday[2] += sat[k];
//            } else if (k >= 21 && k < 28) {
//                saturday[3] += sat[k];
//            } else if (k >= 28 && k < 35) {
//                saturday[4] += sat[k];
//            } else if (k >= 35 && k < 42) {
//                saturday[5] += sat[k];
//            }
//        }
//
//        for(int i = 0; i<friday.length; i++) {
//            Log.d("satuday", String.valueOf(saturday[i]));
//        }
//
//        //barChart
//        int[] colorClassArray = new int[]{Color.RED, Color.CYAN, Color.GREEN, Color.BLUE, Color.GRAY, Color.MAGENTA};
//
//        ArrayList<BarEntry> dataVals = new ArrayList<>();
//        dataVals.add(new BarEntry(0,monday));
//        dataVals.add(new BarEntry(1,tuesday));
//        dataVals.add(new BarEntry(2,wednesdy));
//        dataVals.add(new BarEntry(3,thursday));
//        dataVals.add(new BarEntry(4,friday));
//        dataVals.add(new BarEntry(5,saturday));
//        dataVals.add(new BarEntry(6,sunday));
//
//        stackedChart = findViewById(R.id.stacked_barchart);
//        stackedChart.setDrawGridBackground(false);
//
//        BarDataSet barDataSet = new BarDataSet(dataVals, "Emotions");
//        barDataSet.setColors(colorClassArray);
//        barDataSet.setStackLabels(new String[]{"분노", "슬픔", "불안", "상처", "당황", "기쁨"});
//
//        // x축 설정
//        final List list_x_axis_name = new ArrayList<>();
//        list_x_axis_name.add("월");
//        list_x_axis_name.add("화");
//        list_x_axis_name.add("수");
//        list_x_axis_name.add("목");
//        list_x_axis_name.add("금");
//        list_x_axis_name.add("토");
//        list_x_axis_name.add("일");
//
//        YAxis yAxis = stackedChart.getAxisLeft();
//        yAxis.setAxisMinimum(0);
//        yAxis.setAxisMaximum(5);
//
//        XAxis xAxis = stackedChart.getXAxis();
//        xAxis.setGranularity(1f);
//
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                if(value >= 0){
//                    if(value <= list_x_axis_name.size() -1 ){
//                        return (String) list_x_axis_name.get((int)value);
//                    }
//                    return "";
//                }
//                return "";
//            }
//        });
//
//        BarData barData = new BarData(barDataSet);
//        stackedChart.setData(barData);
//
//
//        //pieChart(소분류)
//        pieChart = (PieChart) findViewById(R.id.pieChart);
//
//        pieChart.setUsePercentValues(true);
//        pieChart.getDescription().setEnabled(false);
//        pieChart.setExtraOffsets(5, 10, 5, 5);
//
//        pieChart.setDragDecelerationFrictionCoef(0.95F);
//
//        pieChart.setDrawHoleEnabled(false);
//        pieChart.setHoleColor(Color.WHITE);
//        pieChart.setTransparentCircleRadius(61F);
//
//        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();
//
//        for (int j = 0; j < emotions.length; j++) {
//            if (num[j] > 0) {
//                yValues.add(new PieEntry(num[j], emotions[j]));
//            }
//        }
//
//        Description description = new Description();
//        description.setText("Small Category");
//        description.setTextSize(15);
//        pieChart.setDescription(description);
//
//        //pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
//
//        PieDataSet dataSet = new PieDataSet(yValues, "emotion");
//        dataSet.setSliceSpace(2f);
//        dataSet.setSelectionShift(3f);
//        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
//
//        PieData data = new PieData((dataSet));
//        data.setValueTextSize(10f);
//        data.setValueTextColor(Color.YELLOW);
//
//
//        pieChart.setData(data);
//
//
//        //pieChart(대분류)
//        pieChartBig = (PieChart) findViewById(R.id.bigPieChart);
//
//        pieChartBig.setUsePercentValues(true);
//        pieChartBig.getDescription().setEnabled(false);
//        pieChartBig.setExtraOffsets(5, 10, 5, 5);
//
//        pieChartBig.setDragDecelerationFrictionCoef(0.95F);
//
//        pieChartBig.setDrawHoleEnabled(false);
//        pieChartBig.setHoleColor(Color.WHITE);
//        pieChartBig.setTransparentCircleRadius(61F);
//
//        ArrayList<PieEntry> yValuesBig = new ArrayList<PieEntry>();
//
//        if (angryNum > 0) {
//            yValuesBig.add(new PieEntry(angryNum, "분노"));
//        }
//        if (sadNum > 0) {
//            yValuesBig.add(new PieEntry(sadNum, "슬픔"));
//        }
//        if (anxiousNum > 0) {
//            yValuesBig.add(new PieEntry(anxiousNum, "불안"));
//        }
//        if (embarrassedNum > 0) {
//            yValuesBig.add(new PieEntry(embarrassedNum, "당황"));
//        }
//        if (hurtNum > 0) {
//            yValuesBig.add(new PieEntry(hurtNum, "상처"));
//        }
//        if (happyNum > 0) {
//            yValuesBig.add(new PieEntry(happyNum, "기쁨"));
//        }
//
//        Description descriptionBig = new Description();
//        descriptionBig.setText("Big Category");
//        descriptionBig.setTextSize(15);
//        pieChartBig.setDescription(descriptionBig);
//
//        //pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
//
//        PieDataSet dataSetBig = new PieDataSet(yValuesBig, "emotion");
//        dataSetBig.setSliceSpace(2f);
//        dataSetBig.setSelectionShift(3f);
//        dataSetBig.setColors(ColorTemplate.PASTEL_COLORS);
//
//        PieData dataBig = new PieData((dataSetBig));
//        dataBig.setValueTextSize(10f);
//        dataBig.setValueTextColor(Color.YELLOW);
//
//
//        pieChartBig.setData(dataBig);


    }



    public static String getDateDay(String date) throws Exception {

        String day = "";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date nDate = dateFormat.parse(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(nDate);

        int dayNum = cal.get(Calendar.DAY_OF_WEEK);

        switch (dayNum) {
            case 1:
                day = "일";
                break;
            case 2:
                day = "월";
                break;
            case 3:
                day = "화";
                break;
            case 4:
                day = "수";
                break;
            case 5:
                day = "목";
                break;
            case 6:
                day = "금";
                break;
            case 7:
                day = "토";
                break;

        }

        return day;
    }



    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.statisticBtn1:
                pieChart.setVisibility(View.VISIBLE);
                pieChartBig.setVisibility(View.GONE);
                stackedChart.setVisibility(View.GONE);
                chart_replace.setVisibility(View.GONE);
                break;

            case R.id.statisticBtn2:
                pieChart.setVisibility(View.GONE);
                pieChartBig.setVisibility(View.VISIBLE);
                stackedChart.setVisibility(View.GONE);
                chart_replace.setVisibility(View.GONE);
                break;

            case R.id.statisticBtn3:
                pieChart.setVisibility(View.GONE);
                pieChartBig.setVisibility(View.GONE);
                stackedChart.setVisibility(View.VISIBLE);
                chart_replace.setVisibility(View.GONE);
                break;
        }

    }
}
