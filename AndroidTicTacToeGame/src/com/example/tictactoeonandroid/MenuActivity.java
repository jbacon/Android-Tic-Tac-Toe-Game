//Josh Bacon
//Menu Activity
//First Activity of app
//Decsription: provides menu with options to player vs another player
// player vs computer, team selection, difficulty selection, and about app information.


package com.example.tictactoeonandroid;

import TicTacToeGame.TicTacToe;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.Toast;

public class MenuActivity extends Activity implements OnClickListener{
	private Button pvpButton;
	private Button pvcButton;
	private Button player1ChooseTeamButton;
	private Button player2ChooseTeamButton;
	private Button aboutButton;
	private ImageView player1ChooseTeamImage;
	private ImageView player2ChooseTeamImage;
	private AlertDialog.Builder dialogBuilder;
	private AlertDialog.Builder dialogBuilder2;
	private AlertDialog player1Selector;
	private AlertDialog player2Selector;
	private ArrayAdapter<String> adapter;
	private String[] teamNames;
	private TypedArray teamImgIds;
	private int player1TeamImgId;
	private int player2TeamImgId;
	private int difficulty = TicTacToe.DIFFICULTY_EASY;
	private MediaPlayer mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		initialize();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void initialize() {
		mp = MediaPlayer.create(this, R.raw.click);
		pvpButton = (Button) findViewById(R.id.playButton);
		pvcButton = (Button) findViewById(R.id.playCompButton);
		player1ChooseTeamButton = (Button) findViewById(R.id.player1ChooseTeamButton);
		player2ChooseTeamButton = (Button) findViewById(R.id.player2ChooseTeamButton);
		player1ChooseTeamImage = (ImageView) findViewById(R.id.player1ChooseTeamImage);
		player2ChooseTeamImage = (ImageView) findViewById(R.id.player2ChooseTeamImage);
		aboutButton = (Button) findViewById(R.id.aboutButton);
		pvpButton.setOnClickListener(this);
		pvcButton.setOnClickListener(this);
		player1ChooseTeamButton.setOnClickListener(this);
		player2ChooseTeamButton.setOnClickListener(this);
		aboutButton.setOnClickListener(this);
		dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder2 = new AlertDialog.Builder(this);
		dialogBuilder.setCancelable(false);
		dialogBuilder2.setCancelable(false);
		dialogBuilder.setTitle("Player 1 Team");
		dialogBuilder2.setTitle("Player 2 Team");
		teamNames = getResources().getStringArray(R.array.teams);
		teamImgIds = getResources().obtainTypedArray(R.array.teamImages);
		player1TeamImgId = R.drawable.x;	//Default image
		player2TeamImgId = R.drawable.o;	//Default image
		adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, teamNames);
		//final ArrayAdapter<String> adapterIcon = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, R.array.teams);
		
		dialogBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				player1TeamImgId = teamImgIds.getResourceId(which, -1);
				player1ChooseTeamImage.setBackgroundResource(player1TeamImgId);
				Toast.makeText(getApplicationContext(), "You Choose the:\n"+teamNames[which], Toast.LENGTH_SHORT).show();
				Log.d("TTT", "You choose team"+which);
				dialog.dismiss();
			}
		});
		player1Selector = dialogBuilder.create();
		
		dialogBuilder2.setAdapter(adapter, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				player2TeamImgId = teamImgIds.getResourceId(which, -1);
				player2ChooseTeamImage.setBackgroundResource(player2TeamImgId);
				Toast.makeText(getApplicationContext(), "You Choose the:\n"+teamNames[which], Toast.LENGTH_SHORT).show();
				Log.d("TTT", "You choose team"+which);
				dialog.dismiss();
			}
		});
		player2Selector = dialogBuilder2.create();
	}
	@Override
	public void onClick(View v) {
		mp.start();
		switch(v.getId()) {
			case R.id.playButton:
				Log.d("TTT", "You clicked Player vs Player Button");
				Intent pvpIntent = new Intent(this, GameActivity.class);
				pvpIntent.putExtra("player1TeamImgId", player1TeamImgId);
				pvpIntent.putExtra("player2TeamImgId", player2TeamImgId);
				pvpIntent.putExtra("numPlayers", 2);
				startActivity(pvpIntent);
				break;
			case R.id.playCompButton:
				Log.d("TTT", "You clicked Player vs Computer Button");
				Intent playCompIntent = new Intent(this, GameActivity.class);
				playCompIntent.putExtra("player1TeamImgId", player1TeamImgId);
				playCompIntent.putExtra("player2TeamImgId", player2TeamImgId);
				playCompIntent.putExtra("numPlayers", 1);
				playCompIntent.putExtra("difficulty", difficulty);
				startActivity(playCompIntent);
				break;
			case R.id.player1ChooseTeamButton:
				Log.d("TTT", "You clicked choose Player 1 Team Button");
				player1Selector.show();
				break;
			case R.id.player2ChooseTeamButton:
				Log.d("TTT", "You clicked choose Player 2 Team Button");
				player2Selector.show();
				break;
			case R.id.aboutButton:
				Log.d("TTT", "You clicked About Button");
				Intent aboutIntent = new Intent(this, AboutActivity.class);
				startActivity(aboutIntent);
				break;
		}
	}
	public void onRadioButtonClicked(View view) {
		mp.start();
		boolean checked = ((RadioButton) view).isChecked();
		switch(view.getId()) {
		case R.id.rd_btn_easy:
			if(checked)
				difficulty = TicTacToe.DIFFICULTY_EASY;
			break;
		case R.id.rd_btn_medium:
			if(checked)
				difficulty = TicTacToe.DIFFICULTY_MEDIUM;
			break;
		case R.id.rd_btn_hard:
			if(checked)
				difficulty = TicTacToe.DIFFICULTY_HARD;
			break;
		}
	}
}
