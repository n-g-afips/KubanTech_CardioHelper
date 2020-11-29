package my.virkato.heart.user;

import android.app.Activity;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.view.View;
import java.text.DecimalFormat;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.DialogFragment;
import android.Manifest;
import android.content.pm.PackageManager;


public class SuggestionActivity extends  Activity { 
	
	
	private HashMap<String, Object> factor = new HashMap<>();
	private double r_age = 0;
	private double smoking = 0;
	private double alcohol_high = 0;
	private double alcohol_medium = 0;
	private double alcohol_light = 0;
	private double sleeping_start = 0;
	private double sleeping_finish = 0;
	private double air = 0;
	private boolean haveAllData = false;
	private double result = 0;
	private double n = 0;
	private double m = 0;
	private HashMap<String, Object> seeks = new HashMap<>();
	private HashMap<String, Object> r_sugg = new HashMap<>();
	private double rez = 0;
	private HashMap<String, Object> risk = new HashMap<>();
	private HashMap<String, Object> r_rcmnd = new HashMap<>();
	private String str = "";
	
	private ArrayList<HashMap<String, Object>> suggestions = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> factors = new ArrayList<>();
	private ArrayList<String> tmp_name = new ArrayList<>();
	private ArrayList<String> seek = new ArrayList<>();
	
