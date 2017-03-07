package com.match.developer.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.match.developer.R;
import com.match.developer.adapter.CostAdapter;
import com.match.developer.model.Cost;
import com.match.developer.ui.AddRecordActivity;
import com.match.developer.util.MyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Tanner on 2017/3/1.
 * 记录
 */

public class RecordFragment extends Fragment {
    private static final String TAG = "RecordFragment";
    @BindView(R.id.id_record_piechart)
    PieChart mChart;

    @BindView(R.id.id_record_gross_income)
    TextView mIncome;//总收入
    @BindView(R.id.id_record_total_expenditure)
    TextView mExpenditure;//总支出
    @BindView(R.id.id_record_balance)
    TextView mBalance;
    @BindView(R.id.id_record_list)
    ListView mListView;
    @BindView(R.id.id_record_type)
    TextView mType;
    @BindView(R.id.id_record_type_money)
    TextView mTypeMoney;

    double income;
    double expenditure;
    double balance;

    float quarterlyFood;
    float quarterlyWen;
    Cost mCostFoode;
    Cost mCostWen;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onResume() {
        getCostAll();
        getLately();
        slectAllout();
        super.onResume();
    }

    private void slectAllout() {
        BmobQuery<Cost> query = new BmobQuery<Cost>();
        query.addWhereEqualTo("type", "out");
        query.sum(new String[]{"money"});
        query.groupby(new String[]{"typeDesc"});
        query.findStatistics(Cost.class, new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                int length = jsonArray.length();
                for (int i = 0; i < length; i++) {
                    try {
                        JSONObject obj = (JSONObject) jsonArray.get(i);
                        String type = obj.getString("typeDesc");
                        if (type.equals(MyUtils.COST_TYPE_OUT_FOOD)) {
                            quarterlyFood = Float.parseFloat(obj.getDouble("_sumMoney") + "");//_(关键字)+首字母大写的列名
                            mCostFoode = new Cost();
                            mCostFoode.setTypeDesc(MyUtils.COST_TYPE_OUT_FOOD);
                            mCostFoode.setMoney(quarterlyFood);

                        } else if (type.equals(MyUtils.COST_TYPE_OUT_WEN)) {
                            quarterlyWen = Float.parseFloat(obj.getDouble("_sumMoney") + "");//_(关键字)+首字母大写的列名
                            mCostWen = new Cost();
                            mCostWen.setTypeDesc(MyUtils.COST_TYPE_OUT_WEN);
                            mCostWen.setMoney(quarterlyWen);
                        }
                        PieData mPieData = getPieData(2, 100);

                        showChart(mChart, mPieData);
                        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                            @Override
                            public void onValueSelected(Entry e, Highlight h) {
                                Cost cost = (Cost) e.getData();
                                String type = cost.getTypeDesc();
                                if (type.equals(MyUtils.COST_TYPE_OUT_FOOD)) {
                                    mType.setText(getResources().getString(R.string.type_food));
                                    mTypeMoney.setText(cost.getMoney()+"");
                                }else if(type.equals(MyUtils.COST_TYPE_OUT_WEN)){
                                    mType.setText(getResources().getString(R.string.type_wen));
                                    mTypeMoney.setText(cost.getMoney()+"");
                                }else{
                                    mType.setText(getResources().getString(R.string.type_out));
                                    mTypeMoney.setText(expenditure+"");
                                }
//                                MyUtils.showToast("VAL SELECTED"+"Value: " + e.getY() + ", index: " + h.getX()+ ", DataSet index: " + h.getDataSetIndex());
                            }

                            @Override
                            public void onNothingSelected() {

                            }
                        });
                        mChart.setOnHoverListener(new View.OnHoverListener() {
                            @Override
                            public boolean onHover(View view, MotionEvent motionEvent) {
                                return false;
                            }
                        });
                        mChart.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View view, boolean b) {
                                if(b){
                                    MyUtils.showToast("选中");
                                }else{

                                    MyUtils.showToast("未选中");
                                }
                            }
                        });

                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * @param count 分成几部分
     * @param range
     */
    private PieData getPieData(int count, float range) {

        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容

        for (int i = 0; i < count; i++) {
            xValues.add("Quarterly" + (i + 1));  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4
        }

        List<PieEntry> yValues = new ArrayList<>();  //yVals用来表示封装每个饼块的实际数据

        // 饼图数据
        /**
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
         * 所以 14代表的百分比就是14%
         */


        boolean add = yValues.add(new PieEntry(quarterlyFood, "", mCostFoode));
        boolean add1 = yValues.add(new PieEntry(quarterlyWen, "", mCostWen));

        //y轴的集合
//        PieDataSet pieDataSet = new PieDataSet(yValues,"sss");/*显示在比例图上*/
        PieDataSet pieDataSet = new PieDataSet(yValues, "");
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离

        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(pieDataSet);

        return pieData;
    }

    private void showChart(PieChart pieChart, PieData pieData) {
//        pieChart.setHoleColorTransparent(true);
        pieChart.setHovered(true);

        pieChart.setHoleRadius(60f);  //半径
        pieChart.setTransparentCircleRadius(64f); // 半透明圈
        //pieChart.setHoleRadius(0)  //实心圆

        pieChart.setDescription(new Description());

        // mChart.setDrawYValues(true);
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

        pieChart.setCenterText(getResources().getString(R.string.out_des));  //饼状图中间的文字

        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }

    @OnClick(R.id.id_record_toadd)
    public void toAdd() {
        getActivity().startActivity(new Intent(getActivity(), AddRecordActivity.class));
    }


    /**
     * 获取所有的消费总和
     */
    public void getCostAll() {
        BmobQuery<Cost> query = new BmobQuery<Cost>();
        query.sum(new String[]{"money"});
        query.groupby(new String[]{"type"});
        query.findStatistics(Cost.class, new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                int length = jsonArray.length();
                for (int i = 0; i < length; i++) {
                    try {
                        JSONObject obj = (JSONObject) jsonArray.get(i);
                        String type = obj.getString("type");
                        if (type.equals("in")) {
                            income = obj.getDouble("_sumMoney");
                            mIncome.setText(income + "");
                        } else if (type.equals("out")) {
                            expenditure = obj.getDouble("_sumMoney");
                            mExpenditure.setText(expenditure + "");
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
                balance = income - expenditure;
                if (balance >= 0) {
                    mBalance.setTextColor(getResources().getColor(R.color.green));
                } else {
                    mBalance.setTextColor(getResources().getColor(R.color.red));
                }
                mBalance.setText(new DecimalFormat("######0.0").format(income - expenditure));
                mType.setText(getResources().getString(R.string.type_out));
                mTypeMoney.setText(expenditure+"");
            }
        });

    }

    /**
     * 获取最近的消费记录 10条
     */
    public void getLately() {
        BmobQuery<Cost> query = new BmobQuery<Cost>();
        query.setLimit(10);
        query.order("-createdAt");
        query.findObjects(new FindListener<Cost>() {
            @Override
            public void done(List<Cost> list, BmobException e) {
                mListView.setAdapter(new CostAdapter(getActivity(), list));
            }
        });
    }
}
