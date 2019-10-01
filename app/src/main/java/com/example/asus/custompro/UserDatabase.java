package com.example.asus.custompro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.example.asus.custompro.home.search.Search;
import com.example.asus.custompro.home.search.post.Post;
import com.example.asus.custompro.home.search.post.comment.PostComment;
import com.example.asus.custompro.home.search.shops.Maker;
import com.example.asus.custompro.home.search.post.bids.offer.Offer;
import com.example.asus.custompro.mynotification.Notif;
import com.example.asus.custompro.myorder.Order;
import com.example.asus.custompro.mywallet.cashin.WalletCashIn;
import com.example.asus.custompro.mywallet.payout.WalletPayout;
import com.example.asus.custompro.mywallet.walletaccount.WalletUser;
import com.example.asus.custompro.shop.nwo.NewWorldOrder;
import com.example.asus.custompro.shop.product.Product;
import com.example.asus.custompro.myrequest.Request;
import com.example.asus.custompro.shop.review.Review;

import java.util.ArrayList;

public class UserDatabase extends SQLiteOpenHelper {

    static String DATABASE ="db_cp";
    static String TBL_USER = "tbl_user";
    static String TBL_ACCOUNT = "tbl_account";
    static String TBL_PRODUCT = "tbl_product";
    static String TBL_MAKER = "tbl_maker";
    static String TBL_REVIEW = "tbl_review";
    static String TBL_POST = "tbl_post";
    static String TBL_SEARCH = "tbl_search";
    static String TBL_REQUEST = "tbl_request";
    static String TBL_POST_COMMENT = "tbl_post_comment";
    static String TBL_POST_OFFER = "tbl_post_offer";
    static String TBL_ORDER = "tbl_order";
    static String TBL_NOTIFICATION = "tbl_notification";

    static String TBL_NW_ORDER = "tbl_nwo_order";
    static String TBL_WALLET_USER = "tbl_wallet_user";
    static String TBL_WALLET_CASH_IN = "tbl_wallet_cash_in";
    static String TBL_WALLET_PAYOUT = "tbl_wallet_payout";

    public UserDatabase(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlUser = "CREATE TABlE " +TBL_USER+ "(userid integer primary key autoincrement, profilepic varchar(50), username varchar(50), password varchar(50), fname varchar(150), lname varchar(150), gender varchar(15), email varchar(50), contact varchar (11), address varchar(100), status varchar(10))";
        db.execSQL(sqlUser);
        String sqlAccount = "CREATE TABlE " +TBL_ACCOUNT+ "(accountid integer primary key autoincrement, userid integer, profilepic varchar(50), username varchar(50), password varchar(50), fname varchar(150), lname varchar(150), gender varchar(15), email varchar(50), contact varchar (11), address varchar(100), status varchar(10))";
        db.execSQL(sqlAccount);
        String sqlMaker = "CREATE TABlE " +TBL_MAKER+ "(makerid integer primary key autoincrement, userid integer, shoppic varchar(100), shopname varchar(50), shopspecialty varchar(100), shoprating float, shopcontact varchar(11), shopemail varchar(50), shopaddress varchar(100), shopsubscription varchar(20))";
        db.execSQL(sqlMaker);
        String sqlMakerProduct = "CREATE TABlE " +TBL_PRODUCT+ "(productid integer primary key autoincrement, userid integer, productpic varchar(100), productname varchar(50), productdetails varchar(100))";
        db.execSQL(sqlMakerProduct);
        String sqlReview = "CREATE TABlE " +TBL_REVIEW+ "(requestid integer primary key, userid integer, makerid integer, reviewerpic varchar(100), reviewername varchar(50), reviewdate varchar(50), rating float, comment varchar(100))";
        db.execSQL(sqlReview);
        String sqlPost = "CREATE TABlE " +TBL_POST+ "(postid integer primary key autoincrement, userid integer, userpic varchar(100), username varchar(100), postpic varchar(100), postdetails varchar(100), postcategory varchar(100), posttype varchar(50), postsize varchar(50), postqty integer, postcolor varhcar(15), postdate varchar(50), postestdate varcher(50), postoptone varchar(50), postopttwo varchar(50))";
        db.execSQL(sqlPost);
        String sqlRequest = "CREATE TABlE " +TBL_REQUEST+ "(requestid integer primary key autoincrement, userid integer, makerid integer, userpic varchar(100), requestpic varchar(100), username varchar(100), requestdetails varchar(100), requestcategory varchar(50), requesttype varchar(30), requestsize varchar(30), requestqty integer, requestcolor varchar(15), requesteddate varchar(15), requeststatus varchar(10))";
        db.execSQL(sqlRequest);
        String sqlSearch = "CREATE TABlE " +TBL_SEARCH+ "(searchid integer primary key autoincrement, userid integer, searchtype varchar(10))";
        db.execSQL(sqlSearch);
        String sqlPC = "CREATE TABlE " +TBL_POST_COMMENT+ "(commentid integer primary key autoincrement, postid integer, userid integer, commentpic varchar(50), commentname varchar(50), comment varchar(200), date varchar(15))";
        db.execSQL(sqlPC);
        String sqlPO = "CREATE TABlE " +TBL_POST_OFFER+ "(offerid integer primary key autoincrement, nwoid integer, userid integer, postid integer, shopid integer, shoppic varchar(50), shopname varchar(50), offerprice varchar(10), offerdate varchar(15), offercomment varchar(200), offerstatus varchar(20))";
        db.execSQL(sqlPO);
//        String sqlO = "CREATE TABlE " +TBL_ORDER+ "(orderid integer primary key autoincrement, userid integer, requestid integer, shopid integer, orderdate varchar(20), ordermade varchar(10), orderstatus varchar(20))";
//        db.execSQL(sqlO);
        String sqlO = "CREATE TABlE " +TBL_ORDER+ "(orderid integer primary key autoincrement, userid integer, requestid integer, shopid integer, orderdate varchar(20), ordermade varchar(10), orderstatus varchar(20))";
        db.execSQL(sqlO);
        String sqlNotif = "CREATE TABLE " +TBL_NOTIFICATION+ "(notifid integer primary key autoincrement, userid integer, shopid integer, requestid integer, date varchar(15), type varchar(20), message varchar(200))";
        db.execSQL(sqlNotif);

        String sql69 = "CREATE TABlE " +TBL_NW_ORDER+ "(nwoid integer primary key autoincrement, userid integer, makerid integer, nwoqty integer, nwopic varchar(100), nwodetails varchar(100), nwocategory varchar(50), nwotype varchar(30), nwosize varchar(30), nwocolor varchar(15), nwodate varchar(25), nwoenddate varchar(25), nwomade varchar(10), nwostatus varchar(20), postestdate varcher(50), postoptone varchar(50), postopttwo varchar(50))";
        db.execSQL(sql69);

        String sql70 = "CREATE TABlE " +TBL_WALLET_USER+ "(walletid integer primary key autoincrement, userid integer, totbalance varchar(30))";
        db.execSQL(sql70);
        String sql71 = "CREATE TABLE " +TBL_WALLET_CASH_IN+ "(cashin_id integer primary key autoincrement, cashin_balance varchar(30), userid integer, cashin_date varchar(12), cashin_time varchar(15))";
        db.execSQL(sql71);
        String sql72 = "CREATE TABLE " +TBL_WALLET_PAYOUT+ "(payout_id integer primary key autoincrement, userid integer, makerid integer, booking_id varchar(30), payout_date varchar(15), payout_fee varchar(12))";
        db.execSQL(sql72);
    }

    // WALLET USER
    public long addWalletUser(int userid, String totbalance) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result;
        ContentValues cv = new ContentValues();

        cv.put("userid", userid);
        cv.put("totbalance", totbalance);
        result = db.insert(TBL_WALLET_USER, null, cv);

