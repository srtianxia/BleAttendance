package com.srtianxia.bleattendance.utils.database;

import com.srtianxia.bleattendance.entity.Course;
import com.srtianxia.bleattendance.entity.NewCourseEntity;
import com.srtianxia.bleattendance.entity.TeaCourse;
import com.srtianxia.bleattendance.entity.TeaCourseEntity;
import com.srtianxia.bleattendance.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by 梅梅 on 2017/1/30.
 */
public class DataBaseManager {

    private final String TAG = "DataBaseManager";

    private Realm realm;

    private static DataBaseManager dataBaseManager;

    public DataBaseManager() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);
    }

    public static DataBaseManager getInstance() {
        if (dataBaseManager == null) {
            dataBaseManager = new DataBaseManager();
        }
        return dataBaseManager;
    }

    public void addTeaCourse(TeaCourseEntity teaCourseEntity, int week) {

        if (teaCourseEntity != null) {

            DBTeaCourseEntity teaCourses = new DBTeaCourseEntity();
            teaCourses.data = new RealmList<>();
            teaCourses.id = week;
            teaCourses.status = teaCourseEntity.status;
            teaCourses.message = teaCourseEntity.message;
            teaCourses.version = teaCourseEntity.version;

            for (int i = 0; i < teaCourseEntity.data.size(); i++) {
                DBTeaCourse tempTeaCourse = new DBTeaCourse();
                tempTeaCourse.trid = teaCourseEntity.data.get(i).trid;
                tempTeaCourse.scNum = teaCourseEntity.data.get(i).scNum;
                tempTeaCourse.jxbID = teaCourseEntity.data.get(i).jxbID;
                tempTeaCourse.hash_day = teaCourseEntity.data.get(i).hash_day;
                tempTeaCourse.hash_lesson = teaCourseEntity.data.get(i).hash_lesson;
                tempTeaCourse.begin_lesson = teaCourseEntity.data.get(i).begin_lesson;
                tempTeaCourse.day = teaCourseEntity.data.get(i).day;
                tempTeaCourse.lesson = teaCourseEntity.data.get(i).lesson;
                tempTeaCourse.course = teaCourseEntity.data.get(i).course;
                tempTeaCourse.teacher = teaCourseEntity.data.get(i).teacher;
                tempTeaCourse.type = teaCourseEntity.data.get(i).type;
                tempTeaCourse.classroom = teaCourseEntity.data.get(i).classroom;
                tempTeaCourse.rawWeek = teaCourseEntity.data.get(i).rawWeek;
                tempTeaCourse.period = teaCourseEntity.data.get(i).period;
                tempTeaCourse.week = new RealmList<>();
                if (teaCourseEntity.data.get(i).week != null) {
                    for (int j = 0; j < teaCourseEntity.data.get(i).week.size(); j++) {
                        tempTeaCourse.week.add(new RealmInteger(teaCourseEntity.data.get(i).week.get(j)));
                    }
                }

                teaCourses.data.add(tempTeaCourse);
            }

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    // todo: 没有判断存储是否成功

                    HashSet<String> isTeaCourse = new HashSet<String>(PreferenceManager.getInstance().getHashSet(PreferenceManager.SP_IS_TEA_COURSE));

                    isTeaCourse.add(String.valueOf(week));
                    PreferenceManager.getInstance().setHashSet(PreferenceManager.SP_IS_TEA_COURSE,isTeaCourse);

                    //DBTeaCourseEntity teaCourses = realm.createObject(DBTeaCourseEntity.class,week);
                    realm.copyToRealmOrUpdate(teaCourses);

                }
            });
        }

    }

    public void addStuCourse(NewCourseEntity stuCourseEntity, int week) {

        if (stuCourseEntity != null) {

            DBStuCourseEntity stuCourses = new DBStuCourseEntity();
            stuCourses.id = week;
            stuCourses.status = stuCourseEntity.status;
            stuCourses.version = stuCourseEntity.version;
            stuCourses.term = stuCourseEntity.term;
            stuCourses.stuNum = stuCourseEntity.stuNum;
            stuCourses.data = new RealmList<>();

            for (int i = 0; i < stuCourseEntity.data.size(); i++) {
                DBStuCourse tempStuCourse = new DBStuCourse();
                tempStuCourse.hash_day = stuCourseEntity.data.get(i).hash_day;
                tempStuCourse.hash_lesson = stuCourseEntity.data.get(i).hash_lesson;
                tempStuCourse.begin_lesson = stuCourseEntity.data.get(i).begin_lesson;
                tempStuCourse.day = stuCourseEntity.data.get(i).day;
                tempStuCourse.lesson = stuCourseEntity.data.get(i).lesson;
                tempStuCourse.course = stuCourseEntity.data.get(i).course;
                tempStuCourse.teacher = stuCourseEntity.data.get(i).teacher;
                tempStuCourse.classroom = stuCourseEntity.data.get(i).classroom;
                tempStuCourse.rawWeek = stuCourseEntity.data.get(i).rawWeek;
                tempStuCourse.weekModel = stuCourseEntity.data.get(i).weekModel;
                tempStuCourse.weekBegin = stuCourseEntity.data.get(i).weekBegin;
                tempStuCourse.weekEnd = stuCourseEntity.data.get(i).weekEnd;
                tempStuCourse.type = stuCourseEntity.data.get(i).type;
                tempStuCourse.period = stuCourseEntity.data.get(i).period;
                tempStuCourse.id = stuCourseEntity.data.get(i).id;
                tempStuCourse.week = new RealmList<>();
                if (stuCourseEntity.data.get(i).week != null) {
                    for (int j = 0; j < stuCourseEntity.data.get(i).week.size(); j++) {
                        tempStuCourse.week.add(new RealmInteger(stuCourseEntity.data.get(i).week.get(j)));
                    }
                }

                stuCourses.data.add(tempStuCourse);
            }

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    // todo: 没有判断存储是否成功

                    HashSet<String> isStuCourse = new HashSet<String>(PreferenceManager.getInstance().getHashSet(PreferenceManager.SP_IS_STU_COURSE));

                    isStuCourse.add(String.valueOf(week));
                    PreferenceManager.getInstance().setHashSet(PreferenceManager.SP_IS_STU_COURSE,isStuCourse);

                    realm.copyToRealmOrUpdate(stuCourses);
                }
            });
        }
    }

    public void queryTeaCourse(int week, OnQueryTeaSuccessListener listener) {

        TeaCourseEntity teaCourse = new TeaCourseEntity();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                DBTeaCourseEntity teaCourseEntity = realm.where(DBTeaCourseEntity.class)
                        .equalTo("id", week).findFirst();

                if (teaCourseEntity != null) {

                    teaCourse.status = teaCourseEntity.status;
                    teaCourse.message = teaCourseEntity.message;
                    teaCourse.version = teaCourseEntity.version;
                    teaCourse.data = new ArrayList<>();

                    for (int i = 0; i < teaCourseEntity.data.size(); i++) {
                        TeaCourse tempCourse = new TeaCourse();
                        tempCourse.trid = teaCourseEntity.data.get(i).trid;
                        tempCourse.scNum = teaCourseEntity.data.get(i).scNum;
                        tempCourse.jxbID = teaCourseEntity.data.get(i).jxbID;
                        tempCourse.hash_day = teaCourseEntity.data.get(i).hash_day;
                        tempCourse.hash_lesson = teaCourseEntity.data.get(i).hash_lesson;
                        tempCourse.begin_lesson = teaCourseEntity.data.get(i).begin_lesson;
                        tempCourse.day = teaCourseEntity.data.get(i).day;
                        tempCourse.lesson = teaCourseEntity.data.get(i).lesson;
                        tempCourse.course = teaCourseEntity.data.get(i).course;
                        tempCourse.teacher = teaCourseEntity.data.get(i).teacher;
                        tempCourse.type = teaCourseEntity.data.get(i).type;
                        tempCourse.classroom = teaCourseEntity.data.get(i).classroom;
                        tempCourse.rawWeek = teaCourseEntity.data.get(i).rawWeek;
                        tempCourse.period = teaCourseEntity.data.get(i).period;
                        tempCourse.week = new ArrayList<>();
                        if (teaCourseEntity.data.get(i).week != null) {
                            for (int j = 0; j < teaCourseEntity.data.get(i).week.size(); j++) {
                                tempCourse.week.add(teaCourseEntity.data.get(i).week.get(j).wek);
                            }
                        }

                        teaCourse.data.add(tempCourse);
                    }
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.onSuccess(teaCourse);
            }

        });

    }

    public void queryStuCourse(int week,OnQueryStuSuccessListener listener) {

        NewCourseEntity stuCourse = new NewCourseEntity();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                DBStuCourseEntity stuCourseEntity = realm.where(DBStuCourseEntity.class)
                        .equalTo("id", week).findFirst();

                stuCourse.status = stuCourseEntity.status;
                stuCourse.version = stuCourseEntity.version;
                stuCourse.term = stuCourseEntity.term;
                stuCourse.stuNum = stuCourseEntity.stuNum;
                stuCourse.data = new ArrayList<>();

                for (int i = 0; i < stuCourseEntity.data.size(); i++) {
                    Course tempCourse = new Course();

                    tempCourse.hash_day = stuCourseEntity.data.get(i).hash_day;
                    tempCourse.hash_lesson = stuCourseEntity.data.get(i).hash_lesson;
                    tempCourse.begin_lesson = stuCourseEntity.data.get(i).begin_lesson;
                    tempCourse.day = stuCourseEntity.data.get(i).day;
                    tempCourse.lesson = stuCourseEntity.data.get(i).lesson;
                    tempCourse.course = stuCourseEntity.data.get(i).course;
                    tempCourse.teacher = stuCourseEntity.data.get(i).teacher;
                    tempCourse.type = stuCourseEntity.data.get(i).type;
                    tempCourse.classroom = stuCourseEntity.data.get(i).classroom;
                    tempCourse.rawWeek = stuCourseEntity.data.get(i).rawWeek;
                    tempCourse.period = stuCourseEntity.data.get(i).period;
                    tempCourse.week = new ArrayList<>();
                    if (stuCourseEntity.data.get(i).week != null) {
                        for (int j = 0; j < stuCourseEntity.data.get(i).week.size(); j++) {
                            tempCourse.week.add(stuCourseEntity.data.get(i).week.get(j).wek);
                        }
                    }

                    stuCourse.data.add(tempCourse);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.onSuccess(stuCourse);
            }
        });

    }

    public boolean deleteTeaCourse() {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<DBTeaCourseEntity> results = realm.where(DBTeaCourseEntity.class).findAll();
                if (results.size() == 0) {
                    return;
                }


                HashSet isTeaCourse = PreferenceManager.getInstance().getHashSet(PreferenceManager.SP_IS_TEA_COURSE);
                if (isTeaCourse != null){
                    isTeaCourse.clear();
                }

                results.deleteAllFromRealm();

            }
        });
        return true;
    }

    public boolean deleteStuCourse() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<DBStuCourseEntity> results = realm.where(DBStuCourseEntity.class).findAll();
                if (results.size() == 0) {
                    return;
                }

                HashSet isStuCourse = PreferenceManager.getInstance().getHashSet(PreferenceManager.SP_IS_STU_COURSE);
                if (isStuCourse != null){
                    isStuCourse.clear();
                }

                results.deleteAllFromRealm();

            }
        });
        return true;
    }

    public boolean isTeaCourse(int week) {
        HashSet<String> isTeaCourse = PreferenceManager.getInstance().getHashSet(PreferenceManager.SP_IS_TEA_COURSE);

        return isTeaCourse.contains(String.valueOf(week));

    }

    public boolean isStuCourse(int week) {
        HashSet<String> isStuCourse = PreferenceManager.getInstance().getHashSet(PreferenceManager.SP_IS_STU_COURSE);

        return isStuCourse.contains(String.valueOf(week));
    }
}
