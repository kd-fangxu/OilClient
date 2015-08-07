package com.oil.activity;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oilclient.R;
import com.oil.bean.MyConfig;
import com.oil.bean.OilUser;
import com.oil.bean.ProductBean;
import com.oil.datamodel.HttpReqRecep;
import com.oil.event.FinishEvent;
import com.oil.inter.OnReturnListener;
import com.oil.utils.HttpTool;
import com.oil.utils.JsonReader;

import de.greenrobot.event.EventBus;

//发布或查看供求
public class GqPublicActivity extends Activity {
	EditText chanpinmingcheng;
	EditText chanpinguige;
	EditText price;
	EditText shuliang;
	EditText remark;
	GridView provinceGridView;
	TextView marked, markedTag;
	String productname = "";
	TextView gongying, xuqiu;
	JSONObject lianxirenjsobj = new JSONObject();
	String type = "1";
	private boolean mIsMine;
	private TextView mDelete, mTitle;
	private Spinner mAreaSpinner;
	private ArrayList<String[]> areas; // 所有地区
	private ArrayList<String[]> provinces = new ArrayList<String[]>();// 对应省份
	private ProvinceAdapter provinceAdapter;
	private ArrayList<String[]> allProvinces; // 全部省份
	ArrayAdapter<String> spinnerAreaAdapter;
	private String mAreaId = null;
	private String[] salesAreaName;
	private String cpId;
	ArrayList<String> selectedProvincesId = new ArrayList<String>(); // 选中的省份id
	private int selectCount = 0; // 忽略spinner一开始默认选中的两次
	private Dialog deleteDia;
	private ProductBean product;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish);

		initView();

		EventBus.getDefault().register(this);

	}

	private void initView() {
		mIsMine = getIntent().getBooleanExtra("isMine", false);
		mDelete = (TextView) findViewById(R.id.shanchu);
		mTitle = (TextView) findViewById(R.id.title);
		markedTag = (TextView) findViewById(R.id.markedTag);
		marked = (TextView) findViewById(R.id.marked);
		provinceGridView = (GridView) findViewById(R.id.provincesGridView);
		gongying = (TextView) findViewById(R.id.tigong);
		xuqiu = (TextView) findViewById(R.id.xuqiu);

		AlertDialog.Builder builder = new AlertDialog.Builder(
				GqPublicActivity.this);
		builder.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		builder.setTitle("提示");
		builder.setMessage("删除该供求?");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				delete();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		});

		deleteDia = builder.create();
		deleteDia.setCancelable(true);
		if (mIsMine) {
			gongying.setEnabled(false);
			xuqiu.setEnabled(false);
			selectCount = 0;
			mDelete.setVisibility(View.VISIBLE);
			mTitle.setText("我的供求");
			mDelete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					deleteDia.show();
				}
			});
			product = (ProductBean) getIntent().getSerializableExtra("product");
			getMyProductInfo();

		} else {
			selectCount = 2;
			marked.setText("全国"); // 默认
			mDelete.setVisibility(View.GONE);
			mTitle.setText("发布供求");

		}

		mAreaSpinner = (Spinner) findViewById(R.id.area);
		areas = JsonReader.getAreas(GqPublicActivity.this);
		allProvinces = JsonReader.getProvinces("-1", GqPublicActivity.this);

		salesAreaName = getNameList(areas);
		spinnerAreaAdapter = new ArrayAdapter<String>(GqPublicActivity.this,
				R.layout.spinner_item_layout, salesAreaName);

		mAreaSpinner.setAdapter(spinnerAreaAdapter);

		provinceAdapter = new ProvinceAdapter();
		provinceGridView.setAdapter(provinceAdapter);
		mAreaSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				selectCount++;
				if (selectCount < 3) {

					try {

						Field field = AdapterView.class
								.getDeclaredField("mOldSelectedPosition");
						field.setAccessible(true); // 设置mOldSelectedPosition可访问
						field.setInt(mAreaSpinner, AdapterView.INVALID_POSITION); // 设置mOldSelectedPosition的值
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					mAreaId = areas.get(position)[0];

					if (mAreaId.equals("0")) {
						marked.setText("全国");
						selectedProvincesId.clear();
						provinces.clear();
						provinceAdapter.notifyDataSetChanged();
					} else {
						provinces = JsonReader.getProvinces(mAreaId,
								GqPublicActivity.this);
						provinceAdapter.notifyDataSetChanged();
					}

				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				mAreaId = null;
			}
		});

		ImageButton back = (ImageButton) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				// if (!mIsMine) {
				// overridePendingTransition(R.anim.anim_null,
				// R.anim.slide_out_bottom);
				// }

			}
		});

		gongying.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gongying.setTextColor(getResources().getColor(R.color.red));
				xuqiu.setTextColor(getResources().getColor(R.color.gray));
				type = "1";
			}
		});
		xuqiu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				xuqiu.setTextColor(getResources().getColor(R.color.red));
				gongying.setTextColor(getResources().getColor(R.color.gray));
				type = "0";
			}
		});
		try {
			if (product.tb.equals("11")) {
				gongying.performClick();

			} else {
				xuqiu.performClick();

			}
		} catch (Exception e) {
		}
		chanpinmingcheng = (EditText) findViewById(R.id.chanpinmingcheng);
		chanpinguige = (EditText) findViewById(R.id.chanpinguige);
		remark = (EditText) findViewById(R.id.remark);

		chanpinmingcheng.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(GqPublicActivity.this,
						ActivityProductName.class);
				startActivityForResult(intent, 20);

			}
		});

		price = (EditText) findViewById(R.id.price);
		shuliang = (EditText) findViewById(R.id.shuliang);

		TextView save = (TextView) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publish(type);
			}
		});
	}

	// 关闭
	public void onEvent(FinishEvent event) {
		finish();
	}

	/**
	 * 获得地区或省份名称列表
	 * 
	 * @param list
	 * @return
	 */
	private String[] getNameList(ArrayList<String[]> list) {

		String[] areaName = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			areaName[i] = list.get(i)[1];
		}

		return areaName;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent itent) {
		if (resultCode == MyConfig.GET_PRODUCTNAME_RESULTCODE) { // 返回产品名称和id
			productname = itent.getExtras().getString("name");
			cpId = itent.getExtras().getString("id");
			chanpinmingcheng.setText(productname);
		}
		super.onActivityResult(requestCode, resultCode, itent);

	}

	// 发布或修改
	public void publish(final String type) {

		if (chanpinmingcheng.getText().length() == 0) {
			Toast.makeText(GqPublicActivity.this, "请填写产品名称", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		if (price.getText().equals("")) {
			Toast.makeText(GqPublicActivity.this, "请填写产品价格", Toast.LENGTH_SHORT)
					.show();
			return;
		}

		if (shuliang.getText().equals("")) {
			Toast.makeText(GqPublicActivity.this, "请填写产品数量", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		ProductBean newProduct = new ProductBean();
		if (mIsMine) {
			newProduct.sdid = product.sdid;
		} else {
			newProduct.sdid = "0";
		}

		newProduct.tb = type;
		final OilUser oilUser = OilUser.getCurrentUser(GqPublicActivity.this);
		newProduct.name = oilUser.getName();
		newProduct.phone = oilUser.getPhone();
		newProduct.cpName = chanpinmingcheng.getText().toString();
		newProduct.cpId = cpId;
		newProduct.model = chanpinguige.getText().toString();
		newProduct.price = price.getText().toString();
		newProduct.stock = shuliang.getText().toString();
		newProduct.remark = remark.getText().toString();
		newProduct.provincesId = selectedProvincesId;
		if (mAreaId == null || mAreaId.equals("0")) {
			newProduct.areaId = "1";// 1:全国 0:其他
		} else {
			newProduct.areaId = "0";
		}

		HttpReqRecep.publish(GqPublicActivity.this, newProduct,
				new OnReturnListener() {

					@Override
					public void onReturn(String jsString) {
						// TODO Auto-generated method stub
						JSONObject js;

						try {
							js = new JSONObject(jsString).getJSONObject("data");
							if (js.getString("succeed").equals("1")) {
								Toast.makeText(GqPublicActivity.this, "发布成功！",
										Toast.LENGTH_SHORT).show();

								Editor editor = getSharedPreferences(
										MyConfig.REFRESH_TIME_FLAG
												+ oilUser.getName(),
										Activity.MODE_PRIVATE).edit();
								editor.putLong(MyConfig.HOME_REFRESH_TIME, 0);
								editor.commit();
								// EventBus.getDefault().post(
								// new RefreshPublishEvent());
								finish();
							} else {
								Toast.makeText(GqPublicActivity.this,
										js.getString("msg"), Toast.LENGTH_SHORT)
										.show();

							}
						} catch (JSONException e) {
						}

					}
				});

	}

	// 获取供求详情
	public void getMyProductInfo() {

		HttpReqRecep.getProductInfo(GqPublicActivity.this, product.sdid,
				new OnReturnListener() {

					@Override
					public void onReturn(String jsString) {
						// TODO Auto-generated method stub
						JSONObject js;

						try {
							js = new JSONObject(jsString).getJSONObject("data");
							if (js.getString("login").equals("1")) {
								final JSONObject temp = js.getJSONArray(
										"message").getJSONObject(0);
								if (temp.getString("tb").equals("1")) {
									gongying.performClick();
								} else {
									xuqiu.performClick();
								}
								cpId = temp.getString("cpId");
								chanpinmingcheng.setText(temp
										.getString("cpname"));
								chanpinguige.setText(temp.getString("model"));
								price.setText(temp.getString("price"));
								shuliang.setText(temp.getString("stock"));
								remark.setText(temp.getString("remark"));
								String s = temp.getString("province");
								if (s.equals("全国")) {
									mAreaSpinner.setSelection(0);
								} else {
									mAreaId = "2"; // 其他
									selectedProvincesId = getSelectedProvincesId(s);
								}
								marked.setText(s);
							} else {
								Toast.makeText(GqPublicActivity.this,
										js.getString("message"),
										Toast.LENGTH_SHORT).show();

							}
						} catch (JSONException e) {
						}

					}
				});

	}

	// 根据id得到名字
	private String getName(String id, ArrayList<String[]> list) {
		String name = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i)[0].equals(id)) {
				name = list.get(i)[1];
				break;
			}
		}
		return name;
	}

	// 根据名字得到索引
	private int getIndex(String name, String[] nameList) {
		int index = 0;
		for (int i = 0; i < nameList.length; i++) {
			if (nameList[i].equals(name)) {
				index = i;
				break;
			}
		}
		return index;
	}

	public void delete() {
		HttpReqRecep.deleteProduct(GqPublicActivity.this, product.sdid,
				new OnReturnListener() {

					@Override
					public void onReturn(String jsString) {
						JSONObject js;

						try {

							js = new JSONObject(jsString).getJSONObject("data");
							if (js.getString("succeed").equals("1")) {
								Toast.makeText(GqPublicActivity.this,
										js.getString("msg"), Toast.LENGTH_SHORT)
										.show();

								Editor editor = getSharedPreferences(
										MyConfig.REFRESH_TIME_FLAG
												+ OilUser.getCurrentUser(
														GqPublicActivity.this)
														.getName(),
										Activity.MODE_PRIVATE).edit();
								editor.putLong(MyConfig.HOME_REFRESH_TIME, 0);
								editor.commit();

								// EventBus.getDefault().post(
								// new RefreshPublishEvent());
								finish();

							} else {
								Toast.makeText(GqPublicActivity.this,
										js.getString("msg"), Toast.LENGTH_SHORT)
										.show();

							}
						} catch (JSONException e) {
						}

					}
				});
	}

	class ProvinceAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return provinces.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return provinces.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		/**
		 * province[0]="id"; province[1]="名称";
		 *
		 */

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			convertView = View.inflate(GqPublicActivity.this,
					R.layout.provinces_item, null);
			CheckBox checkBox = (CheckBox) convertView
					.findViewById(R.id.checkBox);
			checkBox.setText(provinces.get(position)[1]);

			if (selectedProvincesId.contains(provinces.get(position)[0])) {
				provinces.get(position)[2] = "true";
				checkBox.setChecked(true);
			} else {
				provinces.get(position)[2] = "false";
				checkBox.setChecked(false);
			}

			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					provinces.get(position)[2] = provinces.get(position)[2]
							.equals("true") ? "false" : "true";
					onSelectedProvincesChanged(provinces.get(position)[0],
							provinces.get(position)[2].equals("true"));

				}

			});
			return convertView;
		}

	}

	/**
	 * 根据省份名称获得已选id列表
	 */
	private ArrayList<String> getSelectedProvincesId(String provincesList) {
		ArrayList<String> idList = new ArrayList<String>();
		String[] nameList = provincesList.split(",");
		for (int i = 0; i < nameList.length; i++) {
			for (int j = 0; j < allProvinces.size(); j++) {
				if (allProvinces.get(j)[1].contains(nameList[i])) {
					idList.add(allProvinces.get(j)[0]);
					break;
				}
			}
		}

		return idList;
	}

	/**
	 * 已选中省份发生变化
	 * 
	 * @param checked
	 * @param provinId
	 */
	private void onSelectedProvincesChanged(String provinceId, boolean checked) {

		if (checked) { // 添加
			if (!selectedProvincesId.contains(provinceId)) {
				selectedProvincesId.add(provinceId);
			}

		} else { // 删除

			if (selectedProvincesId.contains(provinceId)) {
				selectedProvincesId.remove(provinceId);
			}

		}

		StringBuffer selectedProvinces = new StringBuffer();
		for (int i = 0; i < allProvinces.size(); i++) {
			if (selectedProvincesId.contains(allProvinces.get(i)[0])) {
				selectedProvinces.append(allProvinces.get(i)[1] + "  ");
			}
		}

		provinceAdapter.notifyDataSetChanged();
		marked.setText(selectedProvinces.toString());
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			// if (!mIsMine) {
			// overridePendingTransition(R.anim.anim_null,
			// R.anim.slide_out_bottom);
			// }
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		deleteDia.cancel();

		super.onDestroy();
	}
}