package edu.utexas.ee461l.slidesnap;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;

public class SlidePuzzleActivity extends Activity implements OnKeyListener {
    private static final String LOG_TAG = SlidePuzzleActivity.class.getName();

    private static final int MENU_NEW = 0;
    private static final int MENU_SCORES = 1;
    private static final int MENU_SETTINGS = 2;
    public static final boolean SHOW_STATUS = true;
    public static final int BLANK_LOCATION = 1;
    private static final long GAME_OVER_TIME = 60000 * 2; // 1 minute * 2 = 2 minutes

    public static boolean gameOver = false;
    public static boolean activityOver = false;

    public static boolean PUZZLE_SOLVED = false;
    public static View finalView;

    private static int mSelection;
    public static Uri mCustomLocation;

    private ImageView mCompleteView;
    private TileView mTileView;
    private Chronometer mTimerView;
    private long mTime;

    private String objectID;

    public ParseUser user;
    public ParseQuery<ParseObject> query;



    private AnimationListener mCompleteAnimListener = new AnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            mTileView.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}

        @Override
        public void onAnimationStart(Animation animation) {}
    };

    /** Called when the user clicks the Send button */
    public void toPreferences(View view) {
        finalView = view;
        if(PUZZLE_SOLVED){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            SaveImageDialog dialog = new SaveImageDialog();
            dialog.show(ft, "dialog");

        }else{
            Intent intent = new Intent(this, NotificationsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

       // requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.slide_puzzle);
        mTileView = (TileView) findViewById(R.id.tile_view);
        mTileView.requestFocus();
        mTileView.setOnKeyListener(this);

        mCompleteView = (ImageView) findViewById(R.id.complete_view);
        mCompleteView.setImageBitmap(mTileView.getCurrentImage());

        mTimerView = (Chronometer) findViewById(R.id.timer_view);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mTimerView.setTextColor(getResources().getColor(R.drawable.default_fg_color));

        user = ParseUser.getCurrentUser();

        Bundle extras = getIntent().getExtras();
        String pathUri = extras.getString("pathUri");
        objectID = extras.getString("objectID");

        setCustomLocation(pathUri);

        if (icicle == null) {
            int blankLoc = BLANK_LOCATION;
            mTileView.newGame(null, blankLoc, mTimerView);
            mTime = 0;
        } else {
            Parcelable[] parcelables = icicle.getParcelableArray("tiles");
            Tile[] tiles = null;
            if (parcelables != null) {
                int len = parcelables.length;

                tiles = new Tile[len];
                for (int i = 0; i < len; i++) {
                    tiles[i] = (Tile) parcelables[i];
                }
            }

            mTileView.newGame(tiles, icicle.getInt("blank_first"), mTimerView);
            mTime = icicle.getLong("time", 0);
        }
    }

    // THIS METHOD SETS THE LOCATION FOR THE CUSTOM IMAGE
    // THEREFORE, PASS IN THE CUSTOM URI HERE
    public static void setCustomLocation(String pathUri) {
        if(pathUri.contains("file:")){
            pathUri = pathUri.substring(7);
        }
        File mediaFile = new File(pathUri);
        Uri location = Uri.fromFile(mediaFile);
        mCustomLocation = location;
        mSelection = TileView.IMAGE_CUSTOM;
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (SHOW_STATUS) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        //int bgColor = prefs.getInt(PuzzlePreferenceActivity.BACKGROUND_COLOR, getResources().getColor(R.drawable.default_bg_color));
        int bgColor = getResources().getColor(R.drawable.default_bg_color);
        findViewById(R.id.layout).setBackgroundColor(bgColor);

        mTileView.updateInstantPrefs();
        mTimerView.setBase(SystemClock.elapsedRealtime() - mTime);
        if (!mTileView.isSolved()) {
            mTimerView.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (!mTileView.isSolved()) {
            mTime = (SystemClock.elapsedRealtime() - mTimerView.getBase());
        }
        mTimerView.stop();
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        // Prevent user from moving tiles if the puzzle has been solved 
        if (mTileView.isSolved()) {
            return false;
        }

        boolean moved;
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_DOWN: {
                    moved = mTileView.move(TileView.DIR_DOWN);
                    break;
                }
                case KeyEvent.KEYCODE_DPAD_UP: {
                    moved = mTileView.move(TileView.DIR_UP);
                    break;
                }
                case KeyEvent.KEYCODE_DPAD_LEFT: {
                    moved = mTileView.move(TileView.DIR_LEFT);
                    break;
                }
                case KeyEvent.KEYCODE_DPAD_RIGHT: {
                    moved = mTileView.move(TileView.DIR_RIGHT);
                    break;
                }
                default:
                    return false;
            }

            if (mTileView.checkSolved()) {
                mCompleteView.setImageBitmap(mTileView.getCurrentImage());
                mCompleteView.setVisibility(View.VISIBLE);

                Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
                animation.setAnimationListener(mCompleteAnimListener);
                mCompleteView.startAnimation(animation);
                //postScore();
            }
            return true;
        }

        return false;
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        // Prevent user from moving tiles if the puzzle has been solved
        if((SystemClock.elapsedRealtime() - mTimerView.getBase()) >= GAME_OVER_TIME){
            mTimerView.stop();
            gameOver = true;

            //TODO Parse Stuff (Lost Game)
            query = ParseQuery.getQuery("imageData");
                    try{
                        ParseObject imageData = query.get(objectID);
                        imageData.put("status", "wrong");
                        imageData.remove("pathUri"); // May need to change
                        imageData.save();
                    }catch (ParseException ex){
                        ex.printStackTrace();
                    }

            finish();
//            if(activityOver){
//                  // Do nothing
//            }else{
//                Intent intent = new Intent(this, NotificationsActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                startActivity(intent);
//                activityOver = true;
//            }

            return false;
        }

        if (mTileView.isSolved()) {
            return false;
        }

        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mTileView.grabTile(event.getX(), event.getY());
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                mTileView.dragTile(event.getX(), event.getY());
                return true;
            }
            case MotionEvent.ACTION_UP: {
                boolean moved = mTileView.dropTile(event.getX(), event.getY());

                if (mTileView.checkSolved()) {
                    ImageButton save = (ImageButton) findViewById(R.id.saveButton);
                    save.setVisibility(View.VISIBLE);
                    mCompleteView.setImageBitmap(mTileView.getCurrentImage());
                    mCompleteView.setVisibility(View.VISIBLE);

                    Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
                    animation.setAnimationListener(mCompleteAnimListener);
                    mCompleteView.startAnimation(animation);
                    mTimerView.stop();
                    PUZZLE_SOLVED = true;
                    user.increment("trophies");
                    try{
                        user.save();
                    } catch(ParseException e){

                    }
                    // TODO Parse Stuff (Solved)
                    query = ParseQuery.getQuery("imageData");
                          try{
                              ParseObject imageData = query.get(objectID);
                              imageData.put("status", "correct");
                              imageData.remove("pathUri"); // May need to change
                              imageData.save();
                          }catch (ParseException ex){
                              ex.printStackTrace();
                          }
                        }

                }
                return true;
            }
        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();

        menu.add(0, MENU_NEW, 0, R.string.menu_new).setIcon(
                R.drawable.menu_new_game);
        menu.add(0, MENU_SCORES, 0, R.string.menu_scores).setIcon(
                R.drawable.menu_high_scores);
        menu.add(0, MENU_SETTINGS, 0, R.string.menu_settings).setIcon(
                R.drawable.ic_menu_preferences);

        return true;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArray("tiles", mTileView.getTiles());
        outState.putInt("blank_first", mTileView.mBlankLocation);
        outState.putLong("time", mTime);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}