        db.close();
        return result;
    }

    public boolean findWalletUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_WALLET_USER, null, null, null, null, null, "userid");
        boolean bool = false;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userid = c.getInt(c.getColumnIndex("userid"));

            if(userId == userid) {
                bool = true;
                break;
            }
        }

        c.close();
        db.close();
        return bool;
    }

    public String dbGetWalletBalanceUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_WALLET_USER, null, null, null, null, null, "userid");

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userid = c.getInt(c.getColumnIndex("userid"));

            if(userId == userid) {
                String totbalance = c.getString(c.getColumnIndex("totbalance"));

                c.close();
                db.close();
                return totbalance;
            }
        }

        c.close();
        db.close();
        return "0";
    }

    public void editWalletUser(int userId, String totbalance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

//        cv.put("userid", userId);
        cv.put("totbalance", totbalance);
        db.update(TBL_WALLET_USER, cv, "userid=" +userId, null);

        db.close();
    }

    public ArrayList<WalletUser> getAllWalletUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<WalletUser> list = new ArrayList<>();
        Cursor c = db.query(TBL_WALLET_USER, null, null, null, null, null, "userid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userid = c.getInt(c.getColumnIndex("userid"));

            if(userId == userid) {
                String totbalance = c.getString(c.getColumnIndex("totbalance"));

                list.add(new WalletUser(userid, totbalance));
            }
        }

        c.close();
        db.close();
        return list;
    }

    // WALLET PAYOUT
    public long addWalletPayout(int userid, int makerid, String booking_id, String payout_date, String payout_fee) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result;
        ContentValues cv = new ContentValues();

        cv.put("userid", userid);
        cv.put("makerid", makerid);
        cv.put("booking_id", booking_id);
        cv.put("payout_date", payout_date);
        cv.put("payout_fee", payout_fee);
        result = db.insert(TBL_WALLET_PAYOUT, null, cv);

        db.close();
        return result;
    }

    public ArrayList<WalletPayout> getAllWalletPayout() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<WalletPayout> list = new ArrayList<>();
        Cursor c = db.query(TBL_WALLET_PAYOUT, null, null, null, null, null, "payout_id");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userid = c.getInt(c.getColumnIndex("userid"));
            int makerid = c.getInt(c.getColumnIndex("makerid"));
            String booking_id = c.getString(c.getColumnIndex("booking_id"));
            String payout_date = c.getString(c.getColumnIndex("payout_date"));
            String payout_fee = c.getString(c.getColumnIndex("payout_fee"));

            list.add(new WalletPayout(userid, makerid, booking_id, payout_date, payout_fee));
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<WalletPayout> getAllWalletPayoutUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<WalletPayout> list = new ArrayList<>();
        Cursor c = db.query(TBL_WALLET_PAYOUT, null, null, null, null, null, "payout_id");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userid = c.getInt(c.getColumnIndex("userid"));

            if(userId == userid) {
                int makerid = c.getInt(c.getColumnIndex("makerid"));
                String booking_id = c.getString(c.getColumnIndex("booking_id"));
                String payout_date = c.getString(c.getColumnIndex("payout_date"));
                String payout_fee = c.getString(c.getColumnIndex("payout_fee"));

                list.add(new WalletPayout(userid, makerid, booking_id, payout_date, payout_fee));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public int deleteWalletPayout(String payout_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TBL_WALLET_PAYOUT, null, null, null, null, null, "payout_id");
        int id = 0;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if(payout_id.equals(c.getString(c.getColumnIndex("payout_id")))) {
                id = db.delete(TBL_WALLET_PAYOUT, "payout_id=?", new String[]{payout_id});
            }
        }

        c.close();
        db.close();
        return id;
    }

    public void deleteAllPayout() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_WALLET_PAYOUT, null, null);

        db.close();
    }
    /////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // WALLET CASH IN
    public long addWalletCashIn(int userid, String cashin_balance, String cashin_date, String cashin_time) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result;
        ContentValues cv = new ContentValues();

        cv.put("userid", userid);
        cv.put("cashin_balance", cashin_balance);
        cv.put("cashin_date", cashin_date);
        cv.put("cashin_time", cashin_time);
        result = db.insert(TBL_WALLET_CASH_IN, null, cv);

        db.close();
        return result;
    }

    public ArrayList<WalletCashIn> getAllWalletCashInUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<WalletCashIn> list = new ArrayList<>();
        Cursor c = db.query(TBL_WALLET_CASH_IN, null, null, null, null, null, "userid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userid = c.getInt(c.getColumnIndex("userid"));

            if(userid == userId) {
                String cashin_balance = c.getString(c.getColumnIndex("cashin_balance"));
                String cashin_date = c.getString(c.getColumnIndex("cashin_date"));
                String cashin_id = c.getString(c.getColumnIndex("cashin_id"));
                String cashin_time = c.getString(c.getColumnIndex("cashin_time"));

                list.add(new WalletCashIn(userid, cashin_balance, cashin_date, cashin_id, cashin_time));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<WalletCashIn> getAllWalletCashIn() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<WalletCashIn> list = new ArrayList<>();
        Cursor c = db.query(TBL_WALLET_CASH_IN, null, null, null, null, null, "cashin_id");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userid = c.getInt(c.getColumnIndex("userid"));
            String cashin_balance = c.getString(c.getColumnIndex("cashin_balance"));
            String cashin_date = c.getString(c.getColumnIndex("cashin_date"));
            String cashin_id = c.getString(c.getColumnIndex("cashin_id"));
            String cashin_time = c.getString(c.getColumnIndex("cashin_time"));

            list.add(new WalletCashIn(userid, cashin_balance, cashin_date, cashin_id, cashin_time));
        }

        c.close();
        db.close();
        return list;
    }

    public int deleteWalletCashIn(String cashin_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TBL_WALLET_CASH_IN, null, null, null, null, null, "cashin_id");
        int id = 0;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if(cashin_id.equals(c.getString(c.getColumnIndex("cashin_id")))) {
                id = db.delete(TBL_WALLET_CASH_IN, "cashin_id=?", new String[]{cashin_id});
            }
        }

        c.close();
        db.close();
        return id;
    }

    public void deleteAllCashIn() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_WALLET_CASH_IN, null, null);

        db.close();
    }
    /////////////////////////////////////////////////////////////////////////

    //***********************************************************************************************************
    // USER
    //************************************************************************************************************
    public long addUser(String profilepic, String username, String password , String fname, String lname, String gender, String email, String contact, String address, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = 0;
        ContentValues cv = new ContentValues();

        cv.put("profilepic", profilepic);
        cv.put("username", username);
        cv.put("password", password);
        cv.put("fname", fname);
        cv.put("lname", lname);
        cv.put("gender", gender);
        cv.put("email", email);
        cv.put("contact", contact);
        cv.put("address", address);
        cv.put("status", status);
        result = db.insert(TBL_USER, null, cv);

        db.close();
        return result;
    }

    public ArrayList<Account> findUser(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        int userId;
        String profilepic, username, password, fname, lname, gender, email, contact, address, status;
        ArrayList<Account> list = new ArrayList<>();
        Cursor c = db.query(TBL_USER,null,null,null,null,null,"username");

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            username = c.getString(c.getColumnIndex("username"));

            if(user.equals(username)) {
                userId = c.getInt(c.getColumnIndex("userid"));
                profilepic = c.getString(c.getColumnIndex("profilepic"));
                username = c.getString(c.getColumnIndex("username"));
                password = c.getString(c.getColumnIndex("password"));
                fname = c.getString(c.getColumnIndex("fname"));
                lname = c.getString(c.getColumnIndex("lname"));
                gender = c.getString(c.getColumnIndex("gender"));
                email = c.getString(c.getColumnIndex("email"));
                contact = c.getString(c.getColumnIndex("contact"));
                address = c.getString(c.getColumnIndex("address"));
                status = c.getString(c.getColumnIndex("status"));

                list.add(new Account(userId, Uri.parse(profilepic), username, password , fname, lname, gender, email, contact, address, status));
                break;
            }
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<User> getAllUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<User> list = new ArrayList<User>();
        Cursor c = db.query(TBL_USER,null,null,null,null,null,"username");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String profilepic = c.getString(c.getColumnIndex("profilepic"));
            String username = c.getString(c.getColumnIndex("username"));
            String password = c.getString(c.getColumnIndex("password"));
            String fname = c.getString(c.getColumnIndex("fname"));
            String lname = c.getString(c.getColumnIndex("lname"));
            String gender = c.getString(c.getColumnIndex("gender"));
            String email = c.getString(c.getColumnIndex("email"));
            String contact = c.getString(c.getColumnIndex("contact"));
            String address = c.getString(c.getColumnIndex("address"));
            String status = c.getString(c.getColumnIndex("status"));

            list.add(new User(Uri.parse(profilepic), username, password , fname, lname, gender, email, contact, address, status));
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Account> getAllUserAccounts(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Account> list = new ArrayList<Account>();
        Cursor c = db.query(TBL_USER,null,null,null,null,null,"userid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userId = c.getInt(c.getColumnIndex("userid"));
            String profilepic = c.getString(c.getColumnIndex("profilepic"));
            String username = c.getString(c.getColumnIndex("username"));
            String password = c.getString(c.getColumnIndex("password"));
            String fname = c.getString(c.getColumnIndex("fname"));
            String lname = c.getString(c.getColumnIndex("lname"));
            String gender = c.getString(c.getColumnIndex("gender"));
            String email = c.getString(c.getColumnIndex("email"));
            String contact = c.getString(c.getColumnIndex("contact"));
            String address = c.getString(c.getColumnIndex("address"));
            String status = c.getString(c.getColumnIndex("status"));

            list.add(new Account(userId, Uri.parse(profilepic), username, password , fname, lname, gender, email, contact, address, status));
        }

        c.close();
        db.close();
        return list;
    }

    //***********************************************************************************************************
    // ACCOUNT
    //************************************************************************************************************
    public long addAccount(int userId, String profilepic, String username, String password , String fname, String lname, String gender, String email, String contact, String address, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = 0;
        ContentValues cv = new ContentValues();

        cv.put("userId", userId);
        cv.put("profilepic", profilepic);
        cv.put("username", username);
        cv.put("password", password);
        cv.put("fname", fname);
        cv.put("lname", lname);
        cv.put("gender", gender);
        cv.put("email", email);
        cv.put("contact", contact);
        cv.put("address", address);
        cv.put("status", status);
        result = db.insert(TBL_ACCOUNT, null, cv);

        db.close();
        return result;
    }

    public ArrayList<Account> getAccountInfo(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Account> list = new ArrayList<>();
        Cursor c = db.query(TBL_ACCOUNT,null,null,null,null,null,"username");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userId = c.getInt(c.getColumnIndex("userid"));
            String profilepic = c.getString(c.getColumnIndex("profilepic"));
            String username = c.getString(c.getColumnIndex("username"));
            String password = c.getString(c.getColumnIndex("password"));
            String fname = c.getString(c.getColumnIndex("fname"));
            String lname = c.getString(c.getColumnIndex("lname"));
            String gender = c.getString(c.getColumnIndex("gender"));
            String email = c.getString(c.getColumnIndex("email"));
            String contact = c.getString(c.getColumnIndex("contact"));
            String address = c.getString(c.getColumnIndex("address"));
            String status = c.getString(c.getColumnIndex("status"));

            list.add(new Account(userId, Uri.parse(profilepic), username, password , fname, lname, gender, email, contact, address, status));
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Account> findAccount(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        int userId;
        String profilepic, username, password, fname, lname, gender, email, contact, address, status;
        ArrayList<Account> list = new ArrayList<>();
        Cursor c = db.query(TBL_USER,null,null,null,null,null,"username");

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            username = c.getString(c.getColumnIndex("username"));

            if(user.equals(username)) {
                userId = c.getInt(c.getColumnIndex("userid"));
                profilepic = c.getString(c.getColumnIndex("profilepic"));
                username = c.getString(c.getColumnIndex("username"));
                password = c.getString(c.getColumnIndex("password"));
                fname = c.getString(c.getColumnIndex("fname"));
                lname = c.getString(c.getColumnIndex("lname"));
                gender = c.getString(c.getColumnIndex("gender"));
                email = c.getString(c.getColumnIndex("email"));
                contact = c.getString(c.getColumnIndex("contact"));
                address = c.getString(c.getColumnIndex("address"));
                status = c.getString(c.getColumnIndex("status"));

                addAccount(userId, profilepic, username, password , fname, lname, gender, email, contact, address, status);
                list.add(new Account(userId, Uri.parse(profilepic), username, password , fname, lname, gender, email, contact, address, status));
                break;
            }
        }

        db.close();
        c.close();
        return list;
    }

    public ArrayList<Account> getFindAccount(int userid) {
        SQLiteDatabase db = this.getReadableDatabase();
        //int userId;
        String profilepic, username, password, fname, lname, gender, email, contact, address, status;
        ArrayList<Account> list = new ArrayList<>();
        Cursor c = db.query(TBL_USER,null,null,null,null,null,"username");

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userId = c.getInt(c.getColumnIndex("userid"));

            if(userId == userid) {
                profilepic = c.getString(c.getColumnIndex("profilepic"));
                username = c.getString(c.getColumnIndex("username"));
                password = c.getString(c.getColumnIndex("password"));
                fname = c.getString(c.getColumnIndex("fname"));
                lname = c.getString(c.getColumnIndex("lname"));
                gender = c.getString(c.getColumnIndex("gender"));
                email = c.getString(c.getColumnIndex("email"));
                contact = c.getString(c.getColumnIndex("contact"));
                address = c.getString(c.getColumnIndex("address"));
                status = c.getString(c.getColumnIndex("status"));

                list.add(new Account(userId, Uri.parse(profilepic), username, password , fname, lname, gender, email, contact, address, status));
                break;
            }
        }

        db.close();
        c.close();
        return list;
    }

    public void editUserAccountImage(String profilepic, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("profilepic", profilepic);
        cv.put("username", username);
        db.update(TBL_USER, cv, "username='" +username+ "';", null);
        db.update(TBL_ACCOUNT, cv, "username='" +username+ "';", null);

        db.close();
    }

    public void editUserAccount(String profilepic, String username, String password, String fname, String lname, String gender, String email, String contact, String address, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("profilepic", profilepic);
        cv.put("username", username);
        cv.put("password", password);
        cv.put("fname", fname);
        cv.put("lname", lname);
        cv.put("gender", gender);
        cv.put("email", email);
        cv.put("contact", contact);
        cv.put("address", address);
        cv.put("status", status);
        db.update(TBL_USER, cv, "username='" +username+ "';", null);
        db.update(TBL_ACCOUNT, cv, "username='" +username+ "';", null);

        db.close();
    }

    //************************************************************************************************************
    // MAKER
    //************************************************************************************************************
    public long addMaker(int userId, String shoppic, String shopname, String shopspecialty, String shoprating, String shopcontact, String shopemail, String shopaddress, String shopsubscription) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = 0;
        ContentValues cv = new ContentValues();

        cv.put("userid", userId);
        cv.put("shoppic", shoppic);
        cv.put("shopname", shopname);
        cv.put("shopspecialty", shopspecialty);
        cv.put("shoprating", shoprating);
        cv.put("shopcontact", shopcontact);
        cv.put("shopemail", shopemail);
        cv.put("shopaddress", shopaddress);
        cv.put("shopsubscription", shopsubscription);
        result = db.insert(TBL_MAKER, null, cv);

        db.close();
        return result;
    }

    public ArrayList<Maker> findMaker(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        int userid;
        String shopPic, shopName, shopSpecialty, shopContact, shopEmail, shopAddress, shopSubscription;
        float shopRating;
        ArrayList<Maker> list = new ArrayList<>();
        Cursor c = db.query(TBL_MAKER,null,null,null,null,null,"userid");

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            userid = c.getInt(c.getColumnIndex("userid"));

            if(userid == userId) {
                userId = c.getInt(c.getColumnIndex("userid"));
                shopPic = c.getString(c.getColumnIndex("shoppic"));
                shopName = c.getString(c.getColumnIndex("shopname"));
                shopSpecialty = c.getString(c.getColumnIndex("shopspecialty"));
                shopRating = c.getFloat(c.getColumnIndex("shoprating"));
                shopContact = c.getString(c.getColumnIndex("shopcontact"));
                shopEmail = c.getString(c.getColumnIndex("shopemail"));
                shopAddress = c.getString(c.getColumnIndex("shopaddress"));
                shopSubscription = c.getString(c.getColumnIndex("shopsubscription"));

                list.add(new Maker(userId, Uri.parse(shopPic), shopName, shopSpecialty , shopRating, shopContact, shopEmail, shopAddress, shopSubscription));
                break;
            }
        }

        db.close();
        c.close();
        return list;
    }

    public boolean isMaker(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_MAKER,null,null,null,null,null,"userid");

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userid = c.getInt(c.getColumnIndex("userid"));

            if(userid == userId) {
                c.close();
                db.close();
                return true;
            }
        }

        db.close();
        c.close();
        return false;
    }

    public ArrayList<Maker> findMakerId(int makerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String shopPic, shopName, shopSpecialty, shopContact, shopEmail, shopAddress, shopSubscription;
        float shopRating;
        ArrayList<Maker> list = new ArrayList<>();
        Cursor c = db.query(TBL_MAKER,null,null,null,null,null,"userid");

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int makerid = c.getInt(c.getColumnIndex("makerid"));

            if(makerid == makerId) {
                int userId = c.getInt(c.getColumnIndex("userid"));
                shopPic = c.getString(c.getColumnIndex("shoppic"));
                shopName = c.getString(c.getColumnIndex("shopname"));
                shopSpecialty = c.getString(c.getColumnIndex("shopspecialty"));
                shopRating = c.getFloat(c.getColumnIndex("shoprating"));
                shopContact = c.getString(c.getColumnIndex("shopcontact"));
                shopEmail = c.getString(c.getColumnIndex("shopemail"));
                shopAddress = c.getString(c.getColumnIndex("shopaddress"));
                shopSubscription = c.getString(c.getColumnIndex("shopsubscription"));

                list.add(new Maker(userId, Uri.parse(shopPic), shopName, shopSpecialty , shopRating, shopContact, shopEmail, shopAddress, shopSubscription));
                break;
            }
        }

        db.close();
        c.close();
        return list;
    }

    public void editMaker(int userId, String shoppic, String shopname, String shopspecialty, float shoprating, String shopcontact, String shopemail, String shopaddress) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("userid", userId);
        cv.put("shoppic", shoppic);
        cv.put("shopname", shopname);
        cv.put("shopspecialty", shopspecialty);
        cv.put("shoprating", shoprating);
        cv.put("shopcontact", shopcontact);
        cv.put("shopemail", shopemail);
        cv.put("shopaddress", shopaddress);
        db.update(TBL_MAKER, cv, "userid=" +userId, null);

        db.close();
    }

    public void editMakerSub(int userId, String shopsubscription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("shopsubscription", shopsubscription);
        db.update(TBL_MAKER, cv, "userid=" +userId, null);

        db.close();
    }

    public void editMaker(int userId, float shopRating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("userid", userId);
        cv.put("shoprating", shopRating);
        db.update(TBL_MAKER, cv, "userid=" +userId, null);

        db.close();
    }

    public ArrayList<Maker> getAllMakerUserId(int userId){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Maker> list = new ArrayList<>();
        Cursor c = db.query(TBL_MAKER,null,null,null,null,null,"userid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userid = c.getInt(c.getColumnIndex("userid"));
            ArrayList<Account> acclist = getFindAccount(userid);

            if(userId == userid) {
                if (acclist.get(0).getStatus().equals("ACTIVATED")) {
                    String shoppic = c.getString(c.getColumnIndex("shoppic"));
                    String shopname = c.getString(c.getColumnIndex("shopname"));
                    String shopspecialty = c.getString(c.getColumnIndex("shopspecialty"));
                    float shoprating = c.getFloat(c.getColumnIndex("shoprating"));
                    String shopcontact = c.getString(c.getColumnIndex("shopcontact"));
                    String shopemail = c.getString(c.getColumnIndex("shopemail"));
                    String shopaddress = c.getString(c.getColumnIndex("shopaddress"));
                    String shopsubscription = c.getString(c.getColumnIndex("shopsubscription"));

                    list.add(new Maker(userid, Uri.parse(shoppic), shopname, shopspecialty, shoprating, shopcontact, shopemail, shopaddress, shopsubscription));
                }
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Maker> getAllMaker(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Maker> list = new ArrayList<>();
        Cursor c = db.query(TBL_MAKER,null,null,null,null,null,"userid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userid = c.getInt(c.getColumnIndex("userid"));
            ArrayList<Account> acclist = getFindAccount(userid);

            if(acclist.get(0).getStatus().equals("ACTIVATED")) {
                String shoppic = c.getString(c.getColumnIndex("shoppic"));
                String shopname = c.getString(c.getColumnIndex("shopname"));
                String shopspecialty = c.getString(c.getColumnIndex("shopspecialty"));
                float shoprating = c.getFloat(c.getColumnIndex("shoprating"));
                String shopcontact = c.getString(c.getColumnIndex("shopcontact"));
                String shopemail = c.getString(c.getColumnIndex("shopemail"));
                String shopaddress = c.getString(c.getColumnIndex("shopaddress"));
                String shopsubscription = c.getString(c.getColumnIndex("shopsubscription"));

                list.add(new Maker(userid, Uri.parse(shoppic), shopname, shopspecialty, shoprating, shopcontact, shopemail, shopaddress, shopsubscription));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Maker> getAllMaker(String category){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Maker> list = new ArrayList<>();
        Cursor c = db.query(TBL_MAKER,null,null,null,null,null,"userid");
        Cursor c2 = db.query(TBL_MAKER,null,null,null,null,null,"userid");
        list.clear();
        String[] strAList = category.split(",");
        String cat = "T-Shirt, Bags, Hats, Hoodie or Jacket, Pillow";

        if(category.equals("No Selected Category") || category.equals("") || cat.toLowerCase().equals(category.toLowerCase())) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                int userid = c.getInt(c.getColumnIndex("userid"));
                ArrayList<Account> acclist = getFindAccount(userid);

                if (acclist.get(0).getStatus().equals("ACTIVATED")) {
                    String shoppic = c.getString(c.getColumnIndex("shoppic"));
                    String shopname = c.getString(c.getColumnIndex("shopname"));
                    String shopspecialty = c.getString(c.getColumnIndex("shopspecialty"));
                    float shoprating = c.getFloat(c.getColumnIndex("shoprating"));
                    String shopcontact = c.getString(c.getColumnIndex("shopcontact"));
                    String shopemail = c.getString(c.getColumnIndex("shopemail"));
                    String shopaddress = c.getString(c.getColumnIndex("shopaddress"));
                    String shopsubscription = c.getString(c.getColumnIndex("shopsubscription"));

                    list.add(new Maker(userid, Uri.parse(shoppic), shopname, shopspecialty, shoprating, shopcontact, shopemail, shopaddress, shopsubscription));
                }
            }
        } else {
            for (c2.moveToFirst(); !c2.isAfterLast(); c2.moveToNext()) {
                int userid = c2.getInt(c2.getColumnIndex("userid"));
                ArrayList<Account> acclist = getFindAccount(userid);

                if (acclist.get(0).getStatus().equals("ACTIVATED")) {
                    String shopspecialty = c2.getString(c2.getColumnIndex("shopspecialty"));

                    if (hasCommon(strAList, shopspecialty)) {
                        String shoppic = c2.getString(c.getColumnIndex("shoppic"));
                        String shopname = c2.getString(c.getColumnIndex("shopname"));
                        float shoprating = c2.getFloat(c.getColumnIndex("shoprating"));
                        String shopcontact = c2.getString(c.getColumnIndex("shopcontact"));
                        String shopemail = c2.getString(c.getColumnIndex("shopemail"));
                        String shopaddress = c2.getString(c.getColumnIndex("shopaddress"));
                        String shopsubscription = c2.getString(c.getColumnIndex("shopsubscription"));

                        list.add(new Maker(userid, Uri.parse(shoppic), shopname, shopspecialty, shoprating, shopcontact, shopemail, shopaddress, shopsubscription));
                    }
                }
            }
        }

        c.close();
        c2.close();
        db.close();
        return list;
    }

    private boolean hasCommon(String strAList[], String shopSpecialty) {
        for(String cat : strAList) {
            if(shopSpecialty.contains(cat.trim()))  {
                return true;
            }
        }

        return false;
    }

    //************************************************************************************************************
    // PRODUCT
    //************************************************************************************************************
    public long addProduct(int userId, String productPic, String productName, String productDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = 0;
        ContentValues cv = new ContentValues();

        cv.put("userid", userId);
        cv.put("productpic", productPic);
        cv.put("productname", productName);
        cv.put("productdetails", productDetails);
        result = db.insert(TBL_PRODUCT, null, cv);

        db.close();
        return result;
    }

    public void editProduct(int userId, int productId, String productPic, String productName, String productDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("userid", userId);
        cv.put("productpic", productPic);
        cv.put("productname", productName);
        cv.put("productdetails", productDetails);
        db.update(TBL_PRODUCT, cv, "productid=" +productId, null);

        db.close();
    }

    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_PRODUCT, "productid="+id, null);
        db.close();
    }

    public ArrayList<Product> getMakerProduct(int userId){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Product> list = new ArrayList<>();
        Cursor c = db.query(TBL_PRODUCT,null,null,null,null,null,"userid");
        list.clear();
        int userid;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            userid = c.getInt(c.getColumnIndex("userid"));

            if(userId == userid) {
                userid = c.getInt(c.getColumnIndex("userid"));
                int productid = c.getInt(c.getColumnIndex("productid"));
                String productPic = c.getString(c.getColumnIndex("productpic"));
                String productName = c.getString(c.getColumnIndex("productname"));
                String productDetails = c.getString(c.getColumnIndex("productdetails"));

                list.add(new Product(userid, productid, Uri.parse(productPic), productName, productDetails));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Product> getAllProduct() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Product> list = new ArrayList<>();
        Cursor c = db.query(TBL_PRODUCT,null,null,null,null,null,"userid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userid = c.getInt(c.getColumnIndex("userid"));
            int productid = c.getInt(c.getColumnIndex("productid"));
            String productPic = c.getString(c.getColumnIndex("productpic"));
            String productName = c.getString(c.getColumnIndex("productname"));
            String productDetails = c.getString(c.getColumnIndex("productdetails"));

            list.add(new Product(userid, productid, Uri.parse(productPic), productName, productDetails));
        }

        c.close();
        db.close();
        return list;
    }

    //************************************************************************************************************
    // REVIEW
    //************************************************************************************************************
    public long addReview(int requestId, int userId, int makerId, String reviewerPic, String reviewerName, String reviewdate, float rating, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = 0;
        ContentValues cv = new ContentValues();

        cv.put("requestid", requestId);
        cv.put("userid", userId);
        cv.put("makerid", makerId);
        cv.put("reviewerpic", reviewerPic);
        cv.put("reviewername", reviewerName);
        cv.put("reviewdate", reviewdate);
        cv.put("rating", rating);
        cv.put("comment", comment);
        result = db.insert(TBL_REVIEW, null, cv);

        db.close();
        return result;
    }

    public void editReview(int requestId, float rating, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("requestid", requestId);
        cv.put("rating", rating);
        cv.put("comment", comment);
        db.update(TBL_REVIEW, cv, "requestid=" +requestId, null);

        db.close();
    }

    public ArrayList<Review> findReviewMaker(int makerId){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Review> list = new ArrayList<>();
        int makerid;
        Cursor c = db.query(TBL_REVIEW,null,null,null,null,null,"makerid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            makerid = c.getInt(c.getColumnIndex("makerid"));

            if(makerId == makerid) {
                int requestId = c.getInt(c.getColumnIndex("requestid"));
                int userId = c.getInt(c.getColumnIndex("userid"));
                String reviewerPic = c.getString(c.getColumnIndex("reviewerpic"));
                String reviewerName = c.getString(c.getColumnIndex("reviewername"));
                String reviewdate = c.getString(c.getColumnIndex("reviewdate"));
                float rating = c.getFloat(c.getColumnIndex("rating"));
                String comment = c.getString(c.getColumnIndex("comment"));

                list.add(new Review(requestId, userId, makerId, Uri.parse(reviewerPic), reviewerName, reviewdate, rating, comment));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public float findTotalReviewMaker(int makerId){
        SQLiteDatabase db = this.getReadableDatabase();
        int makerid;
        float totalRating = 0;
        Cursor c = db.query(TBL_REVIEW,null,null,null,null,null,"makerid");

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            makerid = c.getInt(c.getColumnIndex("makerid"));

            if(makerId == makerid) {
                float rating = c.getFloat(c.getColumnIndex("rating"));

                totalRating += rating;
            }
        }

        c.close();
        db.close();
        return totalRating;
    }

    public ArrayList<Review> findReviewRequest(int requestId){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Review> list = new ArrayList<>();
        int requestid;
        Cursor c = db.query(TBL_REVIEW,null,null,null,null,null,"userid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            requestid = c.getInt(c.getColumnIndex("requestid"));

            if(requestId == requestid) {
                int makerId = c.getInt(c.getColumnIndex("makerid"));
                int userId = c.getInt(c.getColumnIndex("userid"));
                String reviewerPic = c.getString(c.getColumnIndex("reviewerpic"));
                String reviewerName = c.getString(c.getColumnIndex("reviewername"));
                String reviewdate = c.getString(c.getColumnIndex("reviewdate"));
                float rating = c.getFloat(c.getColumnIndex("rating"));
                String comment = c.getString(c.getColumnIndex("comment"));

                list.add(new Review(requestId, userId, makerId, Uri.parse(reviewerPic), reviewerName, reviewdate, rating, comment));
                break;
            }
        }

        c.close();
        db.close();
        return list;
    }

    public void deleteReview(int requestId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TBL_REVIEW, "requestid=" +requestId, null);
        db.close();
    }

    //************************************************************************************************************
    // POST
    //************************************************************************************************************
    public long addPost(int userId, String userpic, String username, String postPic, String postDetails, String postcategory, String posttype, String postsize, int postQty, String postColor, String postDate, String postestdate, String postoptone, String postopttwo) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = 0;
        ContentValues cv = new ContentValues();

        cv.put("userid", userId);
        cv.put("userpic", userpic);
        cv.put("username", username);
        cv.put("postpic", postPic);
        cv.put("postdetails", postDetails);
        cv.put("postcategory", postcategory);
        cv.put("posttype", posttype);
        cv.put("postsize", postsize);
        cv.put("postqty", postQty);
        cv.put("postcolor", postColor);
        cv.put("postdate", postDate);
        cv.put("postestdate", postestdate);
        cv.put("postoptone", postoptone);
        cv.put("postopttwo", postopttwo);
//        cv.put("postdate", postDate);
        result = db.insert(TBL_POST, null, cv);

        db.close();
        return result;
    }

    public void editPost(int postId, int userId, String userpic, String username, String postPic, String postDetails, String postcategory, String posttype, String postsize, int postQty, String postColor, String postDate, String postestdate, String postoptone, String postopttwo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("userid", userId);
        cv.put("userpic", userpic);
        cv.put("username", username);
        cv.put("postpic", postPic);
        cv.put("postdetails", postDetails);
        cv.put("postcategory", postcategory);
        cv.put("posttype", posttype);
        cv.put("postsize", postsize);
        cv.put("postqty", postQty);
        cv.put("postcolor", postColor);
        cv.put("postdate", postDate);
        cv.put("postestdate", postestdate);
        cv.put("postoptone", postoptone);
        cv.put("postopttwo", postopttwo);

        db.update(TBL_POST, cv, "postid=" +postId, null);

        db.close();
    }

    public void deletePost(int postId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_POST, "postid=" +postId, null);

        db.close();
    }

    public boolean getUserPostIdBoolean(int postId) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean bool = false;
        Cursor c = db.query(TBL_POST, null, null, null, null, null, "userid");

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int postid = c.getInt(c.getColumnIndex("postid"));

            if(postId == postid) {
                bool = true;
                break;
            }
        }

        return bool;
    }

    public ArrayList<Post> getUserPostId(int postId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Post> list = new ArrayList<>();
        Cursor c = db.query(TBL_POST,null,null,null,null,null,"userid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int postid = c.getInt(c.getColumnIndex("postid"));

            if(postId == postid) {
                int userid = c.getInt(c.getColumnIndex("userid"));
                String userpic = c.getString(c.getColumnIndex("userpic"));
                String username = c.getString(c.getColumnIndex("username"));
                String postpic = c.getString(c.getColumnIndex("postpic"));
                String postdetails = c.getString(c.getColumnIndex("postdetails"));
                String postcategory = c.getString(c.getColumnIndex("postcategory"));
                String posttype = c.getString(c.getColumnIndex("posttype"));
                String postsize = c.getString(c.getColumnIndex("postsize"));
                int postQty = c.getInt(c.getColumnIndex("postqty"));
                String postcolor = c.getString(c.getColumnIndex("postcolor"));
                String postdate = c.getString(c.getColumnIndex("postdate"));
                String postestdate = c.getString(c.getColumnIndex("postestdate"));
                String postoptone = c.getString(c.getColumnIndex("postoptone"));
                String postopttwo = c.getString(c.getColumnIndex("postopttwo"));

                list.add(new Post(userid, postid, Uri.parse(userpic), username, Uri.parse(postpic), postdetails, postcategory, posttype, postsize, postQty, postcolor, postdate, postestdate, postoptone, postopttwo));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Post> getUserPost(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Post> list = new ArrayList<>();
        int userid;
        Cursor c = db.query(TBL_POST,null,null,null,null,null,"userid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            userid = c.getInt(c.getColumnIndex("userid"));

            if(userId == userid) {
                userid = c.getInt(c.getColumnIndex("userid"));
                int postid = c.getInt(c.getColumnIndex("postid"));
                String userpic = c.getString(c.getColumnIndex("userpic"));
                String username = c.getString(c.getColumnIndex("username"));
                String postpic = c.getString(c.getColumnIndex("postpic"));
                String postdetails = c.getString(c.getColumnIndex("postdetails"));
                String postcategory = c.getString(c.getColumnIndex("postcategory"));
                String posttype = c.getString(c.getColumnIndex("posttype"));
                String postsize = c.getString(c.getColumnIndex("postsize"));
                int postQty = c.getInt(c.getColumnIndex("postqty"));
                String postcolor = c.getString(c.getColumnIndex("postcolor"));
                String postdate = c.getString(c.getColumnIndex("postdate"));
                String postestdate = c.getString(c.getColumnIndex("postestdate"));
                String postoptone = c.getString(c.getColumnIndex("postoptone"));
                String postopttwo = c.getString(c.getColumnIndex("postopttwo"));

                list.add(new Post(userid, postid, Uri.parse(userpic), username, Uri.parse(postpic), postdetails, postcategory, posttype, postsize, postQty, postcolor, postdate, postestdate, postoptone, postopttwo));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Post> getAllPost(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Post> list = new ArrayList<>();
        Cursor c = db.query(TBL_POST,null,null,null,null,null,"userid");
        list.clear();
        String cat = "T-Shirt, Bags, Hats, Hoodie or Jacket, Pillow";

        if(category.equals("No Selected Category") || category.equals("") || cat.toLowerCase().equals(category.toLowerCase())) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                int userid = c.getInt(c.getColumnIndex("userid"));
                ArrayList<Account> acclist = getFindAccount(userid);

                if (acclist.get(0).getStatus().equals("ACTIVATED")) {
                    int postid = c.getInt(c.getColumnIndex("postid"));
                    String userpic = c.getString(c.getColumnIndex("userpic"));
                    String username = c.getString(c.getColumnIndex("username"));
                    String postpic = c.getString(c.getColumnIndex("postpic"));
                    String postdetails = c.getString(c.getColumnIndex("postdetails"));
                    String postcategory = c.getString(c.getColumnIndex("postcategory"));
                    String posttype = c.getString(c.getColumnIndex("posttype"));
                    String postsize = c.getString(c.getColumnIndex("postsize"));
                    int postQty = c.getInt(c.getColumnIndex("postqty"));
                    String postcolor = c.getString(c.getColumnIndex("postcolor"));
                    String postdate = c.getString(c.getColumnIndex("postdate"));

                    String postestdate = c.getString(c.getColumnIndex("postestdate"));
                    String postoptone = c.getString(c.getColumnIndex("postoptone"));
                    String postopttwo = c.getString(c.getColumnIndex("postopttwo"));

                    list.add(new Post(userid, postid, Uri.parse(userpic), username, Uri.parse(postpic), postdetails, postcategory, posttype, postsize, postQty, postcolor, postdate, postestdate, postoptone, postopttwo));
                }
            }
        } else {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                int userid = c.getInt(c.getColumnIndex("userid"));
                ArrayList<Account> acclist = getFindAccount(userid);

                if (acclist.get(0).getStatus().equals("ACTIVATED")) {
                    String postcategory = c.getString(c.getColumnIndex("postcategory"));

                    if (category.toLowerCase().contains(postcategory.toLowerCase())) {
                        int postid = c.getInt(c.getColumnIndex("postid"));
                        String userpic = c.getString(c.getColumnIndex("userpic"));
                        String username = c.getString(c.getColumnIndex("username"));
                        String postpic = c.getString(c.getColumnIndex("postpic"));
                        String postdetails = c.getString(c.getColumnIndex("postdetails"));
                        String posttype = c.getString(c.getColumnIndex("posttype"));
                        String postsize = c.getString(c.getColumnIndex("postsize"));
                        int postQty = c.getInt(c.getColumnIndex("postqty"));
                        String postcolor = c.getString(c.getColumnIndex("postcolor"));
                        String postdate = c.getString(c.getColumnIndex("postdate"));

                        String postestdate = c.getString(c.getColumnIndex("postestdate"));
                        String postoptone = c.getString(c.getColumnIndex("postoptone"));
                        String postopttwo = c.getString(c.getColumnIndex("postopttwo"));

                        list.add(new Post(userid, postid, Uri.parse(userpic), username, Uri.parse(postpic), postdetails, postcategory, posttype, postsize, postQty, postcolor, postdate, postestdate, postoptone, postopttwo));
                    }
                }
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Post> getAllPosts() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Post> list = new ArrayList<>();
        Cursor c = db.query(TBL_POST,null,null,null,null,null,"userid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userid = c.getInt(c.getColumnIndex("userid"));
            ArrayList<Account> acclist = getFindAccount(userid);

            if(acclist.get(0).getStatus().equals("ACTIVATED")) {
                int postid = c.getInt(c.getColumnIndex("postid"));
                String userpic = c.getString(c.getColumnIndex("userpic"));
                String username = c.getString(c.getColumnIndex("username"));
                String postpic = c.getString(c.getColumnIndex("postpic"));
                String postdetails = c.getString(c.getColumnIndex("postdetails"));
                String postcategory = c.getString(c.getColumnIndex("postcategory"));
                String posttype = c.getString(c.getColumnIndex("posttype"));
                String postsize = c.getString(c.getColumnIndex("postsize"));
                int postQty = c.getInt(c.getColumnIndex("postqty"));
                String postcolor = c.getString(c.getColumnIndex("postcolor"));
                String postdate = c.getString(c.getColumnIndex("postdate"));

                String postestdate = c.getString(c.getColumnIndex("postestdate"));
                String postoptone = c.getString(c.getColumnIndex("postoptone"));
                String postopttwo = c.getString(c.getColumnIndex("postopttwo"));

                list.add(new Post(userid, postid, Uri.parse(userpic), username, Uri.parse(postpic), postdetails, postcategory, posttype, postsize, postQty, postcolor, postdate, postestdate, postoptone, postopttwo));
            }
        }

        c.close();
        db.close();
        return list;
    }

    //************************************************************************************************************
    // OFFER
    //************************************************************************************************************
    public long addPostOffer(int nwoid, int userId, int postId, int shopId, String shopPic, String shopName, String offerPrice, String offerDate, String offerComment, String offerStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        long res = 0;
        ContentValues cv = new ContentValues();

        cv.put("nwoid", nwoid);
        cv.put("postid", postId);
        cv.put("userid", userId);
        cv.put("shopid", shopId);
        cv.put("shoppic", shopPic);
        cv.put("shopname", shopName);
        cv.put("offerprice", offerPrice);
        cv.put("offerdate", offerDate);
        cv.put("offercomment", offerComment);
        cv.put("offerstatus", offerStatus);
        res = db.insert(TBL_POST_OFFER, null, cv);

        return res;
    }

    public ArrayList<Offer> getPostOffer(int postId, int shopId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Offer> list = new ArrayList<>();
        Cursor c = db.query(TBL_POST_OFFER,null,null,null,null,null,"postid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int postid = c.getInt(c.getColumnIndex("postid"));
            int shopid = c.getInt(c.getColumnIndex("shopid"));

            if(postId == postid && shopId == shopid) {
                int nwoid = c.getInt(c.getColumnIndex("nwoid"));
                int userId = c.getInt(c.getColumnIndex("userid"));
                String shopPic = c.getString(c.getColumnIndex("shoppic"));
                String shopName = c.getString(c.getColumnIndex("shopname"));
                String offerPrice = c.getString(c.getColumnIndex("offerprice"));
                String offerDate = c.getString(c.getColumnIndex("offerdate"));
                String offerComment = c.getString(c.getColumnIndex("offercomment"));
                String offerStatus = c.getString(c.getColumnIndex("offerstatus"));

                list.add(new Offer(nwoid, userId, postId, shopId, shopPic, shopName, offerPrice, offerDate, offerComment, offerStatus));
                break;
            }
        }

        c.close();
        db.close();
        return list;
    }

    public void editPostOffer(int nwoid, int userId, int postId, int shopId, String comment, String date, String price, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("nwoid", nwoid);
        cv.put("userid", userId);
        cv.put("offercomment", comment);
        cv.put("offerdate", date);
        cv.put("offerprice", price);
        cv.put("offerstatus", status);
        db.update(TBL_POST_OFFER, cv, "postid=" +postId+ " AND shopid=" +shopId, null);

        db.close();
    }

    public boolean findOfferSet(int postid) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean bool = false;
        Cursor c = db.query(TBL_POST_OFFER,null,null,null,null,null,"postid");

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int postId = c.getInt(c.getColumnIndex("postid"));
            String offerstatus = c.getString(c.getColumnIndex("offerstatus"));

            if(postId == postid && offerstatus.equals("SELECTED")) {
                bool = true;
                break;
            }
        }

        c.close();
        db.close();
        return bool;
    }

    public ArrayList<Offer> getAllOfferNwoId(int nwoId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Offer> list = new ArrayList<>();
        Cursor c = db.query(TBL_POST_OFFER,null,null,null,null,null,"postid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int nwoid = c.getInt(c.getColumnIndex("nwoid"));

            if(nwoid == nwoId) {
                int postId = c.getInt(c.getColumnIndex("postid"));
                int userId = c.getInt(c.getColumnIndex("userid"));
                int shopId = c.getInt(c.getColumnIndex("shopid"));
                String shopPic = c.getString(c.getColumnIndex("shoppic"));
                String shopName = c.getString(c.getColumnIndex("shopname"));
                String offerPrice = c.getString(c.getColumnIndex("offerprice"));
                String offerDate = c.getString(c.getColumnIndex("offerdate"));
                String offerComment = c.getString(c.getColumnIndex("offercomment"));
                String offerStatus = c.getString(c.getColumnIndex("offerstatus"));

                list.add(new Offer(nwoid, userId, postId, shopId, shopPic, shopName, offerPrice, offerDate, offerComment, offerStatus));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Offer> getAllOffer(int postId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Offer> list = new ArrayList<>();
        Cursor c = db.query(TBL_POST_OFFER,null,null,null,null,null,"postid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int postid = c.getInt(c.getColumnIndex("postid"));

            if(postId == postid) {
                int nwoid = c.getInt(c.getColumnIndex("nwoid"));
                int userId = c.getInt(c.getColumnIndex("userid"));
                int shopId = c.getInt(c.getColumnIndex("shopid"));
                String shopPic = c.getString(c.getColumnIndex("shoppic"));
                String shopName = c.getString(c.getColumnIndex("shopname"));
                String offerPrice = c.getString(c.getColumnIndex("offerprice"));
                String offerDate = c.getString(c.getColumnIndex("offerdate"));
                String offerComment = c.getString(c.getColumnIndex("offercomment"));
                String offerStatus = c.getString(c.getColumnIndex("offerstatus"));

                list.add(new Offer(nwoid, userId, postId, shopId, shopPic, shopName, offerPrice, offerDate, offerComment, offerStatus));
            }
        }

        c.close();
        db.close();
        return list;
    }

    //************************************************************************************************************
    // COMMENT
    //************************************************************************************************************
    public long addPostComment(int postId, int userId, String commentPic, String commentName, String comment, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        long res = 0;
        ContentValues cv = new ContentValues();

        cv.put("postid", postId);
        cv.put("userid", userId);
        cv.put("commentpic", commentPic);
        cv.put("commentname", commentName);
        cv.put("comment", comment);
        cv.put("date", date);
        res = db.insert(TBL_POST_COMMENT, null, cv);

        return res;
    }

    public ArrayList<PostComment> getAllPostComment(int postId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<PostComment> list = new ArrayList<>();
        Cursor c = db.query(TBL_POST_COMMENT,null,null,null,null,null,"postid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int postid = c.getInt(c.getColumnIndex("postid"));

            if(postId == postid) {
                int userId = c.getInt(c.getColumnIndex("userid"));
                String commentPic = c.getString(c.getColumnIndex("commentpic"));
                String commentName = c.getString(c.getColumnIndex("commentname"));
                String comment = c.getString(c.getColumnIndex("comment"));
                String date = c.getString(c.getColumnIndex("date"));

                list.add(new PostComment(postId, userId, Uri.parse(commentPic), commentName, comment, date));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<PostComment> getAllPostComment2(int postId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<PostComment> list = new ArrayList<>();
        Cursor c = db.query(TBL_POST_COMMENT,null,null,null,null,null,"postid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int postid = c.getInt(c.getColumnIndex("postId"));

            if(postId == postid) {
                int commentId = c.getInt(c.getColumnIndex("commentid"));
                int userId = c.getInt(c.getColumnIndex("userid"));
                String commentPic = c.getString(c.getColumnIndex("commentpic"));
                String commentName = c.getString(c.getColumnIndex("commentname"));
                String comment = c.getString(c.getColumnIndex("comment"));
                String date = c.getString(c.getColumnIndex("date"));

                list.add(new PostComment(commentId, postId, userId, Uri.parse(commentPic), commentName, comment, date));
            }
        }

        c.close();
        db.close();
        return list;
    }

    //************************************************************************************************************
    // ORDER
    //************************************************************************************************************
    public long addOrder(int userId, int requestId, int makerId, String orderDate, String orderMade, String orderStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        long res = 0;
        ContentValues cv = new ContentValues();

        cv.put("userid", userId);
        cv.put("requestid", requestId);
        cv.put("shopid", makerId);
        cv.put("orderdate", orderDate);
        cv.put("ordermade", orderMade);
        cv.put("orderstatus", orderStatus);
        res = db.insert(TBL_ORDER, null, cv);

        return res;
    }

    public ArrayList<Order> getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Order> list = new ArrayList<>();
        Cursor c = db.query(TBL_ORDER,null,null,null,null,null,"shopid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userId = c.getInt(c.getColumnIndex("userid"));
            int makerId = c.getInt(c.getColumnIndex("shopid"));
            int requestId = c.getInt(c.getColumnIndex("requestid"));
            String orderDate = c.getString(c.getColumnIndex("orderdate"));
            String orderMade = c.getString(c.getColumnIndex("ordermade"));
            String orderStatus = c.getString(c.getColumnIndex("orderstatus"));

            list.add(new Order(userId, requestId, makerId, orderDate, orderMade, orderStatus));
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Order> getAllOrderUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Order> list = new ArrayList<>();
        Cursor c = db.query(TBL_ORDER,null,null,null,null,null,"shopid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userid = c.getInt(c.getColumnIndex("userid"));

            if(userid == userId) {
                int makerId = c.getInt(c.getColumnIndex("shopid"));
                int requestId = c.getInt(c.getColumnIndex("requestid"));
                String orderDate = c.getString(c.getColumnIndex("orderdate"));
                String orderMade = c.getString(c.getColumnIndex("ordermade"));
                String orderStatus = c.getString(c.getColumnIndex("orderstatus"));

                list.add(new Order(userId, requestId, makerId, orderDate, orderMade, orderStatus));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Order> getAllOrder(int shopId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Order> list = new ArrayList<>();
        Cursor c = db.query(TBL_ORDER,null,null,null,null,null,"shopid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int makerId = c.getInt(c.getColumnIndex("shopid"));

            if(shopId == makerId) {
                int userId = c.getInt(c.getColumnIndex("userid"));
                int requestId = c.getInt(c.getColumnIndex("requestid"));
                String orderDate = c.getString(c.getColumnIndex("orderdate"));
                String orderMade = c.getString(c.getColumnIndex("ordermade"));
                String orderStatus = c.getString(c.getColumnIndex("orderstatus"));

                list.add(new Order(userId, requestId, makerId, orderDate, orderMade, orderStatus));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public void editOrder(int postId, int shopId, String made, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("ordermade", made);
        cv.put("orderstatus", status);
        db.update(TBL_ORDER, cv,"requestid=" +postId+ " AND shopid=" +shopId, null);

        db.close();
    }

    public boolean isSet(int postId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_ORDER,null,null,null,null,null,"shopid");

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int requestId = c.getInt(c.getColumnIndex("requestid"));

            if(postId == requestId) {
                c.close();
                db.close();
                return true;
            }
        }

        c.close();
        db.close();
        return false;
    }

    //************************************************************************************************************
    // REQUEST
    //************************************************************************************************************
    public long addRequest(int userId, int makerId, String userPic, String requestPic, String userName, String requestDetails, String requestCategory, String requestType, String requestSize, int requesQty, String requestColor, String requestedDate, String requestStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = 0;
        ContentValues cv = new ContentValues();

        cv.put("userid", userId);
        cv.put("makerid", makerId);
        cv.put("userpic", userPic);
        cv.put("requestpic", requestPic);
        cv.put("username", userName);
        cv.put("requestdetails", requestDetails);
        cv.put("requestcategory", requestCategory);
        cv.put("requesttype", requestType);
        cv.put("requestsize", requestSize);
        cv.put("requestcolor", requestColor);
        cv.put("requesteddate", requestedDate);
        cv.put("requestqty", requesQty);
        cv.put("requeststatus", requestStatus);
        result = db.insert(TBL_REQUEST, null, cv);

        db.close();
        return result;
    }

    public ArrayList<Request> getRequestMaker(int makerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Request> list = new ArrayList<>();
        Cursor c = db.query(TBL_REQUEST,null,null,null,null,null,"requestid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int makerid = c.getInt(c.getColumnIndex("makerid"));

            if(makerId == makerid) {
                int userId = c.getInt(c.getColumnIndex("userid"));
                int requestId = c.getInt(c.getColumnIndex("requestid"));
                String userPic = c.getString(c.getColumnIndex("userpic"));
                String requestPic = c.getString(c.getColumnIndex("requestpic"));
                String userName = c.getString(c.getColumnIndex("username"));
                String requestDetails = c.getString(c.getColumnIndex("requestdetails"));
                String requestCategory = c.getString(c.getColumnIndex("requestcategory"));
                String requestType = c.getString(c.getColumnIndex("requesttype"));
                String requestSize = c.getString(c.getColumnIndex("requestsize"));
                int requestQty = c.getInt(c.getColumnIndex("requestqty"));
                String requestcolor  = c.getString(c.getColumnIndex("requestcolor"));
                String requestedDate = c.getString(c.getColumnIndex("requesteddate"));
                String requestStatus = c.getString(c.getColumnIndex("requeststatus"));

                list.add(new Request(userId, requestId, makerid, Uri.parse(userPic), Uri.parse(requestPic), userName, requestDetails, requestCategory, requestType, requestSize, requestQty, requestcolor, requestedDate, requestStatus));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Request> getRequestUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Request> list = new ArrayList<>();
        Cursor c = db.query(TBL_REQUEST,null,null,null,null,null,"requestid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userid = c.getInt(c.getColumnIndex("userid"));

            if(userId == userid) {
                int makerId = c.getInt(c.getColumnIndex("makerid"));
                int requestId = c.getInt(c.getColumnIndex("requestid"));
                String userPic = c.getString(c.getColumnIndex("userpic"));
                String requestPic = c.getString(c.getColumnIndex("requestpic"));
                String userName = c.getString(c.getColumnIndex("username"));
                String requestDetails = c.getString(c.getColumnIndex("requestdetails"));
                String requestCategory = c.getString(c.getColumnIndex("requestcategory"));
                String requestType = c.getString(c.getColumnIndex("requesttype"));
                String requestSize = c.getString(c.getColumnIndex("requestsize"));
                int requestQty = c.getInt(c.getColumnIndex("requestqty"));
                String requestcolor  = c.getString(c.getColumnIndex("requestcolor"));
                String requestedDate = c.getString(c.getColumnIndex("requesteddate"));
                String requestStatus = c.getString(c.getColumnIndex("requeststatus"));

                list.add(new Request(userId, requestId, makerId, Uri.parse(userPic), Uri.parse(requestPic), userName, requestDetails, requestCategory, requestType, requestSize, requestQty, requestcolor, requestedDate, requestStatus));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<Request> getRequest(int requestid) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Request> list = new ArrayList<>();
        Cursor c = db.query(TBL_REQUEST,null,null,null,null,null,"requestid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int requestId = c.getInt(c.getColumnIndex("requestid"));

            if(requestId == requestid) {
                int userId = c.getInt(c.getColumnIndex("userid"));
                int makerId = c.getInt(c.getColumnIndex("makerid"));
                String userPic = c.getString(c.getColumnIndex("userpic"));
                String requestPic = c.getString(c.getColumnIndex("requestpic"));
                String userName = c.getString(c.getColumnIndex("username"));
                String requestDetails = c.getString(c.getColumnIndex("requestdetails"));
                String requestCategory = c.getString(c.getColumnIndex("requestcategory"));
                String requestType = c.getString(c.getColumnIndex("requesttype"));
                String requestSize = c.getString(c.getColumnIndex("requestsize"));
                int requestQty = c.getInt(c.getColumnIndex("requestqty"));
                String requestcolor  = c.getString(c.getColumnIndex("requestcolor"));
                String requestedDate = c.getString(c.getColumnIndex("requesteddate"));
                String requestStatus = c.getString(c.getColumnIndex("requeststatus"));

                list.add(new Request(userId, requestId, makerId, Uri.parse(userPic), Uri.parse(requestPic), userName, requestDetails, requestCategory, requestType, requestSize, requestQty, requestcolor, requestedDate, requestStatus));
                break;
            }
        }

        c.close();
        db.close();
        return list;
    }

    public void editRequest(int requestId, String requestStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("requeststatus", requestStatus);
        db.update(TBL_REQUEST, cv, "requestid=" +requestId, null);

        db.close();
    }

    public ArrayList<Request> getAllRequest() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Request> list = new ArrayList<>();
        Cursor c = db.query(TBL_REQUEST,null,null,null,null,null,"requestid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int makerid = c.getInt(c.getColumnIndex("makerid"));
            int userId = c.getInt(c.getColumnIndex("userid"));
            int requestId = c.getInt(c.getColumnIndex("requestid"));
            String userPic = c.getString(c.getColumnIndex("userpic"));
            String requestPic = c.getString(c.getColumnIndex("requestpic"));
            String userName = c.getString(c.getColumnIndex("username"));
            String requestDetails = c.getString(c.getColumnIndex("requestdetails"));
            String requestCategory = c.getString(c.getColumnIndex("requestcategory"));
            String requestType = c.getString(c.getColumnIndex("requesttype"));
            String requestSize = c.getString(c.getColumnIndex("requestsize"));
            int requestQty = c.getInt(c.getColumnIndex("requestqty"));
            String requestcolor  = c.getString(c.getColumnIndex("requestcolor"));
            String requestedDate = c.getString(c.getColumnIndex("requesteddate"));
            String requestStatus = c.getString(c.getColumnIndex("requeststatus"));

            list.add(new Request(userId, requestId, makerid, Uri.parse(userPic), Uri.parse(requestPic), userName, requestDetails, requestCategory, requestType, requestSize, requestQty, requestcolor, requestedDate, requestStatus));
        }

        c.close();
        db.close();
        return list;
    }

    //************************************************************************************************************
    // SEARCH
    //************************************************************************************************************
    public long addSearch(int userId, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = 0;
        ContentValues cv = new ContentValues();

        cv.put("userid", userId);
        cv.put("searchtype", type);
        result = db.insert(TBL_SEARCH, null, cv);

        db.close();
        return result;
    }

    public ArrayList<Search> getAllSearch() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Search> list = new ArrayList<>();
        Cursor c = db.query(TBL_SEARCH,null,null,null,null,null,"searchid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userid = c.getInt(c.getColumnIndex("userid"));
            String type = c.getString(c.getColumnIndex("searchtype"));

            list.add(new Search(userid, type));
        }

        c.close();
        db.close();
        return list;
    }

    //************************************************************************************************************
    // DELETE
    //************************************************************************************************************
    public long addNotification(int userid, int shopid, int requestid, String date, String type, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long res = 0;

        cv.put("userid", userid);
        cv.put("shopid", shopid);
        cv.put("requestid", requestid);
        cv.put("date", date);
        cv.put("type", type);
        cv.put("message", message);
        res = db.insert(TBL_NOTIFICATION, null, cv);

        return res;
    }

    public ArrayList<Notif> getAllNotificationUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TBL_NOTIFICATION, null, null, null, null, null, "userid");
        ArrayList<Notif> list = new ArrayList<>();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userid = c.getInt(c.getColumnIndex("userid"));

            if(userId == userid) {
                int shopid = c.getInt(c.getColumnIndex("shopid"));
                int requestid = c.getInt(c.getColumnIndex("requestid"));
                String date = c.getString(c.getColumnIndex("date"));
                String type = c.getString(c.getColumnIndex("type"));
                String message = c.getString(c.getColumnIndex("message"));

                list.add(new Notif(userId, shopid, requestid, date, type, message));
            }
        }

        c.close();
        db.close();
        return list;
    }
    //************************************************************************************************************
    // NEW WORLD ORDER
    //************************************************************************************************************
    public long addNewWorldOrder(int userId, int makerId, int nwoQty, String nwoPic, String nwoDetails, String nwoCategory, String nwoType, String nwoSize, String nwoColor, String nwoDate, String nwoEndDate, String nwoMade, String nwoStatus, String postestdate, String postoptone, String postopttwo) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = 0;
        ContentValues cv = new ContentValues();

        cv.put("userid", userId);
        cv.put("makerid", makerId);
        cv.put("nwoqty", nwoQty);
        cv.put("nwopic", nwoPic);
        cv.put("nwodetails", nwoDetails);
        cv.put("nwocategory", nwoCategory);
        cv.put("nwotype", nwoType);
        cv.put("nwosize", nwoSize);
        cv.put("nwocolor", nwoColor);
        cv.put("nwodate", nwoDate);
        cv.put("nwoenddate", nwoEndDate);
        cv.put("nwomade", nwoMade);
        cv.put("nwostatus", nwoStatus);
        cv.put("postestdate", postestdate);
        cv.put("postoptone", postoptone);
        cv.put("postopttwo", postopttwo);
        result = db.insert(TBL_NW_ORDER, null, cv);

        db.close();
        return result;
    }

    public ArrayList<NewWorldOrder> getAllNewWorldOrderNwoId(int nwoId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<NewWorldOrder> list = new ArrayList<>();
        Cursor c = db.query(TBL_NW_ORDER,null,null,null,null,null,"makerid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int nwoid = c.getInt(c.getColumnIndex("nwoid"));

            if(nwoid == nwoId) {
                int makerid = c.getInt(c.getColumnIndex("makerid"));
                int userId = c.getInt(c.getColumnIndex("userid"));
                int nwoQty = c.getInt(c.getColumnIndex("nwoqty"));
                String nwoPic = c.getString(c.getColumnIndex("nwopic"));
                String nwoDetails= c.getString(c.getColumnIndex("nwodetails"));
                String nwoCategory = c.getString(c.getColumnIndex("nwocategory"));
                String nwoType = c.getString(c.getColumnIndex("nwotype"));
                String nwoSize = c.getString(c.getColumnIndex("nwosize"));
                String nwoColor  = c.getString(c.getColumnIndex("nwocolor"));
                String nwoDate = c.getString(c.getColumnIndex("nwodate"));
                String nwoEndDate = c.getString(c.getColumnIndex("nwoenddate"));
//                String nwoMake = c.getString(c.getColumnIndex("nwomake"));
                String nwoMade = c.getString(c.getColumnIndex("nwomade"));
                String nwoStatus = c.getString(c.getColumnIndex("nwostatus"));
                String postestdate = c.getString(c.getColumnIndex("postestdate"));
                String postoptone = c.getString(c.getColumnIndex("postoptone"));
                String postopttwo = c.getString(c.getColumnIndex("postopttwo"));

                list.add(new NewWorldOrder(nwoid, userId, makerid, nwoQty, Uri.parse(nwoPic), nwoDetails, nwoCategory, nwoType, nwoSize, nwoColor, nwoDate, nwoEndDate, nwoMade, nwoStatus, postestdate, postoptone, postopttwo));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public void editNewWorldOrderOffer(int nwoid, String date, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("nwodate", date);
        cv.put("nwostatus", status);

        db.update(TBL_NW_ORDER, cv, "nwoid=" +nwoid, null);

        db.close();
    }

    public ArrayList<NewWorldOrder> getAllNewWorldOrderMaker(int makerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<NewWorldOrder> list = new ArrayList<>();
        Cursor c = db.query(TBL_NW_ORDER,null,null,null,null,null,"makerid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int makerid = c.getInt(c.getColumnIndex("makerid"));

            if(makerId == makerid) {
                int nwoid = c.getInt(c.getColumnIndex("nwoid"));
                int userId = c.getInt(c.getColumnIndex("userid"));
                int nwoQty = c.getInt(c.getColumnIndex("nwoqty"));
                String nwoPic = c.getString(c.getColumnIndex("nwopic"));
                String nwoDetails= c.getString(c.getColumnIndex("nwodetails"));
                String nwoCategory = c.getString(c.getColumnIndex("nwocategory"));
                String nwoType = c.getString(c.getColumnIndex("nwotype"));
                String nwoSize = c.getString(c.getColumnIndex("nwosize"));
                String nwoColor  = c.getString(c.getColumnIndex("nwocolor"));
                String nwoDate = c.getString(c.getColumnIndex("nwodate"));
                String nwoEndDate = c.getString(c.getColumnIndex("nwoenddate"));
//                String nwoMake = c.getString(c.getColumnIndex("nwomake"));
                String nwoMade = c.getString(c.getColumnIndex("nwomade"));
                String nwoStatus = c.getString(c.getColumnIndex("nwostatus"));


                String postestdate = c.getString(c.getColumnIndex("postestdate"));
                String postoptone = c.getString(c.getColumnIndex("postoptone"));
                String postopttwo = c.getString(c.getColumnIndex("postopttwo"));

                list.add(new NewWorldOrder(nwoid, userId, makerid, nwoQty, Uri.parse(nwoPic), nwoDetails, nwoCategory, nwoType, nwoSize, nwoColor, nwoDate, nwoEndDate, nwoMade, nwoStatus, postestdate, postoptone, postopttwo));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<NewWorldOrder> getAllNewWorldOrderUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<NewWorldOrder> list = new ArrayList<>();
        Cursor c = db.query(TBL_NW_ORDER,null,null,null,null,null,"makerid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userid = c.getInt(c.getColumnIndex("userid"));

            if(userId == userid) {
                int nwoid = c.getInt(c.getColumnIndex("nwoid"));
                int makerid = c.getInt(c.getColumnIndex("makerid"));
                int nwoQty = c.getInt(c.getColumnIndex("nwoqty"));
                String nwoPic = c.getString(c.getColumnIndex("nwopic"));
                String nwoDetails= c.getString(c.getColumnIndex("nwodetails"));
                String nwoCategory = c.getString(c.getColumnIndex("nwocategory"));
                String nwoType = c.getString(c.getColumnIndex("nwotype"));
                String nwoSize = c.getString(c.getColumnIndex("nwosize"));
                String nwoColor  = c.getString(c.getColumnIndex("nwocolor"));
                String nwoDate = c.getString(c.getColumnIndex("nwodate"));
                String nwoEndDate = c.getString(c.getColumnIndex("nwoenddate"));
//                String nwoMake = c.getString(c.getColumnIndex("nwomake"));
                String nwoMade = c.getString(c.getColumnIndex("nwomade"));
                String nwoStatus = c.getString(c.getColumnIndex("nwostatus"));

                String postestdate = c.getString(c.getColumnIndex("postestdate"));
                String postoptone = c.getString(c.getColumnIndex("postoptone"));
                String postopttwo = c.getString(c.getColumnIndex("postopttwo"));

                list.add(new NewWorldOrder(nwoid, userId, makerid, nwoQty, Uri.parse(nwoPic), nwoDetails, nwoCategory, nwoType, nwoSize, nwoColor, nwoDate, nwoEndDate, nwoMade, nwoStatus, postestdate, postoptone, postopttwo));
            }
        }

        c.close();
        db.close();
        return list;
    }

    public ArrayList<NewWorldOrder> getAllNewWorldOrderUserMaker(int userId, int makerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<NewWorldOrder> list = new ArrayList<>();
        Cursor c = db.query(TBL_NW_ORDER,null,null,null,null,null,"makerid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int userid = c.getInt(c.getColumnIndex("userid"));
            int makerid = c.getInt(c.getColumnIndex("makerid"));

            if(userId == userid && makerId == makerid) {
                int nwoid = c.getInt(c.getColumnIndex("nwoid"));
                int nwoQty = c.getInt(c.getColumnIndex("nwoqty"));
                String nwoPic = c.getString(c.getColumnIndex("nwopic"));
                String nwoDetails= c.getString(c.getColumnIndex("nwodetails"));
                String nwoCategory = c.getString(c.getColumnIndex("nwocategory"));
                String nwoType = c.getString(c.getColumnIndex("nwotype"));
                String nwoSize = c.getString(c.getColumnIndex("nwosize"));
                String nwoColor  = c.getString(c.getColumnIndex("nwocolor"));
                String nwoDate = c.getString(c.getColumnIndex("nwodate"));
                String nwoEndDate = c.getString(c.getColumnIndex("nwoenddate"));
//                String nwoMake = c.getString(c.getColumnIndex("nwomake"));
                String nwoMade = c.getString(c.getColumnIndex("nwomade"));
                String nwoStatus = c.getString(c.getColumnIndex("nwostatus"));

                String postestdate = c.getString(c.getColumnIndex("postestdate"));
                String postoptone = c.getString(c.getColumnIndex("postoptone"));
                String postopttwo = c.getString(c.getColumnIndex("postopttwo"));

                list.add(new NewWorldOrder(nwoid, userId, makerid, nwoQty, Uri.parse(nwoPic), nwoDetails, nwoCategory, nwoType, nwoSize, nwoColor, nwoDate, nwoEndDate, nwoMade, nwoStatus, postestdate, postoptone, postopttwo));
                break;
            }
        }

        c.close();
        db.close();
        return list;
    }

    public void editNewWorldOrderStatus(int nwoid, int userId, int makerId, String made, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("nwomade", made);
        cv.put("nwostatus", status);
        db.update(TBL_NW_ORDER, cv, "nwoid=" +nwoid+ " AND userid=" +userId+ " AND makerid=" +makerId, null);

        db.close();
    }

    public ArrayList<NewWorldOrder> getAllNewWorldOrder() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<NewWorldOrder> list = new ArrayList<>();
        Cursor c = db.query(TBL_NW_ORDER,null,null,null,null,null,"makerid");
        list.clear();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int nwoid = c.getInt(c.getColumnIndex("nwoid"));
            int userId = c.getInt(c.getColumnIndex("userid"));
            int makerid = c.getInt(c.getColumnIndex("makerid"));
            int nwoQty = c.getInt(c.getColumnIndex("nwoqty"));
            String nwoPic = c.getString(c.getColumnIndex("nwopic"));
            String nwoDetails= c.getString(c.getColumnIndex("nwodetails"));
            String nwoCategory = c.getString(c.getColumnIndex("nwocategory"));
            String nwoType = c.getString(c.getColumnIndex("nwotype"));
            String nwoSize = c.getString(c.getColumnIndex("nwosize"));
            String nwoColor  = c.getString(c.getColumnIndex("nwocolor"));
            String nwoDate = c.getString(c.getColumnIndex("nwodate"));
            String nwoEndDate = c.getString(c.getColumnIndex("nwoenddate"));
//            String nwoMake = c.getString(c.getColumnIndex("nwomake"));
            String nwoMade = c.getString(c.getColumnIndex("nwomade"));
            String nwoStatus = c.getString(c.getColumnIndex("nwostatus"));

            String postestdate = c.getString(c.getColumnIndex("postestdate"));
            String postoptone = c.getString(c.getColumnIndex("postoptone"));
            String postopttwo = c.getString(c.getColumnIndex("postopttwo"));

            list.add(new NewWorldOrder(nwoid, userId, makerid, nwoQty, Uri.parse(nwoPic), nwoDetails, nwoCategory, nwoType, nwoSize, nwoColor, nwoDate, nwoEndDate, nwoMade, nwoStatus, postestdate, postoptone, postopttwo));
        }

        c.close();
        db.close();
        return list;
    }

    //************************************************************************************************************
    // DELETE
    //************************************************************************************************************
    public void deleteAllUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_USER, null, null);

        db.close();
    }

    public void deleteAllAccount() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_ACCOUNT, null, null);

        db.close();
    }

    public void deleteAllReview() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_REVIEW, null, null);

        db.close();
    }

    public void deleteAllMaker() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_MAKER, null, null);

        db.close();
    }

    public void deleteAllProduct() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_PRODUCT, null, null);

        db.close();
    }

    public void deleteAllPost() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_POST, null, null);

        db.close();
    }

    public void deletePostOffer(int postId, int shopId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_POST_OFFER, "postid=" +postId+ " AND shopid=" +shopId, null);

        db.close();
    }

    public void deleteAllPostOffer() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_POST_OFFER, null, null);

        db.close();
    }

    public void deleteAllSearch() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_SEARCH, null, null);

        db.close();
    }

    public void deleteAllOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_ORDER, null, null);

        db.close();
    }

    public void deleteAllNotification() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_NOTIFICATION, null, null);

        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}