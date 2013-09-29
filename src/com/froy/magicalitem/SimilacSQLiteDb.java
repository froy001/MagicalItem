//package com.abbott.gini_apps.data;
//
//import java.io.ByteArrayOutputStream;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.util.Log;
//
//import com.abbott.gini_apps.IDataProvider;
//import com.abbott.gini_apps.PersonalDetails;
//import com.abbott.gini_apps.PersonalDetails.FeedingOption;
//import com.abbott.gini_apps.R;
//import com.abbott.gini_apps.data.BreastFeedEntry.EstartSide;
//import com.abbott.gini_apps.data.DiaperChangeEntry.EdiaperType;
//import com.abbott.gini_apps.data.SleepEntry.EtimeOfDay;
//import com.libs.gini_apps.Logger;
//
//public class SimilacSQLiteDb implements IDataProvider {
//	private final String LOG_TAG = "SimilacSQLiteDb";
//
//	private final SimilacSQLiteDbHelper mDb;
//
//	private static SimilacSQLiteDb sInstance = null;
//	private Context mContext;
//
//	public static SimilacSQLiteDb getInstance(Context context) {
//		if (sInstance == null) {
//			sInstance = new SimilacSQLiteDb(context);
//		}
//		return sInstance;
//	}
//
//	private SimilacSQLiteDb(Context context) {
//		mDb = new SimilacSQLiteDbHelper(context);
//		mContext = context;
//	}
//
//	private String getDbDateAsString(Date in) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat(
//				"yyyy-MM-dd HH:mm:ss");
//		return dateFormat.format(in);
//	}
//
//	@Override
//	public void insertBaby(Baby baby) {
//		ContentValues cv = fillContentValue(baby);
//
//		SQLiteDatabase db = mDb.getWritableDatabase();
//		long res = db.insert("tblBaby", null, cv);
//		if (res == -1) {
//			Log.e(LOG_TAG, "Error in insertBaby()");
//			// TODO: throw exception
//		} else {
//			// get the newly inserted record to the class
//			Cursor c = mDb.getRecordByRowId("tblBaby", res);
//			if (c.moveToFirst()) {
//				fillFromCursor(c, baby);
//			} else {
//				// TODO: throw exception
//			}
//		}
//		Log.d(LOG_TAG,
//				"insertBaby: " + baby.getFirstName() + " id: " + baby.getId());
//	}
//
//	@Override
//	public void setPersonalDetails(PersonalDetails pd) {
//		// tblUser
//		ContentValues cv = new ContentValues();
//		cv.put("FullName", pd.getmUserName());
//		cv.put("FeedOptionId", pd.getFeedOption().getCode());
//		mDb.updateRecord("tblUser", cv);
//
//	}
//
//	@Override
//	public String getSetting(String name) {
//		Cursor c = mDb.getRecordsByKey("tblSettings", "Name", name);
//		String ret = null;
//		if (c.moveToFirst()) {
//			ret = c.getString(c.getColumnIndexOrThrow("Value"));
//
//		} else {
//			// TODO: throw excpetion
//		}
//		return ret;
//	}
//
//	@Override
//	public void updateSetting(String settingName, String settingValue) {
//		ContentValues cv = new ContentValues();
//		cv.put("Value", settingValue);
//
//		mDb.updateRecordByKey("tblSetting", cv, settingName, settingValue);
//
//	}
//
//	@Override
//	public PersonalDetails getPersonalDetails() {
//		String name = null;
//		FeedingOption feedingOption = FeedingOption.Breastfeed;
//		Date dayStart = null;
//		Date nightStart = null;
//		Cursor c = mDb.getAllRecordsFromTable("tblUser", false);
//		if (c.moveToFirst()) {
//			name = c.getString(c.getColumnIndexOrThrow("FullName"));
//			int option = c.getInt(c.getColumnIndexOrThrow("FeedOptionId"));
//			feedingOption = FeedingOption.fromInt(option);
//		} else {
//			// TODO: throw expetion
//		}
//
//		// get settings day start
//		String dayStartStr = getSetting("DayStartTime");
//		String nightStartStr = getSetting("NightStartTime");
//		dayStart = getDateFromHoursString(dayStartStr);
//		nightStart = getDateFromHoursString(nightStartStr);
//		PersonalDetails pd = new PersonalDetails(name, feedingOption, dayStart,
//				nightStart);
//		return pd;
//	}
//
//	private Date getDateFromCursor(Cursor c, String fieldName) {
//		String dateString = c.getString(c.getColumnIndexOrThrow(fieldName));
//		Date ret = null;
//		if (dateString != null) {
//			try {
//				ret = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//						.parse(dateString);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}
//		return ret;
//	}
//
//	private Date getDateFromHoursString(String strHour) {
//		Date ret = null;
//		try {
//			ret = new SimpleDateFormat("HH:mm").parse(strHour);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return ret;
//	}
//
//	private void fillFromCursor(Cursor c, Baby baby) {
//		baby.setId(c.getInt(c.getColumnIndexOrThrow("ID")));
//		baby.setFirstName(c.getString(c.getColumnIndexOrThrow("FirstName")));
//		baby.setLastName(c.getString(c.getColumnIndexOrThrow("LastName")));
//		boolean isFemale = c.getInt(c.getColumnIndexOrThrow("IsFemale")) != 0;
//		baby.setIsFemale(isFemale);
//		baby.setBirthdate(getDateFromCursor(c, "Birthdate"));
//		byte[] byteArray = c.getBlob(c.getColumnIndexOrThrow("PhotoData"));
//		if (byteArray != null) {
//			Bitmap b = BitmapFactory.decodeByteArray(byteArray, 0,
//					byteArray.length);
//			if (b != null) {
//				baby.setPhoto(b);
//			}
//		}
//		baby.setCreateDate(getDateFromCursor(c, "CreateDate"));
//		baby.setUpdateDate(getDateFromCursor(c, "UpdateDate"));
//	}
//
//	private ContentValues fillContentValue(Baby baby) {
//		ContentValues ret = new ContentValues();
//		// No need to update the ID, should never occur
//		// ret.put("ID", baby.getId());
//		ret.put("FirstName", baby.getFirstName());
//		ret.put("LastName", baby.getLastName());
//		ret.put("IsFemale", baby.isFemale());
//		ret.put("Birthdate", getDbDateAsString(baby.getBirthdate()));
//		Bitmap bmp = baby.getPhoto();
//		if (bmp != null) {
//			ByteArrayOutputStream stream = new ByteArrayOutputStream();
//			bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
//			byte[] byteArray = stream.toByteArray();
//			ret.put("PhotoData", byteArray);
//		}
//
//		fillDbRecordCommonData(ret, baby);
//		return ret;
//	}
//
//	private void fillDbRecordCommonData(ContentValues cv, DbRecordBase record) {
//		// generic
//		// Create date should never change
//		// cv.put("CreateDate", record.getCreateDate());
//		cv.put("UpdateDate",
//				getDbDateAsString(Calendar.getInstance().getTime()));
//		cv.put("IsActive", record.isActive());
//	}
//
//	@Override
//	public void updateBaby(Baby baby) {
//		ContentValues cv = fillContentValue(baby);
//		int ret = mDb.updateRecord("tblBaby", cv, baby.getId());
//	}
//
//	@Override
//	public void deleteBaby(Baby baby) {
//		baby.deactivate();
//		updateBaby(baby);
//	}
//
//	@Override
//	public Baby getBaby(int babyId) {
//		Baby baby = null;
//
//		Cursor c = mDb.getRecordById("tblBaby", babyId);
//		if (c.moveToFirst()) {
//			baby = new Baby();
//			fillFromCursor(c, baby);
//		} else {
//			// TODO: throw exception
//		}
//		return baby;
//	}
//
//	@Override
//	public int getChildCount() {
//		// TODO: send a count query
//		Cursor c = mDb.getAllRecordsFromTable("tblBaby", true);
//		if (c.moveToFirst()) {
//			return c.getCount();
//		} else {
//			return 0;
//		}
//	}
//
//	private void fillFromCursor(Cursor c, BreastFeedEntry entry) {
//		fillBabyRefRecodFromCursor(c, entry);
//
//		entry.setStartTime(getDateFromCursor(c, "StartDate"));
//		entry.setDuration(c.getInt(c.getColumnIndexOrThrow("Duration")));
//		entry.setStatus(c.getInt(c.getColumnIndexOrThrow("Status")));
//		int side = c.getInt(c.getColumnIndexOrThrow("StartSide"));
//		entry.setStartSide(side == 1 ? BreastFeedEntry.EstartSide.Left
//				: BreastFeedEntry.EstartSide.Right);
//		entry.setEndTime(getDateFromCursor(c, "EndDate"));
//		boolean bothSides = c.getInt(c
//				.getColumnIndexOrThrow("DidFeedOnBothSides")) != 0;
//		entry.setFedOnBothSides(bothSides);
//	}
//
//	private void fillFromCursor(Cursor c, BottleFeedEntry entry) {
//		fillBabyRefRecodFromCursor(c, entry);
//
//		entry.setStartTime(getDateFromCursor(c, "StartDate"));
//		entry.setDuration(c.getInt(c.getColumnIndexOrThrow("Duration")));
//		entry.setAmount(c.getInt(c.getColumnIndexOrThrow("Amount")));
//		entry.setStatus(c.getInt(c.getColumnIndexOrThrow("Status")));
//		entry.setEndTime(getDateFromCursor(c, "EndDate"));
//	}
//
//	private void fillFromCursor(Cursor c, SleepEntry entry) {
//		fillBabyRefRecodFromCursor(c, entry);
//
//		entry.setStartTime(getDateFromCursor(c, "StartDate"));
//		entry.setDuration(c.getInt(c.getColumnIndexOrThrow("Duration")));
//		entry.setStatus(c.getInt(c.getColumnIndexOrThrow("Status")));
//		int timeOfDay = c.getInt(c.getColumnIndexOrThrow("TimeOfDay"));
//		entry.setTimeOfDay(timeOfDay == 1 ? EtimeOfDay.DayTime
//				: EtimeOfDay.NightTime);
//		boolean laidAwake = c.getInt(c.getColumnIndexOrThrow("LaidDownAwake")) != 0;
//		entry.setLaidAwake(laidAwake);
//		entry.setNote(c.getString(c.getColumnIndexOrThrow("Note")));
//		entry.setEndTime(getDateFromCursor(c, "EndDate"));
//	}
//
//	private void fillFromCursor(Cursor c, PumpingEntry entry) {
//		fillBabyRefRecodFromCursor(c, entry);
//
//		entry.setStartTime(getDateFromCursor(c, "StartDate"));
//		entry.setDuration(c.getInt(c.getColumnIndexOrThrow("Duration")));
//		entry.setStatus(c.getInt(c.getColumnIndexOrThrow("Status")));
//		entry.setAmount(c.getInt(c.getColumnIndexOrThrow("Amount")));
//		int side = c.getInt(c.getColumnIndexOrThrow("Side"));
//		entry.setStartSide(side == 1 ? BreastFeedEntry.EstartSide.Left
//				: BreastFeedEntry.EstartSide.Right);
//		entry.setEndTime(getDateFromCursor(c, "EndDate"));
//		boolean bothSides = c.getInt(c
//				.getColumnIndexOrThrow("DidFeedOnBothSides")) != 0;
//		entry.setFedOnBothSides(bothSides);
//	}
//
//	private void fillBabyRefRecodFromCursor(Cursor c, BabyRefRecord r) {
//		r.setId(c.getInt(c.getColumnIndexOrThrow("ID")));
//		r.setCreateDate(getDateFromCursor(c, "CreateDate"));
//		r.setUpdateDate(getDateFromCursor(c, "UpdateDate"));
//		r.setBabyId(c.getInt(c.getColumnIndexOrThrow("BabyRef")));
//	}
//
//	private ContentValues getBabyRefContentValues(BabyRefRecord r) {
//		ContentValues cv = new ContentValues();
//		cv.put("CreateDate", getDbDateAsString(r.getCreateDate()));
//		cv.put("UpdateDate", getDbDateAsString(r.getUpdateDate()));
//		cv.put("BabyRef", r.getBabyId());
//		Logger.d(LOG_TAG, "BabyRef: " + r.getBabyId());
//
//		return cv;
//	}
//
//	@Override
//	public List<BreastFeedEntry> getBreastFeedEntries(int babyId, int status) {
//		List<BreastFeedEntry> result = new ArrayList<BreastFeedEntry>();
//
//		Cursor c = mDb.getAllRecordsFromTable("tblBreastfeed", babyId, true,
//				status);
//		if (c.moveToFirst()) {
//			while (!c.isAfterLast()) {
//				BreastFeedEntry e = new BreastFeedEntry();
//				fillFromCursor(c, e);
//				result.add(e);
//				c.moveToNext();
//			}
//		}
//		return result;
//	}
//
//	private ContentValues getContentValues(BreastFeedEntry entry) {
//		ContentValues cv = getBabyRefContentValues(entry);
//
//		cv.put("StartDate", getDbDateAsString(entry.getStartTime()));
//		cv.put("Status", entry.getStatus());
//		cv.put("Duration", entry.getDuration());
//		cv.put("StartSide", entry.getStartSide() == EstartSide.Left ? 1 : 2);
//		if (entry.getEndTime() != null) {
//			cv.put("EndDate", getDbDateAsString(entry.getEndTime()));
//		}
//		cv.put("DidFeedOnBothSides", entry.isFedOnBothSides());
//		return cv;
//	}
//
//	private ContentValues getContentValues(BottleFeedEntry entry) {
//		ContentValues cv = getBabyRefContentValues(entry);
//
//		cv.put("StartDate", getDbDateAsString(entry.getStartTime()));
//		cv.put("Status", entry.getStatus());
//		cv.put("Duration", entry.getDuration());
//		cv.put("Amount", entry.getAmount());
//		Logger.d(this.toString(), "Amount: " + entry.getAmount());
//		if (entry.getEndTime() != null) {
//			cv.put("EndDate", getDbDateAsString(entry.getEndTime()));
//		}
//
//		return cv;
//	}
//
//	private ContentValues getContentValues(SleepEntry entry) {
//		ContentValues cv = getBabyRefContentValues(entry);
//
//		cv.put("StartDate", getDbDateAsString(entry.getStartTime()));
//		cv.put("Duration", entry.getDuration());
//		cv.put("Status", entry.getStatus());
//		cv.put("Note", entry.getNote());
//		cv.put("TimeOfDay", entry.getTimeOfDay() == EtimeOfDay.DayTime ? 1 : 2);
//		cv.put("LaidDownAwake", entry.isLaidAwake());
//		if (entry.getEndTime() != null) {
//			cv.put("EndDate", getDbDateAsString(entry.getEndTime()));
//		}
//
//		return cv;
//	}
//
//	private ContentValues getContentValues(PumpingEntry entry) {
//		ContentValues cv = getBabyRefContentValues(entry);
//
//		cv.put("StartDate", getDbDateAsString(entry.getStartTime()));
//		cv.put("Amount", entry.getAmount());
//		cv.put("Status", entry.getStatus());
//		cv.put("Duration", entry.getDuration());
//		cv.put("Side", entry.getStartSide() == EstartSide.Left ? 1 : 2);
//		if (entry.getEndTime() != null) {
//			cv.put("EndDate", getDbDateAsString(entry.getEndTime()));
//		}
//		cv.put("DidFeedOnBothSides", entry.isFedOnBothSides());
//		return cv;
//	}
//
//	@Override
//	public void updateBreastFeedEntry(BreastFeedEntry entry) {
//		ContentValues cv = getContentValues(entry);
//		int ret = mDb.updateRecord("tblBreastfeed", cv, entry.getId());
//		if (ret == 0) {
//			// TODO: throw expection
//		}
//	}
//
//	@Override
//	public BreastFeedEntry getBreastFeedEntry(int entryId) {
//		BreastFeedEntry entry = null;
//		Cursor c = mDb.getRecordById("tblBreastfeed", entryId);
//		if (c.moveToFirst()) {
//			entry = new BreastFeedEntry();
//			fillFromCursor(c, entry);
//		} else {
//			// TODO: throw exception
//		}
//
//		return entry;
//	}
//
//	@Override
//	public void deleteBreastFeedEntry(BreastFeedEntry entry) {
//		entry.deactivate();
//		updateBreastFeedEntry(entry);
//
//	}
//
//	@Override
//	public List<PumpingEntry> getPumpingEntries(int babyId, int status) {
//		List<PumpingEntry> result = new ArrayList<PumpingEntry>();
//
//		Cursor c = mDb.getAllRecordsFromTable("tblPumping", babyId, true,
//				status);
//		if (c.moveToFirst()) {
//			while (!c.isAfterLast()) {
//				PumpingEntry e = new PumpingEntry();
//				fillFromCursor(c, e);
//				result.add(e);
//				c.moveToNext();
//			}
//		}
//		return result;
//	}
//
//	@Override
//	public void insertBreastFeedEntry(BreastFeedEntry entry) {
//		ContentValues cv = getContentValues(entry);
//		SQLiteDatabase db = mDb.getWritableDatabase();
//		long res = db.insert("tblBreastfeed", null, cv);
//		if (res == -1) {
//			Log.e(LOG_TAG, "Error in insertBreastFeedEntry()");
//			// TODO: throw exception
//		} else {
//			// get the newly inserted record to the class
//			Cursor c = mDb.getRecordByRowId("tblBreastfeed", res);
//			if (c.moveToFirst()) {
//				fillFromCursor(c, entry);
//			} else {
//				// TODO: throw exception
//			}
//		}
//
//	}
//
//	@Override
//	public void insertPumpingEntry(PumpingEntry entry) {
//		ContentValues cv = getContentValues(entry);
//		SQLiteDatabase db = mDb.getWritableDatabase();
//		long res = db.insert("tblPumping", null, cv);
//		if (res == -1) {
//			Log.e(LOG_TAG, "Error in insertPumpingEntry()");
//			// TODO: throw exception
//		} else {
//			// get the newly inserted record to the class
//			Cursor c = mDb.getRecordByRowId("tblPumping", res);
//			if (c.moveToFirst()) {
//				fillFromCursor(c, entry);
//			} else {
//				// TODO: throw exception
//			}
//		}
//	}
//
//	@Override
//	public void updatePumpingEntry(PumpingEntry entry) {
//		ContentValues cv = getContentValues(entry);
//		int ret = mDb.updateRecord("tblPumping", cv, entry.getId());
//		if (ret == 0) {
//			// TODO: throw expection
//		}
//
//	}
//
//	@Override
//	public void deletePumpingEntry(PumpingEntry entry) {
//		entry.deactivate();
//		updatePumpingEntry(entry);
//
//	}
//
//	@Override
//	public List<BottleFeedEntry> getBottleFeedEntries(int babyId, int status) {
//
//		List<BottleFeedEntry> result = new ArrayList<BottleFeedEntry>();
//		Cursor c = mDb.getAllRecordsFromTable("tblBottleFed", babyId, true,
//				status);
//		if (c.moveToFirst()) {
//			while (!c.isAfterLast()) {
//				BottleFeedEntry e = new BottleFeedEntry();
//				fillFromCursor(c, e);
//				result.add(e);
//				c.moveToNext();
//			}
//		}
//		return result;
//	}
//
//	@Override
//	public BottleFeedEntry getBottleFeedEntry(int entryId) {
//		BottleFeedEntry entry = null;
//		Cursor c = mDb.getRecordById("tblBottleFed", entryId);
//		if (c.moveToFirst()) {
//			entry = new BottleFeedEntry();
//			fillFromCursor(c, entry);
//		} else {
//			// TODO: throw exception
//		}
//
//		return entry;
//	}
//
//	@Override
//	public void insertBottleFeedEntry(BottleFeedEntry entry) {
//		ContentValues cv = getContentValues(entry);
//		SQLiteDatabase db = mDb.getWritableDatabase();
//		long res = db.insert("tblBottleFed", null, cv);
//		if (res == -1) {
//			Log.e(LOG_TAG, "Error in tblBottleFed()");
//			// TODO: throw exception
//		} else {
//			// get the newly inserted record to the class
//			Cursor c = mDb.getRecordByRowId("tblBottleFed", res);
//			if (c.moveToFirst()) {
//				fillFromCursor(c, entry);
//			} else {
//				// TODO: throw exception
//			}
//		}
//	}
//
//	@Override
//	public void updateBottleFeedEntry(BottleFeedEntry entry) {
//		ContentValues cv = getContentValues(entry);
//		int ret = mDb.updateRecord("tblBottleFed", cv, entry.getId());
//		if (ret == 0) {
//			// TODO: throw expection
//		}
//	}
//
//	@Override
//	public void deleteBottleFeedEntry(BottleFeedEntry entry) {
//		entry.deactivate();
//		updateBottleFeedEntry(entry);
//
//	}
//
//	@Override
//	public List<Baby> getBabies() {
//		List<Baby> result = new ArrayList<Baby>();
//		Cursor c = mDb.getAllRecordsFromTable("tblBaby", true);
//		if (c.moveToFirst()) {
//			while (!c.isAfterLast()) {
//				Baby b = new Baby();
//				fillFromCursor(c, b);
//				result.add(b);
//				c.moveToNext();
//			}
//		}
//		return result;
//	}
//
//	@Override
//	public PumpingEntry getPumpingEntry(int entryId) {
//		PumpingEntry entry = null;
//		Cursor c = mDb.getRecordById("tblPumping", entryId);
//		if (c.moveToFirst()) {
//			entry = new PumpingEntry();
//			fillFromCursor(c, entry);
//		} else {
//			// TODO: throw exception
//		}
//
//		return entry;
//	}
//
//	@Override
//	public EstartSide getLastUsedSide() {
//		Cursor c = mDb.rawSql(mContext
//				.getString(R.string.db_get_last_used_side));
//		int side = 0;
//		if (c.moveToFirst()) {
//			side = c.getInt(0);
//		}
//
//		EstartSide ret = null;
//
//		if (side == 1) {
//			ret = EstartSide.Left;
//		} else if (side == 2) {
//			ret = EstartSide.Right;
//		}
//		return ret;
//	}
//
//	@Override
//	public List<SleepEntry> getSleepEntries(int babyId, int status) {
//		List<SleepEntry> result = new ArrayList<SleepEntry>();
//		Cursor c = mDb.getAllRecordsFromTable("tblSleep", babyId, true, status);
//		if (c.moveToFirst()) {
//			while (!c.isAfterLast()) {
//				SleepEntry e = new SleepEntry();
//				fillFromCursor(c, e);
//				result.add(e);
//				c.moveToNext();
//			}
//		}
//		return result;
//	}
//
//	@Override
//	public void insertSleepEntry(SleepEntry entry) {
//		ContentValues cv = getContentValues(entry);
//		SQLiteDatabase db = mDb.getWritableDatabase();
//		long res = db.insert("tblSleep", null, cv);
//		if (res == -1) {
//			Log.e(LOG_TAG, "Error in tblSleep()");
//			// TODO: throw exception
//		} else {
//			// get the newly inserted record to the class
//			Cursor c = mDb.getRecordByRowId("tblSleep", res);
//			if (c.moveToFirst()) {
//				fillFromCursor(c, entry);
//				Log.d(LOG_TAG, "insert record into tblSleep()");
//			} else {
//				// TODO: throw exception
//			}
//		}
//	}
//
//	@Override
//	public void updateSleepEntry(SleepEntry entry) {
//		ContentValues cv = getContentValues(entry);
//		int ret = mDb.updateRecord("tblSleep", cv, entry.getId());
//		if (ret == 0) {
//			// TODO: throw expection
//		}
//
//	}
//
//	@Override
//	public void deleteSleepEntry(SleepEntry entry) {
//		entry.deactivate();
//		updateSleepEntry(entry);
//
//	}
//
//	@Override
//	public List<DiaperChangeEntry> getDiaperChangeEntries(int babyId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Date getLastFeedTime(int babyId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Date getLastSleepTime(int babyId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public double getBabyWeight(int babyId) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public Date getBabyBirthdate(int babyId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getBabyName(int babyId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public SleepEntry getSleepEntry(int entryId) {
//		SleepEntry entry = null;
//		Cursor c = mDb.getRecordById("tblSleep", entryId);
//		if (c.moveToFirst()) {
//			entry = new SleepEntry();
//			fillFromCursor(c, entry);
//		} else {
//			// TODO: throw exception
//		}
//
//		return entry;
//	}
//
//	@Override
//	public List<GrowthEntry> getGrowthEntries(int babyId, int status) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void insertGrowthEntry(GrowthEntry growthEntry) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void updateGrowthEntry(GrowthEntry growthEntry) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void deleteGrowthEntry(GrowthEntry growthEntry) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void insertDiaperChangeEntry(DiaperChangeEntry diaperChangeEntry) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void updateDiaperChangeEntry(DiaperChangeEntry diaperChangeEntry) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void deleteDiaperChangeEntry(DiaperChangeEntry diaperChangeEntry) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public DiaperChangeEntry getDiaperChangeEntry(int entryId) {
//		// TODO Auto-generated method stub
//		return new DiaperChangeEntry(0, new Date(), 3, 0, EdiaperType.Both,
//				false, LOG_TAG);
//	}
//
//}
