package com.oil.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

import com.example.oilclient.R;
import com.oil.bean.Constants;
import com.oil.bean.MyConfig;
import com.oil.bean.MyRequestParams;
import com.oil.bean.ProductBean;
import com.oil.dialogs.CommontitleDialog;
import com.oil.dialogs.CommontitleDialog.onComDialogBtnClick;
import com.oil.inter.OnReturnListener;
import com.oil.utils.HttpTool;
import com.oil.utils.ToastUtils;

public class ShangjiDetailsActivity extends Activity implements OnClickListener {
	ImageView iv_back, iv_more;
	TextView tv_proInfo, tv_connInfo;
	PopupMenu pMenu;
	String sdid;
	Button btn_call;
	boolean isUserReq = false;
	ProductBean currentPro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shangjidetail);
		Intent intent = getIntent();
		if (intent != null) {
			isUserReq = intent.getBooleanExtra("isUserReq", false);
			sdid = intent.getStringExtra("sdid");
		}

		initView();

	}

	protected void onResume() {
		super.onResume();
		initData();
	};

	StringBuilder sb_pro, sb_conn;

	private void initData() {
		// TODO Auto-generated method stub
		if (sdid != null) {
			MyRequestParams params = new MyRequestParams(
					ShangjiDetailsActivity.this);
			params.put("sdid", sdid);
			HttpTool.netRequest(ShangjiDetailsActivity.this, params,
					new OnReturnListener() {

						@Override
						public void onReturn(String jsString) {
							// TODO Auto-generated method stub
							try {
								JSONObject jObject = (JSONObject) new JSONObject(
										jsString).getJSONObject("data")
										.getJSONArray("message").get(0);
								sb_pro = new StringBuilder();
								sb_pro.append("产品名称："
										+ jObject.getString("cpname")
										+ "\n"
										+ "规格类型："
										+ jObject.getString("model")
										+ "\n"
										+ "数量："
										+ ((jObject.getString("stock") == null || jObject
												.getString("stock").equals(
														"null")) ? " "
												: (jObject.get("stock") + "吨"))
										+ "\n"
										+ "价格："
										+ (jObject.getString("price").equals(
												"null") ? "面议" : (jObject
												.getString("price") + "元")));
								tv_proInfo.setText(sb_pro.toString());
								sb_conn = new StringBuilder();
								sb_conn.append("联系人："
										+ jObject.getString("name") + "\n"
										+ "发布企业："
										+ jObject.getString("corpname") + "\n"
										+ "省份：" + jObject.getString("province")
										+ "\n" + "备注："
										+ jObject.getString("remark"));
								tv_connInfo.setText(sb_conn.toString());
								currentPro = getPublishList(jsString).get(0);

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

					}, Constants.URL_getGQ, false);
		}
	}

	private void initView() {
		// TODO Auto-generated method stub

		iv_back = (ImageView) findViewById(R.id.iv_pageback);
		iv_more = (ImageView) findViewById(R.id.iv_more);
		btn_call = (Button) findViewById(R.id.btn_call);
		if (isUserReq) {
			btn_call.setVisibility(View.INVISIBLE);
		}
		btn_call.setOnClickListener(this);
		iv_more.setOnClickListener(this);
		iv_back.setOnClickListener(this);
		tv_proInfo = (TextView) findViewById(R.id.tv_prodetails);
		tv_connInfo = (TextView) findViewById(R.id.tv_conn);
		initPoputMenu();
	}

	private void initPoputMenu() {
		// TODO Auto-generated method stub
		pMenu = new PopupMenu(ShangjiDetailsActivity.this, iv_more);
		if (isUserReq) {
			pMenu.getMenuInflater().inflate(R.menu.user_gq_do, pMenu.getMenu());
		} else {
			pMenu.getMenuInflater().inflate(R.menu.order_details,
					pMenu.getMenu());
		}

		pMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				switch (item.getItemId()) {
				case R.id.item_share:
					// ToastUtils.getInstance(ShangjiDetailsActivity.this)
					// .showText("分享");
					Intent intent = new Intent(Intent.ACTION_SEND);
					intent.setType("image/*");
					intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
					intent.putExtra(Intent.EXTRA_TEXT, sb_pro.toString() + "\n"
							+ sb_conn.toString() + "\n"
							+ "来自——隆众资讯 www.oilchem.net");
					intent.putExtra(Intent.EXTRA_TITLE, "标题");
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(Intent.createChooser(intent, getTitle()));
					break;
				case R.id.item_match:
					doMatch();
					break;
				case R.id.item_update:
					doUpdate();
					break;
				// case R.id.item_shuoxin:
				// doRefreshGq();
				// break;
				case R.id.item_delete:
					final CommontitleDialog titleDialog = new CommontitleDialog(
							ShangjiDetailsActivity.this);
					titleDialog.Init("提示", "确定要删除此供求？",
							new onComDialogBtnClick() {

								@Override
								public void onConfirmClick() {
									// TODO Auto-generated method stub
									doDeleteGq();
									titleDialog.disMiss();
								}

								@Override
								public void onCancelClick() {
									// TODO Auto-generated method stub
									titleDialog.disMiss();
								}
							});
					titleDialog.show();

					break;
				default:
					break;
				}
				return false;
			}

		});
	}

	private void doUpdate() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(ShangjiDetailsActivity.this,
				GqPublicActivity.class);
		intent.putExtra("isMine", true);
		intent.putExtra("product", currentPro);
		startActivity(intent);

	}

	private void doMatch() {
		startActivity(new Intent(ShangjiDetailsActivity.this,
				ShangjiMatachAct.class).putExtra("sdid", sdid));
	}

	private void doDeleteGq() {
		// TODO Auto-generated method stub
		MyRequestParams params = new MyRequestParams(
				ShangjiDetailsActivity.this);
		params.put("sdid", sdid);
		HttpTool.netRequest(ShangjiDetailsActivity.this, params,
				new OnReturnListener() {

					@Override
					public void onReturn(String jsString) {
						// TODO Auto-generated method stub
						try {
							JSONObject jObject = new JSONObject(jsString);
							String code = jObject.getJSONObject("data")
									.getString("succeed");
							String msg = jObject.getJSONObject("data")
									.getString("msg");
							if (code.equals("1")) {
								ToastUtils.getInstance(
										ShangjiDetailsActivity.this).showText(
										msg);
								finish();
							} else {
								ToastUtils.getInstance(
										ShangjiDetailsActivity.this).showText(
										msg);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, MyConfig.DELETE_PRODUCT, false);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_pageback:
			finish();
			break;
		case R.id.iv_more:
			pMenu.show();
			break;
		case R.id.btn_call:
			doCall();
			break;
		default:
			break;
		}
	}

	private void doCall() {
		// TODO Auto-generated method stub
		MyRequestParams params = new MyRequestParams(
				ShangjiDetailsActivity.this);
		params.put("sdid", sdid);
		HttpTool.netRequest(ShangjiDetailsActivity.this, params,
				new OnReturnListener() {

					@Override
					public void onReturn(String jsString) {
						// TODO Auto-generated method stub
						try {
							JSONObject jObject = new JSONObject(jsString)
									.getJSONObject("data");
							String mobile = jObject.getString("mobile");

							Uri uri = Uri.parse("tel:" + mobile);
							Intent it = new Intent(Intent.ACTION_DIAL, uri);
							startActivity(it);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, Constants.URL_getGqPhone, false);
	}

	/**
	 * 解析供求历史列表
	 */
	public ArrayList<ProductBean> getPublishList(String jsString) {
		ArrayList<ProductBean> list = new ArrayList<ProductBean>();
		try {
			JSONArray array = new JSONObject(jsString).getJSONObject("data")
					.getJSONArray("message");

			for (int i = 0; i < array.length(); i++) {
				ProductBean product = new ProductBean();
				JSONObject obj = array.getJSONObject(i);
				product.sdid = obj.getString("sdid");
				product.tb = obj.getString("tb");
				product.addTime = obj.getString("addtime").equals("null") ? ""
						: obj.getString("addtime");
				product.refreshTime = obj.getString("refreshtime").equals(
						"null") ? "" : obj.getString("refreshtime");
				product.corpId = obj.getString("corpid").equals("null") ? ""
						: obj.getString("corpid");
				product.corpName = obj.getString("corpname").equals("null") ? ""
						: obj.getString("corpname");
				product.cpName = obj.getString("cpname").equals("null") ? ""
						: obj.getString("cpname");
				product.stock = obj.getString("stock").equals("null") ? ""
						: obj.getString("stock");

				if (obj.has("lastCallTime")) {
					product.lastCallTime = obj.getString("lastCallTime")
							.equals("null") ? "" : obj
							.getString("lastCallTime");
				}
				if (obj.has("price")) {
					product.price = obj.getString("price").equals("null") ? ""
							: obj.getString("price");
				}
				if (obj.has("cpid")) {
					product.cpId = obj.getString("cpid");
				}
				if (obj.has("phone")) {
					product.phone = obj.getString("phone").equals("null") ? ""
							: obj.getString("phone");
				}
				if (obj.has("model")) {
					product.model = obj.getString("model").equals("null") ? ""
							: obj.getString("model");
				}
				if (obj.has("refreshtime")) {
					product.refreshTime = obj.getString("refreshtime").equals(
							"null") ? "" : obj.getString("refreshtime");
				}

				list.add(product);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
