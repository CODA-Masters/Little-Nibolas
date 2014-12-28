package com.codamasters.android;

import com.google.android.gms.ads.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.codamasters.LittleNibolas;
import com.codamasters.LNHelpers.ActionResolver;

public class AndroidLauncher extends AndroidApplication implements ActionResolver, ConnectionCallbacks, OnConnectionFailedListener{
	protected AdView adView;
	protected View gameView;
	private static final String AD_UNIT_ID_BANNER = "ca-app-pub-2273861139088572/2724446640";
	private static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-2273861139088572/2547819841";
	private static final String GOOGLE_PLAY_URL = "";
	
	private InterstitialAd interstitialAd;
	private GoogleApiClient mGoogleApiClient;
	
	 // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;
    private static final String STATE_RESOLVING_ERROR = "resolving_error";
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		
		// Do the stuff that initialize() would do for you
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

	    RelativeLayout layout = new RelativeLayout(this);
	    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
	    layout.setLayoutParams(params);

	    AdView admobView = createAdView();
	    layout.addView(admobView);
	    View gameView = createGameView(config);
	    layout.addView(gameView);

	    setContentView(layout);
	    startAdvertising(admobView);
	    
	    interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(AD_UNIT_ID_INTERSTITIAL);
        interstitialAd.setAdListener(new AdListener() {
          @Override
          public void onAdLoaded() {
            //Toast.makeText(getApplicationContext(), "Finished Loading Interstitial", Toast.LENGTH_SHORT).show();
          }
          @Override
          public void onAdClosed() {
            //Toast.makeText(getApplicationContext(), "Closed Interstitial", Toast.LENGTH_SHORT).show();
          }
        });
        
     // Create a GoogleApiClient instance
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Games.API)
                .addScope(Games.SCOPE_GAMES)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        
        mResolvingError = savedInstanceState != null
                && savedInstanceState.getBoolean(STATE_RESOLVING_ERROR, false);
	}
	
	private AdView createAdView() {
	    adView = new AdView(this);
	    adView.setAdSize(AdSize.SMART_BANNER);
	    adView.setAdUnitId(AD_UNIT_ID_BANNER);
	    adView.setId(12345); // this is an arbitrary id, allows for relative positioning in createGameView()
	    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
	    params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
	    adView.setLayoutParams(params);
	    adView.setBackgroundColor(Color.BLACK);
	    return adView;
	  }

	  private View createGameView(AndroidApplicationConfiguration cfg) {
	    gameView = initializeForView(new LittleNibolas(this), cfg);
	    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
	    params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
	    params.addRule(RelativeLayout.ABOVE, adView.getId());
	    gameView.setLayoutParams(params);
	    return gameView;
	  }

	  private void startAdvertising(AdView adView) {
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);
	  }
	  
	  @Override
	    public void showOrLoadInterstital() {
	      try {
	        runOnUiThread(new Runnable() {
	          public void run() {
	            if (interstitialAd.isLoaded()) {
	              interstitialAd.show();
	              //Toast.makeText(getApplicationContext(), "Showing Interstitial", Toast.LENGTH_SHORT).show();
	            }
	            else {
	              AdRequest interstitialRequest = new AdRequest.Builder().build();
	              interstitialAd.loadAd(interstitialRequest);
	              //Toast.makeText(getApplicationContext(), "Loading Interstitial", Toast.LENGTH_SHORT).show();
	            }
	          }
	        });
	      } catch (Exception e) {
	      }
	    }
	  
	  	@Override
		protected void onStart() {
		    super.onStart();
		    if (!mResolvingError) {  // more about this later
		        mGoogleApiClient.connect();
		    }
		}
		
		@Override
		protected void onStop() {
		    mGoogleApiClient.disconnect();
		    super.onStop();
		}
	  
	  @Override
	  public void onResume() {
	    super.onResume();
	    if (adView != null) adView.resume();
	  }

	  @Override
	  public void onPause() {
	    if (adView != null) adView.pause();
	    super.onPause();
	  }

	  @Override
	  public void onDestroy() {
	    if (adView != null) adView.destroy();
	    super.onDestroy();
	  }

	  @Override
	  public void onBackPressed() {
	    final Dialog dialog = new Dialog(this);
	    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

	    LinearLayout ll = new LinearLayout(this);
	    ll.setOrientation(LinearLayout.VERTICAL);

	    Button b1 = new Button(this);
	    b1.setText("Salir");
	    b1.setOnClickListener(new OnClickListener() {
	      public void onClick(View v) {
	        finish();
	      }
	    });
	    ll.addView(b1);

	    dialog.setContentView(ll);
	    dialog.show();
	  }

	@Override
	public void submitScore(String id, int score) {
		if(mGoogleApiClient.isConnected()){
			Games.Leaderboards.submitScore(mGoogleApiClient, id, score);
		}
		
	}

	@Override
	public void displayLeaderboard(String id) {
		if(mGoogleApiClient.isConnected()){
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
			        id), 0);
		}
		
	}
	
	@Override
	public void unlockAchievement(String id){
		if(mGoogleApiClient.isConnected()){
			Games.Achievements.unlock(mGoogleApiClient, id);
		}
	}
	
	@Override
	public void displayAchievements(){
		if(mGoogleApiClient.isConnected()){
			 startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient), 0);
		}
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (result.hasResolution()) {
            try {
                mResolvingError = true;
                result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                mGoogleApiClient.connect();
            }
        } else {
            // Show dialog using GooglePlayServicesUtil.getErrorDialog()
            showErrorDialog(result.getErrorCode());
            mResolvingError = true;
        }
		
	}
	
	// The rest of this code is all about building the error dialog

    /* Creates a dialog for an error message */
    private void showErrorDialog(int errorCode) {
        // Create a fragment for the error dialog
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        // Pass the error that should be displayed
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(getFragmentManager(), "errordialog");
    }

    /* Called from ErrorDialogFragment when the dialog is dismissed. */
    public void onDialogDismissed() {
        mResolvingError = false;
    }

    /* A fragment to display an error dialog */
    public static class ErrorDialogFragment extends DialogFragment {
        public ErrorDialogFragment() { }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the error code and retrieve the appropriate dialog
            int errorCode = this.getArguments().getInt(DIALOG_ERROR);
            return GooglePlayServicesUtil.getErrorDialog(errorCode,
                    this.getActivity(), REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            ((AndroidLauncher)getActivity()).onDialogDismissed();
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_RESOLVE_ERROR) {
            mResolvingError = false;
            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!mGoogleApiClient.isConnecting() &&
                        !mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            }
        }
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_RESOLVING_ERROR, mResolvingError);
    }

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		
	}
}
