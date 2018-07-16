package com.match.developer.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioButton;

import com.match.developer.R;
import com.match.developer.model.Cost;
import com.match.developer.util.EmptyUtils;
import com.match.developer.util.MyUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 添加一笔记录
 */
public class AddRecordActivity extends AppCompatActivity {

    @BindView(R.id.id_add_money)
    EditText mMoney;
    @BindView(R.id.id_add_desc)
    EditText mDesc;
    @BindView(R.id.id_add_type_food)
    RadioButton mTypeFood;//支出类型 餐饮
    @BindView(R.id.id_add_type_wen)
    RadioButton mTypeWen;//支出类型 水电气网

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.id_add_in)
    public void addRecordIn() {
        String money = mMoney.getText().toString();
        String desc = mDesc.getText().toString();
        if (EmptyUtils.isEmpty(money)) {
            MyUtils.showToast(getResources().getString(R.string.money_null));
            return;
        }
        if (EmptyUtils.isEmpty(desc)) {
            MyUtils.showToast(getResources().getString(R.string.desc_null));
            return;
        }
        Cost cost = new Cost();
        cost.setMoney(Double.parseDouble(money));
        cost.setDesc(desc);
        cost.setType(MyUtils.COST_TYPE_IN);
        cost.setName(MainActivity.user.getName());
        cost.setDate(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));
        cost.setMonth(new SimpleDateFormat("YYYY年MM月份").format(new Date()));
        cost.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    //保存成功
                    AddRecordActivity.this.finish();
                } else {
                    //保存失败
                    MyUtils.showToast(getResources().getString(R.string.add_record_err));
                }
            }
        });
    }

    @OnClick(R.id.id_add_out)
    public void addRecordOut() {
        String money = mMoney.getText().toString();
        String desc = mDesc.getText().toString();
        boolean checkedFood = mTypeFood.isChecked();
        boolean checkedWen = mTypeWen.isChecked();
        if (EmptyUtils.isEmpty(money)) {
            MyUtils.showToast(getResources().getString(R.string.money_null));
            return;
        }
        if (EmptyUtils.isEmpty(desc)) {
            MyUtils.showToast(getResources().getString(R.string.desc_null));
            return;
        }
        if (!checkedFood && !checkedWen) {
            MyUtils.showToast(getResources().getString(R.string.type_out_null));
            return;
        }
        Cost cost = new Cost();
        cost.setMoney(Double.parseDouble(money));
        cost.setDesc(desc);
        cost.setType(MyUtils.COST_TYPE_OUT);
        if (checkedFood) {
            cost.setTypeDesc(MyUtils.COST_TYPE_OUT_FOOD);
        } else if (checkedWen) {
            cost.setTypeDesc(MyUtils.COST_TYPE_OUT_WEN);
        }
        cost.setName(MainActivity.user.getName());
        cost.setDate(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));
        cost.setMonth(new SimpleDateFormat("YYYY年MM月份").format(new Date()));
        cost.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    //保存成功
                    AddRecordActivity.this.finish();
                } else {
                    //保存失败
                    MyUtils.showToast(getResources().getString(R.string.add_record_err));
                }
            }
        });

    }
}
