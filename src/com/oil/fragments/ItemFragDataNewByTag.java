// package com.oil.fragments;
//
// import java.util.HashMap;
//
// import com.oil.adapter.ExpandDataTagAdapter;
//
// import android.view.View;
// import android.widget.ExpandableListView;
// import android.widget.ExpandableListView.OnChildClickListener;
//
/// **
// * 将数据以tag分组
// *
// * @author xu
// *
// */
// public class ItemFragDataNewByTag extends ItemFragDataNew {
// public ItemFragDataNewByTag(int type, HashMap<String, String> map) {
// this.type = type;
// this.map = map;
// }
//
// @Override
// public void initView() {
// // TODO Auto-generated method stub
// super.initView();
// expendAdapter = new ExpandDataTagAdapter(getActivity(), tagList,
// currentGroupMapList);
// ptep_lv.getRefreshableView().setAdapter(expendAdapter);
// ptep_lv.getRefreshableView().setOnChildClickListener(new
// OnChildClickListener() {
//
// @Override
// public boolean onChildClick(ExpandableListView parent, View v, int
// groupPosition, int childPosition,
// long id) {
// // TODO Auto-generated method stub
// return false;
// }
// });
// }
//
// }