	private ScrollView vs_main;
	private LinearLayout l_main;
	private LinearLayout l_suggestion;
	private LinearLayout l_factors;
	private TextView textview1;
	private ListView lv_suggestions;
	private LinearLayout l_age;
	private LinearLayout l_smoking;
	private LinearLayout l_alcohol_high;
	private LinearLayout l_alcohol_medium;
	private LinearLayout l_alcohol_light;
	private LinearLayout l_sleeping;
	private LinearLayout l_air;
	private Button b_result;
	private TextView t_age;
	private EditText e_age_count;
	private Switch sw_smoking;
	private EditText e_smoking_count;
	private Switch sw_alcohol_high;
	private EditText e_alcohol_high;
	private Switch sw_alcohol_medium;
	private EditText e_alcohol_medium;
	private Switch sw_alcohol_light;
	private EditText e_alcohol_light;
	private LinearLayout l_sleeping_start;
	private LinearLayout l_sleep_finish;
	private TextView textview6;
	private EditText e_sleeping_start;
	private TextView textview7;
	private EditText e_sleeping_finish;
	private Switch sw_air;
	private EditText e_air;
	private TextView textview8;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.suggestion);
		initialize(_savedInstanceState);
		if (Build.VERSION.SDK_INT >= 23) {
			if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
				requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
			}
			else {
				initializeLogic();
			}
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		vs_main = (ScrollView) findViewById(R.id.vs_main);
		l_main = (LinearLayout) findViewById(R.id.l_main);
		l_suggestion = (LinearLayout) findViewById(R.id.l_suggestion);
		l_factors = (LinearLayout) findViewById(R.id.l_factors);
		textview1 = (TextView) findViewById(R.id.textview1);
		lv_suggestions = (ListView) findViewById(R.id.lv_suggestions);
		l_age = (LinearLayout) findViewById(R.id.l_age);
		l_smoking = (LinearLayout) findViewById(R.id.l_smoking);
		l_alcohol_high = (LinearLayout) findViewById(R.id.l_alcohol_high);
		l_alcohol_medium = (LinearLayout) findViewById(R.id.l_alcohol_medium);
		l_alcohol_light = (LinearLayout) findViewById(R.id.l_alcohol_light);
		l_sleeping = (LinearLayout) findViewById(R.id.l_sleeping);
		l_air = (LinearLayout) findViewById(R.id.l_air);
		b_result = (Button) findViewById(R.id.b_result);
		t_age = (TextView) findViewById(R.id.t_age);
		e_age_count = (EditText) findViewById(R.id.e_age_count);
		sw_smoking = (Switch) findViewById(R.id.sw_smoking);
		e_smoking_count = (EditText) findViewById(R.id.e_smoking_count);
		sw_alcohol_high = (Switch) findViewById(R.id.sw_alcohol_high);
		e_alcohol_high = (EditText) findViewById(R.id.e_alcohol_high);
		sw_alcohol_medium = (Switch) findViewById(R.id.sw_alcohol_medium);
		e_alcohol_medium = (EditText) findViewById(R.id.e_alcohol_medium);
		sw_alcohol_light = (Switch) findViewById(R.id.sw_alcohol_light);
		e_alcohol_light = (EditText) findViewById(R.id.e_alcohol_light);
		l_sleeping_start = (LinearLayout) findViewById(R.id.l_sleeping_start);
		l_sleep_finish = (LinearLayout) findViewById(R.id.l_sleep_finish);
		textview6 = (TextView) findViewById(R.id.textview6);
		e_sleeping_start = (EditText) findViewById(R.id.e_sleeping_start);
		textview7 = (TextView) findViewById(R.id.textview7);
		e_sleeping_finish = (EditText) findViewById(R.id.e_sleeping_finish);
		sw_air = (Switch) findViewById(R.id.sw_air);
		e_air = (EditText) findViewById(R.id.e_air);
		textview8 = (TextView) findViewById(R.id.textview8);
		
		b_result.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_calculate();
			}
		});
	}
	
	private void initializeLogic() {
		vs_main.setFillViewport(true);
		_initFields();
		_initEffects();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _initEffects () {
		factors.clear();
		factor = new HashMap<>();
		factor.put("_name", "Курение");
		factor.put("Артериальная гипертензия", "1.1");
		factor.put("ОНМК", "1.1");
		factor.put("Стенокардия, ИБС, инфаркт миокарда", "1.05");
		factor.put("Сердечная недостаточность", "1.01");
		factor.put("Прочие заболевания сердца", "1.001");
		factor.put("_Связанные факторы", "лёгкие, кровеносные сосуды, лимфатическая система, масса тела, возраст");
		factor.put("_recom", "исключить курение");
		factor.put("_limit", "1.0");
		factors.add(factor);
		factor = new HashMap<>();
		factor.put("_name", "Алкоголь крепкий");
		factor.put("Артериальная гипертензия", "1.01");
		factor.put("ОНМК", "1.005");
		factor.put("Стенокардия, ИБС, инфаркт миокарда", "1.005");
		factor.put("Сердечная недостаточность", "1.003");
		factor.put("Прочие заболевания сердца", "1.003");
		factor.put("_Связанные факторы", "жкт, кровеносные сосуды, лимфатическая система, масса тела, возраст");
		factor.put("_recom", "исключить крепкий алкоголь");
		factor.put("_limit", "0.5");
		factors.add(factor);
		factor = new HashMap<>();
		factor.put("_name", "Алкоголь умеренный");
		factor.put("Артериальная гипертензия", "1.01");
		factor.put("ОНМК", "1.005");
		factor.put("Стенокардия, ИБС, инфаркт миокарда", "1.005");
		factor.put("Сердечная недостаточность", "1.003");
		factor.put("Прочие заболевания сердца", "1.003");
		factor.put("_Связанные факторы", "жкт, кровеносные сосуды, лимфатическая система, масса тела, возраст");
		factor.put("_recom", "исключить умеренный алкоголь");
		factor.put("_limit", "0.5");
		factors.add(factor);
		factor = new HashMap<>();
		factor.put("_name", "Алкоголь лёгкий");
		factor.put("Артериальная гипертензия", "1.01");
		factor.put("ОНМК", "1.005");
		factor.put("Стенокардия, ИБС, инфаркт миокарда", "1.005");
		factor.put("Сердечная недостаточность", "1.003");
		factor.put("Прочие заболевания сердца", "1.003");
		factor.put("_Связанные факторы", "жкт, кровеносные сосуды, лимфатическая система, масса тела, возраст");
		factor.put("_recom", "исключить лёгкий алкоголь");
		factor.put("_limit", "0.5");
		factors.add(factor);
		factor = new HashMap<>();
		factor.put("_name", "Атмосфера");
		factor.put("Артериальная гипертензия", "1.01");
		factor.put("ОНМК", "1.005");
		factor.put("Стенокардия, ИБС, инфаркт миокарда", "1.005");
		factor.put("Сердечная недостаточность", "1.003");
		factor.put("Прочие заболевания сердца", "1.003");
		factor.put("_Связанные факторы", "лёгкие, кровеносные сосуды, лимфатическая система, масса тела, возраст");
		factor.put("_recom", "переселиться ближе к лесу");
		factor.put("_limit", "1.0");
		factors.add(factor);
		factor = new HashMap<>();
		factor.put("_name", "Сон начало");
		factor.put("Артериальная гипертензия", "0.001");
		factor.put("ОНМК", "0.001");
		factor.put("Стенокардия, ИБС, инфаркт миокарда", "0.001");
		factor.put("Сердечная недостаточность", "0.001");
		factor.put("Прочие заболевания сердца", "0.001");
		factor.put("_Связанные факторы", "нервная система, кровеносные сосуды, лимфатическая система, возраст");
		factor.put("_recom", "ложиться спать раньше");
		factor.put("_limit", "0.5");
		factors.add(factor);
		factor = new HashMap<>();
		factor.put("_name", "Сон конец");
		factor.put("Артериальная гипертензия", "0.001");
		factor.put("ОНМК", "0.001");
		factor.put("Стенокардия, ИБС, инфаркт миокарда", "0.001");
		factor.put("Сердечная недостаточность", "0.001");
		factor.put("Прочие заболевания сердца", "0.001");
		factor.put("_Связанные факторы", "нервная система, кровеносные сосуды, лимфатическая система, возраст");
		factor.put("_recom", "просыпаться позже");
		factor.put("_limit", "0.5");
		factors.add(factor);
		factor = new HashMap<>();
		factor.put("_name", "Возраст");
		factor.put("Артериальная гипертензия", "0.01");
		factor.put("ОНМК", "0.01");
		factor.put("Стенокардия, ИБС, инфаркт миокарда", "0.01");
		factor.put("Сердечная недостаточность", "0.01");
		factor.put("Прочие заболевания сердца", "0.01");
		factor.put("_Связанные факторы", "нервная система, кровеносные сосуды, лимфатическая система");
		factor.put("_recom", "");
		factor.put("_limit", "0.5");
		factors.add(factor);
	}
	
	
	public void _calculate () {
		suggestions.clear();
		r_sugg = new HashMap<>();
		r_rcmnd = new HashMap<>();
		_prepareParams();
		if (haveAllData) {
			//список заболеваний
			n = factors.size();
			for(int _repeat110 = 0; _repeat110 < (int)(factors.size()); _repeat110++) {
				n--;
				factor = factors.get((int)n);
				ApplicationUtil.getAllKeysFromMap(factor, tmp_name);
				m = tmp_name.size();
				for(int _repeat119 = 0; _repeat119 < (int)(tmp_name.size()); _repeat119++) {
					m--;
					if (!tmp_name.get((int)(m)).startsWith("_")) {
						seeks.put(tmp_name.get((int)(m)), "0");
					}
				}
			}
			ApplicationUtil.getAllKeysFromMap(seeks, seek);
			//применяем значения из формы
			_setFactor("Возраст", r_age);
			_setFactor("Курение", smoking);
			_setFactor("Алкоголь крепкий", alcohol_high);
			_setFactor("Алкоголь умеренный", alcohol_medium);
			_setFactor("Алкоголь лёгкий", alcohol_light);
			_setFactor("Атмосфера", air);
			_setFactor("Сон начало", 24 - sleeping_start);
			_setFactor("Сон конец", sleeping_finish);
			_showSuggestion();
		}
		else {
			ApplicationUtil.showMessage(getApplicationContext(), "Заполните все поля, пожалуйста!");
		}
	}
	
	
	public void _prepareParams () {
		haveAllData = true;
		if (e_age_count.getText().toString().trim().equals("")) {
			haveAllData = false;
		}
		else {
			r_age = Double.parseDouble(e_age_count.getText().toString());
		}
		if (sw_smoking.isChecked()) {
			if (e_smoking_count.getText().toString().trim().equals("")) {
				haveAllData = false;
			}
			else {
				smoking = Double.parseDouble(e_smoking_count.getText().toString());
			}
		}
		else {
			smoking = 0;
		}
		if (sw_alcohol_high.isChecked()) {
			if (e_alcohol_high.getText().toString().trim().equals("")) {
				haveAllData = false;
			}
			else {
				alcohol_high = Double.parseDouble(e_alcohol_high.getText().toString());
			}
		}
		else {
			alcohol_high = 0;
		}
		if (sw_alcohol_medium.isChecked()) {
			if (e_alcohol_medium.getText().toString().trim().equals("")) {
				haveAllData = false;
			}
			else {
				alcohol_medium = Double.parseDouble(e_alcohol_medium.getText().toString());
			}
		}
		else {
			alcohol_medium = 0;
		}
		if (sw_alcohol_light.isChecked()) {
			if (e_alcohol_light.getText().toString().trim().equals("")) {
				haveAllData = false;
			}
			else {
				alcohol_light = Double.parseDouble(e_alcohol_light.getText().toString());
			}
		}
		else {
			alcohol_light = 0;
		}
		if (sw_air.isChecked()) {
			if (e_air.getText().toString().trim().equals("")) {
				haveAllData = false;
			}
			else {
				air = Double.parseDouble(e_air.getText().toString());
			}
		}
		else {
			air = 0;
		}
		if (e_sleeping_start.getText().toString().trim().equals("")) {
			haveAllData = false;
		}
		else {
			sleeping_start = Double.parseDouble(e_sleeping_start.getText().toString());
		}
		if (e_sleeping_finish.getText().toString().trim().equals("")) {
			haveAllData = false;
		}
		else {
			sleeping_finish = Double.parseDouble(e_sleeping_finish.getText().toString());
		}
	}
	
	
	public void _initFields () {
		lv_suggestions.setVisibility(View.GONE);
		lv_suggestions.setAdapter(new Lv_suggestionsAdapter(suggestions));
		((BaseAdapter)lv_suggestions.getAdapter()).notifyDataSetChanged();
		e_age_count.setText("");
		sw_smoking.setChecked(false);
		e_smoking_count.setText("");
		sw_alcohol_high.setChecked(false);
		e_alcohol_high.setText("");
		sw_alcohol_medium.setChecked(false);
		e_alcohol_medium.setText("");
		sw_alcohol_light.setChecked(false);
		e_alcohol_light.setText("");
		sw_air.setChecked(false);
		e_air.setText("");
		e_sleeping_start.setText("");
		e_sleeping_finish.setText("");
	}
	
	
	public void _setFactor (final String _name, final double _k) {
		_findFactor(_name);
		if (n < 0) {
			ApplicationUtil.showMessage(getApplicationContext(), _name.concat(" не найден"));
		}
		else {
			//на что влияет фактор
			factor = factors.get((int)n);
			m = seek.size();
			for(int _repeat45 = 0; _repeat45 < (int)(seek.size()); _repeat45++) {
				m--;
				if (factor.containsKey(seek.get((int)(m)))) {
					//риск возникновения этого заболевания
					rez = Double.parseDouble(factor.get(seek.get((int)(m))).toString());
					rez = rez * _k;
					result = 0;
					if (r_sugg.containsKey(seek.get((int)(m)))) {
						result = Double.parseDouble(r_sugg.get(seek.get((int)(m))).toString());
					}
					result = rez + result;
					r_sugg.put(seek.get((int)(m)), String.valueOf(result));
					if (factor.containsKey("_limit")) {
						if (rez > Double.parseDouble(factor.get("_limit").toString())) {
							str = "";
							if (r_rcmnd.containsKey(seek.get((int)(m)))) {
								str = r_rcmnd.get(seek.get((int)(m))).toString();
							}
							str = factor.get("_recom").toString().concat(", ".concat(str));
							r_rcmnd.put(seek.get((int)(m)), str);
						}
					}
				}
			}
		}
	}
	
	
	public void _findFactor (final String _name) {
		n = -1;
		m = factors.size();
		for(int _repeat13 = 0; _repeat13 < (int)(factors.size()); _repeat13++) {
			m--;
			if (_name.toLowerCase().equals(factors.get((int)m).get("_name").toString().toLowerCase())) {
				n = m;
				break;
			}
		}
	}
	
	
	public void _showSuggestion () {
		ApplicationUtil.getAllKeysFromMap(r_sugg, tmp_name);
		m = tmp_name.size();
		if (m > 0) {
			for(int _repeat16 = 0; _repeat16 < (int)(tmp_name.size()); _repeat16++) {
				m--;
				risk = new HashMap<>();
				risk.put("name", tmp_name.get((int)(m)));
				risk.put("risk", r_sugg.get(tmp_name.get((int)(m))).toString());
				if (r_rcmnd.containsKey(tmp_name.get((int)(m)))) {
					risk.put("recom", r_rcmnd.get(tmp_name.get((int)(m))).toString());
				}
				suggestions.add(risk);
			}
			((BaseAdapter)lv_suggestions.getAdapter()).notifyDataSetChanged();
			lv_suggestions.setVisibility(View.VISIBLE);
		}
		else {
			ApplicationUtil.showMessage(getApplicationContext(), "Не определён риск возникновения какого-либо заболевания сердца.");
		}
	}
	
	
	public class Lv_suggestionsAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Lv_suggestionsAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.suggest, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final LinearLayout l_seek = (LinearLayout) _view.findViewById(R.id.l_seek);
			final LinearLayout l_risk = (LinearLayout) _view.findViewById(R.id.l_risk);
			final TextView textview3 = (TextView) _view.findViewById(R.id.textview3);
			final TextView t_seek = (TextView) _view.findViewById(R.id.t_seek);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
			final TextView t_risk = (TextView) _view.findViewById(R.id.t_risk);
			final LinearLayout l_sugg = (LinearLayout) _view.findViewById(R.id.l_sugg);
			final TextView textview4 = (TextView) _view.findViewById(R.id.textview4);
			final TextView t_recom = (TextView) _view.findViewById(R.id.t_recom);
			
			if (_data.size() > 0) {
				t_seek.setText("");
				if (_data.get((int)_position).containsKey("name")) {
					t_seek.setText(_data.get((int)_position).get("name").toString());
				}
				t_risk.setText("");
				if (_data.get((int)_position).containsKey("risk")) {
					t_risk.setText(_data.get((int)_position).get("risk").toString());
				}
				t_recom.setText("");
				if (_data.get((int)_position).containsKey("recom")) {
					t_recom.setText(_data.get((int)_position).get("recom").toString());
				}
			}
			
			return _view;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
